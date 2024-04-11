package Gioco;

import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;



import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.print.DocFlavor.URL;
import javax.sound.sampled.*;

public class GamePanel extends JPanel implements ActionListener {
	 // Gestisce la navicella del giocatore
    private Ship ship;
    // Lista dei nemici sullo schermo
    private List<Enemy> enemies;
    // Lista delle esplosioni per visualizzare gli effetti al momento della distruzione dei nemici
    private List<Explosion> explosions = new ArrayList<>();
    // Timer per il ciclo di gioco, che aggiorna la logica e la visualizzazione
    private Timer timer;
    // Il punteggio corrente del giocatore
    private int score = 0;
    // Flag che indica se il gioco è terminato
    private boolean gameOver = false;
    // L'immagine utilizzata per l'esplosione
    private Image explosionImage;
    
    private int difficulty;
  
    Random random= new Random();
    
    
    
    
    
    
	//costruttore
	public GamePanel(int difficulty) {
		this.difficulty= difficulty;
		ship = new Ship();
		enemies= new ArrayList<>();
	       generateEnemiesWave(); // Genera la prima ondata di nemici
	       playBackgroundMusic("/sound/music.wav");
	        
	        loadImage(); // Carica l'immagine dell'esplosione da utilizzare per i nemici distrutti
		 setFocusable(true); // Imposta il pannello per accettare l'input da tastiera
	        requestFocusInWindow(); // Richiede il focus per ricevere gli eventi da tastiera
	        addKeyListener(new KeyAdapter() {
	            @Override
	            public void keyPressed(KeyEvent e) {
	                // Riavvia il gioco se premi SPAZIO dopo un game over
	                if (gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
	                    resetGame();
	                }
	            }
	        });
	        addKeyListener(ship); // Aggiunge un KeyListener per gestire gli input della navicella del giocatore

	        timer = new Timer(1000 / 60, this); // Imposta il timer per aggiornare il gioco 60 volte al secondo
	        timer.start(); // Avvia il timer
	    }
	
	//carica immagine esplosione
	private void loadImage() {
		ImageIcon explosion = new ImageIcon(getClass().getResource("/games/image/explosion.png"));
		explosionImage = explosion.getImage();
	}
	
	//genera le nuovv ondate di nemici
	private void generateEnemiesWave() {
	    int numberOfEnemies;
	    switch (difficulty) {
	        case 1: // Facile
	            numberOfEnemies = 5+ random.nextInt(6);;
	            break;
	        case 2: // Medio
	            numberOfEnemies = 8 + random.nextInt(8);;
	            break;
	        case 3: // Difficile
	            numberOfEnemies = 12+ random.nextInt(10);;
	            break;
	        default:
	            numberOfEnemies = 5; // Default a Facile se non specificato
	            break;
	    }

	    for (int i = 0; i < numberOfEnemies; i++) {
            // Calcola posizioni casuali per i nemici
            int x = (i + 1) * 100; // Distribuzione orizzontale
            int y = 5; // Posizione iniziale dall'alto
            enemies.add(new Enemy(x, y)); // Crea e aggiunge il nemico alla lista
        }
	}

