package com.ratiose.testtask.repository;

import org.springframework.data.repository.CrudRepository;

import com.ratiose.testtask.entity.Movie;
import com.ratiose.testtask.entity.User;

public interface MovieRepository extends CrudRepository<Movie, Long>  {
	Movie findByExternalId(Long extrenalId);
}