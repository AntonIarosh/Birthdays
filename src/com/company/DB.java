package com.company;
import java.sql.*;
import java.util.GregorianCalendar;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;
import java.sql.Connection;

/**
 * Класс работы с базой данных <b>conn</b> и <b>answer</b>.
 */
public class DB {
    private  Connection conn;
    private  Statement answer;

    public DB() throws SQLException {
        conn = null;
        answer = null;
        System.out.println("Testing connection");
        conn = connect();
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Connection connect() {
        try {
            String url = "jdbc:postgresql://localhost:5455/Congr";
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "");
            props.setProperty("ssl", "false");
            conn = DriverManager.getConnection(url, props);
            if (conn != null) {
                GregorianCalendar day = new GregorianCalendar();
                System.out.println("Hello World! Now is " + day.getTime());
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    /**
     * Добавление новой записи в базу данных - дня рождения  {@link DB#AddToDB}
     * @param Day - Добавляемый день рождения
     * @return Обновлённый день рождения содержащий айди записи в базе данных
     */
    public Bday AddToDB(Bday Day) throws SQLException {
        this.conn = connect();
        ResultSet res = null;
        Long id = null;
        String date = Day.getDate().getYear() + "-" +  Day.getDate().getMonthValue() + "-"+ Day.getDate().getDayOfMonth();
        String query = "INSERT INTO public.\"Birthdays\"(date, fio, \"personStatus\") VALUES (' "+ date +
                "','"+ Day.getFio()+"', '"+Day.getStatusOfPerson()+"');";
        String selectId = "SELECT \"IdBirthday\"\n" +
                "\tFROM public.\"Birthdays\" WHERE \"fio\" = '" + Day.getFio()+ "';";
        try {
            answer = conn.createStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            answer.executeUpdate(query);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        // id
        try {
            res = answer.executeQuery(selectId);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        try {
            while (res.next()) {
                System.out.println(res.getLong(1 ) );
                id = res.getLong(1 );
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        // id/

        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Day.setId(id);
        return Day;
    }

    /**
     * Редактирование дня рождения записи в базей данных  {@link DB#EditBirthDayInDB}
     * @param Day - Редактируемый день. С новыми данными
     * @return Флаг успешности редактирования
     */
    public int EditBirthDayInDB(Bday Day)
    {
        this.conn = connect();
        String date = Day.getDate().getMonth() + "/" + Day.getDate().getDayOfMonth() + "/"+ Day.getDate().getYear();

        String query = "UPDATE public.\"Birthdays\" SET \"date\" = '" +  date  +"', \"fio\" = '"+Day.getFio() +
                "', \"personStatus\" = '" + Day.getStatusOfPerson()+  "' WHERE (\"IdBirthday\" = '" + Day.getId() + "');";
        int result = 0;
        try {
            answer = conn.createStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            answer.executeUpdate(query);
            result = 1;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Удаление записи о дней рождения из базы данных {@link DB#DeleteFromDB}
     * @param Day - Удаляемый день рождения
     * @return Флаг успешности редактирования
     */
    public int DeleteFromDB(Bday Day)
    {
        this.conn = connect();
        int result = 0;
        String delete ="DELETE FROM public.\"Birthdays\" WHERE \"IdBirthday\" = '" + Day.getId()+ "'; ";
        try {
            answer = conn.createStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            answer.executeUpdate(delete);
            result = 1;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Получение всего списка дней рождений из базы данных и добавление новых записей в текущий список. {@link DB#GetAllBirthdaysToList}
     * @param List - Текущий список записей дней рождений
     * @return Новый список дней рождений
     */
    public List<Bday> GetAllBirthdaysToList(List<Bday> List)
    {
        this.conn = connect();
        ResultSet res = null;
        String query = "SELECT * FROM public.\"Birthdays\"";
        try {
            answer = conn.createStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            res = answer.executeQuery(query);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        try {
            while (res.next()) {
                System.out.println(res.getLong(1 ) + " | " + res.getDate(2) +" | " + res.getString(3) +" | " + res.getString(4) );
                Timestamp timestamp = new Timestamp(res.getDate(2).getTime());
                //Time stamp to LocalDateTime
                timestamp.toLocalDateTime();
                Bday day = new Bday();
                day.setId(res.getLong(1 ));
                day.setDate(timestamp.toLocalDateTime());
                day.setFio(res.getString(3));
                day.setStatusOfPerson(res.getString(4));
                List.add(day);
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return List;
    }
}