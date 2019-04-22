package com.petio.petIO.beans;

public class ListData {
	private int number;
	private Object list;
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Object getList() {
		return list;
	}
	public void setList(Object list) {
		this.list = list;
	}
	public ListData(int number, Object list) {
		super();
		this.number = number;
		this.list = list;
	}
	
}
