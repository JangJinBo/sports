package com.itbank.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itbank.component.MailComponent;
import com.itbank.model.AddressDTO;
import com.itbank.model.MemberDTO;
import com.itbank.model.ReserveDTO;
import com.itbank.model.ScrapDTO;
import com.itbank.model.SpaceDTO;
import com.itbank.service.MemberService;
import com.itbank.service.SpaceService;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memberService;
	@Autowired
	SpaceService spaceService;
	@Autowired
	private MailComponent maliComponent;

	@GetMapping("/join")
	public void join() {}

	@PostMapping("/join") // @RequestBody : JSON 데이터를 받기 위한 선언
	@ResponseBody
	public HashMap<String, String> join(@RequestBody HashMap<String, String> param) {

		HashMap<String, String> map = new HashMap<>();
		String memberType = param.get("memberType");

		if (memberType.equals("supp")) {
			param.put("block", "2");
		} else {
			param.put("block", "0");
		}

		// 관리자 마이페이지에 회원 리스트 block가 2인거 띄우기(공급자 회원가입 요청), 해서 확인키 만들어서, 확인 누르면 block를 0로 update

		int memberInsert = memberService.insertMember(param);
		int row = 0;
		int maxIdx = memberService.maxIdx();
		if (memberInsert != 0) {
			param.put("member_idx", maxIdx+"");
			row = memberService.insertAddress(param);
			if (row == 0) {
				memberService.deleteMember(maxIdx);
			}
		} else {
			memberService.deleteMember(maxIdx);
		}
		map.put("status", row != 0 ? "가입성공" : "가입실패");
		map.put("color", row != 0 ? "blue" : "red");
		return map;
	}

	// 중복체크
	@ResponseBody
	@GetMapping("/checkDuplicate")
	public HashMap<String, String> checkDuplicate(String type, String value) throws JsonProcessingException {
		MemberDTO dto = null;
		String msg = "✓";
		String color = "blue";

		if ("userid".equals(type)) {
			dto = memberService.selectOneById(value);
		} else if ("nickname".equals(type)) {
			dto = memberService.selectOneByNickname(value);
		}

		if (dto != null) {
			msg = "✓";
			color = "red";
		}

		HashMap<String, String> map = new HashMap<>();
		map.put("msg", msg);
		map.put("color", color);

		return map;
	}

	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView("home");
		mav.addObject("loginForm", true);
		return mav;
	}

	@PostMapping("/login")
	public ModelAndView login(MemberDTO user, String url, HttpSession session) {
		ModelAndView mav = new ModelAndView("/msg");
		MemberDTO login = memberService.login(user);
		if (login == null) {
			mav.addObject("msg", "비밀번호가 틀렸거나, 해당 계정이 존재하지 않습니다.");
		} else {
			if ((login.getMemberType()).equals("admin")) {
				mav.setViewName("redirect:/admin/admin");
				session.setAttribute("login", login);
				return mav;
			}
			String viewName = "redirect:"; // 리다이렉트 하는데
			viewName += "".equals(url) ? "/" : url; // 일반 로그인을 하면 URL이 없음, 그래서 "/"을 반환(대문페이지로 이동) 아니면 URL을 받아와서 거기로
													// 리다이렉트
			mav.setViewName(viewName);
			session.setAttribute("login", login);
		}
		return mav;
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/mypage/{idx}")
	public ModelAndView mypage(@PathVariable("idx") int idx, HttpSession session) {
		ModelAndView mav = new ModelAndView("/member/mypage");
		MemberDTO dto = memberService.selectAddrByMemberIdx(idx);
		mav.addObject("dto", dto);
		List<ReserveDTO> reserveList = spaceService.selectAllReserveByMemberIdx(idx);
		mav.addObject("reserveList", reserveList);
		List<ScrapDTO> scrapList = spaceService.selectAllScrapByMemberIdx(idx);
		mav.addObject("scrapList", scrapList);
		List<SpaceDTO> pastReserveList = spaceService.selectAllPastReseveSpaceByMemberIdx(idx);
		mav.addObject("pastReserveList", pastReserveList);
		return mav;
	}

	@GetMapping("/suppMypage/{idx}")
	public ModelAndView suppMypage(@PathVariable("idx") int idx, HttpSession session) throws ParseException {
		ModelAndView mav = new ModelAndView("/member/suppMypage");
		MemberDTO dto = memberService.selectAddrByMemberIdx(idx);
		mav.addObject("dto", dto);
		// 멤버 idx(공급자)로 예약정보 가져오기
		List<ReserveDTO> reserveList = spaceService.selectAllReserveBySuppMemberIdx(idx);
		mav.addObject("reserveList", reserveList);
		// 멤버 idx(공급자)로 공간 등록 정보가져오기
		List<SpaceDTO> spaceList = spaceService.selectAllSpaceByMemberIdx(idx);
		mav.addObject("spaceList", spaceList);
		return mav;
	}

	private Random ran = new Random();

	@GetMapping("/sendAuthNumber")
	@ResponseBody
	public String sendAuthNumber(String email, HttpSession session) {
		String authNumber = (ran.nextInt(899999) + 100000) + "";
		HashMap<String, String> param = new HashMap<>();
		param.put("receiver", email);
		param.put("subject", "[Sports Pair] 인증번호입니다.");
		param.put("content", authNumber);

		int row = maliComponent.sendMimeMessage(param);

		String msg;
		if (row != 1) {
			msg = "인증번호 발송에 실패했습니다.";
		} else {
			msg = "인증번호가 발송되었습니다.";
			session.setAttribute("authNumber", authNumber);
			session.setMaxInactiveInterval(180);
		}
		return msg;
	}

	// 2) 사용자가 확인용으로 입력한 인증번호와 세션에 저장된 인증번호를 비교하여 일치여부를 반환한다.
	@GetMapping("/checkAuthNumber/{userNumber}")
	@ResponseBody
	public String checkAuthNumber(@PathVariable("userNumber") String userNumber, HttpSession session) {
		String sessionNumber = (String) session.getAttribute("authNumber");
		String result = userNumber.equals(sessionNumber) ? "1" : "0";
		if (result.equals("1")) {
			session.invalidate();
		}
		return result;
	}

	@GetMapping("/sendAuthPw")
	@ResponseBody
	public String sendAuthPw(String email, String userid, HttpSession session) {
		String msg;
		MemberDTO user = memberService.selectOneById(userid);
		if (user != null) {
			if (email.equals(user.getEmail())) {
				String authNumber = (ran.nextInt(899999) + 100000) + "";
				HashMap<String, String> param = new HashMap<>();
				param.put("receiver", email);
				param.put("subject", "[Sports Pair] 인증번호입니다.");
				param.put("content", authNumber);

				int row = maliComponent.sendMimeMessage(param);

				if (row != 1) {
					msg = "인증번호 발송에 실패했습니다.";
				} else {
					msg = "인증번호가 발송되었습니다.";
					session.setAttribute("authNumber", authNumber);
					session.setMaxInactiveInterval(180);
				}
			} else {
				msg = "일치하는 계정이 없습니다.";
			}
		} else {
			msg = "일치하는 계정이 없습니다.";
		}
		return msg;
	}

	@GetMapping("/reissuePW")
	@ResponseBody
	public String reissuePW(String userid) {
		MemberDTO user = memberService.selectOneById(userid);
		String tempPw = UUID.randomUUID().toString().substring(0, 8);
		user.setUserpw(tempPw);
		memberService.updateMember(user);
		return tempPw;
	}

	@GetMapping("/userModify/{idx}")
	public ModelAndView userModify(@PathVariable("idx") int idx) {
		ModelAndView mav = new ModelAndView("/member/userModify");
		MemberDTO memberDTO = memberService.selectAddrByMemberIdx(idx);
		mav.addObject("user", memberDTO);
		return mav;
	}

	@PostMapping("/userModify/{idx}")
	public ModelAndView userModify(@PathVariable("idx") int idx, @RequestParam HashMap<String, String> param,
			HttpSession session) {
		ModelAndView mav = new ModelAndView("redirect:/member/mypage/{idx}");
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setIdx(idx);
		memberDTO.setUserpw(param.get("userpw"));
		memberDTO.setNickname(param.get("nickname"));
		memberDTO.setPnum(param.get("pnum"));
		memberDTO.setEmail(param.get("email"));

		int row = memberService.updateMember(memberDTO);
		if (row != 0 && param.get("location") != null) {
			MemberDTO login = memberService.selectAddrByMemberIdx(idx);
			AddressDTO addressDTO = new AddressDTO();
			addressDTO.setMember_idx(idx);
			addressDTO.setIdx(login.getAddressIdx());
			addressDTO.setLocation(param.get("location"));
			addressDTO.setAddX(Double.parseDouble(param.get("addX")));
			addressDTO.setAddY(Double.parseDouble(param.get("addY")));
			memberService.updateAddr(addressDTO);
		}
		session.invalidate();
		return mav;
	}

	@ResponseBody
	@PostMapping("/deleteMember")
	public String deleteMemver(String userpw, String userid) {
		// member의 idx로 객체를 불러와서
		MemberDTO dto = memberService.selectOneById(userid);
		dto.setUserpw(userpw);
		MemberDTO login = memberService.login(dto);
		if (login != null) {
			// 비번이 맞아서 회원 탈퇴 진행
			memberService.deleteMember(login.getIdx());
			return "success";
		} else {
			// 비번이 틀렸을때
			return "fail";
		}
	}

}
