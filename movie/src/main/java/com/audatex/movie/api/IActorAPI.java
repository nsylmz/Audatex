package com.audatex.movie.api;

import java.util.List;

import com.audatex.movie.domain.Actor;


public interface IActorAPI {
	
	public void saveActor(Actor actor);
	
	public void updateActor(Actor actor);

	public void deleteActor(Actor actor);
	
	public void deleteActorById(Long id);
	
	public List<Actor> getAllActors();
	
	public List<String[]> mapActorsToJSON(List<Actor> actors);
	
	public Actor getActorById(Long id);
	
	public Actor getActorByName(String name);

}
