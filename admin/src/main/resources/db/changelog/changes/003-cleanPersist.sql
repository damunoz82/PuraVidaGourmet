--liquibase formatted sql
--changeset Daniel munoz:dmunoz.hon@gmail.com splitStatements:true endDelimite

ALTER TABLE public.departamento DROP CONSTRAINT IF exists uk_ahgt7tvfi2u1vawme9yr6ixng;