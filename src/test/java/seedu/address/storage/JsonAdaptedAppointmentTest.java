package seedu.address.storage;

import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedAppointment.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppointments.ALICE_APPT;

public class JsonAdaptedAppointmentTest {

    private static final UUID VALID_ID = ALICE_APPT.getId();
    private static final UUID VALID_PERSON_ID = ALICE_APPT.getPersonId();
    private static final String VALID_APPOINTMENT_TIME = ALICE_APPT.getAppointmentTimeString();
    @Test
    public void toModelType_validAppointmentDetails_returnsAppointment() throws Exception {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(ALICE_APPT);
        assertEquals(ALICE_APPT, appointment.toModelType());
    }

    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment =
            new JsonAdaptedAppointment(null, VALID_PERSON_ID, VALID_APPOINTMENT_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "id");
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullPersonId_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment =
            new JsonAdaptedAppointment(VALID_ID, null, VALID_APPOINTMENT_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "personId");
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullAppointmentTime_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment =
            new JsonAdaptedAppointment(VALID_ID, VALID_PERSON_ID, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "appointmentTime");
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }
}
