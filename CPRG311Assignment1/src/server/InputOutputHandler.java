/**
 * 
 */
package server;

import java.io.IOException;

import problemdomain.*;

/**
 * Handler serialized data from the client
 * 
 * @author Jaeyoung Kim
 *
 */
public class InputOutputHandler implements Runnable {
	private ClientConnection input;
	private ClientConnection output;
	private ServerGUI serverGUI;

	/**
	 * User-defined constructor for InputOutputHandler.
	 * 
	 * @param input a user ClientConnection
	 * @param output enemy ClientConnection
	 * @param serverGUI GUI for server
	 */
	public InputOutputHandler(ClientConnection input, ClientConnection output, ServerGUI serverGUI) {
		this.input = input;
		this.output = output;
		this.serverGUI = serverGUI;
	}

	/**
	 * run method
	 */
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
					//this.input.getSocket().close();					
					this.output.getOos().writeObject(quitGame);
					//this.output.getSocket().close();
					//소켓을 여기서 닫아야 while문을 빠져나와서 EOF예외처리에서 빠져나올수 있는것인가...?
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();				
			} catch (IOException e) {
				e.printStackTrace();		
			}
		}
	}

}
