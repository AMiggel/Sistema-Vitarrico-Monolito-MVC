/* Populate tables */
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Antonio', 'Marín', 'antonio@gmail.com', '2017-08-01');
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('John', 'Doe', 'john.doe@gmail.com', '2017-08-02');
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2017-08-03');
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Jane', 'Doe', 'jane.doe@gmail.com', '2017-08-04');
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2017-08-05');
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Erich', 'Gamma', 'erich.gamma@gmail.com', '2017-08-06');
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Richard', 'Helm', 'richard.helm@gmail.com', '2017-08-07');
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2017-08-08');
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('John', 'Vlissides', 'john.vlissides@gmail.com', '2017-08-09');
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('James', 'Gosling', 'james.gosling@gmail.com', '2017-08-010');
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Bruce', 'Lee', 'bruce.lee@gmail.com', '2017-08-11');
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Johnny', 'Doe', 'johnny.doe@gmail.com', '2017-08-12');
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('John', 'Roe', 'john.roe@gmail.com', '2017-08-13');
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Jane', 'Roe', 'jane.roe@gmail.com', '2017-08-14');
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Richard', 'Doe', 'richard.doe@gmail.com', '2017-08-15');

/* Populate tabla productos */
INSERT INTO productos (nombre, precio, fecha_creacion,fecha_vencimiento,tipo_producto,cantidad_disponible,cantidad_creada) VALUES('Granola Vitarrico 500 gr', 13000, NOW(),NOW(),'Granola',20,100);
INSERT INTO productos (nombre, precio, fecha_creacion,fecha_vencimiento,tipo_producto,cantidad_disponible,cantidad_creada) VALUES('Zumo de Uva 1L', 13000, NOW(),NOW(),'Zumos',20,100);
INSERT INTO productos (nombre, precio, fecha_creacion,fecha_vencimiento,tipo_producto,cantidad_disponible,cantidad_creada) VALUES('Hojuelas de Maiz 500 gr', 13000, NOW(),NOW(),'Cereales',20,100);
INSERT INTO productos (nombre, precio, fecha_creacion,fecha_vencimiento,tipo_producto,cantidad_disponible,cantidad_creada) VALUES('Avena 500 gr', 13000, NOW(),NOW(),'Avenas',20,100);
INSERT INTO productos (nombre, precio, fecha_creacion,fecha_vencimiento,tipo_producto,cantidad_disponible,cantidad_creada) VALUES('Galletas de caña', 13000, NOW(),NOW(),'Galletas',20,100);
/* Creamos algunas facturas */
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura creada ', null, 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(2, 1, 4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 5);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 3);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura creada hoy', 'Alguna nota importante!', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(3, 2, 5);