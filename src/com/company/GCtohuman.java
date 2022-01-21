package com.company;

import java.time.LocalDateTime;

/**
 * Класс преобразование объекта локальной даты в текстовое представление<b>calendar</b>.
 */
public class GCtohuman {

    private LocalDateTime calendar;

    public GCtohuman(LocalDateTime gc) {
        this.calendar = gc;
    }
    public GCtohuman() {
        this.calendar = null;
    }

    public void setCalendar(LocalDateTime gc){
        this.calendar = gc;
    }

    public String getDate() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.calendar.getYear());
        builder.append("-");
        builder.append(this.calendar.getMonthValue());
        builder.append("-");
        builder.append(this.calendar.getDayOfMonth());
        return builder.toString();
    }


}
