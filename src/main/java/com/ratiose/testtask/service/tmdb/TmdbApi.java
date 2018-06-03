package com.ratiose.testtask.service.tmdb;

import java.util.List;

import com.ratiose.testtask.service.tmdb.dto.TmdbMovieInfo;

public interface TmdbApi {
    String popularMovies();
    String getActorNameById(Long id);
    TmdbMovieInfo getMovieInfo(Long id);
    List<Integer> getAllCastByMovie(Long movieId);
	List<Integer> getAllMovieIdsByActor(Long actorId);
}
