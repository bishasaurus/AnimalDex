package AnimalDex;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Animal extends JFrame {

	private static final long serialVersionUID = 1L;

	static final int MAX_QTY = 22; //determines the maximum amount of AnimalDex entries
	static int dbItems = 0; //amount of SimpleAnimal instances when program launches, increases with creation of instances. Becomes 2 when initiateAnimalCollection() is run while launching

	static SimpleAnimal[] myDB = new SimpleAnimal[MAX_QTY]; //creates Array for SimpleAnimal instances, having the same limit as AnimalDex entries
	static JTable animalTable; //creates JTable for showing AnimalDex entries
	static JButton btnAdd; //determines buttons for adding and searching AnimalDex entries
	static JButton btnAnimalDex;
	private DefaultTableModel model; //determines model for the table
	public static TableRowSorter<DefaultTableModel> sorter; //creates TableRowSorter for AnimalDex entries filtering

	protected static int comparableValue = 3; //the value that checks when myDB value has increased => new instance has been created, and only then empties the input fields for new entry. Further info in later appearances

	public Animal() {
		super("AnimalDex");

		getContentPane().setLayout(null);

		JLabel lblAnimalName = new JLabel("Animal name"); //creates JTextFields for inputting animal parameters, and corresponding JLabels for each field to tell to what input on them all
		lblAnimalName.setBounds(15, 34, 108, 20);
		getContentPane().add(lblAnimalName);

		JTextField nameField = new JTextField();
		nameField.setBounds(251, 31, 161, 26);
		getContentPane().add(nameField);
		nameField.setColumns(10);

		JLabel lblSpecies = new JLabel("Species");
		lblSpecies.setBounds(15, 70, 69, 20);
		getContentPane().add(lblSpecies);

		JComboBox<String> comboBox = new JComboBox<String>(); //creates JComboBox for choosing animal species from prefixed choices, making it easier to pick the right species for the user
		comboBox.setBounds(251, 67, 161, 26);
		getContentPane().add(comboBox);
		comboBox.addItem("Choose...");
		comboBox.addItem("Mammal");
		comboBox.addItem("Fish");
		comboBox.addItem("Bird");
		comboBox.addItem("Insect");
		comboBox.addItem("Reptile");


		JLabel lblHeight = new JLabel("Color");
		lblHeight.setBounds(15, 106, 69, 20);
		getContentPane().add(lblHeight);

		JTextField colorField = new JTextField();
		colorField.setBounds(251, 103, 161, 26);
		getContentPane().add(colorField);
		colorField.setColumns(10);

		JButton btnAdd = new JButton("Add"); //adds Add-button below to the input fields
		btnAdd.setBounds(251, 163, 161, 29);
		getContentPane().add(btnAdd);

		btnAdd.addActionListener(new ActionListener() { //determines the functions of Add-button
			public void actionPerformed(ActionEvent e) {
				if (nameField.getText().equals("") || colorField.getText().equals("")) { //checks if JTextFields have any text; if not, gives an error message and stops execution of the function
					JOptionPane.showMessageDialog(null, "Please fill every field");
				} else if (comboBox.getSelectedItem() == "Choose...") { //checks if JComboBox is left with the default value; if it is, gives an error message and stops execution of the function
					JOptionPane.showMessageDialog(null, "Please choose animal species");
				}

				else if (e.getActionCommand().equals("Add")) { //if everything's okay...
					String speciesField = comboBox.getSelectedItem().toString(); //...gets comboBox value and changes it to String...
					addAnimal(nameField.getText(), speciesField, colorField.getText()); //...and gives the inputed Strings to addAnimal()-function to continue on the creation of new instance
					
					if(comparableValue == dbItems){ //after addAnimal() is done, checks if creation was successful with comparing the two values; the values equal each other if new instance has been created (comparableValue starts with 3, dbItems with 2, dbItems becomes 3 if new object has been created), meaning that input fields can be reseted for the addition of new instance. Adds one to the value of comparableValue if the values matched, to keep it usable after adding new instances
					nameField.setText("");
					colorField.setText("");
					comboBox.setSelectedItem("Choose...");
					populateTable();
					comparableValue++;
					}				
				}
			}
		});
		

		JButton btnAnimalDex = new JButton("Search AnimalDex"); //adds button for searching AnimalDex in the UI
		btnAnimalDex.setBounds(251, 197, 161, 29);
		getContentPane().add(btnAnimalDex);

		MyEventHandler commandHandler = new MyEventHandler(); //commandHandler is created to handle events launched by buttons
		
		btnAdd.addActionListener(commandHandler); //commandHandler added to buttons
		btnAnimalDex.addActionListener(commandHandler);

		animalTable = new JTable(model = new DefaultTableModel(new Object[MAX_QTY][4], //creates JTable on the UI for displaying SimpleAnimal instances
				new String[] { "Name", "Species", "Color", "Weight" })); //determines JTable fields
		sorter = new TableRowSorter<DefaultTableModel>(model); //sorter is assigned to filter JTable entries. Will be used by Search AnimalDex's functions
		animalTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //allows only one row to be chosen for the "Weight to selection" buttons use

		animalTable.setRowSorter(sorter);
		animalTable.setBounds(479, 170, 382, 351);

		getContentPane().add(animalTable);
		
		ImageIcon image = new ImageIcon(AnimalImageControl.imageControl()); //creates image in left bottom of the program's UI, the image itself is handled by separate class, which is summoned via function (check AnimalImageControl)
		JLabel imagelabel = new JLabel(image);
		imagelabel.setBounds(15, 242, 449, 279);
		getContentPane().add(imagelabel);
		
		JLabel lblAnimalName_1 = new JLabel("Animal"); //following labels exist to make the table's data easier to read, labeling each field with corresponding name
		lblAnimalName_1.setBounds(497, 116, 60, 20);
		getContentPane().add(lblAnimalName_1);
		
		JLabel lblName = new JLabel("name");
		lblName.setBounds(499, 137, 69, 20);
		getContentPane().add(lblName);
		
		JLabel lblSpecies_1 = new JLabel("Species");
		lblSpecies_1.setBounds(593, 137, 69, 20);
		getContentPane().add(lblSpecies_1);
		
		JLabel lblColor = new JLabel("Color");
		lblColor.setBounds(697, 137, 69, 20);
		getContentPane().add(lblColor);
		
		JLabel lblWeight = new JLabel("Weight (kg)");
		lblWeight.setBounds(773, 137, 83, 20);
		getContentPane().add(lblWeight);
		
		JButton btnResetButton = new JButton("Reset search"); //a new button for reseting search results and reverting the table to its normal state
		btnResetButton.setBounds(60, 197, 144, 29);
		getContentPane().add(btnResetButton);
		
		JButton btnWeightButton = new JButton("Add weight"); //a new button for adding weight value for an instance
		btnWeightButton.setBounds(748, 66, 113, 29);
		getContentPane().add(btnWeightButton);
		
		JLabel lblSelectAnimalFrom = new JLabel("Select animal from table and input weight here:");
		lblSelectAnimalFrom.setBounds(479, 34, 412, 20);
		getContentPane().add(lblSelectAnimalFrom);
		
		JTextField weightField = new JTextField();
		weightField.setBounds(587, 67, 146, 26);
		getContentPane().add(weightField);
		weightField.setColumns(10);
		
		btnResetButton.addActionListener(commandHandler); //commandHandlers for the new buttons
		//btnWeightButton.addActionListener(commandHandler);
		
		btnWeightButton.addActionListener(new ActionListener() { //determines the functions of Add weight-button
			public void actionPerformed(ActionEvent e) {
				int chosenRow = animalTable.getSelectedRow()+1;
				if(chosenRow == 0) //checks is any row is selected; if not, asks user to select a row
				{
					JOptionPane.showMessageDialog(null, "Please choose an animal from the table");
				}
				else if(animalTable.getSelectedRow() > dbItems-1) //compares selected row value to the amount of instances in array; if row of higher index than amount of instances is chosen, it means that the row can't have animal data, and so adding weight value is meaningless
				{
					JOptionPane.showMessageDialog(null, "Please choose a row with animal data in it");
				}
					
				else if (weightField.getText().equals("")) { //checks if JTextFields have any text; if not, gives an error message and stops execution of the function
					JOptionPane.showMessageDialog(null, "Input field cannot be empty");
					}	
				else if(weightField.getText().matches("[a-zA-Z0-9 ]*") == false) //checks input field for special symbols to prevent wrong value from progressing to the setter
				{
					JOptionPane.showMessageDialog(null, "Only numbers are allowed for weight value");
				}
					else
					{
						
						if(myDB[animalTable.getSelectedRow()].setAnimalWeight(weightField.getText()) == false) //uses setAnimalWeight to do the rest of input validation and tries, if setting weight value for the instance's animalWeight variable is possible currently (check SimpleAnimal)
						{
							JOptionPane.showMessageDialog(null, "Please give a number value for weight");
						}
						else if(myDB[animalTable.getSelectedRow()].setAnimalWeight(weightField.getText()) == true) //if addition is successful, uses setAnimalWeight to set value to instance's animalWeight, informs the user about successful addition and resets input field
						{
							myDB[animalTable.getSelectedRow()].setAnimalWeight(weightField.getText());
							JOptionPane.showMessageDialog(null, "Weight has been added!");
							weightField.setText("");
						}

					}
				}
			});
		
	}

	static void populateTable() { //function that is in charge of filling the table with SimpleAnimal instances; is run every time a new instance is created, and when program is launched to show initiateAnimalCollection()-function's results
		for (int row = 0; row < dbItems; row++) {
			animalTable.setValueAt(myDB[row].animalName, row, 0); 
			animalTable.setValueAt(myDB[row].animalSpecies, row, 1); 
			animalTable.setValueAt(myDB[row].animalColor, row, 2); 
			animalTable.setValueAt(myDB[row].animalWeight, row, 3);
		}
	}

	private static void initiateAnimalCollection() { //like the name indicates, initiates the SimpleAnimal instance collection with two samples. Run when program starts
		myDB[0] = new SimpleAnimal("Cat", "Mammal", "Brown");
		myDB[1] = new SimpleAnimal("Goldfish", "Fish", "Gold");
		dbItems = 2;
	}

	private class MyEventHandler implements ActionListener { //handles the button presses of "Search AnimalDex" and "Reset search", showing the direction to functions program should run when each button is pressed
		public void actionPerformed(ActionEvent myEvent) {
			if (myEvent.getActionCommand().equals("Search AnimalDex"))
				{

				animalDexSearch();
				
				} 
				else if(myEvent.getActionCommand().equals("Reset search")) 
				{
					
				animalDexSearchResetAlt();
				
				}
	
				}
	
			}	
	
	private void animalDexSearch() { // function called by pressing "Search AnimalDex", creates search window input fields and UI

		JTextField animalName = new JTextField(10);
		
		JComboBox<String> animalSpecies = new JComboBox<String>();
		animalSpecies.addItem("");
		animalSpecies.addItem("Mammal");
		animalSpecies.addItem("Fish");
		animalSpecies.addItem("Bird");
		animalSpecies.addItem("Insect");
		animalSpecies.addItem("Reptile");
		
		JTextField animalColor = new JTextField(10);
		
	
		JPanel myPanel = new JPanel();

		myPanel.add(new JLabel("Animal name"));
		myPanel.add(animalName);

		myPanel.add(new JLabel("Species"));
		myPanel.add(animalSpecies);

		myPanel.add(new JLabel("Color"));
		myPanel.add(animalColor);
		
		

		UIManager.put("OptionPane.minimumSize", new Dimension(500, 100)); // creates OptionPane to let user press 'ok' to search, or 'cancel' to cancel the search
		int result = JOptionPane.showConfirmDialog(null, myPanel, "Search AnimalDex", JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {

			ArrayList<RowFilter<DefaultTableModel, Object>> andFilters = new ArrayList<RowFilter<DefaultTableModel, Object>>(); //utilizes RowFilter to filter JTable entries by what user has inputed in search fields. Program takes on account everything the user has inputed in the search fields, and show results that apply to every filter the user has given (for example, name has to have letter 'c', species must be mammal and color must have letters 'bl'). Fields can be left empty too to not filter anything from that specific field

			try {
				RowFilter<DefaultTableModel, Object> nameFilter = RowFilter.regexFilter("(?i)" + animalName.getText(), 0);
				andFilters.add(nameFilter);

				RowFilter<DefaultTableModel, Object> speciesFilter = RowFilter.regexFilter("(?i)" + animalSpecies.getSelectedItem().toString(), 1);
				andFilters.add(speciesFilter);

				RowFilter<DefaultTableModel, Object> colorFilter = RowFilter.regexFilter("(?i)" + animalColor.getText(), 2);
				andFilters.add(colorFilter);
				
				sorter.setRowFilter(RowFilter.andFilter(andFilters)); 
				

			} catch (PatternSyntaxException e) { //program tries the above method, if fails gives an indication of syntax errors and prevents the program from crashing
				
					e.printStackTrace();
					return;
			}	
		}
	}

	static void animalDexSearchResetAlt() { //resets "Search AnimalDex"-buttons effects, turning the table back to normal, showing all the entries there exist without filters
		ArrayList<RowFilter<DefaultTableModel, Object>> andFilters = new ArrayList<RowFilter<DefaultTableModel, Object>>();

		try { //does similar thing than the search function, only not allowing user input and searching for "nothing", practically meaning that the program searches for every instance because there are no limiting filters for search results => resets search results
			RowFilter<DefaultTableModel, Object> nameFilter = RowFilter.regexFilter("(?i)");
			andFilters.add(nameFilter);

			RowFilter<DefaultTableModel, Object> speciesFilter = RowFilter
					.regexFilter("(?i)");
			andFilters.add(speciesFilter);

			RowFilter<DefaultTableModel, Object> colorFilter = RowFilter.regexFilter("(?i)");
			andFilters.add(colorFilter);
			
			sorter.setRowFilter(RowFilter.andFilter(andFilters));
			populateTable();
			

		} catch (PatternSyntaxException e) {
			
				e.printStackTrace();
				return;
		}
		
	}	
	
	private static void addAnimal(String AnimalNameF, String AnimalSpeciesF, String AnimalColorF) { //receives values from the btnAdd-buttons function to make SimpleAnimal instance

		if(Animal.MAX_QTY == Animal.dbItems){ //check if the array is full, if it is prevents the creation of another instance
			JOptionPane.showMessageDialog(null, "AnimalDex full!");
			}
			else
			{	
				myDB[dbItems] = new SimpleAnimal(AnimalNameF, AnimalSpeciesF, AnimalColorF); //continues on creating SimpleAnimal instance (check SimpleAnimal class)
				
				if(comparableValue == dbItems){
					
					populateTable(); //populateTable() after a successful instance creation
					
				}		
			}
		}

	public static void main(String[] args) { //entry point for the program, creating and determining all the necessities for the program
		Animal frame = new Animal();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(943, 596);
		frame.setLocationRelativeTo(null);

		initiateAnimalCollection(); //inserts the samples to the table
		populateTable();
	}
}
