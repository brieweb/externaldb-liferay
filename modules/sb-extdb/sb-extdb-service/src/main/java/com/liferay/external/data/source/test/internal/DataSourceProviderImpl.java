package com.liferay.external.data.source.test.internal;

import javax.sql.DataSource;

import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataSourceProvider;
import com.liferay.portal.kernel.util.PropsUtil;

public class DataSourceProviderImpl implements DataSourceProvider {

	@Override
	public DataSource getDataSource() {
		try {
			return DataSourceFactoryUtil.initDataSource(
				PropsUtil.getProperties("jdbc.ext.", true));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
