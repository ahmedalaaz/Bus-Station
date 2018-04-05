package clg.ahmedalansary.busstation;

public  class Employee extends Person{

private String workID;
private String workPass;
private byte type;
public Employee(String name, String phoneNumber, String id, int age, String workID, String workPass,byte type) {
		super(name, phoneNumber, id, age);
		this.workID = workID;
		this.workPass = workPass;
		this.type = type;
}
public String getWorkID() {
	return workID;
}
public void setWorkID(String workID) {
	this.workID = workID;
}
public String getWorkPass() {
	return workPass;
}
public void setWorkPass(String workPass) {
	this.workPass = workPass;
}
public byte getType() {
	return type;
}
public void setType(byte type) {
	this.type = type;
}

public String getCellText() {
	String t =  type == 1?"Manager":"Driver";
	String ret = "Name : " +  this.getName() + "\n\n" + "Phone number : "+ this.getPhoneNumber() + "\n\n"
			+ "Type : "+t;
	return ret;
}
}
