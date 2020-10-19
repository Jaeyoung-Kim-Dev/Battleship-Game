/**
 * 
 */
package problemdomain;

import java.net.*;
import java.io.*;

/**
 * contains client connection information
 * 
 * @author Jaeyoung Kim
 *
 */
public class ClientConnection {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	/**
	 * User-defined constructor for client connection
	 * 
	 * @param socket Socket
	 * @param ois ObjectInputStream
	 * @param oos ObjectOutputStream
	 */
	public ClientConnection(Socket socket, ObjectInputStream ois, ObjectOutputStream oos) {
		this.socket = socket;
		this.ois = ois;
		this.oos = oos;
	}

	/**
	 * Gets the socket
	 * 
	 * @return socket 
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * Gets ObjectInputStream
	 * 
	 * @return ObjectInputStream
	 */
	public ObjectInputStream getOis() {
		return ois;
	}

	/**
	 * Gets ObjectOutputStream
	 * 
	 * @return ObjectOutputStream
	 */
	public ObjectOutputStream getOos() {
		return oos;
	}
}
