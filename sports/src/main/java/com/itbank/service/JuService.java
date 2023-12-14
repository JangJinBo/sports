package com.itbank.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itbank.component.FileComponent;
import com.itbank.model.BoardDTO;
import com.itbank.model.FileListDTO;
import com.itbank.model.JuBoardDTO;
import com.itbank.model.MatchBoardDTO;
import com.itbank.model.MemberDTO;
import com.itbank.model.PartyBoardDTO;
import com.itbank.model.ReviewDTO;
import com.itbank.model.SubstiBoardDTO;
import com.itbank.repository.JuDAO;

@Service
public class JuService {

	@Autowired
	private FileComponent fileComponent;
	@Autowired
	private JuDAO juDAO;

	public int juInsert(BoardDTO bdDto) {

		// juboard 생성전 board table 생성
		return juDAO.juInsert(bdDto);
	}

	// 글 작성 공통으로 작동하는 함수
	public int mkJuBoard(JuBoardDTO judto, List<MultipartFile> upload, HttpSession session) {

		// 보드에 멤버idx를 넣기 위해 세션에서 로그인값을 받아 온다
		MemberDTO login = (MemberDTO) session.getAttribute("login");

		BoardDTO bdDto = new BoardDTO();
		bdDto.setMember_idx(login.getIdx());
		bdDto.setBoardType(judto.getBoardType());
		juInsert(bdDto);

		// JuBoardDTO에 비어있는 board_idx를 삽입하기 위해 가장 최신의 board_idx를 불러온다
		int boardMaxIdx = selectBoardMaxIdx();
		judto.setBoard_idx(boardMaxIdx);

		// upload파일의 크기가 0일 경우 미생성함
		if ("".equals(upload.get(0).getOriginalFilename()) == false) {
			for (MultipartFile f : upload) {
				FileListDTO filedto = new FileListDTO();
				filedto.setBoard_idx(boardMaxIdx);
				String fileName = uploadFile(f);
				filedto.setFileName(fileName);
				int fileRow = insertJuFile(filedto);
				if (fileRow == 0) {
					deleteFile(fileName);
				}
			}
		}

		// juBoard의 요소를 juBoard에 삽입한다
		return tmInsert(judto);
	}

	public int tmInsert(JuBoardDTO tmdto) {

		int idx = 0;

		// juBoard 요소를 juBoard table에 삽입한다
		int row = juDAO.tmInsert(tmdto);

		// 삽입 성공했을 경우 JuBoard 테이블에서 가장 최근에 만들어진 idx값을 보낸다
		if (row != 0) {
			idx = juDAO.selectJuBoardMaxIdx();
		}
		return idx;
	}

	public int mtchInsert(MatchBoardDTO mtchdto) {

		int idx = 0;

		// matchBoard 요소를 matchBoard table에 삽입한다
		int row = juDAO.mtchInsert(mtchdto);

		// 삽입 성공했을 경우 matchBoard 테이블에서 가장 최근에 만들어진 idx값을 보낸다
		if (row != 0) {
			idx = juDAO.selectMtchBoardMaxIdx();
		}
		return idx;
	}

	public int sbtiInsert(SubstiBoardDTO sbtidto) {
		int idx = 0;
		int row = juDAO.sbtiInsert(sbtidto);
		if (row != 0) {
			idx = juDAO.selectSbtiBoardMaxIdx();
		}
		return idx;
	}

	public int ptyInsert(PartyBoardDTO ptydto) {
		int idx = 0;
		int row = juDAO.ptyInsert(ptydto);
		if (row != 0) {
			idx = juDAO.selectPtyBoardMaxIdx();
		}
		return idx;
	}

	// 테이블 따라 인덱스값 반환하는 함수들
	public int selectBoardMaxIdx() {
		return juDAO.selectBoardMaxIdx();
	}

	public int selectJuBoardMaxIdx() {
		return juDAO.selectJuBoardMaxIdx();
	}

	// 각 게시판들의 단일 뷰
	public JuBoardDTO selectTmOne(int idx) {
		return juDAO.selectTmOne(idx);
	}

	public MatchBoardDTO selectMtchOne(int idx) {
		return juDAO.selectMtchOne(idx);
	}

	public SubstiBoardDTO selectSbtiOne(int idx) {
		return juDAO.selectSbtiOne(idx);
	}

	public PartyBoardDTO selectPtyOne(int idx) {
		return juDAO.selectPtyOne(idx);
	}

