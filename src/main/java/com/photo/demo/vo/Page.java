package com.photo.demo.vo;

import java.io.UnsupportedEncodingException;

public class Page {
	private int num;
	private int totalCount;
 	private int postNum = 12;
	private int pageNum;
	private int sqlPostNum;
	
	private int pageNum_cnt =12 ;
	
	private int endPageNum; 
	private int startPageNum;
	
	private boolean prev;
	private boolean next;
	
	private String queryString;
	private String searchKeyword;
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		dataCalc();
	}

	public int getPostNum() {
		return postNum;
	}

	public int getPageNum() {
		return pageNum;
	}

	public int getSqlPostNum() {
		return sqlPostNum;
	}

	public int getPageNum_cnt() {
		return pageNum_cnt;
	}

	public int getEndPageNum() {
		return endPageNum;
	}

	public int getStartPageNum() {
		return startPageNum;
	}

	public boolean isPrev() {
		return prev;
	}

	public boolean isNext() {
		return next;
	}
	
	public void dataCalc() {
		
		endPageNum = (int)(Math.ceil((double)num/(double)pageNum_cnt)*pageNum_cnt);
		
		startPageNum = endPageNum - (pageNum_cnt-1);
		
		int endPageNum_tmp = (int)(Math.ceil((double)totalCount / (double)pageNum_cnt));
		if(endPageNum > endPageNum_tmp) {
			endPageNum = endPageNum_tmp;
		}
		
		prev = startPageNum == 1? false : true;
		next = endPageNum * pageNum_cnt >= totalCount ? false : true;
		
		sqlPostNum = (num - 1) * postNum;
		
	}
	
	public String getQueryString() {
		return queryString;
	}

	public void setQueryString() throws UnsupportedEncodingException{
		
		String qs = "";
		
		qs += "num="+this.num;//고정
		
		this.queryString = qs;
	}
	

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
		
		if(searchKeyword.equals("")) {
			this.searchKeyword = "";
		}else {
			this.searchKeyword = "&searchKeyword=" + searchKeyword;
		}
	}

	@Override
	public String toString() {
		return "Page [num=" + num + ", totalCount=" + totalCount + ", postNum=" + postNum + ", pageNum=" + pageNum
				+ ", sqlPostNum=" + sqlPostNum + ", pageNum_cnt=" + pageNum_cnt + ", endPageNum=" + endPageNum
				+ ", startPageNum=" + startPageNum + ", prev=" + prev + ", next=" + next + ", queryString="
				+ queryString + ", searchKeyword=" + searchKeyword + "]";
	}


	
	
	
	
}
