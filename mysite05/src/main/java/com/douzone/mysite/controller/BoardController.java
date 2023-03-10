package com.douzone.mysite.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.security.AuthUser;
import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/")
	public String index(Model model,
			@RequestParam(value="no", required=true, defaultValue="1") int pageNo,
			@RequestParam(value="keyword", required=true, defaultValue="") String keyword,
			@RequestParam(value="row", required=true, defaultValue="5") int listSize) {
		Map<String, Object> map = boardService.getContentsList(pageNo, keyword, listSize);
		
		model.addAllAttributes(map);
		
		return "board/list";
	}
	
	@RequestMapping("/view")
	public String view(HttpServletRequest request, HttpServletResponse response, 
			Model model, Long no, 
			int pageno, String keyword, int listsize) {
		BoardVo vo = boardService.getContents(no);
		model.addAttribute("vo", vo);
		model.addAttribute("pageNo", pageno);
		model.addAttribute("keyword", keyword);
		model.addAttribute("listSize", listsize);
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			boolean check = false;
			for (Cookie cookie : cookies) {
				if (request.getParameter("no").equals(cookie.getName())) {
					check = true;
				}
			}
			if(!check) {
				boardService.hitsContents(no);
				// 쿠키 쓰기
				Cookie cookie = new Cookie(request.getParameter("no"), String.valueOf(1));
				cookie.setPath(request.getContextPath());
				cookie.setMaxAge(24 * 60 * 60); // 1day
				response.addCookie(cookie);
			}
		}
		
		return "board/view";
	}
	
	@Auth
	@RequestMapping("/delete")
	public String delete(@AuthUser UserVo authUser, Long no, Model model) {
		boardService.deleteContents(no, authUser.getNo());
		
        model.addAttribute("msg","삭제 되었습니다.");
        model.addAttribute("url","/board/");
		return "tools/redirect";
	}
	
	@Auth
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(@AuthUser UserVo authUser, Model model, Long no) {
		BoardVo vo = boardService.getContents(no, authUser.getNo());
		
		model.addAttribute("vo", vo);
		
		return "board/modify";
	}
	
	@Auth
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(BoardVo vo) {
		boardService.updateContents(vo);
		
		return "redirect:/board/view?no="+vo.getNo();
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(Model model, BoardVo vo) {
		if(null != vo.getgNo()) {
			model.addAttribute("vo", vo);
		}
		
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(@AuthUser UserVo authUser, BoardVo vo) {
		vo.setUserVo(authUser);
		
		boardService.addContents(vo);
		
		return "redirect:/board/";
	}
}
