package com.di.poc.models;

import java.util.Objects;

public class DefaultConstructorBean {

	private final String name = DefaultConstructorBean.class.getName();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DefaultConstructorBean that = (DefaultConstructorBean) o;
		return name.equals(that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
