import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List
import java.awt.Color;
/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
    ///////////////////// constructors //////////////////////////////////

    /**
     * Constructor that takes no arguments 
     */
    public Picture ()
    {
        /* not needed but use it to show students the implicit call to super()
         * child constructors always call a parent constructor 
         */
        super();  
    }

    /**
     * Constructor that takes a file name and creates the picture 
     * @param fileName the name of the file to create the picture from
     */
    public Picture(String fileName)
    {
        // let the parent class handle this fileName
        super(fileName);
    }

    /**
     * Constructor that takes the width and height
     * @param height the height of the desired picture
     * @param width the width of the desired picture
     */
    public Picture(int height, int width)
    {
        // let the parent class handle this width and height
        super(width,height);
    }

    /**
     * Constructor that takes a picture and creates a 
     * copy of that picture
     * @param copyPicture the picture to copy
     */
    public Picture(Picture copyPicture)
    {
        // let the parent class do the copy
        super(copyPicture);
    }

    /**
     * Constructor that takes a buffered image
     * @param image the buffered image to use
     */
    public Picture(BufferedImage image)
    {
        super(image);
    }

    ////////////////////// methods ///////////////////////////////////////

    /**
     * Method to return a string with information about this picture.
     * @return a string with information about the picture such as fileName,
     * height and width.
     */
    public String toString()
    {
        String output = "Picture, filename " + getFileName() + 
            " height " + getHeight() 
            + " width " + getWidth();
        return output;

    }

    /** Method to set the blue to 0 */
    public void zeroBlue()
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                pixelObj.setBlue(0);
            }
        }
    }

    /** Method that mirrors the picture around a 
     * vertical mirror in the center of the picture
     * from left to right */
    public void mirrorVertical()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width / 2; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][width - 1 - col];
                rightPixel.setColor(leftPixel.getColor());
            }
        } 
    }

    /** Mirror just part of a picture of a temple */
    public void mirrorTemple()
    {
        int mirrorPoint = 276;
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int count = 0;
        Pixel[][] pixels = this.getPixels2D();

        // loop through the rows
        for (int row = 27; row < 97; row++)
        {
            // loop from 13 to just before the mirror point
            for (int col = 13; col < mirrorPoint; col++)
            {

                leftPixel = pixels[row][col];      
                rightPixel = pixels[row]                       
                [mirrorPoint - col + mirrorPoint];
                rightPixel.setColor(leftPixel.getColor());
                count ++;
            }
        }
        System.out.println(count);
    }

    /** copy from the passed fromPic to the
     * specified startRow and startCol in the
     * current picture
     * @param fromPic the picture to copy from
     * @param startRow the start row to copy to
     * @param startCol the start col to copy to
     */
    public void copy(Picture fromPic, 
    int startRow, int startCol)
    {
        Pixel fromPixel = null;
        Pixel toPixel = null;
        Pixel[][] toPixels = this.getPixels2D();
        Pixel[][] fromPixels = fromPic.getPixels2D();
        for (int fromRow = 0, toRow = startRow; 
        fromRow < fromPixels.length &&
        toRow < toPixels.length; 
        fromRow++, toRow++)
        {
            for (int fromCol = 0, toCol = startCol; 
            fromCol < fromPixels[0].length &&
            toCol < toPixels[0].length;  
            fromCol++, toCol++)
            {
                fromPixel = fromPixels[fromRow][fromCol];
                toPixel = toPixels[toRow][toCol];
                toPixel.setColor(fromPixel.getColor());
            }
        }   
    }

    /** Method to create a collage of several pictures */
    public void createCollage()
    {
        Picture flower1 = new Picture("flower1.jpg");
        Picture flower2 = new Picture("flower2.jpg");
        this.copy(flower1,0,0);
        this.copy(flower2,100,0);
        this.copy(flower1,200,0);
        Picture flowerNoBlue = new Picture(flower2);
        flowerNoBlue.zeroBlue();
        this.copy(flowerNoBlue,300,0);
        this.copy(flower1,400,0);
        this.copy(flower2,500,0);
        this.mirrorVertical();
        this.write("collage.jpg");
    }

    /** Method to show large changes in color 
     * @param edgeDist the distance for finding edges
     */
    public void edgeDetection(int edgeDist)
    {
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        Pixel[][] pixels = this.getPixels2D();
        Color rightColor = null;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; 
            col < pixels[0].length-1; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][col+1];
                rightColor = rightPixel.getColor();
                if (leftPixel.colorDistance(rightColor) > 
                edgeDist)
                    leftPixel.setColor(Color.BLACK);
                else
                    leftPixel.setColor(Color.WHITE);
            }
        }
    }

    /* Main method for testing - each class in Java can have a main 
     * method 
     */
    public static void main(String[] args) 
    {
        Picture beach = new Picture("beach.jpg");
        beach.explore();
        beach.zeroBlue();
        beach.explore();
    }

    public void mirrorVerticalRightToLeft()
    {
        {

            Pixel[][] pixels = this.getPixels2D();

            Pixel leftPixel = null;

            Pixel rightPixel = null;

            int width = pixels[0].length;

            for (int row = 0; row < pixels.length; row++)

            {

                for (int col = 0; col < width / 2; col++)

                {

                    leftPixel = pixels[row][col];

                    rightPixel = pixels[row][width - 1 - col];

                    rightPixel.setColor(leftPixel.getColor());

                }

            } 

        }
    }

    public void mirrorHorizontal()
    {
        Pixel[][] pixels = this.getPixels2D();
        int pixlength = pixels.length/2;
        for (int col = 0; col < pixels[0].length; col++)
        {
            for (int row = 0; row < pixlength; row++)
            {
                int distfrommid = pixlength - row;

                Pixel topPixel = pixels[row][col];
                Pixel botPixel = pixels[pixlength + distfrommid - 1][col];
                botPixel.setColor(topPixel.getColor());
            }
        }

    }

    public void mirrorHorizontalBotToTop()
    {
        Pixel[][] pixels = this.getPixels2D();
        int pixlength = pixels.length/2;
        for (int col = 0; col < pixels[0].length; col++)
        {
            for (int row = pixlength; row < pixels.length; row++)
            {
                int distfrommid = row - pixlength;

                Pixel botPixel = pixels[row][col];
                Pixel topPixel = pixels[pixlength + distfrommid - 1][col];
                topPixel.setColor(botPixel.getColor());
            }
        }

    }

    public void mirrorArms()
    {
        int mirrorPoint = 200;
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int count = 0;
        Pixel[][] pixels = this.getPixels2D();

        // loop through the rows
        for (int row = 27; row < 97; row++)
        {
            // loop from 13 to just before the mirror point
            for (int col = 13; col < mirrorPoint; col++)
            {

                leftPixel = pixels[row][col];      
                rightPixel = pixels[row]                       
                [mirrorPoint - col + mirrorPoint];
                rightPixel.setColor(leftPixel.getColor());
                count ++;
            }
        }
        System.out.println(count);
    }

    public void mirrorGull()
    {
        Pixel[][] pixels = this.getPixels2D();
        int mirrorPoint = 235;
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int count = 0;
        int amount = 0;

        // loop through the rows
        for (int row = 0; row < pixels.length; row++)
        {

            for (int col = 0; col < mirrorPoint; col++)
            {

                leftPixel = pixels[row][col];
                amount = mirrorPoint - 1 - col;
                rightPixel = pixels[row]                       
                [mirrorPoint + amount];
                rightPixel.setColor(leftPixel.getColor());
                count ++;
            }
        }
        System.out.println(count);

    }

    public void cropAndCopy(Picture sourcePicture, int startSourceRow, int endSourceRow, int startSourceCol, int endSourceCol,
    int startDestRow, int startDestCol)
    {
        Pixel [] [] forpixels = sourcePicture.getPixels2D();
        Pixel [] [] topixels = this.getPixels2D();
        int currcoldif = 0;
        int currrowdif = 0;
        for (int i = startSourceRow; i < endSourceRow; i++)
        {
            currcoldif = 0;
            for (int j = startSourceCol; j < endSourceCol; j++)
            {
                topixels [startDestRow + currrowdif][startDestCol + currcoldif].setColor(forpixels [i] [j].getColor());

                currcoldif ++;
            }
            currrowdif ++;
        }

    }

    public void tint(String color)
    {
        Pixel [] [] temp = this.getPixels2D();
        for (Pixel []  pixelrow : temp)
        {
            for (Pixel pixel : pixelrow)
            {
                if (color.equals("red"))
                {
                    pixel.setRed(pixel.getRed()+40);
                    
                }
                else if (color.equals("blue"))
                {
                    pixel.setBlue(pixel.getBlue()+40);
                }
                else if (color.equals("green"))
                {
                    pixel.setGreen(pixel.getGreen()+40);
                }
            }
        }
    }
    //this is the official method for the collage lab
    public void makeCollage(Picture sourcePicture)
    {
        this.cropAndCopy(sourcePicture, 0, sourcePicture.getHeight(), 0, sourcePicture.getWidth(),0,0);  

        Picture temp = new Picture(sourcePicture.getHeight(),sourcePicture.getWidth());
        temp.copyPicture(sourcePicture);
        temp.mirrorHorizontal();
        this.cropAndCopy(temp, 0,sourcePicture.getHeight(),0,sourcePicture.getWidth(),0,sourcePicture.getWidth());
        
        temp.copyPicture(sourcePicture);
        temp.tint("blue");
        this.cropAndCopy(temp, 0,sourcePicture.getHeight(),0,sourcePicture.getWidth(),sourcePicture.getHeight(),0);
        
        temp.copyPicture(sourcePicture);
        temp.tint("green");
        this.cropAndCopy(temp, 0,sourcePicture.getHeight(),0,sourcePicture.getWidth(),sourcePicture.getHeight(),sourcePicture.getWidth());
    }
} // this } is the end of class Picture, put all new methods before this
