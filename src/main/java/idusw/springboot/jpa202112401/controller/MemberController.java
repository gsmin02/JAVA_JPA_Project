package idusw.springboot.jpa202112401.controller;

import idusw.springboot.jpa202112401.domain.MemberDTO;
import idusw.springboot.jpa202112401.domain.PageRequestDTO;
import idusw.springboot.jpa202112401.service.BoardService;
import idusw.springboot.jpa202112401.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final BoardService boardService;

    @GetMapping("/login")
    public String getLoginform(Model model) {
        model.addAttribute("member", MemberDTO.builder().build());
        return "/members/login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("member") MemberDTO member, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        MemberDTO dto = null;

        if ((dto = memberService.loginByEmail(member)) != null) {
            HttpSession session = request.getSession();
            session.setAttribute("login", dto);
            session.setAttribute("mno", dto.getMno());
            session.setAttribute("access",dto.getMaccess());

            if (dto.getMaccess().contains("unaccess")) {
                response.setContentType("text/html; charset=euc-kr");
                PrintWriter out = response.getWriter();
                out.println("<script>alert('차단당한 사용자입니다'); </script>");
                out.flush();
                return "/members/login";
            }
            if (dto.getId().contains("root")) {
                session.setAttribute("isadmin", dto.getId());
                System.out.println(session.getAttribute("isadmin"));
            }
            return "redirect:/";
        }
        else {
            response.setContentType("text/html; charset=euc-kr");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('아이디 또는 비밀번호 오류'); </script>");
            out.flush();
            return "/members/loginfail";
        }
    }

    @GetMapping("/logout")
    public String getLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/regform")
    public String getRegform(Model model) {

        //정보를 전달받을 객체를 보냄
        model.addAttribute("member", MemberDTO.builder().build());
        return "/members/regform";
    }

    //get
    @GetMapping("/{idx}")
    public String getMember(@PathVariable("idx") Long mno, PageRequestDTO pageRequestDTO, Model model) {
        MemberDTO member = memberService.readById(mno);
        model.addAttribute("member", member);
        model.addAttribute("members",memberService.readListBy(pageRequestDTO));
        return "/members/contacts";
    }

    @GetMapping("/{idx}/upform")
    public String getUpform(@PathVariable("idx") Long mno, Model model) {
        MemberDTO member = memberService.readById(mno);
        model.addAttribute("member", member);
        return "/members/upform";   //view resolving : upform.html
    }

    @GetMapping("/{idx}/delform")
    public String getDelform(@PathVariable("idx") Long mno, Model model) {
        MemberDTO member = memberService.readById(mno);
        model.addAttribute("member",member);
        return "/members/delform";   //view resolving : upform.html
    }

    //Update
    @PutMapping("/{idx}")
    public String putMember(@ModelAttribute("member") MemberDTO member, Model model) {
        //html에서 model 객체를 전달받음 : member (애트리뷰트 명으로 접근, th:object 애트리뷰트 값)
        memberService.update(member);
        model.addAttribute("member", member);
        //return "/members/member";   //view resolving : updated info 확인
        return "redirect:/";
    }

    //Delete
    @DeleteMapping("/{idx}")
    public String deleteMember(@ModelAttribute("member") MemberDTO member, Model model
            , HttpSession session, Long mno) {
        //html에서 model 객체를 전달받음 : member (애트리뷰트 명으로 접근, th:object 애트리뷰트 값)

        memberService.deleteWithBoards(memberService.readById(mno).getMno());
        memberService.delete(member);
        model.addAttribute(member);

        session.invalidate();
        return "redirect:/";   // '/members' 요청을 함, view resolving 대신
    }


    //List
    @GetMapping("")
    public String getMembers(PageRequestDTO pageRequestDTO, Model model){
        //정보를 전달받을 빈(empty) 객체를 보냄
        //List<Member> members = memberService.readAll();
        model.addAttribute("members",memberService.readListBy(pageRequestDTO));
        //return "/pages/tables/simple";
        return "/members/members";   //view resolving : members.html
    }

    //Create
    @PostMapping("")
    public String postMember(@ModelAttribute("member") MemberDTO member, Model model) {
        memberService.create(member);
        model.addAttribute("member", member); //입력한 객체를 전달, DB로 부터 가져온 것 아님
        return "redirect:/";
    }

    @GetMapping("/")
    public String getSbAdmin2() {
        return "index";
    }

}
