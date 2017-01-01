package com.pump.data.branch;

public class MissingBeanException extends BranchException {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	Branch branch;
	Object beanId;
	
	@SuppressWarnings("rawtypes")
	public MissingBeanException(Branch branch, Object beanId) {
		this.branch = branch;
		this.beanId = beanId;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Branch getBranch() {
		return branch;
	}

	@Override
	public Object getBeanId() {
		return beanId;
	}
}