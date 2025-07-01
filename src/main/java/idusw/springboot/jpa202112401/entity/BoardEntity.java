package idusw.springboot.jpa202112401.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "b_202112401")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
//@ToString(exclude = "writer")
public class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno; // 유일키

    private String title; // 제목
    private String content; // 내용
    private Long views; // 조회수
    private String block; // 차단여부

    @ManyToOne
    //@JoinColumn(name="email")
    private MemberEntity writer;  //연관 관계 지정 : 작성자 1명 - 게시물 다수
}
