package upgradeYourContacts;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;//phonebook stores the data

public class PhoneBook {
	private ArrayList<Contact> phoneBooks;

	public PhoneBook() {
		this.phoneBooks = new ArrayList<>();
		// Person person = new Person("Tina", "", "Yao", "F", "Jun17");
		// Organization otherCompany = new Organization("another", "411", "Another
		// St.");
		// Organization company = new Organization("abc", "311", "JohnSt.");

		// this.phoneBooks.add(person);
		// this.phoneBooks.add(otherCompany);
		// this.phoneBooks.add(company);

		try {
			FileInputStream fileStream = new FileInputStream("phonebook.txt");
			ObjectInputStream objectStream = new ObjectInputStream(fileStream);

			// Read / load data from the file
			Object readData = objectStream.readObject();
			this.phoneBooks = (ArrayList<Contact>) readData;

			// Cleanup
			objectStream.close();
			fileStream.close();
		} catch (IOException ioError) {
			// if file doesn't exist, set phoneBooks to empty arraylist
			this.phoneBooks = new ArrayList<Contact>();
		} catch (ClassNotFoundException classError) {
			classError.printStackTrace();
		}
	}

	public boolean addPhoneBook(Contact contact) {
		phoneBooks.add(contact);
		return true;
	}

	public int lengthOfPhoneBook() {
		return phoneBooks.size();
	}

	public void printContacts() {// just printing objects already created
		for (int i = 0; i < phoneBooks.size(); i++) {
			Contact contact = phoneBooks.get(i); // getting the contact at index i and storing it in variable contacts
			contact.printSummary(i); // polymorphism, in both organization and person
		}
	}

	public void removeContact(int recordNumber) {
		int index = recordNumber - 1;
		phoneBooks.remove(index);
	}

	public Contact getContact(int recordNumber) {// get contact object from arraylist
		int index = recordNumber - 1;
		return phoneBooks.get(index);
	}

	public ArrayList<Contact> searchContacts(String userSearchQuery) {//loops through each contact and checks if each contact fits search query
		ArrayList<Contact> matchingContacts = new ArrayList<>(); //use an arraylist to store the list of contacts that match
		//e.g 1. Tina Yao 2. Gtina dfa

		for (int i = 0; i < phoneBooks.size(); i++) {
			Contact contact = phoneBooks.get(i); // getting the contact at index i and storing it in variable contacts
			boolean contactMatchesQuery = contact.matchesSearchQuery(userSearchQuery);//calling the person or organization implemnetation of matchesSearchQuery
			if (contactMatchesQuery) {
				matchingContacts.add(contact); // if matches, add the matching contact to another arraylist, such that the user knows
			}
		}

		return matchingContacts;
	}

	public int findContactIndex(Contact contactToFind) {//finds matching contact object in original phonebook arraylist
		// 1. Loop through the phone book array
		for (int i = 0; i < phoneBooks.size(); i++) {
			// 2. Compare matching contact to phonebook contact
			Contact phoneBookContact = phoneBooks.get(i);
			if (contactToFind.equals(phoneBookContact)) {
				//implemented our own equals method for person and organization, Java will compare them
				//3. once find a match use that index
				return i;
			}
		}

		return -1;

	}

	public void saveContacts() {//where phonebook.txt is created and written on. The txt file is made when first exited.
		// Save to a file
		try {
			FileOutputStream fileStream = new FileOutputStream("phonebook.txt");
			ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
			objectStream.writeObject(this.phoneBooks);

			// Done writing, cleanup
			objectStream.close();
			fileStream.close();
		} catch (IOException ioError) {
			// Print error details
			ioError.printStackTrace();
		}
	}
}