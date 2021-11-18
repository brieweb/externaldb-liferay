<%@ include file="/init.jsp" %>

<p>
	<b><liferay-ui:message key="logger.caption"/></b>
</p>

<p>
	<b><liferay-ui:message key="logincounter.caption"/></b>
	<liferay-ui:search-container total="<%= UserLoginLocalServiceUtil.getUserLoginsCount() %>">


	<liferay-ui:search-container-results
    	results="<%= UserLoginLocalServiceUtil.getUserLogins( searchContainer.getStart(),
    			searchContainer.getEnd()) %>" />
                    
        <liferay-ui:search-container-row
    		className="com.liferay.example.servicebuilder.extdb.model.UserLogin" >
			<liferay-ui:search-container-column-text property="login" name="ID"/>
			<liferay-ui:search-container-column-text property="firstName" name="First Name"/>
			<liferay-ui:search-container-column-text property="lastName" name="Last Name"/>
		    <liferay-ui:search-container-column-text property="totalLogins" name="total Logins"/>
		    <liferay-ui:search-container-column-date property="lastLogin" name="Last Login"/>

	</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</p>