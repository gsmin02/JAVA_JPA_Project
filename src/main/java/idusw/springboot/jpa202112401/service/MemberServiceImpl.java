package idusw.springboot.jpa202112401.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import idusw.springboot.jpa202112401.domain.MemberDTO;
import idusw.springboot.jpa202112401.domain.PageRequestDTO;
import idusw.springboot.jpa202112401.domain.PageResultDTO;
import idusw.springboot.jpa202112401.entity.BoardEntity;
import idusw.springboot.jpa202112401.entity.MemberEntity;
import idusw.springboot.jpa202112401.entity.QMemberEntity;
import idusw.springboot.jpa202112401.repository.BoardRepository;
import idusw.springboot.jpa202112401.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

// Component-Scan : @Component, @Configuration, @Controller, @Service, @Repository, @Beans
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final BoardRepository boardRepository;

    @Override
    public void create(MemberDTO member) {

        MemberEntity entity = dtoToEntity(member);
        //new MemberEntity(mno, id, pw, name, email, phone, address,);
        memberRepository.save(entity);
    }

    private MemberEntity dtoToEntity(MemberDTO member) {
        MemberEntity entity = MemberEntity.builder()
                .mno(member.getMno())
                .id(member.getId())
                .pw(member.getPw())
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .maccess(member.getMaccess())
                .address(member.getAddress())
                .mlevel(member.getMlevel())
                .build();
        return entity;
    }

    // Service -> Controller : entity -> DTO로 변환 후 반환
    public MemberDTO entityToDto(MemberEntity entity) {

        MemberDTO member = MemberDTO.builder()
                .mno(entity.getMno())
                .id(entity.getId())
                .pw(entity.getPw())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .maccess(entity.getMaccess())
                .address(entity.getAddress())
                .mlevel(entity.getMlevel())
                .build();
        return member;
    }

    @Override
    public MemberDTO readById(Long mno) {
        MemberDTO member = null;
        Optional<MemberEntity> result = memberRepository.findById(mno);
        if (result.isPresent()) {
            member = entityToDto(result.get());
        }
        return member;
    }



    @Override
    public List<MemberDTO> readAll() {

        List<MemberDTO> members = new ArrayList<>();   //반환 리스트 객체

        List<MemberEntity> entities = memberRepository.findAll();   //entity들
        for (MemberEntity entity : entities) {
            MemberDTO member = entityToDto(entity);
            members.add(member);
        }
        return members;
    }

    @Override
    public PageResultDTO<MemberDTO, MemberEntity> readListBy(PageRequestDTO pageRequestDTO) {

        // 정렬부분입니다
        Sort sort = Sort.by("mno").descending();
        if(pageRequestDTO.getSort() == null)
            Sort.by("mno").descending();
        else if(pageRequestDTO.getSort().equals("asc"))
            sort = Sort.by("mno").ascending();

        Pageable pageable = pageRequestDTO.getPageable(sort);
        BooleanBuilder booleanBuilder = findByCondition(pageRequestDTO);

        Page<MemberEntity> result = memberRepository.findAll(booleanBuilder, pageable);
        //Page<MemberEntity> result = memberRepository.findAll(pageable);
        Function<MemberEntity, MemberDTO> fn = (entity -> entityToDto(entity));
        return new PageResultDTO<>(result, fn);
    }

    private BooleanBuilder findByCondition(PageRequestDTO pageRequestDTO) {

        String type = pageRequestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QMemberEntity qMemberEntity = QMemberEntity.memberEntity;

        BooleanExpression expression = qMemberEntity.mno.gt(0L);    //where mno > 0 and title == "title"
        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        String keyword = pageRequestDTO.getKeyword();

        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if(type.contains("e")){// email 로 검색
            conditionBuilder.or(qMemberEntity.email.contains(keyword));
        }
        if(type.contains("p")){// phone 로 검색
            conditionBuilder.or(qMemberEntity.phone.contains(keyword));
        }
        if(type.contains("a")){// address 로 검색
            conditionBuilder.or(qMemberEntity.address.contains(keyword));
        }
        if(type.contains("l")){// address 로 검색
            conditionBuilder.or(qMemberEntity.mlevel.contains(keyword));
        }
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;  //완성된 조건 or 술어
    }

    @Override
    public void update(MemberDTO member) {
        MemberEntity entity = dtoToEntity(member);
        memberRepository.save(entity);
    }

    @Override
    public void delete(MemberDTO member) {
        MemberEntity entity = dtoToEntity(member);
        memberRepository.deleteById(entity.getMno());
    }

    @Override
    public MemberDTO readByName(MemberDTO member) {
        return null;
    }

    @Override
    public MemberDTO readByEmail(String email) {
        return null;
    }

    @Override
    public MemberDTO loginByEmail(MemberDTO member) {
        MemberDTO memberDTO = null;
        Object result = memberRepository.getMemberByEmail(member.getEmail(), member.getPw());
        if (result != null) {
            memberDTO = entityToDto((MemberEntity) result);
        }
        return memberDTO;
    }

    @Override
    public Long allUserCount() {
        return memberRepository.count();
    }

    @Override
    public Long newUserCount() {
        return memberRepository.countUsersByRegDateToday(LocalDateTime.now());
    }

    @Override
    public void deleteWithBoards(Long mno) {
        MemberEntity memberEntity = memberRepository.getById(mno);
        List<BoardEntity> boardEntities = memberEntity.getBoardEntityList();
        Iterator b = boardEntities.iterator();
        while (b.hasNext()) {
            Object next = b.next();
            boardRepository.delete((BoardEntity) next);
        }

    }


}
