package es.ite.felizmente.user.model.entity;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Embeddable
public class Address {
	
	private String street;
	private int number;
	private String region;
	private int zipCode;
	private int country;

}
