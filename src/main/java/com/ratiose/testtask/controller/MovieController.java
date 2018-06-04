package com.ratiose.testtask.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ratiose.testtask.entity.Movie;
import com.ratiose.testtask.entity.User;
import com.ratiose.testtask.service.MovieService;
import com.ratiose.testtask.service.UserService;
import com.ratiose.testtask.service.tmdb.TmdbApi;

@RestController
@RequestMapping("/movie")
public class MovieController {
	@Autowired
	private UserService userService;
	@Autowired
	private MovieService movieService;
	@Autowired
	private TmdbApi tmdbApi;

	@RequestMapping(value = "/popular", method = POST)
	public ResponseEntity popular(@RequestParam String email, @RequestParam String password, HttpSession session) {
		if (userService.findUser(email, password) == null) {
			return getBadRequestResponse();
		}
		String popularMovies = tmdbApi.popularMovies();
		return getOkResponse(popularMovies);
	}

	@RequestMapping(value = "/add", method = POST)
	public ResponseEntity addMovie(@RequestParam String email, @RequestParam String password,
			@RequestParam Integer movieId, HttpSession session) {
		User user = userService.findUser(email, password);
		if (user == null)
			return getBadRequestResponse();
		Movie addedMovie = movieService.addMovie(movieId);
		return getOkResponse(addedMovie);
	}

	@RequestMapping(value = "/makrWatched", method = POST)
	public ResponseEntity markMovieWatched(@RequestParam String email, @RequestParam String password,
			@RequestParam Integer movieId, HttpSession session) {
		User user = userService.findUser(email, password);
		if (user == null)
			return getBadRequestResponse();
		Movie markedMovie = movieService.markMovieWatched(movieId, user);
		return getOkResponse(markedMovie);
	}

	@RequestMapping(value = "/searchUnviewed", method = POST)
	public ResponseEntity searchUnviewed(@RequestParam String email, @RequestParam String password,
			@RequestParam Integer month, @RequestParam Integer year, HttpSession session) {
		User user = userService.findUser(email, password);
		if (user == null)
			return getBadRequestResponse();
		List<Movie> unviewedMovies = movieService.getUnviewedMoviesWithFavoriteActors(user, month, year);
		return getOkResponse(unviewedMovies);
	}

	private ResponseEntity getOkResponse(Object responseObject) {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(responseObject);
	}

	private ResponseEntity getBadRequestResponse() {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with such email/password doesn't exist");
	}

}
