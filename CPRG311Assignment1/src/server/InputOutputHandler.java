/**
 * 
 */
package server;

import java.io.IOException;

import problemdomain.*;

/**
 * @author nhamnett
 *
 */
public class InputOutputHandler implements Runnable {
	private ClientConnection input;
	private ClientConnection output;
	
	public InputOutputHandler(ClientConnection input, ClientConnection output) {
		this.input = input;
		this.output = output;
	}
	
	@Override
	public void run() {
		while (!this.input.getSocket().isClosed() && !this.output.getSocket().isClosed()) {
			try {
				Message message = (Message) this.input.getOis().readObject();
				
				System.out.println("Received message: " + message.toString());
				
				//Message send = new Message("Server", "Okay!");
				this.output.getOos().writeObject(message);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
