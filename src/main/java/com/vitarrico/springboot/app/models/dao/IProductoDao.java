package com.vitarrico.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vitarrico.springboot.app.models.entity.Producto;

public interface IProductoDao  extends CrudRepository<Producto, Long>{


	public List<Producto> findByNombreContainingIgnoreCase(String term);
}
