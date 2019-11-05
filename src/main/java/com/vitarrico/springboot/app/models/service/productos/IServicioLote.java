package com.vitarrico.springboot.app.models.service.productos;

import java.util.List;

import com.vitarrico.springboot.app.models.entity.Lote;
import com.vitarrico.springboot.app.models.entity.Mail;


public interface IServicioLote {

	public List<Lote> listar();

	/**
	 * Permite crear un producto nuevo
	 * 
	 * @param usuario
	 * @return la entidad del producto
	 */
	public Lote crearLote(Lote producto);

	/**
	 * Permite buscar un producto ya creado por id
	 * 
	 * @param id
	 * @return la entidad del producto
	 */
	public Lote buscarLotePorId(Long id);

	public void borrarLote(Long id);

	public Lote modificarLote(Long id, Lote producto);

	public Lote modificarCantidadDisponible(Long id, Lote producto);
	
	public Lote buscarProductoPorNombre(String nombre);
	
	public Mail actualizarEmail(Mail email,Long id);
	
	public Mail buscarMailPorId(Long id);
}
