package com.tencent.hours.util;

import cn.hutool.core.lang.Assert;
import com.alibaba.excel.util.StringUtils;
import com.tencent.hours.common.CommonConstant;
import com.tencent.hours.dto.DepWarnCountDto;
import com.tencent.hours.dto.EmpWarnDto;
import com.tencent.hours.dto.EmployeeLackDto;
import com.tencent.hours.dto.MailDto;
import com.tencent.hours.entity.WarnLog;
import com.tencent.hours.repository.WarnLogRepository;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author ghj
 * @Description 邮件发送
 * @date 2020/11/4
 */
@Component
@Slf4j
public class MailSenderUtil {
    @Autowired
    private JavaMailSenderImpl mailSender;
    @Autowired
    private WarnLogRepository warnLogRepository;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    public final String EMP_MAIL_TITLE = "【告警】西区成员工时未填写信息提醒";
    public final String LEADER_MAIL_TITLE = "【请关注】西区工时填报周报分析及数据明细-%s";


    public final String EMP_CONTENT_TEMPLATE = "西区小伙伴，\n" +
            "系统监测到您%s的工时未填写，请关注工时填报及时性并在两天之内完成系统填报。谢谢！若本周内还未完成填报，系统将自动抄送给对应leader。\n" +
            "详细操作指引可见地址：https://gdc.lexiangla.com/teams/k100014/classes/347917aad6e611ea85bd0a58ac130ead/courses/a92d350a22a311eba2f68e8cefdf4a31?company_from=gdc";
    public final String LEADER_CONTENT_TEMPLATE = "Dear Leaders,\n" +
            "关于%s期间，整体西区成员工时填报情况及对应各个部门未填报情况及明细如下，请大家关注并督促大家完成工时填报，谢谢！\n";

    public final String FILE_NAME = "工时未填写人员明细清单.xlsx";


    /**
     * 发送给员工的邮件
     *
     * @param empWarnDto
     * @param lackDate   缺失的工时日期
     */
    @Async
    public void sendEmp(EmpWarnDto empWarnDto, LocalDate lackDate) {
        MailDto mailDto = new MailDto();
        mailDto.setPAccount(empWarnDto.getPAccount());
        mailDto.setTo(empWarnDto.getEmail());
        mailDto.setSubject(EMP_MAIL_TITLE);
        String text = String.format(EMP_CONTENT_TEMPLATE, DateTimeFormatter.ofPattern(CommonConstant.DateFormat.DEFAULT_DATE_FORMAT).format(lackDate));
        mailDto.setText(text);
        send(mailDto, lackDate);
    }

    /**
     * leader周报统计
     *
     * @param mailList
     * @param depWarnCountDtoList
     */
    @Transactional(rollbackFor = Exception.class)
    public void sendLeader(List<String> mailList, List<DepWarnCountDto> depWarnCountDtoList, List<EmployeeLackDto> employeeLackDtoList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String nowStr = formatter.format(LocalDate.now());
        String title = String.format(LEADER_MAIL_TITLE, nowStr);
        MailDto mailDto = new MailDto();
        StringBuffer sb = new StringBuffer();
        mailList.forEach(v -> sb.append(",").append(v));
        String tos = sb.toString().replaceFirst(",", "");
        mailDto.setTo(tos);
        mailDto.setSubject(title);
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("warnModelList", depWarnCountDtoList);
        modelMap.put("employeeList", employeeLackDtoList);
        sendTemplateMail(mailDto, modelMap, depWarnCountDtoList.get(0).getPeriod());
    }

    /**
     * 发送邮件
     *
     * @param mailDto
     * @return
     */
    public void send(MailDto mailDto, LocalDate lackDate) {
        try {
            Assert.notEmpty(mailDto.getTo(), "参数校验失败：{}", "recipient is empty");
            Assert.notEmpty(mailDto.getSubject(), "参数校验失败：{}", "subject is empty");
            Assert.notEmpty(mailDto.getText(), "参数校验失败：{}", "text is empty");
            sendMimeMail(mailDto);
            //数据库记录
            saveLog(mailDto, Boolean.TRUE, lackDate);
        } catch (Exception e) {
            log.error("发送邮件失败:{}", e.getMessage());
            mailDto.setStatus("fail");
            mailDto.setError(e.getMessage().length() > 300 ? e.getMessage().substring(0, 300) : e.getMessage());
            mailDto.setPAccount(mailDto.getPAccount());
            saveLog(mailDto, Boolean.FALSE, lackDate);
            throw new RuntimeException("邮件发送失败", e);
        }
    }

