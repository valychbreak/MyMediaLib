-- SEQUENCE: public.user_role_id_seq

-- DROP SEQUENCE public.user_role_id_seq;

CREATE SEQUENCE public.user_role_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;


-- SEQUENCE: public.users_id_seq

-- DROP SEQUENCE public.users_id_seq;

CREATE SEQUENCE public.users_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;


-- Table: public.user_role

-- DROP TABLE public.user_role;

CREATE TABLE public.user_role
(
    id bigint NOT NULL DEFAULT nextval('user_role_id_seq'::regclass),
    role character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT user_role_pkey PRIMARY KEY (id)
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;


-- Table: public.users

-- DROP TABLE public.users;

CREATE TABLE public.users
(
    id bigint NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    username character varying(255) COLLATE pg_catalog."default" NOT NULL,
    user_role_id bigint NOT NULL,
    root_user_media_collection bigint,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email)
    ,
    CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username)
    ,
    CONSTRAINT fk7x3uo1krtxr8r60py9rd2ys5p FOREIGN KEY (user_role_id)
        REFERENCES public.user_role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;


-- Table: public.media

-- DROP TABLE public.media;

CREATE TABLE public.media
(
    id bigint NOT NULL,
    imdb_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    title character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT media_pkey PRIMARY KEY (id),
    CONSTRAINT uk_c1cn0tq81kervx605dbq6of33 UNIQUE (imdb_id)

)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;


-- Table: public.user_media

-- DROP TABLE public.user_media;

CREATE TABLE public.user_media
(
    id bigint NOT NULL,
    adding_date timestamp without time zone,
    media_id bigint,
    user_id bigint,
    CONSTRAINT user_media_pkey PRIMARY KEY (id),
    CONSTRAINT fkfxb1n3krkmxfttriemaxl4bnn FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkqdbnvokjx1xgf39mor0lg09d5 FOREIGN KEY (media_id)
        REFERENCES public.media (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;


-- Table: public.user_media_collection

-- DROP TABLE public.user_media_collection;

CREATE TABLE public.user_media_collection
(
    id bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    owner_user_id bigint,
    CONSTRAINT user_media_collection_pkey PRIMARY KEY (id),
    CONSTRAINT fkhsl26qbv1mf0b2335stbhan5r FOREIGN KEY (owner_user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;


ALTER TABLE public.users
    ADD CONSTRAINT fk18m96b2663jpfficcwtr4oweq FOREIGN KEY (root_user_media_collection)
        REFERENCES public.user_media_collection (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;


-- Table: public.user_media_collection_user_media_list

-- DROP TABLE public.user_media_collection_user_media_list;

CREATE TABLE public.user_media_collection_user_media_list
(
    user_media_collection_id bigint NOT NULL,
    user_media_id bigint NOT NULL,
    CONSTRAINT fk9d0eqrnqlc6xyfuu101no7wl7 FOREIGN KEY (user_media_id)
        REFERENCES public.user_media (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkdtulih5gq97l4sw2ofyov80ql FOREIGN KEY (user_media_collection_id)
        REFERENCES public.user_media_collection (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;