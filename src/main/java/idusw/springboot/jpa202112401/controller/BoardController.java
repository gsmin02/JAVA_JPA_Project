package idusw.springboot.jpa202112401.controller;

import idusw.springboot.jpa202112401.domain.BoardDTO;
import idusw.springboot.jpa202112401.domain.PageRequestDTO;
import idusw.springboot.jpa202112401.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    //생성자 주입 : Spring Framework 가 해줌 <- Autowired (필드 주입)
    private final BoardService boardService;

    @GetMapping("/regform")
    public String getRegform(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("dto", BoardDTO.builder().build()); //빈 Board객체 생성
        return "/boards/regform";   //boards/regform.html 전달
    }

    @PostMapping("")
    public String post(@ModelAttribute("dto") BoardDTO dto, Model model) {
        System.out.println(dto.getTitle() + ":" + dto.getWriterMno());
        Long bno = boardService.register(dto);
        //model.addAttribute();
        return "redirect:/boards/" + bno;  //등록 후 상세보기
    }

    //list
    @GetMapping("")
    public String getList(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("boards", boardService.getList(pageRequestDTO));
        return "/boards/list";
    }

    //조회
    @GetMapping("/{bno}")
    public String getBoard(@PathVariable("bno") Long bno, Model model) {

        BoardDTO board = boardService.getById(bno);
        boardService.updateView(bno);
        model.addAttribute("dto", board);

        return "/boards/read";
    }

    //업데이트 폼으로
    @GetMapping("/{bno}/upform")
    public String getUpform(@PathVariable("bno") Long bno, Model model) {
        BoardDTO board = boardService.getById(bno);
        model.addAttribute("board", board);
        return "/boards/upform";   //view resolving : upform.html
    }

    //Update
    @PutMapping("/{bno}")
    public String putMember(@ModelAttribute("board") BoardDTO board, Model model) {
        //html에서 model 객체를 전달받음 : member (애트리뷰트 명으로 접근, th:object 애트리뷰트 값)
        boardService.update(board);
        model.addAttribute("board", board);
        //return "/members/member";   //view resolving : updated info 확인
        return "redirect:/boards";
    }

    @GetMapping("/{bno}/delform")
    public String getDelform(@PathVariable("bno") Long bno, Model model) {
        BoardDTO board = boardService.getById(bno);
        model.addAttribute("board",board);
        return "/boards/delform";   //view resolving : upform.html
    }

    //Delete
    @DeleteMapping("/{bno}")
    public String deleteMember(@ModelAttribute("board") BoardDTO board, Model model) {
        //html에서 model 객체를 전달받음 : member (애트리뷰트 명으로 접근, th:object 애트리뷰트 값)
        boardService.delete(board);
        model.addAttribute(board);
        return "redirect:/boards";   // '/members' 요청을 함, view resolving 대신
    }

}
