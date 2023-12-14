package com.itbank.model;

public class PageMakeDTO {

	private int pageNum;		// 현재 페이지
	private int startPage;		// 컨트롤바 첫숫자
	private int endPage;		// 컨트롤바 마지막숫자
	private boolean prev, next;	// 이전/다음 페이지 유무
	private int total;			// 데이터 수
	private int realEnd;		// 총 페이지 수
	private int startRow;		// 현재 페이지 시작 데이터 인덱스 (list.get(인덱스))
	private int endRow;			// 현재 페이지 마지막 데이터 인덱스
	
	// 생성자 매개변수로 현재페이지와 리스트의 데이터 개수를 전달받아 변수들의 값을 계산하여 저장한다.
	public PageMakeDTO(int pageNum, int total) {
		
		this.pageNum = pageNum;
		
		this.total = total;
		
		// 현재페이지의 10으로 나눴을때 소숫점 왼쪽 숫자를 구하여 컨트롤바의 가장 왼쪽, 오른쪽에 들어갈 숫자를 구한다.
		this.endPage = (int)(Math.ceil(pageNum / 10.0)) * 10;
		
		this.startPage = this.endPage - 9;
		
		// 리스트의 데이터 개수를 이용해 제일 끝 페이지를 계산한다.
		realEnd = (int)(Math.ceil(total * 1.0 / 10));
		
		if(realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		// 현재 페이지의 이전페이지 혹은 뒷페이지가 존재하는지 여부를 boolean 값으로 저장한다.
		this.prev = this.startPage > 1;
		
		this.next = this.endPage < realEnd;
		
		// 현재 페이지에 들어갈 첫번쨰 데이터와 마지막 데이터의 index를 구한다.
		this.startRow = (pageNum - 1) * 10;
		this.endRow = startRow + 9;
		if(startRow + 9 > total - 1) {
			this.endRow = total - 1;
		}
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRealEnd() {
		return realEnd;
	}

	public void setRealEnd(int realEnd) {
		this.realEnd = realEnd;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	
	
}
