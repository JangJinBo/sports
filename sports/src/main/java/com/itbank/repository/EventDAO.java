package com.itbank.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itbank.model.EventBoardDTO;
import com.itbank.model.FileListDTO;

@Repository
public interface EventDAO {

	int trainingInsert(int member_idx);

	int competitionInsert(int member_idx);

	int trInsert(EventBoardDTO dto);

	int comInsert(EventBoardDTO dto);

	int selectEventBoardMaxIdx();

	EventBoardDTO selectOne(int idx);

	List<EventBoardDTO> trList();

	List<EventBoardDTO> comList();

	int insertFile(FileListDTO filedto);

	int trDeleteBoard(int idx);

	int comDeleteBoard(int idx);

	int trUpdate(EventBoardDTO dto);

	int comUpdate(EventBoardDTO dto);
	
	int deleteFileList(HashMap<String, String> param);

	List<FileListDTO> selectFileListByIdx(int board_idx);

	EventBoardDTO selectUsername(int idx);

	int update(EventBoardDTO dto);

	List<String> selectFileName(int idx);

	int selectBoardIdx(int idx);

	int deleteBoard(int eventRow);

}
