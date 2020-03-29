package com.wez.quartz.manage.job;

import com.alibaba.fastjson.JSON;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class NotifyJob implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		Object obj = jobDataMap.get("taskInfo");
		if (obj != null) {
			System.out.println("Get data: " + JSON.toJSONString(obj));
		} else {
			System.out.println("Not get data.");
		}
	}

}
