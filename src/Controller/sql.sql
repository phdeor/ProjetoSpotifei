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

CREATE TABLE musica (
    id_musica SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    genero VARCHAR(50)
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


CREATE TABLE IF NOT EXISTS public.historico (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    genero VARCHAR(50)
);


CREATE TABLE artista ( 
    id_artista INT PRIMARY KEY REFERENCES pessoa(id), 
    nome VARCHAR(100),

);




