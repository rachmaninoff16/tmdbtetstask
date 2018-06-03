package com.ratiose.testtask.service.tmdb;

import com.ratiose.testtask.service.tmdb.dto.TmdbMovieInfo;

public interface TmdbApi {
    String popularMovies();
    String getActorNameById(Long id);
    TmdbMovieInfo getMovieInfo(Long id);
}
