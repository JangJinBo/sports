package com.itbank.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.itbank.model.FileListDTO;
import com.itbank.model.JuBoardDTO;
import com.itbank.model.MatchBoardDTO;
import com.itbank.model.PageMakeDTO;
import com.itbank.model.PartyBoardDTO;
import com.itbank.model.ReviewDTO;
import com.itbank.model.SubstiBoardDTO;
import com.itbank.service.JuService;

@Controller
@RequestMapping("/juBoard")
public class JuController {

	@Autowired
	JuService juService;

	// 각 보드의 리스트 띄우기
	@GetMapping("/teamBoard")
	public ModelAndView teamBoard(@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam HashMap<String, String> param) {
		ModelAndView mav = new ModelAndView();
		List<JuBoardDTO> tmBoardList = juService.selectTm();
		mav.addObject("boardType", "teamBoard");

		HashMap<String, String> map = param;
		String searchType = map.get("searchType");
		String keyword = map.get("keyword");
		List<JuBoardDTO> list = new ArrayList<JuBoardDTO>();
		int total = tmBoardList.size();

		if (searchType == null || searchType.equals("")) {
			tmBoardList.forEach(e -> {
				list.add(e);
			});
		} else if (searchType.equals("title")) {
			for (int i = 0; i < total; i++) {
				if (tmBoardList.get(i).getTitle().contains(keyword)) {
					list.add(tmBoardList.get(i));
				}
			}
		} else if (searchType.equals("content")) {
			for (int i = 0; i < total; i++) {
				if (tmBoardList.get(i).getContent().contains(keyword)) {
					list.add(tmBoardList.get(i));
				}
			}
		} else if (searchType.equals("all")) {
			for (int i = 0; i < total; i++) {
				if (tmBoardList.get(i).getTitle().contains(keyword)
						|| tmBoardList.get(i).getContent().contains(keyword)) {
					list.add(tmBoardList.get(i));
				}
			}
		}

		// 페이징
		total = list.size();
		PageMakeDTO pageMake = new PageMakeDTO(pageNum, total);
		mav.addObject("pageMake", pageMake);
		List<JuBoardDTO> pageList = new ArrayList<JuBoardDTO>();
		int end = list.size() - 1;
		int fine = pageMake.getEndRow() < end ? pageMake.getEndRow() : end;
		for (int i = pageMake.getStartRow(); i <= fine; i++) {
			pageList.add(list.get(i));
		}
		mav.addObject("searchType", searchType);
		mav.addObject("keyword", keyword);
		mav.addObject("tmBoardList", pageList);
		return mav;
	}

	@GetMapping("/substiBoard")
	public ModelAndView substiBoard(@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam HashMap<String, String> param) {
		ModelAndView mav = new ModelAndView();
		List<SubstiBoardDTO> sbBoardList = juService.selectSb();
		mav.addObject("boardType", "substiBoard");

		HashMap<String, String> map = param;
		String searchType = map.get("searchType");
		String keyword = map.get("keyword");
		List<SubstiBoardDTO> list = new ArrayList<SubstiBoardDTO>();
		int total = sbBoardList.size();

		if (searchType == null || searchType.equals("")) {
			sbBoardList.forEach(e -> {
				list.add(e);
			});
		} else if (searchType.equals("title")) {
			for (int i = 0; i < total; i++) {
				if (sbBoardList.get(i).getTitle().contains(keyword)) {
					list.add(sbBoardList.get(i));
				}
			}
		} else if (searchType.equals("content")) {
			for (int i = 0; i < total; i++) {
				if (sbBoardList.get(i).getContent().contains(keyword)) {
					list.add(sbBoardList.get(i));
				}
			}
		} else if (searchType.equals("all")) {
			for (int i = 0; i < total; i++) {
				if (sbBoardList.get(i).getTitle().contains(keyword)
						|| sbBoardList.get(i).getContent().contains(keyword)) {
					list.add(sbBoardList.get(i));
				}
			}
		}

		// 페이징
		total = list.size();
		PageMakeDTO pageMake = new PageMakeDTO(pageNum, total);
		mav.addObject("pageMake", pageMake);
		List<SubstiBoardDTO> pageList = new ArrayList<SubstiBoardDTO>();
		int end = list.size() - 1;
		int fine = pageMake.getEndRow() < end ? pageMake.getEndRow() : end;
		for (int i = pageMake.getStartRow(); i <= fine; i++) {
			pageList.add(list.get(i));
		}
		mav.addObject("searchType", searchType);
		mav.addObject("keyword", keyword);

		mav.addObject("sbBoardList", pageList);
		return mav;
	}

