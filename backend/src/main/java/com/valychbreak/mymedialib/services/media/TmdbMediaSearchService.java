package com.valychbreak.mymedialib.services.media;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.Media;
import com.uwetrottmann.tmdb2.entities.MediaResultsPage;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.services.utils.SearchParams;
import com.valychbreak.mymedialib.services.utils.SearchResult;
import com.valychbreak.mymedialib.services.utils.SearchResultFactory;
import com.valychbreak.mymedialib.services.utils.TmdbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
// TODO: remove favorite determination logic and move it to controller
public class TmdbMediaSearchService implements MediaSearchService {
    private static final Logger LOG = LoggerFactory.getLogger(TmdbMediaSearchService.class);

    private Tmdb tmdb;
    private TmdbService tmdbService;
    private UserMediaService userMediaService;

    public TmdbMediaSearchService(Tmdb tmdb, TmdbService tmdbService, UserMediaService userMediaService) {
        this.tmdb = tmdb;
        this.tmdbService = tmdbService;
        this.userMediaService = userMediaService;
    }

    @Override
    public SearchResult<MediaFullDetails> search(SearchParams searchParams, User user) throws IOException {
        MediaResultsPage movieResults = searchMedia(searchParams, tmdb);
        List<MediaFullDetails> mediaSearchResults = convertMedia(movieResults, user);
        return new SearchResultFactory().create(searchParams.getPage(),  movieResults.total_pages,  movieResults.total_results, mediaSearchResults);
    }

    public SearchResult<Media> searchBasic(SearchParams searchParams, User user) throws IOException {
        MediaResultsPage movieResults = searchMedia(searchParams, tmdb);
        List<Media> mediaSearchResults = convertMediaBasic(movieResults);
        return new SearchResultFactory().create(searchParams.getPage(),  movieResults.total_pages,  movieResults.total_results, mediaSearchResults);
    }

    private List<Media> convertMediaBasic(MediaResultsPage movieResults) {
        return movieResults.results;
    }

    private List<MediaFullDetails> convertMedia(MediaResultsPage movieResults, User user) {
        Stream<MediaFullDetails> stream = movieResults.results.parallelStream()
                .map(tmdbMedia -> {
                    try {
                        return tmdbService.getMediaDetails(tmdbMedia);
                    } catch (IOException e) {
                        LOG.error("Media was not added", e);
                    }
                    return Optional.<MediaFullDetails>empty();
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .peek(mediaFullDetails -> {
                    mediaFullDetails.setFavourite(userMediaService.isUserFavorite(user, mediaFullDetails));
                });

        return stream.collect(Collectors.toList());
    }

    private MediaResultsPage searchMedia(SearchParams searchParams, Tmdb tmdb) throws IOException {
        Call<MediaResultsPage> call = tmdb.searchService().multi(searchParams.getQuery(), searchParams.getPage(), null, null, null);
        return call.execute().body();
    }
}
