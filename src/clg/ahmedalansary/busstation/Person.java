package clg.ahmedalansary.busstation;

public class Person {
	private String name;
	private String phoneNumber;
	private String id;
	private int age;

public Person(String name, String phoneNumber, String id, int age) {

		this.name = name;
		this.phoneNumber = phoneNumber;
		this.id = id;
		this.age = age;
}
public Person(String name, String phoneNumber) {

	this.name = name;
	this.phoneNumber = phoneNumber;
	
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPhoneNumber() {
	return phoneNumber;
}
public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public int getAge() {
	return age;
}
public void setAge(int age) {
	this.age = age;
}
@Override
public String toString() {
	return "Person [name=" + name + ", phoneNumber=" + phoneNumber + ", id=" + id + ", age=" + age + "]";
}


}
