package com.cailleach.mudengine.world.model.pk;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Embeddable
@Data
@EqualsAndHashCode
public class PlaceExitEntityPK implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer placeCode;
	
	@Column(length = 10)
	private String direction;
	
}
