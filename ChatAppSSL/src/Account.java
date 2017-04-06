import java.io.Serializable;

public class Account implements Serializable {
	
	protected static final long serialVersionUID = 145893929849L;
	
	 
	private String username;
	private String password;
	private String type;
	private String status;
	
	
	public Account(String username, String password) {
		this.username = username;
		this.password = password;
		this.type = "standard";
		this.status = "offline";
	
	}
	
	public Account(String username, String password, String type) {
		this.username = username;
		this.password = password;
		this.type = type;
		this.status = "offline";
	}

	
	


	


	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
}
