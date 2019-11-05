package com.vitarrico.springboot.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitarrico.springboot.app.models.entity.Lote;
import com.vitarrico.springboot.app.models.entity.Mail;
import com.vitarrico.springboot.app.models.entity.Producto;
import com.vitarrico.springboot.app.models.service.productos.IServicioLote;
import com.vitarrico.springboot.app.models.service.productos.IServicioProducto;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/inventario")
public class ControladorInventario {

	@Autowired
	private IServicioLote servicioLote;
	
	@Autowired
	private IServicioProducto servicioProducto;
	
	@GetMapping(value="/lotes")
	public List<Lote> obtenerLotes(){
		return servicioLote.listar();
	}
	
	@GetMapping(value="/productos")
	public List<Producto> obtenerProductos(){
		return servicioProducto.listar();
	}
	
	@PostMapping(value="/crear-producto")
	public Producto cearProducto(@RequestBody Producto producto) throws Exception {
		return servicioProducto.crearProducto(producto);
	}
	
	
	@PostMapping(value="/crear-lote")
	public Lote crearLote(@RequestBody Lote producto) {
		return servicioLote.crearLote(producto);
	}
	
	@GetMapping(value = "/lote/{id}")
	public Lote buscarLotePorId(@PathVariable(value = "id") Long id) {
		return servicioLote.buscarLotePorId(id);
	}
	
	@PutMapping(value= "modificar-lote/{id}")
	public Lote modificarLote(@PathVariable (value = "id") Long id, @RequestBody Lote producto) {
		return servicioLote.modificarLote(id, producto);
	}
	
	@PutMapping(value= "modificar-cantidad/{id}")
	public Lote modificarCantidadDisponible(@PathVariable (value = "id") Long id, @RequestBody Lote producto) {
		return servicioLote.modificarCantidadDisponible(id, producto);
	}
	
	@DeleteMapping(value= "/{id}")
	public void borrarLote(@PathVariable (value="id")Long id) {
		servicioLote.borrarLote(id);
		
	}
	
	@GetMapping(value="/productos/{nombre}" )
	public List<Producto> buscarProductoPorNombre (@PathVariable (value="nombre") String nombre) {
		return servicioProducto.buscarProductoPorNombre(nombre);
	}
	
	@DeleteMapping(value="/producto/{id}")
	public void eliminarProducto(@PathVariable (value="id") Long id) {
		servicioProducto.borrarProducto(id);
	}
	
	@PutMapping(value= "modificar-producto/{id}")
	public Producto modificarProducto(@PathVariable (value = "id") Long id, @RequestBody Producto producto) {
		return servicioProducto.modificarProducto(id, producto);
	}
	
	@GetMapping(value = "/producto/{id}")
	public Producto buscarProductoPorId(@PathVariable(value = "id") Long id) {
		return servicioProducto.buscarProductoPorId(id);
	}
	

	@PutMapping(value= "modificar-email/{id}")
	public Mail modificarPEmail(@PathVariable (value = "id") Long id, @RequestBody Mail mail) {
		return servicioLote.actualizarEmail(mail, id);
	}
	
	@GetMapping(value = "/buscar-email/{id}")
	public Mail buscarEmailPorId(@PathVariable(value = "id") Long id) {
		return  servicioLote.buscarMailPorId(id);
	}
	
	
}
