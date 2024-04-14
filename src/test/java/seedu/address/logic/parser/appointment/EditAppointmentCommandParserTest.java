package seedu.address.logic.parser.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.appointment.EditAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentTime;

public class EditAppointmentCommandParserTest {

    private EditAppointmentCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new EditAppointmentCommandParser();
    }

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        Index targetIndex = Index.fromOneBased(1);
        // Correcting the prefix in the user input
        String userInput = "1 d/ 31/12/2024 9am-5pm";
        AppointmentTime appointmentTime = new AppointmentTime("31/12/2024 9am-5pm");

        EditAppointmentCommand.EditAppointmentDescriptor descriptor = new EditAppointmentCommand.EditAppointmentDescriptor();
        descriptor.setAppointmentTime(appointmentTime);

        EditAppointmentCommand expectedCommand = new EditAppointmentCommand(targetIndex, descriptor);

        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_optionalFieldsMissing_failure() {
        // The date field is mandatory and using the right prefix
        String userInput = "1";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Including the correct prefix in the user input
        String userInput = "a d/ 31/12/2024 9am-5pm";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_duplicateDate_throwsParseException() {
        // Using the correct prefix and checking for duplicate usage
        String userInput = "1 d/ 31/12/2024 9am-5pm d/ 01/01/2025 10am-4pm";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidDateFormat_throwsParseException() {
        // Testing with the correct prefix but incorrect date format
        final String userInput1 = "1 d/ invalid-date-format";
        assertThrows(ParseException.class, () -> parser.parse(userInput1));

        // Correct date, incorrect time format with correct prefix
        final String userInput2 = "1 d/ 31/12/2024 9-5pm";
        assertThrows(ParseException.class, () -> parser.parse(userInput2));
    }

    @Test
    public void parse_noFieldSpecified_throwsParseException() {
        // Only index provided without any date
        String userInput = "1";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_incorrectAmPmFormat_throwsParseException() {
        // Missing AM/PM designation with correct prefix
        String userInput = "1 d/ 31/12/2024 9-5";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
