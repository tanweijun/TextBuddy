/*
 * CS2103 CE1
 * Author		: Tan Wei Jun
 * Matric No.	: A0125360R
 * Assumptions: - File is saved after each user operation.
 *				- Program exits if no file name is entered.
 *				- File is created if it does not exist and the file name entered is always correct.
 *				- Line numbers are not added in the file, it is only shown when printing.
 * Description: This program opens a file specified by the user and allows the user to 
 * 				display the file's content, add text to it, delete a certain line in
 * 				the file and clear the file. The program ends when the user enters "exit".
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class TextBuddy {

	private static final String MESSAGE_FILE_NOT_ENTERED = "File name not entered. Exiting program.";
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %s is ready for use.";
	private static final String MESSAGE_ENTER_COMMAND = "command: ";
	private static final String MESSAGE_ERROR = "Error executing command.";
	private static final String MESSAGE_CLEAR = "all content deleted from %s";
	private static final String MESSAGE_DELETED_LINE = "deleted from %s: \"%s\"";
	private static final String MESSAGE_ADDED_LINE = "added to %s: \"%s\"";
	private static final String MESSAGE_FILE_EMPTY = "%s is empty.";
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command. Please try again.";
	
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {

		exitIfNoArg(args);
		printWelcomeMessage(args[0]);
		runUntilExit(args[0]);
	}

	private static void runUntilExit(String fileName) {
		String input = getUserInput(sc);
		String command = getCommandFromUserInput(input);
		while (!command.equalsIgnoreCase("exit")) {
			executeCommandByType(fileName, command, input);
			input = getUserInput(sc);
			command = getCommandFromUserInput(input);
		}
		sc.close();
	}
	
	private static String getUserInput(Scanner sc) {
		System.out.print(MESSAGE_ENTER_COMMAND);
		String input = sc.nextLine();	
		return input;
	}
	
	private static String getDataFromUserInput(String input) {
		String[] data = input.split(" ", 2);
		return data[1];
	}

	private static String getCommandFromUserInput(String input) {
		String[] command = input.split(" ", 2);
		return command[0];
	}

	public static void executeCommandByType(String file, String command, String input) {
		if (command.equalsIgnoreCase("add")) {
			addToFile(file, input);
		} else if (command.equalsIgnoreCase("display")) {
			displayFileContent(file);
		} else if (command.equalsIgnoreCase("delete")) {
			deleteLineInFile(file, input);
		} else if (command.equalsIgnoreCase("clear")) {
			clearFile(file);
		} else if (command.equalsIgnoreCase("sort")) {
		
		} else {
			System.out.println(MESSAGE_INVALID_COMMAND);
		}
	}

	private static void clearFile(String fileName) {
		try {
			File file = new File(fileName);
			file.delete();
			File newFile = new File(fileName);
			newFile.createNewFile();
			System.out.println(String.format(MESSAGE_CLEAR, file));
		} catch (Exception e) {
			System.out.println(MESSAGE_ERROR);
		}
	}

	private static void deleteLineInFile(String fileName, String input) {
		try {
			String data = getDataFromUserInput(input);
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

			System.out.println(String.format(MESSAGE_DELETED_LINE, fileName, deletedLine));
			bufferedReader.close();
			bufferedWriter.close();
			originalFile.delete();
			tempFile.renameTo(originalFile);

		} catch (Exception e) {
			System.out.println(MESSAGE_ERROR);
		}
	}

	private static void displayFileContent(String fileName) {
		try {
			File file = new File(fileName);
			if (file.length() == 0) {
				System.out.println(String.format(MESSAGE_FILE_EMPTY, fileName));
			} else {
				FileReader fileReader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String line = "";
				int lineNum = 0;
				while ((line = bufferedReader.readLine()) != null) {
					lineNum += 1;
					System.out.println(lineNum + ". " + line);
				}
				bufferedReader.close();
			}
		} catch (Exception e) {
			System.out.println(MESSAGE_ERROR);
		}
	}

	private static void addToFile(String fileName, String input) {
		try {
			FileWriter fileWriter = new FileWriter(fileName, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			String data = getDataFromUserInput(input);
			bufferedWriter.write(data);
			bufferedWriter.newLine();
			bufferedWriter.close();
			System.out.println(String.format(MESSAGE_ADDED_LINE, fileName, data));
		} catch (Exception e) {
			System.out.println(MESSAGE_ERROR);
		}
	}

	private static void exitIfNoArg(String[] args) {
		if (args.length == 0) {
			System.out.println(MESSAGE_FILE_NOT_ENTERED);
			System.exit(0);
		}
	}

	private static void printWelcomeMessage(String fileName) {
		System.out.println(String.format(MESSAGE_WELCOME, fileName));
	}

	public static void sortFileContent(String string) {
		// TODO Auto-generated method stub
		
	}

}
