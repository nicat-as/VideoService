package com.uniso.video;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.concurrent.TimeUnit;

public class TempTest {

    @Test
    public void testTimeConversion(){
        try {
            long start = System.currentTimeMillis();
            Thread.sleep(9000);
            long end = System.currentTimeMillis();
            end -=start;
            System.out.println(TimeUnit.SECONDS.convert(end,TimeUnit.MILLISECONDS));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testTimeValidation(){
        DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm:ss.[SS]");

        LocalTime d = LocalTime.parse("12:33:11.23",    f);
        System.out.println(d);

    }
}
