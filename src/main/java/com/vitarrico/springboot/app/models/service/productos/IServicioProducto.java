package com.vitarrico.springboot.app.models.service.productos;

import java.util.List;

import com.vitarrico.springboot.app.models.entity.Producto;


public interface IServicioProducto {

	public List<Producto> listar();

	/**
	 * Permite crear un producto nuevo
	 * 
	 * @param usuario
	 * @return la entidad del producto
	 */
	public Producto crearProducto(Producto producto);

	/**
	 * Permite buscar un producto ya creado por id
	 * 
	 * @param id
	 * @return la entidad del producto
	 */
	public Producto buscarProductoPorId(Long id);

	public void borrarProducto(Long id);

	public Producto modificarProducto(Long id, Producto producto);

	public Producto modificarCantidadDisponible(Long id, Producto producto);
	
	public Producto buscarProductoPorNombre(String nombre);
}
