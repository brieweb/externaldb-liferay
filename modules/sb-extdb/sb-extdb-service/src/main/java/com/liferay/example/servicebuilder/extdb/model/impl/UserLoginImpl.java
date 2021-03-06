/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.example.servicebuilder.extdb.model.impl;

import org.osgi.service.component.annotations.Reference;



import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import com.liferay.portal.kernel.service.UserLocalServiceUtil;

/**
 * The extended model implementation for the UserLogin service. Represents a row in the &quot;UserLogin&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.example.servicebuilder.extdb.model.UserLogin</code> interface.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class UserLoginImpl extends UserLoginBaseImpl {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a user login model instance should use the {@link com.liferay.example.servicebuilder.extdb.model.UserLogin} interface instead.
	 */
	public UserLoginImpl() {
	}
	
	public String getFirstName() {
		String firstName = "Error Name";
		try {
			User theUser = UserLocalServiceUtil.getUser(this.getUserId());

			firstName = theUser.getFirstName();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			
		}
		return firstName;
	}
	
	public String getLastName() {
		String firstName = "Error Name";
		try {
			User theUser = UserLocalServiceUtil.getUser(this.getUserId());
			firstName = theUser.getLastName();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			
		}
		return firstName;
	}
	
	public String getLogin() {
		String firstName = "Error Name";
		try {
			User theUser = UserLocalServiceUtil.getUser(this.getUserId());
			firstName = theUser.getLogin();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			
		}
		return firstName;
	}
	


	
	//private static final Logger _log = LoggerFactory.getLogger(UserLoginImpls.class);

}
