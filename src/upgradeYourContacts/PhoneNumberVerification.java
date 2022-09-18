package upgradeYourContacts;

import java.io.Serializable;//if we want to write a class to file we have to implement serializable

public class PhoneNumberVerification implements Serializable {

	public boolean phoneNumberVerification(String phoneNumber) {
		if (phoneNumber.equals(""))
			return false; // empty string

		String[] groups = phoneNumberSplit(phoneNumber);
		boolean firstGroupValid = firstGroupValidation(groups[0]);// boolean true or false
		if (!firstGroupValid) {
			return false;
		}
		for (int i = 1; i < groups.length; i++) {// From second group, a group should be at least 2 symbols in length
			boolean otherGroupValid = otherGroupValidation(groups[i]);
			if (!otherGroupValid) {
				return false;
			}
		}
		if (!parethesisCheck(groups)) {
			return false;
		}

		return true;
	}

	private String[] phoneNumberSplit(String phoneNumber) {
		String[] groups = phoneNumber.split("[- ]");// entire array
		return groups;
	}

	private boolean firstGroupValidation(String firstGroup) {// checking the first group, looping through a string

		// If firstGroup is empty, i.e. "", it is invalid
		if (firstGroup.length() == 0) {
			return false;
		}
		// If no plus sign, must have at least 1 character
		else if (firstGroup.charAt(0) != '+' && firstGroup.length() == 0) { // no +, and no characters
			return false;
		} else if (firstGroup.charAt(0) == '+' && firstGroup.length() < 2) {
			// [(+,1)] length has to be 2 or more
			return false;
		}

		int i = 0; // index for String, initiated outside of for loop
		// groups[0]
		// myString.charAt(0) abc 0,1,3
		if (firstGroup.charAt(0) == '+') {
			i = 1;// iterator variable is initialized outside of for loop
		}
		for (; i < firstGroup.length(); i++) {
			char currentCharacter = firstGroup.charAt(i);// charAt only works for strings
			if (!Character.isLetterOrDigit(currentCharacter) && currentCharacter != '(' && currentCharacter != ')') {

			}
		}

		return true;// otherwise first group is valid
	}

	private boolean otherGroupValidation(String otherGroup) {
		if (otherGroup.length() < 2) {// From second group on the groups should be at least 2 symbols
			return false;
		}

		for (int i = 0; i < otherGroup.length(); i++) {
			char currentCharacter = otherGroup.charAt(i);// charAt only works for strings
			if (!Character.isLetterOrDigit(currentCharacter) && currentCharacter != '(' && currentCharacter != ')') {
				// if the character is not alphabetic or numberic, make sure not in parenthesis
				return false;
			}
		}

		return true;
	}

	private boolean parethesisCheck(String[] groups) {
		boolean firstGroupHasParentheses = false;

		for (int i = 0; i < groups.length; i++) {
			if (i >= 2) {// checks groups 3 and onward
				if (groups[i].contains("(") || groups[i].contains(")")) {
					return false;// return means exiting function completely
				}
			} else if (i == 0 || i == 1) {// Checks if both first and second has parenthesis, invalid
				if (i == 0) {
					if (groups[i].contains("(")) {
						firstGroupHasParentheses = true;
					}
				}
				if (i == 1) {
					if (groups[i].contains("(") && firstGroupHasParentheses == true) {
						return false;
					}
				}

				if (groups[i].contains("(") && groups[i].charAt(0) != '(') {
					// Checks if opening parenthesis are in the right place, if not at the beginning
					// it is invalid
					// invalid, 113(4545)
					return false;
				}
				if (groups[i].contains(")") && groups[i].charAt(groups[i].length() - 1) != ')') {// checks if closing parenthesis are in the right place
					return false;
				}

				// Check for invalid case of multiple parenthesis
				int openingCount = 0;
				int closingCount = 0;
				for (int j = 0; j < groups[i].length(); j++) {
					if (groups[i].charAt(j) == '(') {
						openingCount++;
					}
					if (groups[i].charAt(j) == ')') {
						closingCount++;
					}
				}

				if (openingCount > 1 || closingCount > 1) {// checks for multiple pairs of parenthesis
					return false; // then invalid
				}
				if (openingCount != closingCount) {// checks if imbalanced
					return false; // then invalid
				}
			}
		}

		return true;
	}

}
