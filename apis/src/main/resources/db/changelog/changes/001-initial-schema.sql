--
-- PostgreSQL database dump
--

-- Dumped from database version 14.9 (Ubuntu 14.9-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 15.5 (Ubuntu 15.5-0ubuntu0.23.10.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: puravida
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO puravida;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: categoria-receta; Type: TABLE; Schema: public; Owner: puravida
--

CREATE TABLE public."categoria-receta" (
    id bigint NOT NULL,
    nombre character varying(255)
);


ALTER TABLE public."categoria-receta" OWNER TO puravida;

--
-- Name: categoria-receta_id_seq; Type: SEQUENCE; Schema: public; Owner: puravida
--

CREATE SEQUENCE public."categoria-receta_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."categoria-receta_id_seq" OWNER TO puravida;

--
-- Name: categoria-receta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: puravida
--

ALTER SEQUENCE public."categoria-receta_id_seq" OWNED BY public."categoria-receta".id;


--
-- Name: departamento; Type: TABLE; Schema: public; Owner: puravida
--

CREATE TABLE public.departamento (
    id bigint NOT NULL,
    nombre character varying(255),
    responsable_id bigint
);


ALTER TABLE public.departamento OWNER TO puravida;

--
-- Name: departamento_id_seq; Type: SEQUENCE; Schema: public; Owner: puravida
--

CREATE SEQUENCE public.departamento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.departamento_id_seq OWNER TO puravida;

--
-- Name: departamento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: puravida
--

ALTER SEQUENCE public.departamento_id_seq OWNED BY public.departamento.id;


--
-- Name: ingrediente; Type: TABLE; Schema: public; Owner: puravida
--

CREATE TABLE public.ingrediente (
    ingrediente_id bigint NOT NULL,
    cantidad bigint NOT NULL,
    producto_id bigint
);


ALTER TABLE public.ingrediente OWNER TO puravida;

--
-- Name: ingrediente_ingrediente_id_seq; Type: SEQUENCE; Schema: public; Owner: puravida
--

CREATE SEQUENCE public.ingrediente_ingrediente_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ingrediente_ingrediente_id_seq OWNER TO puravida;

--
-- Name: ingrediente_ingrediente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: puravida
--

ALTER SEQUENCE public.ingrediente_ingrediente_id_seq OWNED BY public.ingrediente.ingrediente_id;


--
-- Name: inventario; Type: TABLE; Schema: public; Owner: puravida
--

CREATE TABLE public.inventario (
    id bigint NOT NULL,
    estado smallint,
    fecha bigint NOT NULL,
    departamento_id bigint,
    CONSTRAINT inventario_estado_check CHECK (((estado >= 0) AND (estado <= 3)))
);


ALTER TABLE public.inventario OWNER TO puravida;

--
-- Name: inventario_id_seq; Type: SEQUENCE; Schema: public; Owner: puravida
--

CREATE SEQUENCE public.inventario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.inventario_id_seq OWNER TO puravida;

--
-- Name: inventario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: puravida
--

ALTER SEQUENCE public.inventario_id_seq OWNED BY public.inventario.id;


--
-- Name: inventario_registro; Type: TABLE; Schema: public; Owner: puravida
--

CREATE TABLE public.inventario_registro (
    id bigint NOT NULL,
    cantidad_en_bodega bigint NOT NULL,
    valor bigint NOT NULL,
    producto_id bigint
);


ALTER TABLE public.inventario_registro OWNER TO puravida;

--
-- Name: inventario_registro_id_seq; Type: SEQUENCE; Schema: public; Owner: puravida
--

CREATE SEQUENCE public.inventario_registro_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.inventario_registro_id_seq OWNER TO puravida;

--
-- Name: inventario_registro_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: puravida
--

ALTER SEQUENCE public.inventario_registro_id_seq OWNED BY public.inventario_registro.id;


--
-- Name: inventario_registros; Type: TABLE; Schema: public; Owner: puravida
--

CREATE TABLE public.inventario_registros (
    inventario_id bigint NOT NULL,
    registros_id bigint NOT NULL
);


ALTER TABLE public.inventario_registros OWNER TO puravida;

--
-- Name: producto; Type: TABLE; Schema: public; Owner: puravida
--

CREATE TABLE public.producto (
    id bigint NOT NULL,
    cantidad_por_unidad bigint NOT NULL,
    coste_unitario real NOT NULL,
    formato_compra smallint,
    nombre character varying(255),
    porcentaje_merma real NOT NULL,
    precio_de_compra bigint NOT NULL,
    proveedor character varying(255),
    unidad_medida smallint,
    tipo_producto_id bigint,
    CONSTRAINT producto_formato_compra_check CHECK (((formato_compra >= 0) AND (formato_compra <= 3))),
    CONSTRAINT producto_unidad_medida_check CHECK (((unidad_medida >= 0) AND (unidad_medida <= 2)))
);


ALTER TABLE public.producto OWNER TO puravida;

--
-- Name: producto_id_seq; Type: SEQUENCE; Schema: public; Owner: puravida
--

CREATE SEQUENCE public.producto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.producto_id_seq OWNER TO puravida;

--
-- Name: producto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: puravida
--

ALTER SEQUENCE public.producto_id_seq OWNED BY public.producto.id;


--
-- Name: recetas; Type: TABLE; Schema: public; Owner: puravida
--

CREATE TABLE public.recetas (
    id bigint NOT NULL,
    alergenos character varying(255),
    costo_porcion double precision NOT NULL,
    costo_receta double precision NOT NULL,
    elaboracion character varying(255),
    equipo_necesario character varying(255),
    fecha_modificacion date,
    fecha_registro date,
    impuestos double precision NOT NULL,
    margen_ganancia double precision NOT NULL,
    nombre character varying(255),
    numero_porciones bigint NOT NULL,
    precio_de_venta double precision NOT NULL,
    tamanio_porcion bigint NOT NULL,
    temperatura_de_servido bigint NOT NULL,
    tiempo_coccion bigint NOT NULL,
    tiempo_preparacion bigint NOT NULL,
    categoria_receta_id bigint,
    usuario_modifica_id bigint,
    usuario_registra_id bigint
);


ALTER TABLE public.recetas OWNER TO puravida;

--
-- Name: recetas_id_seq; Type: SEQUENCE; Schema: public; Owner: puravida
--

CREATE SEQUENCE public.recetas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.recetas_id_seq OWNER TO puravida;

--
-- Name: recetas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: puravida
--

ALTER SEQUENCE public.recetas_id_seq OWNED BY public.recetas.id;


--
-- Name: recetas_ingredientes; Type: TABLE; Schema: public; Owner: puravida
--

CREATE TABLE public.recetas_ingredientes (
    receta_id bigint NOT NULL,
    ingredientes_ingrediente_id bigint NOT NULL
);


ALTER TABLE public.recetas_ingredientes OWNER TO puravida;

--
-- Name: tipo_producto; Type: TABLE; Schema: public; Owner: puravida
--

CREATE TABLE public.tipo_producto (
    id bigint NOT NULL,
    nombre character varying(255)
);


ALTER TABLE public.tipo_producto OWNER TO puravida;

--
-- Name: tipo_producto_id_seq; Type: SEQUENCE; Schema: public; Owner: puravida
--

CREATE SEQUENCE public.tipo_producto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tipo_producto_id_seq OWNER TO puravida;

--
-- Name: tipo_producto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: puravida
--

ALTER SEQUENCE public.tipo_producto_id_seq OWNED BY public.tipo_producto.id;


--
-- Name: usuario; Type: TABLE; Schema: public; Owner: puravida
--

CREATE TABLE public.usuario (
    id bigint NOT NULL,
    email character varying(255),
    enabled boolean NOT NULL,
    name character varying(255),
    password character varying(255),
    provider smallint,
    provider_id character varying(255),
    roles character varying(255),
    CONSTRAINT usuario_provider_check CHECK (((provider >= 0) AND (provider <= 1)))
);


ALTER TABLE public.usuario OWNER TO puravida;

--
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: puravida
--

CREATE SEQUENCE public.usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuario_id_seq OWNER TO puravida;

--
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: puravida
--

ALTER SEQUENCE public.usuario_id_seq OWNED BY public.usuario.id;


--
-- Name: categoria-receta id; Type: DEFAULT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public."categoria-receta" ALTER COLUMN id SET DEFAULT nextval('public."categoria-receta_id_seq"'::regclass);


--
-- Name: departamento id; Type: DEFAULT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.departamento ALTER COLUMN id SET DEFAULT nextval('public.departamento_id_seq'::regclass);


--
-- Name: ingrediente ingrediente_id; Type: DEFAULT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.ingrediente ALTER COLUMN ingrediente_id SET DEFAULT nextval('public.ingrediente_ingrediente_id_seq'::regclass);


--
-- Name: inventario id; Type: DEFAULT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.inventario ALTER COLUMN id SET DEFAULT nextval('public.inventario_id_seq'::regclass);


--
-- Name: inventario_registro id; Type: DEFAULT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.inventario_registro ALTER COLUMN id SET DEFAULT nextval('public.inventario_registro_id_seq'::regclass);


--
-- Name: producto id; Type: DEFAULT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.producto ALTER COLUMN id SET DEFAULT nextval('public.producto_id_seq'::regclass);


--
-- Name: recetas id; Type: DEFAULT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.recetas ALTER COLUMN id SET DEFAULT nextval('public.recetas_id_seq'::regclass);


--
-- Name: tipo_producto id; Type: DEFAULT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.tipo_producto ALTER COLUMN id SET DEFAULT nextval('public.tipo_producto_id_seq'::regclass);


--
-- Name: usuario id; Type: DEFAULT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id SET DEFAULT nextval('public.usuario_id_seq'::regclass);

--
-- Name: categoria-receta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: puravida
--

SELECT pg_catalog.setval('public."categoria-receta_id_seq"', 1, false);


--
-- Name: departamento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: puravida
--

SELECT pg_catalog.setval('public.departamento_id_seq', 1, false);


--
-- Name: ingrediente_ingrediente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: puravida
--

SELECT pg_catalog.setval('public.ingrediente_ingrediente_id_seq', 1, false);


--
-- Name: inventario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: puravida
--

SELECT pg_catalog.setval('public.inventario_id_seq', 1, false);


--
-- Name: inventario_registro_id_seq; Type: SEQUENCE SET; Schema: public; Owner: puravida
--

SELECT pg_catalog.setval('public.inventario_registro_id_seq', 1, false);


--
-- Name: producto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: puravida
--

SELECT pg_catalog.setval('public.producto_id_seq', 2, true);


--
-- Name: recetas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: puravida
--

SELECT pg_catalog.setval('public.recetas_id_seq', 1, false);


--
-- Name: tipo_producto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: puravida
--

SELECT pg_catalog.setval('public.tipo_producto_id_seq', 1, false);


--
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: puravida
--

SELECT pg_catalog.setval('public.usuario_id_seq', 1, true);


--
-- Name: categoria-receta categoria-receta_pkey; Type: CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public."categoria-receta"
    ADD CONSTRAINT "categoria-receta_pkey" PRIMARY KEY (id);


--
-- Name: departamento departamento_pkey; Type: CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.departamento
    ADD CONSTRAINT departamento_pkey PRIMARY KEY (id);


--
-- Name: ingrediente ingrediente_pkey; Type: CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.ingrediente
    ADD CONSTRAINT ingrediente_pkey PRIMARY KEY (ingrediente_id);


--
-- Name: inventario inventario_pkey; Type: CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.inventario
    ADD CONSTRAINT inventario_pkey PRIMARY KEY (id);


--
-- Name: inventario_registro inventario_registro_pkey; Type: CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.inventario_registro
    ADD CONSTRAINT inventario_registro_pkey PRIMARY KEY (id);


--
-- Name: producto producto_pkey; Type: CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.producto
    ADD CONSTRAINT producto_pkey PRIMARY KEY (id);


--
-- Name: recetas recetas_pkey; Type: CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.recetas
    ADD CONSTRAINT recetas_pkey PRIMARY KEY (id);


--
-- Name: tipo_producto tipo_producto_pkey; Type: CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.tipo_producto
    ADD CONSTRAINT tipo_producto_pkey PRIMARY KEY (id);


--
-- Name: inventario_registros uk_45tnnve7j6gh4upcn7ncwwqoa; Type: CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.inventario_registros
    ADD CONSTRAINT uk_45tnnve7j6gh4upcn7ncwwqoa UNIQUE (registros_id);


--
-- Name: usuario uk_5171l57faosmj8myawaucatdw; Type: CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT uk_5171l57faosmj8myawaucatdw UNIQUE (email);


--
-- Name: recetas uk_56pi1bdehk3p1yjmgp1espt22; Type: CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.recetas
    ADD CONSTRAINT uk_56pi1bdehk3p1yjmgp1espt22 UNIQUE (nombre);


--
-- Name: producto uk_9su14n91mtgcg5ehl658v4afx; Type: CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.producto
    ADD CONSTRAINT uk_9su14n91mtgcg5ehl658v4afx UNIQUE (nombre);


--
-- Name: departamento uk_ahgt7tvfi2u1vawme9yr6ixng; Type: CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.departamento
    ADD CONSTRAINT uk_ahgt7tvfi2u1vawme9yr6ixng UNIQUE (responsable_id);


--
-- Name: recetas uk_hfijor37psiv4xtuliw7x22h; Type: CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.recetas
    ADD CONSTRAINT uk_hfijor37psiv4xtuliw7x22h UNIQUE (categoria_receta_id);


--
-- Name: recetas_ingredientes uk_k9mx948kyn17ure4kfnsn48n0; Type: CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.recetas_ingredientes
    ADD CONSTRAINT uk_k9mx948kyn17ure4kfnsn48n0 UNIQUE (ingredientes_ingrediente_id);


--
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- Name: producto fk2em9dpm8qsg21wqf2v1bmshdw; Type: FK CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.producto
    ADD CONSTRAINT fk2em9dpm8qsg21wqf2v1bmshdw FOREIGN KEY (tipo_producto_id) REFERENCES public.tipo_producto(id);


--
-- Name: inventario_registros fk419ecps1q7okmbmcjhvftv4k0; Type: FK CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.inventario_registros
    ADD CONSTRAINT fk419ecps1q7okmbmcjhvftv4k0 FOREIGN KEY (inventario_id) REFERENCES public.inventario(id);


--
-- Name: recetas fk801ugfajtpshnd3mobp38x41n; Type: FK CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.recetas
    ADD CONSTRAINT fk801ugfajtpshnd3mobp38x41n FOREIGN KEY (usuario_registra_id) REFERENCES public.usuario(id);


--
-- Name: inventario_registro fkcijre9mrcoan7k2wvo0he50vq; Type: FK CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.inventario_registro
    ADD CONSTRAINT fkcijre9mrcoan7k2wvo0he50vq FOREIGN KEY (producto_id) REFERENCES public.producto(id);


--
-- Name: recetas fkgksx9uxi0etsh1926beylqego; Type: FK CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.recetas
    ADD CONSTRAINT fkgksx9uxi0etsh1926beylqego FOREIGN KEY (categoria_receta_id) REFERENCES public."categoria-receta"(id);


--
-- Name: inventario_registros fkjaq7dnnr1lcj5rdpgykgn0hk2; Type: FK CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.inventario_registros
    ADD CONSTRAINT fkjaq7dnnr1lcj5rdpgykgn0hk2 FOREIGN KEY (registros_id) REFERENCES public.inventario_registro(id);


--
-- Name: recetas_ingredientes fkmrwgn96o573370e877sj57gad; Type: FK CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.recetas_ingredientes
    ADD CONSTRAINT fkmrwgn96o573370e877sj57gad FOREIGN KEY (ingredientes_ingrediente_id) REFERENCES public.ingrediente(ingrediente_id);


--
-- Name: departamento fkneca3xqc5vqgt83njcrqgp6ss; Type: FK CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.departamento
    ADD CONSTRAINT fkneca3xqc5vqgt83njcrqgp6ss FOREIGN KEY (responsable_id) REFERENCES public.usuario(id);


--
-- Name: inventario fkp2h3r1rbufpbh4chanjui8my5; Type: FK CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.inventario
    ADD CONSTRAINT fkp2h3r1rbufpbh4chanjui8my5 FOREIGN KEY (departamento_id) REFERENCES public.departamento(id);


--
-- Name: ingrediente fkpk4nqcpjkuib2owyk6482lyki; Type: FK CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.ingrediente
    ADD CONSTRAINT fkpk4nqcpjkuib2owyk6482lyki FOREIGN KEY (producto_id) REFERENCES public.producto(id);


--
-- Name: recetas fks6odnohb9vwjr6l46yrm8klhl; Type: FK CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.recetas
    ADD CONSTRAINT fks6odnohb9vwjr6l46yrm8klhl FOREIGN KEY (usuario_modifica_id) REFERENCES public.usuario(id);


--
-- Name: recetas_ingredientes fkt1aceirr9btp3yigo2hn0i9s8; Type: FK CONSTRAINT; Schema: public; Owner: puravida
--

ALTER TABLE ONLY public.recetas_ingredientes
    ADD CONSTRAINT fkt1aceirr9btp3yigo2hn0i9s8 FOREIGN KEY (receta_id) REFERENCES public.recetas(id);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: puravida
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

