/*
 * CS2103 CE2
 * Author		: Tan Wei Jun
 * Matric No.	: A0125360R
 * Assumptions: - File is saved after each user operation.
 *				- Program exits if no file name is entered.
 *				- File is created if it does not exist and the file name entered is always correct.
 *				- Line numbers are not added in the file, it is only shown when printing.
 * Description: This program opens a file specified by the user and allows the user to 
 * 				display the file's content, add text to it, delete a certain line in
 * 				the file, clear the file, sort the file, and search the file for lines containing 
 * 				a specific word. The program ends when the user enters "exit".
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class TextBuddy {

	//constants for printing string feedbacks
	private static final String MESSAGE_FILE_NOT_ENTERED = "File name not entered. Exiting program.";
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %s is ready for use.";
	private static final String MESSAGE_ENTER_COMMAND = "command: ";
	private static final String MESSAGE_ERROR = "Error executing command.";
	private static final String MESSAGE_CLEAR = "all content deleted from %s";
	private static final String MESSAGE_DELETED_LINE = "deleted from %s: \"%s\"";
	private static final String MESSAGE_ADDED_LINE = "added to %s: \"%s\"";
	private static final String MESSAGE_FILE_EMPTY = "%s is empty.";
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command. Please try again.";
	private static final String MESSAGE_SORTED = "File content has been sorted.";
	private static final String MESSAGE_NO_RESULTS = "No results found.";
	
	//constants of index of the command and data parts of the user input to be used after splitting the input string
	private static final int COMMAND_INDEX = 0;
	private static final int DATA_INDEX = 1;
	
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {

		exitIfNoArg(args);
		printFeedback(String.format(MESSAGE_WELCOME, args[0]));
		runUntilExit(args[0]);
	}

	private static void runUntilExit(String fileName) {
		String input = getUserInput(sc);
		String command = getCommandFromUserInput(input);
		while (!command.equalsIgnoreCase("exit")) {
			String feedback = executeCommandByType(fileName, command, input);
			printFeedback(feedback);
			input = getUserInput(sc);
			command = getCommandFromUserInput(input);
		}
		sc.close();
	}
	
	//prompts the user for input and takes in the input
	private static String getUserInput(Scanner sc) {
		System.out.print(MESSAGE_ENTER_COMMAND);
		String input = sc.nextLine();	
		return input;
	}
	
	//splits the user input and return only the data portion without the command
	private static String getDataFromUserInput(String input) {
		try {
			String[] data = input.split(" ", 2);
			return data[DATA_INDEX];
		} catch (Exception e) {
			return "";
		}
	}

	//splits the user input and return only the command to be executed
	private static String getCommandFromUserInput(String input) {
		String[] command = input.split(" ", 2);
		return command[COMMAND_INDEX];
	}

	//executes a command base on user input and return the feedback string
	public static String executeCommandByType(String file, String command, String input) {
		if (command.equalsIgnoreCase("add")) {
			String data = getDataFromUserInput(input);
			String feedback = addToFile(file, data);
			return feedback;
		} else if (command.equalsIgnoreCase("display")) {
			String feedback = displayFileContent(file);
			return feedback;
		} else if (command.equalsIgnoreCase("delete")) {
			String data = getDataFromUserInput(input);
			String feedback = deleteLineInFile(file, data);
			return feedback;
		} else if (command.equalsIgnoreCase("clear")) {
			String feedback = clearFile(file);
			return feedback;
		} else if (command.equalsIgnoreCase("sort")) {
			String feedback = sortFileContent(file);
			return feedback;
		} else if (command.equalsIgnoreCase("search")) {
			String data = getDataFromUserInput(input);
			String feedback = searchFile(file, data);
			return feedback;
		} else {
			return MESSAGE_INVALID_COMMAND;
		}
	}
	
	//prints a string as feedback
	private static void printFeedback(String feedback) {
		System.out.println(feedback);
	}
	
	//search the file for a specified word and return the lines containing it
	private static String searchFile(String fileName, String data) {
		try {
			ArrayList<String> fileContent = addContentToArray(fileName);
			String searchResults = getSearchResults(data, fileContent);
			if (searchResults.length() == 0) {
				return MESSAGE_NO_RESULTS;
			} else {
				return searchResults;
			}
		} catch (Exception e) {
			return MESSAGE_ERROR;
		}
	}

	//search an arraylist for a specified word and return the lines containing it
	//this is the main search logic for the searchFile() method
	private static String getSearchResults(String data, ArrayList<String> fileContent) {
		String searchResults = "";
		int numbering = 1;
		for (int i = 0; i < fileContent.size(); i++) {
			String line = fileContent.get(i).toLowerCase().replaceAll("[^\\w\\s]","");
			String[] wordsInLine = line.split(" ");
			if (Arrays.asList(wordsInLine).contains(data.toLowerCase())) {
				searchResults = searchResults + numbering + ". " + fileContent.get(i) + "\r\n";
				numbering++;
			}
		}
		searchResults = searchResults.trim();
		return searchResults;
	}

	//empty the file
	private static String clearFile(String fileName) {
		try {
			File file = new File(fileName);
			file.delete();
			File newFile = new File(fileName);
			newFile.createNewFile();
			return String.format(MESSAGE_CLEAR, file);
		} catch (Exception e) {
			return MESSAGE_ERROR;
		}
	}

	//remove a specified line in the file
	private static String deleteLineInFile(String fileName, String data) {
		try {
			int rowToDel = Integer.valueOf(data);

			File originalFile = new File(fileName);
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			File tempFile = new File("tempFile.txt");
			FileWriter fileWriter = new FileWriter("tempFile.txt", true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			String line = "";
			String deletedLine = "";
			int lineNum = 0;
			while ((line = bufferedReader.readLine()) != null) {
				lineNum += 1;
				if (lineNum != rowToDel) {
					bufferedWriter.write(line);
					bufferedWriter.newLine();
				} else {
					deletedLine = line;
				}
			}
			
			bufferedReader.close();
			bufferedWriter.close();
			originalFile.delete();
			tempFile.renameTo(originalFile);
			return String.format(MESSAGE_DELETED_LINE, fileName, deletedLine);
		} catch (Exception e) {
			return MESSAGE_ERROR;
		}
	}

	//returns content of the file in a string with added line numbers
	private static String displayFileContent(String fileName) {
		try {
			File file = new File(fileName);
			if (file.length() == 0) {
				return String.format(MESSAGE_FILE_EMPTY, fileName);
			} else {
				FileReader fileReader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String output = "";
				String line = "";
				int lineNum = 0;
				while ((line = bufferedReader.readLine()) != null) {
					lineNum += 1;
					output += lineNum + ". " + line + "\r\n";
				}
				output = output.trim();
				bufferedReader.close();
				return output;
			}
		} catch (Exception e) {
			return MESSAGE_ERROR;
		}
	}

	//append data into the file
	private static String addToFile(String fileName, String data) {
		try {
			FileWriter fileWriter = new FileWriter(fileName, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(data);
			bufferedWriter.newLine();
			bufferedWriter.close();
			return String.format(MESSAGE_ADDED_LINE, fileName, data);
		} catch (Exception e) {
			return MESSAGE_ERROR;
		}
	}

	//prints an error message and exit the program if no file is specified
	private static void exitIfNoArg(String[] args) {
		if (args.length == 0) {
			System.out.println(MESSAGE_FILE_NOT_ENTERED);
			System.exit(0);
		}
	}

	//sorts the file by lines in alphabetical order ignoring case
	private static String sortFileContent(String fileName) {
		ArrayList<String> contentToSort = addContentToArray(fileName);
		Collections.sort(contentToSort, String.CASE_INSENSITIVE_ORDER);
		String feedback = returnSortedContentToFile(fileName, contentToSort);
		return feedback;
	}

	//replace data in a file with data that is already sorted
	private static String returnSortedContentToFile(String fileName, ArrayList<String> contentToSort) {
		try {
			clearFile(fileName);
			for (int i = 0; i < contentToSort.size(); i++) {
				addToFile(fileName, contentToSort.get(i));
			}
			return MESSAGE_SORTED;
		} catch (Exception e) {
			return MESSAGE_ERROR;
		}
	}

	//adds data into a file to an arraylist and return it
	private static ArrayList<String> addContentToArray(String fileName) {
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			ArrayList<String> contentList = new ArrayList<String>();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				contentList.add(line);
			}
			bufferedReader.close();
			return contentList;
		} catch (Exception e) {
			return null;
		}
	}
}
