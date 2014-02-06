package com.audatex.movie.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.audatex.movie.api.IActorAPI;
import com.audatex.movie.dao.ActorDaoI;
import com.audatex.movie.domain.Actor;

@Component
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, noRollbackFor = {EmptyResultDataAccessException.class}, rollbackFor = {Exception.class})
public class ActorAPI implements IActorAPI {

	private static Logger log = LoggerFactory.getLogger(ActorAPI.class);

	@Autowired
	private ActorDaoI actorDao;

	public void saveActor(Actor actor) {
		actorDao.persist(actor);
	}
	
	public void updateActor(Actor actor) {
		actorDao.merge(actor);
	}

	public void deleteActor(Actor actor) {
		actorDao.delete(actorDao.findById(actor.getId()));
	}
	
	public void deleteActorById(Long id) {
		actorDao.delete(actorDao.findById(id));
	}
	
	public List<Actor> getAllActors() {
		return actorDao.findAll();
	}
	
	public Actor getActorById(Long id) {
		return actorDao.findById(id);
	}
	
	public Actor getActorByName(String name) {
		Actor exampleInstance = new Actor();
		exampleInstance.setName(name);
		List<Actor> resultList = actorDao.findByExample(exampleInstance);
		if (resultList != null) {
			return resultList.get(0);
		}
		return null;
	}
	
	public List<String[]> mapActorsToJSON(List<Actor> actors) {
		List<String[]> jsonList = new ArrayList<>();
		String[] tempActor;
		for (Actor actor : actors) {
			tempActor = new String[2];
			tempActor[0] = actor.getId().toString();
			tempActor[1] = actor.getName();
			jsonList.add(tempActor);
		}
		return jsonList;
	}
	
}
