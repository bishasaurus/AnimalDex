package AnimalDex;

import javax.swing.JOptionPane;

public class SimpleAnimal { //class for constructing SimpleAnimal -instances for animalTable
	
	//variables for AnimalDex instances
	String animalName;
	String animalSpecies;
	String animalColor;
	int validationValue;
	
	private Boolean setAnimalName (String Name) { //checks if animal name has any special letters; if it has, sends back 'false' to prevent the creation of instance
		if (Name.matches("[a-zA-Z]+$")){
			return true;
		}
		else
		{
			return false;
		}
	}	
	
	private Boolean setAnimalColor (String Color) { ////checks if animal color has any special letters; if it has, sends back 'false' to prevent the creation of instance
		if (Color.matches("[a-zA-Z]+$")){
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	SimpleAnimal (String aAnimalName, String aAnimalSpecies, String aAnimalColor) //constructor for AnimalDex instances, receiving three String values to create an instance with needed parameters
	{
		String phName = aAnimalName;; //the variables which receive corresponding parameters from constructor
		String phSpecies = aAnimalSpecies;
		String phColor = aAnimalColor;
		
		if(this.setAnimalName(phName) == false) //if/else if -statements to go through the received String values from Animal class; if unallowed characters are present, the user receives message about which string needs editing before it gets allowed. Species is not checked, because it's already done in Animal class
		{
			JOptionPane.showMessageDialog(null, "Give an English animal name (no numbers or special symbols either)");
			this.animalName = null;
			this.animalSpecies = null;
			this.animalColor = null;
			return;
		}
		else if(this.setAnimalColor(phColor) == false)	
		{
			JOptionPane.showMessageDialog(null, "Write a color without numbers or special symbols");
			this.animalName = null;
			this.animalSpecies = null;
			this.animalColor = null;
			return;
		}
		else if(this.setAnimalName(phName) == true && this.setAnimalColor(phColor) == true) //if everything is fine, creates the instance with the given values and adds one to the value of dbItems, indicating that new object has been created
		{
			Animal.animalDexSearchResetAlt();
			this.animalName = phName;
			this.animalSpecies = phSpecies;
			this.animalColor = phColor;
			Animal.dbItems++;
		} //returns to Animal class
	}

}
