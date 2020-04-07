package com.uniso.video;

import org.junit.jupiter.api.Test;

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
}
