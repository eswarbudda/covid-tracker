package com.xebia.response;

import com.xebia.dto.CovidDetailsDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CovidResponse {

  private Map<String, CovidDetailsDto> covidDetailsDtoMap;

}
