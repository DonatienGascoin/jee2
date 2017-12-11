package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "users")

@NamedQuery(name = "Users.list", query = "select u from UserModel u")
public class UserModel implements Serializable{

	private static final long serialVersionUID = 2529555305297519000L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private int id;
	
	@NotNull
    @Column(name = "login", unique = true)
	private String login;
	
	@NotNull
    @Column(name = "password")
	private String password;
	
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
	private Role role;
    
    @Column(name = "validAuth")
	private boolean validAuth;
	
	public UserModel(){
	}
	
	public UserModel(String login, String password){
		this.login = login;
		this.password = password;
		this.role = Role.NONE;
	}
	
	public UserModel(String login, String password, Role role){
		this.login = login;
		this.password = password;
		this.role = role;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
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

	public boolean isValidAuth() {
		return validAuth;
	}

	public void setValidAuth(boolean validAuth) {
		this.validAuth = validAuth;
	}

	@Override
	public String toString() {
		return "UserModel [login=" + login + ", password=" + password + ", validAuth=" + validAuth +", role=" + role 
				+ "]";
	}
	
	
}