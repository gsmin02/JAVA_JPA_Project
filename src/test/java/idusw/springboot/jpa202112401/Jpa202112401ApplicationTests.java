package idusw.springboot.jpa202112401;

import idusw.springboot.jpa202112401.entity.BoardEntity;
import idusw.springboot.jpa202112401.entity.MemberEntity;
import idusw.springboot.jpa202112401.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
class Jpa202112401ApplicationTests {
	@Autowired
	MemberRepository memberRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testAdmin() {
		// Integer 데이터 흐름, Lambda 식 - 함수형 언어의 특징을 활용
		String str = "root";
		MemberEntity entity = MemberEntity.builder()
				.id(str)
				.pw(str)
				.name("name-" + str )
				.email(str + "@induk.ac.kr")
				.phone("phone-" + new Random().nextInt(50))
				.address("address-" + new Random().nextInt(50))
				.maccess("access")
				.build();
		memberRepository.save(entity);
	}

	@Test
	void testCreateUserAndBoard() {
		int count = 100;
		String str = "user";
		String board = "board";
		for(int i = 1; i <= count; i++) {
			MemberEntity entity = MemberEntity.builder()
					.id(str+i)
					.pw(str+i)
					.name("name-" + str+i)
					.email(str+i+"@induk.ac.kr")
					.phone("phone-" +i)
					.address("address-"+i)
					.maccess("access")
					.build();
			memberRepository.save(entity);
		}
		for(int i = 1; i <= count; i++) {
			BoardEntity boardEntity = BoardEntity.builder()
					.bno((long) i)
					.title(str+i+board)
					.content("content-" + str+i+board)
					.build();
		}

	}

}
