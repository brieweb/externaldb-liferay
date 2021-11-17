package com.liferay.example.servicebuilder.extdb.event;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.PortalUtil;

import com.liferay.example.servicebuilder.extdb.service.UserLoginLocalService;
import com.liferay.example.servicebuilder.extdb.service.UserLoginLocalServiceUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author Brian E. Lavender
 */
@Component(
		  immediate = true, property = {"key=login.events.post"},
		  service = LifecycleAction.class
		)
public class SbExtdbPostlogin implements LifecycleAction {

	@Override
	public void processLifecycleEvent(LifecycleEvent lifecycleEvent) throws ActionException {
		   // okay, we need the user login for the event
	    User user = null;
	    _log.info("Dude where is my car?");

	    try {
	      user = PortalUtil.getUser(lifecycleEvent.getRequest());
	    } catch (PortalException e) {
	      _log.error("Error accessing login user: " + e.getMessage(), e);
	    }

	    if (user == null) {
	    	_log.warn("Could not find the logged in user, nothing to track.");

	      return;
	    }

	    // we have the user, let's invoke the service
	    //_userLoginLocalService.updateUserLogin(user.getUserId(), new Date());

	    // alternatively we could just use the local service util:
	    UserLoginLocalServiceUtil.updateUserLogin(user.getUserId(), new Date());
		
	}
	


	  // use the OSGi service tracker to get an instance of the service when available.
//	  @Reference
//	  UserLoginLocalService _userLoginLocalService;
	  
	  private static final Logger _log = LoggerFactory.getLogger(SbExtdbPostlogin.class);

}