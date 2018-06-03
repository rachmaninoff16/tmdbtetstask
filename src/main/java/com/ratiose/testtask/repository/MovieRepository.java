package com.ratiose.testtask.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ratiose.testtask.entity.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long>  {
	Movie findByExternalId(Long extrenalId);
	
	@Query(nativeQuery = true, value = "SELECT m.id, m.external_id, m.release_date, m.title "
			+ "FROM MOVIE m LEFT JOIN WATCHED_MOVIE wm "
			+ "ON m.id = wm.movie_id "
			+ "WHERE wm.movie_id IS NULL "
			+ "AND MONTH(m.release_date) = :month "
			+ "AND YEAR(m.release_date) = :year")
	List<Movie> findNotWatchedMoviesByMothAndYear(@Param("month")Integer month, @Param("year")Integer year);
}