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
				Object receive = (Object) this.input.getOis().readObject();
				if (receive instanceof Message) {
					Message message = (Message) receive;
					System.out.println("Received message: " + message.toString());
					// Message send = new Message("Server", "Okay!");
					this.output.getOos().writeObject(message);
				} else if (receive instanceof Battle) {
					Battle battle = (Battle) receive;
					System.out.println("Received message: " + battle.toString());
					this.output.getOos().writeObject(battle);
				} else if (receive instanceof AfterAttack) {
					AfterAttack afterAttack = (AfterAttack) receive;
					System.out.println("Received message: " + afterAttack.toString());
					this.output.getOos().writeObject(afterAttack);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
