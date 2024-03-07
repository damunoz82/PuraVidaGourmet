--liquibase formatted sql
--changeset Daniel munoz:dmunoz.hon@gmail.com splitStatements:true endDelimite

ALTER TABLE public.recetas DROP CONSTRAINT IF exists uk_hfijor37psiv4xtuliw7x22h;
