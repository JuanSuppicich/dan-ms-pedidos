INSERT INTO MS_PEDIDOS.ESTADO_PEDIDO (estado) VALUES 
    ('Aceptado'),
    ('Pendiente'),
    ('Enviado');

INSERT INTO MS_PEDIDOS.OBRA (id_obra, descripcion) VALUES
    (1, 'desc1'),
    (2, 'desc2'),
    (3, 'desc3');

INSERT INTO MS_PEDIDOS.PRODUCTO (id_producto, descripcion, precio) VALUES
    (1, 'desc1', 10.00),
    (2, 'desc2', 20.00),
    (3, 'desc3', 30.00);

INSERT INTO MS_PEDIDOS.PEDIDO (fecha, id_estado_pedido, id_obra) VALUES
    ('2021-05-08T23:12:52.810Z', 1, 1),
    ('2021-05-08T23:12:52.810Z', 1, 2),
    ('2021-05-08T23:12:52.810Z', 3, 3);

INSERT INTO MS_PEDIDOS.DETALLE_PEDIDO (cantidad, precio, id_producto, id_pedido) VALUES 
    (1, 10.00, 1, 1),
    (1, 20.00, 2, 1),
    (1, 10.00, 1, 2),
    (1, 20.00, 2, 2),
    (1, 30.00, 3, 2),
    (1, 10.00, 1, 3);

