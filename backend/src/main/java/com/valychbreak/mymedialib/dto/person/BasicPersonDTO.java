package com.valychbreak.mymedialib.dto.person;

import com.valychbreak.mymedialib.dto.movie.BasicMediaDTO;

import java.util.List;

public class BasicPersonDTO {
    private Long id;
    private String name;
    private String image;
    private Integer popularity;
    private List<BasicMediaDTO> knownFor;

    public BasicPersonDTO(Long id, String name, String image, Integer popularity, List<BasicMediaDTO> knownFor) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.popularity = popularity;
        this.knownFor = knownFor;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<BasicMediaDTO> getKnownFor() {
        return knownFor;
    }
}
