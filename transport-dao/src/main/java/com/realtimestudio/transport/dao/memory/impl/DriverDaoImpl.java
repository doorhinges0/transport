package com.realtimestudio.transport.dao.memory.impl;

import static com.realtimestudio.transport.utils.StringUtils.*;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.realtimestudio.transport.dao.baisha.DriverDao;
import com.realtimestudio.transport.dao.baisha.OrganizationDao;
import com.realtimestudio.transport.model.baisha.Driver;
import com.realtimestudio.transport.model.baisha.Gender;
import com.realtimestudio.transport.utils.DateUtils;

public class DriverDaoImpl extends CommonDaoImpl<Driver> implements DriverDao {
	
	private OrganizationDao organizationDao;
	

	public DriverDaoImpl(String inputFilePath, OrganizationDao organizationDao) {
		super(inputFilePath);
		this.organizationDao = organizationDao;
		parse();
	}

	@Override
	protected Driver parseLine(String[] parts) {
		Driver driver = new Driver();
		int i = 0;
		driver.setId(Long.parseLong(parts[i++]));
		driver.setName(parts[i++]);
		i++; //skip status
		driver.setAddress(parts[i++]);
		driver.setPhoneNum(parts[i++]);
		i++; //skip updated_time
		driver.setOrgan(organizationDao.findById(parseLongOrDefault(parts[i++], -1)));
		driver.setDriverLicense(parts[i++]);
		i++; //skip identity_card
		driver.setRegistrationDate(parseLongOrDefault(parts[i++], 0));
		i++; //skip is_gaoshi_leader
		i++; //skip zy_member_type
		i++; //skip zy_car_status;
		i++; //skip zy_check_time
		i++; //skip stop_time
		i++; //skip check_time
		String dob = parts[i++];
		driver.setDob(DateUtils.getDateOrNull(dob, "yyyyMMdd"));
		driver.setGender(Gender.getGender(parseIntOrDefault(parts[i++], 1))); //default is male
		return driver;
	}

	@Override
	protected long getId(Driver driver) {
		return driver.getId();
	}

	@Override
	protected void setId(Driver driver, long id) {
		driver.setId(id);
		
	}

}
