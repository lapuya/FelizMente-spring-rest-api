package es.ite.felizmente.user.model.entity;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Embeddable
public class Address {
	
	private String street;
	private String number;
	private String region;
	private String zipCode;
	private String country;

}
