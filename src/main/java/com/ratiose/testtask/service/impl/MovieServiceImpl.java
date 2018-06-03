package com.ratiose.testtask.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ratiose.testtask.entity.Movie;
import com.ratiose.testtask.entity.User;
import com.ratiose.testtask.repository.MovieRepository;
import com.ratiose.testtask.service.MovieService;
import com.ratiose.testtask.service.tmdb.TmdbApi;
import com.ratiose.testtask.service.tmdb.dto.TmdbMovieInfo;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private TmdbApi tmdbApi;
	
	@Override
	public Movie markMovieWatched(Long movieId, User user) {
		Movie movie = movieRepository.findByExternalId(movieId);
		if (movie == null)
			movie = createNewMovie(movieId);
		movie.setUsers(getUsers(user));
		return movieRepository.save(movie);
	}

	private Movie createNewMovie(Long movieId) {
		TmdbMovieInfo movieInfo = tmdbApi.getMovieInfo(movieId);
		Movie movie = new Movie();
		movie.setExternalId(movieId);
		movie.setTitle(movieInfo.getTitle());
		movie.setReleaseDate(converStringToDate(movieInfo.getReleaseDate()));
		return movie;
	}

	Date converStringToDate(String dateInString) {
		String pattern = "yyyy-MM-dd";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDate localDate = LocalDate.parse(dateInString, formatter);
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	private HashSet<User> getUsers(User user) {
		HashSet<User> users = new HashSet<User>();
		users.add(user);
		return users;
	}

}
