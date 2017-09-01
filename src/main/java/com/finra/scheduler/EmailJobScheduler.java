package com.finra.scheduler;

import com.finra.mail.Mail;
import com.finra.mail.MailService;
import com.finra.model.FileMetaData;
import com.finra.repo.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by mchin on 8/31/2017.
 */
@Component
public class EmailJobScheduler {

    @Autowired
    private FileRepo fileRepo;

    @Autowired
    private MailService mailService;


    @Scheduled(cron = "${email.notifications}")
    public void emailNewFileUploadNotifications() throws Exception{
        Calendar now = new GregorianCalendar();
        now.add(Calendar.HOUR, -1);
        List<FileMetaData> result = fileRepo.findByUploadDateAfter(now.getTime());

        Mail mail = new Mail();
        mail.setMailTo("markf.chin@gmail.com");
        mail.setMailSubject("Test Email");
        mail.getModel().put("files", result);
        mailService.sendEmail(mail);
    }
}
