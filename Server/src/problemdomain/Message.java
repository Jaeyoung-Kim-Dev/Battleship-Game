/**
 * 
 */
package problemdomain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Message to communicate users each other
 * 
 * @author Jaeyoung Kim
 *
 */
public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Date date;	
	private String username;	
	private String message;
	
	/**
	 * User-defined constructor for message
	 */
	public Message() {
		
	}
	
	/**
	 * User-defined constructor for message
	 * 
	 * @param username a user name
	 * @param message a message
	 */
	public Message(String username, String message) {
		this.date = new Date();
		this.username = username;
		this.message = message;
	}
	
	/**
	 * Gets the date and time
	 * 
	 * @return date and time
	 */
	public Date getDate() {
		return this.date;
	}
	
	/**
	 * Gets the message
	 * 
	 * @return message
	 */
	public String getMessage() {
		return this.message;
	}
	
	/**
	 * Print the data, time, user name and message 
	 */
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return String.format("[%s] %s sent: %s", format.format(this.date), this.username, this.message);
	}
}
