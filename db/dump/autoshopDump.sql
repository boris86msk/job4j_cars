--
-- PostgreSQL database dump
--

-- Dumped from database version 14.5
-- Dumped by pg_dump version 14.5

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: auto_post; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.auto_post (
    id integer NOT NULL,
    description character varying NOT NULL,
    created timestamp without time zone NOT NULL,
    auto_user_id integer NOT NULL,
    car_id integer NOT NULL,
    file_id integer,
    price integer
);


ALTER TABLE public.auto_post OWNER TO postgres;

--
-- Name: auto_post_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.auto_post_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auto_post_id_seq OWNER TO postgres;

--
-- Name: auto_post_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.auto_post_id_seq OWNED BY public.auto_post.id;


--
-- Name: auto_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.auto_user (
    id integer NOT NULL,
    login character varying NOT NULL,
    password character varying NOT NULL
);


ALTER TABLE public.auto_user OWNER TO postgres;

--
-- Name: auto_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.auto_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auto_user_id_seq OWNER TO postgres;

--
-- Name: auto_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.auto_user_id_seq OWNED BY public.auto_user.id;


--
-- Name: body_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.body_type (
    id integer NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE public.body_type OWNER TO postgres;

--
-- Name: body_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.body_type_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.body_type_id_seq OWNER TO postgres;

--
-- Name: body_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.body_type_id_seq OWNED BY public.body_type.id;


--
-- Name: car; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.car (
    id integer NOT NULL,
    brand character varying NOT NULL,
    model character varying NOT NULL,
    body_id integer NOT NULL,
    tm character varying,
    volume character varying,
    power character varying,
    drive character varying,
    age integer NOT NULL,
    mileage integer NOT NULL,
    fuel character varying NOT NULL,
    color character varying
);


ALTER TABLE public.car OWNER TO postgres;

--
-- Name: car_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.car_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.car_id_seq OWNER TO postgres;

--
-- Name: car_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.car_id_seq OWNED BY public.car.id;


--
-- Name: databasechangelog; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20),
    contexts character varying(255),
    labels character varying(255),
    deployment_id character varying(10)
);


ALTER TABLE public.databasechangelog OWNER TO postgres;

--
-- Name: databasechangeloglock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);


ALTER TABLE public.databasechangeloglock OWNER TO postgres;

--
-- Name: files; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.files (
    id integer NOT NULL,
    path character varying
);


ALTER TABLE public.files OWNER TO postgres;

--
-- Name: files_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.files_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.files_id_seq OWNER TO postgres;

--
-- Name: files_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.files_id_seq OWNED BY public.files.id;


--
-- Name: history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.history (
    id integer NOT NULL,
    startat timestamp without time zone,
    endat timestamp without time zone,
    owner_id integer
);


ALTER TABLE public.history OWNER TO postgres;

--
-- Name: history_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.history_id_seq OWNER TO postgres;

--
-- Name: history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.history_id_seq OWNED BY public.history.id;


--
-- Name: history_owners; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.history_owners (
    id integer NOT NULL,
    car_id integer NOT NULL,
    owner_id integer NOT NULL
);


ALTER TABLE public.history_owners OWNER TO postgres;

--
-- Name: history_owners_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.history_owners_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.history_owners_id_seq OWNER TO postgres;

--
-- Name: history_owners_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.history_owners_id_seq OWNED BY public.history_owners.id;


