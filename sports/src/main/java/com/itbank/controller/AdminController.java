package com.itbank.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itbank.model.AdminBoardDTO;
import com.itbank.model.InquiryDTO;
import com.itbank.model.MemberDTO;
import com.itbank.model.MessageDTO;
import com.itbank.service.AdminService;
import com.itbank.service.CSCService;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private CSCService cscService;

	@GetMapping("/admin/admin")
	public ModelAndView admin(@RequestParam HashMap<String, String> param) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, String> map = param;

		// 검색하는 종류
		String searchType = map.get("searchType");

		// 검색할 키워드
		String keyword = map.get("keyword");

		// 밴 당한 사람들의 메시지 받아오기
		List<MessageDTO> bannedMemberList = adminService.getBannedList();
		mav.addObject("bannedMemberList", bannedMemberList);

		// 문의 답변온 문답 리스트 받아오기
		List<InquiryDTO> inquiryList = adminService.selectInquiryList();
		mav.addObject("inquiryList", inquiryList);

		// 밴 해야할 멤버들 신고 메시지 리스트 받아오기
		List<MessageDTO> messageDeclar = adminService.getMessageDeclar();
		mav.addObject("messageDeclar", messageDeclar);

		// 우선, 전체 멤버 리스트를 받아온다
		List<MemberDTO> searchMemberList = adminService.searchMember();
		List<MemberDTO> theList = new ArrayList<MemberDTO>();
		int total = searchMemberList.size();

		if (searchType == null || searchType.equals("")) {
		}

		// 그 후, 각 검색종류에 맞는 영역에서 키워드를 대조한다
		else if (searchType.equals("userid")) {
			for (int i = 0; i < total; i++) {
				if (searchMemberList.get(i).getUserid().contains(keyword)) {
					// 키워드를 포함하는 리스트를 빈 리스트에 넣는다
					theList.add(searchMemberList.get(i));
				}
			}
		}

		// 그 후, 각 검색종류에 맞는 영역에서 키워드를 대조한다
		else if (searchType.equals("username")) {
			for (int i = 0; i < total; i++) {
				if (searchMemberList.get(i).getUsername().contains(keyword)) {
					// 키워드를 포함하는 리스트를 빈 리스트에 넣는다
					theList.add(searchMemberList.get(i));
				}
			}
		}

		// 그 후, 각 검색종류에 맞는 영역에서 키워드를 대조한다
		else if (searchType.equals("nickname")) {
			for (int i = 0; i < total; i++) {
				if (searchMemberList.get(i).getNickname().contains(keyword)) {
					// 키워드를 포함하는 리스트를 빈 리스트에 넣는다
					theList.add(searchMemberList.get(i));
				}
			}
		}

		// 그 후, 각 검색종류에 맞는 영역에서 키워드를 대조한다
		else if (searchType.equals("wholeKeyword")) {
			for (int i = 0; i < total; i++) {
				if (searchMemberList.get(i).getUserid().contains(keyword)
						|| searchMemberList.get(i).getNickname().contains(keyword)) {
					// 키워드를 포함하는 리스트를 빈 리스트에 넣는다
					theList.add(searchMemberList.get(i));
				} else if (searchMemberList.get(i).getUsername().contains(keyword)) {
					// 키워드를 포함하는 리스트를 빈 리스트에 넣는다
					theList.add(searchMemberList.get(i));
				}
			}
		}
		String type = param.get("type") == null ? "" : param.get("type");
		if (type.equals("ban")) {
			searchMemberList.forEach(e -> {
				if (e.getBlock().equals("1")) {
					theList.add(e);
				}
			});
		}
		if (type.equals("sign")) {
			searchMemberList.forEach(e -> {
				if (e.getBlock().equals("2")) {
					theList.add(e);
				}
			});
		}
		mav.addObject("searchMemberList", theList);
		return mav;
	}

	// 공지사항 글쓰기 진입
	@GetMapping("/admin/noticeWrite")
	public void noticeWrite() {}

	// 자주묻는 질문 글쓰기 진입
	@GetMapping("/admin/faqWrite")
	public void faqWrite() {}

	// 공지사항 및 자주묻는 질문 글 삽입
	@PostMapping("/admin/adminWrite")
	String adminWrite(AdminBoardDTO adminDTO) {
		adminService.adminWrite(adminDTO);
		return "redirect:/admin/admin";
	}

	// 문의/답변 사항 답변하기위한 관리자 전용 문/답 단일뷰
	@GetMapping("/admin/adminInquiryView/{idx}")
	public ModelAndView adminInquiryView(@PathVariable("idx") int idx) {
		ModelAndView mav = new ModelAndView("/admin/adminInquiryView");
		InquiryDTO dto = cscService.selectInquiryOne(idx);
		mav.addObject("dto", dto);
		return mav;
	}

	// 답변한 내용 수정하기
	@PostMapping("/admin/adminInquiryView/{idx}")
	public String adminInquiryView(InquiryDTO dto) {
		adminService.replyInquiry(dto);
		return "redirect:/admin/admin";
	}

	// 밴 실행
	@PostMapping("/admin/doBlock/{idx}")
	@ResponseBody
	public String doBlock(@PathVariable("idx") int idx) {
		String result = "fail";
		int row = adminService.doBlock(idx);
		if (row != 0) {
			result = "success";
		}
		return result;
	}

	// 밴해제
	@PostMapping("/admin/unDoBlock/{idx}")
	@ResponseBody
	public String unDoBlock(@PathVariable("idx") int idx) {
		String result = "fail";
		int row = adminService.unDoBlock(idx);
		if (row != 0) {
			result = "success";
			MessageDTO messageDTO = new MessageDTO();
			String receiverId = adminService.selectuseridByIdx(idx);
			String sendId = "admin";
			String title = receiverId + "님의 밴이 해제 되셨습니다";
			String content = receiverId + "님의 밴 상태가 해제 되셨습니다.\n재로그인 후 정상적으로 플랫폼을 사용하실 수 있습니다.\n하지만 주의하세요, 경고 없이 다시 밴될 수 있으니 깨끗한 인터넷 사용을 유지해주세요.";
			messageDTO.setSendId(sendId);
			messageDTO.setReceiverId(receiverId);
			messageDTO.setTitle(title);
			messageDTO.setContent(content);
			adminService.sendMessageBan(messageDTO);
		}
		return result;
	}
	
	// 등록 승인
	@PostMapping("/admin/unDoBlockSupp/{idx}")
	@ResponseBody
	public String unDoBlockSupp(@PathVariable("idx") int idx) {
		String result = "fail";
		int row = adminService.unDoBlock(idx);
		if (row != 0) {
			result = "success";
			MessageDTO messageDTO = new MessageDTO();
			String receiverId = adminService.selectuseridByIdx(idx);
			String sendId = "admin";
			String title = receiverId + "님의 공급자 등록이 승인 되셨습니다";
			String content = receiverId + "님의 공급자 등록이 승인 되셨습니다\n재로그인 후 정상적으로 플랫폼을 사용하실 수 있습니다.\n하지만 주의하세요, 경고 없이 밴될 수 있으니 깨끗한 인터넷 사용을 유지해주세요.";
			messageDTO.setSendId(sendId);
			messageDTO.setReceiverId(receiverId);
			messageDTO.setTitle(title);
			messageDTO.setContent(content);
			adminService.sendMessageBan(messageDTO);
		}
		return result;
	}

}
