package Gioco;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;



import java.awt.Graphics;

import javax.swing.JFrame;

public class Main extends JFrame {
	
	//costruttore
	public Main(){
		this.setSize(1000,1000);//grandezza finestra
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //chiusura applicazione
		this.setTitle("Space");
		this.setLocationRelativeTo(null);
		this.add(new Menu(this));
		this.setResizable(false);
		
		
	}

	public static void main(String[] args) {
		 java.awt.EventQueue.invokeLater(() -> {
	            new Main().setVisible(true);
	        });

	}

}
