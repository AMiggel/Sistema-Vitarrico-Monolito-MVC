package com.vitarrico.springboot.app.models.service.productos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitarrico.springboot.app.models.dao.IProductoDao;
import com.vitarrico.springboot.app.models.entity.Producto;

@Service
public class ServicioProducto implements IServicioProducto{

	@Autowired
	private IProductoDao productoDao;

	@Override
	public List<Producto> listar() {
		return (List<Producto>) productoDao.findAll();
	}

	@Override
	public Producto crearProducto(Producto producto) throws Exception {
		
		if (producto.getNombre()==null) {
			throw new Exception("Nombre es un campo obligatorio");
		} else if (producto.getStockMinimo()<=0 ) {
			throw new Exception("Debe ingresar un stock minimo para notificar que el producto se está agotando");
		} else if (producto.getPrecio()==null) {
			throw new Exception("Ingrese un precio al producto");
		} else if (producto.getTipoProducto()==null) {
			throw new Exception("Ingrese el tipo de producto");
		}
		
		for (int i = 0; i < listar().size(); i++) {
			if (producto.getNombre().equalsIgnoreCase(listar().get(i).getNombre())) {
				throw new Exception("Ya hay registrado un producto con este mismo nombre en el sistema");
			}
		}
		
		return productoDao.save(producto);
	}

	@Override
	public Producto buscarProductoPorId(Long id) {
		return productoDao.findById(id).orElse(null);
	}

	@Override
	public void borrarProducto(Long id) {
		productoDao.deleteById(id);
		
	}

	@Override
	public Producto modificarProducto(Long id, Producto producto) {
		Producto productoActual = buscarProductoPorId(id);
		productoActual.setNombre(producto.getNombre());
		productoActual.setPrecio(producto.getPrecio());
		productoActual.setStockMinimo(producto.getStockMinimo());
		productoActual.setTipoProducto(productoActual.getTipoProducto());
		return productoDao.save(productoActual);
	}

	@Override
	public List<Producto> buscarProductoPorNombre(String nombre) {
		return  productoDao.findByNombreContainingIgnoreCase(nombre);
	}
}
