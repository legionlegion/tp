package seedu.address.model.person;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;


public class PersonFromAppointmentListPredicate implements Predicate<Person> {
    private final ObservableList<Appointment> appointments;
    public PersonFromAppointmentListPredicate(ObservableList<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public boolean test(Person person) {
        for (Appointment a : appointments) {
            if (a.getPersonId().equals(person.getId())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonFromAppointmentListPredicate)) {
            return false;
        }

        PersonFromAppointmentListPredicate otherP = (PersonFromAppointmentListPredicate) other;
        return appointments.equals(otherP.appointments);
    }

}
