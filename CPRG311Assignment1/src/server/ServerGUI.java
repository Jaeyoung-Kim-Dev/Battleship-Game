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
 * @author nhamnett
 *
 */
public class ServerGUI {
	private JFrame frame;
	
	private DefaultListModel<String> chatListModel;
	private JList<String> chatList;
	
	private Date date;

	public ServerGUI() {
		this.frame = new JFrame("Battleship Game Server");
		
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLayout(new BorderLayout());
		this.frame.setSize(400, 800);
		
		JPanel chatPanel = this.createChatPanel();
		this.frame.add(chatPanel, BorderLayout.CENTER);
		
		JPanel connectionPanel = this.createConnectionPanel();
		this.frame.add(connectionPanel, BorderLayout.SOUTH);
	}
	
	private JPanel createChatPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		
		this.chatListModel = new DefaultListModel<String>();
		this.chatList = new JList(this.chatListModel);
		
		JScrollPane scrollPane = new JScrollPane(this.chatList);
		
		panel.add(scrollPane, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createConnectionPanel() {
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
	 * @param message
	 */
	public void addMessage(String message) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.date = new Date();
		String currentDateTime = String.format("[%s] ", format.format(this.date));
		this.chatListModel.addElement(currentDateTime+message);
	}
	
	public void display() {
		this.frame.setVisible(true);		
	}
}