	@GetMapping("/partyBoard")
	public ModelAndView partyBoard(@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam HashMap<String, String> param) {
		ModelAndView mav = new ModelAndView();
		List<PartyBoardDTO> ptBoardList = juService.selectPt();
		mav.addObject("boardType", "partyBoard");

		HashMap<String, String> map = param;
		String searchType = map.get("searchType");
		String keyword = map.get("keyword");
		List<PartyBoardDTO> list = new ArrayList<PartyBoardDTO>();
		int total = ptBoardList.size();

		if (searchType == null || searchType.equals("")) {
			ptBoardList.forEach(e -> {
				list.add(e);
			});
		} else if (searchType.equals("title")) {
			for (int i = 0; i < total; i++) {
				if (ptBoardList.get(i).getTitle().contains(keyword)) {
					list.add(ptBoardList.get(i));
				}
			}
		} else if (searchType.equals("content")) {
			for (int i = 0; i < total; i++) {
				if (ptBoardList.get(i).getContent().contains(keyword)) {
					list.add(ptBoardList.get(i));
				}
			}
		} else if (searchType.equals("all")) {
			for (int i = 0; i < total; i++) {
				if (ptBoardList.get(i).getTitle().contains(keyword)
						|| ptBoardList.get(i).getContent().contains(keyword)) {
					list.add(ptBoardList.get(i));
				}
			}
		}

		// 페이징
		total = list.size();
		PageMakeDTO pageMake = new PageMakeDTO(pageNum, total);
		mav.addObject("pageMake", pageMake);
		List<PartyBoardDTO> pageList = new ArrayList<PartyBoardDTO>();
		int end = list.size() - 1;
		int fine = pageMake.getEndRow() < end ? pageMake.getEndRow() : end;
		for (int i = pageMake.getStartRow(); i <= fine; i++) {
			pageList.add(list.get(i));
		}
		mav.addObject("searchType", searchType);
		mav.addObject("keyword", keyword);
		mav.addObject("ptBoardList", pageList);
		return mav;
	}

	@GetMapping("/matchBoard")
	public ModelAndView matchBoard(@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam HashMap<String, String> param) {
		ModelAndView mav = new ModelAndView();
		List<MatchBoardDTO> mtBoardList = juService.selectMt();
		mav.addObject("boardType", "matchBoard");

		HashMap<String, String> map = param;
		String searchType = map.get("searchType");
		String keyword = map.get("keyword");
		List<MatchBoardDTO> list = new ArrayList<MatchBoardDTO>();
		int total = mtBoardList.size();

		if (searchType == null || searchType.equals("")) {
			mtBoardList.forEach(e -> {
				list.add(e);
			});
		} else if (searchType.equals("title")) {
			for (int i = 0; i < total; i++) {
				if (mtBoardList.get(i).getTitle().contains(keyword)) {
					list.add(mtBoardList.get(i));
				}
			}
		} else if (searchType.equals("content")) {
			for (int i = 0; i < total; i++) {
				if (mtBoardList.get(i).getContent().contains(keyword)) {
					list.add(mtBoardList.get(i));
				}
			}
		} else if (searchType.equals("all")) {
			for (int i = 0; i < total; i++) {
				if (mtBoardList.get(i).getTitle().contains(keyword)
						|| mtBoardList.get(i).getContent().contains(keyword)) {
					list.add(mtBoardList.get(i));
				}
			}
		}

		// 페이징
		total = list.size();
		PageMakeDTO pageMake = new PageMakeDTO(pageNum, total);
		mav.addObject("pageMake", pageMake);
		List<MatchBoardDTO> pageList = new ArrayList<MatchBoardDTO>();
		int end = list.size() - 1;
		int fine = pageMake.getEndRow() < end ? pageMake.getEndRow() : end;
		for (int i = pageMake.getStartRow(); i <= fine; i++) {
			pageList.add(list.get(i));
		}
		mav.addObject("searchType", searchType);
		mav.addObject("keyword", keyword);

		mav.addObject("mtBoardList", pageList);
		return mav;
	}

