package com.itbank.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.itbank.model.AddressDTO;
import com.itbank.model.BoardDTO;
import com.itbank.model.FacilityDTO;
import com.itbank.model.FileListDTO;
import com.itbank.model.MemberDTO;
import com.itbank.model.PageMakeDTO;
import com.itbank.model.PayDTO;
import com.itbank.model.ReviewDTO;
import com.itbank.model.ReserveDTO;
import com.itbank.model.SpaceDTO;
import com.itbank.service.SpaceService;

@Controller
@RequestMapping("/space")
public class SpaceController {

	@Autowired
	SpaceService spaceService;

	@GetMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "1") int pageNum,	// pageNum : 현재 페이지 번호 defualt 1로 설정
			@RequestParam HashMap<String, String> param, HttpSession session) {	// 검색어를 HashMap으로 받아온다.
		ModelAndView mav = new ModelAndView();
		MemberDTO login = (MemberDTO) session.getAttribute("login");
		List<SpaceDTO> spaceList = null;
		if (login != null) { // 로그인 되었을 때
			AddressDTO addressDTO = spaceService.selectAddressByIdx(login.getIdx()); // address에 있는 위도와 경도를 불러온다
			spaceList = spaceService.selectListByAddressDTO(addressDTO); // 위도와 경도를 이용하여 위치 순 spaceList를 불러온다
		} else { // 로그인 되어 있지 않을 때
			spaceList = spaceService.selectList(); // 예약자수(count) 순 spaceList를 불러온다
		}

		List<String> categoryList = spaceService.selectCategoryList();
		HashSet<String> categorySet = new HashSet<String>(categoryList);
		ArrayList<String> spaceCategoryList = new ArrayList<String>(categorySet);
		spaceCategoryList.sort((a, b) -> a.compareTo(b));

		// 검색 시작
		// HashMap으로 받은 검색 컬럼 값이 null일 경우 처리를 해준다.
		String category = param.get("category") == null ? "" : param.get("category");
		String region = param.get("region") == null ? "" : param.get("region");
		String minPrice = param.get("minPrice") == null || param.get("minPrice").equals("") ? "0": param.get("minPrice");
		String maxPrice = param.get("maxPrice") == null || param.get("maxPrice").equals("") ? Integer.MAX_VALUE + "" : param.get("maxPrice");
		String starRating = param.get("starRating") == null || param.get("starRating").equals("") ? "0.0": param.get("starRating");
		String spaceName = param.get("spaceName") == null ? "" : param.get("spaceName");

		// addObject하기 위해 새로운 HashMap에 처리한 변수들을 담아준다.
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("category", category);
		map.put("region", region);
		map.put("minPrice", minPrice);
		map.put("maxPrice", maxPrice);
		map.put("starRating", starRating);
		map.put("spaceName", spaceName);

		// 검색을 처리한 결과를 담을 리스트를 만든다.
		List<SpaceDTO> s = new ArrayList<SpaceDTO>();

		// 수치비교로 검색을 처리하기 위해 형변환 해준다.
		int min = Integer.parseInt(minPrice);
		int max = Integer.parseInt(maxPrice);

		Double rate = Double.parseDouble(starRating);

		// 모든 컬럼 검색 처리
		spaceList.forEach(e -> {
			int price = Integer.parseInt(e.getPrice());

			if (e.getSpaceCategory().contains(category) || category.equals("")) {
				if (e.getRegion().contains(region) || region.equals("") || region.equals("")) {
					if (price >= min && price <= max) {
						if (e.getRate() >= rate) {
							if (e.getSpaceName().contains(spaceName) || spaceName.equals("")) {
								s.add(e);	// 처리된 결과를 담는다.
							}
						}
					}
				}
			}
		});

		// 페이징 시작
		int total = s.size();	// 검색처리가 완료된 리스트의 길이를 구해 total에 저장한다.
		PageMakeDTO pageMake = new PageMakeDTO(pageNum, total); //PageMakeDTO 생성자 매개변수로 현재페이지와 total을 전달한다.
		mav.addObject("pageMake", pageMake);

		// 페이징을 처리한 최종 결과를 담을 리스트 생성
		List<SpaceDTO> pageList = new ArrayList<SpaceDTO>();
		// 페이징 처리 (한 페이지에 나올 10개의 데이터만 저장)
		for (int i = pageMake.getStartRow(); i <= pageMake.getEndRow(); i++) {
			pageList.add(s.get(i));
		}
		// 페이징 끝

		// spaceCategory 검색어 리스트
		mav.addObject("map", map);
		mav.addObject("spaceList", pageList);
		mav.addObject("spaceCategoryList", spaceCategoryList);
		return mav;
	}

	@GetMapping("/register")
	public void register() {}

	@PostMapping("/register")
	public String register(@RequestParam HashMap<String, String> map, List<MultipartFile> upload, HttpSession session) {
		MemberDTO login = (MemberDTO) session.getAttribute("login");
		map.put("member_idx", login.getIdx() + "");
		int boardRow = spaceService.insertBoard(login.getIdx()); // board 테이블에 등록을 한다
		if (boardRow != 0) { // board가 등록이 되었다면
			int boardMaxIdx = spaceService.selectBoardMaxIdx(); // 등록된 board의 idx를 불러온다
			int addressRow = spaceService.insertAddress(map); // address 테이블에 등록한다
			if (addressRow != 0) { // address가 등록이 되었다면
				int addressMaxIdx = spaceService.selectAddressMaxIdx(); // 등록된 address의 idx를 불러온다
				map.put("address_idx", addressMaxIdx + "");
				map.put("board_idx", boardMaxIdx + "");
				map.put("openTime", map.get("openTime") + ":00");
				map.put("closeTime", map.get("closeTime") + ":00");
				int registerRow = spaceService.register(map); // space 테이블에 등록한다
				if (registerRow != 0) { // space가 등록이 되었다면
					if ("".equals(upload.get(0).getOriginalFilename()) == false) { // 파일이 있다면
						for (MultipartFile f : upload) {
							FileListDTO fileDTO = new FileListDTO();
							fileDTO.setBoard_idx(boardMaxIdx);
							String fileName = spaceService.uploadFile(f); // 파일을 업로드 하고
							fileDTO.setFileName(fileName);
							int fileRow = spaceService.insertFile(fileDTO); // fileList 테이블에 fileName들을 등록한다
							if (fileRow == 0) { // fileList에 등록하지 못했다면
								spaceService.deleteFile(fileName); // 업로드된 파일을 삭제한다
							}
						}
					}
					int spaceMaxIdx = spaceService.selectSpaceMaxIdx(); // space등록 후 등록된 space의 view를 불러오기 위한 idx
					return "redirect:/space/view/" + spaceMaxIdx; // 등록된 space의 상세보기로 바로 간다
				}
				spaceService.deleteAddress(addressMaxIdx); // space의 등록이 되지 않았다면 등록된 address를 삭제한다
			}
			spaceService.deleteBoard(boardMaxIdx); // address의 등록이 되지 않았다면 등록된 board를 삭제한다
		}
		return "redirect:/space/list";
	}

	@GetMapping("/view/{idx}")
	public ModelAndView view(@PathVariable("idx") int idx, @RequestParam HashMap<String, String> param) {	// 검색어, 페이지 정보를 HashMap으로 받는다.
		ModelAndView mav = new ModelAndView("/space/view");
		SpaceDTO spaceDTO = spaceService.selectOneByIdx(idx); // space의 정보를 불러올 spaceDTO
		BoardDTO boardDTO = spaceService.selectBoardByIdx(spaceDTO.getBoard_idx()); // 작성자를 불러올 boardDTO
		MemberDTO memberDTO = spaceService.selectMemberByIdx(boardDTO.getMember_idx());
		AddressDTO addressDTO = spaceService.selectSpaceAddressByIdx(spaceDTO.getAddress_idx()); // 주소 정보를 불러올
																									// addressDTO
		List<FileListDTO> fileList = spaceService.selectFileListByIdx(spaceDTO.getBoard_idx()); // 이미지를 불러올 fileListDTO
		List<ReviewDTO> reviewList = spaceService.selectReviewListByIdx(spaceDTO.getBoard_idx()); // 후기 정보를 불러올
																									// reviewDTO
		List<FacilityDTO> facilityList = spaceService.selectFacilityListBySpaceIdx(spaceDTO.getIdx());
		HashMap<String, String> map = makehash(param);	// null값 처리 함수
		mav.addObject("pageNum", param.get("pageNum"));
		mav.addObject("map", map);
		mav.addObject("boardDTO", boardDTO);
		mav.addObject("memberDTO", memberDTO);
		mav.addObject("spaceDTO", spaceDTO);
		mav.addObject("addressDTO", addressDTO);
		mav.addObject("fileList", fileList);
		mav.addObject("reviewList", reviewList);
		mav.addObject("facilityList", facilityList);
		return mav;
	}

	// 예약 폼 띄우기
	@GetMapping("/reserve/{idx}")
	public ModelAndView reserve(@PathVariable("idx") int idx) {
		java.util.Date d = new java.util.Date();
		String today = new SimpleDateFormat("yyyy-MM-dd").format(d);
		String time = new SimpleDateFormat("HH").format(d);
		ModelAndView mav = new ModelAndView("/space/reserve");
		SpaceDTO spaceDTO = spaceService.selectOneByIdx(idx);
		mav.addObject("spaceDTO", spaceDTO);
		mav.addObject("today", today);
		mav.addObject("time", time);
		return mav;
	}

	@ResponseBody
	@PostMapping("/reserveCheck/{idx}")
	public HashMap<String, Object> reserveCheck(@PathVariable("idx") int idx,
			@RequestBody HashMap<String, String> param) throws ParseException {
		HashMap<String, Object> result = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		java.util.Date d = new java.util.Date();
		String startDay = param.get("startDay") + " " + param.get("startTime");
		String endDay = param.get("endDay") + " " + param.get("endTime");

		// space의 idx와 param 받아와서 reserveDTO에 세팅하기
		ReserveDTO dto = new ReserveDTO();
		dto.setSpace_idx(idx);
		dto.setMember_idx(Integer.parseInt(param.get("member_idx")));
		dto.setStartTime(startDay);
		dto.setEndTime(endDay);
		dto.setReserverName(param.get("reserverName"));
		dto.setReserverNumber(param.get("reserverNumber"));
		int check = spaceService.checkReserve(dto);

		// space의 idx로 spaceDTO를 받아와서 openTime,closeTime불러와서
		SpaceDTO spaceDTO = spaceService.selectOneByspaceIdx(idx);
		// 이용 시간 구하기
		Date startDate = new Date(sdf.parse(startDay).getTime());
		Date endDate = new Date(sdf.parse(endDay).getTime());
		long startTimeInMillis = startDate.getTime();
		long endTimeInMillis = endDate.getTime();
		long today = d.getTime();

		if (today > startTimeInMillis) {
			// 예약 불가
			result.put("message", "현재 시간 이후로 예약이 가능합니다.");
			result.put("color", "red");
		} else {
			// 요금을 계산하기 위해 필요한 작업
			if (check == 0) {

				// 끝 시간에서 시작 시간 빼기
				long timeDifferenceInMillis = endTimeInMillis - startTimeInMillis;
				// 밀리초를 시간으로 변 환
				long hoursDifference = timeDifferenceInMillis / (60 * 60 * 1000);

				int usageTime = Long.valueOf(hoursDifference).intValue();
				int price = Integer.parseInt(spaceDTO.getPrice());
				int total = usageTime * price;
				if (total > 0) {
					result.put("total", String.format("최종 결제 금액 : %,d원", total));
					result.put("message", "예약 가능한 기간입니다");
					result.put("color", "blue");
					result.put("totalValue", total);
					result.put("ReserveDTO", dto);

				} else { // 예약일자는 중복이 아닌데, 시작시간이 종료시간보다 뒤에 있을 경우
					result.put("message", "예약 [시간]을 확인해주세요.");
					result.put("color", "red");
				}
			} else {
				result.put("message", "예약 불가한 기간입니다");
				result.put("color", "red");
			}
		}
		// 예약기간에 이미 되어있는 리스트 띄우기
		HashMap<String, Object> param2 = new HashMap<>();
		param2.put("spaceIdx", idx);
		param2.put("startDay", param.get("startDay"));
		List<ReserveDTO> reserveDayList = spaceService.selectAllStartTime(param2);
		result.put("reserveDayList", reserveDayList);
		return result;
	}

	// 결제 성공시에 예약테이블 등록
	@ResponseBody
	@PostMapping("/reserve")
	public HashMap<String, String> reserve(@RequestBody ReserveDTO reserveDTO) {
		HashMap<String, String> result = new HashMap<>();
		int row = spaceService.insertReserve(reserveDTO);
		if (row != 0) {
			result.put("success", "성공");
		} else {
			String payIdx = reserveDTO.getPay_payIdx();
			spaceService.deletePayIdx(payIdx);
		}
		return result;
	}

	// 예약취소
	@GetMapping("/reserveCancel/{idx}")//reserveDTO.idx
	public String reserveCancel(@PathVariable("idx") int idx) {
		ReserveDTO reserveDTO = spaceService.selectByReserveIdx(idx);
		int member_idx = reserveDTO.getMember_idx();
		String payIdx = reserveDTO.getPay_payIdx();
		int row = spaceService.deleteReserveByIdx(idx);
		if (row != 0) {
			// payidx로 pay 테이블 삭제
			spaceService.deletePayIdx(payIdx);
		}
		return "redirect:/member/mypage/" + member_idx;
	}

	// 결제
	@ResponseBody
	@PostMapping("/pay/{idx}")
	public HashMap<String, Object> payInsert(@PathVariable("idx") int idx, @RequestBody HashMap<String, String> param) {
		HashMap<String, Object> result = new HashMap<>();
		String payIdx = param.get("imp_uid");
		String reserveNum = param.get("merchant_uid");
		String reservePrice = param.get("paid_amount");

		PayDTO dto = new PayDTO();
		dto.setPayIdx(payIdx);
		dto.setReservePrice(reservePrice);
		dto.setReserveNum(reserveNum);
		spaceService.payInsert(dto);
		result.put("payIdx", dto.getPayIdx());
		return result;
	}

	@PostMapping("/toggleScrap")
	@ResponseBody
	public HashMap<String, Boolean> join(@RequestBody HashMap<String, Integer> param) {
		HashMap<String, Boolean> map = new HashMap<>();
		int row = spaceService.selectScrapByIdx(param); // scrap에 등록이 되었는지 확인
		int scrapRow = 0;
		if (row != 0) { // scrap에 등록이 되었다면
			scrapRow = spaceService.deleteScrap(param); // scrap에서 삭제한다
			map.put("isScrapped", scrapRow == 0);
		} else { // scrap에 등록이 되어 있지 않다면
			scrapRow = spaceService.insertScrap(param); // scrap에 추가한다
			map.put("isScrapped", scrapRow != 0);
		}
		return map;
	}

	@GetMapping("/delete/{idx}")
	public ModelAndView delete(@PathVariable("idx") int space_idx) {
		ModelAndView mav = new ModelAndView("redirect:/space/list");
		int reserveRow = spaceService.selectReserveForDelete(space_idx); // space에 예약자가 남아 있는지 확인하고
		String msg;
		if (reserveRow != 0) { // 예약자가 남아 있으면
			msg = "예약자가 존재합니다. 확인 후 삭제 해 주세요";
			mav.addObject("msg", msg);
			mav.setViewName("/space/goBack");
			return mav;
		}

		int spaceBoardIdx = spaceService.selectspaceBoardIdx(space_idx); // 삭제에 필요한 board_idx 가져오기
		int spaceAddressIdx = spaceService.selectAddressIdx(space_idx); // space가 삭제되기 전에 미리 address_idx 저장(on delete
																		// cascade)
		spaceService.deleteholidayBySpaceIdx(space_idx); // 휴일 삭제
		int deleteRow = spaceService.deleteBoard(spaceBoardIdx);
		if (deleteRow != 0) {
			spaceService.deleteAddress(spaceAddressIdx);
		}
		return mav;
	}

	@GetMapping("/modify/{idx}")
	public ModelAndView modify(@PathVariable("idx") int space_idx) {
		ModelAndView mav = new ModelAndView("/space/modify");
		SpaceDTO spaceDTO = spaceService.selectOneByIdx(space_idx);
		AddressDTO addressDTO = spaceService.selectSpaceAddressByIdx(spaceDTO.getAddress_idx());
		List<FileListDTO> fileList = spaceService.selectFileListByIdx(spaceDTO.getBoard_idx());
		mav.addObject("spaceDTO", spaceDTO);
		mav.addObject("addressDTO", addressDTO);
		mav.addObject("fileList", fileList);
		return mav;
	}

	@DeleteMapping("/deleteFile/{board_idx}/{fileName}")
	@ResponseBody
	public boolean deleteFile(@PathVariable("board_idx") String board_idx, @PathVariable("fileName") String fileName) {
		HashMap<String, String> param = new HashMap<>();
		param.put("board_idx", board_idx);
		param.put("fileName", fileName);
		int row = spaceService.deleteOneFile(param);
		return row != 0;
	}

	@PostMapping("/modify/{idx}")
	public String modify(@RequestParam HashMap<String, String> map, @PathVariable("idx") int space_idx,
			List<MultipartFile> upload) {
		SpaceDTO spaceDTO = spaceService.selectOneByspaceIdx(space_idx);
		map.put("address_idx", spaceDTO.getAddress_idx() + "");
		if (map.get("location") != null) {
			spaceService.updateAddress(map);
		} else {
			map.put("region", spaceDTO.getRegion());
		}
		map.put("space_idx", space_idx + "");
		map.put("openTime", map.get("openTime") + ":00");
		map.put("closeTime", map.get("closeTime") + ":00");
		spaceService.updateSpace(map);
		if ("".equals(upload.get(0).getOriginalFilename()) == false) {
			for (MultipartFile f : upload) {
				FileListDTO fileDTO = new FileListDTO();
				fileDTO.setBoard_idx(spaceDTO.getBoard_idx());
				String fileName = spaceService.uploadFile(f);
				fileDTO.setFileName(fileName);
				int fileRow = spaceService.insertFile(fileDTO);
				if (fileRow == 0) {
					spaceService.deleteFile(fileName);
				}
			}
		}
		return "redirect:/space/view/" + space_idx;
	}

	@GetMapping("/holiday/{idx}")
	public ModelAndView holiday(@PathVariable("idx") int space_idx) {
		ModelAndView mav = new ModelAndView("/space/holiday");
		SpaceDTO spaceDTO = spaceService.selectOneByspaceIdx(space_idx);
		List<ReserveDTO> holidayList = spaceService.selectHolidayList(space_idx);
		java.util.Date d = new java.util.Date();
		String today = new SimpleDateFormat("yyyy-MM-dd").format(d);
		mav.addObject("today", today);
		mav.addObject("spaceDTO", spaceDTO);
		mav.addObject("holidayList", holidayList);
		return mav;
	}

	@PostMapping("/holiday/{idx}")
	public ModelAndView holiday(@RequestParam HashMap<String, String> param, @PathVariable("idx") int idx)
			throws ParseException {
		ModelAndView mav = new ModelAndView("redirect:/space/holiday/" + idx);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		String startDay = param.get("startDay") + " " + param.get("startTime");
		String endDay = param.get("endDay") + " " + param.get("endTime");
		String msg;

		long stDay = sdf.parse(startDay).getTime();
		long edDay = sdf.parse(endDay).getTime();

		if (stDay >= edDay) {
			msg = "날짜와 시간을 다시 확인 해 주세요.";
			mav.addObject("msg", msg);
			mav.setViewName("/space/goBack");
			return mav;
		}

		// space의 idx와 param 받아와서 reserveDTO에 세팅하기
		ReserveDTO dto = new ReserveDTO();
		dto.setSpace_idx(idx);
		dto.setMember_idx(Integer.parseInt(param.get("member_idx")));
		dto.setStartTime(startDay);
		dto.setEndTime(endDay);
		dto.setReserverName(param.get("reserverName"));

		int holidayCheck = spaceService.holidayCheck(dto);
		if (holidayCheck != 0) {
			msg = "중복된 휴일이 있습니다. 확인 후 등록 해 주세요";
			mav.addObject("msg", msg);
			mav.setViewName("/space/goBack");
			return mav;
		}
		int reserveCheck = spaceService.checkReserve(dto);
		if (reserveCheck != 0) {
			msg = "선택하신 휴일에 예약자가 존재합니다. 확인 후 등록 해 주세요";
			mav.addObject("msg", msg);
			mav.setViewName("/space/goBack");
			return mav;
		}
		spaceService.insertHoliday(dto);
		return mav;
	}

	@GetMapping("/holidayDelete/{space_idx}/{holiday_idx}")
	public String holidayDelete(@PathVariable("space_idx") int space_idx, @PathVariable("holiday_idx") int idx) {
		spaceService.deleteHoliday(idx);
		return "redirect:/space/holiday/" + space_idx;
	}

	@GetMapping("/review/{idx}")
	public ModelAndView review(@PathVariable("idx") int board_idx) {
		ModelAndView mav = new ModelAndView("/space/review");
		SpaceDTO spaceDTO = spaceService.selectSpaceByBoardIdx(board_idx);
		mav.addObject("spaceDTO", spaceDTO);
		return mav;
	}

	@PostMapping("/review/{idx}")
	public String review(@RequestParam HashMap<String, String> param, @PathVariable("idx") int board_idx) {
		int space_idx = Integer.parseInt(param.get("space_idx")); // view로 돌아갈 idx
		param.put("board_idx", board_idx + "");
		spaceService.insertReview(param);
		return "redirect:/space/view/" + space_idx;
	}

	@GetMapping("/deleteReview/{space_idx}/{idx}")
	public String deleteReview(@PathVariable("space_idx") int space_idx, @PathVariable("idx") int idx) {
		spaceService.deleteReview(idx);
		return "redirect:/space/view/" + space_idx;
	}

	@GetMapping("/facility/{space_idx}")
	public String facility() {
		return "/space/facility";
	}

	@PostMapping("/facility/{space_idx}")
	public String facility(@PathVariable("space_idx") int space_idx, @RequestParam HashMap<String, String> param) {
		param.put("space_idx", space_idx + "");
		spaceService.insertFacility(param);
		return "redirect:/space/view/" + space_idx;
	}

	@GetMapping("/deleteFacility/{space_idx}/{idx}")
	public String deleteFacility(@PathVariable("space_idx") int space_idx, @PathVariable("idx") int idx) {
		spaceService.deleteFacility(idx);
		return "redirect:/space/view/" + space_idx;
	}

	private HashMap<String, String> makehash(HashMap<String, String> param) {
		HashMap<String, String> map = new HashMap<String, String>();
		String category = param.get("category") == null ? "" : param.get("category");
		String region = param.get("region") == null ? "" : param.get("region");
		String minPrice = param.get("minPrice") == null || param.get("minPrice").equals("") ? "0"
				: param.get("minPrice");
		String maxPrice = param.get("maxPrice") == null || param.get("maxPrice").equals("") ? Integer.MAX_VALUE + ""
				: param.get("maxPrice");
		String starRating = param.get("starRating") == null || param.get("starRating").equals("") ? "0.0"
				: param.get("starRating");
		String spaceName = param.get("spaceName") == null ? "" : param.get("spaceName");
		map.put("category", category);
		map.put("region", region);
		map.put("minPrice", minPrice);
		map.put("maxPrice", maxPrice);
		map.put("starRating", starRating);
		map.put("spaceName", spaceName);
		return map;
	}
}
