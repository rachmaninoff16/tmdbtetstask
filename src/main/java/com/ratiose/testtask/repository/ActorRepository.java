package com.ratiose.testtask.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ratiose.testtask.entity.Actor;

public interface ActorRepository extends CrudRepository<Actor,Long>{	
	Actor findByExternalId(Long externalId);
	
	@Query(nativeQuery = true, value = "SELECT a.id, a.external_id, a.name "
			+ "FROM ACTOR as a  "
			+ "INNER JOIN FAVORITE_ACTOR as fa ON a.id = fa.actor_id "
			+ "INNER JOIN USER as u ON fa.user_id = u.id "
			+ "WHERE u.email = :userEmail")
	List<Actor> findFavoriteActors(@Param("userEmail") String userEmail);
}
