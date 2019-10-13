package com.vitarrico.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vitarrico.springboot.app.models.entity.Producto;


public interface IProductoDao extends CrudRepository<Producto, Long>{

	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> findByNombre(String term);
	
	public List<Producto> findByNombreLikeIgnoreCase(String term);
	
	
	public List<Producto> findAllByOrderByFechaVencimientoAsc();
	
	@Query("SELECT p FROM Producto p WHERE p.nombre = :nombre" )
	Producto findByNombres(@Param("nombre") String nombre);
}
