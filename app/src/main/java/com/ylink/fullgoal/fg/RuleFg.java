package com.ylink.fullgoal.fg;

public class RuleFg {

	private String ruleName;//规则名称
	private String ruleRemark;//规则信息
	private String triLevel;//警告级别

	public RuleFg() {
	}

	public RuleFg(String triLevel, String ruleRemark) {
		this.ruleRemark = ruleRemark;
		this.triLevel = triLevel;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleRemark() {
		return ruleRemark;
	}

	public void setRuleRemark(String ruleRemark) {
		this.ruleRemark = ruleRemark;
	}

	public String getTriLevel() {
		return triLevel;
	}

	public void setTriLevel(String triLevel) {
		this.triLevel = triLevel;
	}

}