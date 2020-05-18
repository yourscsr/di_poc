package com.di.poc;

import com.di.poc.context.ApplicationContext;
import com.di.poc.models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrototypeBeanTest {

	ApplicationContext ac;

	@Before
	public void setup() {
		ac = ApplicationContext.getInstance();
	}

	@After
	public void tearDown() {
		ac.destory();
	}

	@Test
	public void testPrototypeBeanCreationWithDefaultConstructor() {
		ac.registerPrototypeBean(DefaultConstructorBean.class);
		ac.initialize();

		DefaultConstructorBean bean1 = ac.getBean(DefaultConstructorBean.class);
		DefaultConstructorBean bean2 = ac.getBean(DefaultConstructorBean.class);
		assertNotNull(bean1);
		assertNotNull(bean2);

		assertNotSame(bean1, bean2);
		assertEquals(bean1, bean2);
	}

	@Test
	public void testPrototypeBeanCreationWithParameterizedConstructor() {
		ac.registerPrototypeBean(ParameterizedConstructorBean.class, new ABean(), new BBean());
		ac.initialize();

		ParameterizedConstructorBean bean1 = ac.getBean(ParameterizedConstructorBean.class);
		ParameterizedConstructorBean bean2 = ac.getBean(ParameterizedConstructorBean.class);
		assertNotNull(bean1);
		assertNotNull(bean2);

		assertNotSame(bean1, bean2);
		assertEquals(bean1, bean2);
		assertSame(bean1.getABean(), bean2.getABean());
		assertEquals(bean1.getABean(), bean2.getABean());
		assertSame(bean1.getBBean(), bean2.getBBean());
		assertEquals(bean1.getBBean(), bean2.getBBean());
	}

	@Test
	public void testPrototypeBeanCreationWithParameterizedConstructorAutowired() {
		ac.registerPrototypeBean(ParameterizedConstructorAutowiredBean.class);
		ac.registerPrototypeBean(ABean.class);
		ac.registerPrototypeBean(BBean.class);
		ac.initialize();

		ParameterizedConstructorAutowiredBean bean1 = ac.getBean(ParameterizedConstructorAutowiredBean.class);
		ParameterizedConstructorAutowiredBean bean2 = ac.getBean(ParameterizedConstructorAutowiredBean.class);
		assertNotNull(bean1);
		assertNotNull(bean2);

		assertNotSame(bean1, bean2);
		assertEquals(bean1, bean2);
		assertNotSame(bean1.getABean(), bean2.getABean());
		assertEquals(bean1.getABean(), bean2.getABean());
		assertNotSame(bean1.getBBean(), bean2.getBBean());
		assertEquals(bean1.getBBean(), bean2.getBBean());
	}

	@Test(expected = RuntimeException.class)
	public void testPrototypeBeanCreationWithParameterizedConstructorAutowiredWithMissingABean() {
		ac.registerPrototypeBean(ParameterizedConstructorAutowiredBean.class);
		ac.registerPrototypeBean(BBean.class);
		ac.initialize();

		ac.getBean(ParameterizedConstructorAutowiredBean.class);
	}
}