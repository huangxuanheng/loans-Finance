package com.harry.boostrap.startup.pe;

import com.harry.boostrap.startup.analyze.utils.EmailHelper;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PeServiceTest{

    @Autowired
    private PeService peService;
    @Autowired
    private EmailHelper emailHelper;
    @Test
    public void testScheduleCheckLowPeAndSendMsg() {
        peService.scheduleCheckLowPeAndSendMsg();
    }

    @Test
    public void sendEmail() throws MessagingException, UnsupportedEncodingException {
        emailHelper.sendEmail("503116108@qq.com","爱是你我","哈哈哈");
    }
}