package com.wez.quartz.ctrl;

import com.wez.quartz.svc.TimerTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/timer_task")
public class TimerTaskController {

    @Autowired
    private TimerTaskService timerTaskService;

    /**
     * 获取所有定时任务
     */
    public void list() {

    }

    /**
     * 添加定时任务
     * @return
     */
    @GetMapping(value="add")
    public void addTask() {
        timerTaskService.add();
    }

    /**
     * 修改定时任务触发时间
     */
    public void modifyTime() {

    }

    /**
     * 删除定时任务
     */
    public void delete() {

    }

    /**
     * 暂停定时任务
     */
    public void pause() {

    }

    /**
     * 恢复定时任务
     */
    public void resume() {

    }

    /**
     * 立即执行定时任务
     */
    public void nowRun() {

    }

    /**
     * 启动所有定时任务
     */
    public void startAll() {

    }

    /**
     * 关闭所有定时任务
     */
    public void shutdownAll() {

    }

}
