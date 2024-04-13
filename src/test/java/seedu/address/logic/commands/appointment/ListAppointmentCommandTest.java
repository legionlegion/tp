package seedu.address.logic.commands.appointment;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.AppointmentTime;

public class ListAppointmentCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() throws CommandException {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        AppointmentTime appointmentTime = new AppointmentTime("10/04/2024 2PM-3PM");
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(INDEX_FIRST_PERSON, appointmentTime);
        addAppointmentCommand.execute(model);
        addAppointmentCommand.execute(expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListAppointmentCommand(), model, ListAppointmentCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nullModel_failure() {
        ListAppointmentCommand listAppointmentCommand = new ListAppointmentCommand();
        assertThrows(NullPointerException.class, () -> listAppointmentCommand.execute(null));
    }
}
