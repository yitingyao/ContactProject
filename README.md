# ContactProject
A program to create contacts and search for people or organizations by name.
It allows users to add, edit, save, and remove contacts. 
Generates time created and time last edited of each contact.
Performs phone number validation using regex.
Utilizes object-oriented programming concepts including abstraction encapsulation, inheritance, and polymorphism.

The following is an example of the project implementation:

[menu] Enter action (add, list, search, count, exit): > list

1. Tina Yao
[list] Enter action ([number], back):> back
[menu] Enter action (add, list, search, count, exit): > add
Enter the the type (person, organization): >
organization
Enter the name: >Coffee Shop
Enter the number: >111
Enter the address: >123 Street
The record added.
The record is saved!
[menu] Enter action (add, list, search, count, exit): > count
The Phone Book has 2 records.
[menu] Enter action (add, list, search, count, exit): > search
Enter search query: >ti
1. Tina Yao
[search] Enter action ([number], back, again): > 1
Name: Tina
Surname: Yao
Birthdate: Sep21
Gender: F
Number: 111
Time created: 2022-09-21T12:43:00.135523500
Time last edit: 2022-09-21T12:43:00.135523500

[record] Enter action (edit, delete, menu): > edit
Select a field (name, surname, birthdate, gender, number): > 
