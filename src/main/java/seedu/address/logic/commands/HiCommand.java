package seedu.address.logic.commands;

import seedu.address.model.Model;

public class HiCommand extends Command {
    public static final String COMMAND_WORD = "hi";

    public static final String SHOWING_HELLO_MESSAGE = "Hello there.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELLO_MESSAGE);
    }
}