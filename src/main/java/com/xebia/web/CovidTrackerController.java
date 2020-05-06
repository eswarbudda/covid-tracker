package com.xebia.web;


import com.xebia.dto.CovidDetailsDto;
import com.xebia.exception.ResourceNotFoundException;
import com.xebia.response.CovidResponse;
import com.xebia.response.ResponseMsg;
import com.xebia.service.CovidDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;


@Slf4j
@RestController
@RequestMapping
public class CovidTrackerController {

  @Autowired
  private CovidDetailsService covidDetailsService;

  @GetMapping("/details")
  public ResponseEntity<CovidResponse> searchByLocation(@RequestParam(name = "location", required = false) Set<String> locations) {
    log.info("searchByLocation  locations");
    CovidResponse covidResponse = covidDetailsService.getCovidDetails(locations);
    return new ResponseEntity<>(covidResponse, HttpStatus.OK);
  }

  @PostMapping("/coviddetails")
  public ResponseEntity<ResponseMsg> saveCovidDetails(@RequestParam("file") MultipartFile file) throws IOException {
    log.info("saveCovidDetails....");
    // validate file
    if (file.isEmpty()) {
      throw new ResourceNotFoundException("Invalid/empty input");
    } else {
        covidDetailsService.saveCovidDetails(file);
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setMsg("Save covid details is successful");
        responseMsg.setStatus(true);
        return new ResponseEntity<>(responseMsg, HttpStatus.CREATED);
      }
    }

  @PutMapping("/coviddetails")
  public ResponseEntity<ResponseMsg> updateCovidDetails(@RequestBody CovidDetailsDto covidDetailsDto) throws IOException {
    log.info("updateCovidDetails....");
    covidDetailsService.updateCovidDetails(covidDetailsDto);
    ResponseMsg responseMsg = new ResponseMsg();
    responseMsg.setMsg("Update covid details is successful");
    responseMsg.setStatus(true);
    return new ResponseEntity<>(responseMsg, HttpStatus.CREATED);
  }

}
