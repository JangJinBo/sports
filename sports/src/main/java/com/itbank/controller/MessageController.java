package com.itbank.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itbank.model.MemberDTO;
import com.itbank.model.MessageDTO;
import com.itbank.service.MessageService;

@Controller
public class MessageController {

	@Autowired
	private MessageService messageService;

	@GetMapping("/message/list")
	public ModelAndView list(HttpSession session) {
		MemberDTO login = (MemberDTO) session.getAttribute("login");
		String receiverId = login.getUserid();
		ModelAndView mav = new ModelAndView();
		List<MessageDTO> list = messageService.selectMyMessage(receiverId);

		list.forEach(e -> {
			String tmp = e.getTitle() + e.getSendId();
			int takeoff = 24 - e.getSendId().length();
			if (tmp.length() > 24) {
				e.setCut(e.getTitle().substring(0, takeoff) + "...");
			} else {
				e.setCut(e.getTitle());
			}
		});
		mav.addObject("list", list);
		return mav;
	}

	@GetMapping("/message/delete/{idx}")
	public String delete(@PathVariable("idx") int idx) {
		messageService.deleteMessage(idx);
		return "redirect:/message/list";
	}

	@GetMapping("/message/sendMessage")
	public ModelAndView sendMessage(@RequestParam(required = false, defaultValue = "") String receiverId,
			HttpSession session) {
		MemberDTO login = (MemberDTO) session.getAttribute("login");
		ModelAndView mav = new ModelAndView();
		if ("0".equals(login.getBlock())) {
			mav.addObject("receiverId", receiverId);
		} else {
			mav.setViewName("redirect:/message/sendBannedMessage");
		}
		return mav;
	}

	@PostMapping("/message/sendMessage")
	public String sendMessage(MessageDTO dto, HttpSession session) {
		MemberDTO login = (MemberDTO) session.getAttribute("login");
		dto.setSendId(login.getUserid());
		messageService.sendMessage(dto);
		return "redirect:/";
	}

	@GetMapping("/message/apply")
	public ModelAndView apply(@RequestParam HashMap<String, String> param) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, String> map = param;
		String receiverId = map.get("receiverId");
		String boardType = map.get("boardType");
		String vn = "";
		if (boardType.equals("teamBoard")) {
			vn = "팀원 모집";
		}
		if (boardType.equals("substiBoard")) {
			vn = "용병 모집";
		}
		if (boardType.equals("partyBoard")) {
			vn = "파티 모집";
		}
		if (boardType.equals("matchBoard")) {
			vn = "팀 매칭";
		}
		String title = String.format("[%s] 신청 !!!", vn);

		mav.addObject("title", title);
		mav.addObject("receiverId", receiverId);
		mav.addObject("boardType", boardType);
		return mav;
	}

	@PostMapping("/message/apply")
	public String apply(MessageDTO dto, HttpSession session, String boardType) {
		MemberDTO login = (MemberDTO) session.getAttribute("login");
		dto.setSendId(login.getUserid());
		messageService.sendMessage(dto);

		return "redirect:/juBoard/" + boardType;
	}

	@PostMapping("/message/sendNotice")
	public String sendNotice(MessageDTO dto, HttpSession session, int idx) {
		MemberDTO login = (MemberDTO) session.getAttribute("login");
		dto.setSendId(login.getUserid());
		dto.setTitle("[문의] " + dto.getTitle());
		messageService.sendMessage(dto);
		return "redirect:/space/view/" + idx;
	}

	@GetMapping("/message/sendBannedMessage")
	public ModelAndView sendBannedMessage(@RequestParam(required = false, defaultValue = "") String receiverId,
			HttpSession session) {
		MemberDTO login = (MemberDTO) session.getAttribute("login");
		ModelAndView mav = new ModelAndView();
		if ("1".equals(login.getBlock())) {
			mav.addObject("receiverId", receiverId);
		} else {
			mav.setViewName("redirect:/message/sendMessage");
		}
		return mav;
	}

	@GetMapping("/message/declare")
	public void declare() {
	}

	@PostMapping("/message/declare")
	public String declare(MessageDTO dto, HttpSession session) {
		MemberDTO login = (MemberDTO) session.getAttribute("login");
		dto.setSendId(login.getUserid());
		dto.setTitle("[신고] " + dto.getTitle());
		dto.setReceiverId("admin");
		messageService.sendMessage(dto);
		return "redirect:/";
	}

	@PostMapping("/message/sendBannedMessage")
	public String sendBannedMessage(MessageDTO dto, HttpSession session) {
		MemberDTO login = (MemberDTO) session.getAttribute("login");
		dto.setSendId(login.getUserid());
		dto.setTitle("[밴 해제 요청] " + dto.getTitle());
		messageService.sendBannedMessage(dto);
		return "redirect:/";
	}

	@GetMapping("/message/sendSuppMessage")
	public ModelAndView sendSuppMessage(@RequestParam(required = false, defaultValue = "") String receiverId,
			HttpSession session) {
		MemberDTO login = (MemberDTO) session.getAttribute("login");
		ModelAndView mav = new ModelAndView();
		if ("2".equals(login.getBlock())) {
			mav.addObject("receiverId", receiverId);
		} else {
			mav.setViewName("redirect:/message/sendMessage");
		}
		return mav;
	}

	@PostMapping("/message/sendSuppMessage")
	public String sendSuppMessage(MessageDTO dto, HttpSession session) {
		MemberDTO login = (MemberDTO) session.getAttribute("login");
		dto.setSendId(login.getUserid());
		dto.setTitle("[공급자 등록 신청] " + dto.getTitle());
		messageService.sendSuppBannedMessage(dto);
		return "redirect:/";
	}

	@ResponseBody
	@GetMapping("/message/msgChecker")
	public HashMap<String, String> msgChecker(int idx) throws JsonProcessingException {
		HashMap<String, String> map = new HashMap<String, String>();
		messageService.readMessage(idx);
		map.put("ment", "operate");
		return map;
	}

	@ResponseBody
	@GetMapping("/message/countingmsg")
	public HashMap<String, String> countingmsg(String receiverId) throws JsonProcessingException {
		HashMap<String, String> map = new HashMap<String, String>();
		// 안읽은 쪽지의 개수를 counternum에 저장
		int counternum = messageService.messageCounter(receiverId);
		String color = "orangered";
		String counter = counternum + "";
		// 안읽은 쪽지가 없다면 none
		if (counternum == 0) {
			color = "none";
		}
		if (counternum > 9) {
			counter = "9+";
		}
		map.put("counter", counter + "");
		map.put("color", color);
		return map;
	}
}