	@GetMapping("/juWrite/{boardType}")
	public ModelAndView juWrite(@PathVariable("boardType") String boardType) {
		ModelAndView mav = new ModelAndView("/juBoard/juWrite");
		mav.addObject("boardType", boardType);
		return mav;
	}

	// 각 게시판의 글 쓰기 구현
	@PostMapping("/juWrite")
	public String juWrite(JuBoardDTO judto, List<MultipartFile> upload, HttpSession session) {

		// 글 작성 공통으로 구현되는 함수를 이용해 juBoard의 idx값을 받아온다
		int idx = juService.mkJuBoard(judto, upload, session);

		return "redirect:/juBoard/tmView/" + idx + "?pageNum=1&searchType=&keyword=";
	}

	@PostMapping("/sbtiWrite")
	public String sbtiWrite(JuBoardDTO judto, SubstiBoardDTO sbtidto, List<MultipartFile> upload, HttpSession session) {

		// 글 작성 공통으로 구현되는 함수를 실행
		int row = juService.mkJuBoard(judto, upload, session);

		// 공통 구현 함수가 성공하였을 경우 작성된 글 단일 뷰로 넘어갑니다
		if (row != 0) {
			int juBoardMaxIdx = juService.selectJuBoardMaxIdx();
			sbtidto.setJuBoard_idx(juBoardMaxIdx);
			int idx = juService.sbtiInsert(sbtidto);
			return "redirect:/juBoard/sbtiView/" + idx + "?pageNum=1&searchType=&keyword=";
		}

		// 실패시 글쓰기 페이지로 다시 리다이렉트합니다
		return "redirect:/juWrite";
	}

	@PostMapping("/ptyWrite")
	public String ptyWrite(JuBoardDTO judto, PartyBoardDTO ptydto, List<MultipartFile> upload, HttpSession session) {

		// 글 작성 공통으로 구현되는 함수를 실행
		int row = juService.mkJuBoard(judto, upload, session);

		// 공통 구현 함수가 성공하였을 경우 작성된 글 단일 뷰로 넘어갑니다
		if (row != 0) {
			int juBoardMaxIdx = juService.selectJuBoardMaxIdx();
			ptydto.setJuBoard_idx(juBoardMaxIdx);

			int idx = juService.ptyInsert(ptydto);
			return "redirect:/juBoard/ptyView/" + idx + "?pageNum=1&searchType=&keyword=";
		}

		// 실패시 글쓰기 페이지로 다시 리다이렉트합니다
		return "redirect:/juWrite";
	}

	@PostMapping("/mtchWrite")
	public String mtchWrite(JuBoardDTO judto, MatchBoardDTO mtchdto, List<MultipartFile> upload, HttpSession session) {

		// 글 작성 공통으로 구현되는 함수를 실행
		int row = juService.mkJuBoard(judto, upload, session);

		// 공통 구현 함수가 성공하였을 경우 작성된 글 단일 뷰로 넘어갑니다
		if (row != 0) {
			int juBoardMaxIdx = juService.selectJuBoardMaxIdx();
			mtchdto.setJuBoard_idx(juBoardMaxIdx);

			int idx = juService.mtchInsert(mtchdto);
			return "redirect:/juBoard/mtchView/" + idx + "?pageNum=1&searchType=&keyword=";
		}

		// 실패시 글쓰기 페이지로 다시 리다이렉트합니다
		return "redirect:/juWrite";
	}

