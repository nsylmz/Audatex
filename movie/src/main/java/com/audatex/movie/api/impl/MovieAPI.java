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

import com.audatex.movie.api.IMovieAPI;
import com.audatex.movie.dao.MovieDaoI;
import com.audatex.movie.domain.Movie;

@Component
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, noRollbackFor = {EmptyResultDataAccessException.class}, rollbackFor = {Exception.class})
public class MovieAPI implements IMovieAPI {

	private static Logger log = LoggerFactory.getLogger(MovieAPI.class);

	@Autowired
	private MovieDaoI movieDao;

	public void saveMovie(Movie movie) {
		movieDao.persist(movie);
	}
	
	public void updateMovie(Movie movie) {
		movieDao.merge(movie);
	}

	public void deleteMovie(Movie movie) {
		movieDao.delete(movieDao.findById(movie.getId()));
	}
	
	public void deleteMovieById(Long id) {
		movieDao.delete(movieDao.findById(id));
	}
	
	public List<Movie> getAllMovies() {
		return movieDao.findAll();
	}
	
	public Movie getMovieById(Long id) {
		return movieDao.findById(id);
	}
	
	public Movie getMovieByName(String name) {
		Movie exampleInstance = new Movie();
		exampleInstance.setName(name);
		List<Movie> resultList = movieDao.findByExample(exampleInstance);
		if (resultList != null) {
			return resultList.get(0);
		}
		return null;
	}
	
	public List<String[]> mapMoviesToJSON(List<Movie> movies) {
		List<String[]> jsonList = new ArrayList<>();
		String[] tempMovie;
		for (Movie movie : movies) {
			tempMovie = new String[2];
			tempMovie[0] = movie.getId().toString();
			tempMovie[1] = movie.getName();
			jsonList.add(tempMovie);
		}
		return jsonList;
	}

}
