package com.app.configuration.component;

import java.util.List;


public interface Log4jConfiguratorMXBean
{

    List<String> getLoggers();


    String getLogLevel(String logger);


    void setLogLevel(String logger, String level);

}