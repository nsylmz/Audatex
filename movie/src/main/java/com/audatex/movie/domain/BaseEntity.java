package com.audatex.movie.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;


@SuppressWarnings("serial")
@MappedSuperclass
public class BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;

	@Version
	@Column(name = "VERSION")
    private Integer version;

	@NotNull
	@Column(name = "TRANSACTION_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || this == null || this.id == null) {
			return super.equals(obj);
		}
		return this.id.equals(((BaseEntity) obj).getId());
	}

}
