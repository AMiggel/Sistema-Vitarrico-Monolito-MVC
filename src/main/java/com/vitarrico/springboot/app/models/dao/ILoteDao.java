package com.vitarrico.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vitarrico.springboot.app.models.entity.Lote;


public interface ILoteDao extends CrudRepository<Lote, Long>{

	@Query("select p from Lote p where p.nombre like %?1%")
	public List<Lote> findByNombre(String term);
	
	public List<Lote> findByNombreLikeIgnoreCase(String term);
	
	
	public List<Lote> findAllByOrderByFechaVencimientoAsc();
	
	@Query("SELECT p FROM Lote p WHERE p.nombre = :nombre" )
	Lote findByNombres(@Param("nombre") String nombre);
}
