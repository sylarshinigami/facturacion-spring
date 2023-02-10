INSERT INTO clientes(nombre, apellido, email, create_at, foto) values('Andres', 'Guzman', 'guzman@mail.com', '2017-08-28', 'default.png');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) values('Ronald', 'Aguilar', 'ronald@mail.com', '2023-01-23', 'default.png');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) values('Ronald', 'Aguilar', 'ronald@mail.com', '2023-01-23', 'default.png');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) values('Ronald', 'Aguilar', 'ronald@mail.com', '2023-01-23', 'default.png');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) values('Ronald', 'Aguilar', 'ronald@mail.com', '2023-01-23', 'default.png');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) values('Ronald', 'Aguilar', 'ronald@mail.com', '2023-01-23', 'default.png');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) values('Ronald', 'Aguilar', 'ronald@mail.com', '2023-01-23', 'default.png');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) values('Ronald', 'Aguilar', 'ronald@mail.com', '2023-01-23', 'default.png');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) values('Ronald', 'Aguilar', 'ronald@mail.com', '2023-01-23', 'default.png');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) values('Ronald', 'Aguilar', 'ronald@mail.com', '2023-01-23', 'default.png');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) values('Ronald', 'Aguilar', 'ronald@mail.com', '2023-01-23', 'default.png');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) values('Ronald', 'Aguilar', 'ronald@mail.com', '2023-01-23', 'default.png');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) values('Ronald', 'Aguilar', 'ronald@mail.com', '2023-01-23', 'default.png');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) values('Ronald', 'Aguilar', 'ronald@mail.com', '2023-01-23', 'default.png');
INSERT INTO clientes(nombre, apellido, email, create_at, foto) values('Ronald', 'Aguilar', 'ronald@mail.com', '2023-01-23', 'default.png');


INSERT INTO productos(nombre, precio, create_at) values('Panasonic Pantalla LCD', 300, NOW());
INSERT INTO productos(nombre, precio, create_at) values('Panasonic Audifonos', 400, NOW());
INSERT INTO productos(nombre, precio, create_at) values('Sony Pantalla Mini LED', 500, NOW());
INSERT INTO productos(nombre, precio, create_at) values('Samsung s25', 600, NOW());
INSERT INTO productos(nombre, precio, create_at) values('Cigarros', 600, NOW());
INSERT INTO productos(nombre, precio, create_at) values('Tomates', 600, NOW());


INSERT INTO facturas(descripcion, observacion, cliente_id, create_at) VALUES('Factura equipos de oficina','', 1, NOW());

INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1,1,1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(2,1,4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1,1,5);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1,1,1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(5,1,1);

INSERT INTO facturas(descripcion, observacion, cliente_id, create_at) VALUES('Factura bicicleta','Alguna nota importante', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(3,2,6);

INSERT INTO users(username, password, enabled) VALUES('ronald', '$2a$10$j/TOFBrlPCZDMrVlce/WQ.O2aPJJ6xyJPFS.T.trtAjxD.lNpCeJe', 1 );
INSERT INTO users(username, password, enabled) VALUES('arqui', '$2a$10$bk9IO282PiMJmRyIXsd1Z.ifhIrBUvq7xnTUu/1mBJWE3Wfvcda0K', 1 );

INSERT INTO authorities(user_id, authority) VALUES (1, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES (2, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES (2, 'ROLE_ADMIN');

