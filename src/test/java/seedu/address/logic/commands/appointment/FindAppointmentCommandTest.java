package seedu.address.logic.commands.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.AppointmentContainsKeywordsPredicate;
import seedu.address.testutil.TypicalPersons;

public class FindAppointmentCommandTest {
    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_nullModel_throwsException() {
        ArrayList<String> keyword = new ArrayList<>();
        keyword.add("TEST");
        Function<Model, AppointmentContainsKeywordsPredicate> predicate = AppointmentContainsKeywordsPredicate.build(keyword);
        FindAppointmentCommand findAppointmentCommand = new FindAppointmentCommand(predicate);

        assertThrows(NullPointerException.class, () -> findAppointmentCommand.execute(null));
    }

    @Test
    public void equals() {
        ArrayList<String> keywordOne = new ArrayList<>();
        keywordOne.add("TEST");
        Function<Model, AppointmentContainsKeywordsPredicate> predicateOne = AppointmentContainsKeywordsPredicate.build(keywordOne);

        ArrayList<String> keywordTwo = new ArrayList<>();
        keywordTwo.add("TSET");
        Function<Model, AppointmentContainsKeywordsPredicate> predicateTwo = AppointmentContainsKeywordsPredicate.build(keywordOne);

        FindAppointmentCommand findAppointmentCommandOne = new FindAppointmentCommand(predicateOne);
        FindAppointmentCommand findAppointmentCommandTwo = new FindAppointmentCommand(predicateTwo);

        // Same command
        assertTrue(findAppointmentCommandOne.equals(findAppointmentCommandOne));

        FindAppointmentCommand duplicateOne = new FindAppointmentCommand(predicateOne);
        assertTrue(findAppointmentCommandOne.equals(duplicateOne));

        ArrayList<String> keywordDuplicate = new ArrayList<>();
        keywordDuplicate.add("TEST");
        Function<Model, AppointmentContainsKeywordsPredicate> predicateDuplicate = AppointmentContainsKeywordsPredicate.build(keywordDuplicate);
        FindAppointmentCommand duplicateTwo = new FindAppointmentCommand(predicateDuplicate);
        assertFalse(findAppointmentCommandTwo.equals(duplicateTwo));

        // Non-equal
        assertFalse(findAppointmentCommandOne.equals(findAppointmentCommandTwo));

        // Null
        assertFalse(findAppointmentCommandOne.equals(null));

        // Other class
        Object obj = new Object();
        assertFalse(findAppointmentCommandOne.equals(obj));
    }

    @Test
    public void toStringMethod() {
        ArrayList<String> keyword = new ArrayList<>();
        keyword.add("TEST");
        Function<Model, AppointmentContainsKeywordsPredicate> predicate = AppointmentContainsKeywordsPredicate.build(keyword);
        FindAppointmentCommand findAppointmentCommand = new FindAppointmentCommand(predicate);

        String expected = FindAppointmentCommand.class.getCanonicalName() + "{predicateFunction=" + predicate + "}";
        assertEquals(expected, findAppointmentCommand.toString());
    }
}
