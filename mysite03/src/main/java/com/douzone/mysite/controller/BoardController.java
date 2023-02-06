package com.douzone.mysite.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@RequestMapping("/delete")
	public String delete(HttpSession session, Long no, Model model) {
		////////////AC
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		//////////////
		boardService.deleteContents(no, authUser.getNo());
		
        model.addAttribute("msg","삭제 되었습니다.");
        model.addAttribute("url","/board/");
		return "tools/redirect";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(Model model, Long no , HttpSession session) {
		////////////AC
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		//////////////
		
		BoardVo vo = boardService.getContents(no, authUser.getNo());
		
		model.addAttribute("vo", vo);
		
		return "board/modify";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(BoardVo vo, HttpSession session) {
		////////////AC
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		//////////////
		
		boardService.updateContents(vo);
		
		return "redirect:/board/view?no="+vo.getNo();
	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(Model model, BoardVo vo, HttpSession session) {
		////////////AC
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		//////////////
		
		if(null != vo.getgNo()) {
			model.addAttribute("vo", vo);
		}
		
		return "board/write";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(HttpSession session, BoardVo vo) {
		////////////AC
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		//////////////
		
		vo.setUserVo((UserVo)session.getAttribute("authUser"));
		
		boardService.addContents(vo);
		
		return "redirect:/board/";
	}
}
