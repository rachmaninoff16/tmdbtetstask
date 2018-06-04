package com.ratiose.testtask.service.tmdb;

import java.util.List;

import com.ratiose.testtask.service.tmdb.dto.TmdbMovieInfo;

public interface TmdbApi {
    String popularMovies();
    String getActorNameById(Integer actorId);
    TmdbMovieInfo getMovieInfo(Integer movieId);    
    List<Integer> getAllCastByMovie(Integer movieId);
	List<Integer> getAllMovieIdsByActor(Integer actorId);
}
