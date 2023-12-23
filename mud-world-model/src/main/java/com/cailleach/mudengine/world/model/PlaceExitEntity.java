package com.cailleach.mudengine.world.model;

import jakarta.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import com.cailleach.mudengine.world.model.pk.PlaceExitEntityPK;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="MUD_PLACE_EXIT")
@Data
@EqualsAndHashCode(of="pk")
public class PlaceExitEntity {

	@EmbeddedId
	private PlaceExitEntityPK pk;

	@Column
	@ColumnDefault(value = "true")	
	private boolean opened;
	
	@Column
	@ColumnDefault(value = "true")	
	private boolean visible;
	
	@Column
	@ColumnDefault(value = "false")
	private boolean locked;
	
	@Column	
	@ColumnDefault(value = "false")	
	private boolean lockable;

	@Column(name="TARGET_PLACE_CODE", nullable = false)
	private Integer targetPlaceCode;
	
	@Transient
	public String getDirection() {
		return pk.getDirection();
	}
}
