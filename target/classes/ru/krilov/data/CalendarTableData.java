/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.krilov.data;

/**
 *
 * @author Home
 */
public class CalendarTableData {
    private Integer value;
    private Integer dayNumber;
    private Integer weekNumber;

    public CalendarTableData(Integer value, Integer dayNumber, Integer weekNumber) {
        this.value = value;
        this.dayNumber = dayNumber;
        this.weekNumber = weekNumber;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public Integer getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(Integer weekNumber) {
        this.weekNumber = weekNumber;
    }
}
