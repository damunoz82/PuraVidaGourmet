--liquibase formatted sql
--changeset Daniel munoz:dmunoz.hon@gmail.com splitStatements:true endDelimite

drop table inventario_registros;
drop table inventario_registro;
drop table inventario;

create table inventario (
	id INT GENERATED ALWAYS AS IDENTITY,
	fecha_creacion timestamp not NULL,
	fecha_modificacion timestamp,
	comentario varchar(250) not null default '',
	departamento_id int not null,
	responsable_id int not NULL,
	periodo_meta varchar(30) not null,
	estado int,
	primary key (id),
	constraint fk_inventario_responsable
		foreign key (responsable_id)
			references usuario(id),
	constraint fk_inventario_departamento
		foreign key (departamento_id)
			references departamento(id)
);

create table inventario_detalle (
	detalle_id INT GENERATED ALWAYS AS IDENTITY,
	inventario_id int not null,
	nombre_producto varchar(255) not null,
	categoria_producto varchar(255) not null,
	ubicacion_producto varchar(255) not null,
	formato_compra_producto int not null,
	unidad_medida_producto int not null,
	cantidad_unidad_producto int not null,
	precio_compra_producto int not null,
	cantidad_bodega int not null default 0,
	valor_en_bodega float not null default 0,
	primary key (detalle_id),
	constraint fk_inventario_detalle
		foreign key (inventario_id)
			references inventario(id)
);

alter table tipo_producto
	add column if not exists ubicacion varchar(50) not null default '';

alter table ingrediente
	add column if not exists receta_id int;

alter table ingrediente alter column receta_id set not null;

alter table ingrediente
	add constraint fk_receta_ingrediente
		foreign key (receta_id)
			references recetas(id);

drop table if exists recetas_ingredientes;

ALTER TABLE "categoria-receta"
  RENAME TO categoria_receta;

create or replace procedure iniciarInventario(departamento_id int,
   responsable_id int,
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

--rollback alter table tipo_producto drop column ubicacion;
--rollback drop procedure iniciarInventario(departamento_id int, responsable_id int, periodo_meta varchar(30), comentario varchar(250), estado int, inventario_id INOUT bigint);
--rollback drop table inventario_detalle;
--rollback drop table inventario;
--rollback ALTER TABLE categoria_receta RENAME TO "categoria-receta";
