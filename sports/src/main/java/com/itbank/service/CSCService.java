package com.itbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itbank.model.AdminBoardDTO;
import com.itbank.model.InquiryDTO;
import com.itbank.model.MessageDTO;
import com.itbank.repository.AdminBoardDAO;
import com.itbank.repository.InquiryDAO;

@Service
public class CSCService {

	@Autowired
	private AdminBoardDAO adminboardDAO;
	@Autowired
	private InquiryDAO inquiryDAO;

	public List<AdminBoardDTO> selectNoticeList() {

		return adminboardDAO.selectNoticeList();
	}

	public List<AdminBoardDTO> selectFaqList() {
		return adminboardDAO.selectFaqList();
	}

	public List<InquiryDTO> selectInquiryList(String sendId) {
		return inquiryDAO.selectInquiryList(sendId);
	}

	public int sendInquiry(InquiryDTO dto) {
		return inquiryDAO.sendInquiry(dto);
	}

	public AdminBoardDTO selectOne(int idx) {
		return adminboardDAO.selectOne(idx);
	}

	public InquiryDTO selectInquiryOne(int idx) {
		return inquiryDAO.selectInquiryOne(idx);
	}

	public int sendDeclaration(MessageDTO dto) {
		return inquiryDAO.sendDeclaration(dto);
	}

	public int delete(int idx) {
		return adminboardDAO.delete(idx);
	}

	public int update(AdminBoardDTO dto) {
		return adminboardDAO.update(dto);
	}

}
