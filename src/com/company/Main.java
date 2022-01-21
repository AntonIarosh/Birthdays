package com.company;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Locale;
import java.util.Scanner;

/**
 * Точка входа в программу.
 */
public class Main {

    public static void main(String[] args) throws IOException, SQLException, ParseException {
        DB db = null;
        Locale.setDefault(new Locale("ru","RU"));
        try
        {
            db = new DB();
        } catch(Exception E)
        {
            System.out.println("Database is fail " + E.getMessage());
        }
        //Console.ReadKey();
        BirthDayList listing = new BirthDayList();
        do
        {
            listing.CheckTheBirthDays();
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("*****************************************************");
            System.out.println("***Birthdays for today                            ***");
            System.out.println("*****************************************************");
            listing.BirthDaysOnThisDate();
            System.out.println("/////////////////////////////////////////////////////");
            System.out.println("///The nearest birthdays in the range of 10 days  ///");
            listing.UpcomingBirthdays();
            System.out.println("/////////////////////////////////////////////////////");
            //Выводим меню, его пункты с соответствующими цифрами\символами
            System.out.println("### MENU ###");
            System.out.println("1. Adding a new birthday to the list of birthdays");
            System.out.println("2. Viewing a list of birthdays");
            System.out.println("3. Deleting a day from the list by the name of the birthday person");
            System.out.println("4. Edit the selected day by the name of the birthday boy");

            System.out.println("5. Write a list to a file ");
            System.out.println("6. Download the birtdays list from a file");

            System.out.println("7. View birthdays for today");
            System.out.println("8. View past birthdays");

            System.out.println("9.View the nearest birthdays in the range of 10 days");
            System.out.println("_________________________________________________________");
            System.out.println("10. Adding a new birthday to the database");
            System.out.println("11. Adding a new birthday from list to the database");
            System.out.println("12. Delete birtday from a database");
            System.out.println("13. Download the birtdays list from a database");
            System.out.println("14. Update the birtday in database");

            System.out.println("15. Exit");
            System.out.println("\n" + "Введите команду: ");

            int number = 0;

            Scanner in = new Scanner(System.in);
            System.out.print("\n" + "Введите команду: ");
            String ch = in.nextLine();
            number = Integer.parseInt (ch);

            switch (number)
            {
                case  1 :
                {
                    listing.addToListBirthDays();
                    break;
                }
                case  2 :
                {
                    listing.LookBirthdayList();
                    break;
                }
                case  3 :
                {
                    listing.DeleteBirthDay();
                    break;
                }
                case  4 :
                {
                    listing.ChangeBirthDay();
                    break;
                }
                case  5 :
                {
                    listing.writeListToFile();
                    break;
                }
                case  6 :
                {
                    listing.readListFromFile();
                    break;
                }
                case 7 :
                {
                    listing.BirthDaysOnThisDate();
                    break;
                }
                case 8 :
                {
                    listing.PastBirthdays();
                    break;
                }
                case 9 :
                {
                    listing.UpcomingBirthdays();
                    break;
                }
                case 10:
                {
                    listing.AddToDD(db);
                    break;
                }
                case 11:
                {
                    listing.AddListToDb(db);
                    break;
                }
                case 12:
                {
                    listing.DeleteFromDB(db);
                    break;
                }
                case 13:
                {
                    listing.GetListFromDB(db);
                    break;
                }
                case 14:
                {
                    listing.UpdateBirthdayFromDB(db);
                    break;
                }
                case 15:
                {
                    return;
                }
                default:
                {

                    break;
                }
            }
        } while (true);

    }
}
