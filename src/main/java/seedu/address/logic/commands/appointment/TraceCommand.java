package seedu.address.logic.commands.appointment;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class TraceCommand extends Command {

    public static final String COMMAND_WORD = "trace";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Performs contact tracing on the "
        + "appointment identified.\n"
        + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_TRACE_SUCCESS = "Traced appointment: %1$s";
    private final Index targetIndex;

    /**
     * @param index of the appointment in the filtered appointment list to edit
     */
    public TraceCommand(Index index) {
        requireNonNull(index);
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }
        Appointment appointmentToDelete = lastShownList.get(targetIndex.getZeroBased());
        //model.updateFilteredAppointmentList();
        //model.updateFilteredPersonList();
        return new CommandResult(String.format(MESSAGE_TRACE_SUCCESS,
            Messages.formatAppointment(appointmentToDelete)));
    }
}
