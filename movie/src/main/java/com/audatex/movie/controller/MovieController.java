package com.audatex.movie.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.audatex.movie.api.IMovieAPI;
import com.audatex.movie.domain.Actor;
import com.audatex.movie.domain.Movie;

@Controller
@RequestMapping(value = "/movies")
public class MovieController {

	private static Logger logger = LoggerFactory.getLogger(MovieController.class);
	
	@Autowired
	private IMovieAPI movieAPI;
	
	@Autowired
	private IActorAPI actorAPI;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showUIGeneration(Model model) {
		logger.debug("Received request to show ui generation screen");
		return "/movie/movie";
	}
	
	@RequestMapping(value = "/getMovies", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getMovies(Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		List<Movie> movies = movieAPI.getAllMovies();
		data.put("movies", movieAPI.mapMoviesToJSON(movies));
		data.put("status", "1");
		return data;
	}
	
	@RequestMapping(value = "/getMovieActors", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getMovieActors(@RequestParam(value="id") Long id, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		Movie movie = movieAPI.getMovieById(id);
		List<Actor> actors = new ArrayList<>();
		actors.addAll(movie.getActors());
		data.put("movieActors", actorAPI.mapActorsToJSON(actors));
		data.put("status", "1");
		return data;
	}
	
	@RequestMapping(value = "/saveMovieActors", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveMovieActors(@RequestParam(value="movieId") Long movieId, 
			@RequestParam(value="newMovieActorIds") String newMovieActorIds, 
			@RequestParam(value="removedMovieActorIds") String removedMovieActorIds, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		Movie movie = movieAPI.getMovieById(movieId);
		Long actorId;
		List<Actor> actors = new ArrayList<>();
		actors.addAll(movie.getActors());
		if (!StringUtils.isEmpty(removedMovieActorIds) && !removedMovieActorIds.equals("undefined")) {
			String[] removedChildren = removedMovieActorIds.split("-");
			for (String id : removedChildren) {
				actorId = Long.parseLong(id);
				for (Actor actor : actors) {
					if (actor.getId().equals(actorId)) {
						movie.getActors().remove(actor);
					}
				}
			}
		}
		if (!StringUtils.isEmpty(newMovieActorIds) && !newMovieActorIds.equals("undefined")) {
			String[] newMovieActors = newMovieActorIds.split("-");
			for (String id : newMovieActors) {
				actorId = Long.parseLong(id);
				movie.getActors().add(actorAPI.getActorById(actorId));
			}
		}
		movieAPI.updateMovie(movie);
		data.put("status", "1");
		return data;
	}
	
	@RequestMapping(value = "/updateMovie", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateMovie(@RequestParam(value="id") Long id, @RequestParam(value="name") String name, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		try {
			Movie movie = movieAPI.getMovieById(id);
			movie.setName(name);
			movie.setTransactionTime(new Date());
			movieAPI.updateMovie(movie);
			data.put("status", "1");
		} catch (Exception e) {
			data.put("status", "-1");
			e.printStackTrace();
		}
		return data;
	}
	
	@RequestMapping(value = "/createMovie", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createMovie(@RequestParam(value="name") String name, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		try {
			Movie movie = new Movie();
			movie.setName(name);
			movie.setTransactionTime(new Date());
			movieAPI.saveMovie(movie);
			data.put("status", "1");
			data.put("id", movie.getId());
		} catch (Exception e) {
			data.put("status", "-1");
			e.printStackTrace();
		}
		return data;
	}
	
	@RequestMapping(value = "/deleteMovie", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deleteMovie(@RequestParam(value="id") Long id, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		try {
			movieAPI.deleteMovieById(id);
			data.put("status", "1");
		} catch (Exception e) {
			data.put("status", "-1");
			e.printStackTrace();
		}
		return data;
	}
	
}
