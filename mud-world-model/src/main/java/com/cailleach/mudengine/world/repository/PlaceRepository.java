package com.cailleach.mudengine.world.repository;


import org.springframework.data.repository.CrudRepository;


import com.cailleach.mudengine.world.model.PlaceEntity;

public interface PlaceRepository extends CrudRepository<PlaceEntity, Long> {

	/**
	 * Just to AOP pointcut to be able to grab this
	 */
	@Override
	<S extends PlaceEntity> S save(S entity);

}
