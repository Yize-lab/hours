package com.tencent.hours.task;

import com.sun.org.apache.xpath.internal.functions.FuncTrue;
import com.tencent.hours.dto.DepWarnCountDto;
import com.tencent.hours.dto.EmployeeLackDto;
import com.tencent.hours.entity.EmployeeLeader;
import com.tencent.hours.repository.EmployeeLeaderRepository;
import com.tencent.hours.repository.HoursTempRepository;
import com.tencent.hours.service.WarnService;
import com.tencent.hours.util.MailSenderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author ghj
 * @Description 每周统计任务
 * @date 2020/11/5
 */
@Component
@Slf4j
public class WeekStatisticsTask {
    @Autowired
    private WarnService warnService;
    @Autowired
    private HoursTempRepository hoursTempRepository;
    @Autowired
    private MailSenderUtil mailSenderUtil;
    @Autowired
    private EmployeeLeaderRepository employeeLeaderRepository;
    @Autowired
    @Qualifier("asyncExecutor")
    private Executor executor;


    @Scheduled(cron = "0 0 10 ? * sun-mon") //每周日/周一上午10点发送统计邮件
    public void sendWeekWarnMail() {
        List<DepWarnCountDto> depWarnCountDtoList = warnService.findWeekWarnGroupByEmp();
        List<EmployeeLackDto> employeeLackDtoList = warnService.findEmpList();
        if (depWarnCountDtoList != null) {
            List<EmployeeLeader> employeeLeaderList = employeeLeaderRepository.findAll();
            List<String> leaderMailList = employeeLeaderList.stream().map(leader -> leader.getEmail()).collect(Collectors.toList());
            try {
                mailSenderUtil.sendLeader(leaderMailList, depWarnCountDtoList, employeeLackDtoList);
                log.info("工时填报统计邮件发送任务执行成功");
            } catch (Exception e) {
                log.info("工时填报统计邮件发送任务执行出现异常：{}", e);
                throw new RuntimeException(e);
            }
        }
    }

    @Scheduled(cron = "0 0 0 ? * *")
    public void delHoursTemp() {
        hoursTempRepository.deleteAll();
        log.info(" delete all hours temp data!");
    }

    public void task1() {
        log.info("执行任务！！--{}", Thread.currentThread().getName());
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> task2());
        int a = 20;
        int b = 90;
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> add(a, b), executor);
        try {
            Integer integer = future.get();
            System.out.println("return:" + integer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.err.println("task1     执行！！！！！！！！");
    }

    private int add(int a, int b) {
        log.info("执行任务！！--{}", Thread.currentThread().getName());
        return a + b;
    }

    public String task2() {
        log.info("执行任务！！--{}", Thread.currentThread().getName());
        return "success";
    }

    class Task implements Supplier<String> {

        @Override
        public String get() {
            return "success";
        }
    }

}
