package com.ratiose.testtask.service;

import com.ratiose.testtask.entity.Movie;
import com.ratiose.testtask.entity.User;

public interface MovieService {
	Movie markMovieWatched(Long movieId, User user);
}