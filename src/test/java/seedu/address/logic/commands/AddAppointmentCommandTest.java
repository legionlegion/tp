package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.appointment.AddAppointmentCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.person.AddPersonCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddAppointmentCommandTest {

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
        AppointmentTime appointmentTimeTwoDuplicate = new AppointmentTime("10/05/2024 2PM-3PM");

        Index indexOne = Index.fromOneBased(1);
        Index indexOneDuplicate = Index.fromOneBased(1);
        Index indexTwo = Index.fromOneBased(2);
        Index indexTwoDuplicate = Index.fromOneBased(2);

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
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPersonCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddPersonCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddPersonCommand.MESSAGE_SUCCESS, Messages.formatPerson(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddPersonCommand addCommand = new AddPersonCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, AddPersonCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
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

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAppointment(Appointment target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAppointment(Appointment target, Appointment editedAppointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Appointment> getFilteredAppointmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person getPersonById(UUID personId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Appointment> getSourceAppointmentList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
