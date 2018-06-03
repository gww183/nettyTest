package serializable;

import org.msgpack.annotation.Message;

@Message
public class UserInfo {
	private String userName;
	
	private int userId;
	
	
	
	public UserInfo() {
	}

	public UserInfo buildUserName(String userName) {
		this.userName = userName;
		return this;
	}
	
	public UserInfo  buildUserId(int userId) {
		this.userId = userId;
		return this;
	}

	public final String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public final int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	@Override
	public String toString() {
		return "['userId':"+userId+",'userName':"+this.userName+"]";
	}

}
