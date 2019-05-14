package com.petio.petIO.beans;

import javax.validation.constraints.NotNull;

public class SignupInfo {
	@NotNull(message="用户名不允许为空")
    private String username;
    
    @NotNull(message="密码不允许为空")
    private String password;
    
    @NotNull(message="电话不允许为空")
    private String userTel;

    @NotNull(message="邮箱不允许为空")
    private String userMail;
    
    @NotNull(message="验证码不允许为空")
    private String verifyCode;
    
    public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

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
