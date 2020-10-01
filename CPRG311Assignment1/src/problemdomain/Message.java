/**
 * 
 */
package problemdomain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author nhamnett
 *
 */
public class Message implements Serializable {
	private Date date;
	
	private String username;
	
	private String message;
	
	public Message() {
		
	}
	
	public Message(String username, String message) {
		this.date = new Date();
		this.username = username;
		this.message = message;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return String.format("[%s] %s sent: %s", format.format(this.date), this.username, this.message);
	}
}
