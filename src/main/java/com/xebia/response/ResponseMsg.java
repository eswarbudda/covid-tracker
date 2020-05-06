package com.xebia.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMsg<T> {
  private boolean status;
  private String msg;
  private T data;
}
