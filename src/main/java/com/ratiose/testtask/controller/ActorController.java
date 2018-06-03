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

import com.ratiose.testtask.entity.Actor;
import com.ratiose.testtask.entity.User;
import com.ratiose.testtask.service.ActorService;
import com.ratiose.testtask.service.UserService;

@RestController
@RequestMapping("/actor")
public class ActorController {
	@Autowired
	private UserService userService;
	@Autowired
	private ActorService favoritActorService;

	@RequestMapping(value = "/addfavorit", method = POST)
	public ResponseEntity addFavoriteActor(@RequestParam String email, @RequestParam String password,
			@RequestParam Long favoritActorId, HttpSession session) {
		User user = userService.findUser(email, password);
		if (user == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with such email/password doesn't exist");
		Actor addedActor = favoritActorService.addFavoritActor(favoritActorId, user);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(addedActor);
	}

	@RequestMapping(value = "/removefavorit", method = POST)
    public ResponseEntity removeFavoriteActor(@RequestParam String email,
                                  @RequestParam String password,
                                  @RequestParam Long favoritActorId,
                                  HttpSession session) {
        User user = userService.findUser(email, password);
		if (user == null) 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with such email/password doesn't exist");
        Actor removedActor = favoritActorService.removeFavoriteActor(favoritActorId, user);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(removedActor);
    }
}