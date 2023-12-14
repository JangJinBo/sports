package com.itbank.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itbank.component.FileComponent;
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
import com.itbank.repository.SpaceDAO;

@Service
public class SpaceService {

	@Autowired
	private FileComponent fileComponent;
	@Autowired
	private SpaceDAO spaceDAO;

	public int insertBoard(int member_idx) {
		return spaceDAO.insertBoard(member_idx);
	}

	public int selectBoardMaxIdx() {
		return spaceDAO.selectBoardMaxIdx();
	}

	public int insertAddress(HashMap<String, String> map) {
		return spaceDAO.insertAddress(map);
	}

	public int deleteBoard(int idx) {
		List<String> fileNames = spaceDAO.selectFileName(idx);
		if (fileNames != null) {
			for (String fileName : fileNames) {
				fileComponent.delete(fileName);
			}
		}
		return spaceDAO.deleteBoard(idx);
	}

	public int selectAddressMaxIdx() {
		return spaceDAO.selectAddressMaxIdx();
	}

	public int register(HashMap<String, String> map) {
		return spaceDAO.register(map);
	}

	public int deleteAddress(int idx) {
		return spaceDAO.deleteAddress(idx);
	}

	public String uploadFile(MultipartFile f) {
		return fileComponent.upload(f);
	}

	public int insertFile(FileListDTO fileDTO) {
		return spaceDAO.insertFile(fileDTO);
	}

	public void deleteFile(String fileName) {
		fileComponent.delete(fileName);
	}

	public List<SpaceDTO> selectList() {
		return spaceDAO.selectList();
	}

	public SpaceDTO selectOneByIdx(int idx) {
		return spaceDAO.selectOneByIdx(idx);
	}

	public AddressDTO selectAddressByIdx(int idx) {
		return spaceDAO.selectAddressByIdx(idx);
	}

	public List<SpaceDTO> selectListByAddressDTO(AddressDTO addressDTO) {
		return spaceDAO.selectListByAddressDTO(addressDTO);
	}

	public AddressDTO selectSpaceAddressByIdx(int idx) {
		return spaceDAO.selectSpaceAddressByIdx(idx);
	}

	public List<FileListDTO> selectFileListByIdx(int board_idx) {
		return spaceDAO.selectFileListByIdx(board_idx);
	}

	public int selectScrapByIdx(HashMap<String, Integer> param) {
		return spaceDAO.selectScrapByIdx(param);
	}

	public int insertScrap(HashMap<String, Integer> param) {
		return spaceDAO.insertScrap(param);
	}

	public int deleteScrap(HashMap<String, Integer> param) {
		return spaceDAO.deleteScrap(param);
	}

	public List<ReviewDTO> selectReviewListByIdx(int board_idx) {
		return spaceDAO.selectReviewListByIdx(board_idx);
	}

	public BoardDTO selectBoardByIdx(int board_idx) {
		return spaceDAO.selectBoardByIdx(board_idx);
	}

	public int selectSpaceMaxIdx() {
		return spaceDAO.selectSpaceMaxIdx();
	}

	public int selectspaceBoardIdx(int idx) {
		return spaceDAO.selectspaceBoardIdx(idx);
	}

	public int selectReserveForDelete(int space_idx) {
		return spaceDAO.selectReserveForDelete(space_idx);
	}

	public int selectAddressIdx(int idx) {
		return spaceDAO.selectAddressIdx(idx);
	}

	public int deleteOneFile(HashMap<String, String> param) {
		fileComponent.delete(param.get("fileName"));
		return spaceDAO.deleteFileList(param);
	}

	public SpaceDTO selectOneByspaceIdx(int space_idx) {
		return spaceDAO.selectOneByspaceIdx(space_idx);
	}

	public int updateAddress(HashMap<String, String> map) {
		return spaceDAO.updateAddress(map);
	}

	public int updateSpace(HashMap<String, String> map) {
		return spaceDAO.updateSpace(map);
	}

	public List<ReserveDTO> selectHolidayList(int space_idx) {
		return spaceDAO.selectHolidayList(space_idx);
	}

	public int insertHoliday(ReserveDTO dto) {
		return spaceDAO.insertHoliday(dto);
	}

	public int checkReserve(ReserveDTO dto) {
		return spaceDAO.checkReserve(dto);
	}

	public int insertReserve(ReserveDTO dto) {
		return spaceDAO.insertReserve(dto);
	}

	public void deleteHoliday(int idx) {
		spaceDAO.deleteHoliday(idx);
	}

	public int holidayCheck(ReserveDTO dto) {
		return spaceDAO.holidayCheck(dto);
	}

	public int deleteholidayBySpaceIdx(int space_idx) {
		return spaceDAO.deleteholidayBySpaceIdx(space_idx);
	}

	public SpaceDTO selectSpaceByBoardIdx(int board_idx) {
		return spaceDAO.selectSpaceByBoardIdx(board_idx);
	}

	public int insertReview(HashMap<String, String> param) {
		return spaceDAO.insertReview(param);
	}

	public int payInsert(PayDTO dto) {
		return spaceDAO.payInsert(dto);
	}

	public List<ReserveDTO> selectAllReserveByMemberIdx(int idx) {
		// 현재 시간 이후의 것을 spaceDTO.status를 2로 변경하기
		spaceDAO.pastReservations();
		return spaceDAO.selectAllReserveByMemberIdx(idx);
	}

	public List<ReserveDTO> selectAllStartTime(HashMap<String, Object> param2) {
		return spaceDAO.selectAllStartTime(param2);
	}

	public ReserveDTO selectByReserveIdx(int idx) {
		return spaceDAO.selectByReserveIdx(idx);
	}

	public int deleteReserveByIdx(int idx) {
		return spaceDAO.deleteReserveByIdx(idx);
	}

	public int deletePayIdx(String payIdx) {
		return spaceDAO.deletePayIdx(payIdx);
	}

	public List<ScrapDTO> selectAllScrapByMemberIdx(int idx) {
		return spaceDAO.selectAllScrapByMemberIdx(idx);
	}

	public List<String> selectCategoryList() {
		return spaceDAO.selectCategoryList();
	}

	public int deleteReview(int idx) {
		return spaceDAO.deleteReview(idx);
	}

	public int insertFacility(HashMap<String, String> param) {
		return spaceDAO.insertFacility(param);
	}

	public List<FacilityDTO> selectFacilityListBySpaceIdx(int space_idx) {
		return spaceDAO.selectFacilityListBySpaceIdx(space_idx);
	}

	public MemberDTO selectMemberByIdx(int member_idx) {
		return spaceDAO.selectMemberByIdx(member_idx);
	}

	public int deleteFacility(int idx) {
		return spaceDAO.deleteFacility(idx);
	}

	public List<ReserveDTO> selectAllReserveBySuppMemberIdx(int idx) throws ParseException {
		List<ReserveDTO> list = spaceDAO.selectAllReserveBySuppMemberIdx(idx);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (ReserveDTO dto : list) {
			java.util.Date endTime = sdf.parse(dto.getEndTime());
			endTime.setTime(endTime.getTime() + (1000 * 60));
			dto.setEndTime(sdf.format(endTime));
		}
		return list;
	}

	public List<SpaceDTO> selectAllSpaceByMemberIdx(int idx) {
		return spaceDAO.selectAllSpaceByMemberIdx(idx);
	}

	public List<SpaceDTO> selectAllPastReseveSpaceByMemberIdx(int idx) {
		return spaceDAO.selectAllPastReseveSpaceByMemberIdx(idx);
	}

}