	// 각 게시판의 단일뷰 구성

	@GetMapping("/tmView/{idx}")
	public ModelAndView tmView(@PathVariable("idx") int idx, int pageNum, @RequestParam HashMap<String, String> param) {
		ModelAndView mav = new ModelAndView("/juBoard/tmView");
		JuBoardDTO tmdto = juService.selectTmOne(idx);
		List<FileListDTO> fileList = juService.selectFileListByIdx(tmdto.getBoard_idx());
		List<ReviewDTO> reviewList = juService.selectReviewByIdx(tmdto.getBoard_idx());
		mav.addObject("tmdto", tmdto);
		mav.addObject("fileList", fileList);
		mav.addObject("reviewList", reviewList);
		HashMap<String, String> map = param;
		String searchType = map.get("searchType");
		String keyword = map.get("keyword");
		mav.addObject("pageNum", pageNum);
		mav.addObject("searchType", searchType);
		mav.addObject("keyword", keyword);
		return mav;
	}

	@GetMapping("/sbtiView/{idx}")
	public ModelAndView sbtiView(@PathVariable("idx") int idx, int pageNum,
			@RequestParam HashMap<String, String> param) {
		ModelAndView mav = new ModelAndView("/juBoard/sbtiView");
		SubstiBoardDTO sbtidto = juService.selectSbtiOne(idx);
		List<FileListDTO> fileList = juService.selectFileListByIdx(sbtidto.getBoard_idx());
		List<ReviewDTO> reviewList = juService.selectReviewByIdx(sbtidto.getBoard_idx());
		mav.addObject("sbtidto", sbtidto);
		mav.addObject("fileList", fileList);
		mav.addObject("reviewList", reviewList);
		HashMap<String, String> map = param;
		String searchType = map.get("searchType");
		String keyword = map.get("keyword");
		mav.addObject("pageNum", pageNum);
		mav.addObject("searchType", searchType);
		mav.addObject("keyword", keyword);
		return mav;
	}

	@GetMapping("/ptyView/{idx}")
	public ModelAndView ptyView(@PathVariable("idx") int idx, int pageNum,
			@RequestParam HashMap<String, String> param) {
		ModelAndView mav = new ModelAndView("/juBoard/ptyView");
		PartyBoardDTO ptydto = juService.selectPtyOne(idx);
		List<FileListDTO> fileList = juService.selectFileListByIdx(ptydto.getBoard_idx());
		List<ReviewDTO> reviewList = juService.selectReviewByIdx(ptydto.getBoard_idx());
		mav.addObject("ptydto", ptydto);
		mav.addObject("fileList", fileList);
		mav.addObject("reviewList", reviewList);
		HashMap<String, String> map = param;
		String searchType = map.get("searchType");
		String keyword = map.get("keyword");
		mav.addObject("pageNum", pageNum);
		mav.addObject("searchType", searchType);
		mav.addObject("keyword", keyword);
		return mav;
	}

	@GetMapping("/mtchView/{idx}")
	public ModelAndView mtchView(@PathVariable("idx") int idx, int pageNum,
			@RequestParam HashMap<String, String> param) {
		ModelAndView mav = new ModelAndView("/juBoard/mtchView");
		MatchBoardDTO mtchdto = juService.selectMtchOne(idx);
		List<FileListDTO> fileList = juService.selectFileListByIdx(mtchdto.getBoard_idx());
		List<ReviewDTO> reviewList = juService.selectReviewByIdx(mtchdto.getBoard_idx());
		mav.addObject("mtchdto", mtchdto);
		mav.addObject("fileList", fileList);
		mav.addObject("reviewList", reviewList);
		HashMap<String, String> map = param;
		String searchType = map.get("searchType");
		String keyword = map.get("keyword");
		mav.addObject("pageNum", pageNum);
		mav.addObject("searchType", searchType);
		mav.addObject("keyword", keyword);
		return mav;
	}

