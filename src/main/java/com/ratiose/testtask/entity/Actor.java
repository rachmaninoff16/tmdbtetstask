package com.ratiose.testtask.entity;

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

@Entity
public class Actor {
	
	@Id
    @GeneratedValue    
    Long id;	
	@Column(unique=true)
	Long externalId;
	String name;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "FAVORITE_ACTORS", 
		joinColumns = { @JoinColumn(name = "ACTOR_ID") }, 
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<User> getUsers() {
		return this.users;
	}
	
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
}
