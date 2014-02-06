package com.audatex.movie.api;

import java.util.List;

import com.audatex.movie.domain.Movie;


public interface IMovieAPI {

	public void saveMovie(Movie movie);
	
	public void updateMovie(Movie movie);

	public void deleteMovie(Movie movie);
	
	public void deleteMovieById(Long id);
	
	public List<Movie> getAllMovies();
	
	public List<String[]> mapMoviesToJSON(List<Movie> movies);
	
	public Movie getMovieById(Long id);
	
	public Movie getMovieByName(String name);

}
