package seedu.address.model.appointment;

import java.util.function.Predicate;

public class AppointmentTimeOverlapPredicate implements Predicate<Appointment> {
    private Appointment target;

    public AppointmentTimeOverlapPredicate (Appointment target) {
        this.target = target;
    }

    @Override
    public boolean test (Appointment appointment) {
        AppointmentTime targetTime = target.getAppointmentTime();
        AppointmentTime appointmentTime = appointment.getAppointmentTime();

        if (!targetTime.getAppointmentDate().equals(appointmentTime.getAppointmentDate())) {
            return false;
        }

        return targetTime.getStartTime().isBefore(appointmentTime.getEndTime()) &&
            appointmentTime.getStartTime().isBefore(targetTime.getEndTime());
    }
}

