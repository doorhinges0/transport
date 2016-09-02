package com.realtimestudio.transport.model.baisha;

import static com.realtimestudio.transport.utils.DateUtils.getYearDiff;

import java.util.Date;

import com.realtimestudio.transport.utils.JsonUtils;

public class Driver {
	private long id;	
	private String name;
	private String address;
	private String phoneNum;
	private Organization organ;
	private String driverLicense;
	private long registrationDate;
	private Date dob;
	private Gender gender;
	
	public Driver(){}
		
	public Driver(long id) {
		this.id = id;
	}
		
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Organization getOrgan() {
		return organ;
	}

	public void setOrgan(Organization organ) {
		this.organ = organ;
	}

	public String getDriverLicense() {
		return driverLicense;
	}

	public void setDriverLicense(String driverLicense) {
		this.driverLicense = driverLicense;
	}

	public long getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(long registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public int getAge(){
		return getYearDiff(new Date(), dob);
	}
	
	public void setGender(Gender gender){
		this.gender = gender;
	}
	
	public Gender getGender(){
		return gender;
	}

	
	/*@Override
	public String toString(){
		return String.format("Driver: name=%s; phone=%s;  license=%s; ID=%s; birthday=%s"
				, name, phoneNum, driverLicense, id, getDateStr(dob, "yyyy-MM-dd"));
	}
	*/
	
	@Override
	public String toString(){
		return JsonUtils.toString(this);
	}


}
