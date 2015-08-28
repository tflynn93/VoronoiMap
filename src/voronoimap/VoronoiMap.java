package voronoimap;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/**
 * A program to generate a Voronoi Diagram based on an input image.
 * More information on Voronoi Diagrams: https://en.wikipedia.org/wiki/Voronoi_diagram
 * Inspiration for this project: https://codegolf.stackexchange.com/questions/50299/draw-an-image-as-a-voronoi-map
 * @author Timothy Flynn
 */
public class VoronoiMap {
    JFrame frame = new JFrame(); //Initialize JFrame and necessary parts
    JLabel label;
    BufferedImage result; //Declare a bufferedimage to hold result
    int r, g, b, col; //Declare rgb and color ints
    int nodes; //Declare number of nodes (defines definition)
    double distance; //Distance (calculated by either euclidian or manhattan formula)
    boolean distFormula; //Variable on which formula to use

    /**
     * Main
     * @param args
     * @throws IOException
     */
    public static void main(String args[]) throws IOException{
            //Create new VoronoiMap instance
            VoronoiMap vm = new VoronoiMap();
            //Gather settings info from user
            File f = vm.settings();
            //Perform Voronoi map transformation
            vm.voronoi(f.getCanonicalPath());
    }

    /**
     *
     * @return
     */
    public File settings(){
        //Open filechooser to select input file        
        JFileChooser fc = new JFileChooser();
        int returnVal; 
        returnVal = fc.showOpenDialog(frame);
        File input_image = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            input_image = fc.getSelectedFile();
        } else {
            //User did not choose a valid file (too lazy to add validation right now)
        }
      
        //Set up frame
        frame.getContentPane().setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        //Get the number of nodes
        nodes = Integer.parseInt(JOptionPane.showInputDialog(frame, "Number of nodes:"));
        
        //Get the distance formula to be used
        Object[] options = {"Euclidian", "Manhattan"};
        int n = JOptionPane.showOptionDialog(frame, "Distance Formula", "Distance", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
        
        //Apply appropriate distance formula based on user input
        if(n == 0){distFormula = true;}
        else{distFormula = false;}
        
        //Return the input image file
        return input_image;
    }

    /**
     *
     * @param path
     * @throws IOException
     */
    public void voronoi(String path) throws IOException
    {
            //Initial progress message
            System.out.println("Working...");
            Random rand = new Random();
            
            //BufferedImage for original
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File(path));
            } catch (IOException e) {
                    System.out.println("IOException");
            }
            
            //Create new labels for viewing
            JLabel original = new JLabel(new ImageIcon(img));
            frame.add(original);
            
            //Create new result of same size as original
            result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

            //Set up some more frame stuff
            label = new JLabel(new ImageIcon(result));
            frame.getContentPane().add(label);
            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);

            int color = 0;
            int x = 0;
            int y = 0;
            ArrayList<Point> colors = new ArrayList<Point>();

            //Pick at random the number of nodes specified by the user
            for(int i = 0; i < nodes; i++){
                    x = rand.nextInt(img.getWidth());
                    y = rand.nextInt(img.getHeight());
                    color = img.getRGB(x, y);
                    colors.add(new Point(color, x, y));
            }

            //Go through each and every pixel and find the closest node then match the color to that node
            for(int j = 0; j < img.getHeight(); j++){
                    for(int k = 0; k < img.getWidth(); k++){
                        for(Point p: colors){
                            if(distFormula == true){
                              distance = Math.sqrt(Math.pow((p.getX() - k), 2) + Math.pow((p.getY() - j), 2));  
                            }
                            else{
                              distance = Math.abs(p.getX()-k) + Math.abs(p.getY()-j);  
                            }
                            p.setDistance(distance);
                        }

                        //Iterate through all nodes to find the closest one
                        Collections.sort(colors, new Comparator<Point>() {
                            @Override
                            public int compare(Point z1, Point z2) {
                                if (z1.getDistance() > z2.getDistance())
                                    return 1;
                                if (z1.getDistance() < z2.getDistance())
                                    return -1;
                                return 0;
                            }
                        });

                        //Get the rgb of the closest node
                        r = colors.get(0).getRed();// red component 0...255
                        g = colors.get(0).getGreen();// green component 0...255
                        b = colors.get(0).getBlue();// blue component 0...255
                        col = (r << 16) | (g << 8) | b;

                        //Set the pixel to the closest node color and refresh the screen
                        result.setRGB(k,j,col);
                        frame.repaint();
                    }
            }
            
            //Create output file and write bufferedimage to it
            File f = new File("images/output/output.png");
            ImageIO.write(result, "png", f);
            System.out.println("File write complete.");
    }
}