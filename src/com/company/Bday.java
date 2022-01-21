package com.company;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Класс Дня Рождения <b>Id</b> и <b>Date</b> <b>Fio</b.
 */
public class Bday {
    /** поле ФИО человека у которого ДР */
    private String Fio;

    /** поле Дата рождения */
    private LocalDateTime Date;

    /** поле Идентификатор */
    private long Id;

    /** поле Прошло ли ДР на настоящее время*/
    private Boolean IsPassed;

    /** поле Статус человека */
    private String StatusOfPerson;

    /**
     * Конструктор - создание нового объекта
     * @see Bday#Bday()
     */
    public Bday()
    {
        this.Date = LocalDateTime.now();
        this.Fio = null;
        this.Id = -1;
        this.StatusOfPerson = null;
        this.IsPassed = false;
    }

    /**
     * Конструктор - создание нового объекта сo значениями
     * @param Day - Объект Дня рождения
     * @see Bday#Bday()
     */
    public Bday(Bday Day)
    {
        this.Date = Day.Date;
        this.Fio = Day.Fio;
        this.Id = Day.Id;
        this.StatusOfPerson = Day.StatusOfPerson;
        this.IsPassed = Day.IsPassed;
    }

    /**
     * Конструктор - создание нового объекта сo значениями
     * @param data - Дата дня рождения
     * @param fio - ФИО человека у которого ДР
     * @param id - идентификатор записи
     * @param status - статус
     * @param  isPassed - флаг, о том прошёл ли ДР
     * @see Bday#Bday()
     */
    public Bday(LocalDateTime data, String fio, long id, String status, Boolean isPassed)
    {
        this.Date = data;
        this.Fio = fio;
        this.Id = id;
        this.StatusOfPerson = status;
        this.IsPassed = isPassed;
    }

    public void setId(long id)
    {
        this.Id = id;
    }

    public long getId()
    {
        return this.Id;
    }

    public void setDate(LocalDateTime date)
    {
        this.Date = date;
    }

    public LocalDateTime getDate()
    {
        return this.Date;
    }

    public void setFio(String fio)
    {
        this.Fio = fio;
    }

    public String getFio()
    {
        return this.Fio;
    }

    public void setIsPassed(Boolean isPassed)
    {
        this.IsPassed = isPassed;
    }

    public Boolean getIsPassed()
    {
        return this.IsPassed;
    }

    public void setStatusOfPerson(String statusOfPerson)
    {
        this.StatusOfPerson = statusOfPerson;
    }

    public String getStatusOfPerson()
    {
        return this.StatusOfPerson;
    }

    /**
     * Функция получения значения поля {@link Bday#CheckBirthDayPassed}
     * @param Date - Дата ДР
     * @return возвращает лошический результат проверки - true -  день рождения ещё прошёл
     */
    public static Boolean CheckBirthDayPassed(LocalDateTime Date)
    {
        LocalDateTime now = LocalDateTime.now();
        if (Date.isAfter(now))
        {
            return false;
        }
        else
            return true;
    }

    /**
     * Изменение триггера Дня Рождения, прошёл уже или нет  {@link Bday#SwitchPassedTrigger}
     * @param Day - Дата ДР
     * @return Измененённый День Рождениял
     */
    public static Bday SwitchPassedTrigger(Bday Day)
    {
        if (CheckBirthDayPassed(Day.Date))
        {
            Day.IsPassed = true;
        }
        return Day;
    }
}
