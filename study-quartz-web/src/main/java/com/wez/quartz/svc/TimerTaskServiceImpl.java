package com.wez.quartz.svc;

import com.wez.quartz.manage.QuartzManager;
import com.wez.quartz.manage.job.NotifyJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TimerTaskServiceImpl implements TimerTaskService {

    @Autowired
    private QuartzManager quartzManager;

    @Override
    public void add() {
        Map<String, Object> params = new HashMap<>();
        params.put("ret", "ok");
        quartzManager.addJobWithData(NotifyJob.class, "0/3 * * * * ?", params);
    }

}
