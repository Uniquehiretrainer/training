package com.uniquehire.cafe.dto;

import lombok.Data;
import java.util.Set;

@Data
public class ActorResponseDTO {

    private Long id;
    private String name;

    private Set<MovieSimpleDTO> movies;
}
