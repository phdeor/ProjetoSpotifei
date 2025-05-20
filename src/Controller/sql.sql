/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  unifpeoliveira
 * Created: 26 de abr. de 2025
 */

CREATE TABLE  IF NOT EXISTS usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS artistas(
    id_artista SERIAL  PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS musicas (
    id_musica SERIAL  PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    genero VARCHAR(50),
    id_artista INT NOT NULL,
    FOREIGN KEY (id_artista) REFERENCES artistas(id_artista)
);

CREATE TABLE IF NOT EXISTS curtidas (
    id SERIAL  PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_musica INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_musica) REFERENCES musicas(id_musica)
);

CREATE TABLE IF NOT EXISTS historico (
    id SERIAL  PRIMARY KEY,
    tipo VARCHAR(100),
    pesquisa VARCHAR(100),
    id_usuario integer NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

CREATE TABLE  IF NOT EXISTS playlists (
    id SERIAL  PRIMARY KEY,
    nome_playlist VARCHAR(100),
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

CREATE TABLE  IF NOT EXISTS musicas_playlists (
    id_playlist INT NOT NULL,
    id_musica INT NOT NULL,
    FOREIGN KEY (id_playlist) REFERENCES playlists(id),
    FOREIGN KEY (id_musica) REFERENCES musicas(id_musica)
);



