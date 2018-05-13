package com.example.core.service;

import com.example.common.converter.TimeConversion;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;


public class TimeConvertTest {

    @Test
    public void stringToInstantTest() {
        String inputDate = "2018-05-07";
        LocalDateTime localDateTime = TimeConversion.fromString(inputDate);
        Assert.assertEquals(localDateTime.toString(), "2018-05-07T00:00");
    }

    @Test
    public void bla() {
        //todo test this properly
        System.out.println(TimeConversion.getStartOfDay());
        System.out.println(TimeConversion.getEndOfDay());
    }
}
