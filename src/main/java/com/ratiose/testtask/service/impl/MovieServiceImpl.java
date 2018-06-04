package com.ratiose.testtask.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ratiose.testtask.entity.Actor;
import com.ratiose.testtask.entity.Movie;
import com.ratiose.testtask.entity.User;
import com.ratiose.testtask.repository.ActorRepository;
import com.ratiose.testtask.repository.MovieRepository;
import com.ratiose.testtask.service.MovieService;
import com.ratiose.testtask.service.tmdb.TmdbApi;
import com.ratiose.testtask.service.tmdb.dto.TmdbMovieInfo;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

	private static final String DATE_PATTERN = "yyyy-MM-dd";
	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private ActorRepository actorRepository;
	@Autowired
	private TmdbApi tmdbApi;
	
	@Override
	public Movie addMovie(Integer movieId) {
		Movie movie = createNewMovie(movieId);		
		return movieRepository.save(movie);
	}
	
	@Override
	public Movie markMovieWatched(Integer movieId, User user) {
		Movie movie = movieRepository.findByExternalId(movieId);
		if (movie == null)
			movie = createNewMovie(movieId);
		movie.setUsers(getUsers(user));
		return movieRepository.save(movie);
	}
	
	@Override
	public List<Movie> getUnviewedMoviesWithFavoriteActors(User user, Integer month, Integer year) {
		List<Actor> favoriteActors = actorRepository.findFavoriteActors(user.getEmail());
		List<Integer> favoriteActorsMovieIds = getFavoriteActorsMovieIds(favoriteActors);		
		List<Movie> unviewedMovies = movieRepository.findUnviewedMoviesByMothAndYear(month, year);			
		return clearNotFavoriteActorsMovies(favoriteActorsMovieIds, unviewedMovies);	
	}

	private List<Integer> getFavoriteActorsMovieIds(List<Actor> favoriteActors) {
		return favoriteActors.stream()
			.map(actor -> tmdbApi.getAllMovieIdsByActor(actor.getExternalId()))
			.flatMap(actorMovieIds -> actorMovieIds.stream())
			.collect(Collectors.toList());
	}

	private Movie createNewMovie(Integer movieId) {
		TmdbMovieInfo movieInfo = tmdbApi.getMovieInfo(movieId);
		Movie movie = new Movie();
		movie.setExternalId(movieId);
		movie.setTitle(movieInfo.getTitle());
		movie.setReleaseDate(converStringToDate(movieInfo.getReleaseDate()));
		return movie;
	}

	private Date converStringToDate(String dateInString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
		LocalDate localDate = LocalDate.parse(dateInString, formatter);
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}	

	private List<Movie> clearNotFavoriteActorsMovies(List<Integer> actorsMovieIds, List<Movie> unviewedMovies) {
		return unviewedMovies.stream()
			.filter(
				movie -> actorsMovieIds.stream()
						.anyMatch(movieId -> movieId.equals(movie.getExternalId()))
			)
			.collect(Collectors.toList());
			
	}
	
	private HashSet<User> getUsers(User user) {
		HashSet<User> users = new HashSet<User>();
		users.add(user);
		return users;
	}

}