--
-- Name: owners; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.owners (
    id integer NOT NULL,
    name character varying NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.owners OWNER TO postgres;

--
-- Name: owners_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.owners_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.owners_id_seq OWNER TO postgres;

--
-- Name: owners_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.owners_id_seq OWNED BY public.owners.id;


--
-- Name: participates; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.participates (
    id integer NOT NULL,
    user_id integer NOT NULL,
    post_id integer NOT NULL
);


ALTER TABLE public.participates OWNER TO postgres;

--
-- Name: participates_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.participates_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.participates_id_seq OWNER TO postgres;

--
-- Name: participates_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.participates_id_seq OWNED BY public.participates.id;


--
-- Name: price_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.price_history (
    id integer NOT NULL,
    created timestamp without time zone DEFAULT now(),
    post_id integer,
    price bigint NOT NULL
);


ALTER TABLE public.price_history OWNER TO postgres;

--
-- Name: price_history_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.price_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.price_history_id_seq OWNER TO postgres;

--
-- Name: price_history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.price_history_id_seq OWNED BY public.price_history.id;


--
-- Name: auto_post id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auto_post ALTER COLUMN id SET DEFAULT nextval('public.auto_post_id_seq'::regclass);


--
-- Name: auto_user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auto_user ALTER COLUMN id SET DEFAULT nextval('public.auto_user_id_seq'::regclass);


--
-- Name: body_type id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.body_type ALTER COLUMN id SET DEFAULT nextval('public.body_type_id_seq'::regclass);


--
-- Name: car id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.car ALTER COLUMN id SET DEFAULT nextval('public.car_id_seq'::regclass);


--
-- Name: files id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.files ALTER COLUMN id SET DEFAULT nextval('public.files_id_seq'::regclass);


--
-- Name: history id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.history ALTER COLUMN id SET DEFAULT nextval('public.history_id_seq'::regclass);


--
-- Name: history_owners id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.history_owners ALTER COLUMN id SET DEFAULT nextval('public.history_owners_id_seq'::regclass);


--
-- Name: owners id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.owners ALTER COLUMN id SET DEFAULT nextval('public.owners_id_seq'::regclass);


--
-- Name: participates id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participates ALTER COLUMN id SET DEFAULT nextval('public.participates_id_seq'::regclass);


--
-- Name: price_history id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.price_history ALTER COLUMN id SET DEFAULT nextval('public.price_history_id_seq'::regclass);


--
-- Data for Name: auto_post; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.auto_post (id, description, created, auto_user_id, car_id, file_id, price) FROM stdin;
2	танк	2024-02-11 22:27:00	1	2	2	450000
1	Отличное состояние	2024-02-11 22:16:00	2	1	1	1000000
3	В хорошем состоянии	2024-02-11 22:35:00	3	3	3	1200000
4	моя ласточка	2024-02-12 21:49:00	3	4	4	900000
5	Японская сборка, один хозяин.	2024-02-13 11:10:00	4	5	5	550000
6	Прекрасное состояние	2024-02-13 20:48:00	5	6	6	400000
7	Я собственник, владею автомобилем почти год. Очень надежная и редкая комплектация с двигателем 2.0 EcoBlue и 8 ступенчатым автоматом. Такой двигатель Ford ставит на коммерческий транспорт. Провел большое ТО не на словах, а на деле: заменил ГРМ, колодки, абсолютно все фильтра, старые запчасти и заказ наряд отдам. 2 комплекта новой резины.	2024-02-15 23:27:00	6	7	7	990000
\.


--
-- Data for Name: auto_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.auto_user (id, login, password) FROM stdin;
1	bobm@mail.ru	1111
2	SimenSemenych@mail.ru	1111
3	ViktorPetrovych@mail.ru	1111
4	PetrPetrovihc@mail.ru	1111
5	julie@mail.ru	1111
6	Andrey@mail.ru	1111
\.


--
-- Data for Name: body_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.body_type (id, name) FROM stdin;
1	Сидан
2	Хечбек
3	Универсал
4	Кроссовер
5	Внедорожник
6	Минивэн
7	Пикап
8	Купе
9	Кабриолет
10	Лимузин
\.


--
-- Data for Name: car; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.car (id, brand, model, body_id, tm, volume, power, drive, age, mileage, fuel, color) FROM stdin;
4	Lada	Kalina	2	МКПП	1.6	105	FWD	2018	150000	Бензин	Оранж
2	Лада	Нива	3	МКПП	1.6	86	4WD	2012	160000	Бензин	Космос
7	Ford	Focus 3	3	РКПП	2	118	FWD	2018	99000	Бензин	Огонь
1	Лада	Веста	3	МКПП	1.8	105	FWD	2019	90000	Бензин	Серый
6	Citroen	C3 Picasso	9	РКПП	1.4	95	FWD	2013	202000	Бензин	Рыжий
5	Toyota	Corolla	1	РКПП	1.6	115	FWD	2010	215000	Бензин	Кофе с молоком
3	Kia	Sportreg	4	АКПП	2.0	145	4WD	2012	190000	Бензин	Черный
\.


--
-- Data for Name: databasechangelog; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) FROM stdin;
raw	includeAll	db/scripts/001_ddl_create_table_body_type.sql	2024-02-18 22:54:38.982237	1	EXECUTED	8:8bcd28ee91b4dd622b512b58ab95b910	sql		\N	4.15.0	\N	\N	8286078785
raw	includeAll	db/scripts/002_dml_insert_body_type.sql	2024-02-18 22:54:39.007988	2	EXECUTED	8:c654af359ba8a489f4a5d4772ed721a4	sql		\N	4.15.0	\N	\N	8286078785
raw	includeAll	db/scripts/003_ddl_create_table_auto_user.sql	2024-02-18 22:54:39.040396	3	EXECUTED	8:e93b0a8d0d972d6fc13db8a302ad266a	sql		\N	4.15.0	\N	\N	8286078785
raw	includeAll	db/scripts/004_ddl_create_table_car.sql	2024-02-18 22:54:39.069763	4	EXECUTED	8:c9737e70d519379de69430dc30f7e9a5	sql		\N	4.15.0	\N	\N	8286078785
raw	includeAll	db/scripts/005_ddl_create_table_auto_post.sql	2024-02-18 22:54:39.098474	5	EXECUTED	8:c863620c44226c380558a2264d5ad78b	sql		\N	4.15.0	\N	\N	8286078785
raw	includeAll	db/scripts/006_ddl_create_price_history.sql	2024-02-18 22:54:39.122896	6	EXECUTED	8:21b383a65ab6e6837468433f968c5612	sql		\N	4.15.0	\N	\N	8286078785
raw	includeAll	db/scripts/007_ddl_create_table_participates.sql	2024-02-18 22:54:39.147348	7	EXECUTED	8:ab46cd371ea5ba9ca3dab912b1fcd34d	sql		\N	4.15.0	\N	\N	8286078785
raw	includeAll	db/scripts/008_ddl_create_table_owners.sql	2024-02-18 22:54:39.190345	8	EXECUTED	8:234a06f8d59c7daf6a792c52d1ff79da	sql		\N	4.15.0	\N	\N	8286078785
raw	includeAll	db/scripts/009_ddl_create_table_history.sql	2024-02-18 22:54:39.222458	9	EXECUTED	8:5705d5293e3f4dce9c02559a9ea1cd62	sql		\N	4.15.0	\N	\N	8286078785
raw	includeAll	db/scripts/010_ddl_create_history_owners.sql	2024-02-18 22:54:39.274725	10	EXECUTED	8:2a70d3fc0f80e936105b67165ca070ad	sql		\N	4.15.0	\N	\N	8286078785
raw	includeAll	db/scripts/011_ddl_create_table_files.sql	2024-02-18 22:54:39.30249	11	EXECUTED	8:309b1ac38b3c37318a4b8a844eff9e5e	sql		\N	4.15.0	\N	\N	8286078785
raw	includeAll	db/scripts/012_ddl_update_table_post.sql	2024-02-18 22:54:39.32928	12	EXECUTED	8:3bf5ca03d32eb2d00818b1c13ec7a05e	sql		\N	4.15.0	\N	\N	8286078785
raw	includeAll	db/scripts/013_ddl_update_participates.sql	2024-02-18 22:54:39.348501	13	EXECUTED	8:1ec77d858caa1b7207bccc9b4d3f208f	sql		\N	4.15.0	\N	\N	8286078785
raw	includeAll	db/scripts/014_ddl_update_price_history.sql	2024-02-18 22:54:39.367041	14	EXECUTED	8:da5ae3f4154804ad8d9e9a7e4deceeab	sql		\N	4.15.0	\N	\N	8286078785
1708265063809-1	boris (generated)	db/dbchangelog.xml	2024-02-18 22:54:39.495662	15	EXECUTED	8:bc533adf3730325d6bc116f26f3a9521	loadData tableName=auto_user		\N	4.15.0	\N	\N	8286078785
1708265275536-1	boris (generated)	db/dbchangelog.xml	2024-02-18 22:59:52.11129	16	EXECUTED	8:8343f104fb963ff72d0051fdab936ed5	loadData tableName=car		\N	4.15.0	\N	\N	8286391783
1708265334598-1	boris (generated)	db/dbchangelog.xml	2024-02-18 23:00:48.748443	17	EXECUTED	8:d9d82231399c9278a0df974f108c0819	loadData tableName=files		\N	4.15.0	\N	\N	8286448496
1708265007030-1	boris (generated)	db/dbchangelog.xml	2024-02-18 23:13:16.69276	18	EXECUTED	8:73a855574d4d71b122f2c3c50c004ad5	loadData tableName=auto_post		\N	4.15.0	\N	\N	8287196383
1708265636281-1	boris (generated)	db/dbchangelog.xml	2024-02-18 23:13:54.368015	19	EXECUTED	8:3b4114be1e9007a70b9666dac387c4da	loadData tableName=participates		\N	4.15.0	\N	\N	8287234174
1708265681067-1	boris (generated)	db/dbchangelog.xml	2024-02-18 23:14:38.174075	20	EXECUTED	8:98ccf47804d030eb0efded359ecea96e	loadData tableName=price_history		\N	4.15.0	\N	\N	8287277913
\.


