package com.example.demo;

import java.beans.Introspector;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

@SpringBootApplication
public class DemoApplication {
	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {

		ApplicationContext ctx = new SpringApplicationBuilder(DemoApplication.class)
				.beanNameGenerator(new PackageBeanNameGenerator()).run(args);
		log.info("Beans provided by Spring Boot:");
		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			log.info(beanName);
		}
	}

	public static class PackageBeanNameGenerator extends AnnotationBeanNameGenerator {
		protected String buildDefaultBeanName(BeanDefinition definition) {
			String beanClassName = definition.getBeanClassName();
			Assert.state(beanClassName != null, "No bean class name set");
			String shortClassName = ClassUtils.getShortName(beanClassName);
			String packageName = ClassUtils.getPackageName(beanClassName);
			if (isApplicationBean(definition)) {
				return packageName + "." + Introspector.decapitalize(shortClassName);
			} else {
				return Introspector.decapitalize(shortClassName);
			}
		}

		private boolean isApplicationBean(BeanDefinition definition) {
			String beanPackageName = ClassUtils.getPackageName(definition.getBeanClassName());
			String applicationPackageName = ClassUtils.getPackageName(DemoApplication.class);
			return beanPackageName.startsWith(applicationPackageName);
		}
	}
}
