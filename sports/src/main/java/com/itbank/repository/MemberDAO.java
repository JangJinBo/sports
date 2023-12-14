package com.itbank.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itbank.model.AddressDTO;
import com.itbank.model.MemberDTO;

@Repository
public interface MemberDAO {

	int insertMember(HashMap<String, String> param);

	MemberDTO selectOnebyId(String userid);

	MemberDTO selectOnebyNickname(String nickname);

	List<MemberDTO> selectAll(String memberIdx);

	int updateMember(MemberDTO user);

	int maxIdx();

	int insertAddress(HashMap<String, String> param);

	int deleteMember(int maxIdx);


	int updateAddr(AddressDTO addressDTO);

	MemberDTO selectAddrByMemberIdx(int idx);

	AddressDTO selectAddr(int idx);

}
