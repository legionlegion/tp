package seedu.address.logic.parser.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.appointment.DeleteAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteAppointmentCommandParserTest {

    private DeleteAppointmentCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new DeleteAppointmentCommandParser();
    }

    @Test
    public void parse_validArgs_returnsDeleteAppointmentCommand() throws Exception {
        // Assuming valid numeric input "1" corresponds to the first indexed item
        String userInput = "1";
        DeleteAppointmentCommand expectedCommand = new DeleteAppointmentCommand(Index.fromOneBased(1));
        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Test input with an invalid index (not a number)
        String userInput = "a";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_outOfRangeIndex_throwsParseException() {
        // Test input with an index out of possible range (e.g., a very large number)
        String userInput = Long.toString(Integer.MAX_VALUE + 1L);
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        // Test input that's empty
        String userInput = "";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_whitespaceOnly_throwsParseException() {
        // Test input that contains only whitespace
        String userInput = "    ";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
