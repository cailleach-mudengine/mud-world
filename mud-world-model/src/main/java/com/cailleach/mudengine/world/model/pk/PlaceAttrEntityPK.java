package com.cailleach.mudengine.world.model.pk;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Embeddable
@Data
@EqualsAndHashCode
public class PlaceAttrEntityPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="PLACE_CODE")
	private Integer placeCode;

	@Column(name="CODE", length = 5)
	private String code;
}
