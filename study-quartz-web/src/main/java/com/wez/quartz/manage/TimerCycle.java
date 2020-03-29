package com.wez.quartz.manage;

import java.io.Serializable;

/**
 * 定时器周期
 * @author Admin
 *
 */
public class TimerCycle implements Serializable {

	private static final long serialVersionUID = -9219034978871091584L;

	/** 定时器周期，month、weekday、day */
	private String cycle;

	/** 每月哪一天 */
	private Integer day;

	/** 每周哪一天 */
	private Integer week;

	/** 每天哪一时 */
	private Integer hour;

	/** 每小时哪一分 */
	private Integer minutes;

	/** 每分钟哪一秒 */
	private Integer second;

	/** 循环次数 */
	private Integer loop;

	public TimerCycle() {}

	public String toCron() {
    	String cron = null;
		if ("month".equals(this.getCycle())) { // 每月的某天，0 15 10 15 * ?
			cron = String.format("%s %s %s %s * ?", this.getSecond(), this.getMinutes(), this.getHour(), this.getDay());
		} else if ("week".equals(this.getCycle())) { // 每星期的星期几 ，0 15 10 ? * 1
			cron = String.format("%s %s %s ? * %s", this.getSecond(), this.getMinutes(), this.getHour(), this.getWeek());
		} else if ("day".equals(this.getCycle())) { // 每天的什么时刻，0 15 10 * * ?
			cron = String.format("%s %s %s * * ?", this.getSecond(), this.getMinutes(), this.getHour());
		}else if ("second".equals(this.getCycle())) { // 每隔几秒，0/X * * * * ?
			cron = String.format("0/%s * * * * ?", this.getSecond());
		}
		return cron;
    }

	public static TimerCycle build(String cycle, Integer day, Integer week, Integer hour, Integer minutes, Integer second, Integer loop) {
		TimerCycle timerCycle = new TimerCycle();
		timerCycle.setCycle(cycle);
		timerCycle.setDay(day);
		timerCycle.setWeek(week);
		timerCycle.setHour(hour);
		timerCycle.setMinutes(minutes);
		timerCycle.setSecond(second);
		timerCycle.setLoop(loop);
		return timerCycle;
	}
	public static TimerCycle buildSecond(Integer second, Integer loop) {
		return build("second", null, null, null, null, second, loop);
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

	public Integer getSecond() {
		return second;
	}

	public void setSecond(Integer second) {
		this.second = second;
	}

	public Integer getLoop() {
		return loop;
	}

	public void setLoop(Integer loop) {
		this.loop = loop;
	}

}
