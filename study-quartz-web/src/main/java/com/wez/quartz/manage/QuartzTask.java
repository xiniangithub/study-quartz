package com.wez.quartz.manage;

import java.io.Serializable;

/**
 * Quartz任务信息
 * @author Admin
 *
 */
public class QuartzTask implements Serializable {

	private static final long serialVersionUID = -4897365471416219518L;

	private String jobName;

	private String jobGroupName;

	private String triggerName;

	private String triggerGroupName;

	private String triggerState;

	public QuartzTask() {}

	public QuartzTask(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
		this(jobName, jobGroupName, triggerName, triggerGroupName, null);
	}

	public QuartzTask(String jobName, String jobGroupName, String triggerName, String triggerGroupName, String triggerState) {
		super();
		this.jobName = jobName;
		this.jobGroupName = jobGroupName;
		this.triggerName = triggerName;
		this.triggerGroupName = triggerGroupName;
		this.triggerState = triggerState;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroupName() {
		return jobGroupName;
	}

	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerGroupName() {
		return triggerGroupName;
	}

	public void setTriggerGroupName(String triggerGroupName) {
		this.triggerGroupName = triggerGroupName;
	}

	public String getTriggerState() {
		return triggerState;
	}

	public void setTriggerState(String triggerState) {
		this.triggerState = triggerState;
	}

}
