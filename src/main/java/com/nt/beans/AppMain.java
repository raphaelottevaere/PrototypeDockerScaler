package com.nt.beans;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class AppMain {

	// This Method Is Used To Load The Spring Bean Configuration File And Return The 'BeanFactory' Object
	public static void main(String[] args){
	    ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"Scaler.xml"});
	    BeanFactory factory=context;
	    Scaler scalerBean=(Scaler)factory.getBean("ScalerBean");
	    scalerBean.init();
	}
}