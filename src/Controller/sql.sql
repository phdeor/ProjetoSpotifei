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




CREATE TABLE IF NOT EXISTS public."Artista"
(
    "ID_Artista" integer NOT NULL,
    "Nome" "char" NOT NULL,
    "ID_Musica" integer NOT NULL,
    CONSTRAINT "Artista_pkey" PRIMARY KEY ("ID_Artista", "ID_Musica")
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Artista"
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public."Musica"
(
    "ID_Musica" integer NOT NULL,
    "Nome" "char" NOT NULL,
    "ID_Playlist" integer NOT NULL,
    "ID_Artista" integer NOT NULL,
    CONSTRAINT "Musica_pkey" PRIMARY KEY ("ID_Musica", "ID_Playlist")
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Musica"
    OWNER to postgres;