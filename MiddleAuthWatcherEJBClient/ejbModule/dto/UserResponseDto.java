package dto;

import model.Role;

public class UserResponseDto{
	
	private String login;
	private String password;
	private Role role;
	private boolean isAuth;
	
	public UserResponseDto(){
	}
	
	public UserResponseDto(String login, String password, Role role, boolean isAuth){
		this.login = login;
		this.password = password;
		this.role = role;
		this.isAuth = isAuth;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	/**
	 * Security field, impossible to getPassword
	 * @return ******
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isAuth() {
		return isAuth;
	}

	public void setAuth(boolean isAuth) {
		this.isAuth = isAuth;
	}

	
	
}
