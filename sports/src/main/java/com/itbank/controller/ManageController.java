package com.itbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.itbank.service.ManageService;

@Controller
public class ManageController {

	@Autowired ManageService manageService;
}
