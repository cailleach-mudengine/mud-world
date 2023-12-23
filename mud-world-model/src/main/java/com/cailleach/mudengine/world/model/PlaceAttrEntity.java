package com.cailleach.mudengine.world.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;

import com.cailleach.mudengine.world.model.pk.PlaceAttrEntityPK;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="MUD_PLACE_ATTR")
@Data
@EqualsAndHashCode(of= {"id"})
public class PlaceAttrEntity {

	@EmbeddedId
	private PlaceAttrEntityPK id;
	
	@Column(name="VALUE", nullable = false)
	@ColumnDefault(value = "0")
	private Integer value;
	
	@Transient
	public String getCode() {
		return id.getCode();
	}
}
