package com.ratiose.testtask.service.impl;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ratiose.testtask.entity.Actor;
import com.ratiose.testtask.entity.User;
import com.ratiose.testtask.repository.ActorRepository;
import com.ratiose.testtask.service.ActorService;
import com.ratiose.testtask.service.tmdb.TmdbApi;

@Service
@Transactional
public class ActorServiceImpl implements ActorService{
	
	@Autowired
	private TmdbApi tmdbApi;
	@Autowired
	private ActorRepository actorRepository;
	
	@Override
	public Actor addFavoritActor(Long favoritActorId, User user) {
		Actor actor = actorRepository.findByExternalId(favoritActorId);
		if(actor == null ) 
			actor = createNewActor(favoritActorId);		
		actor.setUsers(getUsers(user));
		return actorRepository.save(actor);
	}
	
	@Override
	public Actor removeFavoriteActor(Long favoritActorId, User user) {
		Actor actor = actorRepository.findByExternalId(favoritActorId);
		if(actor == null)
			throw new RuntimeException("Actor with external id " + favoritActorId + " doesn't exist");
		actor.getUsers().remove(user);		
		return actorRepository.save(actor);
	}
	
	private Actor createNewActor(Long favoritActorId) {		
		String actorName = tmdbApi.getActorNameById(favoritActorId);
		Actor actor = new Actor();
		actor.setExternalId(favoritActorId);
		actor.setName(actorName);
		return actor;
	}
	
	private HashSet<User> getUsers(User user) {
		HashSet<User> users = new HashSet<User>();
		users.add(user);
		return users;
	}


}
