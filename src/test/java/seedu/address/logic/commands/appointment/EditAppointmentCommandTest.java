package seedu.address.logic.commands.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class EditAppointmentCommandTest {
    // TODO THIS ENTIRE THING
    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void empty_getAppointmentTime_editAppointmentDescriptor() {
        EditAppointmentCommand.EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentCommand.EditAppointmentDescriptor();
        assertEquals(Optional.empty(), editAppointmentDescriptor.getAppointmentTime());
    }

    @Test
    public void full_getAppointmentTime_editAppointmentDescriptor() {
        EditAppointmentCommand.EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentCommand.EditAppointmentDescriptor();
        editAppointmentDescriptor.setAppointmentTime(new AppointmentTime("10/04/2050 2PM-3PM"));
        assertEquals(Optional.of(new AppointmentTime("10/04/2050 2PM-3PM")), editAppointmentDescriptor.getAppointmentTime());
    }

    @Test
    public void noFieldEdited_editAppointmentDescriptor() {
        EditAppointmentCommand.EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentCommand.EditAppointmentDescriptor();
        assertFalse(editAppointmentDescriptor.isAnyFieldEdited());
    }

    @Test
    public void oneFieldEdited_editAppointmentDescriptor() {
        EditAppointmentCommand.EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentCommand.EditAppointmentDescriptor();
        editAppointmentDescriptor.setAppointmentTime(new AppointmentTime("10/04/2050 2PM-3PM"));
        assertTrue(editAppointmentDescriptor.isAnyFieldEdited());
    }

    @Test
    public void equals_editAppointmentDescriptor() {
        EditAppointmentCommand.EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentCommand.EditAppointmentDescriptor();
        AppointmentTime appointmentTime = new AppointmentTime("10/04/2050 2PM-3PM");
        editAppointmentDescriptor.setAppointmentTime(appointmentTime);

        EditAppointmentCommand.EditAppointmentDescriptor emptyEditAppointmentDescriptor = new EditAppointmentCommand.EditAppointmentDescriptor();

        // Same command
        assertTrue(editAppointmentDescriptor.equals(editAppointmentDescriptor));
        assertTrue(emptyEditAppointmentDescriptor.equals(emptyEditAppointmentDescriptor));

        EditAppointmentCommand.EditAppointmentDescriptor newEditAppointmentDescriptor = new EditAppointmentCommand.EditAppointmentDescriptor();
        newEditAppointmentDescriptor.setAppointmentTime(appointmentTime);
        assertTrue(editAppointmentDescriptor.equals(newEditAppointmentDescriptor));

        EditAppointmentCommand.EditAppointmentDescriptor newEditAppointmentDescriptor2 = new EditAppointmentCommand.EditAppointmentDescriptor();
        AppointmentTime newAppointmentTime = new AppointmentTime("10/04/2050 2PM-3PM");
        newEditAppointmentDescriptor2.setAppointmentTime(newAppointmentTime);
        assertTrue(editAppointmentDescriptor.equals(newEditAppointmentDescriptor2));

        // Different commands
        AppointmentTime differentAppointmentTime = new AppointmentTime("10/04/2051 2PM-3PM");
        EditAppointmentCommand.EditAppointmentDescriptor differentEditAppointmentDescriptor = new EditAppointmentCommand.EditAppointmentDescriptor();
        differentEditAppointmentDescriptor.setAppointmentTime(differentAppointmentTime);
        assertFalse(editAppointmentDescriptor.equals(differentEditAppointmentDescriptor));

        assertFalse(editAppointmentDescriptor.equals(emptyEditAppointmentDescriptor));

        // Equals null
        assertFalse(editAppointmentDescriptor.equals(null));
        assertFalse(emptyEditAppointmentDescriptor.equals(null));

        // Equals other class
        Object obj = new Object();
        assertFalse(editAppointmentDescriptor.equals(obj));
        assertFalse(emptyEditAppointmentDescriptor.equals(obj));
    }

    @Test
    public void addAppointment_success() {
        AppointmentTime appointmentTime = new AppointmentTime("10/04/2024 2PM-3PM");
        Index index = Index.fromOneBased(10);
        assertTrue(new AddAppointmentCommand(index, appointmentTime) instanceof AddAppointmentCommand);
    }

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
    public void execute_invalidPersonIndex_throwsCommandException() {
        Person firstPerson = model.getAddressBook().getPersonList().get(0);
        UUID personId = firstPerson.getId();
        AppointmentTime appointmentTime = new AppointmentTime("10/04/2024 2PM-3PM");
        Appointment appointmentToAdd = new Appointment(personId, appointmentTime);
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(Index.fromOneBased(10000), appointmentTime);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addAppointment(appointmentToAdd);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () -> addAppointmentCommand.execute(expectedModel));
    }

    @Test
    public void constructor_editAppointmentCommand_nullIndex_throwsNullPointerException() {
        EditAppointmentCommand.EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentCommand.EditAppointmentDescriptor();
        editAppointmentDescriptor.setAppointmentTime(new AppointmentTime("10/04/2050 2PM-3PM"));
        assertThrows(NullPointerException.class, () -> new EditAppointmentCommand(null, editAppointmentDescriptor));
    }

    @Test
    public void constructor_editAppointmentCommand_nullEditAppointmentDescriptor_throwsNullPointerException() {
        Index index = Index.fromOneBased(1);
        assertThrows(NullPointerException.class, () -> new EditAppointmentCommand(index, null));
    }

    @Test
    public void constructor_editAppointmentCommand_allFieldsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditAppointmentCommand(null, null));
    }

    @Test
    public void editAppointmentCommand_toStringMethod() {
        EditAppointmentCommand.EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentCommand.EditAppointmentDescriptor();
        editAppointmentDescriptor.setAppointmentTime(new AppointmentTime("10/04/2050 2PM-3PM"));
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(INDEX_FIRST_PERSON, editAppointmentDescriptor);

        String expected = EditAppointmentCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST_PERSON
                + ", editAppointmentDescriptor=" + editAppointmentDescriptor + "}";
        assertEquals(expected, editAppointmentCommand.toString());
    }
}
