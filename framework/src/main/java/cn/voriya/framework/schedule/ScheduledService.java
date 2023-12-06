package cn.voriya.framework.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ScheduledService {
    private final ScheduledExecutorService taskScheduler = Executors.newScheduledThreadPool(10);
    private final Map<String, ScheduledFuture<?>> scheduledTasks = new HashMap<>();

    public ScheduledService() {
    }

    public void scheduleTask(String taskId, Runnable task, LocalDateTime taskTime) {
        cancelTask(taskId); // 取消已存在的任务
        final LocalDateTime now = LocalDateTime.now();
        // 计算taskTime与当前时间的时间差
        final long initialDelay = ChronoUnit.MILLIS.between(now, taskTime);
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(task, initialDelay, TimeUnit.MILLISECONDS);
        scheduledTasks.put(taskId, scheduledTask);
        log.info("定时任务 {} 已开启，执行时间：{}", taskId, taskTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public void cancelTask(String taskId) {
        ScheduledFuture<?> scheduledTask = scheduledTasks.get(taskId);
        if (scheduledTask != null && !scheduledTask.isCancelled()) {
            scheduledTask.cancel(true);
            scheduledTasks.remove(taskId);
            log.info("定时任务 {} 已取消", taskId);
        }
    }

}
