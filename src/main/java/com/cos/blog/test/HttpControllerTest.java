package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Delegate;

//사용자가 요청->응답(html파일)
//@Controller

//사용자가 요청-> 응답(data)
@RestController
public class HttpControllerTest {
	
	@GetMapping("/http/get")
	public String getTest(@RequestParam int id, @RequestParam String username) {
		return "get요청" + id + " : " + username;
	}
	
	@PostMapping("/http/post")
	public String postTest() {
		return "post요청";
	}
	
	@PutMapping("/http/put")
	public String putTest() {
		return "put요청";
	}
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete요청";
	}
}