	// 각 게시판의 삭제 구성

	@GetMapping("/tmDel/{idx}")
	public ModelAndView tmDel(@PathVariable("idx") int idx) {
		ModelAndView mav = new ModelAndView("redirect:/juBoard/teamBoard");
		int bdRow = juService.deleteJuBoard(idx);
		juService.deleteBoard(bdRow);
		return mav;
	}

	@GetMapping("/sbtiDel/{idx}")
	public ModelAndView sbtiDel(@PathVariable("idx") int idx) {
		ModelAndView mav = new ModelAndView("redirect:/juBoard/substiBoard");
		int juRow = juService.deleteSbBoard(idx);
		int bdRow = juService.deleteJuBoard(juRow);
		juService.deleteBoard(bdRow);
		return mav;
	}

	@GetMapping("/ptyDel/{idx}")
	public ModelAndView ptyDel(@PathVariable("idx") int idx) {
		ModelAndView mav = new ModelAndView("redirect:/juBoard/partyBoard");
		int juRow = juService.deletePtBoard(idx);
		int bdRow = juService.deleteJuBoard(juRow);
		juService.deleteBoard(bdRow);
		return mav;
	}

	@GetMapping("/mtchDel/{idx}")
	public ModelAndView mtchDel(@PathVariable("idx") int idx) {
		ModelAndView mav = new ModelAndView("redirect:/juBoard/matchBoard");
		int juRow = juService.deleteMtBoard(idx);
		int bdRow = juService.deleteJuBoard(juRow);
		juService.deleteBoard(bdRow);
		return mav;
	}

	// 각 게시판의 수정 구성

	@GetMapping("tmModi/{idx}")
	public ModelAndView tmModi(@PathVariable("idx") int idx) {
		ModelAndView mav = new ModelAndView("/juBoard/tmModi");
		JuBoardDTO tmdto = juService.selectTmOne(idx);
		List<FileListDTO> fileList = juService.selectFileListByIdx(tmdto.getBoard_idx());
		mav.addObject("tmdto", tmdto);
		mav.addObject("fileList", fileList);
		return mav;
	}

	@PostMapping("tmModi/{idx}")
	public ModelAndView tmModi(JuBoardDTO param, List<MultipartFile> upload) {
		ModelAndView mav = new ModelAndView();
		if ("".equals(upload.get(0).getOriginalFilename()) == false) {
			juService.uploading(upload, param.getBoard_idx());
		}
		int row = juService.selectTmOneMd(param);
		if (row != 0) {
			mav.setViewName("redirect:/juBoard/tmView/{idx}?pageNum=1&searchType=&keyword=");
		} else {
			mav.addObject("tmdto", row);
			mav.setViewName("/juBoard/tmModi/{idx}");
		}
		return mav;
	}

	@GetMapping("/sbtiModi/{idx}")
	public ModelAndView sbtiModi(@PathVariable("idx") int idx) {
		ModelAndView mav = new ModelAndView("/juBoard/sbtiModi");
		SubstiBoardDTO sbtidto = juService.selectSbtiOne(idx);
		List<FileListDTO> fileList = juService.selectFileListByIdx(sbtidto.getBoard_idx());
		mav.addObject("sbtidto", sbtidto);
		mav.addObject("fileList", fileList);
		return mav;
	}

	@PostMapping("sbtiModi/{idx}")
	public ModelAndView sbtiModi(SubstiBoardDTO param, List<MultipartFile> upload) {
		ModelAndView mav = new ModelAndView();
		if ("".equals(upload.get(0).getOriginalFilename()) == false) {
			juService.uploading(upload, param.getBoard_idx());
		}
		int row = juService.selectSbOneMd(param);
		if (row != 0) {
			mav.setViewName("redirect:/juBoard/sbtiView/{idx}?pageNum=1&searchType=&keyword=");
		} else {
			mav.addObject("sbtidto", row);
			mav.setViewName("/juBoard/sbtiModi/{idx}");
		}
		return mav;
	}

