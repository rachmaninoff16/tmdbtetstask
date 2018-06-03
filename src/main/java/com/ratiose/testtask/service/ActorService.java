package com.ratiose.testtask.service;

import com.ratiose.testtask.entity.Actor;
import com.ratiose.testtask.entity.User;

public interface ActorService {
	Actor addFavoritActor(Long favoritActorId, User user);
	Actor removeFavoriteActor(Long favoriteActorId, User user);	
}
