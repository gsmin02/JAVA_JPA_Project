package idusw.springboot.jpa202112401.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private Long id;
    private String email;
    private String pw;
    private String fullname;
    private String address;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}