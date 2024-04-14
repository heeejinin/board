package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.DTO.AjaxDTO;


@Controller
public class AjaxController {
	 @GetMapping("/ex01")
	    public String ex01() { // 01은 반환 타입이 String 
	        System.out.println("AjaxController.ex01");
	        return "index"; // index.html에 작성된 내용이 출력됨
	    }
	 
	 @PostMapping("/ex02") 
	    public @ResponseBody String ex02() { // @ResponseBody => body는 그 http 메시지 http 프로토콜의 바디를 의미
	        System.out.println("AjaxController.ex02");
	        return "안녕하세요"; // 리턴값이 그대로 출력됨 //@ResponseBody의 역할은 리턴 값을 바디에 실어서 보내줌  
	    }
	 
	 @GetMapping("/ex03") //컨트롤러에서 가장 많이, 쉽게 활용하는 파라미터 전달 
	    public @ResponseBody String ex03(@RequestParam("param1") String param1, //ajax에서의 value 값이 String param1 변수에 담기게 됨
	                                     @RequestParam("param2") String param2) {
	        System.out.println("param1 = " + param1 + ", param2 = " + param2);
	        return "ex03메서드 호출 완료";
	    }
	 
	 @PostMapping("/ex04")
	    public @ResponseBody String ex04(@RequestParam("param1") String param1,
	                                     @RequestParam("param2") String param2) {
	        System.out.println("param1 = " + param1 + ", param2 = " + param2);
	        return "ex04메서드 호출 완료";
	    }
	//04를 params라는 매개변수로 한번에 받는 방법 
//	 @PostMapping("/ex04")
//	 public @ResponseBody String ex04(@RequestBody Map<String, String> params) {
//	     String param1 = params.get("param1");
//	     String param2 = params.get("param2");
//	     System.out.println("param1 = " + param1 + ", param2 = " + param2);
//	     return "ex04메서드 호출 완료";
//	 }
	 
	 @GetMapping("/ex05") // @ResponseBody로 별도로 파라미터를 받을 때는 RequestParam을 이용했었는데 게시판이나 회원가입을 생각하면 작성자, 내용 들을 따로 받아서 그걸 dto에다가 담는 과정이 필요한 건데 이제 model Attribute라는 어노테이션을 제공함으로써 dto로 곧바로 받을 수가 있었다. 그것을 위한 전제조건은 info태그에 입력 받은 데이터를 name 속성과 dto의 이름이 모두 정확하게 일치해야만 스프링이 이름에 맞춰서 값을 담아서 dto 객체로 만들어줬음 => 파라미터 이름만 맞으면 스프링이 많은 역할을 해준다. 여기서도 modelAttribute를 활용할 수 있음
	    public @ResponseBody AjaxDTO ex05(@ModelAttribute AjaxDTO ajaxDTO) { //필드 값이 모두 세팅이 된 AjaxDTO로 받아줄 수 있음 //사실 ModelAttribute는 생략 가능함 but 익숙치 않은 상황에서는 인지를 위해 그냥 붙이는 걸 추천함
	        System.out.println("ajaxDTO = " + ajaxDTO);
	        return ajaxDTO;
	    }
	 
	 @PostMapping("/ex06")
	    public @ResponseBody AjaxDTO ex06(@ModelAttribute AjaxDTO ajaxDTO) {
	        System.out.println("ajaxDTO = " + ajaxDTO);
	        return ajaxDTO;
	    }

	    @PostMapping("/ex07") //ex06과의 차이는 제이슨 형태로 보내는 방법이 가능하다는 것과 컨트롤러에서 받을 때 리퀘스트바디라는 어노테이션으로 받아줄 수 있다는 것 
	    public @ResponseBody AjaxDTO ex07(@RequestBody AjaxDTO ajaxDTO) {
	        System.out.println("ajaxDTO = " + ajaxDTO);
	        return ajaxDTO;
	    }

	    private List<AjaxDTO> DTOList() {
	        List<AjaxDTO> dtoList = new ArrayList<>();
	        dtoList.add(new AjaxDTO("data1", "data11")); //list의 index 0번째 목록 //param1값, param2값
	        dtoList.add(new AjaxDTO("data2", "data22")); //list의 index 1번째 목록
	        return dtoList;
	    }
  
	    @PostMapping("/ex08") //리스트로 뿌릴 것이기 때문에 반환 타입은 list로 
	    public @ResponseBody List<AjaxDTO> ex08(@RequestBody AjaxDTO ajaxDTO) {
	        System.out.println("ajaxDTO = " + ajaxDTO);
	        List<AjaxDTO> dtoList = DTOList();
	        dtoList.add(ajaxDTO); //list의 index 2번째 목록이 됨
	        return dtoList;
	    }

	    @PostMapping("/ex09") // ResponseEntity는 리스폰스바디처럼 바디의 입력값 자체를 전달해주는 것 + httpStatus(500,404,200...)에 대한 정보도 같이 줄 수 있음 => responseBody보다 더 포괄적인 리턴 방식
	    public ResponseEntity ex09(@RequestBody AjaxDTO ajaxDTO) { //ResponseEntity는 리턴타입을 따로 작성하지 않음
	        System.out.println("ajaxDTO = " + ajaxDTO);
//	        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 컨트롤러에서 어떤 처리냐에 따라 susccess로 보낼거냐 error로 보낼거냐를 제어할 수 있음
	        return new ResponseEntity<>(ajaxDTO, HttpStatus.OK); //리턴문이 다름 => ResponseEntity를 선언하고 httpStatus 코드를 줄 수 있음
	        //데이터를 리턴하지 않고 status 코드만 리턴하는 경우임 //요청에 대해서 처리를 했고 처리 결과 아무 이상이 없다 + 리턴해줄 데이터도 딱히 없는 경우에 status 코드로 ok만 리턴해주면 되고// 데이터와 status코드를 같이 보낼 수도 있음
	    }

	    @PostMapping("/ex10")
	    public ResponseEntity ex10(@RequestBody AjaxDTO ajaxDTO) {
	        System.out.println("ajaxDTO = " + ajaxDTO);
	        List<AjaxDTO> dtoList = DTOList(); //리스트와 status코드를 같이 반환하기 위해 list 선언
	        dtoList.add(ajaxDTO);
	        return new ResponseEntity<>(dtoList, HttpStatus.OK);
	    }

	 
}
