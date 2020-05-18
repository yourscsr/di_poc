package com.di.poc;

import com.di.poc.context.ApplicationContext;
import com.di.poc.models.ABean;
import com.di.poc.models.BBean;
import com.di.poc.models.ParameterizedConstructorAutowiredBean;
import com.di.poc.models.ParameterizedConstructorBean;

public class Main {
	public static void main(String[] args) {

		ApplicationContext context = new ApplicationContext();
		context.registerSingletonBean(ABean.class);
		context.registerSingletonBean(BBean.class);
		context.registerSingletonBean(ParameterizedConstructorBean.class, new ABean(), new BBean());
		context.registerSingletonBean(ParameterizedConstructorAutowiredBean.class);
		context.initialize();

		ParameterizedConstructorAutowiredBean sb1 = context.getBean(ParameterizedConstructorAutowiredBean.class);
		ParameterizedConstructorAutowiredBean sb2 = context.getBean(ParameterizedConstructorAutowiredBean.class);
		if (sb2 != null && sb1 == sb2
				&& sb1.getABean() != null
				&& sb1.getBBean() != null
				&& sb1.getABean() == sb2.getABean()
				&& sb1.getBBean() == sb2.getBBean()) {
			System.out.println("Singleton SingletonBeanWithAutowire creation successful!");
		} else {
			System.out.println("Singleton bean creation failed!");
		}
		ParameterizedConstructorBean sb3 = context.getBean(ParameterizedConstructorBean.class);
		ParameterizedConstructorBean sb4 = context.getBean(ParameterizedConstructorBean.class);
		if (sb3 != null
				&& sb3 == sb4
				&& sb4.getABean() != null
				&& sb4.getBBean() != null
				&& sb3.getABean() == sb4.getABean()
				&& sb3.getBBean() == sb4.getBBean()) {
			System.out.println("Singleton SingletonBeanWithoutAutowire creation successful!");
		} else {
			System.out.println("Singleton bean creation failed!");
		}

	}
}
