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
	private ServerGUI serverGUI;

	public InputOutputHandler(ClientConnection input, ClientConnection output, ServerGUI serverGUI) {
		this.input = input;
		this.output = output;
		this.serverGUI = serverGUI;
	}

	@Override
	public void run() {
		while (!this.input.getSocket().isClosed() && !this.output.getSocket().isClosed()) {
			try {
				Object receive = (Object) this.input.getOis().readObject();
				if (receive instanceof StartGame) {
					StartGame startGame = (StartGame) receive;
					serverGUI.addMessage(startGame.toString());
					this.output.getOos().writeObject(startGame);
				} else if (receive instanceof Message) {
					Message message = (Message) receive;
					serverGUI.addMessage(message.toString());
					this.output.getOos().writeObject(message);
				} else if (receive instanceof Battle) {
					Battle battle = (Battle) receive;
					serverGUI.addMessage(battle.toString());
					this.output.getOos().writeObject(battle);
				} else if (receive instanceof AfterAttack) {
					AfterAttack afterAttack = (AfterAttack) receive;
					serverGUI.addMessage(afterAttack.toString());
					this.output.getOos().writeObject(afterAttack);
				} else if (receive instanceof ReGame) {
					ReGame reGame = (ReGame) receive;
					serverGUI.addMessage(reGame.toString());
					this.input.getSocket().close();
					this.output.getOos().writeObject(reGame);
					//this.output.getSocket().close();			
				} else if (receive instanceof QuitGame) {
					QuitGame quitGame = (QuitGame) receive;
					serverGUI.addMessage(quitGame.toString());
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
