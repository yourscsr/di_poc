package com.di.poc.models;

import com.di.poc.context.Autowired;

import java.util.Objects;

public class ParameterizedConstructorAutowiredBean {

	private final com.di.poc.models.ABean ABean;
	private final BBean BBean;

	@Autowired
	public ParameterizedConstructorAutowiredBean(ABean ABean, BBean BBean) {
		this.ABean = ABean;
		this.BBean = BBean;
	}

	public ABean getABean() {
		return ABean;
	}

	public BBean getBBean() {
		return BBean;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ParameterizedConstructorAutowiredBean that = (ParameterizedConstructorAutowiredBean) o;
		return ABean.equals(that.ABean) &&
				BBean.equals(that.BBean);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ABean, BBean);
	}
}
