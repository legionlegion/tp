package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.testutil.TypicalPersons;

public class PersonFromAppointmentListPredicateTest {

    @Test
    public void equals() {
        Appointment appointment = new Appointment(TypicalPersons.ALICE.getId(), new AppointmentTime("10/04/2050 2PM-3PM"));
        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
        appointmentObservableList.add(appointment);
        PersonFromAppointmentListPredicate personFromAppointmentListPredicate = new PersonFromAppointmentListPredicate(appointmentObservableList);

        assertTrue(personFromAppointmentListPredicate.equals(personFromAppointmentListPredicate));
        assertFalse(personFromAppointmentListPredicate.equals(new Object()));
    }

}
