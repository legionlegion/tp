package seedu.address.logic.parser.appointment;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class FindAppointmentCommandParserTest {

    private FindAppointmentCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new FindAppointmentCommandParser();
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        String userInput = "";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_whitespaceOnly_throwsParseException() {
        String userInput = "   ";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
