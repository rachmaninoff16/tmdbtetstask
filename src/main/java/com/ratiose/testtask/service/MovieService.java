package com.ratiose.testtask.service;

import java.util.List;

import com.ratiose.testtask.entity.Movie;
import com.ratiose.testtask.entity.User;

public interface MovieService {
	Movie markMovieWatched(Integer movieId, User user);
	List<Movie> getUnviewedMoviesWithFavoriteActors(User user, Integer month, Integer year);
	Movie addMovie(Integer movieId);
}