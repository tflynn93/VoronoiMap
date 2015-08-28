package voronoimap;

/**
 * A class to store points and there corresponding color values
 * @author Timothy Flynn
 */
public class Point {
    private int color, x, y, red, blue, green;
    private double distance;
	
    /**
     * Constructor class to initialize variables.  Takes color (in int form)
     * and the x and y coordinates of the image as parameters.
     * @param color
     * @param x
     * @param y
     */
    public Point(int color, int x, int y){
        this.color = color;
        this.x = x;
        this.y = y;
        this.red = (color & 0x00ff0000) >> 16;
        this.green =  (color & 0x0000ff00) >> 8;
        this.blue = color & 0x000000ff;
        this.distance = 0;
    }
    
    /**
     * sets the color
     * @param color
     */
    public void setColor(int color){
        this.color = color;
    }
    
    /**
     * sets red value
     * @param red
     */
    public void setRed(int red){
        this.red = red;
    }
    
    /**
     * sets green value
     * @param green
     */
    public void setGreen(int green){
        this.green = green;
    }
    
    /**
     * sets blue value
     * @param blue
     */
    public void setBlue(int blue){
        this.blue = blue;
    }
    
    /**
     * sets x value
     * @param x
     */
    public void setX(int x){
        this.x = x;
    }
    
    /**
     * sets y value
     * @param y
     */
    public void setY(int y){
        this.y = y;
    }
    
    /**
     * sets distance value
     * @param distance
     */
    public void setDistance(double distance){
        this.distance = distance;
    }
    
    /**
     * returns color value
     * @return
     */
    public int getColor(){
        return this.color;
    }
    
    /**
     * returns red value
     * @return
     */
    public int getRed(){
        return this.red;
    }
    
    /**
     * returns blue value
     * @return
     */
    public int getBlue(){
        return this.blue;
    }
    
    /**
     * returns green value
     * @return
     */
    public int getGreen(){
        return this.green;
    }
    
    /**
     * returns x value
     * @return
     */
    public int getX(){
        return this.x;
    }
    
    /**
     * returns y value
     * @return
     */
    public int getY(){
        return this.y;
    }
    
    /**
     * returns distance value
     * @return
     */
    public double getDistance(){
        return distance;
    }
    
}

