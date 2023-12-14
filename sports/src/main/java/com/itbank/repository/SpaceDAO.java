package com.itbank.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itbank.model.AddressDTO;
import com.itbank.model.BoardDTO;
import com.itbank.model.FacilityDTO;
import com.itbank.model.FileListDTO;
import com.itbank.model.MemberDTO;
import com.itbank.model.PayDTO;
import com.itbank.model.ReserveDTO;
import com.itbank.model.ReviewDTO;
import com.itbank.model.ScrapDTO;
import com.itbank.model.SpaceDTO;

@Repository
public interface SpaceDAO {

	int insertBoard(int member_idx);

	int selectBoardMaxIdx();

	int insertAddress(HashMap<String, String> map);

	int deleteBoard(int idx);

	int selectAddressMaxIdx();

	int register(HashMap<String, String> map);

	int deleteAddress(int idx);

	int insertFile(FileListDTO fileDTO);

	List<SpaceDTO> selectList();

	SpaceDTO selectOneByIdx(int idx);

	AddressDTO selectAddressByIdx(int idx);

	List<SpaceDTO> selectListByAddressDTO(AddressDTO addressDTO);

	AddressDTO selectSpaceAddressByIdx(int idx);

	List<FileListDTO> selectFileListByIdx(int board_idx);

	int selectScrapByIdx(HashMap<String, Integer> param);

	int insertScrap(HashMap<String, Integer> param);

	int deleteScrap(HashMap<String, Integer> param);

	List<ReviewDTO> selectReviewListByIdx(int board_idx);

	BoardDTO selectBoardByIdx(int board_idx);

	int selectSpaceMaxIdx();

	int selectspaceBoardIdx(int idx);

	int selectReserveForDelete(int space_idx);

	List<String> selectFileName(int idx);

	int selectAddressIdx(int idx);

	int deleteFileList(HashMap<String, String> param);

	SpaceDTO selectOneByspaceIdx(int space_idx);

	int updateAddress(HashMap<String, String> map);

	int updateSpace(HashMap<String, String> map);

	List<ReserveDTO> selectHolidayList(int space_idx);

	int insertHoliday(ReserveDTO dto);

	int checkReserve(ReserveDTO dto);

	int insertReserve(ReserveDTO dto);

	void deleteHoliday(int idx);

	int holidayCheck(ReserveDTO dto);

	int deleteholidayBySpaceIdx(int space_idx);

	SpaceDTO selectSpaceByBoardIdx(int board_idx);

	int insertReview(HashMap<String, String> param);

	int payInsert(PayDTO dto);

	List<ReserveDTO> selectAllReserveByMemberIdx(int idx);

	List<ReserveDTO> selectAllStartTime(HashMap<String, Object> param2);

	ReserveDTO selectByReserveIdx(int idx);

	int deleteReserveByIdx(int idx);

	int deletePayIdx(String payIdx);

	List<ScrapDTO> selectAllScrapByMemberIdx(int idx);

	List<String> selectCategoryList();

	int deleteReview(int idx);

	int insertFacility(HashMap<String, String> param);

	List<FacilityDTO> selectFacilityListBySpaceIdx(int space_idx);

	MemberDTO selectMemberByIdx(int member_idx);

	int deleteFacility(int idx);

	List<ReserveDTO> selectAllReserveBySuppMemberIdx(int idx);

	List<SpaceDTO> selectAllSpaceByMemberIdx(int idx);

	void pastReservations();

	List<SpaceDTO> selectAllPastReseveSpaceByMemberIdx(int idx);
}
