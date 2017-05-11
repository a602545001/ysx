package com.ysx.admin.ui;

public class User implements Redisable<String> {

    private static final long serialVersionUID = -1L;

    private Integer id;
    private String username;
    private Integer age;
    private String ddt;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDdt() {
		return ddt;
	}

	public void setDdt(String ddt) {
		this.ddt = ddt;
	}

	public User(Integer id, String username, Integer age, String ddt) {
		this.id=id;
        this.username = username;
        this.age = age;
        this.ddt = ddt;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String getKey() {
		return this.getUsername();
	}


}