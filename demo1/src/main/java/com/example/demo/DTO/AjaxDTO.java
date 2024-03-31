package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor //모든 필드를 매개변수로 하는 그런 생성자
public class AjaxDTO {
    private String param1;
    private String param2;
}