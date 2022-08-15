package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import lombok.experimental.PackagePrivate;




@RestController
public class DummyControllerTest {

	@Autowired
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public  String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			return "삭제에 실패하셨습니다. 해당 ID는 존재하지 않습니다.";
		}
		return "삭제되었습니다. id : " + id; 
	}
	//save함수는 id를 전달하지 않으면 insert을 해주고
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	//save함수는 id를 전달하면 해당 id에 데이터가 없으면 insert해준다.
	//email, password
	//Json데이터를 요청->Java Object(MessageConvter의 Jackson라이브러리가 변환해서 받아준다.)
	//@Transactional를 기입하면 save함수 호출하지 않아도 update가 실행된다.
	@Transactional   
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id : " + id);
		System.err.println("pw : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
//		userRepository.save(user);
		
		//더티 체킹
		return user;
	}
	
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	//한페이지당 2건에 데이타를 리턴받아 볼 예정
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC)Pageable pageable){
		Page<User> pageingUser =userRepository.findAll(pageable);
		List<User> users = pageingUser.getContent();
		return users;
		
	}
	@PostMapping("/dummy/join")
	public String join(User user) {
		userRepository.save(user);
		return "회원가입이 완료됬습니다.";
	}
	
	@GetMapping("dummy/user/{id}")
	//user/4을 찾으면 내가 데이타베이스에서 못찾아오게 되면 user가 null이 될 것 아냐?
	// 그럼 return null 이 리턴이 되잔아.. 그럼 프로그램에 문제가 될지 않겠니?
	//따라서 Optional로 너의 User객체를 감싸서 겨져올터니 null인지 아닌지 판단해서 return 해라..
	public User detail(@PathVariable int id) {
		User user=userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id :" + id);
			}
		});
		//스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
		//만약에 자바 오브젝트를 리턴하게 되면 MessageConveter가 Jackson라이브러리를 호출해서
		//user 오브젝트를 json으로 변환해서 브라우저에게 던져줍니다.
		return user;
	}
}
