package com.di.poc.models;

import com.di.poc.context.Autowired;

import java.util.Objects;

public class MultipleParameterizedConstructorsAutowiredBean {

	private final com.di.poc.models.ABean ABean;
	private final BBean BBean;
	private final UnRegisteredBean unRegisteredBean;

	@Autowired
	public MultipleParameterizedConstructorsAutowiredBean(ABean ABean, BBean BBean) {
		this.ABean = ABean;
		this.BBean = BBean;
		this.unRegisteredBean = null;
	}

	@Autowired
	public MultipleParameterizedConstructorsAutowiredBean(ABean ABean, UnRegisteredBean unRegisteredBean) {
		this.ABean = ABean;
		this.unRegisteredBean = unRegisteredBean;
		this.BBean = null;
	}

	public ABean getABean() {
		return ABean;
	}

	public BBean getBBean() {
		return BBean;
	}

	public UnRegisteredBean getUnRegisteredBean() {
		return unRegisteredBean;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MultipleParameterizedConstructorsAutowiredBean that = (MultipleParameterizedConstructorsAutowiredBean) o;
		return ABean.equals(that.ABean) &&
				BBean.equals(that.BBean) &&
				unRegisteredBean.equals(that.unRegisteredBean);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ABean, BBean, unRegisteredBean);
	}
}
