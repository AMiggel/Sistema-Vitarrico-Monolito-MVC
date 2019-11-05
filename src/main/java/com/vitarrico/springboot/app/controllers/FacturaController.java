package com.vitarrico.springboot.app.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vitarrico.springboot.app.models.entity.Cliente;
import com.vitarrico.springboot.app.models.entity.Factura;
import com.vitarrico.springboot.app.models.entity.ItemFactura;
import com.vitarrico.springboot.app.models.entity.Lote;
import com.vitarrico.springboot.app.models.service.IClienteService;
import com.vitarrico.springboot.app.models.service.productos.ServicioLote;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private ServicioLote servicioProducto;
	

	private final Logger log = LoggerFactory.getLogger(getClass());

	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Factura factura = clienteService.fetchFacturaByIdWithClienteWhithItemFacturaWithProducto(id);

		if (factura == null) {
			flash.addFlashAttribute("error", "El pedido no existe en la base de datos!");
			return "redirect:/listar";
		}

		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Pedido: ".concat(factura.getDescripcion()));
		return "factura/ver";
	}

	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable(value = "clienteId") Long clienteId, Map<String, Object> model,
			RedirectAttributes flash) {

		Cliente cliente = clienteService.findOne(clienteId);

		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}

		Factura factura = new Factura();
		factura.setCliente(cliente);

		model.put("factura", factura);
		model.put("titulo", "Crear pedido");

		return "factura/form";
	}

	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Lote> cargarProductos(@PathVariable String term) {
		return clienteService.findByNombre(term);
	}

	@PostMapping("/form")
	public String guardar(@Valid Factura factura, BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear pedido");
			return "factura/form";
		}

		if (itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "Crear pedido");
			model.addAttribute("error", "Error: El pedido debe tener al menos un producto!");
			return "factura/form";
		}

	
		
		for (int i = 0; i < itemId.length; i++) {
			Lote producto = clienteService.findProductoById(itemId[i]);

			ItemFactura linea = new ItemFactura();
			linea.setCantidad(cantidad[i]);
	
			if(linea.getCantidad() <= 0) {
				model.addAttribute("titulo", "Crear pedido");
				model.addAttribute("error", "ingrese una cantidad de productos valida para el item: " + producto.getNombre());
				return "factura/form";
			}
			
			if (linea.getCantidad() > producto.getCantidadDisponible()) {
				model.addAttribute("titulo", "Crear pedido");
				model.addAttribute("error", "No hay productos suficientes para " + producto.getNombre()
						+ " cantidad disponible " + producto.getCantidadDisponible());

				return "factura/form";
			}

			linea.setProducto(producto);
			factura.addItemFactura(linea);
			Integer restarCantidad = producto.getCantidadDisponible() - linea.getCantidad();
			if (restarCantidad == 0) {
				producto.setCantidadDisponible(0);
			} else {
				producto.setCantidadDisponible(restarCantidad);
			}
			servicioProducto.modificarCantidadDisponible(producto.getId(), producto);
			//aqui
			if (producto.getCantidadDisponible() <= producto.getStockMinimo() && producto.getCantidadDisponible() != 0) {
				servicioProducto.enviarEmailProductoAgotado(producto, servicioProducto.buscarMailPorId((long) 1).getDestinatario());
			} else if (producto.getCantidadDisponible() == 0){
				servicioProducto.enviarEmailProductoAgotado2(producto, servicioProducto.buscarMailPorId((long)1).getDestinatario());
			}

			log.info("ID: " + itemId[i].toString() + ", cantidad: " + cantidad[i].toString());
		}
		

		clienteService.saveFactura(factura);

		status.setComplete();

		flash.addFlashAttribute("success", "pedido creado con éxito!");

		return "redirect:/ver/" + factura.getCliente().getId();

	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		Factura factura = clienteService.findFacturaById(id);

		if (factura != null) {
			clienteService.deleteFactura(id);
			flash.addFlashAttribute("success", "pedido eliminado con éxito!");
			return "redirect:/ver/" + factura.getCliente().getId();
		}
		flash.addFlashAttribute("error", "El pedido no existe en la base de datos, no se pudo eliminar!");

		return "redirect:/listar";
	}

}
