package seedu.address.model.appointment.exceptions;

/**
 * Signals that the operation will result in overlapping Appointments
 */
public class OverlappingAppointmentsException extends RuntimeException {
    public OverlappingAppointmentsException() {
        super("Operation would result in overlapping appointments");
    }
}
