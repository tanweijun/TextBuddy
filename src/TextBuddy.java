
/*
 * CS2103 CE1
 * Name			: Tan Wei Jun
 * Matric No.	: A0125360R
 * Assumptions: - File is saved after each user operation.
 *				- Program exits if no file name is entered.
 *				- File is created if it does not exist and the file name entered is always correct.
 *				- Line numbers are not added in the file, it is only shown when printing.
 * Description: This program opens a file specified by the user and allows the user to 
 * 				display the file's content, add text to it, delete a certain line in
 * 				the file and clear the file. The program ends when the user enters "exit".
 */
import java.util.*;
import java.io.*;

public class TextBuddy {
	public static void main(String[] args) {

		exitIfNoArg(args);
		printWelcomeMessage(args[0]);
		runUntilExit(args[0]);
	}

	// Runs the program until "exit" is entered as the command.
	// Exits immediately if file name is not entered.
	private static void runUntilExit(String file) {
		Scanner sc = new Scanner(System.in);
		System.out.print("command: ");
		String command = sc.next();
		while (!command.equalsIgnoreCase("exit")) {
			if (command.equalsIgnoreCase("add")) {
				addToFile(file, sc);
			} else if (command.equalsIgnoreCase("display")) {
				displayFileContent(file);
			} else if (command.equalsIgnoreCase("delete")) {
				deleteLineInFile(file, sc);
			} else if (command.equalsIgnoreCase("clear")) {
				clearFile(file);
			}
			System.out.print("command: ");
			command = sc.next();
		}
		sc.close();
	}

	private static void clearFile(String fileName) {
		try {
			File file = new File(fileName);
			file.delete();
			File newFile = new File(fileName);
			newFile.createNewFile();
			System.out.println("all content deleted from " + file);
		} catch (Exception e) {
			System.out.println("Error clearing file.");
		}
	}

	private static void deleteLineInFile(String fileName, Scanner sc) {
		try {
			int rowToDel = sc.nextInt();
			sc.nextLine();

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

			System.out.println("deleted from " + fileName + ": \"" + deletedLine + "\"");
			bufferedReader.close();
			bufferedWriter.close();
			originalFile.delete();
			tempFile.renameTo(originalFile);

		} catch (Exception e) {
			System.out.println("Error deleting line in file.");
		}
	}

	private static void displayFileContent(String fileName) {
		try {
			File file = new File(fileName);
			if (file.length() == 0) {
				System.out.println(fileName + " is empty.");
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
			System.out.println("Error reading file.");
		}
	}

	private static void addToFile(String fileName, Scanner sc) {
		try {
			FileWriter fileWriter = new FileWriter(fileName, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			String input = sc.nextLine();
			bufferedWriter.write(input.trim());
			bufferedWriter.newLine();
			bufferedWriter.close();
			System.out.println("added to " + fileName + ": \"" + input.trim() + "\"");
		} catch (Exception e) {
			System.out.println("Error writing to file.");
		}
	}

	private static void exitIfNoArg(String[] args) {
		if (args.length == 0) {
			System.out.println("File name not entered. Exiting program.");
			System.exit(0);
		}
	}

	private static void printWelcomeMessage(String fileName) {
		System.out.println("Welcome to TextBuddy. " + fileName + " is ready for use.");
	}

}
