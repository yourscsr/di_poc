package com.di.poc.context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {

	private final Map<Class<?>, Object[]> prototypeClassHolder = Collections.synchronizedMap(new HashMap<>());
	private final Map<Class<?>, Object[]> singletonClassHolder = Collections.synchronizedMap(new HashMap<>());
	private final Map<Class<?>, Object> singletonInstanceHolder = Collections.synchronizedMap(new HashMap<>());

	private static final ApplicationContext applicationContext = new ApplicationContext();

	public static ApplicationContext getInstance() {
		return applicationContext;
	}

	public <T> void registerSingletonBean(Class<T> instanceType) {
		singletonClassHolder.put(instanceType, null);
	}

	public <T> void registerSingletonBean(Class<T> instanceType, Object... arguments) {
		singletonClassHolder.put(instanceType, arguments);
	}

	public <T> void registerPrototypeBean(Class<T> instanceType) {
		prototypeClassHolder.put(instanceType, null);
	}

	public <T> void registerPrototypeBean(Class<T> instanceType, Object... arguments) {
		prototypeClassHolder.put(instanceType, arguments);
	}

	public void initialize() {
		for (Map.Entry<Class<?>, Object[]> entry : singletonClassHolder.entrySet()) {
			Class<?> type = entry.getKey();
			try {
				singletonInstanceHolder.put(type, resolveInstance(type, singletonClassHolder, singletonInstanceHolder));
			} catch (Exception e) {
				throw new RuntimeException("Failed to create singleton instance of a class with type: " + type, e);
			}
		}
	}

	private Object resolveInstance(Class<?> type,
								   Map<Class<?>, Object[]> classHolder,
								   Map<Class<?>, Object> instanceHolder) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		if (instanceHolder.get(type) != null) {
			return instanceHolder.get(type);
		}
		validate(type);
		Object newInstance = null;
		Constructor<?>[] constructors = type.getConstructors();

		for (Constructor<?> constructor : constructors) {
			int constructorParameterCount = constructor.getParameterCount();
			if (constructorParameterCount > 0
					&& classHolder.get(type) != null
					&& constructorParameterCount == classHolder.get(type).length) {
				Class<?>[] constructorParameterTypes = constructor.getParameterTypes();
				Object[] passedConstructorArguments = classHolder.get(type);
				boolean isAssignable = true;
				for (int i = 0; i < constructorParameterCount; i++) {
					if (passedConstructorArguments[i] != null && !constructorParameterTypes[i].isAssignableFrom(passedConstructorArguments[i].getClass())) {
						isAssignable = false;
					}
				}
				if (isAssignable) {
					newInstance = constructor.newInstance(classHolder.get(type));
				}
			}
		}
		if (instanceHolder.get(type) == null && type.getConstructors().length == 1) {
			Constructor<?> constructor = type.getConstructors()[0];
			Annotation[] annotations = constructor.getAnnotations();
			int constructorParameterCount = constructor.getParameterCount();
			Object[] arguments = new Object[constructorParameterCount];
			if (Arrays.stream(annotations).anyMatch(annotation -> annotation.annotationType().equals(Autowired.class)) && constructorParameterCount > 0) {
				Class<?>[] parameterTypes = constructor.getParameterTypes();
				for (int i = 0; i < constructorParameterCount; i++) {
					Class<?> argType = parameterTypes[i];
					Object instance = instanceHolder.get(argType);
					if (instance == null) {
						instance = resolveInstance(argType, classHolder, instanceHolder);
					}
					arguments[i] = instance;
				}
				newInstance = constructor.newInstance(arguments);
			}

		}
		if (instanceHolder.get(type) == null
				&& classHolder.containsKey(type)
				&& constructors.length == 1 && constructors[0].getParameterCount() == 0) {
			newInstance = type.newInstance();
		}
		if (newInstance == null) {
			throw new RuntimeException("Unable to resolve bean with type: " + type);
		} else {
			instanceHolder.put(type, newInstance);
		}
		return newInstance;
	}

	public void validate(Class<?> type) {
		Constructor<?>[] constructors = type.getConstructors();
		int autowiredConstructorsCount = 0;
		for (Constructor<?> constructor : constructors) {
			Annotation[] annotations = constructor.getAnnotations();
			if (Arrays.stream(annotations).anyMatch(annotation -> annotation.annotationType().equals(Autowired.class))) {
				autowiredConstructorsCount++;
			}
		}
		if (autowiredConstructorsCount > 1) {
			throw new RuntimeException("Ambiguity! Autowired mutiple constructors for the bean type: " + type);
		}
	}

	public <T> T getBean(Class<T> type) {
		if (singletonInstanceHolder.containsKey(type)) {
			return (T) singletonInstanceHolder.get(type);
		}
		//Need to extend for other scopes like request/session/global for web based application context.
		//For all other cases return the new instance on every request of #getBean(Class).
		if (prototypeClassHolder.containsKey(type)) {
			try {
				return (T) resolveInstance(type, prototypeClassHolder, new HashMap<>());
			} catch (Exception e) {
				throw new RuntimeException("Failed to create instance of a class with type: " + type, e);
			}
		}
		throw new RuntimeException("Unsupported bean type! Bean is not registered in the ApplicationContext!");
	}

	/*Only used for test cases*/
	public void destory() {
		prototypeClassHolder.clear();
		singletonClassHolder.clear();
		singletonInstanceHolder.clear();
	}
}
