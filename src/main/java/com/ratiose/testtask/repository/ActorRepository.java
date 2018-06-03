package com.ratiose.testtask.repository;

import org.springframework.data.repository.CrudRepository;

import com.ratiose.testtask.entity.Actor;

public interface ActorRepository extends CrudRepository<Actor,Long>{	
	Actor findByExternalId(Long externalId);
}
