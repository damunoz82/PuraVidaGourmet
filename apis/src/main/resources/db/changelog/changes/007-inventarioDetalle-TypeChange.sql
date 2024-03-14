--liquibase formatted sql
--changeset Daniel munoz:dmunoz.hon@gmail.com splitStatements:true endDelimite

alter table inventario_detalle alter column cantidad_bodega type float8;

alter table inventario add column if not exists total_valor_bodega float8 not null default 0;