package seedu.address.model.person;

import static seedu.address.testutil.TypicalAppointments.ALICE_APPT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;

public class TestPersonFromAppointmentPredicate {
    private ObservableList<Appointment> appointments;
    private Person person1, person2;

    @BeforeEach
    void setUp() {
        person1 = ALICE;
        person2 = BOB;

        appointments = FXCollections.observableArrayList();
        appointments.add(ALICE_APPT);
    }

    @Test
    void testPersonFromAppointmentList_predicateReturnsTrueForPersonInList() {
        PersonFromAppointmentListPredicate predicate = new PersonFromAppointmentListPredicate(appointments);
        assertTrue(predicate.test(person1), "Predicate should return true for a person with an appointment");
    }

    @Test
    void testPersonFromAppointmentList_predicateReturnsFalseForPersonNotInList() {
        PersonFromAppointmentListPredicate predicate = new PersonFromAppointmentListPredicate(appointments);
        assertFalse(predicate.test(person2), "Predicate should return false for a person without an appointment");
    }

    @Test
    void testPersonFromAppointmentList_predicateReturnsFalseForEmptyList() {
        PersonFromAppointmentListPredicate predicate = new PersonFromAppointmentListPredicate(FXCollections.observableArrayList());
        assertFalse(predicate.test(person1), "Predicate should return false when no appointments are in the list");
    }

    @Test
    void testPersonFromAppointmentList_predicateEquality() {
        PersonFromAppointmentListPredicate predicate1 = new PersonFromAppointmentListPredicate(appointments);
        PersonFromAppointmentListPredicate predicate2 = new PersonFromAppointmentListPredicate(appointments);
        PersonFromAppointmentListPredicate predicate3 = new PersonFromAppointmentListPredicate(FXCollections.observableArrayList());

        assertEquals(predicate1, predicate2, "Predicates with the same appointment list should be equal");
        assertNotEquals(predicate1, predicate3, "Predicates with different appointment lists should not be equal");
    }
}
