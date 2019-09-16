package cn.itheima.commons.spring;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class SpringContext extends ApplicationObjectSupport {
	private static ApplicationContext applicationContext;

	public void initApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
	}

	public static ApplicationContext getContext() {
		checkApplicationContext();
		return applicationContext;
	}

	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> clazz) {
		checkApplicationContext();
		LinkedHashMap<String, T> map = (LinkedHashMap) applicationContext.getBeansOfType(clazz);
		if (MapUtils.isNotEmpty(map)) {
			return (T) ((Map.Entry) map.entrySet().iterator().next()).getValue();
		}
		return null;
	}

	public static Resource getResource(String location) {
		checkApplicationContext();
		Resource res = applicationContext.getResource(location);
		return res;
	}

	public static Properties getProperties(String location) {

		try {
			Resource res = getResource(location);
			return PropertiesLoaderUtils.loadProperties(new EncodedResource(res, "UTF-8"));
		} catch (IOException localIOException) {
		}
		return null;
	}

	public static void publishEvent(ApplicationEvent event) {
		checkApplicationContext();
		applicationContext.publishEvent(event);
	}

	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException(
					"applicaitonContext not found, please config SpringContext bean in the applicationContext.xml");
		}
	}
}
