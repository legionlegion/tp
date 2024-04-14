package seedu.address.model.util;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.UUID;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_returnsCorrectPersonData() {
        Person[] persons = SampleDataUtil.getSamplePersons();
        assertNotNull(persons);
        assertEquals(6, persons.length); // Checks if all persons are included

        // Test details of one of the persons to ensure data integrity
        Person firstPerson = persons[0];
        assertEquals(UUID.fromString("c7cc471f-32b4-4ba5-880a-3cf25ba855a6"), firstPerson.getId());
        assertEquals("Alex Yeoh", firstPerson.getName().toString());
        assertEquals("87438807", firstPerson.getPhone().toString());
        assertEquals("Blk 30 Geylang Street 29, #06-40", firstPerson.getAddress().toString());
        assertTrue(firstPerson.getTags().contains(new Tag("friends")));
    }

    @Test
    public void getSampleAppointments_returnsCorrectAppointmentData() {
        Appointment[] appointments = SampleDataUtil.getSampleAppointments();
        assertNotNull(appointments);
        assertEquals(2, appointments.length); // Check correct number of appointments

        // Test details of an appointment
        Appointment firstAppointment = appointments[0];
        assertEquals(UUID.fromString("c7cc471f-32b4-4ba5-880a-3cf25ba855a6"), firstAppointment.getPersonId());
        assertEquals("10/02/2024 11am-2pm", firstAppointment.getAppointmentTime().getFormattedDateTime());
    }

    @Test
    public void getSampleAddressBook_containsAllEntities() {
        ReadOnlyAddressBook sampleAb = SampleDataUtil.getSampleAddressBook();
        assertNotNull(sampleAb);

        // Ensure all persons and appointments are added to the address book
        assertEquals(6, sampleAb.getPersonList().size());
        assertEquals(2, sampleAb.getAppointmentList().size());
    }

    @Test
    public void getTagSet_returnsProperTagSet() {
        Set<Tag> tags = SampleDataUtil.getTagSet("friends", "colleagues");
        assertNotNull(tags);
        assertEquals(2, tags.size());
        assertTrue(tags.contains(new Tag("friends")));
        assertTrue(tags.contains(new Tag("colleagues")));
    }
}
