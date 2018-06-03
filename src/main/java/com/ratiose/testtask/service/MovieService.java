package com.ratiose.testtask.service;

import java.util.List;

import com.ratiose.testtask.entity.Movie;
import com.ratiose.testtask.entity.User;

public interface MovieService {
	Movie markMovieWatched(Long movieId, User user);
	List<Movie> getNotWatchedMoviesWithFavoriteActors(User user, Integer month, Integer year);
}