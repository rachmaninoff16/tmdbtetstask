package com.ratiose.testtask.entity;

import java.util.Date;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Movie {

	@Id
    @GeneratedValue    
    Long id;	
	@Column(unique=true)
	Long externalId;	
	@Temporal(TemporalType.DATE)
	Date releaseDate;
	String title;
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "WATCHED_MOVIE", 
		joinColumns = { @JoinColumn(name = "MOVIE_ID") }, 
		inverseJoinColumns = { @JoinColumn(name = "USER_ID") })
	Set<User> users = new HashSet<User>(0);
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getExternalId() {
		return externalId;
	}
	
	public void setExternalId(Long externalId) {
		this.externalId = externalId;
	}
	
	public Date getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Set<User> getUsers() {
		return this.users;
	}
	
	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
