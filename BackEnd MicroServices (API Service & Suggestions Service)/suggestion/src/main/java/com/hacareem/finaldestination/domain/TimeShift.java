package com.hacareem.finaldestination.domain;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by waqas on 4/30/17.
 */
public enum TimeShift {
    MORNING(1, 9, 12), AFTERNOON(2, 12, 17), EVENING(3, 18, 24), MIDNIGHT(4,0,8);

    private final int value;
    private final int minHour;
    private final int maxHour;

    TimeShift(int value, int minHour, int maxHour) {
        this.value = value;
        this.minHour = minHour;
        this.maxHour = maxHour;
    }

    public int getValue() {
        return value;
    }

    public static TimeShift decideShift(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        for (final TimeShift shift : values()) {
            int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
            if (hourOfDay >= shift.minHour && hourOfDay <= shift.maxHour) {
                return shift;
            }
        }
        return null;
    }
}
