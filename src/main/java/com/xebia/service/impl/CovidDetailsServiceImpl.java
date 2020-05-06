package com.xebia.service.impl;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.xebia.dto.CovidDetailsDto;
import com.xebia.entities.CovidDetails;
import com.xebia.exception.ResourceNotFoundException;
import com.xebia.repository.CovidDetailsRepository;
import com.xebia.response.CovidResponse;
import com.xebia.service.CovidDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service("covidDetailsService")
public class CovidDetailsServiceImpl implements CovidDetailsService {

  @Autowired
  private CovidDetailsRepository covidDetailsRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public boolean saveCovidDetails(MultipartFile file) throws IOException {
    // parse CSV file to create a list of `CovidDetails` objects
    try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

      // create csv bean reader
      CsvToBean<CovidDetailsDto> csvToBean = new CsvToBeanBuilder(reader)
          .withType(CovidDetailsDto.class)
          .withIgnoreLeadingWhiteSpace(true)
          .build();

      List<CovidDetailsDto> covidDetailsDtos = csvToBean.parse();
      log.info("Covid records size:{}", covidDetailsDtos.size());

      List<CovidDetails> covidDetails = new ArrayList<>();

      for(CovidDetailsDto covidDetailsDto : covidDetailsDtos) {
        covidDetails.add(mapToCovidDetails(covidDetailsDto));
      }

      covidDetailsRepository.saveAll(covidDetails);
      return true;
    }

  }

  @Override
  public boolean updateCovidDetails(CovidDetailsDto covidDetailsDto) {
    log.info("updateCovidDetails for location:{}", covidDetailsDto.getLocation());
    CovidDetails dbCovidDetails = covidDetailsRepository.findTopByLocationName(covidDetailsDto.getLocation());
    CovidDetails covidDetails = mapToCovidDetails(covidDetailsDto);
    if (Objects.nonNull(dbCovidDetails)) {
      covidDetails.setActive(dbCovidDetails.getActive() + covidDetails.getActive());
      covidDetails.setTested(dbCovidDetails.getTested() + covidDetails.getTested());
      covidDetails.setRecovered(dbCovidDetails.getRecovered() + covidDetails.getRecovered());
      covidDetails.setConfirmed(dbCovidDetails.getConfirmed() + covidDetails.getConfirmed());
      covidDetails.setDead(dbCovidDetails.getActive() + covidDetails.getDead());
      LocalDateTime now = LocalDateTime.now();
      covidDetails.setUpdateTime(now);
      covidDetails.setCreateTime(now);
      covidDetailsRepository.save(covidDetails);
    } else {
      throw new ResourceNotFoundException("Invalid location:" + covidDetailsDto.getLocation());
    }
    log.info("incremental update is successful");
    return true;
  }

  @Override
  public CovidResponse getCovidDetails(Set<String> locations) {
    log.info("searchByLocation  locations count:{}", Objects.nonNull(locations) ? locations.size() : 0);

    List<CovidDetails> covidDetails = new ArrayList<>();
    if (Objects.isNull(locations) || locations.isEmpty()) {
      covidDetails = (List<CovidDetails>) covidDetailsRepository.findAll();
    } else {
      for(String locationName : locations) {
        CovidDetails dbCovidDetails = covidDetailsRepository.findTopByLocationName(locationName.toLowerCase());

        if (Objects.nonNull(dbCovidDetails)) {
          covidDetails.add(dbCovidDetails);
        }
      }
    }

    for(CovidDetails covidDetail : covidDetails) {
      if( Objects.isNull(covidDetail.getUpdateTime())) {
        covidDetail.setUpdateTime(covidDetail.getCreateTime());
      }
    }


    Map<String, CovidDetailsDto> covidDetailsDtoMap = new HashMap<>();

    Collections.sort(covidDetails, Comparator.comparing(CovidDetails::getId).reversed());

    for(CovidDetails covidDetails1 : covidDetails ) {
      covidDetailsDtoMap.putIfAbsent(covidDetails1.getLocationName(), modelMapper.map(covidDetails1, CovidDetailsDto.class));
    }

    CovidResponse covidResponse = new CovidResponse();
    covidResponse.setCovidDetailsDtoMap(covidDetailsDtoMap);

    return covidResponse;
  }

  private CovidDetails mapToCovidDetails(CovidDetailsDto covidDetailsDto) {
    LocalDateTime now = LocalDateTime.now();
    return CovidDetails.builder()
        .active(Objects.nonNull(covidDetailsDto.getActive()) ? covidDetailsDto.getActive() : 0)
        .confirmed(Objects.nonNull(covidDetailsDto.getConfirmed()) ? covidDetailsDto.getConfirmed() : 0)
        .dead(Objects.nonNull(covidDetailsDto.getDead()) ? covidDetailsDto.getDead() : 0)
        .recovered(Objects.nonNull(covidDetailsDto.getRecovered()) ? covidDetailsDto.getRecovered() : 0)
        .tested(Objects.nonNull(covidDetailsDto.getTested()) ? covidDetailsDto.getTested() : 0)
        .locationName(covidDetailsDto.getLocation().toLowerCase())
        .createTime(now)
        .updateTime(now)
        .build();
  }

}


