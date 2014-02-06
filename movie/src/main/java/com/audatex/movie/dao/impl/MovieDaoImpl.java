package com.audatex.movie.dao.impl;

import org.springframework.stereotype.Component;

import com.audatex.movie.dao.MovieDaoI;
import com.audatex.movie.domain.Movie;

@Component
public class MovieDaoImpl extends CommonDaoImpl<Movie, Long> implements MovieDaoI {

}
