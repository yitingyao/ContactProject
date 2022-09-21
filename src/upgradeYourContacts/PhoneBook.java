package upgradeYourContacts;

import java.io.FileInputStream;//used to obtain input bytes from a file in a file system
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;//phonebook stores the data

public class PhoneBook {
	private ArrayList<Contact> phoneBooks;

	public PhoneBook() {//default constructor, runs one time at the beginning, to load contacts in phonebook.txt
		this.phoneBooks = new ArrayList<>();
		//load the file within phonebook constructor such that each time the program is run
		//the previous created contacts are loaded into new arraylist
		try {//sets the new phonebook arraylist to the data in the file
				//the same data but a brand new arraylist each time
			FileInputStream fileStream = new FileInputStream("phonebook2.txt");
			//This is where the file is opened, the data in file is bytes
			ObjectInputStream objectStream = new ObjectInputStream(fileStream);
			//Need an ObjectInputStream to convert the bytes in the file to a Java object or a form of data that Java understands
			//Takes in the "file stream" (the file) on the right-hand side
			// Read load data from the file
			Object readData = objectStream.readObject();//converted to Java object
			//This is where the bytes are read from the file, converting into an object, and storing it in a variable
			this.phoneBooks = (ArrayList<Contact>) readData;
			//Cast back the data into its actual type. Java only knows it's some random type of object
			// Cleanup
			objectStream.close();
			fileStream.close();
		} catch (IOException ioError) {
			// if file doesn't exist, set phoneBooks to empty arraylist
			this.phoneBooks = new ArrayList<Contact>();
		} catch (ClassNotFoundException classError) {//trying to load a class that doesn't exist
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

	public void saveContacts() {
		//where phonebook.txt is created and written on. The txt file is made when the first contact is added
		//this method is called within addRecords()
		// Save to a file
		try {

			FileOutputStream fileStream = new FileOutputStream("phonebook2.txt");
			//where the file is being opened, txt is created if it doesn't exist
			ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
			//need an ObjectInputStream to convert the bytes in the file to a Java obejct
			//or a form of data that Java understands
			//Notice it takes the file stream on the right-hand side
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