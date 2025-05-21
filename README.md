# ProjetoSpotifei

Este projeto é uma aplicação inspirada no Spotify, desenvolvida em Java utilizando o padrão MVC, com interface gráfica feita em Swing e persistência de dados com banco relacional via JDBC. A proposta é oferecer funcionalidades similares às de uma plataforma de streaming de música, com foco na experiência do usuário e no gerenciamento de conteúdo musical.

A aplicação permite que o usuário se cadastre e faça login com autenticação baseada em banco de dados. Após o login, ele pode buscar músicas por nome, artista ou gênero. As informações detalhadas das músicas são exibidas para facilitar a navegação e escolha.

Os usuários podem curtir e descurtir músicas, e esse comportamento é armazenado para permitir a construção de um histórico personalizado. Também é possível criar, editar e excluir playlists, além de adicionar e remover músicas dessas listas. A área de histórico permite visualizar as dez últimas músicas buscadas, bem como as músicas curtidas e descurtidas.

O sistema está estruturado em camadas: a camada de modelo representa as entidades principais (como usuários, músicas e playlists), a camada de controle gerencia as regras de negócio e chamadas entre interface e banco, enquanto a camada de visualização é composta por telas desenvolvidas com Swing.

Este projeto ainda está em desenvolvimento e algumas melhorias estão sendo planejadas, mas a base das funcionalidades principais já está implementada.
