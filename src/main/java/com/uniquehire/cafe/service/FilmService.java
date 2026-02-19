package com.uniquehire.cafe.service;

import com.uniquehire.cafe.dto.ActorRequestDTO;
import com.uniquehire.cafe.dto.ActorResponseDTO;
import com.uniquehire.cafe.dto.MovieSimpleDTO;

import java.util.List;

public interface FilmService {

    ActorResponseDTO saveActor(ActorRequestDTO dto);

    List<ActorResponseDTO> getAllActors();

    List<MovieSimpleDTO> getAllMovies();
}
