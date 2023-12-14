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

import com.itbank.model.EventBoardDTO;
import com.itbank.model.FileListDTO;
import com.itbank.model.MemberDTO;
import com.itbank.model.PageMakeDTO;
import com.itbank.service.EventService;

@Controller
@RequestMapping("/event")
public class EventController {

	@Autowired
	EventService eventService;

	@GetMapping("/training")
	public ModelAndView trList(@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam HashMap<String, String> param) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, String> map = param;
		String searchType = map.get("searchType");
		String keyword = map.get("keyword");
		List<EventBoardDTO> trList = eventService.trList();
		List<EventBoardDTO> list = new ArrayList<EventBoardDTO>();
		int total = trList.size();

		if (searchType == null || searchType.equals("")) {
			trList.forEach(e -> {
				list.add(e);
			});
		} else if (searchType.equals("title")) {
			for (int i = 0; i < total; i++) {
				if (trList.get(i).getTitle().contains(keyword)) {
					list.add(trList.get(i));
				}
			}
		} else if (searchType.equals("content")) {
			for (int i = 0; i < total; i++) {
				if (trList.get(i).getContent().contains(keyword)) {
					list.add(trList.get(i));
				}
			}
		} else if (searchType.equals("all")) {
			for (int i = 0; i < total; i++) {
				if (trList.get(i).getTitle().contains(keyword) || trList.get(i).getContent().contains(keyword)) {
					list.add(trList.get(i));
				}
			}
		}

