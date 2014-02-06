package com.audatex.movie.dao.impl;

import org.springframework.stereotype.Component;

import com.audatex.movie.dao.ActorDaoI;
import com.audatex.movie.domain.Actor;

@Component
public class ActorDaoImpl extends CommonDaoImpl<Actor, Long> implements ActorDaoI {

}
