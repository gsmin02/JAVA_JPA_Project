package idusw.springboot.jpa202112401.service;

import idusw.springboot.jpa202112401.domain.MemberDTO;
import idusw.springboot.jpa202112401.domain.PageRequestDTO;
import idusw.springboot.jpa202112401.domain.PageResultDTO;
import idusw.springboot.jpa202112401.entity.MemberEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {

    void deleteWithBoards(Long mno);

    void create(MemberDTO member);

    MemberDTO readById(Long mno);

    List<MemberDTO> readAll();

    PageResultDTO<MemberDTO, MemberEntity> readListBy(PageRequestDTO pageRequestDTO);

    void update(MemberDTO member);

    void delete(MemberDTO member);

    MemberDTO readByName(MemberDTO member);

    MemberDTO readByEmail(String email);

    MemberDTO loginByEmail(MemberDTO dto);

    Long allUserCount();
    Long newUserCount();
}
