package com.app.common.jpa;

import com.app.common.util.SiteProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class EntityFactory {

	private static final String DEFAULT_CONFIG = "entity.properties";
	private static EntityFactory instance;
	private static final Log logger = LogFactory.getLog(EntityFactory.class);
	private final Properties config = new Properties();

	private EntityFactory() {
		try (InputStream is = SiteProperties.class.getClassLoader().getResourceAsStream(DEFAULT_CONFIG)) {
			config.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static EntityFactory getInstance() {
		if (instance == null)
			instance = new EntityFactory();

		return instance;
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getImplementation(Class<T> interfaze) {
		try {
			return (Class<T>) Class.forName(getInstance().config.getProperty(interfaze.getName()));
		} catch (ClassNotFoundException e) {
			logger.error("Failed to get implementation for interface " + interfaze, e);
		}

		return null;
	}

	public static <T> T newInstance(Class<T> interfaze) {
		try {
			return getImplementation(interfaze).newInstance();
		} catch (InstantiationException e) {
			logger.error("Failed to instantiate implementation for interface " + interfaze, e);
		} catch (IllegalAccessException e) {
			logger.error("Failed to instantiate implementation for interface " + interfaze, e);
		}

		return null;
	}

}
