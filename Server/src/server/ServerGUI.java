/**
 * 
 */
package server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import problemdomain.Message;


/**
 * Serveer GUI
 * 
 * @author Jaeyoung Kim
 *
 */
public class ServerGUI {
	private JFrame frame;
	
	private DefaultListModel<String> chatListModel;
	private JList<String> chatList;
	
	private Date date;

	/**
	 * Server GUI Frame
	 */
	public ServerGUI() {
		this.frame = new JFrame("Battleship Game Server");
		
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLayout(new BorderLayout());
		this.frame.setSize(400, 800);
		
		JPanel chatPanel = this.createMessagePanel();
		this.frame.add(chatPanel, BorderLayout.CENTER);
		
		JPanel connectionPanel = this.createExitPanel();
		this.frame.add(connectionPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * create message panel
	 * 
	 * @return message panel
	 */
	private JPanel createMessagePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		
		this.chatListModel = new DefaultListModel<String>();
		this.chatList = new JList<String>(this.chatListModel);
		
		JScrollPane scrollPane = new JScrollPane(this.chatList);
		
		panel.add(scrollPane, BorderLayout.CENTER);
		
		return panel;
	}
	
	/**
	 * create Exit Panel
	 * 
	 * @return Exit Panel
	 */
	private JPanel createExitPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2));			
		
		JButton exitButton = new JButton("Exit");
		
		exitButton.addActionListener((ActionEvent evt) -> {
			System.exit(0);
		});
		
		panel.add(exitButton);
		
		return panel;
	}
	
	/**
	 * Add a message to the JList.
	 * @param message a user's message
	 */
	public void addMessage(String message) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.date = new Date();
		String currentDateTime = String.format("[%s] ", format.format(this.date));
		this.chatListModel.addElement(currentDateTime+message);
	}
	
	/**
	 * Display the server GUI frame
	 */
	public void display() {
		this.frame.setVisible(true);		
	}
}
