package idusw.springboot.jpa202112401.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long bno;
    private String title;
    private String content;

    private Long views;
    private String block; // 차단

    private Long writerMno;
    private String writerId;
    private String writerName;
    private String writerEmail;
    
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    
    private int replyCount; //게시글 댓글 수




}