	// Resetta il gioco per una nuova partita
	private void resetGame() {
	    score = 0; // Azzera il punteggio
	    gameOver = false; // Indica che il gioco è nuovamente in corso
	    enemies.clear(); // Svuota la lista dei nemici
	    explosions.clear(); // Svuota la lista delle esplosioni
	    ship.reset(); // Ricrea la navicella del giocatore
	    
	  
	    requestFocusInWindow(); // Assicura che il pannello abbia di nuovo il focus per ricevere input da tastiera
	    timer.start(); // Riavvia il timer
	}

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Imposta lo sfondo del pannello di gioco
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight()); // Riempie il background di nero

        // Se il gioco non è finito, disegna gli elementi di gioco
        if (!gameOver) {
            ship.draw(g); // Disegna la navicella del giocatore
            for (Enemy enemy : enemies) {
                enemy.draw(g); // Disegna ciascun nemico
            }

            // Gestisce e disegna le esplosioni
            Iterator<Explosion> it = explosions.iterator();
            while (it.hasNext()) {
                Explosion explosion = it.next();
                explosion.draw(g);
                // Rimuove le esplosioni finite dalla lista
                if (explosion.shouldBeRemoved()) {
                    it.remove();
                }
            }

            // Mostra il punteggio corrente del giocatore
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Punteggio: " + score, 10, 30);
        } 
           
        
    }
	
    @Override
    public void actionPerformed(ActionEvent e) {
        // Aggiorna lo stato del gioco ad ogni tick del timer
        if (!gameOver) {
            updateGame();
            repaint(); // Richiede la ridisegnatura del pannello per riflettere gli aggiornamenti
          
        }
    }

    // Aggiorna lo stato del gioco, inclusa la posizione dei nemici e la logica delle collisioni
    private void updateGame() {
        ship.updateBullets(); // Aggiorna la posizione dei proiettili sparati dalla navicella
        for (Enemy enemy : enemies) {
            enemy.update(); // Aggiorna la posizione dei nemici
        }
        checkCollisions(); // Verifica le collisioni tra proiettili, nemici e la navicella

        // Se tutti i nemici sono stati distrutti, genera una nuova ondata
        if (enemies.isEmpty()) {
            generateEnemiesWave();
        }
    }
    private void checkCollisions() {
        Iterator<Bullet> bulletIt = ship.getBullets().iterator();
        while (bulletIt.hasNext()) {
            Bullet bullet = bulletIt.next();
            Iterator<Enemy> enemyIt = enemies.iterator();
            while (enemyIt.hasNext()) {
                Enemy enemy = enemyIt.next();
                if (bullet.getX() < enemy.getX() + enemy.getWidth() &&
                    bullet.getX() + bullet.getWidth() > enemy.getX() &&
                    bullet.getY() < enemy.getY() + enemy.getHeight() &&
                    bullet.getY() + bullet.getHeight() > enemy.getY()) {
                    enemy.hit(); // Il nemico è colpito
                    bulletIt.remove(); // Rimuovi il proiettile
                    if (enemy.isDestroyed()) {
                        enemyIt.remove(); // Rimuovi il nemico se distrutto
                        score++; // Aumenta il punteggio
                        explosions.add(new Explosion(enemy.getX(), enemy.getY(), explosionImage)); // Aggiungi esplosione
                    }
                    break;
                }
            }
        }

        // Controlla collisioni tra navicella giocatore e nemici
        for (Enemy enemy : enemies) {
            if (ship.getX() < enemy.getX() + enemy.getWidth() &&
            		ship.getX() + ship.getWidth() > enemy.getX() &&
            		ship.getY() < enemy.getY() + enemy.getHeight() &&
            		ship.getY() + ship.getHeight() > enemy.getY()) {
                timer.stop();
                gameOver = true;
                break;
            }
        }
    }

       

    // Classe interna Explosion per gestire l'animazione delle esplosioni
    public class Explosion {
        private int x, y;
        private long startTime;
        private Image image;

        public Explosion(int x, int y, Image image) {
            this.x = x;
            this.y = y;
            this.image = image;
            this.startTime = System.currentTimeMillis(); // Imposta il momento di inizio dell'esplosione
        }

        // Verifica se l'esplosione è terminata (dopo 1,5 secondi)
        public boolean shouldBeRemoved() {
            return System.currentTimeMillis() - startTime > 1500; // 1500 millisecondi = 1,5 secondi
        }

        // Disegna l'esplosione se non è ancora terminata
        public void draw(Graphics g) {
            if (!shouldBeRemoved()) {
                g.drawImage(image, x, y, null);
            }
        }
    }
    
    
    
    //sonoro
    public void playBackgroundMusic(String filePath) {
        try {
            // Carica l'audio
            java.net.URL url = this.getClass().getResource(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);

            // Imposta il clip per loop continuo
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            // Inizia la riproduzione
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}

