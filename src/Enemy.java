import java.awt.Point;

import javax.swing.ImageIcon;

public class Enemy extends DynamicObject{
	private static final int SPEED = 7;
	private static final Point STORAGE = new Point(-20, -20);
	
	public Enemy(MainPanel panel){
		super(STORAGE.x, STORAGE.y, panel);
		this.radian = 0;
	}
	
	public void move(){
		if(isInStorage()){
			return;
		}

		y += Math.sin(radian)*SPEED;
		x += Math.cos(radian)*SPEED;
		if( ( y < 0 || y > panel.getHeight() ) || ( x < 0 || x > panel.getWidth() ) ){
			store();
		}		
	}
	
	public void store(){
		x = STORAGE.x;
		y = STORAGE.y;
	}

	public boolean isInStorage(){
		if(x == STORAGE.x && y == STORAGE.x){
			return true;
		}
		return false;
	}
	
/*	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform at = new AffineTransform();
		at.setToTranslation(x-(width/2), y-(height/2));
		g2.setTransform(at);
		g2.rotate(getRadian() + Math.PI/2,width/2,height/2);
		g2.drawImage(image, 0, 0, null);
		
	//	g.drawImage(image, x - width / 2, y - height / 2, null);
	}*/
	
	protected void loadImage(){
		ImageIcon icon = new ImageIcon(getClass().getResource("image/alien.gif"));
		image = icon.getImage();
		
		width = image.getWidth(panel);
		height = image.getHeight(panel);
	}
}