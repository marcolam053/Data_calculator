package com.company;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateCalculator {

    private static Scanner keyboard = new Scanner(System.in);
    private static String startDate = null;
    private  static String endDate = null;

    // Helper method for setting date in entry Handler
    private static String set_date() {
        Scanner input = new Scanner(System.in);
        String date ="";
        while (input.hasNextLine()) {
            date = input.nextLine();
            if(!isValid(date)){
                continue;
            }else {
                break;
            }
        }
        return date;
    }

    // Check if the input date is valid or not.
    private static boolean isValid(String date) {
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
            } else if(!isValidDate(Integer.parseInt(arr[0]),Integer.parseInt(arr[1]),Integer.parseInt(arr[2]))){
                return false;
            }
        }else {
            System.out.println("Invalid date. Accepted Pattern: DD/MM/YYYY. Please try again.");
            return false;
        }
        return true;
    }

    // Check for invalid dates.
    private static boolean isValidDate(int day, int month, int year) {
        try {
            LocalDate.of(year,month,day);
        } catch (DateTimeException e) {
            System.out.println("The Date is invalid. Try again.");
            return false;
        }
        return true;
    }

    // Handle date calculation
    private static long daysBetween(String start, String end) {
        int[] start_arr = toIntArr(start);
        int[] end_arr = toIntArr(end);
        LocalDate startDate = LocalDate.of(start_arr[2],start_arr[1],start_arr[0]);
        LocalDate endDate = LocalDate.of(end_arr[2],end_arr[1],end_arr[0]);
        long daysBetween = ChronoUnit.DAYS.between(startDate,endDate);
        if (daysBetween < 0){
            daysBetween= daysBetween*-1;
        }
        return Math.toIntExact(daysBetween)-1;
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

    // Handle user input.
    private static void greetUser() {
        System.out.println("Welcome to Date Calculator. Please choose a data entry method:");
        System.out.println("1 : Manual Entry\n2 : Quit Programme\n");

        // Validate Input method.
        while (keyboard.hasNextLine()) {
            String command = keyboard.nextLine();
            if (command.isEmpty()) {
                System.out.println("Please enter a number.");
            }

            // Input handler
            if (command.matches("[1-2]")) {
                if (command.equals("1")) {
                    System.out.println("Please enter start date. Date format accepted :  DD/MM/YYYY.");
                    startDate = set_date();
                    // set end Date and validate
                    System.out.println("Please enter end date. Date format accepted :  DD/MM/YYYY.");
                    endDate = set_date();
                    break;
                } else if (command.equals("2")) {
                    System.out.println("Now quiting ...\nThank you for using date calculator.");
                    System.exit(0);
                }
            } else {
                System.out.println("Invalid input.Try again.");
                continue;
            }
        }
    }

    // Validate command line arguments
    private static void argument(String[] args) {
        if (args.length != 0) {
            // validate argument len
            if (args.length > 2 ) {
                System.out.println("\nToo many arguments.\nQuiting date calculator ...");
                System.exit(1);
            } else if (args.length < 2){
                System.out.println("\nMissing arguments. Please enter an extra date and try again.\nQuiting date calculator.");
                System.exit(1);
            } else {
                // validate input
                if (isValid(args[0])){
                    startDate = args[0];
                } else {
                    System.out.println("\nERROR : Invalid start date\nQuiting date calculator. Please try again.");
                    System.exit(1);
                }
                if (isValid(args[1])){
                    endDate = args[1];
                    System.out.println("Command line argument detected ! Here is the result!\n\n");
                }else {
                    System.out.println("\nERROR : Invalid end date.\nQuiting date calculator. Please try again.");
                    System.exit(1);
                }
            }
        }
    }

    // Calculate days between
    private static void result(String startDate, String endDate){
        System.out.println("\nCalculating days between ...");
        long result = daysBetween(startDate,endDate);
        System.out.println("Days Between : " + result + " days\n");
        System.out.println("Thank you for using date calculator. See you next time.");
    }

    public static void main(String[] args) {
        // Check if there is command line argument. If not th
        DateCalculator.argument(args); // TODO : Test case for main.argument method
        // If there is no command line argument, choose option.
        if (args.length == 0){
            DateCalculator.greetUser(); // TODO : Test case for main argument method.
        }
        // Calculate the days between two dates
        DateCalculator.result(startDate,endDate); // TODO : test case for calculate days between
    }
}
