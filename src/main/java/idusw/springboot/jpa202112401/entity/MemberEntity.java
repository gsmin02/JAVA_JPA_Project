package idusw.springboot.jpa202112401.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

//Spring Data 관련 Annotation
@Entity //Spring Data JPA의 엔티티(Entity, 개체)임을 나타냄.
@Table(name = "m_202112401")
//Lombok 관련 Annotation
@ToString   //toString() 생성
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor //모든 매개 변수를 갖는 생성자
@NoArgsConstructor  //Default 생성자(매개 변수X)
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_member_mno_gen")
    //@SequenceGenerator(sequenceName = "t_member_mno", name = "t_member_mno_gen", allocationSize = 1)
    private Long mno;

    @Column(length = 30, nullable = false)
    private String id;
    @Column(length = 30, nullable = false)
    private String pw;
    @Column(length = 30, nullable = false)
    private String name;
    @Column(length = 50, nullable = false)
    private String email;
    @Column(length = 100)
    private String phone;
    @Column(length = 100)
    private String address;
    private String mlevel;

    @OneToMany(mappedBy = "writer")
    private List<BoardEntity> boardEntityList;

    private String maccess;
}
