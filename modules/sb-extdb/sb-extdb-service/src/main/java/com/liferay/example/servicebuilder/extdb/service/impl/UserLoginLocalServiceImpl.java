package com.liferay.example.servicebuilder.extdb.service.impl;

import org.osgi.service.component.annotations.Component;

import com.liferay.example.servicebuilder.extdb.model.UserLogin;
import com.liferay.example.servicebuilder.extdb.service.base.UserLoginLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import java.util.Date;

/**
 * The implementation of the user login local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.example.servicebuilder.extdb.service.UserLoginLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserLoginLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.example.servicebuilder.extdb.model.UserLogin",
	service = AopService.class
)
public class UserLoginLocalServiceImpl extends UserLoginLocalServiceBaseImpl {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>com.liferay.example.servicebuilder.extdb.service.UserLoginLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.example.servicebuilder.extdb.service.UserLoginLocalServiceUtil</code>.
	 */
         private static final Log logger = LogFactoryUtil.getLog(UserLoginLocalServiceImpl.class);

	/**
  * updateUserLogin: Updates the user login record with the given info.
  * @param userId User who logged in.
  * @param loginDate Date when the user logged in.
  */
  public void updateUserLogin(final long userId, final Date loginDate) {
    UserLogin login;

    // first try to get the existing record for the user
    login = fetchUserLogin(userId);

    if (login == null) {
      // user has never logged in before, need a new record
      if (logger.isDebugEnabled()) logger.debug("User " + userId + " has never logged in before.");

      // create a new record
      login = createUserLogin(userId);

      // update the login date
      login.setLastLogin(loginDate);

      // initialize the values
      login.setTotalLogins(1);
      login.setShortestTimeBetweenLogins(Long.MAX_VALUE);
      login.setLongestTimeBetweenLogins(0);

      // add the login
      addUserLogin(login);
    } else {
      // user has logged in before, just need to update record.

      // increment the logins count
      login.setTotalLogins(login.getTotalLogins() + 1);

      // determine the duration time between the current and last login
      long duration = loginDate.getTime() - login.getLastLogin().getTime();

      // if this duration is longer than last, update the longest duration.
      if (duration > login.getLongestTimeBetweenLogins()) {
        login.setLongestTimeBetweenLogins(duration);
      }

      // if this duration is shorter than last, update the shortest duration.
      if (duration < login.getShortestTimeBetweenLogins()) {
        login.setShortestTimeBetweenLogins(duration);
      }

      // update the last login timestamp
      login.setLastLogin(loginDate);

      // update the record
      updateUserLogin(login);
    }
  }
}
