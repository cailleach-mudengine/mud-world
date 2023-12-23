package com.cailleach.mudengine.world.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.*;

@Entity
@Table(name="MUD_PLACE")
@SequenceGenerator(name = "mud_place_seq", sequenceName="mud_place_seq", allocationSize=1)
@Data
public class PlaceEntity {
	
	@Id
	@GeneratedValue(generator="mud_place_seq", strategy=GenerationType.SEQUENCE)
	private Integer code;
	
	@Column(name="NAME")
	private String name;
	
	@Column(length = 500)
	private String description;

	@ManyToOne
	@JoinColumn(name="CLASS_CODE", referencedColumnName="CODE", nullable = false)
	private PlaceClassEntity placeClass;
	
	@OneToMany(mappedBy="pk.placeCode", fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	private Set<PlaceExitEntity> exits;
	
	@OneToMany(mappedBy="id.placeCode", fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	private Set<PlaceAttrEntity> attrs;
	
	
	public PlaceEntity() {
		this.exits = new HashSet<>();
		this.attrs = new HashSet<>();
	}
}