    /**
     * 保存数据库记录
     *
     * @param mailDto
     * @param sendStatus
     */
    private void saveLog(MailDto mailDto, Boolean sendStatus, LocalDate lackDate) {
        WarnLog warnLog = new WarnLog();
        warnLog.setpAccount(mailDto.getPAccount());
        warnLog.setSendStatus(sendStatus);
        warnLog.setError(mailDto.getError());
        warnLog.setLackDate(lackDate);
        warnLogRepository.save(warnLog);
    }

    private void sendMimeMail(MailDto mailDto) {
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);//true表示支持复杂类型
            String from = mailSender.getJavaMailProperties().getProperty("from");
            mailDto.setFrom(from);//邮件发信人从配置项读取
            messageHelper.setFrom(mailDto.getFrom());//邮件发信人
            messageHelper.setTo(mailDto.getTo().split(","));//邮件收信人
            messageHelper.setSubject(mailDto.getSubject());//邮件主题
            messageHelper.setText(mailDto.getText());//邮件内容
            if (!StringUtils.isEmpty(mailDto.getCc())) {//抄送
                messageHelper.setCc(mailDto.getCc().split(","));
            }
            if (!StringUtils.isEmpty(mailDto.getBcc())) {//密送
                messageHelper.setCc(mailDto.getBcc().split(","));
            }
            if (mailDto.getMultipartFiles() != null) {//添加邮件附件
                for (MultipartFile multipartFile : mailDto.getMultipartFiles()) {
                    messageHelper.addAttachment(multipartFile.getOriginalFilename(), multipartFile);
                }
            }
            if (StringUtils.isEmpty(mailDto.getSentDate())) {//发送时间
                mailDto.setSentDate(new Date());
                messageHelper.setSentDate(mailDto.getSentDate());
            }
            mailSender.send(messageHelper.getMimeMessage());//正式发送邮件
            mailDto.setStatus("ok");
            log.info("统计邮件发送成功：{}->{}", mailDto.getFrom(), mailDto.getTo());
        } catch (Exception e) {
            log.error("统计邮件发送失败：{}", e);
            throw new RuntimeException(e);//发送失败
        }
    }

    /**
     * 发送模板邮件
     *
     * @param mailDto
     * @param modelMap
     */
    public void sendTemplateMail(MailDto mailDto, Map<String, Object> modelMap, String period) {
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);//true表示支持复杂类型
            String from = mailSender.getJavaMailProperties().getProperty("from");
            mailDto.setFrom(from);//邮件发信人从配置项读取
            messageHelper.setFrom(mailDto.getFrom());//邮件发信人
            messageHelper.setTo(mailDto.getTo().split(","));//邮件收信人
            messageHelper.setSubject(mailDto.getSubject());//邮件主题
//            String text = String.format(LEADER_CONTENT_TEMPLATE, period);
            if (!StringUtils.isEmpty(mailDto.getCc())) {//抄送
                messageHelper.setCc(mailDto.getCc().split(","));
            }
            if (!StringUtils.isEmpty(mailDto.getBcc())) {//密送
                messageHelper.setCc(mailDto.getBcc().split(","));
            }
            Object warnModelList = modelMap.get("warnModelList");
            if (warnModelList != null) {
                Template template = freeMarkerConfigurer.getConfiguration().getTemplate("mail.ftl");
                String text = String.format(LEADER_CONTENT_TEMPLATE, period) + FreeMarkerTemplateUtils.processTemplateIntoString(template, modelMap);
                messageHelper.setText(text, true);
            }

            Object employeeList = modelMap.get("employeeList");
            if (employeeList != null) {
                //发送附件
                Template excelTemplate = freeMarkerConfigurer.getConfiguration().getTemplate("excel.ftl");
                File file = new File(MailSenderUtil.class.getResource("/emp.xlsx").getPath());
                excelTemplate.process(modelMap, new FileWriter(file));
                messageHelper.addAttachment(FILE_NAME, file);
            }

            if (StringUtils.isEmpty(mailDto.getSentDate())) {//发送时间
                mailDto.setSentDate(new Date());
                messageHelper.setSentDate(mailDto.getSentDate());
            }
            mailSender.send(messageHelper.getMimeMessage());//正式发送邮件
            mailDto.setStatus("ok");
            log.info("发送邮件成功：{}->{}", mailDto.getFrom(), mailDto.getTo());
        } catch (Exception e) {
            log.error("邮件发送失败:", e);
            throw new RuntimeException("邮件发送失败");
        }
    }


}
