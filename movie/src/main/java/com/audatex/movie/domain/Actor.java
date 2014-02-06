package com.audatex.movie.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;


@SuppressWarnings("serial")
@Entity
@Table(name="Actor",
	   uniqueConstraints = @UniqueConstraint(columnNames = {"id", "NAME"}))
public class Actor extends BaseEntity {
	
	@Column(name = "NAME")
	@NotNull
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
