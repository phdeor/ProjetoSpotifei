# ProjetoSpotifei

CREATE TABLE artista (
    id_artista INT PRIMARY KEY REFERENCES pessoa(id),
    nome VARCHAR(100),
    
    
);

CREATE TABLE curtida (
    id_usuario INT,
    id_musica INT,
    PRIMARY KEY (id_usuario, id_musica),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_musica) REFERENCES musica(id_musica)
);


CREATE TABLE musica (
    id_musica SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    id_artista INT NOT NULL REFERENCES artista(id)
);

CREATE TABLE playlist (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    usuario_id INT NOT NULL REFERENCES usuario(id)
);

CREATE TABLE playlist_musica (
    playlist_id INT REFERENCES playlist(id) ON DELETE CASCADE,
    musica_id INT REFERENCES musica(id) ON DELETE CASCADE,
    PRIMARY KEY (playlist_id, musica_id)
);
