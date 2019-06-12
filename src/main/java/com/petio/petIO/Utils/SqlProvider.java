package com.petio.petIO.Utils;

public class SqlProvider {
	private int noteNumber = 20;

	public String getAdoptionTotalNumber(String title, String location, String type) {
		StringBuffer sql = new StringBuffer("select count(*) from Adoption where aState = 1");
		if (!"All".equals(location)) {
			sql.append(" and location=#{location}");
		}
		if (!"All".equals(type)) {
			sql.append(" and aType=#{type}");
		}

		if (title != null && !"".equals(title)) {
			System.out.println("[title:" + title + "]");
			sql.append(" and aTitle LIKE '%" + title + "%'");
		}
		return sql.toString();
	}

	public String getAdoption(String title, String location, String type, Integer page) {
		StringBuffer sql = new StringBuffer("select * from Adoption where aState = 1");
		if (!"All".equals(location)) {
			sql.append(" and location=#{location}");
		}
		if (!"All".equals(type)) {
			sql.append(" and aType=#{type}");
		}

		if (title != null && !"".equals(title)) {
			System.out.println("[title:" + title + "]");
			sql.append(" and aTitle LIKE '%" + title + "%'");
		}
		if (page < 1) {
			page = 1;
		}
		int offset = 20 * (page - 1);
		sql.append(" order by publishDate desc limit " + offset + " , " + noteNumber);
		System.out.println(sql.toString());
		return sql.toString();
	}
}
