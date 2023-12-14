package com.itbank.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itbank.model.AdminBoardDTO;

@Repository
public interface AdminBoardDAO {

	List<AdminBoardDTO> selectNoticeList();

	List<AdminBoardDTO> selectFaqList();

	AdminBoardDTO selectOne(int idx);

	int delete(int idx);

	int update(AdminBoardDTO dto);

}
