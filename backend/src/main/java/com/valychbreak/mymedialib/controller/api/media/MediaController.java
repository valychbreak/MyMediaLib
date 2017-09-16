package com.valychbreak.mymedialib.controller.api.media;

import com.omertron.omdbapi.OMDBException;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.*;
import com.uwetrottmann.tmdb2.enumerations.AppendToResponseItem;
import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.data.movie.adapters.MediaFullDetailsTmdbMovieAdapter;
import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.repository.UserRepository;
import com.valychbreak.mymedialib.services.TmdbMovieProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.Call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valeriy on 3/18/2017.
 */
@RestController
@RequestMapping(value = "/api/media")
public class MediaController extends APIController {

}
