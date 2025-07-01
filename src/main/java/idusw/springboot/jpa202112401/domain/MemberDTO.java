package idusw.springboot.jpa202112401.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDTO {   // DTO(Data Transfer Object) : Client <-> Controller <-> Service

    private Long mno;    //시퀀스 번호, 자동 증가하는 유일키
    private String id;
    private String pw;
    private String name;
    private String email;
    private String phone;
    private String address;

    private String mlevel;

    private String maccess;

}
