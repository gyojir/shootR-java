import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

abstract public class DynamicObject {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected double radian;
	protected Image image;
	
	protected MainPanel panel;
	
	public DynamicObject( int x, int y, MainPanel panel ){
		this.x = x;
		this.y = y;
		this.radian = 0.0;
		this.panel = panel;
		
		loadImage();
	}
	
	public void setPos(int x, int y, int angle){
		this.x = x;
		this.y = y;
		this.radian = Math.toRadians(angle);
	}
	
	public Point getPos(){
		return new Point(x,y);
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	public double getRadian(){
		return radian;
	}
	
	public int getAngle(){
		return (int) Math.toDegrees(radian);
	}
	
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform at = new AffineTransform();
		at.setToTranslation(x-(width/2), y-(height/2));
		g2.setTransform(at);
		g2.rotate(radian + Math.PI/2,width/2,height/2);
		g2.drawImage(image, 0, 0, null);		
	
//		g.drawImage(image, x, y, null);
	}
	
	public boolean collideWith(DynamicObject object){
		Rectangle rectMy = new Rectangle(x, y, width, height);
		Point pos = object.getPos();
		Rectangle rectObject = new Rectangle(pos.x, pos.y, object.getWidth(), object.getHeight());
		
		return rectMy.intersects(rectObject);
	}
	
	abstract protected void loadImage();
}