package com.company;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateCalculator {

    private static Scanner keyboard = new Scanner(System.in);
    private static Date startDate = null;
    private  static Date endDate = null;
    public static Date ref = new Date(1,1,1901);

    // Handle user input.
    public static void greetUser() {
        System.out.println("Please choose a data entry method:");
        System.out.println("1 : Manual Entry\n2 : Quit Programme\n");

        // Validate Input method.
        while (keyboard.hasNextLine()) {
            String command = keyboard.nextLine();
            if (command.isEmpty()) {
                System.out.println("Please enter a number.");
            }
            // Input handler
            if (command.matches("[1-2]")) {
                Manual_entry(command);
                break;
            } else {
                System.out.println("Invalid input.Try again.");
                continue;
            }
        }
    }

    public static void Manual_entry(String command) {
        if (command.equals("1")) {
            System.out.println("Please enter start date. Date format accepted :  DD/MM/YYYY.");
            startDate = set_date();
            // set end Date and validate
            System.out.println("Please enter end date. Date format accepted :  DD/MM/YYYY.");
            endDate = set_date();
        } else if (command.equals("2")) {
            System.out.println("Thank you for using date calculator.");
            System.exit(0);
        }
    }

    // Helper method for setting date in entry Handler
    public static Date set_date() {
        Scanner input = new Scanner(System.in);
        String data ="";
        Date date = null;
        while (input.hasNextLine()) {
            data = input.nextLine();
            if(isValid(data)){
                date = toDate(data);
                break;
            }else {
                continue;
            }
        }
        return date;
    }

    public static Date toDate(String date) {
        int[] arr = toIntArr(date);
        Date d = new Date(arr[0],arr[1],arr[2]);
        return d;
    }

    // Check if the input date is valid or not.
    public static boolean isValid(String date) {
        String valid_pattern = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
        Pattern pattern = Pattern.compile(valid_pattern);
        Matcher matcher = pattern.matcher(date);
        String[] arr = date.split("/");

        // pattern check.
        if (matcher.matches()){
            // validate year
            if (Integer.parseInt(arr[2]) < 1901 || Integer.parseInt(arr[2]) > 2999){
                System.out.println("Invalid year. The year should be between 1901 and 2999.");
                return false;
            } else if(!isValidDate(Integer.parseInt(arr[0]),Integer.parseInt(arr[1]),Integer.parseInt(arr[2]))){ // check for valid date
                return false;
            }
        }else {
            System.out.println("Invalid date pattern. Accepted Pattern: DD/MM/YYYY. Please try again.");
            return false;
        }
        return true;
    }

    // Check for invalid dates.
    public static boolean isValidDate(int day, int month, int year) {
        try {
            LocalDate.of(year,month,day);
        } catch (DateTimeException e) {
            System.out.println("The date does not exist. Try again.");
            return false;
        }
        return true;
    }

    // helper for coverting string into int[]
    private static int[] toIntArr(String input) {
        String[] arr = input.split("/");
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = Integer.parseInt(arr[i]);
        }
        return result;
    }

    // Validate command line arguments
    public static void argument(String[] args) {
        if (args.length != 0) {
            // validate argument len
            if (args.length > 2 ) {
                throw new IllegalArgumentException("Too many arguments.");
            } else if (args.length < 2){
                throw new IllegalArgumentException("Missing arguments.");
            } else {
                // validate and set start date
                if (isValid(args[0])){
                    startDate = toDate(args[0]);
                } else {
                    throw new IllegalArgumentException("ERROR : Invalid start date");
                }
                // validate and set end date
                if (isValid(args[1])){
                    endDate = toDate(args[1]);
                }else {
                    throw new IllegalArgumentException("ERROR : Invalid end date.");
                }
            }
        }
    }

    // Calculate days between
    public static int result(Date startDate,Date endDate){
        int days1 = getDays(startDate);
        int days2 = getDays(endDate);

        int result = Math.abs(days2-days1) - 1;
        if(result < 0) {
            result = 0;
        }
        return result;
    }

    // Handle date calculation
    // Reference : http://mathforum.org/library/drmath/view/66535.html
    public static int leapYear(Date date) {
        int year = date.getYear();
        if (date.getMonth() <= 2) {
           year --;
        }
        return year/4-year/100+year/400;
    }
    public static int getDays(Date date) {
        int daysInMonth[] = {31,28,31,30,31,30,31,31,30,31,30,31};
        int diff = (date.getYear()-1901)*365 + date.getDate();
        for (int i = 0 ; i < date.getMonth()-1; i++) {
            diff += daysInMonth[i];
        }
        diff += leapYear(date);
        return diff;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Date Calculator.\n");
        // If there is no command line argument, choose option.
        if (args.length == 0){
            DateCalculator.greetUser();
        } else {
            DateCalculator.argument(args);
        }
        // Calculate the days between two dates
        int result = DateCalculator.result(startDate,endDate);
        System.out.println("The result is : ");
        System.out.println(result + " days\n");
        keyboard.close();
    }
}
