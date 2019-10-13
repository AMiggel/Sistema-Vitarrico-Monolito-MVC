package com.vitarrico.springboot.app.models.service.productos.excepcion;

public class ExcepcionInventario extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public ExcepcionInventario(String mensaje) {
		super(mensaje);
	}
}
