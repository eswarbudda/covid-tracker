package com.xebia.repository;

import com.xebia.entities.CovidDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CovidDetailsRepository extends CrudRepository<CovidDetails, Long> {

  String TOP_1_BY_LOCATION_NAME = "select id, location_name, tested, active, recovered, confirmed, dead, create_time, update_time from covid_details where location_name = :locationName order by id desc limit 1";

  List<CovidDetails> findByLocationName(String locationName);

  @Query(value = TOP_1_BY_LOCATION_NAME, nativeQuery = true)
  CovidDetails findTopByLocationName(@Param("locationName") String locationName);

}