		// 페이징
		total = list.size();
		PageMakeDTO pageMake = new PageMakeDTO(pageNum, total);
		mav.addObject("pageMake", pageMake);
		List<EventBoardDTO> pageList = new ArrayList<EventBoardDTO>();
		int end = list.size() - 1;
		int fine = pageMake.getEndRow() < end ? pageMake.getEndRow() : end;
		for (int i = pageMake.getStartRow(); i <= fine; i++) {
			pageList.add(list.get(i));
		}
		mav.addObject("boardType", "training");
		mav.addObject("searchType", searchType);
		mav.addObject("keyword", keyword);
		mav.addObject("trList", pageList);
		return mav;
	}

	@GetMapping("/competition")
	public ModelAndView comList(@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam HashMap<String, String> param) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, String> map = param;
		String searchType = map.get("searchType");
		String keyword = map.get("keyword");
		List<EventBoardDTO> comList = eventService.comList();
		int total = comList.size();
		List<EventBoardDTO> list = new ArrayList<EventBoardDTO>();

		if (searchType == null || searchType.equals("")) {
			comList.forEach(e -> {
				list.add(e);
			});
		} else if (searchType.equals("title")) {
			for (int i = 0; i < total; i++) {
				if (comList.get(i).getTitle().contains(keyword)) {
					list.add(comList.get(i));
				}
			}
		} else if (searchType.equals("content")) {
			for (int i = 0; i < total; i++) {
				if (comList.get(i).getContent().contains(keyword)) {
					list.add(comList.get(i));
				}
			}
		} else if (searchType.equals("all")) {
			for (int i = 0; i < total; i++) {
				if (comList.get(i).getTitle().contains(keyword) || comList.get(i).getContent().contains(keyword)) {
					list.add(comList.get(i));
				}
			}
		}

		// 페이징
		total = list.size();
		PageMakeDTO pageMake = new PageMakeDTO(pageNum, total);
		mav.addObject("pageMake", pageMake);
		List<EventBoardDTO> pageList = new ArrayList<EventBoardDTO>();
		int end = list.size() - 1;
		int fine = pageMake.getEndRow() < end ? pageMake.getEndRow() : end;
		for (int i = pageMake.getStartRow(); i <= fine; i++) {
			pageList.add(list.get(i));
		}
		mav.addObject("boardType", "competition");
		mav.addObject("searchType", searchType);
		mav.addObject("keyword", keyword);
		mav.addObject("comList", pageList);
		return mav;
	}

	@GetMapping("/write/{boardType}")
	public ModelAndView write(@PathVariable("boardType") String boardType) {
		ModelAndView mav = new ModelAndView("/event/write");
		mav.addObject("boardType", boardType);
		return mav;
	}

	@PostMapping("/trWrite")
	public String trWrite(EventBoardDTO dto, List<MultipartFile> upload, HttpSession session) {
		MemberDTO login = (MemberDTO) session.getAttribute("login");
		eventService.trainingInsert(login.getIdx());
		int boardMaxIdx = eventService.selectEventBoardMaxIdx();
		dto.setBoard_idx(boardMaxIdx);

		if ("".equals(upload.get(0).getOriginalFilename()) == false) {
			for (MultipartFile f : upload) {
				FileListDTO filedto = new FileListDTO();
				filedto.setBoard_idx(boardMaxIdx);
				String fileName = eventService.uploadFile(f);
				filedto.setFileName(fileName);
				int fileRow = eventService.insertFile(filedto);
				if (fileRow == 0) {
					eventService.deleteFile(fileName);
				}
			}
		}

		eventService.trInsert(dto);
		return "redirect:/event/training";

	}

	@PostMapping("/comWrite")
	public String comwrite(EventBoardDTO dto, List<MultipartFile> upload, HttpSession session) {
		MemberDTO login = (MemberDTO) session.getAttribute("login");
		eventService.competitionInsert(login.getIdx());
		int boardMaxIdx = eventService.selectEventBoardMaxIdx();
		dto.setBoard_idx(boardMaxIdx);

		if ("".equals(upload.get(0).getOriginalFilename()) == false) {
			for (MultipartFile f : upload) {
				FileListDTO filedto = new FileListDTO();
				filedto.setBoard_idx(boardMaxIdx);
				String fileName = eventService.uploadFile(f);
				filedto.setFileName(fileName);
				int fileRow = eventService.insertFile(filedto);
				if (fileRow == 0) {
					eventService.deleteFile(fileName);
				}
			}
		}
		eventService.comInsert(dto);
		return "redirect:/event/competition";

	}

	@GetMapping("/trview/{idx}")
	public ModelAndView trview(@PathVariable("idx") int idx, int pageNum, @RequestParam HashMap<String, String> param) {
		ModelAndView mav = new ModelAndView("/event/trview");
		HashMap<String, String> map = param;
		String searchType = map.get("searchType");
		String keyword = map.get("keyword");
		EventBoardDTO eventDTO = eventService.selectOne(idx);
		eventDTO.setUsername(eventService.selectUsername(eventDTO.getBoard_idx()).getUsername());
		eventDTO.setUserid(eventService.selectUsername(eventDTO.getBoard_idx()).getUserid());
		List<FileListDTO> fileList = eventService.selectFileListByIdx(eventDTO.getBoard_idx());
		mav.addObject("fileList", fileList);
		mav.addObject("pageNum", pageNum);
		mav.addObject("searchType", searchType);
		mav.addObject("keyword", keyword);
		mav.addObject("eventDTO", eventDTO);
		return mav;
	}

	@GetMapping("/comview/{idx}")
	public ModelAndView comview(@PathVariable("idx") int idx, int pageNum,
			@RequestParam HashMap<String, String> param) {
		ModelAndView mav = new ModelAndView("/event/comview");
		HashMap<String, String> map = param;
		String searchType = map.get("searchType");
		String keyword = map.get("keyword");
		EventBoardDTO eventDTO = eventService.selectOne(idx);
		eventDTO.setUsername(eventService.selectUsername(eventDTO.getBoard_idx()).getUsername());
		eventDTO.setUserid(eventService.selectUsername(eventDTO.getBoard_idx()).getUserid());
		List<FileListDTO> fileList = eventService.selectFileListByIdx(eventDTO.getBoard_idx());
		mav.addObject("fileList", fileList);
		mav.addObject("pageNum", pageNum);
		mav.addObject("searchType", searchType);
		mav.addObject("keyword", keyword);
		mav.addObject("eventDTO", eventDTO);
		return mav;
	}

	@GetMapping("/trDelete/{idx}")
	public ModelAndView trDelete(@PathVariable("idx") int idx) {
		ModelAndView mav = new ModelAndView("redirect:/event/training");
		int eventRow  = eventService.trDeleteBoard(idx);
		if(eventRow != 0) {
			int row = eventService.deleteBoard(eventRow);
		}
		return mav;
	}

	@GetMapping("/comDelete/{idx}")
	public ModelAndView comDelete(@PathVariable("idx") int idx) {
		ModelAndView mav = new ModelAndView("redirect:/event/competition");
		int eventRow = eventService.comDeleteBoard(idx);
		if(eventRow != 0) {
			int row = eventService.deleteBoard(eventRow);
		}
		return mav;
	}

	@GetMapping("/modify/{idx}")
	public ModelAndView modify(@PathVariable("idx") int idx) {
		ModelAndView mav = new ModelAndView("/event/modify");
		EventBoardDTO eventDTO = eventService.selectOne(idx);
		List<FileListDTO> fileList = eventService.selectFileListByIdx(eventDTO.getBoard_idx());
		mav.addObject("fileList", fileList);
		mav.addObject("eventDTO", eventDTO);
		return mav;
	}

	@PostMapping("/modify/{idx}")
	public ModelAndView modify(List<MultipartFile> upload, EventBoardDTO dto) {
		ModelAndView mav = new ModelAndView("/modify");
		int idx = dto.getIdx();

		if ("".equals(upload.get(0).getOriginalFilename()) == false) {
			for (MultipartFile f : upload) {
				FileListDTO filedto = new FileListDTO();
				filedto.setBoard_idx(dto.getBoard_idx());
				String fileName = eventService.uploadFile(f);
				filedto.setFileName(fileName);
				int fileRow = eventService.insertFile(filedto);
				if (fileRow == 0) {
					eventService.deleteFile(fileName);
				}
			}
		}

		eventService.update(dto);

		// 강습 안내 게시글 수정하기
		if (dto.getBoardType().equals("training")) {
			mav.setViewName("redirect:/event/trview/" + idx + "?pageNum=1&searchType=&keyword=");
		}
		// 대회 공지 안내 게시글 수정하기
		else if (dto.getBoardType().equals("competition")) {
			mav.setViewName("redirect:/event/comview/" + idx + "?pageNum=1&searchType=&keyword=");
		}
		return mav;
	}

	@DeleteMapping("/deleteFile/{board_idx}/{fileName}")
	@ResponseBody
	public boolean deleteFile(@PathVariable("board_idx") String board_idx, @PathVariable("fileName") String fileName) {
		HashMap<String, String> param = new HashMap<>();
		param.put("board_idx", board_idx);
		param.put("fileName", fileName);
		int row = eventService.deleteOneFile(param);
		return row != 0;
	}

}
