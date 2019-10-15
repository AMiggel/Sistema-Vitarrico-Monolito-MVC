package com.vitarrico.springboot.app.models.service.productos;

import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.vitarrico.springboot.app.models.dao.IProductoDao;
import com.vitarrico.springboot.app.models.entity.Producto;
import com.vitarrico.springboot.app.models.service.productos.excepcion.ExcepcionInventario;

@Service
public class ServicioProducto implements IServicioProducto {

	public static final String PRODUCTO_INEXISTENTE = "El producto no existe en la base de datos";
	public static final String CANTIDAD_CREADA_INVALIDA = "Debe ingresar una cantidad válida";
	public static final String CAMPO_NULO= " el campo %s es requerido";

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private IProductoDao repositorioProducto;

	@Override
	public List<Producto> listar() {
		return (List<Producto>) repositorioProducto.findAllByOrderByFechaVencimientoAsc();
	}

	@Override
	public Producto crearProducto(Producto producto) {

		
		producto.setCantidadDisponible(producto.getCantidadCreada());
		if (producto.getCantidadCreada() <= 0) {
			throw new ExcepcionInventario(CANTIDAD_CREADA_INVALIDA);
		} else if (producto.getFechaVencimiento() == null) {
			throw new ExcepcionInventario(String.format(CAMPO_NULO,"Fecha Vencimiento" ));
		} else if (producto.getNombre() == null) {
			throw new ExcepcionInventario(String.format(CAMPO_NULO,"Nombre" ));
		}else if (producto.getPrecio() == null) {
			throw new ExcepcionInventario(String.format(CAMPO_NULO,"precio" ));
		}

			asignarFechaCreacion(producto);

			return repositorioProducto.save(producto);
	}

	@Override
	public Producto buscarProductoPorId(Long id) {
		Producto producto = repositorioProducto.findById(id).orElse(null);

		if (producto == null) {
			throw new ExcepcionInventario(PRODUCTO_INEXISTENTE);
		} else {
			return producto;
		}
	}

	@Override
	@Transactional
	public void borrarProducto(Long id) {
		repositorioProducto.deleteById(id);
	}

	@Override
	public Producto modificarCantidadDisponible(Long id, Producto producto) {
		Producto productoActual = buscarProductoPorId(id);

		productoActual.setCantidadDisponible(producto.getCantidadDisponible());

		return repositorioProducto.save(productoActual);
	}

	@Override
	public Producto modificarProducto(Long id, Producto producto) {
		Producto productoActual = buscarProductoPorId(id);
		
		productoActual.setCantidadCreada(producto.getCantidadCreada());
		productoActual.setCantidadDisponible(producto.getCantidadCreada());
		productoActual.setNombre(producto.getNombre());
		productoActual.setFechaVencimiento(producto.getFechaVencimiento());
		productoActual.setPrecio(producto.getPrecio());
		productoActual.setTipoProducto(producto.getTipoProducto());
		return repositorioProducto.save(productoActual);
	}

	public void asignarFechaCreacion(Producto producto) {
		producto.setFechaCreacion(Calendar.getInstance().getTime());
	}

	public void enviarEmailProductoAgotado(Producto producto) {

		SimpleMailMessage mensaje = new SimpleMailMessage();
		mensaje.setTo("amarin@unac.edu.co");
		mensaje.setSubject("Producto agotado");
		mensaje.setText("El producto :" + producto.getNombre() + " con fecha de vencimiento : "
				+ producto.getFechaVencimiento() + "se agotó");

		javaMailSender.send(mensaje);
	}

	@Override
	public Producto buscarProductoPorNombre(String nombre) {
		return repositorioProducto.findByNombres(nombre);
	}

}
