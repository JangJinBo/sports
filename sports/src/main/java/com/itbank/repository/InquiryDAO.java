package com.itbank.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itbank.model.InquiryDTO;
import com.itbank.model.MessageDTO;

@Repository
public interface InquiryDAO {

	List<InquiryDTO> selectInquiryList(String sendId);

	int sendInquiry(InquiryDTO dto);

	InquiryDTO selectInquiryOne(int idx);

	int sendDeclaration(MessageDTO dto);
}
