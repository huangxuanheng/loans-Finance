package com.harry.boostrap.startup.analyze.enterprise.cash;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@Slf4j
public class DividendServiceTest {
    @Test
    public void testGetDividendDate() {
        Date date = DividendService.getDividendDate("SH600036");
        System.out.println(date);
    }

    @Test
    public void replace(){
        String str="123456789";
        str.replace("5","a");
        System.out.println(str);
    }

}