	// 각 게시판들의 리스트
	public List<JuBoardDTO> selectTm() {
		return juDAO.selectTm();
	}

	public List<SubstiBoardDTO> selectSb() {
		return juDAO.selectSb();
	}

	public List<MatchBoardDTO> selectMt() {
		return juDAO.selectMt();
	}

	public List<PartyBoardDTO> selectPt() {
		return juDAO.selectPt();
	}

	// 매칭, 용병, 파티 모집 테이블 게시글 삭제 함수들

	public int deleteMtBoard(int idx) {

		// 팀매칭 게시글을 삭제하기 전 juBoard_idx를 따로 저장해둔다
		int juBdIdxMt = juDAO.selJuidxMt(idx);

		// 삭제 성공적으로 마쳤을 경우에만 juBoard_idx를 반환한다
		int row = juDAO.deleteMtBoard(idx);
		if (row != 0) {
			return juBdIdxMt;
		}

		return 0;
	}

	public int deleteSbBoard(int idx) {

		// 용병모집 게시글을 삭제하기 전 juBoard_idx를 따로 저장해둔다
		int juBdIdxSb = juDAO.selJuidxSb(idx);

		// 삭제 성공적으로 마쳤을 경우에만 juBoard_idx를 반환한다
		int row = juDAO.deleteSbBoard(idx);
		if (row != 0) {
			return juBdIdxSb;
		}
		return 0;
	}

	public int deletePtBoard(int idx) {

		// 파티모집 게시글을 삭제하기 전 juBoard_idx를 따로 저장해둔다
		int juBdIdxPt = juDAO.selJuidxPt(idx);

		// 삭제 성공적으로 마쳤을 경우에만 juBoard_idx를 반환한다
		int row = juDAO.deletePtBoard(idx);
		if (row != 0) {
			return juBdIdxPt;
		}
		return 0;
	}

	public int deleteJuBoard(int idx) {

		// juBoard를 삭제하기전 부모 table인 board 테이블의 idx를 저장한다
		int juBoardidx = juDAO.selBdidxJu(idx);

		// juBoard의 게시글이 성공적으로 삭제했을 경우에만 board_idx를 반환한다
		int row = juDAO.deleteJuBoard(idx);

		if (row != 0) {
			return juBoardidx;
		}
		return 0;
	}

	public int deleteBoard(int bdRow) {

		// DB에 파일이 있는 지 여부를 확인한다
		List<String> fileList = juDAO.selectFileName(bdRow);
		if (fileList != null) {
			// fileList을 받아왔을 때 null이 아닌 경우 테이블 내부에 board_idx가 동일한 파일들을 삭제한다
			for (String fileName : fileList) {
				fileComponent.delete(fileName);
			}
		}
		// 그후 board 테이블에 있는 게시글 삭제를 시작한다
		return juDAO.deleteBoard(bdRow);
	}

	// juBoard 테이블 내부의 게시 내용 수정하기
	public int selectTmOneMd(JuBoardDTO param) {
		return juDAO.selectTmOneMd(param);
	}

	// matchBoard 테이블 내부의 게시 내용 수정하기
	public int selectMtOneMd(MatchBoardDTO param) {

		int row = 0;
		JuBoardDTO judto = new JuBoardDTO();
		judto.setIdx(param.getJuBoard_idx());
		judto.setTitle(param.getTitle());
		judto.setGender(param.getGender());
		judto.setAge(param.getAge());
		judto.setContent(param.getContent());
		judto.setCategory(param.getCategory());
		judto.setMainarea(param.getMainarea());
		judto.setUsername(param.getUsername());

		// juBoard 테이블 내부 내용 수정
		int juRow = juDAO.selectTmOneMd(judto);

		// juBoard 내용 수정이 성공적으로 완료되었을 경우
		if (juRow != 0) {

			// matchBoard 내용 수정을 들어간다
			MatchBoardDTO mtchdto = new MatchBoardDTO();
			mtchdto.setIdx(param.getIdx());
			mtchdto.setPlace(param.getPlace());
			mtchdto.setSchedule(param.getSchedule());
			row = juDAO.selectMtOneMd(mtchdto);
		}
		return row;
	}

