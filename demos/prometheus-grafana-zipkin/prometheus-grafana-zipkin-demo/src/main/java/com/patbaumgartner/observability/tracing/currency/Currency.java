package com.patbaumgartner.observability.tracing.currency;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Currency {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@EqualsAndHashCode.Include
	private Long id;

	private String name;

	private String symbol;

	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Country> countries = new ArrayList<>();

}
