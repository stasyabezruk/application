package com.app.configuration.component.impl;

import com.app.configuration.component.Log4jConfiguratorMXBean;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Component("log4jConfiguratorMXBean")
public class Log4jConfiguratorMXBeanImpl implements Log4jConfiguratorMXBean {

	@Override
	@SuppressWarnings("rawtypes")
	public List<String> getLoggers() {
		List<String> list = new ArrayList<>();

		for (Enumeration e = LogManager.getCurrentLoggers(); e.hasMoreElements();) {
			Logger log = (Logger) e.nextElement();

			if (log.getLevel() != null)
				list.add(log.getName() + " = " + log.getLevel().toString());
		}
		return list;
	}

	@Override
	public String getLogLevel(String logger) {
		String level = "unavailable";

		if (StringUtils.isNotBlank(logger)) {
			Logger log = LogManager.getLogger(logger);

			if (log != null)
				level = log.getLevel().toString();
		}
		return level;
	}

	@Override
	public void setLogLevel(String logger, String level) {
		if (StringUtils.isNotBlank(logger) && StringUtils.isNotBlank(level)) {
			Logger log = Logger.getLogger(logger);

			if (log != null)
				log.setLevel(Level.toLevel(level.toUpperCase()));
		}
	}
}