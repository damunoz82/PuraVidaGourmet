--liquibase formatted sql
--changeset Daniel munoz:dmunoz.hon@gmail.com splitStatements:true endDelimite

drop procedure iniciarInventario(departamento_id int,
                                    responsable_id int,
                                    periodo_meta varchar(30),
                                    comentario varchar(250),
                                    estado int,
                                    inventario_id INOUT bigint
                                    ;

create or replace procedure iniciarInventario(departamento_id bigint,
   responsable_id bigint,
   periodo_meta varchar(30),
   comentario varchar(250),
   estado int,
   inventario_id INOUT bigint
   )
language plpgsql as '
declare
	rec record;
begin
	insert into inventario(fecha_creacion, comentario, departamento_id, responsable_id, periodo_meta, estado) values
		(CURRENT_TIMESTAMP, comentario, departamento_id, responsable_id, periodo_meta, estado) returning inventario.id into inventario_id;

	for rec in (select p.nombre, tp.nombre as nombre_categoria, tp.ubicacion, p.formato_compra, p.unidad_medida, p.cantidad_por_unidad, p.precio_de_compra  from producto p
        join tipo_producto tp on (p.tipo_producto_id  = tp.id) order by tp.nombre, p.nombre)
		loop
			insert into inventario_detalle (inventario_id, nombre_producto, categoria_producto, ubicacion_producto, formato_compra_producto,
			unidad_medida_producto, cantidad_unidad_producto, precio_compra_producto) values
			 (inventario_id, rec.nombre, rec.nombre_categoria, rec.ubicacion, rec.formato_compra, rec.unidad_medida, rec.cantidad_por_unidad,
			 rec.precio_de_compra);
		end loop;

end;
' ;