package com.uniquehire.cafe.serviceimpl;

import com.uniquehire.cafe.dto.ActorRequestDTO;
import com.uniquehire.cafe.dto.ActorResponseDTO;
import com.uniquehire.cafe.dto.MovieSimpleDTO;
import com.uniquehire.cafe.model.Actor;
import com.uniquehire.cafe.model.Movie;
import com.uniquehire.cafe.repository.ActorRepository;
import com.uniquehire.cafe.repository.MovieRepository;
import com.uniquehire.cafe.service.FilmService;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class FilmServiceImpl implements FilmService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;

    public FilmServiceImpl(ActorRepository actorRepository,
                           MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public ActorResponseDTO saveActor(ActorRequestDTO dto) {

        Actor actor = new Actor();
        actor.setName(dto.getName());

        dto.getMovies().forEach(movieDto -> {

            Movie movie = movieRepository
                    .findByTitle(movieDto.getTitle())
                    .orElseGet(() -> {
                        Movie newMovie = new Movie();
                        newMovie.setTitle(movieDto.getTitle());
                        return newMovie;
                    });

            actor.addMovie(movie);
        });

        Actor savedActor = actorRepository.save(actor);

        return mapActorToResponse(savedActor);
    }

    @Override
    public List<ActorResponseDTO> getAllActors() {
        return actorRepository.findAll()
                .stream()
                .map(this::mapActorToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieSimpleDTO> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(movie -> {
                    MovieSimpleDTO dto = new MovieSimpleDTO();
                    dto.setId(movie.getId());
                    dto.setTitle(movie.getTitle());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private ActorResponseDTO mapActorToResponse(Actor actor) {

        ActorResponseDTO response = new ActorResponseDTO();
        response.setId(actor.getId());
        response.setName(actor.getName());

        Set<MovieSimpleDTO> movies = actor.getMovies().stream()
                .map(movie -> {
                    MovieSimpleDTO dto = new MovieSimpleDTO();
                    dto.setId(movie.getId());
                    dto.setTitle(movie.getTitle());
                    return dto;
                })
                .collect(Collectors.toSet());

        response.setMovies(movies);

        return response;
    }
}
