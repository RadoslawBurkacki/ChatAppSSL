import java.io.Serializable;

public class PrivateMessage implements Serializable{
	
	protected static final long serialVersionUID = 145893929849L;
	
	private String msg;
	private String receiver;
	private String sender;
	
	public PrivateMessage(String msg, String receiver, String sender){
		this.msg=msg;
		this.receiver = receiver;
		this.sender = sender;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	
	
	
}
