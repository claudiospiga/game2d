package Gioco;

import javax.swing.ImageIcon;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Ship implements KeyListener {
    private int x, y; // Posizione della navicella
    private final int speed = 19; // Velocità della nave
    private Image image; // Immagine della navicella
    private List<Bullet> bullets = new ArrayList<>(); // Lista dei proiettili sparati dalla nave
    
    // Costruttore
    public Ship() {
        reset(); // Usa il metodo reset per inizializzare la nave
    }
    
    // Metodo reset per inizializzare o reinizializzare lo stato della nave
    public void reset() {
        // Caricamento dell'immagine della navicella
        ImageIcon ii = new ImageIcon(getClass().getResource("/games/image/ship.png"));
        image = ii.getImage();
        this.y = 800; // Posizione iniziale della navicella
        this.x = 400; // Assumi una posizione iniziale x, ad esempio al centro orizzontalmente
        bullets.clear(); // Pulisce la lista dei proiettili
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // Implementazione non necessaria per questo esempio
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            x -= speed;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            x += speed;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Aggiunge un proiettile alla lista quando si preme lo spazio
            bullets.add(new Bullet(x + image.getWidth(null) / 2, y));
            playSound("/sound/laser4.wav");
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        // Implementazione non necessaria per questo esempio
    }
    
    // Disegna la navicella e i proiettili sullo schermo
    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
        for (Bullet bullet : new ArrayList<>(bullets)) {
            bullet.draw(g);
        }
        // Rimuove i proiettili fuori dallo schermo
        bullets.removeIf(Bullet::isOffScreen);
    }
    
    public void updateBullets() {
        for (Bullet bullet : bullets) {
            bullet.update();
        }
    }
    
    // Metodo per riprodurre il suono dello sparo
    private void playSound(String soundFileName) {
        try {
            java.net.URL url = this.getClass().getResource(soundFileName);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace(); // Gestione degli errori semplice per esempio
        }
    }

    // Getter
    public List<Bullet> getBullets() { return bullets; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return image.getWidth(null); }
    public int getHeight() { return image.getHeight(null); }
}

