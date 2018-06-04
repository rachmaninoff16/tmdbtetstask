package com.ratiose.testtask.service;

import com.ratiose.testtask.entity.Actor;
import com.ratiose.testtask.entity.User;

public interface ActorService {
	Actor addFavoritActor(Integer favoritActorId, User user);
	Actor removeFavoriteActor(Integer favoriteActorId, User user);	
}
