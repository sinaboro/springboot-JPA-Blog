package com.cos.blog.Handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value=IllegalArgumentException.class)
	public String handlerArgumentException(IllegalArgumentException e) {
		return "<h1>"+e.getMessage()+"</h1>";
	}
}
