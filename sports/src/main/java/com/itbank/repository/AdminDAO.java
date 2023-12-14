package com.itbank.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itbank.model.AdminBoardDTO;
import com.itbank.model.InquiryDTO;
import com.itbank.model.MemberDTO;
import com.itbank.model.MessageDTO;

@Repository
public interface AdminDAO {

	List<MemberDTO> searchMember();

	List<MessageDTO> selectBannedList();

	int adminWrite(AdminBoardDTO noticeDTO);

	int changeBlock(HashMap<String, String> param);

	List<MessageDTO> getMessageDeclar();
	
	List<InquiryDTO> selectInquiryList();

	int replyInquiry(InquiryDTO dto);

	int doBlock(int idx);

	int unDoBlock(int idx);

	String selectuseridByIdx(int idx);

	int sendMessageBan(MessageDTO messageDTO);

}
