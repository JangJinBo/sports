package com.itbank.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itbank.component.FileComponent;
import com.itbank.model.EventBoardDTO;
import com.itbank.model.FileListDTO;
import com.itbank.repository.EventDAO;

@Service
public class EventService {

	@Autowired
	private FileComponent fileComponent;
	@Autowired
	private EventDAO eventDAO;

	public int trainingInsert(int member_idx) {
		return eventDAO.trainingInsert(member_idx);
	}

	public int competitionInsert(int member_idx) {
		return eventDAO.competitionInsert(member_idx);
	}

	public int selectEventBoardMaxIdx() {
		return eventDAO.selectEventBoardMaxIdx();
	}

	public int trInsert(EventBoardDTO dto) {
		return eventDAO.trInsert(dto);
	}

	public int comInsert(EventBoardDTO dto) {
		return eventDAO.comInsert(dto);
	}

	public EventBoardDTO selectOne(int idx) {
		return eventDAO.selectOne(idx);
	}

	public List<EventBoardDTO> trList() {
		return eventDAO.trList();
	}

	public List<EventBoardDTO> comList() {
		return eventDAO.comList();
	}

	public int deleteOneFile(HashMap<String, String> param) {
		fileComponent.delete(param.get("fileName"));
		return eventDAO.deleteFileList(param);
	}

	public String uploadFile(MultipartFile f) {
		return fileComponent.upload(f);
	}

	public int insertFile(FileListDTO filedto) {
		return eventDAO.insertFile(filedto);
	}

	public void deleteFile(String fileName) {
		fileComponent.delete(fileName);
	}

	public int trDeleteBoard(int idx) {
		int board_idx = eventDAO.selectBoardIdx(idx);
		 int row = eventDAO.trDeleteBoard(idx);
		 if(row != 0) {
			 return board_idx;
		 }
		 return 0;
	}

	public int comDeleteBoard(int idx) {
		int board_idx = eventDAO.selectBoardIdx(idx);
		int row = eventDAO.comDeleteBoard(idx);
		 if(row != 0) {
			 return board_idx;
		 }
		 return 0;
	}
	
	public int trUpdate(EventBoardDTO dto) {
		return eventDAO.trUpdate(dto);
	}

	public int comUpdate(EventBoardDTO dto) {
		return eventDAO.comUpdate(dto);
	}

	public List<FileListDTO> selectFileListByIdx(int board_idx) {
		return eventDAO.selectFileListByIdx(board_idx);
	}

	public EventBoardDTO selectUsername(int board_idx) {
		return eventDAO.selectUsername(board_idx);
	}

	public int update(EventBoardDTO dto) {
		return eventDAO.update(dto);
	}

	public int deleteBoard(int eventRow) {
		List<String> fileNames = eventDAO.selectFileName(eventRow);
		System.out.println(fileNames);
		if(fileNames != null) {
			for(String fileName : fileNames) {
				fileComponent.delete(fileName);
			}
		}
		return eventDAO.deleteBoard(eventRow);
	}
}
