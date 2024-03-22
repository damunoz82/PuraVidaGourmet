--liquibase formatted sql
--changeset Daniel munoz:dmunoz.hon@gmail.com splitStatements:true endDelimite


CREATE TABLE public.mesas_restaurante (
	id int8 GENERATED ALWAYS AS IDENTITY NOT NULL,
	nombre varchar NOT NULL,
	capacidad int NOT NULL,
	CONSTRAINT mesas_restaurante_pk PRIMARY KEY (id)
);