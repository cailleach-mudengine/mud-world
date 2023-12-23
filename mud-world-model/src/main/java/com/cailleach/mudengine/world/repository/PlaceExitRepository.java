package com.cailleach.mudengine.world.repository;

import org.springframework.data.repository.CrudRepository;

import com.cailleach.mudengine.world.model.PlaceExitEntity;
import com.cailleach.mudengine.world.model.pk.PlaceExitEntityPK;

public interface PlaceExitRepository extends CrudRepository<PlaceExitEntity, PlaceExitEntityPK> {

	public Iterable<PlaceExitEntity> findByTargetPlaceCode(Integer targetPlaceCode);
}
