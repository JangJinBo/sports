package com.itbank.controller;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itbank.model.AdminBoardDTO;
import com.itbank.model.MemberDTO;
import com.itbank.model.MessageDTO;
import com.itbank.model.PageMakeDTO;
import com.itbank.model.InquiryDTO;
import com.itbank.service.CSCService;

@Controller
public class CSCController {

	@Autowired
	CSCService cscService;

	@GetMapping("/csc/notice")
	public ModelAndView notice(@RequestParam(required = false, defaultValue = "1") int pageNum, String keyword,
			String searchType) {
		ModelAndView mav = new ModelAndView();
		List<AdminBoardDTO> noticeList = cscService.selectNoticeList();
		int listSize = noticeList.size();
		List<AdminBoardDTO> list = new ArrayList<AdminBoardDTO>();
		if (keyword == null) {
			keyword = "";
		}
		if (searchType == null) {
			searchType = "title";
		}
		if (searchType.equals("title")) {
			for (int i = 0; i < listSize; i++) {
				if (noticeList.get(i).getTitle().contains(keyword)) {
					list.add(noticeList.get(i));
				}
			}
		}
		if (searchType.equals("content")) {
			for (int i = 0; i < listSize; i++) {
				if (noticeList.get(i).getContent().contains(keyword)) {
					list.add(noticeList.get(i));
				}
			}
		}
		if (searchType.equals("all")) {
			for (int i = 0; i < listSize; i++) {
				if (noticeList.get(i).getTitle().contains(keyword)
						|| noticeList.get(i).getContent().contains(keyword)) {
					list.add(noticeList.get(i));
				}
			}
		}

		int total = list.size();
		PageMakeDTO pageMake = new PageMakeDTO(pageNum, total);
		mav.addObject("pageMake", pageMake);

		List<AdminBoardDTO> searchList = new ArrayList<AdminBoardDTO>();
		for (int i = pageMake.getStartRow(); i <= pageMake.getEndRow(); i++) {
			searchList.add(noticeList.get(i));
		}
		mav.addObject("keyword", keyword);
		mav.addObject("searchType", searchType);
		mav.addObject("noticeList", searchList);
		return mav;
	}

	@GetMapping("/csc/faq")
	public ModelAndView faq() {
		ModelAndView mav = new ModelAndView();
		List<AdminBoardDTO> faqList = cscService.selectFaqList();
		mav.addObject("faqList", faqList);
		return mav;
	}

	@GetMapping("/csc/inquiry")
	public ModelAndView inquiry(HttpSession session) {
		MemberDTO login = (MemberDTO) session.getAttribute("login");
		ModelAndView mav = new ModelAndView("/csc/inquiry");
		
		// 오늘 날짜를 생성하고 today에 sdf를 이용해 저장한다.
		Date aday = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(aday);
		
		// today를 이용해 1년 전의 날짜를 구한다.
		String year = today.substring(0, 4);
		String lastYear = (Integer.parseInt(year) - 1) + today.substring(4);
		String sendId = login.getUserid();
		
		// 리스트에서 작성일이 1년이 넘은 데이터는 안보이게 하는 속성을가지게 한다.
		List<InquiryDTO> inquiryList = cscService.selectInquiryList(sendId);
		inquiryList.forEach(e -> {
			e.setDisplayBlock(lastYear.compareTo(e.getWdate().toString()) < 0);
		});
		mav.addObject("inquiryList", inquiryList);
		return mav;
	}

	@GetMapping("/csc/cscInquiryWrite")
	public void cscInquiryWrite() {
	}

	@PostMapping("/csc/cscInquiryWrite")
	public String cscInquiryWrite(InquiryDTO dto) {
		cscService.sendInquiry(dto);
		return "redirect:/csc/inquiry";
	}

	@GetMapping("/csc/declaration")
	public void declaration() {
	}

	@PostMapping("/csc/declaration")
	public String declaration(MessageDTO dto) {
		cscService.sendDeclaration(dto);
		return "redirect:/csc/inquiry";
	}

	@GetMapping("/csc/noticeView/{idx}")
	public ModelAndView noticeView(@PathVariable("idx") int idx, int pageNum, String keyword, String searchType) {
		ModelAndView mav = new ModelAndView("/csc/noticeView");
		AdminBoardDTO dto = cscService.selectOne(idx);
		mav.addObject("pageNum", pageNum);
		mav.addObject("keyword", keyword);
		mav.addObject("searchType", searchType);
		mav.addObject("dto", dto);
		return mav;
	}

	@GetMapping("/csc/faqView/{idx}")
	public ModelAndView faqView(@PathVariable("idx") int idx) {
		ModelAndView mav = new ModelAndView("/csc/faqView");
		AdminBoardDTO dto = cscService.selectOne(idx);
		mav.addObject("dto", dto);
		return mav;
	}

	@GetMapping("/csc/myInquiryView/{idx}")
	public ModelAndView myInquiry(@PathVariable("idx") int idx) {
		ModelAndView mav = new ModelAndView("/csc/myInquiryView");
		InquiryDTO dto = cscService.selectInquiryOne(idx);
		mav.addObject("dto", dto);
		return mav;
	}

	@GetMapping("/csc/noticemod/{idx}")
	public ModelAndView noticemod(@PathVariable("idx") int idx) {
		ModelAndView mav = new ModelAndView("csc/noticemod");
		AdminBoardDTO dto = cscService.selectOne(idx);
		mav.addObject("dto", dto);
		return mav;
	}

	@GetMapping("/csc/faqmod/{idx}")
	public ModelAndView faqmod(@PathVariable("idx") int idx) {
		ModelAndView mav = new ModelAndView("/csc/faqmod");
		AdminBoardDTO dto = cscService.selectOne(idx);
		mav.addObject("dto", dto);
		return mav;
	}

	@PostMapping("/csc/noticemod")
	public String noticemod(AdminBoardDTO dto) {
		cscService.update(dto);
		return "redirect:/csc/noticeView/" + dto.getIdx() + "?pageNum=1";
	}

	@PostMapping("/csc/faqmod")
	public String faqmod(AdminBoardDTO dto) {
		cscService.update(dto);
		return "redirect:/csc/faqView/" + dto.getIdx();
	}

	@GetMapping("/csc/noticedel/{idx}")
	public String noticedel(@PathVariable("idx") int idx) {
		cscService.delete(idx);
		return "redirect:/csc/notice";
	}

	@GetMapping("/csc/faqdel/{idx}")
	public String faqdel(@PathVariable("idx") int idx) {
		cscService.delete(idx);
		return "redirect:/csc/faq";
	}
}
