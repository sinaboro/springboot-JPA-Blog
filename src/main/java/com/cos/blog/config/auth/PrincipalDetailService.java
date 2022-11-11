package com.cos.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.repository.UserRepository;
import com.cos.blog.model.User;

@Service
public class PrincipalDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	//스프링이 로그인 요청을 가로챌 때, username, password 변수2 개를 가로채는데
	// password 부분 철는 알아서함.
	//username이 db있는지만 확인해 주면된다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username)
				.orElseThrow(()->{
					return new UsernameNotFoundException("해당 사용자를 찾을수 없습니다." + username);
				});
		return new PrincipalDetail(principal);  //시큐리티의 세션에 유저정보가 저장이 됨.
	}
	
	
}
