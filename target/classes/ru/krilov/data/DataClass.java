/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.krilov.data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author Home
 */
public class DataClass {    
    public Stream<String> getMonthsValues() {
        final Stream<String> months = Stream.of(Month.values()).map(currentMonth ->
            currentMonth.getDisplayName(
                TextStyle.FULL, Locale.forLanguageTag("lv")
            )
        );
        
        return Stream.concat(
            Stream.of("Gada budžets"), months
        );
    }

    public Stream<String> getDaysValues() {
        return Stream.of(DayOfWeek.values()).map(value ->
            value.getDisplayName(
              TextStyle.SHORT, Locale.forLanguageTag("lv")
            )
        );
    }

    public int getWeekCountInCurrentMonth(final int month) {
        return Integer.parseInt(
            LocalDate.of(
              2017, Month.of(month), Month.of(month).length(Year.isLeap(2017))
            ).format(DateTimeFormatter.ofPattern("W"))
        );
    }
    
    public List<CalendarTableData> dayInCurrentMonthCollection(final int month) {
        return IntStream.rangeClosed(
            1, Month.of(month).length(Year.isLeap(2017))
        ).mapToObj(
            currentDay -> LocalDate.of(2017, month, currentDay)
        ).map(value -> 
            new CalendarTableData(
                value.getDayOfMonth(),
                value.getDayOfWeek().getValue(),
                Integer.parseInt(value.format(DateTimeFormatter.ofPattern("W")))
            )
        ).collect(Collectors.toList());
    }
    
    public Stream<String> editButtonItems() {
        return Stream.of("Pievienot", "Dzēst", "Rediģēt", "Informācija");
    }
    
    public Stream<String> timeTableItems() {
        return Stream.of("Laiks", "Ienākumi", "Izmaksas", "Apraksts");
    }
    
    public Stream<Month> getMonthsFullData() {
        return Stream.of(Month.values());
    }
}
