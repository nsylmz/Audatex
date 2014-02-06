package com.audatex.movie.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;


@SuppressWarnings("serial")
@Entity
@Table(name="MOVIE",
	   uniqueConstraints = @UniqueConstraint(columnNames = {"id", "NAME"}))
public class Movie extends BaseEntity {
	
	@Column(name = "NAME")
	@NotNull
	private String name;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "MOVIE_ACTOR", joinColumns = { @JoinColumn(name = "MOVIE_ID", referencedColumnName = "id") }, 
									 inverseJoinColumns = { @JoinColumn(name = "ACTOR_ID", referencedColumnName = "id") })
	private Set<Actor> actors;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Actor> getActors() {
		return actors;
	}

	public void setActors(Set<Actor> actors) {
		this.actors = actors;
	}
	
}
