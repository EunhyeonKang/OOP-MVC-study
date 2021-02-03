package guimain;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GuiMain {
	private ArrayList<String []> fileData = new ArrayList<String []>();
	private ArrayList<String []> tempList, sortedFileData, leftMerge, rightMerge;
	private JLabel statusLabel, resultLabel;
	private static String displayResult = "", displayStatus = "";

	// Constructor
	public GuiMain() {

		//// FRAME ////
		JFrame frame = new JFrame("Assignment #5");
		frame.setLayout(new BorderLayout());
		frame.setBounds(50,50,400,200);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		//// BUTTONS ////
		JPanel btnPanel = new JPanel();

		JButton selectionSortBtn = new JButton("Selection Sort");
		selectionSortBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// update status as currently sorting
				displayStatus = "Status: Processing selection sort. Please wait...";
				statusLabel.setText(displayStatus);

				float startTime = System.nanoTime();

				selectionSortFile(fileData);

				float sortTime = (System.nanoTime() - startTime);
				displayResult = "Sorted in: " + (sortTime/1000000000) + " seconds";
				resultLabel.setText(displayResult);

				if(verifyAlphabetization(sortedFileData) == true) {
					displayStatus = "Status: Selection sort complete. Alphabetization Verified.";
					statusLabel.setText(displayStatus);
				}
				else {
					displayStatus = "Status: Selection sort complete. Unable to verify alphabetization.";
					statusLabel.setText(displayStatus);
				}
			}
		});
		btnPanel.add(selectionSortBtn);

		JButton mergeSortBtn = new JButton("Merge Sort");
		mergeSortBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				float startTime = System.nanoTime();	// start timer
				mergeSortFile(fileData);	// sort data

				float sortTime = (System.nanoTime() - startTime);	// end timer
				displayResult = "Sorted in: " + (sortTime/1000000000) + " seconds";		// display timer
				resultLabel.setText(displayResult);

				if(verifyAlphabetization(sortedFileData) == true) {
					displayStatus = "Status: Merge sort complete. Alphabetization Verified.";
					statusLabel.setText(displayStatus);
				}
				else {
					displayStatus = "Status: Merge sort complete. Unable to verify alphabetization.";
					statusLabel.setText(displayStatus);
				}

			}
		});
		btnPanel.add(mergeSortBtn);

		JButton printBtn = new JButton("Print List");
		printBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					printSortedFile();
					displayStatus = "Status: Alphabetized list printed in console.";
					statusLabel.setText(displayStatus);
				}
				catch (Exception ex) {
					displayStatus = "Status: Error! Unable to print list. Please sort first.";
					statusLabel.setText(displayStatus);
				}
			}
		});
		btnPanel.add(printBtn);
		frame.add(btnPanel, BorderLayout.NORTH);

		//// SORT RESULTS ////
		resultLabel = new JLabel(displayResult);
		resultLabel.setHorizontalAlignment(JLabel.CENTER);
		frame.add(resultLabel, BorderLayout.CENTER);

		//// PROGRAM STATUS ////
		statusLabel = new JLabel(displayStatus);
		frame.add(statusLabel, BorderLayout.SOUTH);

		readFile();
	}	// end GuiMain()

	private void readFile() {
		try {
			FileReader f = new FileReader("phonebook.txt");
			BufferedReader br = new BufferedReader(f);
			String line = br.readLine();

			displayStatus = "Status: Loading file. Please wait...";
			statusLabel.setText(displayStatus);
			while (line != null) {
				String[] parts = line.split(" ");
				parts[1] = parts[1].substring(0,  parts[1].length()-1);		// remove comma from last name
				fileData.add(parts);
				line = br.readLine();
			}
			displayStatus = "Status: File read complete.";
			statusLabel.setText(displayStatus);
		} catch (Exception e) {
			displayStatus = "Status: Error! Could not read file.";
			statusLabel.setText(displayStatus);
			System.err.println("Error: Unable to read file");
			e.printStackTrace();
		}
	}	// end readFile()

	private String getLastName(int line) {
		@SuppressWarnings("unused")
		String lastNameField;
		String[] dataLine = tempList.get(line);
		return lastNameField = dataLine[1];
	}	// end getLastName()

	private String getFirstName(int line) {
		@SuppressWarnings("unused")
		String firstNameField;
		String[] dataLine = tempList.get(line);
		return firstNameField = dataLine[2];
	}	// end getFirstName()

	private String[] getAllDataLine(int line) {
		String[] dataLine = tempList.get(line);
		return dataLine;
	}	// end getAllDataLine()

	private String getLeftMergeLastName(int line) {
		String lastNameField;
		String[] dataLine = leftMerge.get(line);
		return lastNameField = dataLine[1];
	}	// end getLeftMergeLastName()

	private String getRightMergeLastName(int line) {
		String lastNameField;
		String[] dataLine = rightMerge.get(line);
		return lastNameField = dataLine[1];
	}	// end getRightMergeLastName()

	private void printSortedFile() {
		for (String[] arr : sortedFileData) {
			System.out.println(Arrays.toString(arr));
		}
	}	// end printSortedFile()

	private void deleteArrayList(ArrayList<String[]> toBeDeleted) {
		for (int i = 0; i < toBeDeleted.size(); i++) {
			toBeDeleted.remove(i);
		}
	}	// end deleteArrayList()

	private ArrayList<String[]> selectionSortFile(ArrayList<String[]> dataToBeSorted) {
		tempList = new ArrayList<String[]>(dataToBeSorted);		// create copy of original ArrayList file data
		for (int i = 0; i < tempList.size(); i++) {
			int min = i;
			for (int j = i + 1; j < tempList.size(); j++) {
				if (getLastName(min).equals(getLastName(j))) {		// if same last name, then
					if (getFirstName(min).compareTo(getFirstName(j)) > 0){		// compare first names
						min = j;
					}
				}
				if (getLastName(min).compareTo(getLastName(j)) > 0) {		// compare last names
					min = j;
				}
			}
			String[] temp = getAllDataLine(i);
			tempList.set(i, getAllDataLine(min));
			tempList.set(min, temp);
		}
		sortedFileData = new ArrayList<String[]>(tempList);		// record completed sorted arraylist
		deleteArrayList(tempList);	// release temp list
		return sortedFileData;
	}	// end selectionSortFile()

	private ArrayList<String[]> mergeSortFile(ArrayList<String[]> dataToBeSorted) {
		tempList = new ArrayList<String[]>(dataToBeSorted);		// create copy of original ArrayList file data
		leftMerge = new ArrayList<String[]>();
		rightMerge = new ArrayList<String[]>();

		// if arraylist only has 1 data element, it's already sorted by definition
		if (tempList.size() == 0) {
			sortedFileData = new ArrayList<String[]>(tempList);		// record completed sorted arraylist
			deleteArrayList(tempList);	// release temp list
			return sortedFileData;
		}
		else {
			int center = tempList.size()/2;
			// copy left half
			for (int i = 0; i < center; i++) {
				leftMerge.add(getAllDataLine(i));
			}
			// copy right half
			for (int i = center; i < tempList.size(); i++) {
				rightMerge.add(getAllDataLine(i));
			}
			// sort left and right halves via recursion
			leftMerge = mergeSortFile(leftMerge);
			rightMerge = mergeSortFile(rightMerge);
			// merge halves back together
			merge(leftMerge, rightMerge, tempList);
		}
		// copy then delete tempList
		sortedFileData = new ArrayList<String[]>(tempList);
		deleteArrayList(tempList);
		return sortedFileData;
	}	// end mergeSortFile()

	private void merge(ArrayList<String[]> left, ArrayList<String[]> right, ArrayList<String[]> list) {
		int leftIndex = 0, rightIndex = 0, tempIndex = 0;
		// keep adding until left and right merge arraylists are used up
		while (leftIndex < left.size() && rightIndex < right.size()) {
			if (getLeftMergeLastName(leftIndex).compareTo(getRightMergeLastName(rightIndex)) < 0) {
				list.set(tempIndex, getAllDataLine(leftIndex));
				leftIndex++;
			}
			else {
				list.set(tempIndex, getAllDataLine(rightIndex));
				rightIndex++;
			}
			tempIndex++;
		}
		ArrayList<String[]> rest = new ArrayList<String[]>();
		int restIndex;
		if (leftIndex >= left.size()) {		// leftMerge arraylist is done
			rest = right;
			restIndex = rightIndex;
		}
		else {		// rightMerge arraylist is done
			rest = left;
			restIndex = leftIndex;
		}
		for (int i = restIndex; i < rest.size(); i++) {		// copy either arraylist that has not been used up
			list.set(tempIndex, getAllDataLine(i));
			tempIndex++;
		}
	}	// end merge()

	private boolean verifyAlphabetization(ArrayList<String[]> dataToBeVerified) {
		for (int i = 0; i < (dataToBeVerified.size()/2-1); i++) {
			//System.out.println(i + ": " + getLastName(i) + " - " + getLastName(i+1));
			if (getLastName(i).compareTo(getLastName(i+1)) > 0) {
				return false;
			}
		}
		return true;
	}	// end verifyAlphabetization()

	//// Main ////
	public static void main(String[] args) {
		GuiMain run = new GuiMain();
	}	//end main()
}	// end GuiMain class