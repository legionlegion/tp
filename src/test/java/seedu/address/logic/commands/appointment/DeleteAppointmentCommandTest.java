package seedu.address.logic.commands.appointment;

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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class DeleteAppointmentCommandTest {

    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_appointmentDeletedByModel_addAppointmentSuccessful() throws Exception {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        Person firstPerson = model.getAddressBook().getPersonList().get(0);
        UUID personId = firstPerson.getId();
        AppointmentTime appointmentTime = new AppointmentTime("10/04/2024 2PM-3PM");
        Appointment appointmentToDelete = new Appointment(personId, appointmentTime);
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(INDEX_FIRST_PERSON, appointmentTime);
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(Index.fromOneBased(1));

        addAppointmentCommand.execute(model);

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                Messages.formatAppointment(appointmentToDelete, firstPerson.getName().fullName));

        assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noAppointment_throwsNullPointerException() {
        Index index = Index.fromOneBased(1);
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(index);
        assertThrows(CommandException.class, () -> deleteAppointmentCommand.execute(model));
    }

    @Test
    public void equals() {
        Index indexOne = Index.fromOneBased(1);
        Index indexOneDuplicate = Index.fromOneBased(1);

        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(indexOne);

        // Same command
        assertTrue(deleteAppointmentCommand.equals(deleteAppointmentCommand));

        DeleteAppointmentCommand deleteAppointmentCommandDuplicate = new DeleteAppointmentCommand(indexOne);
        assertTrue(deleteAppointmentCommand.equals(deleteAppointmentCommandDuplicate));

        DeleteAppointmentCommand differentObjectDeleteCommand = new DeleteAppointmentCommand(indexOneDuplicate);
        assertTrue(deleteAppointmentCommand.equals(differentObjectDeleteCommand));

        // Other class
        Object obj = new Object();
        assertFalse(deleteAppointmentCommand.equals(obj));
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteAppointmentCommand(null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        Index index = Index.fromOneBased(1);
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(index);
        assertThrows(NullPointerException.class, () -> deleteAppointmentCommand.execute(null));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(index);
        String expected = DeleteAppointmentCommand.class.getCanonicalName() + "{targetIndex=" + index + "}";
        assertEquals(expected, deleteAppointmentCommand.toString());
    }
}
