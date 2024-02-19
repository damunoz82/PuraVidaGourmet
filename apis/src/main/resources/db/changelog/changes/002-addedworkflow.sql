CREATE TABLE public.workflow (
    id bigint NOT NULL,
    nombre character varying(255)
);

ALTER TABLE public.workflow OWNER TO puravida;

CREATE SEQUENCE public.workflow_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.workflow_id_seq OWNER TO puravida;

ALTER SEQUENCE public.workflow_id_seq OWNED BY public.workflow.id;

ALTER TABLE ONLY public.workflow ALTER COLUMN id SET DEFAULT nextval('public.workflow_id_seq'::regclass);

SELECT pg_catalog.setval('public.workflow_id_seq', 1, false);

ALTER TABLE ONLY public.workflow
    ADD CONSTRAINT workflow_pkey PRIMARY KEY (id);