--
-- Data for Name: databasechangeloglock; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.databasechangeloglock (id, locked, lockgranted, lockedby) FROM stdin;
1	f	\N	\N
\.


--
-- Data for Name: files; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.files (id, path) FROM stdin;
1	src/main/resources/static/img\\76b1c513-160e-4137-b84b-1ed0f028593bUK9.jpg
2	src/main/resources/static/img\\d504fa8f-c714-442d-8283-614a54de30d1777.jpg
3	src/main/resources/static/img\\66bdd0a9-42df-4c9c-ae68-a5a105a2e561546.jpg
4	src/main/resources/static/img\\166057b1-d70b-4d94-9dbf-099e6ce073c1lada_kalina_khetchbek_8.jpg
5	src/main/resources/static/img\\176134aa-683e-45fd-b9af-e3cabc26273cRJP1.jpg
6	src/main/resources/static/img\\1484df9a-9108-4c62-b36e-c633ee359beac2.jpg
7	src/main/resources/static/img\\bd64d495-1fb2-4465-b94c-5de4125b03b6df.jpg
\.


--
-- Data for Name: history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.history (id, startat, endat, owner_id) FROM stdin;
\.


--
-- Data for Name: history_owners; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.history_owners (id, car_id, owner_id) FROM stdin;
\.


