package Gioco;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics;
import java.util.Random;

public class Enemy {
	//attributi
	private int x,y; //posizione del meteorite
	private int speed; //velocita
	private Image image; 
	private int health= 3; //salute, 3 bullets e viene distrutto
	private static final Random random= new Random(); //generatore numeri casuali 
	
	public Enemy(int x, int y) {
		this.x = x;
		this.y =y;
		this.speed=2 + random.nextInt(6); //assegna velocita casuale
		//carico delle immagini
		ImageIcon Meteoryte= new ImageIcon(getClass().getResource("/games/image/meteorite.png"));
		image= Meteoryte.getImage();
	}
	
	//aggiornare posizione per farlo muovere verso giu 
	
	public void update() {
		y+=speed;
		  // Se il nemico esce dallo schermo
        if (y > 1200) { // Assumendo che 600 sia l'altezza dello schermo
            // Fa riapparire il nemico dall'alto con una nuova velocità
            y = -random.nextInt(100) - image.getHeight(null);
            x = random.nextInt(800 - image.getWidth(null)); // Cambia la posizione X casualmente
            speed = 1 + random.nextInt(4); // Assegna una nuova velocità casuale
        }
    }
    

    // Disegna il nemico sulla schermata di gioco
    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    // Viene chiamato quando il nemico viene colpito da un proiettile
    public void hit() {
        health--;
    }

    // Verifica se il nemico è stato distrutto
    public boolean isDestroyed() {
        return health <= 0;
    }

    // Metodi getter per la posizione e le dimensioni, utili per il rilevamento delle collisioni
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return image.getWidth(null);
    }

    public int getHeight() {
        return image.getHeight(null);
    }
}

