package com.uniquehire.cafe.controller;

import com.uniquehire.cafe.dto.ActorRequestDTO;
import com.uniquehire.cafe.dto.ActorResponseDTO;
import com.uniquehire.cafe.dto.MovieSimpleDTO;
import com.uniquehire.cafe.service.FilmService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping("/actors")
    public ResponseEntity<ActorResponseDTO> saveActor(
            @RequestBody ActorRequestDTO dto) {

        return ResponseEntity.ok(filmService.saveActor(dto));
    }

    @GetMapping("/actorsfilm")
    public ResponseEntity<List<ActorResponseDTO>> getActors() {

        return ResponseEntity.ok(filmService.getAllActors());
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MovieSimpleDTO>> getMovies() {

        return ResponseEntity.ok(filmService.getAllMovies());
    }
}