--
-- Data for Name: owners; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.owners (id, name, user_id) FROM stdin;
\.


--
-- Data for Name: participates; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.participates (id, user_id, post_id) FROM stdin;
1	1	3
2	1	1
3	4	4
4	1	5
5	1	6
\.


--
-- Data for Name: price_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.price_history (id, created, post_id, price) FROM stdin;
1	2024-02-15 23:27:00	7	990000
2	2024-02-11 22:16:00	1	1000000
3	2024-02-11 22:27:00	2	450000
4	2024-02-11 22:35:00	3	1200000
5	2024-02-12 21:49:00	4	900000
6	2024-02-15 23:35:22	5	550000
7	2024-02-13 20:48:00	6	400000
\.


--
-- Name: auto_post_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.auto_post_id_seq', 1, false);


--
-- Name: auto_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.auto_user_id_seq', 1, false);


--
-- Name: body_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.body_type_id_seq', 10, true);


--
-- Name: car_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.car_id_seq', 1, false);


--
-- Name: files_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.files_id_seq', 1, false);


--
-- Name: history_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.history_id_seq', 1, false);


--
-- Name: history_owners_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.history_owners_id_seq', 1, false);


--
-- Name: owners_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.owners_id_seq', 1, false);


--
-- Name: participates_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.participates_id_seq', 1, false);


