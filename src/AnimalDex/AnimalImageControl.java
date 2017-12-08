package AnimalDex;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class AnimalImageControl { //class responsible for AnimalDex's image

	static String imageControl() //tries to load the default AnimalDex image and checks its availability via ImageIO and IOException; if ImageIO fails to load the image, IOException triggers and loads an alternative image from different source
	{
	String primaryImage = null;
	
		try { //tries to find the default image
			Image handleImage = ImageIO.read(new File("src/image/animalia.jpg"));//handleImage is not used itself, only for checking if the searched image is available

			primaryImage = "src/image/animaliaT.png";
			return primaryImage;		

		} catch (IOException e) { //if failure, loads alternative image
			
				primaryImage = "src/AnimalDex/animals.jpg";
				return primaryImage;	
			
		}
		
	}

}
