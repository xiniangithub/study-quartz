package com.wez.quartz.manage;

import com.wez.quartz.generator.StrongUuidGenerator;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuartzManager {

	private static final String JOB_GROUP_NAME = "DEFAULT_JOB_GROUP_NAME";

	private static final String TRIGGER_GROUP_NAME = "DEFAULT_TRIGGER_GROUP_NAME";

	private static StrongUuidGenerator generator = new StrongUuidGenerator();

	private Scheduler scheduler;

	/**
	 * 添加定时任务
	 * @param jobClass 任务
	 * @param cron 时间设置
	 * @return
	 */
	public QuartzTask addJob(Class<? extends Job> jobClass, String cron) {
        return addJobWithData(jobClass, cron, null);
    }

	/**
	 * 添加定时任务
	 * @param jobClass 任务
	 * @param cron 时间设置
	 * @param data 数据
	 * @return
	 */
	public QuartzTask addJobWithData(Class<? extends Job> jobClass, String cron, Map<String, Object> data) {
        try {
        	String jobName = generator.getNextId();
            JobBuilder jobBuilder = JobBuilder.newJob(jobClass)
                    .withIdentity(jobName, JOB_GROUP_NAME)
                    .storeDurably(true);

            if (data == null) {
                JobDataMap jobData = new JobDataMap(data);
                jobBuilder.setJobData(jobData);
            }

            JobDetail jobDetail = jobBuilder.build();

                    String triggerName = generator.getNextId();
            CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger()
		            .withIdentity(triggerName, TRIGGER_GROUP_NAME)
		            .withSchedule(CronScheduleBuilder.cronSchedule(cron))
		            .build();

            if (!scheduler.isShutdown()) {
            	scheduler.scheduleJob(jobDetail, trigger);
            }
            if (!scheduler.isStarted()) {
                scheduler.start();
            }
            return new QuartzTask(jobName, JOB_GROUP_NAME, triggerName, TRIGGER_GROUP_NAME);
        } catch (Exception e) {
            throw new RuntimeException("添加定时任务失败，" + e.getMessage());
        }
    }

	/**
	 * 获取所有定时任务
	 */
	public List<QuartzTask> list() {
		List<QuartzTask> tasks = new ArrayList<QuartzTask>();
		try {
            for (String groupName : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                    for (Trigger trigger : triggers) {
                    	TriggerKey triggerKey = trigger.getKey();
                    	Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey); // 获取执行器执行状态

                        String state = null;
                        switch (triggerState) {
    	                    case NONE :
    	                		state = "无";
    	                		break;
                        	case NORMAL :
                        		state = "正常状态";
                        		break;
                        	case PAUSED :
                        		state = "暂停状态";
                        		break;
                        	case COMPLETE :
                        		state = "完成";
                        		break;
                        	case ERROR :
                        		state = "错误";
                        		break;
                        	case BLOCKED :
                        		state = "堵塞";
                        		break;
                        }
                        QuartzTask task = new QuartzTask(jobKey.getName(), jobKey.getGroup(), triggerKey.getName(), triggerKey.getGroup(), state);
                        tasks.add(task);
					}
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return tasks;
	}

	/**
     * 修改定时任务触发时间
     * @param triggerName
     * @param cron
     */
    public void modifyJobTime(String triggerName, String cron) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, TRIGGER_GROUP_NAME);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }

            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                /** 方式一 ：调用 rescheduleJob 开始 */
                // 触发器
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组
                triggerBuilder.withIdentity(triggerName, TRIGGER_GROUP_NAME);
                triggerBuilder.startNow();
                // 触发器时间设定
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                scheduler.rescheduleJob(triggerKey, trigger);
                /** 方式一 ：调用 rescheduleJob 结束 */

                /** 方式二：先删除，然后在创建一个新的Job  */
                //JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
                //Class<? extends Job> jobClass = jobDetail.getJobClass();
                //removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
                //addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);
                /** 方式二 ：先删除，然后在创建一个新的Job */
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除定时任务
     * @param jobName
     */
    public void deleteJob(String jobName, String triggerName) {
    	try {
    		TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, TRIGGER_GROUP_NAME);
    		scheduler.pauseTrigger(triggerKey); // 停止触发器
            scheduler.unscheduleJob(triggerKey); // 移除触发器
    		JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
			scheduler.deleteJob(jobKey); // 删除任务
		} catch (SchedulerException e) {
			throw new RuntimeException("删除定时任务失败，" + e.getMessage());
		}
    }

    /**
     * 暂停定时任务
     * @param jobName
     */
    public void pauseJob(String jobName) {
        try {
        	JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
			scheduler.pauseJob(jobKey);
		} catch (SchedulerException e) {
			throw new RuntimeException("暂停定时任务失败，" + e.getMessage());
		}
    }

    /**
     * 恢复定时任务
     * @param jobName
     */
    public void resumeJob(String jobName) {
        try {
        	JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
			scheduler.resumeJob(jobKey);
		} catch (SchedulerException e) {
			throw new RuntimeException("恢复定时任务失败，" + e.getMessage());
		}
    }

    /**
     * 立即执行定时任务
     * @param jobName
     */
    public void nowRunJob(String jobName) {
    	try {
    		JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
			scheduler.triggerJob(jobKey);
		} catch (SchedulerException e) {
			throw new RuntimeException("立即执行定时任务失败，" + e.getMessage());
		}
    }

    /**
     * 启动所有定时任务
     */
    public void startJobs() {
        try {
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException("启动所有定时任务失败，" + e.getMessage());
        }
    }

    /**
     * 关闭所有定时任务
     */
    public void shutdownJobs() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
        	throw new RuntimeException("关闭所有定时任务，" + e.getMessage());
        }
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
