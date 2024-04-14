package seedu.address.logic.parser.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.appointment.AddAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentTime;

public class AddAppointmentCommandParserTest {

    private AddAppointmentCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new AddAppointmentCommandParser();
    }

    @Test
    public void parse_validArgs_returnsAddAppointmentCommand() throws Exception {
        String userInput = "1 d/13/12/2024 9am-12pm";
        AddAppointmentCommand expectedCommand = new AddAppointmentCommand(Index.fromOneBased(1),
                new AppointmentTime("13/12/2024 9am-12pm"));
        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_missingDate_throwsParseException() {
        // Test input missing the date prefix
        String userInput = "1";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Test input with an invalid index
        String userInput = "a --date 2023-10-01T14:00";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_duplicateDate_throwsParseException() {
        // Test input with duplicate date prefixes
        String userInput = "1 --date 2023-10-01T14:00 --date 2023-11-01T15:00";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidDateFormat_throwsParseException() {
        // Test input with an invalid date format
        String userInput = "1 --date invalid-date";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        // Test input missing the index entirely
        String userInput = "--date 2023-10-01T14:00";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
