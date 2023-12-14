package com.itbank.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itbank.model.AdminBoardDTO;
import com.itbank.model.InquiryDTO;
import com.itbank.model.MemberDTO;
import com.itbank.model.MessageDTO;
import com.itbank.repository.AdminDAO;

@Service
public class AdminService {

	@Autowired
	private AdminDAO adminDAO;

	public List<MemberDTO> searchMember() {
		return adminDAO.searchMember();
	}

	public List<MessageDTO> getBannedList() {
		return adminDAO.selectBannedList();
	}

	public int adminWrite(AdminBoardDTO noticeDTO) {
		return adminDAO.adminWrite(noticeDTO);
	}

	public int changeBlock(HashMap<String, String> param) {
		return adminDAO.changeBlock(param);
	}

	public List<MessageDTO> getMessageDeclar() {
		return adminDAO.getMessageDeclar();
	}

	public List<InquiryDTO> selectInquiryList() {
		return adminDAO.selectInquiryList();
	}

	public int replyInquiry(InquiryDTO dto) {
		return adminDAO.replyInquiry(dto);
	}

	public int doBlock(int idx) {
		return adminDAO.doBlock(idx);
	}

	public int unDoBlock(int idx) {
		return adminDAO.unDoBlock(idx);
	}

	public String selectuseridByIdx(int idx) {
		return adminDAO.selectuseridByIdx(idx);
	}

	public int sendMessageBan(MessageDTO messageDTO) {
		return adminDAO.sendMessageBan(messageDTO);
	}


}
