package upgradeYourContacts;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Scanner;

public abstract class Contact implements Serializable {
	private String name;
	private String number;
	private LocalDateTime timeCreated;
	private LocalDateTime timeLastEdited;

	public abstract String[] getFieldNames();

	public abstract String getFieldValue(String fieldName);

	public abstract void setFieldValue(String fieldName, String newValue);

	public abstract void printSummary(int index);

	public abstract boolean matchesSearchQuery(String searchQuery); // Contact and Person each has their own implementation

	public void setName(String name) {//updates time last edited whenever name is set
		this.name = name;
		updateTimeLastEdited();
	}

	public void setNumber(String number) {//updates time last edited whenever number is set
		this.number = number;
		updateTimeLastEdited();
	}

	public Contact(String name, String number) {//updates time created whenever a new contact is created
		this.name = name;
		this.number = number;
		this.timeCreated = LocalDateTime.now(); // localdatetime is object of class, now is function
		this.timeLastEdited = LocalDateTime.now();
	}

	public String getName() {
		return name;
	}

	public String getNumber() {
		return number;
	}

	public LocalDateTime getTimeCreated() {
		return timeCreated;
	}

	public LocalDateTime getTimeLastEdited() {
		return timeLastEdited;
	}

	public void updateTimeLastEdited() {
		this.timeLastEdited = LocalDateTime.now();
	}

	public String getFieldIfNotEmpty(String valueToCheck) {//checks if value is empty to return [no data]
		if (valueToCheck.equals("")) {
			return "[no data]";
		}
		return valueToCheck;
	}

	public String getFieldValueFromUser(String fieldName) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the " + fieldName + ": >");
		String fieldValue = scanner.nextLine();
		if (fieldValue.equals("")) {
			System.out.println("Bad " + fieldName + "!");
			return "";

		} else
			return fieldValue;

	}
}