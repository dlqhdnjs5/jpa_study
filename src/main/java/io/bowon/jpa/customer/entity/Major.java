package io.bowon.jpa.customer.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "MAJOR_TB")
public class Major {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long majorId;
	private String name;
	private String category;


	public Major(String name, String category) {
		this.name = name;
		this.category = category;
	}

	public Major() {

	}
}
