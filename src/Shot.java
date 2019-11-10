import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;

public class Shot extends DynamicObject{
	private static final int SPEED = 10;
	private static final Point STORAGE = new Point(-20, -20);

	private int count = 0;
	
	private Image safeShot;
	private Image dangerShot;

	public Shot(MainPanel panel){
		super(STORAGE.x, STORAGE.y, panel);
		radian = 0;
	}

	public void move(){
		if(isInStorage()){
			return;
		}

		y += Math.sin(radian)*SPEED;
		x += Math.cos(radian)*SPEED;
		if( ( y < 0 || y > panel.getHeight() ) || ( x < 0 || x > panel.getWidth() ) ){
		//	store();
			if(count == 0){
				image = dangerShot;
			}
			
			radian += Math.PI / 2;
			count++;
			
			if(count > 6){
				store();
			}
		}
	}
	
	public void store(){
		x = STORAGE.x;
		y = STORAGE.y;
		count = 0;
		image = safeShot;
	}

	public boolean isInStorage(){
		if(x == STORAGE.x && y == STORAGE.x){
			return true;
		}
		return false;
	}
	
	public boolean isReturn(){
		if(count > 0){
			return true;
		}
		return false;
	}
/*
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform at = new AffineTransform();
		at.setToTranslation(x-(width/2), y-(height/2));
		g2.setTransform(at);
		g2.rotate(radian + Math.PI/2,width/2,height/2);
		g2.drawImage(image, 0, 0, null);		
	
//		g.drawImage(image, x, y, null);
	}
	*/

    protected void loadImage() {
    	ImageIcon icon = new ImageIcon(getClass().getResource("image/shot_safe.gif"));
        safeShot = icon.getImage();
        icon = new ImageIcon(getClass().getResource("image/shot.gif"));
        dangerShot = icon.getImage();
        
        image = safeShot;

        width = image.getWidth(panel);
        height = image.getHeight(panel);
    }
}
