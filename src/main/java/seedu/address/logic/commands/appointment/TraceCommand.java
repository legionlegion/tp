package seedu.address.logic.commands.appointment;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentTimeOverlapPredicate;
import seedu.address.model.person.PersonFromAppointmentListPredicate;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class TraceCommand extends Command {

    public static final String COMMAND_WORD = "trace";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Performs contact tracing on the "
        + "appointment identified.\n"
        + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_TRACE_SUCCESS = "Traced appointment: %1$s";

    private AppointmentTimeOverlapPredicate appointmentTimeOverlapPredicate;

    private PersonFromAppointmentListPredicate personFromAppointmentListPredicate;
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
        Appointment appointmentToTrace = lastShownList.get(targetIndex.getZeroBased());
        this.appointmentTimeOverlapPredicate = new AppointmentTimeOverlapPredicate(appointmentToTrace);
        model.updateFilteredAppointmentList(appointmentTimeOverlapPredicate);

        personFromAppointmentListPredicate = new PersonFromAppointmentListPredicate(model.getFilteredAppointmentList());
        model.updateFilteredPersonList(personFromAppointmentListPredicate);
        return new CommandResult(String.format(MESSAGE_TRACE_SUCCESS,
            Messages.formatAppointment(appointmentToTrace)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TraceCommand)) {
            return false;
        }

        TraceCommand otherTraceCommand = (TraceCommand) other;
        return targetIndex.equals(otherTraceCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetIndex", targetIndex)
            .toString();
    }
}
