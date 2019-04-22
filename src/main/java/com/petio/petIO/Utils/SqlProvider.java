package com.petio.petIO.Utils;

public class SqlProvider {
	private int noteNumber = 20;
	public String getFosterTotalNumber(String title,String location,String type){
		StringBuffer sql = new StringBuffer("select count(*) from Fosterage where fState = 1");
		if (!"All".equals(location)) {
			sql.append(" and location=#{location}");
		}
		if (!"All".equals(type)) {
			sql.append(" and fType=#{type}");
		}
		
		if (title!=null&&!"".equals(title)) {
			System.out.println("[title:"+title+"]");
			sql.append(" and fTitle LIKE '%"+title+"%'");
		}
		return sql.toString();
	}
	public String getFosterage(String title,String location,String type,Integer page){
		StringBuffer sql = new StringBuffer("select * from Fosterage where fState = 1");
		if (!"All".equals(location)) {
			sql.append(" and location=#{location}");
		}
		if (!"All".equals(type)) {
			sql.append(" and fType=#{type}");
		}
		
		if (title!=null&&!"".equals(title)) {
			System.out.println("[title:"+title+"]");
			sql.append(" and fTitle LIKE '%"+title+"%'");
		}
		if (page<1) {
			page = 1;
		}
		int offset = 20*(page-1);
		sql.append(" limit "+offset+" , "+noteNumber);
		return sql.toString();
	}
}
