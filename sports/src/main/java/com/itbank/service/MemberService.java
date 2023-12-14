package com.itbank.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itbank.component.HashComponent;
import com.itbank.model.AddressDTO;
import com.itbank.model.MemberDTO;
import com.itbank.repository.MemberDAO;

@Service
public class MemberService {

	@Autowired
	private HashComponent hashComponent;
	@Autowired
	private MemberDAO memberDAO;

	public int insertMember(HashMap<String, String> param) {
		int row;
		String salt = hashComponent.getRandomSalt();
		param.put("salt", salt);
		String hash = hashComponent.getHash(param.get("userpw"), salt);
		param.put("userpw", hash);
		row = memberDAO.insertMember(param); // 데이터에 insert
		return row;
	}

	public int maxIdx() {
		return memberDAO.maxIdx();
	}

	public int insertAddress(HashMap<String, String> param) {
		return memberDAO.insertAddress(param);
	}

	public MemberDTO login(MemberDTO user) {
		MemberDTO login = memberDAO.selectOnebyId(user.getUserid());

		if (login != null) {
			String curHash = login.getUserpw(); // 데이터에 저장되어있는 hash(pw)가져옴
			String inputHash = hashComponent.getHash(user.getUserpw(), login.getSalt()); // 입력된 pw과 저장되어 있는 salt로 hash처리
			if (inputHash.equals(curHash)) { // 두 값이 같으면 login을 넘겨준다.
				return login;
			}
		}
		return null;
	}

	public MemberDTO selectOneById(String userid) {
		MemberDTO dto = memberDAO.selectOnebyId(userid);
		return dto;
	}

	public MemberDTO selectOneByNickname(String nickname) {
		MemberDTO dto = memberDAO.selectOnebyNickname(nickname);
		return dto;
	}

	public List<MemberDTO> selectAll(String memberIdx) {
		List<MemberDTO> list = memberDAO.selectAll(memberIdx);
		return list;
	}

	public int updateMember(MemberDTO user) {
		String salt = hashComponent.getRandomSalt();
		String hash = hashComponent.getHash(user.getUserpw(), salt);
		user.setUserpw(hash);
		user.setSalt(salt);
		return memberDAO.updateMember(user);
	}

	public int deleteMember(int maxIdx) {
		return memberDAO.deleteMember(maxIdx);
	}

	public int updateAddr(AddressDTO addressDTO) {
		return memberDAO.updateAddr(addressDTO);
	}

	public MemberDTO selectAddrByMemberIdx(int idx) {
		return memberDAO.selectAddrByMemberIdx(idx);
	}

	public AddressDTO selectAddr(int idx) {
		return memberDAO.selectAddr(idx);
	}

}
