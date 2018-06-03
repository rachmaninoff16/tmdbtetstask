package com.ratiose.testtask.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity popular(@RequestParam String email,
                                  @RequestParam String password,
                                  HttpSession session) {
        if (userService.findUser(email, password) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String popularMovies = tmdbApi.popularMovies();

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(popularMovies);
    }
    
//	@RequestMapping(value = "/add", method = POST)
//	public ResponseEntity markMovieWatched(@RequestParam String email, @RequestParam String password,
//			@RequestParam Long movieId, HttpSession session) {
//		User user = userService.findUser(email, password);
//		if (user == null)
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with such email/password doesn't exist");
//		Actor addedActor = favoritActorService.addFavoritActor(movieId, user);
//		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(addedActor);
//	}
    
	@RequestMapping(value = "/makrWatched", method = POST)
	public ResponseEntity markMovieWatched(@RequestParam String email, @RequestParam String password,
			@RequestParam Long movieId, HttpSession session) {
		User user = userService.findUser(email, password);
		if (user == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with such email/password doesn't exist");
		Movie makredMovie = movieService.markMovieWatched(movieId, user);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(makredMovie);
	}
    
}
