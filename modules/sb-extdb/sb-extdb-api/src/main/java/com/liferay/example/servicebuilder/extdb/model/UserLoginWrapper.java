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

package com.liferay.example.servicebuilder.extdb.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link UserLogin}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserLogin
 * @generated
 */
public class UserLoginWrapper
	extends BaseModelWrapper<UserLogin>
	implements ModelWrapper<UserLogin>, UserLogin {

	public UserLoginWrapper(UserLogin userLogin) {
		super(userLogin);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("userId", getUserId());
		attributes.put("lastLogin", getLastLogin());
		attributes.put("totalLogins", getTotalLogins());
		attributes.put(
			"longestTimeBetweenLogins", getLongestTimeBetweenLogins());
		attributes.put(
			"shortestTimeBetweenLogins", getShortestTimeBetweenLogins());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date lastLogin = (Date)attributes.get("lastLogin");

		if (lastLogin != null) {
			setLastLogin(lastLogin);
		}

		Long totalLogins = (Long)attributes.get("totalLogins");

		if (totalLogins != null) {
			setTotalLogins(totalLogins);
		}

		Long longestTimeBetweenLogins = (Long)attributes.get(
			"longestTimeBetweenLogins");

		if (longestTimeBetweenLogins != null) {
			setLongestTimeBetweenLogins(longestTimeBetweenLogins);
		}

		Long shortestTimeBetweenLogins = (Long)attributes.get(
			"shortestTimeBetweenLogins");

		if (shortestTimeBetweenLogins != null) {
			setShortestTimeBetweenLogins(shortestTimeBetweenLogins);
		}
	}

	/**
	 * Returns the last login of this user login.
	 *
	 * @return the last login of this user login
	 */
	@Override
	public Date getLastLogin() {
		return model.getLastLogin();
	}

	/**
	 * Returns the longest time between logins of this user login.
	 *
	 * @return the longest time between logins of this user login
	 */
	@Override
	public long getLongestTimeBetweenLogins() {
		return model.getLongestTimeBetweenLogins();
	}

	/**
	 * Returns the primary key of this user login.
	 *
	 * @return the primary key of this user login
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the shortest time between logins of this user login.
	 *
	 * @return the shortest time between logins of this user login
	 */
	@Override
	public long getShortestTimeBetweenLogins() {
		return model.getShortestTimeBetweenLogins();
	}

	/**
	 * Returns the total logins of this user login.
	 *
	 * @return the total logins of this user login
	 */
	@Override
	public long getTotalLogins() {
		return model.getTotalLogins();
	}

	/**
	 * Returns the user ID of this user login.
	 *
	 * @return the user ID of this user login
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this user login.
	 *
	 * @return the user uuid of this user login
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the last login of this user login.
	 *
	 * @param lastLogin the last login of this user login
	 */
	@Override
	public void setLastLogin(Date lastLogin) {
		model.setLastLogin(lastLogin);
	}

	/**
	 * Sets the longest time between logins of this user login.
	 *
	 * @param longestTimeBetweenLogins the longest time between logins of this user login
	 */
	@Override
	public void setLongestTimeBetweenLogins(long longestTimeBetweenLogins) {
		model.setLongestTimeBetweenLogins(longestTimeBetweenLogins);
	}

	/**
	 * Sets the primary key of this user login.
	 *
	 * @param primaryKey the primary key of this user login
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the shortest time between logins of this user login.
	 *
	 * @param shortestTimeBetweenLogins the shortest time between logins of this user login
	 */
	@Override
	public void setShortestTimeBetweenLogins(long shortestTimeBetweenLogins) {
		model.setShortestTimeBetweenLogins(shortestTimeBetweenLogins);
	}

	/**
	 * Sets the total logins of this user login.
	 *
	 * @param totalLogins the total logins of this user login
	 */
	@Override
	public void setTotalLogins(long totalLogins) {
		model.setTotalLogins(totalLogins);
	}

	/**
	 * Sets the user ID of this user login.
	 *
	 * @param userId the user ID of this user login
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this user login.
	 *
	 * @param userUuid the user uuid of this user login
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected UserLoginWrapper wrap(UserLogin userLogin) {
		return new UserLoginWrapper(userLogin);
	}

}