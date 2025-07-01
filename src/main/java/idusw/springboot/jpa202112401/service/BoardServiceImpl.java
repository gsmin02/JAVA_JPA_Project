package idusw.springboot.jpa202112401.service;

import idusw.springboot.jpa202112401.domain.BoardDTO;
import idusw.springboot.jpa202112401.domain.PageRequestDTO;
import idusw.springboot.jpa202112401.domain.PageResultDTO;
import idusw.springboot.jpa202112401.entity.BoardEntity;
import idusw.springboot.jpa202112401.entity.MemberEntity;
import idusw.springboot.jpa202112401.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    @Override
    public Long register(BoardDTO dto) {   //Controller -> DTO 객체 -> Service
        //log.info(dto);
        System.out.println(dto);
        BoardEntity boardEntity = dtoToEntity(dto);
        System.out.println(boardEntity);
        boardRepository.save(boardEntity);
        return boardEntity.getBno();    //게시물 번호
    }

    @Override
    public void delete(BoardDTO board) {
        BoardEntity entity = dtoToEntity(board);
        boardRepository.deleteById(entity.getBno());
    }

    @Transactional
    @Override
    public int updateView(Long bno) {
        return boardRepository.updateView(bno);
    }

    @Override
    public Long allBoardsCount() {
        return boardRepository.count();
    }

    @Override
    public Long newBoardsCount() {
        return boardRepository.countBoardsByRegDateToday(LocalDateTime.now());
    }


    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info(">>>>" + pageRequestDTO);

        Function<Object[], BoardDTO> fn = (entity -> entityToDto((BoardEntity) entity[0],
                (MemberEntity) entity[1],(Long) entity[2]));
        String type = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = null;
        String page = pageRequestDTO.getSort();
        String asc = "asc";
        pageable = pageRequestDTO.getPageable(Sort.by("views").descending());
        if(page != null) {
            if(page.equals(asc)) {
                pageable = pageRequestDTO.getPageable(Sort.by("views").ascending());
            }
        }

        Page<Object[]> result = boardRepository.searchPage(type, keyword, pageable);

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO getById(Long bno) {
        Object result = boardRepository.getBoardByBno(bno); //기본 CRUD는 JpaRepository 가 제공
        Object[] en = (Object[]) result;
        return entityToDto((BoardEntity) en[0], (MemberEntity) en[1], (Long) en[2]);
    }

    @Override
    public Long modify(BoardDTO dto) {
        return null;
    }


    @Override
    public void update(BoardDTO board) {
        BoardEntity entity = dtoToEntity(board);
        boardRepository.save(entity);
    }

}
