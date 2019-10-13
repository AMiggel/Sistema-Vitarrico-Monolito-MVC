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

import com.vitarrico.springboot.app.models.entity.Producto;
import com.vitarrico.springboot.app.models.service.productos.IServicioProducto;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/inventario")
public class ControladorInventario {

	@Autowired
	private IServicioProducto servicioProducto;
	
	@GetMapping(value="/productos")
	public List<Producto> obtenerProductos(){
		return servicioProducto.listar();
	}
	
	@PostMapping(value="/crear-producto")
	public Producto crearProducto(@RequestBody Producto producto) {
		return servicioProducto.crearProducto(producto);
	}
	
	@GetMapping(value = "/producto/{id}")
	public Producto buscarProductoPorId(@PathVariable(value = "id") Long id) {
		return servicioProducto.buscarProductoPorId(id);
	}
	
	@PutMapping(value= "modificar-producto/{id}")
	public Producto modificarProducto(@PathVariable (value = "id") Long id, @RequestBody Producto producto) {
		return servicioProducto.modificarProducto(id, producto);
	}
	
	@PutMapping(value= "modificar-cantidad/{id}")
	public Producto modificarCantidadDisponible(@PathVariable (value = "id") Long id, @RequestBody Producto producto) {
		return servicioProducto.modificarCantidadDisponible(id, producto);
	}
	
	@DeleteMapping(value= "/{id}")
	public void borrarProducto(@PathVariable (value="id")Long id) {
		servicioProducto.borrarProducto(id);
		
	}
	
	@GetMapping(value="/productos/{nombre}")
	public Producto buscarProductoPorNombre (@PathVariable (value="nombre") String nombre) {
		return servicioProducto.buscarProductoPorNombre(nombre);
	}
	
}