--
-- Name: price_history_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.price_history_id_seq', 1, false);


--
-- Name: auto_post auto_post_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auto_post
    ADD CONSTRAINT auto_post_pkey PRIMARY KEY (id);


--
-- Name: auto_user auto_user_login_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auto_user
    ADD CONSTRAINT auto_user_login_key UNIQUE (login);


--
-- Name: auto_user auto_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auto_user
    ADD CONSTRAINT auto_user_pkey PRIMARY KEY (id);


--
-- Name: body_type body_type_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.body_type
    ADD CONSTRAINT body_type_name_key UNIQUE (name);


--
-- Name: body_type body_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.body_type
    ADD CONSTRAINT body_type_pkey PRIMARY KEY (id);


--
-- Name: car car_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.car
    ADD CONSTRAINT car_pkey PRIMARY KEY (id);


--
-- Name: databasechangeloglock databasechangeloglock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);


--
-- Name: files files_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.files
    ADD CONSTRAINT files_pkey PRIMARY KEY (id);


--
-- Name: history_owners history_owners_car_id_owner_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.history_owners
    ADD CONSTRAINT history_owners_car_id_owner_id_key UNIQUE (car_id, owner_id);


--
-- Name: history_owners history_owners_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.history_owners
    ADD CONSTRAINT history_owners_pkey PRIMARY KEY (id);


--
-- Name: history history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.history
    ADD CONSTRAINT history_pkey PRIMARY KEY (id);


--
-- Name: owners owners_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.owners
    ADD CONSTRAINT owners_name_key UNIQUE (name);


--
-- Name: owners owners_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.owners
    ADD CONSTRAINT owners_pkey PRIMARY KEY (id);


--
-- Name: participates participates_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participates
    ADD CONSTRAINT participates_pkey PRIMARY KEY (id);


--
-- Name: price_history price_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.price_history
    ADD CONSTRAINT price_history_pkey PRIMARY KEY (id);


--
-- Name: unique_participates_user_post; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX unique_participates_user_post ON public.participates USING btree (user_id, post_id);


--
-- Name: auto_post auto_post_auto_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auto_post
    ADD CONSTRAINT auto_post_auto_user_id_fkey FOREIGN KEY (auto_user_id) REFERENCES public.auto_user(id);


--
-- Name: auto_post auto_post_car_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auto_post
    ADD CONSTRAINT auto_post_car_id_fkey FOREIGN KEY (car_id) REFERENCES public.car(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: auto_post auto_post_file_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auto_post
    ADD CONSTRAINT auto_post_file_id_fkey FOREIGN KEY (file_id) REFERENCES public.files(id);


--
-- Name: car car_body_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.car
    ADD CONSTRAINT car_body_id_fkey FOREIGN KEY (body_id) REFERENCES public.body_type(id);


--
-- Name: history history_owner_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.history
    ADD CONSTRAINT history_owner_id_fkey FOREIGN KEY (owner_id) REFERENCES public.owners(id);


--
-- Name: history_owners history_owners_car_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.history_owners
    ADD CONSTRAINT history_owners_car_id_fkey FOREIGN KEY (car_id) REFERENCES public.car(id);


--
-- Name: history_owners history_owners_owner_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.history_owners
    ADD CONSTRAINT history_owners_owner_id_fkey FOREIGN KEY (owner_id) REFERENCES public.owners(id);


--
-- Name: owners owners_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.owners
    ADD CONSTRAINT owners_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.auto_user(id);


--
-- Name: participates participates_post_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participates
    ADD CONSTRAINT participates_post_id_fkey FOREIGN KEY (post_id) REFERENCES public.auto_post(id);


--
-- Name: participates participates_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participates
    ADD CONSTRAINT participates_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.auto_user(id);


--
-- Name: price_history price_history_post_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.price_history
    ADD CONSTRAINT price_history_post_id_fkey FOREIGN KEY (post_id) REFERENCES public.auto_post(id);


--
-- PostgreSQL database dump complete
--

