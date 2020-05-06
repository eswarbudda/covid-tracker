package com.xebia.service;

import com.xebia.dto.CovidDetailsDto;
import com.xebia.response.CovidResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;


public interface CovidDetailsService {

  boolean saveCovidDetails(MultipartFile file) throws IOException;
  boolean updateCovidDetails(CovidDetailsDto covidDetailsDto);
  CovidResponse getCovidDetails(Set<String> locations);

}
