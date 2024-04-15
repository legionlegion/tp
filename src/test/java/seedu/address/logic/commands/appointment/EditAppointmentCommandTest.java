package seedu.address.logic.commands.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.testutil.TypicalPersons;

public class EditAppointmentCommandTest {

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
    public void toString_editAppointmentDescriptor() {
        EditAppointmentCommand.EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentCommand.EditAppointmentDescriptor();
        AppointmentTime appointmentTime = new AppointmentTime("10/04/2050 2PM-3PM");
        editAppointmentDescriptor.setAppointmentTime(appointmentTime);

        String expected = EditAppointmentCommand.EditAppointmentDescriptor.class.getCanonicalName() + "{appointment time=" + appointmentTime + "}";
        assertEquals(expected, editAppointmentDescriptor.toString());
    }

    @Test
    public void emptyToString_editAppointmentDescriptor() {
        EditAppointmentCommand.EditAppointmentDescriptor emptyEditAppointmentDescriptor = new EditAppointmentCommand.EditAppointmentDescriptor();

        String expected = EditAppointmentCommand.EditAppointmentDescriptor.class.getCanonicalName() + "{appointment time=null}";
        assertEquals(expected, emptyEditAppointmentDescriptor.toString());
    }

    @Test
    public void constructorNullIndex_editAppointmentCommand_throwsNullPointerException() {
        EditAppointmentCommand.EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentCommand.EditAppointmentDescriptor();
        editAppointmentDescriptor.setAppointmentTime(new AppointmentTime("10/04/2050 2PM-3PM"));
        assertThrows(NullPointerException.class, () -> new EditAppointmentCommand(null, editAppointmentDescriptor));
    }

    @Test
    public void constructorNullEditAppointmentDescriptor_editAppointmentCommand_throwsNullPointerException() {
        Index index = Index.fromOneBased(1);
        assertThrows(NullPointerException.class, () -> new EditAppointmentCommand(index, null));
    }

    @Test
    public void constructorAllFieldsNull_editAppointmentCommand_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditAppointmentCommand(null, null));
    }

    @Test
    public void execute_null() {
        EditAppointmentCommand.EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentCommand.EditAppointmentDescriptor();
        AppointmentTime appointmentTime = new AppointmentTime("10/04/2050 2PM-3PM");
        editAppointmentDescriptor.setAppointmentTime(appointmentTime);
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(INDEX_FIRST_PERSON, editAppointmentDescriptor);

        assertThrows(CommandException.class, () -> editAppointmentCommand.execute(model));
    }

    @Test
    public void execute_wrongIndex_throwsCommandException() {
        EditAppointmentCommand.EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentCommand.EditAppointmentDescriptor();
        AppointmentTime appointmentTime = new AppointmentTime("10/04/2050 2PM-3PM");
        editAppointmentDescriptor.setAppointmentTime(appointmentTime);
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(Index.fromOneBased(10000), editAppointmentDescriptor);

        assertThrows(CommandException.class, () -> editAppointmentCommand.execute(model));
    }

    @Test
    public void execute_wrongAppointmentTime_throwsCommandException() {
        AppointmentTime originalTime = new AppointmentTime("10/04/2050 1PM-2PM");
        model.addAppointment(new Appointment(TypicalPersons.ALICE.getId(), originalTime));

        EditAppointmentCommand.EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentCommand.EditAppointmentDescriptor();
        AppointmentTime appointmentTime = new AppointmentTime("10/04/2050 3PM-2PM");
        editAppointmentDescriptor.setAppointmentTime(appointmentTime);
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(Index.fromOneBased(1), editAppointmentDescriptor);

        assertThrows(CommandException.class, () -> editAppointmentCommand.execute(model));
    }

    @Test
    public void execute_success() {
        AppointmentTime originalTime = new AppointmentTime("10/04/2050 1PM-2PM");
        model.addAppointment(new Appointment(TypicalPersons.ALICE.getId(), originalTime));

        EditAppointmentCommand.EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentCommand.EditAppointmentDescriptor();
        AppointmentTime appointmentTime = new AppointmentTime("10/04/2050 2PM-3PM");
        editAppointmentDescriptor.setAppointmentTime(appointmentTime);
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(Index.fromOneBased(1), editAppointmentDescriptor);

        Model expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        Appointment newAppointment = new Appointment(TypicalPersons.ALICE.getId(), appointmentTime);
        expectedModel.addAppointment(newAppointment);

        String expectedMessage = "Edited Appointment for Alice Pauline; Time: 10/04/2050: from 2:00PM to 3:00PM";

        assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals_editAppointmentCommand() {
        EditAppointmentCommand.EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentCommand.EditAppointmentDescriptor();
        AppointmentTime appointmentTime = new AppointmentTime("10/04/2050 2PM-3PM");
        editAppointmentDescriptor.setAppointmentTime(appointmentTime);
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(INDEX_FIRST_PERSON, editAppointmentDescriptor);

        // Same command
        assertTrue(editAppointmentCommand.equals(editAppointmentCommand));

        EditAppointmentCommand.EditAppointmentDescriptor newEditAppointmentDescriptor = new EditAppointmentCommand.EditAppointmentDescriptor();
        newEditAppointmentDescriptor.setAppointmentTime(appointmentTime);
        EditAppointmentCommand newEditAppointmentCommand = new EditAppointmentCommand(INDEX_FIRST_PERSON, newEditAppointmentDescriptor);
        assertTrue(editAppointmentCommand.equals(newEditAppointmentCommand));

        EditAppointmentCommand.EditAppointmentDescriptor newEditAppointmentDescriptor2 = new EditAppointmentCommand.EditAppointmentDescriptor();
        newEditAppointmentDescriptor2.setAppointmentTime(new AppointmentTime("10/04/2050 2PM-3PM"));
        EditAppointmentCommand newEditAppointmentCommand2 = new EditAppointmentCommand(INDEX_FIRST_PERSON, newEditAppointmentDescriptor2);
        assertTrue(editAppointmentCommand.equals(newEditAppointmentCommand2));

        // Different descriptor, same index
        EditAppointmentCommand.EditAppointmentDescriptor differentEditAppointmentDescriptor = new EditAppointmentCommand.EditAppointmentDescriptor();
        differentEditAppointmentDescriptor.setAppointmentTime(new AppointmentTime("10/04/2051 2PM-3PM"));
        EditAppointmentCommand differentDescriptorEditAppointmentCommand = new EditAppointmentCommand(INDEX_FIRST_PERSON, differentEditAppointmentDescriptor);
        assertFalse(editAppointmentCommand.equals(differentDescriptorEditAppointmentCommand));

        // Same descriptor, different index
        EditAppointmentCommand differentIndexEditAppointmentCommand = new EditAppointmentCommand(INDEX_SECOND_PERSON, editAppointmentDescriptor);
        assertFalse(editAppointmentCommand.equals(differentIndexEditAppointmentCommand));

        // Different descriptor, different index
        EditAppointmentCommand allDifferentEditAppointmentCommand = new EditAppointmentCommand(INDEX_SECOND_PERSON, differentEditAppointmentDescriptor);
        assertFalse(editAppointmentCommand.equals(allDifferentEditAppointmentCommand));

        // Equals null
        assertFalse(editAppointmentCommand.equals(null));

        // Equals other class
        Object obj = new Object();
        assertFalse(editAppointmentCommand.equals(obj));
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
