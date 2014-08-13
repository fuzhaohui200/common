package org.shine.common.ldap.exchange;

import java.util.Calendar;
import java.util.Timer;

public class ScheduleSychonizeRun {

    public static void main(String[] args) {
        Timer timer = new Timer();
        Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        date.set(Calendar.HOUR, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        ScheduleSynchonizeMysql synchonize = new ScheduleSynchonizeMysql();
        synchonize.setPasswordKey("123456");
        timer.schedule(synchonize, date.getTime(), 10 * 60 * 1000);
    }

}
