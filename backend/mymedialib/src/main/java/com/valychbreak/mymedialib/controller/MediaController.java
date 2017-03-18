package com.valychbreak.mymedialib.controller;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.OmdbApi;
import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.omertron.omdbapi.model.SearchResults;
import com.omertron.omdbapi.tools.OmdbBuilder;
import com.valychbreak.mymedialib.entity.Media;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valeriy on 3/18/2017.
 */
@RestController
@RequestMapping(value = "/movie")
public class MediaController {
    public static final Logger logger = LoggerFactory.getLogger(MediaController.class);


    @RequestMapping(value = "/getlist", produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    public ResponseEntity<List<Media>> getMovies() throws OMDBException {
        List<Media> mediaList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mediaList.add(new Media("tt0145487", "Spider-Man"));
            mediaList.add(new Media("tt1722512", "Ultimate Spider-Man"));
        }

        return new ResponseEntity<>(mediaList, HttpStatus.OK);
    }
    @RequestMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    public ResponseEntity<List<OmdbVideoBasic>> searchMovies(@RequestParam(value = "s") String searchTerm,
                                                             @RequestParam(value = "year", required = false) Integer year,
                                                             @RequestParam(value = "page", required = false) Integer page
    ) throws OMDBException {
        OmdbApi api = new OmdbApi();
        OmdbBuilder omdbBuilder = new OmdbBuilder();

        omdbBuilder.setSearchTerm(searchTerm);

        if(year != null) {
            omdbBuilder.setYear(year);
        }


        SearchResults results = api.search(omdbBuilder.build());
        logger.info("is response: " + results.isResponse() + "; current results: " + results.getResults().size() + "; total results = " + results.getTotalResults());
        return new ResponseEntity<>(results.getResults(), HttpStatus.OK);
    }
}