	// substiBoard 테이블 내부의 게시 내용 수정하기
	public int selectSbOneMd(SubstiBoardDTO param) {
		int row = 0;
		JuBoardDTO judto = new JuBoardDTO();
		judto.setIdx(param.getJuBoard_idx());
		judto.setTitle(param.getTitle());
		judto.setGender(param.getGender());
		judto.setAge(param.getAge());
		judto.setContent(param.getContent());
		judto.setCategory(param.getCategory());
		judto.setMainarea(param.getMainarea());
		judto.setUsername(param.getUsername());

		// juBoard 테이블 내부 내용 수정
		int juRow = juDAO.selectTmOneMd(judto);

		if (juRow != 0) {

			// substiBoard 내용 수정을 들어간다
			SubstiBoardDTO sbtidto = new SubstiBoardDTO();
			sbtidto.setIdx(param.getIdx());
			sbtidto.setJoinPrice(param.getJoinPrice());
			sbtidto.setPlace(param.getPlace());
			sbtidto.setSchedule(param.getSchedule());
			row = juDAO.selectSbOneMd(sbtidto);
		}
		return row;
	}

	// partyBoard 테이블 내부의 게시 내용 수정하기
	public int selectPtOneMd(PartyBoardDTO param) {
		int row = 0;
		JuBoardDTO judto = new JuBoardDTO();
		judto.setIdx(param.getJuBoard_idx());
		judto.setTitle(param.getTitle());
		judto.setGender(param.getGender());
		judto.setAge(param.getAge());
		judto.setContent(param.getContent());
		judto.setCategory(param.getCategory());
		judto.setMainarea(param.getMainarea());
		judto.setUsername(param.getUsername());

		// juBoard 테이블 내부 내용 수정
		int juRow = juDAO.selectTmOneMd(judto);

		if (juRow != 0) {

			// partyBoard 내용 수정을 들어간다
			PartyBoardDTO ptydto = new PartyBoardDTO();
			ptydto.setIdx(param.getIdx());
			ptydto.setMaxCapacity(param.getMaxCapacity());
			ptydto.setPlace(param.getPlace());
			ptydto.setSchedule(param.getSchedule());
			row = juDAO.selectPtOneMd(ptydto);
		}
		return row;
	}

	// 파일 업로드 , 파일 삽입, 파일 삭제, 파일 dto 리스트 불러오기
	public String uploadFile(MultipartFile f) {
		return fileComponent.upload(f);
	}

	public int insertJuFile(FileListDTO filedto) {
		return juDAO.insertJuFile(filedto);
	}

	public void deleteFile(String fileName) {
		fileComponent.delete(fileName);
	}

	public List<FileListDTO> selectFileListByIdx(int board_idx) {
		return juDAO.selectFileListByIdx(board_idx);
	}

	public int deleteOneFile(HashMap<String, String> param) {
		fileComponent.delete(param.get("fileName"));
		return juDAO.deleteFileList(param);
	}

	// 업로드 실행하는 함수 모음
	public void uploading(List<MultipartFile> upload, int board_idx) {
		for (MultipartFile f : upload) {
			FileListDTO filedto = new FileListDTO();
			filedto.setBoard_idx(board_idx);
			String fileName = uploadFile(f);
			filedto.setFileName(fileName);
			int fileRow = insertJuFile(filedto);
			if (fileRow == 0) {
				deleteFile(fileName);
			}
		}
	}

	// 댓글 목록 불러오기

	public List<ReviewDTO> selectReviewByIdx(int board_idx) {
		return juDAO.selectReviewByIdx(board_idx);
	}

	// 댓글 생성하기

	public int mkReview(ReviewDTO dto) {

		// member_idx를 찾은 후
		int memIdx = juDAO.selectMemIdx(dto.getUserid());

		// 댓글을 생성한다
		dto.setMember_idx(memIdx);
		int row = juDAO.mkReview(dto);
		int idx = 0;

		// 댓글 생성에 성공한 경우
		if (row != 0) {

			// 우선적으로 생성된 댓글의 상위 보드타입을 판별한다
			String boardType = juDAO.selectBoardType(dto.getBoard_idx());

			// 보드 타입을 알아낸 후 그에 따른 idx값을 찾는다
			if (boardType.equals("teamBoard")) {
				idx = juDAO.selJuIdx(dto.getBoard_idx());
			} else {
				int juIdx = juDAO.selJuIdx(dto.getBoard_idx());
				if (boardType.equals("substiBoard")) {
					idx = juDAO.selSbIdx(juIdx);
				} else if (boardType.equals("partyBoard")) {
					idx = juDAO.selPtIdx(juIdx);
				} else if (boardType.equals("matchBoard")) {
					idx = juDAO.selMtIdx(juIdx);
				}
			}
		}
		return idx;
	}

	public int delReview(int idx) {
		return juDAO.delReview(idx);
	}

}
