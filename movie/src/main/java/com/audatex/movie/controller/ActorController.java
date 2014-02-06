package com.audatex.movie.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.audatex.movie.api.IActorAPI;
import com.audatex.movie.domain.Actor;

@Controller
@RequestMapping(value = "/actors")
public class ActorController {

	private static Logger logger = LoggerFactory.getLogger(ActorController.class);
	
	@Autowired
	private IActorAPI actorAPI;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showUIGeneration(Model model) {
		logger.debug("Received request to show ui generation screen");
		return "/actor/actor";
	}
	
	@RequestMapping(value = "/getActors", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getActors(Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		List<Actor> actors = actorAPI.getAllActors();
		data.put("actors", actorAPI.mapActorsToJSON(actors));
		data.put("status", "1");
		return data;
	}
	
	@RequestMapping(value = "/getActorIds", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getActorIds(Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		List<Actor> actors = actorAPI.getAllActors();
		List<Long> actorIds = new ArrayList<>();
		for (Actor actor : actors) {
			actorIds.add(actor.getId());
		}
		data.put("actorIds", actorIds);
		data.put("status", "1");
		return data;
	}
	
	@RequestMapping(value = "/updateActor", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateActor(@RequestParam(value="id") Long id, @RequestParam(value="name") String name, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		try {
			Actor actor = actorAPI.getActorById(id);
			actor.setName(name);
			actor.setTransactionTime(new Date());
			actorAPI.updateActor(actor);
			data.put("status", "1");
		} catch (Exception e) {
			data.put("status", "-1");
			e.printStackTrace();
		}
		return data;
	}
	
	@RequestMapping(value = "/createActor", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createActor(@RequestParam(value="name") String name, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		try {
			Actor actor = new Actor();
			actor.setName(name);
			actor.setTransactionTime(new Date());
			actorAPI.saveActor(actor);
			data.put("status", "1");
			data.put("id", actor.getId());
		} catch (Exception e) {
			data.put("status", "-1");
			e.printStackTrace();
		}
		return data;
	}
	
	@RequestMapping(value = "/deleteActor", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deleteActor(@RequestParam(value="id") Long id, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		try {
			actorAPI.deleteActorById(id);
			data.put("status", "1");
		} catch (Exception e) {
			data.put("status", "-1");
			e.printStackTrace();
		}
		return data;
	}
	
}
