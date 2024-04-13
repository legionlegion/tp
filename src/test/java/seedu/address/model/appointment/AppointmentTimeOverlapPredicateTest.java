package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalAppointments.APPT1;
import static seedu.address.testutil.TypicalAppointments.APPT2;

import org.junit.jupiter.api.Test;

public class AppointmentTimeOverlapPredicateTest {
    @Test
    public void test_appointmentTimeOverlaps_returnsTrue() {
        AppointmentTimeOverlapPredicate appointmentPredicate = new AppointmentTimeOverlapPredicate(APPT1);
        Appointment appointment = new Appointment(ALICE.getId(), APPT1.getAppointmentTime());
        assertTrue(appointmentPredicate.test(appointment));
    }

    @Test
    public void test_appointmentTimeDoesNotOverlap_returnsFalse() {
        AppointmentTimeOverlapPredicate appointmentPredicate = new AppointmentTimeOverlapPredicate(APPT1);
        Appointment otherAppointment = new Appointment(BOB.getId(), APPT2.getAppointmentTime());
        assertFalse(appointmentPredicate.test(otherAppointment));
    }
}
