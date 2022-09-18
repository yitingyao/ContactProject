package upgradeYourContacts;

import java.io.Serializable;
import java.util.Scanner;

public class Organization extends Contact implements Serializable {
	//uses serializable to write a class to file																	
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
		updateTimeLastEdited();
	}

	public Organization(String name, String number, String address) {
		super(name, number);
		this.address = address;
	}

	public String[] getFieldNames() {
		String[] fieldNames = new String[] { "name", "address", "number" };
		return fieldNames;
	}

	@Override
	public String getFieldValue(String fieldName) {
		if (fieldName.equals("name")) {
			return getName();
		}
		if (fieldName.equals("address")) {
			return address;
		}
		if (fieldName.equals("number")) {
			return getNumber();
		}
		return "";
	}

	public void setFieldValue(String fieldName, String newValue) {
		if (fieldName.equals("name")) {
			setName(newValue);
		}
		if (fieldName.equals("address")) {
			setAddress(newValue);
		}
		if (fieldName.equals("number")) {
			setNumber(newValue);
		}
	}

	@Override
	public boolean matchesSearchQuery(String searchQuery) {
		// (?i) == ignore case
		// .* == match any characters
		String pattern = "(?i).*" + searchQuery + ".*"; //return with match as long as the search query is within the string
		if (getName().matches(pattern)) {// match is a string method
			return true;
		} else {
			return false;
		}
		// System.out.println("Comparing " + getName() + " " +
		// searchQuery.toLowerCase());
		// if (getName().toLowerCase().contains(searchQuery.toLowerCase())) {
		// return true;
		// } else {
		// return false;
		// }
	}

	public void printSummary(int index) {
		System.out.println(index + 1 + ". " + getFieldIfNotEmpty(getName()));
	}

	@Override
	public boolean equals(Object otherObj) {
		// Check if otherObj is a Organization object
		if (otherObj instanceof Organization) {
			Organization otherOrganization = (Organization) otherObj;
			boolean namesEqual = getName().equals(otherOrganization.getName());
			boolean addressesEqual = getAddress().equals(otherOrganization.getAddress());
			boolean numbersEqual = getNumber().equals(otherOrganization.getNumber());
			boolean createdAtEqual = getTimeCreated().equals(otherOrganization.getTimeCreated());
			boolean editedAtEqual = getTimeLastEdited().equals(otherOrganization.getTimeLastEdited());
			return namesEqual && addressesEqual && numbersEqual && createdAtEqual && editedAtEqual;
		} else {
			return false;
		}
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Name: " + getFieldIfNotEmpty(getName()) + "\n");
		builder.append("Number: " + getFieldIfNotEmpty(getNumber()) + "\n");
		builder.append("Address: " + getFieldIfNotEmpty(getAddress()) + "\n");
		builder.append("Time created: " + getTimeCreated() + "\n");
		builder.append("Time last edit: " + getTimeLastEdited() + "\n");
		return builder.toString();
	}

	public Organization addOrganization(PhoneNumberVerification phoneNumberVerification) {
		Scanner scanner = new Scanner(System.in);
		String name = getFieldValueFromUser("name");
		System.out.print("Enter the number: >");
		String phoneNumber = scanner.nextLine();
		if (!phoneNumberVerification.phoneNumberVerification(phoneNumber)) {
			System.out.println("Wrong number format!");
			phoneNumber = "";
		} // instructions says set to empty string
		String address = getFieldValueFromUser("address");
		Organization organization = new Organization(name, phoneNumber, address);
		return organization;
	}
}
