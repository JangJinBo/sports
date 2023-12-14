package com.itbank.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itbank.model.BoardDTO;
import com.itbank.model.FileListDTO;
import com.itbank.model.JuBoardDTO;
import com.itbank.model.MatchBoardDTO;
import com.itbank.model.PartyBoardDTO;
import com.itbank.model.ReviewDTO;
import com.itbank.model.SubstiBoardDTO;

@Repository
public interface JuDAO {

	int juInsert(BoardDTO bdDto);

	int tmInsert(JuBoardDTO tmdto);
	
	int mtchInsert(MatchBoardDTO mtchdto);
	
	int sbtiInsert(SubstiBoardDTO sbtidto);
	
	int ptyInsert(PartyBoardDTO ptydto);
	
	int selectBoardMaxIdx();

	int selectJuBoardMaxIdx();

	int selectMtchBoardMaxIdx();
	
	int selectSbtiBoardMaxIdx();
	
	int selectPtyBoardMaxIdx();
	
	JuBoardDTO selectTmOne(int idx);

	MatchBoardDTO selectMtchOne(int idx);
	
	SubstiBoardDTO selectSbtiOne(int idx);
	
	PartyBoardDTO selectPtyOne(int idx);
	
	int insertJuFile(FileListDTO filedto);
	
	List<String> selectFileName(int bdRow);
	
	List<FileListDTO> selectFileListByIdx(int board_idx);

	int deleteFileList(HashMap<String, String> param);

	List<JuBoardDTO> selectTm();

	List<SubstiBoardDTO> selectSb();

	List<MatchBoardDTO> selectMt();
	
	List<PartyBoardDTO> selectPt();
		
	int selBdidxJu(int idx);

	int deleteJuBoard(int idx);

	int deleteBoard(int bdRow);

	int selJuidxMt(int idx);

	int deleteMtBoard(int idx);

	int selJuidxSb(int idx);

	int deleteSbBoard(int idx);

	int selJuidxPt(int idx);

	int deletePtBoard(int idx);

	int selectTmOneMd(JuBoardDTO param);

	int selectMtOneMd(MatchBoardDTO param);

	int selectSbOneMd(SubstiBoardDTO param);

	int selectPtOneMd(PartyBoardDTO ptydto);

	List<ReviewDTO> selectReviewByIdx(int board_idx);

	int mkReview(ReviewDTO dto);

	String selectBoardType(int board_idx);

	int selSbIdx(int juIdx);

	int selPtIdx(int juIdx);

	int selMtIdx(int juIdx);

	int selJuIdx(int board_idx);

	int selectMemIdx(String userid);

	int delReview(int idx);

}
