package com.itbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itbank.repository.NoticeDAO;


@Service
public class NoticeService {

	@Autowired private NoticeDAO noticeDAO;
}
