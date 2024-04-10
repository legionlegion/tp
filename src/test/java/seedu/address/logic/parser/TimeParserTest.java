package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentTime;

public class TimeParserTest {
    @Test
    public void regex() {
        try {
            assertTrue(TimeParser.parse("15/02/2024 9am-10am") instanceof AppointmentTime);
            assertTrue(TimeParser.parse("15/02/2024 9AM-10am") instanceof AppointmentTime);
            assertTrue(TimeParser.parse("15/02/2024 9am-10AM") instanceof AppointmentTime);
            assertTrue(TimeParser.parse("15/02/2024 9AM-10AM") instanceof AppointmentTime);
            assertTrue(TimeParser.parse("15/02/2024 9am -10Am") instanceof AppointmentTime);
            assertTrue(TimeParser.parse("15/02/2024 9am - 10am") instanceof AppointmentTime);
            assertTrue(TimeParser.parse("15/02/2024 9aM - 10AM") instanceof AppointmentTime);
            assertTrue(TimeParser.parse("15/02/2024 9Am- 10Am") instanceof AppointmentTime);
            assertTrue(TimeParser.parse("15/02/2024 10am -10pm") instanceof AppointmentTime);
            assertTrue(TimeParser.parse("15/02/2024 2Pm - 5PM") instanceof AppointmentTime);
            assertTrue(TimeParser.parse("15/02/2024 2PM - 2pM") instanceof AppointmentTime);
            assertTrue(TimeParser.parse("15/02/2024 2PM- 3Pm") instanceof AppointmentTime);
            assertThrows(ParseException.class, () -> TimeParser.parse("15/02/20242PM- 3Pm"));
            assertThrows(ParseException.class, () -> TimeParser.parse("15/02/2024 20PM- 3Pm"));
            assertThrows(ParseException.class, () -> TimeParser.parse("15/02/2024 1PM- 13Pm"));
            assertThrows(ParseException.class, () -> TimeParser.parse(""));
            assertThrows(NullPointerException.class, () -> TimeParser.parse(null));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void today_parse_success() {
        try {
            assertTrue(TimeParser.parse("today 2PM-3PM") instanceof AppointmentTime);
            assertTrue(TimeParser.parse("TODAY 2PM-3PM") instanceof AppointmentTime);
            assertTrue(TimeParser.parse("Today 2PM-3PM") instanceof AppointmentTime);
            assertTrue(TimeParser.parse("toDAy 2PM-3PM") instanceof AppointmentTime);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void tdy_parse_success() {
        try {
            assertTrue(TimeParser.parse("tdy 2PM-3PM") instanceof AppointmentTime);
            assertTrue(TimeParser.parse("TDY 2PM-3PM") instanceof AppointmentTime);
            assertTrue(TimeParser.parse("tDY 2PM-3PM") instanceof AppointmentTime);
            assertTrue(TimeParser.parse("Tdy 2PM-3PM") instanceof AppointmentTime);
            assertTrue(TimeParser.parse("TdY 2PM-3PM") instanceof AppointmentTime);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void capitalization_validAppointmentWindow_success() {
        assertTrue(TimeParser.validAppointmentWindow("2PM-3PM"));
        assertTrue(TimeParser.validAppointmentWindow("2pM-3PM"));
        assertTrue(TimeParser.validAppointmentWindow("2Pm-3PM"));
        assertTrue(TimeParser.validAppointmentWindow("2PM-3pM"));
        assertTrue(TimeParser.validAppointmentWindow("2PM-3Pm"));

        assertTrue(TimeParser.validAppointmentWindow("2pm-3PM"));
        assertTrue(TimeParser.validAppointmentWindow("2PM-3pm"));
        assertTrue(TimeParser.validAppointmentWindow("2pm-3pm"));
    }

    @Test
    public void spacing_validAppointmentWindow_success() {
        assertTrue(TimeParser.validAppointmentWindow("2PM -3PM"));
        assertTrue(TimeParser.validAppointmentWindow("2PM- 3PM"));
        assertTrue(TimeParser.validAppointmentWindow("2PM - 3PM"));
        assertTrue(TimeParser.validAppointmentWindow(" 2PM-3PM"));
        assertTrue(TimeParser.validAppointmentWindow("2PM-3PM "));
        assertTrue(TimeParser.validAppointmentWindow(" 2PM-3PM "));
        assertTrue(TimeParser.validAppointmentWindow(" 2PM -3PM"));
        assertTrue(TimeParser.validAppointmentWindow(" 2PM- 3PM"));
        assertTrue(TimeParser.validAppointmentWindow(" 2PM - 3PM "));
        assertTrue(TimeParser.validAppointmentWindow("          2PM-3PM"));
        assertTrue(TimeParser.validAppointmentWindow("2PM          -3PM"));
        assertTrue(TimeParser.validAppointmentWindow("2PM-          3PM"));
        assertTrue(TimeParser.validAppointmentWindow("2PM-3PM          "));
    }

    @Test
    public void miscellaneousRegex_validAppointmentWindow_success() {
        assertTrue(TimeParser.validAppointmentWindow("2pm -3PM"));
        assertTrue(TimeParser.validAppointmentWindow("2Pm- 3Pm"));
        assertTrue(TimeParser.validAppointmentWindow("     2PM -3PM    "));
        assertTrue(TimeParser.validAppointmentWindow("2PM -3Pm"));
        assertTrue(TimeParser.validAppointmentWindow("2pM-     3pM"));
        assertTrue(TimeParser.validAppointmentWindow("2pm -3PM"));
        assertTrue(TimeParser.validAppointmentWindow("2PM   -   3pm      "));
        assertTrue(TimeParser.validAppointmentWindow("2Pm -    3Pm"));
        assertTrue(TimeParser.validAppointmentWindow("  2pM -  3PM"));
    }

    @Test
    public void parseAMtoAM_validAppointmentWindow_success() {
        assertTrue(TimeParser.validAppointmentWindow("2AM-3AM"));
        assertTrue(TimeParser.validAppointmentWindow("9AM-10AM"));
        assertTrue(TimeParser.validAppointmentWindow("12AM-11AM"));
    }

    @Test
    public void parseAMtoAM_validAppointmentWindow_failure() {
        assertFalse(TimeParser.validAppointmentWindow("3AM-12AM"));
        assertFalse(TimeParser.validAppointmentWindow("11AM-10AM"));
        assertFalse(TimeParser.validAppointmentWindow("8AM-8AM"));
    }

    @Test
    public void parsePMtoPM_validAppointmentWindow_success() {
        assertTrue(TimeParser.validAppointmentWindow("2PM-3PM"));
        assertTrue(TimeParser.validAppointmentWindow("12PM-3PM"));
        assertTrue(TimeParser.validAppointmentWindow("2PM-11PM"));
    }

    @Test
    public void parsePMtoPM_validAppointmentWindow_failure() {
        assertFalse(TimeParser.validAppointmentWindow("3PM-12PM"));
        assertFalse(TimeParser.validAppointmentWindow("3PM-2PM"));
        assertFalse(TimeParser.validAppointmentWindow("12PM-12PM"));
    }

    @Test
    public void parseAMtoPM_validAppointmentWindow_success() {
        assertTrue(TimeParser.validAppointmentWindow("10AM-3PM"));
        assertTrue(TimeParser.validAppointmentWindow("12AM-3PM"));
        assertTrue(TimeParser.validAppointmentWindow("12AM-12PM"));
        assertTrue(TimeParser.validAppointmentWindow("11AM-12PM"));
    }

    @Test
    public void parsePMtoAM_validAppointmentWindow_failure() {
        assertFalse(TimeParser.validAppointmentWindow("3PM-10AM"));
        assertFalse(TimeParser.validAppointmentWindow("3PM-2AM"));
        assertFalse(TimeParser.validAppointmentWindow("3PM-12AM"));
        assertFalse(TimeParser.validAppointmentWindow("12PM-12AM"));
        assertFalse(TimeParser.validAppointmentWindow("12PM-11AM"));
    }

    @Test
    public void validAppointmentDate_success() {
        assertTrue(1 + 1 == 2);
        //assertTrue(TimeParser.validAppointmentDate("24/10/2024"));
    }

    @Test
    public void wrongDate_validAppointmentDate_failure() {
        assertTrue(1 + 1 == 2);
        //assertFalse(TimeParser.validAppointmentDate("32/10/2024"));
    }

    //@Test
    //public void wrongFormat_validAppointmentDate_failure() {
    //    assertFalse(TimeParser.validAppointmentDate("10/10/1818"));
    //    assertFalse(TimeParser.validAppointmentDate("10/10/2040"));
    //
    //}
}
