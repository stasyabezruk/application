package com.app.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class SiteProperties {
	private static final String TRUE = "TRUE";
	private static final String DEFAULT_CONFIG = "site.properties";
	private static final Log LOGGER = LogFactory.getLog(SiteProperties.class);
	private static final Map<String, SiteProperties> INSTANCES = new HashMap<>();
	private final Properties config = new Properties();

	private SiteProperties(String config) {
		try (InputStream is = SiteProperties.class.getClassLoader().getResourceAsStream(config)) {
			this.config.load(is);
		} catch (IOException e) {
			LOGGER.error("Error loading config for SiteProperties(" + config + ")", e);
		}
	}

	public static SiteProperties getInstance() {
		return getInstance(DEFAULT_CONFIG);
	}

	public static SiteProperties getInstance(String config) {
		SiteProperties properties = INSTANCES.get(config);

		if (properties == null)
			properties = new SiteProperties(config);
		INSTANCES.put(config, properties);

		return properties;
	}

	public String get(String property) {
		return config.getProperty(property);
	}

	public String get(String property, String defaultValue) {
		return config.getProperty(property, defaultValue);
	}

	/*public String get(String property, Map<String, String> variables) {
		String value = get(property);
		return StrSubstitutor.replace(value, variables, VARIABLE_PREFIX, VARIABLE_SUFFIX);
	}

	public String get(String property, String defaultValue, Map<String, String> variables) {
		String value = get(property, defaultValue);
		return StrSubstitutor.replace(value, variables, VARIABLE_PREFIX, VARIABLE_SUFFIX);
	}

	public int getInt(String property) {
		return MonkeyNumberUtils.toInt(get(property));
	}

	public int getInt(String property, Integer defaultValue) {
		return MonkeyNumberUtils.toInt(get(property, defaultValue.toString()));
	}

	public long getLong(String property) {
		return MonkeyNumberUtils.toLong(get(property));
	}

	public double getDouble(String property) {
		return MonkeyNumberUtils.toDouble(get(property));
	}*/

	public boolean isTrue(String property) {
		return TRUE.equalsIgnoreCase(get(property));
	}

	public boolean has(String property) {
		return get(property) != null;
	}
}
