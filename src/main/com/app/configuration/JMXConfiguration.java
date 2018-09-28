package com.app.configuration;


import com.app.configuration.component.Log4jConfiguratorMXBean;
import com.zaxxer.hikari.HikariPoolMXBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.support.RegistrationPolicy;


import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class JMXConfiguration {
    @Autowired
    private Log4jConfiguratorMXBean log4jConfiguratorMXBean;

    @Bean(name = "mbeanServer")
    public MBeanServer mbeanServer() {
        return ManagementFactory.getPlatformMBeanServer();
    }

    @Bean(name = "mbeanExporter")
    public MBeanExporter mbeanExporter() {
        MBeanExporter mbeanExporter = new MBeanExporter();
        mbeanExporter.setServer(mbeanServer());
        mbeanExporter.setBeans(createBeansMap());
        mbeanExporter.setRegistrationPolicy(RegistrationPolicy.REPLACE_EXISTING);
        return mbeanExporter;
    }

    private Map<String, Object> createBeansMap() {
        Map<String, Object> beansMap = new HashMap<>();
        beansMap.put("com.application:type=log4j_manager", log4jConfiguratorMXBean);
        return beansMap;
    }

    @Bean
    public HikariPoolMXBean hikariPoolMXBean() throws MalformedObjectNameException {
        ObjectName poolName = new ObjectName("com.zaxxer.hikari:type=Pool (application_pool)");
        return JMX.newMXBeanProxy(mbeanServer(), poolName, HikariPoolMXBean.class);
    }
}