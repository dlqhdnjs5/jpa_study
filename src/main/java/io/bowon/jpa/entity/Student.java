package io.bowon.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.bowon.jpa.customer.entity.Major;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "STUDENT_TB")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studentId;
	private String name;
	private String grade;


	@ManyToOne
	@JoinColumn(name = "majorId")
	private Major major;

	public Student(String name, String grade) {
		this.name = name;
		this.grade = grade;
	}
}
