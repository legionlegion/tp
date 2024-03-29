package seedu.address.logic.commands.appointment;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class TraceCommand extends Command {

    public static final String COMMAND_WORD = "trace";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Performs contact tracing on the "
        + "appointment identified.\n"
        + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_TRACE_SUCCESS = "Traced appointments and persons listed!";
    private final Index index;

    /**
     * @param index of the appointment in the filtered appointment list to edit
     */
    public TraceCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(MESSAGE_TRACE_SUCCESS);
    }
}