	@GetMapping("/ptyModi/{idx}")
	public ModelAndView ptyModi(@PathVariable("idx") int idx) {
		ModelAndView mav = new ModelAndView("/juBoard/ptyModi");
		PartyBoardDTO ptydto = juService.selectPtyOne(idx);
		List<FileListDTO> fileList = juService.selectFileListByIdx(ptydto.getBoard_idx());
		mav.addObject("ptydto", ptydto);
		mav.addObject("fileList", fileList);
		return mav;
	}

	@PostMapping("ptyModi/{idx}")
	public ModelAndView ptyModi(PartyBoardDTO param, List<MultipartFile> upload) {
		ModelAndView mav = new ModelAndView();
		if ("".equals(upload.get(0).getOriginalFilename()) == false) {
			juService.uploading(upload, param.getBoard_idx());
		}
		int row = juService.selectPtOneMd(param);
		if (row != 0) {
			mav.setViewName("redirect:/juBoard/ptyView/{idx}?pageNum=1&searchType=&keyword=");
		} else {
			mav.addObject("ptydto", row);
			mav.setViewName("/juBoard/ptyModi/{idx}");
		}
		return mav;
	}

	@GetMapping("/mtchModi/{idx}")
	public ModelAndView mtchModi(@PathVariable("idx") int idx) {
		ModelAndView mav = new ModelAndView("/juBoard/mtchModi");
		MatchBoardDTO mtchdto = juService.selectMtchOne(idx);
		List<FileListDTO> fileList = juService.selectFileListByIdx(mtchdto.getBoard_idx());
		mav.addObject("mtchdto", mtchdto);
		mav.addObject("fileList", fileList);
		return mav;
	}

	@PostMapping("mtchModi/{idx}")
	public ModelAndView mtchModi(MatchBoardDTO param, List<MultipartFile> upload) {
		ModelAndView mav = new ModelAndView();
		if ("".equals(upload.get(0).getOriginalFilename()) == false) {
			juService.uploading(upload, param.getBoard_idx());
		}
		int row = juService.selectMtOneMd(param);
		if (row != 0) {
			mav.setViewName("redirect:/juBoard/mtchView/{idx}?pageNum=1&searchType=&keyword=");
		} else {
			mav.addObject("dto", row);
			mav.setViewName("/juBoard/mtchModi/{idx}");
		}
		return mav;
	}

	// 파일 삭제 구현

	@DeleteMapping("/deleteFile/{board_idx}/{fileName}")
	@ResponseBody
	public boolean deleteFile(@PathVariable("board_idx") String board_idx, @PathVariable("fileName") String fileName) {
		HashMap<String, String> param = new HashMap<>();
		param.put("board_idx", board_idx);
		param.put("fileName", fileName);
		int row = juService.deleteOneFile(param);
		return row != 0;
	}

	// 댓글 작성

	@PostMapping("/mkReview")
	public String mkReview(@RequestParam HashMap<String, String> map, ReviewDTO dto) {
		String pageNum = map.get("pageNum");
		String boardType = map.get("boardType");

		// 단일뷰로 리다이렉트를 위한 idx 받아오기
		int idx = juService.mkReview(dto);
		return "redirect:/juBoard/" + boardType + "/" + idx + "?pageNum=" + pageNum + "&searchType=&keyword=";
	}

	// 댓글 삭제
	@PostMapping("/delRev/{idx}")
	@ResponseBody
	public String delRev(@PathVariable("idx") int idx) {
		String result = "fail";
		int row = juService.delReview(idx);

		if (row != 0) {
			result = "success";
		}
		return result;
	}
}
