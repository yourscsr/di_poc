package com.di.poc.models;

import java.util.Objects;

public class ABean {

	private final String name = ABean.class.getName();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ABean ABean = (ABean) o;
		return name.equals(ABean.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
