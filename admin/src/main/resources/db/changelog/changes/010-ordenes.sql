--liquibase formatted sql
--changeset Daniel munoz:dmunoz.hon@gmail.com splitStatements:true endDelimite

ALTER TABLE public.item_menu ADD dep_responsable int8 DEFAULT 1 NOT NULL;
ALTER TABLE public.item_menu ADD CONSTRAINT item_menu_departamento_fk FOREIGN KEY (dep_responsable) REFERENCES public.departamento(id);

CREATE TABLE public.ordenes (
	id int8 GENERATED ALWAYS AS IDENTITY NOT NULL,
	mesa_id int8 NOT NULL,
	mesero_id int8 NOT NULL,
	fecha_creacion timestamp NOT NULL,
	estado varchar NOT NULL,
	total_neto float8 DEFAULT 0 NOT NULL,
	impuestos float8 DEFAULT 0 NOT NULL,
	total float8 DEFAULT 0 NOT NULL,
	fecha_finalizacion timestamp NULL,
	CONSTRAINT ordenes_pk PRIMARY KEY (id)
);

ALTER TABLE public.ordenes ALTER COLUMN estado TYPE int8 USING estado::int8;
ALTER TABLE public.ordenes ALTER COLUMN mesero_id TYPE varchar USING mesero_id::varchar;
ALTER TABLE public.ordenes ADD CONSTRAINT ordenes_mesas_restaurante_fk FOREIGN KEY (mesa_id) REFERENCES public.mesas_restaurante(id);
ALTER TABLE public.ordenes ADD CONSTRAINT ordenes_usuario_fk FOREIGN KEY (mesero_id) REFERENCES public.usuario(email);


CREATE TABLE public.detalle_orden (
	detalle_id int8 GENERATED ALWAYS AS IDENTITY NOT NULL,
	orden_id int8 NOT NULL,
	item_id int8 NOT NULL,
	cantidad int8 DEFAULT 1 NOT NULL,
	estado varchar NOT NULL,
	CONSTRAINT detalle_orden_pk PRIMARY KEY (detalle_id),
	CONSTRAINT detalle_orden_ordenes_fk FOREIGN KEY (detalle_id) REFERENCES public.ordenes(id),
	CONSTRAINT detalle_orden_item_menu_fk FOREIGN KEY (item_id) REFERENCES public.item_menu(item_menu_id)
);

ALTER TABLE public.detalle_orden DROP CONSTRAINT detalle_orden_ordenes_fk;
ALTER TABLE public.detalle_orden ADD CONSTRAINT detalle_orden_ordenes_fk FOREIGN KEY (orden_id) REFERENCES public.ordenes(id);

