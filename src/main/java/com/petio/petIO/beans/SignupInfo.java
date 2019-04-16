package com.petio.petIO.beans;

import javax.validation.constraints.NotNull;

public class SignupInfo {
	@NotNull(message="用户名不允许为空")
    private String username;
    
    @NotNull(message="密码不允许为空")
    private String password;
    
    @NotNull(message="电话不允许为空")
    private String userTel;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	@Override
	public String toString() {
		return "SignupInfo [username=" + username + ", password=" + password + ", userTel=" + userTel + "]";
	}
}
