package cn.itheima.commons.utils;

public class SimpleHashBean {
	private String password;
	private String salt;

	public SimpleHashBean(String password, String salt) {
		this.password = password;
		this.salt = salt;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}
