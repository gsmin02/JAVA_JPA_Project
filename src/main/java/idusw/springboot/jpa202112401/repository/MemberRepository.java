package idusw.springboot.jpa202112401.repository;

import idusw.springboot.jpa202112401.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>,
        QuerydslPredicateExecutor<MemberEntity> {
    //JDBC 프로그래밍 순서
    //사용할 자원 선언(Connection 선언, Statement 선언, ResultSet 선언)
    //Connection 객체 선언 및 생성 - 연결 설정(application.properties)
    //Statement 생성(Statement, PreparedStatement, CallableStatement)
    //ResultSet 생성(entity - entities : records)
    //ResultSet -> DTO로 변환 후 변환 / 영향받은 record 수를 반환
    //List<DTO> - readList, DTO - readOne, int (row 수) - create, update, delete)

    @Query("select m from MemberEntity m where m.email = :email and m.pw = :pw")
    Object getMemberByEmail(@Param("email") String email, @Param("pw") String pw);

    @Query("SELECT COUNT(m) FROM MemberEntity m WHERE FUNCTION('DATE', m.regDate) = FUNCTION('DATE', :today)")
    long countUsersByRegDateToday(@Param("today") LocalDateTime today);
}
