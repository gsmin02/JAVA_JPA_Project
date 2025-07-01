package idusw.springboot.jpa202112401.repository;

import idusw.springboot.jpa202112401.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {

    BoardEntity searchBoard();

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
