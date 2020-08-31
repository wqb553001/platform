package com.doctor.assistant.scheduleserver;

import com.doctor.assistant.scheduleserver.manager.MyJob;
import com.doctor.assistant.scheduleserver.manager.QuartzManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleCenterApplicationTests {

	@Autowired
	Scheduler scheduler;

	@Test
	public void contextLoads() {
		QuartzManager manager = new QuartzManager();
		String jobName = "job_today";
		String jobGroupName = "job_groupOne";
		String triggerName = "triggerName_One";
		String triggerGroupName = "triggerGroupNameOne";
		Class jobClass = MyJob.class;
		String cron = "0/10 * * * * ? ";
		try {
			manager.addJob(scheduler, jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}
