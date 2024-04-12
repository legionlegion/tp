package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.appointment.AddAppointmentCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class AddAppointmentCommandTest {

    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_appointmentAcceptedByModel_addAppointmentSuccessful() {
        Person firstPerson = model.getAddressBook().getPersonList().get(0);
        UUID personId = firstPerson.getId();
        AppointmentTime appointmentTime = new AppointmentTime("10/04/2024 2PM-3PM");
        Appointment appointmentToAdd = new Appointment(personId, appointmentTime);
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(INDEX_FIRST_PERSON, appointmentTime);

        String expectedMessage = String.format(AddAppointmentCommand.MESSAGE_SUCCESS,
                Messages.formatAppointment(appointmentToAdd, firstPerson.getName().fullName));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addAppointment(appointmentToAdd);

        assertCommandSuccess(addAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateAppointment_throwsCommandException() {
        Person firstPerson = model.getAddressBook().getPersonList().get(0);
        UUID personId = firstPerson.getId();
        AppointmentTime appointmentTime = new AppointmentTime("10/04/2024 2PM-3PM");
        Appointment appointmentToAdd = new Appointment(personId, appointmentTime);
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(INDEX_FIRST_PERSON, appointmentTime);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addAppointment(appointmentToAdd);

        assertThrows(CommandException.class, AddAppointmentCommand.MESSAGE_DUPLICATE_APPOINTMENT, () -> addAppointmentCommand.execute(expectedModel));
    }

    @Test
    public void addAppointment_success() {
        AppointmentTime appointmentTime = new AppointmentTime("10/04/2024 2PM-3PM");
        Index index = Index.fromOneBased(10);
        assertTrue(new AddAppointmentCommand(index, appointmentTime) instanceof AddAppointmentCommand);
    }

    @Test
    public void equals() {
        AppointmentTime appointmentTimeOne = new AppointmentTime("10/04/2024 2PM-3PM");
        AppointmentTime appointmentTimeOneDuplicate = new AppointmentTime("10/04/2024 2PM-3PM");
        AppointmentTime appointmentTimeTwo = new AppointmentTime("10/05/2024 2PM-3PM");

        Index indexOne = Index.fromOneBased(1);
        Index indexOneDuplicate = Index.fromOneBased(1);
        Index indexTwo = Index.fromOneBased(2);

        AddAppointmentCommand addAppointmentCommand =
                new AddAppointmentCommand(indexOne, appointmentTimeOne);

        // Same command
        assertTrue(addAppointmentCommand.equals(addAppointmentCommand));

        AddAppointmentCommand addAppointmentCommandDuplicate =
                new AddAppointmentCommand(indexOne, appointmentTimeOne);
        assertTrue(addAppointmentCommand.equals(addAppointmentCommandDuplicate));

        AddAppointmentCommand differentObjectAppointmentCommand =
                new AddAppointmentCommand(indexOneDuplicate, appointmentTimeOneDuplicate);
        assertTrue(addAppointmentCommand.equals(differentObjectAppointmentCommand));

        // Same index, different date
        AddAppointmentCommand differentDateAppointmentCommand =
                new AddAppointmentCommand(indexOne, appointmentTimeTwo);
        assertFalse(addAppointmentCommand.equals(differentDateAppointmentCommand));

        AddAppointmentCommand differentDateObjectAppointmentCommand =
                new AddAppointmentCommand(indexOne, appointmentTimeOneDuplicate);
        assertTrue(addAppointmentCommand.equals(differentDateObjectAppointmentCommand));

        // Different index, same date
        AddAppointmentCommand differentIndexAppointmentCommand =
                new AddAppointmentCommand(indexTwo, appointmentTimeOne);
        assertFalse(addAppointmentCommand.equals(differentIndexAppointmentCommand));

        AddAppointmentCommand differentIndexObjectAppointmentCommand =
                new AddAppointmentCommand(indexOneDuplicate, appointmentTimeOne);
        assertTrue(addAppointmentCommand.equals(differentIndexObjectAppointmentCommand));

        // Different index, different date
        AddAppointmentCommand differentAppointmentCommand =
                new AddAppointmentCommand(indexTwo, appointmentTimeTwo);
        assertFalse(addAppointmentCommand.equals(differentAppointmentCommand));

        // Other class
        Object obj = new Object();
        assertFalse(addAppointmentCommand.equals(obj));
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        AppointmentTime appointmentTime = new AppointmentTime("10/04/2024 2PM-3PM");
        assertThrows(NullPointerException.class, () -> new AddAppointmentCommand(null, appointmentTime));
    }

    @Test
    public void constructor_nullAppointmentTime_throwsNullPointerException() {
        Index index = Index.fromOneBased(1);
        assertThrows(NullPointerException.class, () -> new AddAppointmentCommand(index, null));
    }

    @Test
    public void constructor_allFieldsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAppointmentCommand(null, null));
    }

    @Test
    public void toStringMethod() {
        AppointmentTime appointmentTime = new AppointmentTime("10/04/2024 2PM-3PM");
        Index index = Index.fromOneBased(1);
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(index, appointmentTime);
        String expected = AddAppointmentCommand.class.getCanonicalName() + "{index=" + index
                + ", appointmentTime=" + appointmentTime + "}";
        assertEquals(expected, addAppointmentCommand.toString());
    }
}
