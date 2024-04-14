package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class AppointmentTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AppointmentTime(null));
    }

    @Test
    public void getters() {
        AppointmentTime appointmentTime = new AppointmentTime("12/01/2024 2pm-4pm");

        // same date
        assertTrue(appointmentTime.getAppointmentDate().equals(LocalDate.of(2024, 1, 12)));

        // same start time
        assertTrue(appointmentTime.getStartTime().equals(LocalTime.of(14, 0)));

        // same end time
        assertTrue(appointmentTime.getEndTime().equals(LocalTime.of(16, 0)));
    }

    @Test
    public void equals() {
        AppointmentTime appointmentTime = new AppointmentTime("12/01/2024 2pm-4pm");

        // same values -> returns true
        assertTrue(appointmentTime.equals(new AppointmentTime("12/01/2024 2pm-4pm")));

        // same object -> returns true
        assertTrue(appointmentTime.equals(appointmentTime));

        // null -> returns false
        assertFalse(appointmentTime.equals(null));

        // different types -> returns false
        assertFalse(appointmentTime.equals(5.0f));

        // different typing -> returns true
        assertTrue(appointmentTime.equals(new AppointmentTime("12/01/2024 2PM - 4PM")));

        // different values -> returns false
        assertFalse(appointmentTime.equals(new AppointmentTime("12/01/2024 2pm-5pm")));
    }

    @Test
    public void buildCurrent_correctInstance() {
        AppointmentTime current = AppointmentTime.buildCurrent();
        LocalTime now = LocalTime.now();

        assertTrue(Math.abs(Duration.between(now, current.getStartTime()).toMinutes()) < 1000);
        assertTrue(Math.abs(Duration.between(now, current.getEndTime()).toMinutes()) < 1000);
    }

    @Test
    public void constructor_invalidTimeFormat_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new AppointmentTime("12/01/2024 12ppm-2ppm"));
        assertThrows(IllegalArgumentException.class, () -> new AppointmentTime("12/01/2024 2am-3aam"));
    }

    @Test
    public void getFormattedDateTime_formatsCorrectly() {
        AppointmentTime morning = new AppointmentTime("12/01/2024 12am-1am");
        assertEquals("12/01/2024 12am-1am", morning.getFormattedDateTime());

        AppointmentTime noon = new AppointmentTime("12/01/2024 12pm-1pm");
        assertEquals("12/01/2024 12pm-1pm", noon.getFormattedDateTime());

        AppointmentTime evening = new AppointmentTime("12/01/2024 11pm-12am");
        assertEquals("12/01/2024 11pm-12am", evening.getFormattedDateTime());

    }

    @Test
    public void compareTo_correctOrdering() {
        AppointmentTime early = new AppointmentTime("12/01/2024 2pm-4pm");
        AppointmentTime late = new AppointmentTime("12/01/2024 6pm-8pm");
        assertTrue(early.compareTo(late) > 0);
        assertTrue(late.compareTo(early) < 0);

        AppointmentTime earlierDate = new AppointmentTime("11/01/2024 2pm-4pm");
        AppointmentTime laterDate = new AppointmentTime("13/01/2024 2pm-4pm");
        assertTrue(earlierDate.compareTo(laterDate) > 0);
        assertTrue(laterDate.compareTo(earlierDate) < 0);
    }

    @Test
    public void compareTo_sameDateDifferentTimes() {
        AppointmentTime earlier = new AppointmentTime("12/01/2024 2pm-3pm");
        AppointmentTime later = new AppointmentTime("12/01/2024 4pm-5pm");
        assertTrue(earlier.compareTo(later) > 0);
        assertTrue(later.compareTo(earlier) < 0);
    }

    @Test
    public void compareTo_nullObject() {
        AppointmentTime appointmentTime = new AppointmentTime("12/01/2024 2pm-3pm");
        assertTrue(appointmentTime.compareTo(null) > 0);
    }

}
