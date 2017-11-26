package com.valychbreak.mymedialib.dto.person;

import com.valychbreak.mymedialib.dto.movie.BasicMovieDTO;

import java.util.List;

public class BasicPersonDTO {
    private Long id;
    private String name;
    private List<BasicMovieDTO> knownFor;

    public BasicPersonDTO(Long id, String name, List<BasicMovieDTO> knownFor) {
        this.id = id;
        this.name = name;
        this.knownFor = knownFor;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<BasicMovieDTO> getKnownFor() {
        return knownFor;
    }
}
