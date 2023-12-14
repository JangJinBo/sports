package com.itbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itbank.model.MessageDTO;
import com.itbank.repository.MessageDAO;

@Service
public class MessageService {

	@Autowired
	private MessageDAO messageDAO;

	public List<MessageDTO> selectMyMessage(String receiverId) {
		return messageDAO.selectMyMessage(receiverId);
	}

	public int sendMessage(MessageDTO dto) {
		return messageDAO.sendMessage(dto);
	}

	public int deleteMessage(int idx) {
		return messageDAO.deleteMessage(idx);
	}

	public int readMessage(int idx) {
		return messageDAO.readMessage(idx);
	}

	public int messageCounter(String receiverId) {
		return messageDAO.messageCounter(receiverId);
	}

	public int sendBannedMessage(MessageDTO dto) {
		return messageDAO.sendBannedMessage(dto);
	}

	public int sendSuppBannedMessage(MessageDTO dto) {
		return messageDAO.sendSuppBannedMessage(dto);
	}
}
