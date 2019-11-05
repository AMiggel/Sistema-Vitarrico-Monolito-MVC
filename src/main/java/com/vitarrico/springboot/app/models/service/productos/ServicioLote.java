package com.vitarrico.springboot.app.models.service.productos;

import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.vitarrico.springboot.app.models.dao.ILoteDao;
import com.vitarrico.springboot.app.models.dao.IMailDao;
import com.vitarrico.springboot.app.models.entity.Lote;
import com.vitarrico.springboot.app.models.entity.Mail;
import com.vitarrico.springboot.app.models.service.productos.excepcion.ExcepcionInventario;


@Service
public class ServicioLote implements IServicioLote {

	public static final String PRODUCTO_INEXISTENTE = "El producto no existe en la base de datos";
	public static final String CANTIDAD_CREADA_INVALIDA = "Debe ingresar una cantidad válida";
	public static final String CAMPO_NULO = " el campo %s es requerido";

	@Autowired
	private JavaMailSender javaMailSender;
	
	
	@Autowired
	private ILoteDao repositorioProducto;
	
	@Autowired
	private IMailDao mailDao;

	@Override
	public List<Lote> listar() {
		return (List<Lote>) repositorioProducto.findAllByOrderByFechaVencimientoAsc();
	}

	@Override
	public Lote crearLote(Lote producto) {

		producto.setCantidadDisponible(producto.getCantidadCreada());
		if (producto.getCantidadCreada() <= 0) {
			throw new ExcepcionInventario(CANTIDAD_CREADA_INVALIDA);
		} else if (producto.getFechaVencimiento() == null) {
			throw new ExcepcionInventario(String.format(CAMPO_NULO, "Fecha Vencimiento"));
		} else if (producto.getNombre() == null) {
			throw new ExcepcionInventario(String.format(CAMPO_NULO, "Nombre"));
		} else if (producto.getPrecio() == null) {
			throw new ExcepcionInventario(String.format(CAMPO_NULO, "precio"));
		}

		asignarFechaCreacion(producto);

		return repositorioProducto.save(producto);
	}

	@Override
	public Lote buscarLotePorId(Long id) {
		Lote producto = repositorioProducto.findById(id).orElse(null);

		if (producto == null) {
			throw new ExcepcionInventario(PRODUCTO_INEXISTENTE);
		} else {
			return producto;
		}
	}

	@Override
	@Transactional
	public void borrarLote(Long id) {
		repositorioProducto.deleteById(id);
	}

	@Override
	public Lote modificarCantidadDisponible(Long id, Lote producto) {
		Lote productoActual = buscarLotePorId(id);

		productoActual.setCantidadDisponible(producto.getCantidadDisponible());

		return repositorioProducto.save(productoActual);
	}

	@Override
	public Lote modificarLote(Long id, Lote producto) {
		Lote productoActual = buscarLotePorId(id);

		int dia = producto.getFechaVencimiento().getDate() - 1;
		producto.getFechaVencimiento().setDate(dia);
		productoActual.setCantidadCreada(producto.getCantidadCreada());
		productoActual.setCantidadDisponible(producto.getCantidadCreada());
		productoActual.setNombre(producto.getNombre());
		productoActual.setFechaVencimiento(producto.getFechaVencimiento());
		productoActual.setPrecio(producto.getPrecio());
		productoActual.setTipoProducto(producto.getTipoProducto());
		return repositorioProducto.save(productoActual);
	}

	public void asignarFechaCreacion(Lote producto) {
		producto.setFechaCreacion(Calendar.getInstance().getTime());
	}

	public void enviarEmailProductoAgotado(Lote producto, String mail) {

		SimpleMailMessage mensaje = new SimpleMailMessage();
		mensaje.setTo(mail);
		mensaje.setSubject("Producto casi se agota");

		mensaje.setText("El producto: " + producto.getNombre() + " con fecha de vencimiento : "
				+ producto.getFechaVencimiento() + " está a punto de agotarse");
		javaMailSender.send(mensaje);
	}

	public void enviarEmailProductoAgotado2(Lote producto, String mail) {

		SimpleMailMessage mensaje = new SimpleMailMessage();
		mensaje.setTo(mail);
		mensaje.setSubject("Producto agotado");

		mensaje.setText("El producto: " + producto.getNombre() + " con fecha de vencimiento : "
				+ producto.getFechaVencimiento() + " se agotó");
		javaMailSender.send(mensaje);
	}

	@Override
	public Lote buscarProductoPorNombre(String nombre) {
		return repositorioProducto.findByNombres(nombre);
	}

	@Override
	public Mail actualizarEmail(Mail email,Long id) {
		Mail mailActual = buscarMailPorId(id);
		mailActual.setDestinatario(email.getDestinatario());
		return mailDao.save(mailActual);
	}

	@Override
	public Mail buscarMailPorId(Long id) {
		return mailDao.findById(id).orElse(null);
	}

	

}
