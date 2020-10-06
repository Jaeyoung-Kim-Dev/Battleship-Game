package client;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class test {

	public static void main(String[] args) {
		JPanel panel = new JPanel(new GridLayout(1, 2));
		// TODO Auto-generated method stub
		String ip = JOptionPane.showInputDialog("Enter ip address or hostname:");
	}

}
