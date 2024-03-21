--liquibase formatted sql
--changeset Daniel munoz:dmunoz.hon@gmail.com splitStatements:true endDelimite

CREATE TABLE public.workflow (
    id bigint NOT NULL,
    nombre character varying(255)
);

CREATE SEQUENCE public.workflow_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.workflow_id_seq OWNED BY public.workflow.id;

ALTER TABLE ONLY public.workflow ALTER COLUMN id SET DEFAULT nextval('public.workflow_id_seq'::regclass);

SELECT pg_catalog.setval('public.workflow_id_seq', 1, false);

ALTER TABLE ONLY public.workflow
    ADD CONSTRAINT workflow_pkey PRIMARY KEY (id);

--rollback drop table public.workflow;
