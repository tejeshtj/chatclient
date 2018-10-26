package com.dev.chatClient;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Client  {
		
	JTextField outgoing;
	JTextArea incoming;
	PrintWriter writer;
	BufferedReader reader;
	Socket sock;
	public static void main(String[] args) {
		new Client().go();
	}
	
	public void go() {
		JFrame frame=new JFrame("Simple chat client");
		JPanel mainpanel=new JPanel();
		
		incoming=new JTextArea(15,50);
		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		JScrollPane qscroller=new JScrollPane(incoming);
		qscroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qscroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		outgoing=new JTextField(20);
		JButton sendbutton=new  JButton("send");
		sendbutton.addActionListener(new SendListener());
		mainpanel.add(qscroller);
		mainpanel.add(outgoing);
		mainpanel.add(sendbutton);
		setUpNetworking();
		
		Thread readerThread=new Thread(new IncomingReader());
		readerThread.start();
		frame.getContentPane().add(BorderLayout.CENTER, mainpanel);
		
		frame.setSize(400,500);
		frame.setVisible(true);
	}
	
	private void setUpNetworking() {
		try {
			sock=new Socket("193.161.193.99", 48708);
			writer=new PrintWriter(sock.getOutputStream());
			System.out.println("connetion established");
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		
	}
	public class SendListener implements ActionListener {
		
		PrintWriter writer;	
		JTextField outgoing;

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					writer.println(outgoing.getText());
					writer.flush();
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
				outgoing.setText("");
				outgoing.requestFocus();
				
			}
	
	
}
	public class IncomingReader implements Runnable{
		BufferedReader reader;
		JTextArea incoming;
		String message;
			@Override
			public void run() {
				String message;
				try {
				
					while((message=reader.readLine())!=null) {
						System.out.println("read"+message);
						incoming.append(message+"\n");
					}
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			
		}


	
}
