/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgSimple_Movie_Maker;

import guzdial.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Scanner;
import java.lang.reflect.Field;
import java.util.Random;

/**
 *
 * @author Justin
 */
public class MovieMaker {

    public void circleMove(String directory) {
        int totalNumFrames = 60;
        //Create a new movie in the specified directory from the parameters
        FrameSequencer frameSequencer = new FrameSequencer(directory);
        //Show the player.
        frameSequencer.setShown(true);
        Scanner scan = new Scanner(System.in);
        //Prompt user for inputs.
        System.out.print("Colour of the circle: ");
        String circleColour = scan.next();
        System.out.print("Preferred width of the circle: ");
        int width = scan.nextInt();
        System.out.print("Preferred the height of the circle: ");
        int height = scan.nextInt();

        //For each frame, move the circle's position to a new 
        //co-ordiante along the diagonal line.
        for (int i = 0; i < totalNumFrames; i++) {
                //New picture of size/dimension 640x480 pixels
                Picture circlePicture = new Picture(640,480);
                //Get the graphics object of the picture in order to 
                //manipulate and add to.
                Graphics circleGraphics = circlePicture.getGraphics();
                Color color;
                try {
                    //Get the specified colour name
                    Field field = Class.forName("java.awt.Color").getField(circleColour.toLowerCase());
                    color = (Color)field.get(null);
                } catch (Exception e) {
                    color = null;
                }
                //Set graphics color to the one the user specified.
                circleGraphics.setColor(color);
                //640 divided by the total frames = ~11
                //480 divided by the total frames = ~8
                circleGraphics.fillOval((i*11), 480 - (i*8), width, height);
                //Add all the frames of the circle picture together.
                frameSequencer.addFrame(circlePicture);
        }
        //Play all the frames in sequence.
        frameSequencer.play(totalNumFrames);
    }
    
    public void makeTickerTapeMovie(String directory, String text) {
        int totalNumFrames = 60;
        //Create a new movie in the specified directory from the parameters
        FrameSequencer frameSequencer = new FrameSequencer(directory);
        Random rand = new Random();

        for(int i = 0; i < totalNumFrames; i++) {
                Picture p = new Picture(640,480);
                Graphics g = p.getGraphics();
                //Set font color to black.
                g.setColor(Color.BLACK); 
                //Random font size of 0-50
                int randomNum = rand.nextInt(51);
                Font font = new Font("Verdana",Font.PLAIN,randomNum);
                //Set the new font to the graphics object.
                g.setFont(font);
                //Write the new mesage in the middle of the screen's width, and 
                //incrementing height to move it from the bottom to the top.
                //Height gets multiplied by 8 as a result of height divided by
                //total number of frames (480/60).
                g.drawString(text, (p.getWidth()/2), (p.getHeight() - i * 8));
                //Add all the frames together of the picture object.
                frameSequencer.addFrame(p);
        }
        //Play all the frames in sequence
        frameSequencer.play(totalNumFrames);
    }
    
    public void randomMovementSimulation(String directory) {
        int totalNumFrames = 60;
        FrameSequencer frameSequencer = new FrameSequencer(directory);
        
        //Randomize the circle and square's original starting locations.
        //But make sure it's not greater than the picture's dimensions.
        Random rand = new Random();
        int randWidthCircle = rand.nextInt(640);
        int randHeightCircle = rand.nextInt(480);
        int randWidthSquare = rand.nextInt(640);
        int randHeightSquare = rand.nextInt(480);
        
        //For each frame...
        for (int i = 0; i < totalNumFrames; i++) { 
                Picture p = new Picture(640,480);
                Graphics g = p.getGraphics();
                
                //Settings of object 1 (circle)
                g.setColor(Color.BLUE);
                //Randomize the direction it's going, either increase or decrease.
                int direction = rand.nextInt(2);
                if (direction == 0) {
                    //Increase the x and y value by a max of 5.
                    randWidthCircle += rand.nextInt(6);
                    randHeightCircle += rand.nextInt(6);
                    //Position of circle is increased by a max of 5 pixels with
                    //constant dimensions of 50x50 pixels.
                    g.fillOval(randWidthCircle, randHeightCircle, 50, 50);
                } else {
                    //Decrease the x and y value by a max of 5.
                    randWidthCircle -= rand.nextInt(6);
                    randHeightCircle -= rand.nextInt(6);
                    //Position of circle is decreased by a max of 5 pixels with
                    //constant dimensions of 50x50 pixels.
                    g.fillOval(randWidthCircle, randHeightCircle, 50, 50);
                }
                
                //Settings of objet 2 (square)
                g.setColor(Color.GREEN);
                //Randomize the direction it's going, either increase or decrease.
                direction = rand.nextInt(2);
                if (direction == 0) {
                    //Increase the x and y value by a max of 5.
                    randWidthSquare += rand.nextInt(6);
                    randHeightSquare += rand.nextInt(6);
                    //Position of square is increased by a max of 5 pixels with
                    //constant dimensions of 80x80 pixels.
                    g.fillRect(randWidthSquare, randHeightSquare, 80, 80);
                } else {
                    //Decrease the x and y value by a max of 5.
                    randWidthSquare -= rand.nextInt(6);
                    randHeightSquare -= rand.nextInt(6);
                    //Position of square is decreased by a max of 5 pixels with
                    //constant dimensions of 80x80 pixels.
                    g.fillRect(randWidthSquare, randHeightSquare, 80, 80);
                }
                
                //Add together the frames with the two new object's locations.
                frameSequencer.addFrame(p);
        }
        //Play the whole animation.
        frameSequencer.play(totalNumFrames);
    }
    
    public static void main(String[] args) {
        while (true){
            MovieMaker project = new MovieMaker();
            System.out.println("What would you like to do? "
                                + "\n [1] Move a circle across the screen"
                                + "\n [2] Tickertape"
                                + "\n [3] Random movement simulator"
                                + "\n [4] Exit"
                                + "\n");
            Scanner scanner = new Scanner(System.in);
            //Gets the user's selection.
            String command = scanner.nextLine();
        
            switch (command) {
                case "1":
                    System.out.println("Executing circle move task...\n");
                    //Creates the movie and puts the result in the specified directory.
                    project.circleMove("output/circle/");
                    break;
                case "2":
                    System.out.println("What string would you like to be tickertaped?");
                    Scanner scanner2 = new Scanner(System.in);
                    String text = scanner2.nextLine();
                    System.out.println("Executing ticker tape task...\n");
                    project.makeTickerTapeMovie("output/tickertape/", text);
                    break;
                case "3":
                    System.out.println("Executing random movement simulator...\n");
                    project.randomMovementSimulation("output/randomMovement");
                    break;
                case "4":
                    System.out.println("Sorry, we don't have an animation for exiting the program.");
                    System.out.println("Now exiting...");
                    System.exit(0);
                default: 
                    System.out.println("Invalid option - please try again.\n");
                    break;
            }
        }
        
    }
    
}
