package com.nataliatsi.certificatessender.api.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class ValidityDateHelper {

    public static LocalDate calculateValidity(int days) {
        LocalDate date = LocalDate.now();
        int added = 0;

        while (added < days) {
            date = date.plusDays(1);
            if (!(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                added++;
            }
        }
        return date;
    }
}
