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

package com.liferay.example.servicebuilder.extdb.service.persistence.impl;

import com.liferay.example.servicebuilder.extdb.exception.NoSuchUserLoginException;
import com.liferay.example.servicebuilder.extdb.model.UserLogin;
import com.liferay.example.servicebuilder.extdb.model.impl.UserLoginImpl;
import com.liferay.example.servicebuilder.extdb.model.impl.UserLoginModelImpl;
import com.liferay.example.servicebuilder.extdb.service.persistence.UserLoginPersistence;
import com.liferay.example.servicebuilder.extdb.service.persistence.impl.constants.ExtDBPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the user login service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = UserLoginPersistence.class)
public class UserLoginPersistenceImpl
	extends BasePersistenceImpl<UserLogin> implements UserLoginPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>UserLoginUtil</code> to access the user login persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		UserLoginImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public UserLoginPersistenceImpl() {
		setModelClass(UserLogin.class);

		setModelImplClass(UserLoginImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the user login in the entity cache if it is enabled.
	 *
	 * @param userLogin the user login
	 */
	@Override
	public void cacheResult(UserLogin userLogin) {
		entityCache.putResult(
			UserLoginImpl.class, userLogin.getPrimaryKey(), userLogin);
	}

	/**
	 * Caches the user logins in the entity cache if it is enabled.
	 *
	 * @param userLogins the user logins
	 */
	@Override
	public void cacheResult(List<UserLogin> userLogins) {
		for (UserLogin userLogin : userLogins) {
			if (entityCache.getResult(
					UserLoginImpl.class, userLogin.getPrimaryKey()) == null) {

				cacheResult(userLogin);
			}
		}
	}

	/**
	 * Clears the cache for all user logins.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(UserLoginImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the user login.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(UserLogin userLogin) {
		entityCache.removeResult(UserLoginImpl.class, userLogin);
	}

	@Override
	public void clearCache(List<UserLogin> userLogins) {
		for (UserLogin userLogin : userLogins) {
			entityCache.removeResult(UserLoginImpl.class, userLogin);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(UserLoginImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new user login with the primary key. Does not add the user login to the database.
	 *
	 * @param userId the primary key for the new user login
	 * @return the new user login
	 */
	@Override
	public UserLogin create(long userId) {
		UserLogin userLogin = new UserLoginImpl();

		userLogin.setNew(true);
		userLogin.setPrimaryKey(userId);

		return userLogin;
	}

	/**
	 * Removes the user login with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userId the primary key of the user login
	 * @return the user login that was removed
	 * @throws NoSuchUserLoginException if a user login with the primary key could not be found
	 */
	@Override
	public UserLogin remove(long userId) throws NoSuchUserLoginException {
		return remove((Serializable)userId);
	}

	/**
	 * Removes the user login with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the user login
	 * @return the user login that was removed
	 * @throws NoSuchUserLoginException if a user login with the primary key could not be found
	 */
	@Override
	public UserLogin remove(Serializable primaryKey)
		throws NoSuchUserLoginException {

		Session session = null;

		try {
			session = openSession();

			UserLogin userLogin = (UserLogin)session.get(
				UserLoginImpl.class, primaryKey);

			if (userLogin == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserLoginException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(userLogin);
		}
		catch (NoSuchUserLoginException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected UserLogin removeImpl(UserLogin userLogin) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(userLogin)) {
				userLogin = (UserLogin)session.get(
					UserLoginImpl.class, userLogin.getPrimaryKeyObj());
			}

			if (userLogin != null) {
				session.delete(userLogin);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (userLogin != null) {
			clearCache(userLogin);
		}

		return userLogin;
	}

	@Override
	public UserLogin updateImpl(UserLogin userLogin) {
		boolean isNew = userLogin.isNew();

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(userLogin);
			}
			else {
				userLogin = (UserLogin)session.merge(userLogin);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(UserLoginImpl.class, userLogin, false, true);

		if (isNew) {
			userLogin.setNew(false);
		}

		userLogin.resetOriginalValues();

		return userLogin;
	}

	/**
	 * Returns the user login with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user login
	 * @return the user login
	 * @throws NoSuchUserLoginException if a user login with the primary key could not be found
	 */
	@Override
	public UserLogin findByPrimaryKey(Serializable primaryKey)
		throws NoSuchUserLoginException {

		UserLogin userLogin = fetchByPrimaryKey(primaryKey);

		if (userLogin == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchUserLoginException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return userLogin;
	}

	/**
	 * Returns the user login with the primary key or throws a <code>NoSuchUserLoginException</code> if it could not be found.
	 *
	 * @param userId the primary key of the user login
	 * @return the user login
	 * @throws NoSuchUserLoginException if a user login with the primary key could not be found
	 */
	@Override
	public UserLogin findByPrimaryKey(long userId)
		throws NoSuchUserLoginException {

		return findByPrimaryKey((Serializable)userId);
	}

	/**
	 * Returns the user login with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userId the primary key of the user login
	 * @return the user login, or <code>null</code> if a user login with the primary key could not be found
	 */
	@Override
	public UserLogin fetchByPrimaryKey(long userId) {
		return fetchByPrimaryKey((Serializable)userId);
	}

	/**
	 * Returns all the user logins.
	 *
	 * @return the user logins
	 */
	@Override
	public List<UserLogin> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user logins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserLoginModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user logins
	 * @param end the upper bound of the range of user logins (not inclusive)
	 * @return the range of user logins
	 */
	@Override
	public List<UserLogin> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the user logins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserLoginModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user logins
	 * @param end the upper bound of the range of user logins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user logins
	 */
	@Override
	public List<UserLogin> findAll(
		int start, int end, OrderByComparator<UserLogin> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user logins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserLoginModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user logins
	 * @param end the upper bound of the range of user logins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of user logins
	 */
	@Override
	public List<UserLogin> findAll(
		int start, int end, OrderByComparator<UserLogin> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<UserLogin> list = null;

		if (useFinderCache) {
			list = (List<UserLogin>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_USERLOGIN);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_USERLOGIN;

				sql = sql.concat(UserLoginModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<UserLogin>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the user logins from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (UserLogin userLogin : findAll()) {
			remove(userLogin);
		}
	}

	/**
	 * Returns the number of user logins.
	 *
	 * @return the number of user logins
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_USERLOGIN);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "userId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_USERLOGIN;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return UserLoginModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the user login persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class, new UserLoginModelArgumentsResolver(),
			MapUtil.singletonDictionary(
				"model.class.name", UserLogin.class.getName()));

		_finderPathWithPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(UserLoginImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();

		for (ServiceRegistration<FinderPath> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	@Override
	@Reference(
		target = ExtDBPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = ExtDBPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = ExtDBPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private BundleContext _bundleContext;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_USERLOGIN =
		"SELECT userLogin FROM UserLogin userLogin";

	private static final String _SQL_COUNT_USERLOGIN =
		"SELECT COUNT(userLogin) FROM UserLogin userLogin";

	private static final String _ORDER_BY_ENTITY_ALIAS = "userLogin.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No UserLogin exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		UserLoginPersistenceImpl.class);

	private FinderPath _createFinderPath(
		String cacheName, String methodName, String[] params,
		String[] columnNames, boolean baseModelResult) {

		FinderPath finderPath = new FinderPath(
			cacheName, methodName, params, columnNames, baseModelResult);

		if (!cacheName.equals(FINDER_CLASS_NAME_LIST_WITH_PAGINATION)) {
			_serviceRegistrations.add(
				_bundleContext.registerService(
					FinderPath.class, finderPath,
					MapUtil.singletonDictionary("cache.name", cacheName)));
		}

		return finderPath;
	}

	private Set<ServiceRegistration<FinderPath>> _serviceRegistrations =
		new HashSet<>();
	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class UserLoginModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			UserLoginModelImpl userLoginModelImpl =
				(UserLoginModelImpl)baseModel;

			long columnBitmask = userLoginModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(userLoginModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						userLoginModelImpl.getColumnBitmask(columnName);
				}

				if (finderPath.isBaseModelResult() &&
					(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION ==
						finderPath.getCacheName())) {

					finderPathColumnBitmask |= _ORDER_BY_COLUMNS_BITMASK;
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(userLoginModelImpl, columnNames, original);
			}

			return null;
		}

		private static Object[] _getValue(
			UserLoginModelImpl userLoginModelImpl, String[] columnNames,
			boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] = userLoginModelImpl.getColumnOriginalValue(
						columnName);
				}
				else {
					arguments[i] = userLoginModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static final Map<FinderPath, Long>
			_finderPathColumnBitmasksCache = new ConcurrentHashMap<>();

		private static final long _ORDER_BY_COLUMNS_BITMASK;

		static {
			long orderByColumnsBitmask = 0;

			orderByColumnsBitmask |= UserLoginModelImpl.getColumnBitmask(
				"lastLogin");

			_ORDER_BY_COLUMNS_BITMASK = orderByColumnsBitmask;
		}

	}

}