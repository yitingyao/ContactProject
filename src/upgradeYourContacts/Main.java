package upgradeYourContacts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	static Scanner scanner = new Scanner(System.in);
	static PhoneBook phoneBook = new PhoneBook();
	static PhoneNumberVerification phoneNumberVerification = new PhoneNumberVerification();

	public static void main(String[] args) {
		while (true) {
			System.out.print("[menu] Enter action (add, list, search, count, exit): > "); //main menu to return to after each choice
			String action = scanner.nextLine();
			if (action.equalsIgnoreCase(Action.ADD.name())) {
				addRecord();
			}
			if (action.equalsIgnoreCase(Action.COUNT.name())) {
				countRecords();
			}
			if (action.equalsIgnoreCase(Action.SEARCH.name())) {
				searchRecords();
			}
			if (action.equalsIgnoreCase(Action.LIST.name())) {
				if (phoneBook.lengthOfPhoneBook() == 0) {
					System.out.println("No records to list!");
				} else {
					// Print summary of contacts
					phoneBook.printContacts();// loop through phonebook for each contact, calls printSummary()
					listContactsMenu();
				}
			}
			if (action.equalsIgnoreCase(Action.EXIT.name())) {
				break;
			}
		}
	}

	public static void searchRecords() {
		int choice = 0;
		boolean done = false;

		while (!done) {

			System.out.print("Enter search query: >"); //enters search query to be matched
			String userSearchQuery = scanner.nextLine();

			ArrayList<Contact> matchingContacts = phoneBook.searchContacts(userSearchQuery);// search contacts return an
																							// // matchingContacts

			for (int i = 0; i < matchingContacts.size(); i++) {
				//after finding all matching contacts and storing to matchingContacts arraylist
				//loop through to print them out

				Contact contact = matchingContacts.get(i);
				//getting existing contact from arraylist matchingContacts and storing it in variable contact
				//did not make a new contact object
				//could've also done matchingContacts.get(i).printSummary(i) same thing
				contact.printSummary(i);
			}

			System.out.print("[search] Enter action ([number], back, again): > ");
			String contactChoice = scanner.nextLine();

			if (contactChoice.equalsIgnoreCase("back")) {
				return;
			} else if (contactChoice.equalsIgnoreCase("again")) {
				continue; // go back to the top of the loop: Enter search query: >
			} else if (validStringNumberInRange(contactChoice, 1, matchingContacts.size())) {
				//checks if String contactchoice is parasable to integer
				//checks if it's within bounds from 1 to matchContacts size
				done = true;

				// action is the number of the contact 1. abc
				// > 1
				// matchingContacts = [abc], index = 0
				choice = Integer.parseInt(contactChoice); //parses to integer 
				Contact matchingContact = matchingContacts.get(choice - 1);
				//retrieves contact selected, stores into variable matchingContact
				//subtracts 1 to convert to index
				//needs integer to put into get()
				// abc, [Tina, another, abc] -> 2
				int phoneBookIndex = phoneBook.findContactIndex(matchingContact);
				//finds the corresponding contact in the original phonebook arraylist
				//e.g it was 2 in matching arraylist, but another number in phonebook
				recordDetailsAction(phoneBookIndex + 1);
			} else {
				System.out.println("Invalid action.");
			}
		}
	}

	public static boolean validStringNumberInRange(String stringNum, int min, int max) {
		//converts String input of contact choice into an integer
		//makes sure userinput for list falls within valid range e.g 1 to endoflist
		try {
			int parsedNum = Integer.parseInt(stringNum);//converts into integer
			if (parsedNum < min || parsedNum > max) {//if greater than size of matchingContacts arraylist or less than 1
				return false;
			} else {
				return true;
			}
		} catch (NumberFormatException e) {// String cannot be converted into integer
			return false;
		}
	}

	public static void addRecord() {

		String contactType = "";
		boolean done = false;
		while (!done) {
			System.out.println("Enter the the type (person, organization): >");
			contactType = scanner.nextLine();
			if (contactType.equalsIgnoreCase("person") || contactType.equalsIgnoreCase("organization")) {
				done = true; // if either person or organizagtion stop asking, breaks out of while loop
			}

		}
		if (contactType.equalsIgnoreCase("person")) {
			Person person = new Person("", "", "", "", "");// dummy object
			Person newPerson = person.addPerson(phoneNumberVerification);
			phoneBook.addPhoneBook(newPerson);// adds new person object into arraylist

		}
		if (contactType.equalsIgnoreCase("organization")) {
			Organization organization = new Organization("", "", "");// dummy object
			Organization newOrganization = organization.addOrganization(phoneNumberVerification);
			phoneBook.addPhoneBook(newOrganization);// adds new organization object into arraylist

		}
		System.out.println("The record added.");
		phoneBook.saveContacts(); //contacts are being saved 
		System.out.println("The record is saved!");
	}

	public static void listContactsMenu() {//prints out list of saved contacts to choose from

		System.out.print("[list] Enter action ([number], back):");

		int choice = 0;
		boolean done = false;
		while (!done) {
			System.out.print("> ");
			String input = scanner.nextLine();

			// Check if user typed "back" and go back to previous menu
			if (input.equalsIgnoreCase("back"))
				return;

			// Check if user input is a number between 1 and phonebook length.
			// If so, call parse to integer to check if valid, if so use parsed choice and
			// call recordMenu
			else if (validStringNumberInRange(input, 1, phoneBook.lengthOfPhoneBook())) {//1 is min
				done = true;//runs the rest of the block, stops the while loop on the next run
				choice = Integer.parseInt(input); //if false this else if block never runs
			} else {
				System.out.println("Invalid choice.");
			}
		}

		recordDetailsAction(choice);//after a valid choice is selected, calls method to print out record details
	}

	public static void recordDetailsAction(int recordNumber) {// lists out contact details each time changes are made in
																// record

		Contact selectedContact = phoneBook.getContact(recordNumber);// gets the selected contact to print

		while (true) {// Prints out the selected contact details including name, surname, birthdate etc
			System.out.println(selectedContact.toString());
			// Every class has its own toString, made our own
			System.out.print("[record] Enter action (edit, delete, menu): > ");
			String action = scanner.nextLine();

			if (action.equalsIgnoreCase("menu")) {
				break;
			} else if (action.equalsIgnoreCase("edit")) {
				editRecord(recordNumber);
			} else if (action.equalsIgnoreCase("delete")) {
				removeRecord(recordNumber);
				break;
			} else {
				System.out.println("Invalid action, try again.");
			}
		}
	}

	public static void countRecords() {
		System.out.printf("The Phone Book has %d records.\n", phoneBook.lengthOfPhoneBook());
	}

	private static void editRecord(int recordNumber) {
		Contact editContact = phoneBook.getContact(recordNumber); // gets contact at selected record to be edited

		String[] fieldNames = editContact.getFieldNames();
		//The array is soley used for printing out fieldnames menu for Person or Organization
		//After selecting a contact, this stores the array(name, surname, birthdate, gender, number) into variable fieldnames

		System.out.print("Select a field (" + Arrays.toString(fieldNames).replace("[", "").replace("]", "") + "): > ");
		//used toString to print array fieldNames in readable format, replaced square with round brackets
		String fieldChoice = scanner.nextLine(); //taking in choice of either name, surname

		boolean isValidChoice = Arrays.asList(fieldNames).contains(fieldChoice);
		//converts fieldNames into arraylist, checks if contains the user selected choice
		//since cannot use arrays.contains
		if (!isValidChoice) {
			System.out.println("Invalid input!");
			return;
		}
		System.out.print("Enter " + fieldChoice + ": >");
		String inputField = scanner.nextLine();
		editContact.setFieldValue(fieldChoice, inputField);
		phoneBook.saveContacts();
		System.out.println("The record is saved!");
	}

	public static void removeRecord(int recordNumber) {
		phoneBook.removeContact(recordNumber);
		phoneBook.saveContacts();
		System.out.println("The record is removed!");
	}
}
