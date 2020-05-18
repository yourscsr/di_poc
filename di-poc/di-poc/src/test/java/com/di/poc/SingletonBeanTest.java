package com.di.poc;

import com.di.poc.context.ApplicationContext;
import com.di.poc.models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SingletonBeanTest {

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
	public void testSingletonBeanCreationWithDefaultConstructor() {
		ac.registerSingletonBean(DefaultConstructorBean.class);
		ac.initialize();

		DefaultConstructorBean bean1 = ac.getBean(DefaultConstructorBean.class);
		DefaultConstructorBean bean2 = ac.getBean(DefaultConstructorBean.class);
		assertNotNull(bean1);
		assertNotNull(bean2);

		assertSame(bean1, bean2);
		assertEquals(bean1, bean2);
	}

	@Test
	public void testSingletonBeanCreationWithParameterizedConstructor() {
		ac.registerSingletonBean(ParameterizedConstructorBean.class, new ABean(), new BBean());
		ac.initialize();

		ParameterizedConstructorBean bean1 = ac.getBean(ParameterizedConstructorBean.class);
		ParameterizedConstructorBean bean2 = ac.getBean(ParameterizedConstructorBean.class);
		assertNotNull(bean1);
		assertNotNull(bean2);

		assertSame(bean1, bean2);
		assertEquals(bean1, bean2);
		assertSame(bean1.getABean(), bean2.getABean());
		assertEquals(bean1.getABean(), bean2.getABean());
		assertSame(bean1.getBBean(), bean2.getBBean());
		assertEquals(bean1.getBBean(), bean2.getBBean());
	}

	@Test
	public void testSingletonBeanCreationWithParameterizedConstructorWithNullParameter() {
		ac.registerSingletonBean(ParameterizedConstructorBean.class, new ABean(), null);
		ac.initialize();

		ParameterizedConstructorBean bean1 = ac.getBean(ParameterizedConstructorBean.class);
		ParameterizedConstructorBean bean2 = ac.getBean(ParameterizedConstructorBean.class);
		assertNotNull(bean1);
		assertNotNull(bean2);

		assertSame(bean1, bean2);
		assertEquals(bean1, bean2);
		assertSame(bean1.getABean(), bean2.getABean());
		assertEquals(bean1.getABean(), bean2.getABean());
		assertSame(bean1.getBBean(), bean2.getBBean());
		assertEquals(bean1.getBBean(), bean2.getBBean());
	}

	@Test
	public void testSingletonBeanCreationWithParameterizedConstructorAutowired() {
		ac.registerSingletonBean(ParameterizedConstructorAutowiredBean.class);
		ac.registerSingletonBean(ABean.class);
		ac.registerSingletonBean(BBean.class);
		ac.initialize();

		ParameterizedConstructorAutowiredBean bean1 = ac.getBean(ParameterizedConstructorAutowiredBean.class);
		ParameterizedConstructorAutowiredBean bean2 = ac.getBean(ParameterizedConstructorAutowiredBean.class);
		assertNotNull(bean1);
		assertNotNull(bean2);

		assertSame(bean1, bean2);
		assertEquals(bean1, bean2);
		assertSame(bean1.getABean(), bean2.getABean());
		assertEquals(bean1.getABean(), bean2.getABean());
		assertSame(bean1.getBBean(), bean2.getBBean());
		assertEquals(bean1.getBBean(), bean2.getBBean());
	}

	@Test(expected = RuntimeException.class)
	public void testSingletonBeanCreationWithParameterizedConstructorAutowiredWithMissingBBean() {
		ac.registerSingletonBean(ParameterizedConstructorAutowiredBean.class);
		ac.registerSingletonBean(ABean.class);
		ac.initialize();
	}

	@Test(expected = RuntimeException.class)
	public void testSingletonBeanCreationWithUnregisteredBean() {
		ac.registerSingletonBean(ABean.class);
		ac.initialize();
		ac.getBean(UnRegisteredBean.class);
	}

	@Test(expected = RuntimeException.class)
	public void testSingletonBeanCreationWithMultipleParameterizedConstructorsAutowired() {
		ac.registerSingletonBean(MultipleParameterizedConstructorsAutowiredBean.class);
		ac.registerSingletonBean(ABean.class);
		ac.registerSingletonBean(BBean.class);
		ac.registerSingletonBean(UnRegisteredBean.class);
		ac.initialize();
	}
}