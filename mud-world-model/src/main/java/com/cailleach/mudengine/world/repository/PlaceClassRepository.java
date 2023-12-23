package com.cailleach.mudengine.world.repository;

import org.springframework.data.repository.CrudRepository;

import com.cailleach.mudengine.world.model.MudPlaceClass;

public interface PlaceClassRepository extends CrudRepository<MudPlaceClass, String> {

}
