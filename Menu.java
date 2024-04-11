package Gioco;

import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {
    private JFrame frame;

    public Menu(JFrame frame) {
        this.frame = frame;
        setLayout(new GridLayout(0, 1)); // Disposizione della colonna
        setBackground(Color.BLACK); // Imposta lo sfondo del pannello su nero

        Font font = new Font("Arial", Font.BOLD, 20); // Crea un font personalizzato
        Color greenColor = new Color(0, 255, 0); // Definisci il colore verde

        JLabel label = new JLabel("Seleziona la difficoltà");
        label.setForeground(greenColor); // Imposta il colore del testo su verde
        label.setFont(new Font("Arial", Font.BOLD, 30)); 
        add(label);

        JButton easyButton = new JButton("Facile");
        easyButton.setForeground(greenColor); // Imposta il colore del testo su verde
        easyButton.setFont(new Font("Arial", Font.BOLD, 40)); 
        easyButton.setBackground(Color.BLACK);
        easyButton.setOpaque(true);
        easyButton.setBorderPainted(false);
        easyButton.setContentAreaFilled(false);
        easyButton.addActionListener(e -> startGame(1));
        add(easyButton);

        JButton mediumButton = new JButton("Media");
        mediumButton.setForeground(greenColor); // Imposta il colore del testo su verde
        mediumButton.setFont(new Font("Arial", Font.BOLD, 40)); 
        mediumButton.setBackground(Color.BLACK);
        mediumButton.setOpaque(true);
        mediumButton.setBorderPainted(false);
        mediumButton.setContentAreaFilled(false);
        mediumButton.addActionListener(e -> startGame(2));
        add(mediumButton);

        JButton hardButton = new JButton("Difficile");
        hardButton.setForeground(greenColor); // Imposta il colore del testo su verde
        hardButton.setFont(new Font("Arial", Font.BOLD, 40));
        hardButton.setOpaque(true);
        hardButton.setBorderPainted(false);
        hardButton.setContentAreaFilled(false);
        hardButton.addActionListener(e -> startGame(3));
        add(hardButton);

        JButton exitButton = new JButton("Esci");
        exitButton.setForeground(greenColor); // Imposta il colore del testo su verde
        exitButton.setFont(new Font("Arial", Font.BOLD, 40)); 
        exitButton.setOpaque(true);
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);
    }

    private void startGame(int difficulty) {
        GamePanel gamePanel = new GamePanel(difficulty); // Crea GamePanel con la difficoltà selezionata
        frame.setContentPane(gamePanel);
        frame.revalidate();
        frame.repaint();
        gamePanel.requestFocusInWindow();
    }
}

