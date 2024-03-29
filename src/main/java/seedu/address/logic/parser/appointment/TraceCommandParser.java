package seedu.address.logic.parser.appointment;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.appointment.TraceCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class TraceCommandParser implements Parser<TraceCommand> {
    public TraceCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new TraceCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TraceCommand.MESSAGE_USAGE), pe);
        }
    }
}
