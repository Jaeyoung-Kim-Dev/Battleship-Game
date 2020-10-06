/**
 * 
 */
package client;

import problemdomain.*;
import java.io.*;
import java.net.*;

/**
 * @author Jaeyoung Kim
 *
 */
public class ServerHandler implements Runnable {
	private MainWindow mainWindow;

	private Socket server;
	private ObjectInputStream ois;

	public ServerHandler(MainWindow mainWindow, Socket server, ObjectInputStream ois) {
		this.mainWindow = mainWindow;
		this.server = server;
		this.ois = ois;
	}

	@Override
	public void run() {
		while (!this.server.isClosed()) {
			try {
				Object receive = (Object) this.ois.readObject();
				if (receive instanceof StartGame) {
					StartGame startGame = (StartGame) receive;
					this.mainWindow.addMessage(startGame.toString());
					this.mainWindow.startGame(((StartGame) receive).isGoFirst());					
				} else if (receive instanceof Message) {
					receive = (Message) receive;
					this.mainWindow.addMessage(receive.toString());
				} else if (receive instanceof Battle) {
					Battle battle = (Battle) receive;					
					this.mainWindow.battle(battle.getX(),battle.getY());
				} else if (receive instanceof AfterAttack) {
					AfterAttack afterAttack = (AfterAttack) receive;
					//if(afterAttack.getTotalships() == 0) this.mainWindow.addMessage(afterAttack.toString());
					this.mainWindow.afterAttack(afterAttack.getX(),afterAttack.getY(), afterAttack.isStrike(), afterAttack.getTotalships());
				} else if (receive instanceof ReGame) {
					ReGame reGame = (ReGame) receive;
					if (!reGame.isWantReGame()) server.close();
					this.mainWindow.connect(reGame.getIp(), reGame.getPort());
				} else if (receive instanceof QuitGame) {
					QuitGame quitGame = (QuitGame) receive;
					if (quitGame.isQuitGame()) this.mainWindow.disconnect();
					server.close();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
