package com.itbank.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itbank.model.MessageDTO;

@Repository
public interface MessageDAO {

	List<MessageDTO> selectMyMessage(String receiverId);

	int sendMessage(MessageDTO dto);

	int deleteMessage(int idx);

	int readMessage(int idx);

	int messageCounter(String receiverId);

	int sendBannedMessage(MessageDTO dto);

	int sendSuppBannedMessage(MessageDTO dto);

}
