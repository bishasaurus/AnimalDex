package AnimalDex;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Animal extends JFrame {
	/**
	 * 
	 */

	public static boolean DEBUG = true; // ONLY FOR DEV MODE, REMOVE FOR PRODUCTION BUILDS

	private static final long serialVersionUID = 1L;
	static int MAX_QTY = 100;
	static int dbItems = 0;

	static SimpleAnimal[] myDB = new SimpleAnimal[MAX_QTY];
	static JTable animalTable;
	static JButton btnAdd;
	static JButton btnAnimalDex;
	private DefaultTableModel model;
	public TableRowSorter<DefaultTableModel> sorter;
	private JTextField textField;

	public Animal() {
		super("AnimalDex");

		getContentPane().setLayout(null);

		JLabel lblAnimalName = new JLabel("Animal name");
		lblAnimalName.setBounds(15, 34, 108, 20);
		getContentPane().add(lblAnimalName);

		JTextField nameField = new JTextField();
		nameField.setBounds(238, 31, 161, 26);
		getContentPane().add(nameField);
		nameField.setColumns(10);

		JLabel lblSpecies = new JLabel("Species");
		lblSpecies.setBounds(15, 70, 69, 20);
		getContentPane().add(lblSpecies);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(238, 67, 161, 26);
		getContentPane().add(comboBox);
		comboBox.addItem("Choose...");
		comboBox.addItem("Mammal");
		comboBox.addItem("Fish");
		comboBox.addItem("Bird");
		comboBox.addItem("Insect");
		comboBox.addItem("Reptile");

		/*
		 * JTextField speciesField = new JTextField(); speciesField.setBounds(238, 67,
		 * 161, 26); getContentPane().add(speciesField); speciesField.setColumns(10);
		 */

		JLabel lblHeight = new JLabel("Color");
		lblHeight.setBounds(15, 106, 69, 20);
		getContentPane().add(lblHeight);

		JTextField colorField = new JTextField();
		colorField.setBounds(238, 109, 161, 26);
		getContentPane().add(colorField);
		colorField.setColumns(10);

		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(238, 154, 161, 29);
		getContentPane().add(btnAdd);

		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (nameField.getText().equals("") || colorField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill every field");
				} else if (comboBox.getSelectedItem() == "Choose...") {
					JOptionPane.showMessageDialog(null, "Please choose animal species");
				}

				else if (e.getActionCommand().equals("Add")) {
					String varName = (String) comboBox.getSelectedItem();
					String speciesField = comboBox.getSelectedItem().toString();
					// myDB[dbItems] = new SimpleAnimal(nameField.getText(), speciesField.getText(),
					// colorField.getText());
					addAnimal(nameField.getText(), speciesField, colorField.getText());
					populateTable();
					nameField.setText("");
					colorField.setText("");
					comboBox.setSelectedItem("Choose...");
				}
			}
		});

		JButton btnAnimalDex = new JButton("Search AnimalDex");
		btnAnimalDex.setBounds(238, 199, 161, 29);
		getContentPane().add(btnAnimalDex);

		MyEventHandler commandHandler = new MyEventHandler();

		btnAdd.addActionListener(commandHandler);
		btnAnimalDex.addActionListener(commandHandler);

		animalTable = new JTable(model = new DefaultTableModel(new Object[MAX_QTY][3], // MAX_QTY rows, 3 columns
				new String[] { "Name", "Species", "Color" }));
		sorter = new TableRowSorter<DefaultTableModel>(model);

		animalTable.setRowSorter(sorter);
		animalTable.setBounds(8, 142, 194, 90);

		new JScrollPane(animalTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(animalTable);

		/*
		 * Scrollbar scrollbar = new Scrollbar(); scrollbar.setBounds(206, 142, 26, 86);
		 * getContentPane().add(scrollbar);
		 */

	}

	private static void populateTable() {
		for (int row = 0; row < dbItems; row++) {
			animalTable.setValueAt(myDB[row].animalName, row, 0); // PlateNr
			animalTable.setValueAt(myDB[row].animalSpecies, row, 1); // Colour
			animalTable.setValueAt(myDB[row].animalColor, row, 2); // Model
		}
	}

	private static void initiateAnimalCollection() {
		myDB[0] = new SimpleAnimal("Cat", "Mammal", "Brown");
		myDB[1] = new SimpleAnimal("Goldfish", "Fish", "Gold");
		dbItems = 2;
	}

	private class MyEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent myEvent) {
			if (myEvent.getActionCommand().equals("Search AnimalDex")) {

				animalDexSearch();

			} else {
				// addAnimal();
				populateTable();
			}
		}
	}

	private void animalDexSearch() { // creating AnimalDex search window input fields

		JTextField animalName = new JTextField(10);
		JComboBox<String> animalSpecies = new JComboBox<String>();
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

		UIManager.put("OptionPane.minimumSize", new Dimension(500, 100)); // manage OptionPane size
		int result = JOptionPane.showConfirmDialog(null, myPanel, "Search AnimalDex", JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {

			ArrayList<RowFilter<DefaultTableModel, Object>> andFilters = new ArrayList<RowFilter<DefaultTableModel, Object>>();

			try {
				RowFilter<DefaultTableModel, Object> nameFilter = RowFilter.regexFilter("(?i)" + animalName.getText(),
						0);
				andFilters.add(nameFilter);

				RowFilter<DefaultTableModel, Object> speciesFilter = RowFilter
						.regexFilter("(?i)" + animalSpecies.getSelectedItem().toString(), 1);
				andFilters.add(speciesFilter);

				RowFilter<DefaultTableModel, Object> colorFilter = RowFilter.regexFilter("(?i)" + animalColor.getText(),
						1);
				andFilters.add(colorFilter);

			} catch (PatternSyntaxException e) {
				if (DEBUG) {
					e.printStackTrace();
				}
				return;
			}

			sorter.setRowFilter(RowFilter.andFilter(andFilters));

		}
	}

	private static void addAnimal(String AnimalNameF, String AnimalSpeciesF, String AnimalColorF) {

		myDB[dbItems] = new SimpleAnimal(AnimalNameF, AnimalSpeciesF, AnimalColorF);
		dbItems++;
		MAX_QTY++;

	}

	public static void main(String[] args) {
		Animal frame = new Animal();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(500, 300);
		frame.setLocationRelativeTo(null);

		initiateAnimalCollection();
		populateTable();
	}
}