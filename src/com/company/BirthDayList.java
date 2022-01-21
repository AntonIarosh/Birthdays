package com.company;

import java.io.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Класс списка дней рождений <b>BirthDay</b> и <b>List</b>.
 */
public class BirthDayList {
    private Bday BirthDay;

    public java.util.List<Bday> List;

    public BirthDayList()
    {
        this.List = new LinkedList<Bday>();
        this.BirthDay = new Bday();
    }

    public Bday getBirthDay() {
        return this.BirthDay;
    }

    public void setBirtDay(Bday day) {
        this.BirthDay = day;
    }

    /**
     * Добавление дня рождения в список  {@link BirthDayList#addToListBirthDays}
     */
    public void addToListBirthDays()
    {
        String fio, status, day, month, year;
        Scanner in = new Scanner(System.in);

        System.out.println(" | Write a person's fio");
        fio = in.nextLine();
        System.out.println(" | Write a person's status");
        status = in.nextLine();
        System.out.println(" | Write the date of Birthday. Enter the day:");
        day = in.nextLine();
        System.out.println(" | Write the date of Birthday. Enter the month:");
        month = in.nextLine();
        System.out.println(" | Write the date of Birthday. Enter the year:");
        year = in.nextLine();

        LocalDateTime date = LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day),0,0);
        Bday NewBirthDay = new Bday(date, fio, -1, status, Bday.CheckBirthDayPassed(date));
        this.List.add(NewBirthDay);
    }

    /**
     * Просмотр списка дней рождений  {@link BirthDayList#LookBirthdayList}
     */
    public void LookBirthdayList()
    {
        System.out.println("List of BirthDays: ");
        for (Bday t: List)
        {
            Bday ThisBirthDay = new Bday(t);
            Boolean done = t.getIsPassed();
            PrintDay(t, done);
        }
    }

    /**
     * Удаление выбранного по ФИО дня рождения из списка  {@link BirthDayList#DeleteBirthDay}
     */
    public void DeleteBirthDay()
    {
        String fio;
        System.out.println(" | If you want to delete a Birthday you must write a fio of person :");
        Scanner in = new Scanner(System.in);
        fio = in.nextLine();
        Boolean result = false;
        try
        {
            for (Bday t: List)
            {

                String contains = t.getFio();
                if (fio.equals(contains))
                {
                    result = List.remove(t);
                }
            }
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        if (result)
        {
            System.out.println("Delete is succesful!!!");
        }
    }

    /**
     * Запись всего списка дней рождений в файл  {@link BirthDayList#writeListToFile}
     */
    public void writeListToFile()
    {
        try
        {
            String fileName = "out.txt";
            OutputStream out = new FileOutputStream(fileName);
            Writer file = new OutputStreamWriter(out);
            PrintWriter print = new PrintWriter(file,true);
            file.flush();
            GCtohuman gcth = new GCtohuman();
            try
            {
                for(Bday t : List)
                {
                    gcth.setCalendar(t.getDate());
                    print.println(t.getFio());
                    print.println(gcth.getDate());
                    print.println(t.getStatusOfPerson());
                    print.println(t.getIsPassed());
                    print.println(t.getId());
                }
            }

            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
            print.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Чтения файла записи дней рождений и составление списка.  {@link BirthDayList#readListFromFile}
     */
    public void readListFromFile() throws ParseException, FileNotFoundException {
        String fileName = "out.txt";
        InputStream fin = new FileInputStream(fileName);
        Reader reader = new InputStreamReader(fin);
        Scanner in = new Scanner(reader);
        while (in.hasNextLine())
        {
            Bday newBirthDay = new Bday();
            newBirthDay.setFio(in.nextLine());
            System.out.println(newBirthDay.getFio() + " - FIO");
            ///
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
            dateFormat.setLenient(false);
            Date d = dateFormat.parse(in.nextLine());

            LocalDateTime c = d.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            System.out.println(c.toString() + " - Data");

            newBirthDay.setDate(c);
            newBirthDay.setStatusOfPerson(in.nextLine());
            System.out.println(newBirthDay.getStatusOfPerson() + " - STATUS");
            newBirthDay.setIsPassed(in.nextBoolean());
            System.out.println(newBirthDay.getIsPassed() + " - setIsPassed");
            newBirthDay.setId(in.nextLong());
            System.out.println(newBirthDay.getId() + " - id");
            List.add(newBirthDay);

            in.nextLine();
        }
        in.close();
    }

    /**
     * Изменение дня рождения в списке. Поиск изменяемого дня рождения по ФИО  {@link BirthDayList#ChangeBirthDay}
     */
    public void ChangeBirthDay()
    {
        Scanner in = new Scanner(System.in);
        String fio, status, day, month, year; ;
        String NewFio, yesNo;

        System.out.println(" | If you want to change a Task you must write a fio of person:");
        fio = in.nextLine();
        try
        {
            for (Bday t : List)
            {
                String contains = t.getFio();
                if (fio.equals(contains))
                {
                    System.out.println(" | Make change Birthday fio? - Y/N");
                    yesNo = in.nextLine();
                    if (yesNo.equals("Y"))
                    {
                        System.out.println(" | Write the new fio of person thi Berthday ");
                        NewFio = in.nextLine();
                        t.setFio(NewFio);
                    }
                    System.out.println(" | Make change Birthday status of person? - Y/N");
                    yesNo = in.nextLine();
                    if (yesNo.equals("Y"))
                    {
                        System.out.println(" | Write the new status");
                        status = in.nextLine();
                        t.setStatusOfPerson(status);
                    }
                    System.out.println(" | Make change Birthday date - Y/N");
                    yesNo = in.nextLine();
                    if (yesNo.equals("Y"))
                    {
                        System.out.println(" | Write the date of Birthday. Enter the day:");
                        day = in.nextLine();
                        System.out.println(" | Write the date of Birthday. Enter the month:");
                        month = in.nextLine();
                        System.out.println(" | Write the date of Birthday. Enter the year:");
                        year = in.nextLine();
                        LocalDateTime date = LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 0,0);
                        t.setDate(date);
                    }
                }
            }
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Уже прошедший дни рождения {@link BirthDayList#PastBirthdays}
     */
    public void PastBirthdays()
    {
        System.out.println("List of past birthdays: ");
        for(Bday t : List)
        {
            Boolean done = t.getIsPassed();
            if (done)
            {
                PrintDay(t, done);
            }
        }
    }

    /**
     * Дни рождения на сегодняшний день {@link BirthDayList#BirthDaysOnThisDate}
     */
    public void BirthDaysOnThisDate()
    {
        LocalDateTime BirthDayDate;
        LocalDateTime now = LocalDateTime.now();
        for (Bday t : List)
        {
            BirthDayDate = t.getDate();
            if ((BirthDayDate.getMonth() == now.getMonth())
            && (BirthDayDate.getDayOfMonth() == now.getDayOfMonth()))
            {
                Boolean done = t.getIsPassed();
                PrintDay(t, done);
            }
        }
    }


    /**
     * Печать дней рождений которые находятся в диапазоне: от -5 до +5 дней. {@link BirthDayList#UpcomingBirthdays}
     */
    public void UpcomingBirthdays()
    {
        LocalDateTime BirthDayDate;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime later = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(),0,0);
        later = later.plusDays(5);
        LocalDateTime earlier = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(),0,0);
        earlier = earlier.minusDays(5);
        for (Bday t : List)
        {
            BirthDayDate = t.getDate();
            if ((BirthDayDate.getMonth() == earlier.getMonth())
                    && (BirthDayDate.getMonth() == later.getMonth())
                    && (BirthDayDate.getDayOfMonth() > earlier.getDayOfMonth())
                    && (BirthDayDate.getDayOfMonth() < later.getDayOfMonth())
                    && (BirthDayDate.getDayOfMonth() != now.getDayOfMonth()))
            {
                Boolean done = t.getIsPassed();
                PrintDay(t, done);
            }
        }
    }

    /**
     * Печать дней рождений которые находятся в диапазоне: от -5 до +5 дней. {@link BirthDayList#PrintDay}
     * @param day - День рождения
     * @return флаг указывающий прошёл день рождения уже или нет
     */
    public void PrintDay(Bday day, Boolean done)
    {
        System.out.println("________________");
        System.out.println(day.getFio());
        GCtohuman gcth = new GCtohuman(day.getDate());
        System.out.println(gcth.getDate());
        System.out.println(day.getStatusOfPerson());
        if (done)
        {
            System.out.println("The Birthday is passed");
        }
        else
        {
            System.out.println("Birthday not yet passed");
        }

        System.out.println("________________");
    }

    /**
     * Проверка каждого дня рождения в списке на то прошёл он уже или нет {@link BirthDayList#CheckTheBirthDays}
     */
    public void CheckTheBirthDays()
    {
        for(Bday t : List)
        {
            Bday.SwitchPassedTrigger(t);
        }
    }

    /**
     * Добавление записи в базу данных - дня рождения {@link BirthDayList#AddToDD}
     * @param db - Объект подключения к базе данных
     */
    public void AddToDD(DB db) throws SQLException {
        Scanner in = new Scanner(System.in);
        String fio, status, day, month, year;

        System.out.println(" | Write a person's fio");
        fio = in.nextLine();

        System.out.println(" | Write a person's status");
        status = in.nextLine();

        System.out.println(" | Write the date of Birthday. Enter the day:");
        day = in.nextLine();
        System.out.println(" | Write the date of Birthday. Enter the month:");
        month = in.nextLine();
        System.out.println(" | Write the date of Birthday. Enter the year:");
        year = in.nextLine();
        LocalDateTime date = LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day),0,0);
        Bday NewBirthDay = new Bday(date, fio, -1, status, Bday.CheckBirthDayPassed(date));
        this.List.add(db.AddToDB(NewBirthDay));
    }

    /**
     * Добавление списка данных в БД {@link BirthDayList#DeleteFromDB}
     * @param db - Объект подключения к базе данных
     */
    public void AddListToDb(DB db) throws SQLException {
        for (Bday t : List)
        {
            if (t.getId() == -1)
            {
                db.AddToDB(t);
            }
        }
    }

    /**
     * Удаление записи из бд - день рождения ищется по ФИО, а удаляется по идентификатору {@link BirthDayList#DeleteFromDB}
     * @param db - Объект подключения к базе данных
     */
    public void DeleteFromDB(DB db)
    {
        String fio;
        System.out.println(" | If you want to delete a Birthday you must write a fio of person :");
        Scanner in = new Scanner(System.in);
        fio = in.nextLine();
        int answer = 0;
        try
        {
            for (Bday t : List)
            {

                String contains = t.getFio();
                if (fio.equals(contains))
                {
                    answer = db.DeleteFromDB(t);
                }
                if (answer != 0)
                {
                    List.remove(t);
                }
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    public void GetListFromDB(DB db)
    {
        this.List.addAll(db.GetAllBirthdaysToList(List));
    }

    /**
     * Обновление дня рождения по фио. Обновление всех полей записи. {@link BirthDayList#UpdateBirthdayFromDB}
     * @param db - Объект подключения к базе данных
     */
    public void UpdateBirthdayFromDB(DB db)
    {
        String fio;
        System.out.println(" | If you want to change a Birthday you must write a fio of person :");
        Scanner in = new Scanner(System.in);
        fio = in.nextLine();
        int answer = 0;
        try
        {
            for (Bday t : List)
            {
                answer = 0;
                String contains = t.getFio();
                if (fio.equals(contains))
                {
                    System.out.println("ID = " + t.getId());
                    String newFio, status, day, month, year;

                    System.out.println(" | Write a person's fio");
                    newFio = in.nextLine();

                    System.out.println(" | Write a person's status");
                    status = in.nextLine();

                    System.out.println(" | Write the date of Birthday. Enter the day:");
                    day = in.nextLine();
                    System.out.println(" | Write the date of Birthday. Enter the month:");
                    month = in.nextLine();
                    System.out.println(" | Write the date of Birthday. Enter the year:");
                    year = in.nextLine();
                    LocalDateTime date = LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day),0,0);

                    t.setDate(date);
                    t.setFio(newFio);
                    t.setStatusOfPerson(status);


                    answer = db.EditBirthDayInDB(t);
                }
                if (answer != 0)
                {
                    System.out.println("Replace is succesful");
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
