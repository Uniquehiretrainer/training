package com.uniquehire.cafe.dto;

import lombok.Data;
import java.util.Set;

@Data
public class ActorRequestDTO {

    private String name;

    // Accept full movie objects
    private Set<MovieRequestDTO> movies;
}
