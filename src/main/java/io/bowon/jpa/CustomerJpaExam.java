package io.bowon.jpa;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerJpaExam {
	private String id;
	private String name;
	private LocalDateTime registerDate;
	private LocalDateTime updateDate;
}
