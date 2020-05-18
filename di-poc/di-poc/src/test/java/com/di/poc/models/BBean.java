package com.di.poc.models;

import java.util.Objects;

public class BBean {

	private final String name = BBean.class.getName();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BBean BBean = (BBean) o;
		return name.equals(BBean.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
