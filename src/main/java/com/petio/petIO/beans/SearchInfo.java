package com.petio.petIO.beans;

import javax.validation.constraints.Size;

public class SearchInfo {
	@Size(min=0,max=200)
	private String searchText;
	
	private String regionSelect;
	
	private String kindSelect;

	private int page;
	
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public String getRegionSelect() {
		return regionSelect;
	}
	public void setRegionSelect(String regionSelect) {
		this.regionSelect = regionSelect;
	}
	public String getKindSelect() {
		return kindSelect;
	}
	public void setKindSelect(String kindSelect) {
		this.kindSelect = kindSelect;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public SearchInfo() {
		super();
		this.searchText = "";
		this.regionSelect = "All";
		this.kindSelect = "All";
		this.page = 1;
	}
	
}
