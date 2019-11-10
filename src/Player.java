import javax.swing.ImageIcon;

public class Player extends DynamicObject{
	
	public Player( int x, int y, MainPanel panel){
		super(x, y, panel);
	}
	
	public void move(int x, int y){
		this.x = x;
		this.y = y;

		
		radian += (2*Math.PI/360)*2;
		if( radian >= 2*Math.PI ){
			radian = 0;
		}
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
		ImageIcon icon = new ImageIcon(getClass().getResource("image/player.gif"));
		image = icon.getImage();
		
		width = image.getWidth(panel);
		height = image.getHeight(panel);
	}
}
