/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  unifpeoliveira
 * Created: 26 de abr. de 2025
 */

CREATE TABLE  usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE curtidas (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER NOT NULL REFERENCES usuarios(id),
    musica_id INTEGER NOT NULL REFERENCES musica(id_musica),
    UNIQUE (usuario_id, musica_id)
);

CREATE TABLE IF NOT EXISTS public."Artista"
(
    "id_artista" integer NOT NULL,
    "nome" "char" NOT NULL,
    "id_musica" integer NOT NULL,
    CONSTRAINT "Artista_pkey" PRIMARY KEY ("id_artista", "id_musica")
)


CREATE TABLE IF NOT EXISTS public."musica"
(
    "id_musica" integer NOT NULL,
    "nome" "char" NOT NULL,
    "id_playlist" integer NOT NULL,
    "id_artista" integer NOT NULL,
    CONSTRAINT "Musica_pkey" PRIMARY KEY ("ID_Musica", "ID_Playlist")
)

CREATE TABLE IF NOT EXISTS public.historico
(
    id integer NOT NULL DEFAULT nextval('historico_id_seq'::regclass),
    nome character varying(100) COLLATE pg_catalog."default",
    genero character varying(50) COLLATE pg_catalog."default",
    CONSTRAINT historico_pkey PRIMARY KEY (id)
)
