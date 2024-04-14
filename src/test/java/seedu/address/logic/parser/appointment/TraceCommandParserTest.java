package seedu.address.logic.parser.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.appointment.TraceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class TraceCommandParserTest {

    private TraceCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new TraceCommandParser();
    }

    @Test
    public void parse_validArgs_returnsTraceCommand() throws Exception {
        String userInput = "1";
        TraceCommand expectedCommand = new TraceCommand(Index.fromOneBased(1));
        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Non-integer input
        String userInput = "a";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_outOfRangeInput_throwsParseException() {
        // Input is a number too large for an index
        String userInput = Long.toString(Integer.MAX_VALUE + 1L);
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        // Empty string should not parse correctly
        String userInput = "";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_whitespaceOnly_throwsParseException() {
        // Whitespace only input should not parse correctly
        String userInput = "   ";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
