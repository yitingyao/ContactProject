package upgradeYourContacts;

import java.io.Serializable;
import java.util.Scanner;

public class Person extends Contact implements Serializable {// have to use serializable to write a class to a file
	private String surname;
	private String gender;
	private String birthdate;

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
		updateTimeLastEdited();
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
		updateTimeLastEdited();
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
		updateTimeLastEdited();
	}

	public Person(String name, String number, String surname, String gender, String birthdate) {
		super(name, number);
		this.surname = surname;
		this.gender = gender;
		this.birthdate = birthdate;
	}

	public void printPerson(int index) {
		if (super.getNumber().equals("")) {
			System.out.printf("%d. %s %s, [no number]\n", index + 1, super.getName(), getSurname());
		} else {
			System.out.printf("%d. %s %s, %s\n", index + 1, super.getName(), getSurname(), super.getNumber());
		}
	}

	public void editPerson(Scanner scanner, PhoneNumberVerification phoneNumberVerification) {
		System.out.print("Select a field (name, surname, number): >");
		String selection = scanner.next();
		if (selection.equals("name")) {
			System.out.print("Enter name: > ");
			scanner.nextLine();
			String nameUpdate = scanner.next();
			super.setName(nameUpdate);// updates first name
		}
		if (selection.equals("surname")) {
			System.out.print("Enter surname: > ");
			scanner.nextLine();
			String surnameUpdate = scanner.next();
			setSurname(surnameUpdate);// updates last name
		}
		if (selection.equals("number")) {
			System.out.print("Enter number: > ");
			scanner.nextLine();
			String numberUpdate = scanner.nextLine();
			if (!phoneNumberVerification.phoneNumberVerification(numberUpdate)) {
				System.out.println("Wrong number format!");
				super.setNumber(""); // instructions says set to empty string
			} else {
				super.setNumber(numberUpdate);// updates phonenumber
			}
		}
	}

	public String[] getFieldNames() {
		String[] fieldNames = new String[] { "name", "surname", "birthdate", "gender", "number" };
		return fieldNames;
	}

	public String getFieldValue(String fieldName) {
		if (fieldName.equals("name")) {
			return getName();
		}
		if (fieldName.equals("surname")) {
			return getSurname();
		}
		if (fieldName.equals("birthdate")) {
			return birthdate;
		}
		if (fieldName.equals("gender")) {
			return gender;
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
		if (fieldName.equals("surname")) {
			setSurname(newValue);
		}
		if (fieldName.equals("birthdate")) {
			setBirthdate(newValue);
		}
		if (fieldName.equals("gender")) {
			setGender(newValue);
		}
		if (fieldName.equals("number")) {
			setNumber(newValue);
		}
	}

	public void printSummary(int index) {
		System.out.println(index + 1 + ". " + getFieldIfNotEmpty(getName()) + " " + getFieldIfNotEmpty(getSurname()));
	}

	@Override
	public boolean matchesSearchQuery(String searchQuery) {//implementation for Person class
		String fullName = getName() + " " + getSurname();
		// (?i) == ignore case
		// .* == match any characters

		//".*tina.*"
		//hello tina!

		String pattern = "(?i).*" + searchQuery + ".*"; //regex is the pattern
		if (fullName.matches(pattern)) {// match is a string method, checks with matches pattern
			return true;
		} else {
			return false;
		}
		//		System.out.println("Comparing " + getName() + " " + searchQuery.toLowerCase());
		//		System.out.println("Comparing " + getSurname() + " " + searchQuery.toLowerCase());
		//		if (getName().toLowerCase().contains(searchQuery.toLowerCase())
		//				|| getSurname().toLowerCase().contains(searchQuery.toLowerCase())) {
		//			return true;
		//		} else {
		//			return false;
		//		}
	}

	public String toString() {//The makeup of recordDetails summary
		StringBuilder builder = new StringBuilder();
		builder.append("Name: " + getFieldIfNotEmpty(getName()) + "\n");
		builder.append("Surname: " + getFieldIfNotEmpty(getSurname()) + "\n");
		builder.append("Birthdate: " + getFieldIfNotEmpty(getBirthdate()) + "\n");
		builder.append("Gender: " + getFieldIfNotEmpty(gender) + "\n");
		builder.append("Number: " + getFieldIfNotEmpty(getNumber()) + "\n");
		builder.append("Time created: " + getTimeCreated() + "\n");
		builder.append("Time last edit: " + getTimeLastEdited() + "\n");

		return builder.toString();
	}

	//	public boolean equals(Object otherObj) {//implemented our own equals object
	//		// Check if otherObj is a Person object 
	//		//checks if name, surname, birthday, genders equal
	//		if (otherObj instanceof Person) {
	//			Person otherPerson = (Person) otherObj;
	//			boolean namesEqual = getName().equals(otherPerson.getName());
	//			boolean surnamesEqual = getSurname().equals(otherPerson.getSurname());
	//			boolean birthdatesEqual = birthdate.equals(otherPerson.getBirthdate());
	//			boolean gendersEqual = gender.equals(otherPerson.getGender());
	//			boolean numbersEqual = getNumber().equals(otherPerson.getNumber());
	//			boolean createdAtEqual = getTimeCreated().equals(otherPerson.getTimeCreated());
	//			boolean editedAtEqual = getTimeLastEdited().equals(otherPerson.getTimeLastEdited());
	//
	//			return namesEqual && surnamesEqual && birthdatesEqual && gendersEqual && numbersEqual && createdAtEqual
	//					&& editedAtEqual;
	//		} else {
	//			return false;
	//		}
	//	}

	public Person addPerson(PhoneNumberVerification phoneNumberVerification) {
		Scanner scanner = new Scanner(System.in);

		String name = getFieldValueFromUser("name");
		String surname = getFieldValueFromUser("surname");
		System.out.print("Enter the number: >");
		String phoneNumber = scanner.nextLine();
		if (!phoneNumberVerification.phoneNumberVerification(phoneNumber)) {
			System.out.println("Wrong number format!");
			phoneNumber = "";
		} // instructions says set to empty string
		String birthdate = getFieldValueFromUser("birthdate");
		System.out.print("Enter the gender (M, F): >");
		String gender = scanner.nextLine();
		boolean validGender = gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F");
		if (!validGender) {
			System.out.println("Bad gender!");
			gender = "";
		}
		Person person = new Person(name, phoneNumber, surname, gender, birthdate);

		return person;
	}
}
