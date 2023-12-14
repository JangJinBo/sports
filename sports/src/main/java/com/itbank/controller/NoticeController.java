package com.itbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.itbank.service.NoticeService;

@Controller
public class NoticeController {

	@Autowired NoticeService noticeService;
}
