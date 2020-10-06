/**
 * 
 */
package server;

import java.io.IOException;

import problemdomain.*;

/**
 * @author Jaeyoung Kim
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
				if (receive instanceof StartGame) {
					StartGame startGame = (StartGame) receive;
					System.out.println("Received message: " + startGame.toString());
					this.output.getOos().writeObject(startGame);
				} else if (receive instanceof Message) {
					Message message = (Message) receive;
					System.out.println("Received message: " + message.toString());
					this.output.getOos().writeObject(message);
				} else if (receive instanceof Battle) {
					Battle battle = (Battle) receive;
					System.out.println("Received message: " + battle.toString());
					this.output.getOos().writeObject(battle);
				} else if (receive instanceof AfterAttack) {
					AfterAttack afterAttack = (AfterAttack) receive;
					System.out.println("Received message: " + afterAttack.toString());
					this.output.getOos().writeObject(afterAttack);
				} else if (receive instanceof ReGame) {
					ReGame reGame = (ReGame) receive;
					System.out.println("Received message: " + reGame.toString());
					this.input.getSocket().close();					
					this.output.getOos().writeObject(reGame);
					//this.output.getSocket().close();			
				} else if (receive instanceof QuitGame) {
					QuitGame quitGame = (QuitGame) receive;
					System.out.println("Received message: " + quitGame.toString());
					this.input.getSocket().close();					
					this.output.getOos().writeObject(quitGame);
					//this.output.getSocket().close();			
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
