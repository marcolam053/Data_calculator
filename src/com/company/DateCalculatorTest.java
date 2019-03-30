package com.company;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DateCalculatorTest {
    // Tests for Command Line Argument Handler and validation
    @Test
    void CheckExtraArguments(){
        String[] input ={"01/01/1996","01/01/1989","01/01/2000"};
        Exception exception = assertThrows(Exception.class,()->DateCalculator.argument(input));
        assertEquals("Too many arguments.",exception.getMessage());
    }

    @Test
    void CheckMissingArguments() {
        String[] input = {"01/01/1996", "01/01/1989", "01/01/2000"};
        Exception exception = assertThrows(Exception.class, () -> DateCalculator.argument(input));
        assertEquals("Too many arguments.", exception.getMessage());
    }

    // Impossible Dates Validation Check
    @Test
    void CheckArgumentsImpossibleStartMonth(){
        String[] input ={"01/00/1996","01/01/1996"};
        Exception exception = assertThrows(Exception.class,()->DateCalculator.argument(input));
        assertEquals("ERROR : Invalid start date",exception.getMessage());
    }

    @Test
    void CheckArgumentsImpossibleStartDay(){
        String[] input ={"32/01/1996","01/01/1996"};
        Exception exception = assertThrows(Exception.class,()->DateCalculator.argument(input));
        assertEquals("ERROR : Invalid start date",exception.getMessage());
    }

    // Start Date Validation Check
    @Test
    void CheckArgumentsStartYearBefore(){
        String[] input ={"31/01/1899","01/01/1996"};
        Exception exception = assertThrows(Exception.class,()->DateCalculator.argument(input));
        assertEquals("ERROR : Invalid start date",exception.getMessage());

    }

    @Test
    void CheckArgumentsStartDatesAfter(){
        String[] input ={"31/01/3000","01/01/1996"};
        Exception exception = assertThrows(Exception.class,()->DateCalculator.argument(input));
        assertEquals("ERROR : Invalid start date",exception.getMessage());
    }

    // End Date Validation Check
    @Test
    void CheckArgumentsEndDatesBefore(){
        String[] input ={"31/01/1901","01/01/1899"};
        Exception exception = assertThrows(Exception.class,()->DateCalculator.argument(input));
        assertEquals("ERROR : Invalid end date.",exception.getMessage());
    }

    @Test
    void CheckArgumentsEndDatesAfter(){
        String[] input ={"31/01/1901","01/01/3000"};
        Exception exception = assertThrows(Exception.class,()->DateCalculator.argument(input));
        assertEquals("ERROR : Invalid end date.",exception.getMessage());
    }

    // Test for validity check for dates
    @Test
    void CheckPattern1() {
        assertEquals(false, DateCalculator.isValid("31/0/2019"));
    }

    @Test
    void CheckPattern2() {
        assertEquals(false, DateCalculator.isValid("3/01/2019"));
    }

    @Test
    void CheckPattern3() {
        assertEquals(false, DateCalculator.isValid("31/01/19"));
    }

    @Test
    void CheckPattern4() {
        assertEquals(true, DateCalculator.isValid("31/01/2019"));
    }

    @Test
    void CheckPattern5() {
        assertEquals(true, DateCalculator.isValid("31/03/2019"));
    }

    @Test
    void CheckPattern6() {
        assertEquals(false, DateCalculator.isValid("1/0/1"));
    }

    @Test
    void CheckImpossibleDays() {
        assertEquals(false, DateCalculator.isValid("31/04/2019"));
    }

    @Test
    void CheckImpossibleMonth1() {
        assertEquals(false, DateCalculator.isValid("31/13/2019"));
    }

    @Test
    void CheckImpossibleMonth2() {
        assertEquals(false, DateCalculator.isValid("31/00/2019"));
    }

    @Test
    void CheckIsValidDateLower() {
        assertEquals(false,DateCalculator.isValid("01/01/1900"));
    }

    @Test
    void CheckIsValidDateUpper() {
        assertEquals(false,DateCalculator.isValid("01/01/3000"));
    }

    @Test
    void CheckValidDateCorrect() {
        assertEquals(true,DateCalculator.isValid("01/01/2019"));
    }

    // Test for days between calculation and logic
    @Test
    void CheckLogic1() {
        assertEquals(1,DateCalculator.daysBetween("01/01/2000","03/01/2000"));
    }

    @Test
    void CheckLogic2() {
        assertEquals(1,DateCalculator.daysBetween("03/08/1983","05/08/1983"));
    }

    @Test
    void CheckSameDay() {
        assertEquals(0,DateCalculator.daysBetween("01/01/2000","01/01/2000"));
    }

    @Test
    void CheckDaysBetween2() {
        assertEquals(19,DateCalculator.daysBetween("02/06/1983","22/06/1983"));
    }

    @Test
    void CheckDaysBetween3() {
        assertEquals(173,DateCalculator.daysBetween("04/07/1984","25/12/1984"));
    }

    @Test
    void CheckDaysBetween4() {
        assertEquals(1979,DateCalculator.daysBetween("03/01/1989","03/08/1983"));
    }

    @Test
    void CheckSwappingPosition() {
        assertEquals(1979,DateCalculator.daysBetween("03/08/1983","03/01/1989"));
    }

    @Test
    void CheckZeroDay() {
        assertEquals(0,DateCalculator.daysBetween("03/08/1983","03/08/1983"));
    }

}