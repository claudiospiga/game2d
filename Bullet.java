package Gioco;
import java.awt.Graphics;
import javax.sound.sampled.*;
import java.awt.Color;

public class Bullet {
	//attributi 
	private int x,y; //posizione del proiettile 
	private final int speed = 10; //velocita del proiettile 
	private final int width=5,height=10;  //dimensione 
	
	public Bullet(int x, int y) {
		this.x =x +22; //serve per centrare il proiettile con la navicella
		this.y=y;
	}
	
	// Aggiorna la posizione del proiettile (muove verso l'alto)
	public void update() {
		y-=speed;
	}
	
	//metodo per disegnare il proiettile 
	public void draw(Graphics g) {
		g.setColor(Color.GREEN); //colore proiettile
		g.fillRect(x, y, width, height);
		
	}
	public boolean isOffScreen() {
		return y + height<0;
	}
	 // Getter per la posizione X del proiettile
    public int getX() {
        return x;
    }

    // Getter per la posizione Y del proiettile
    public int getY() {
        return y;
    }

    // Getter per la larghezza del proiettile
    public int getWidth() {
        return width;
    }

    // Getter per l'altezza del proiettile
    public int getHeight() {
        return height;
    }
    
   
	

}
