package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Member {
	private int id;
	private String username, password, email;
}
