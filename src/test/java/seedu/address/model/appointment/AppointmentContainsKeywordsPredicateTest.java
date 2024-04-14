package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalAppointments.APPT1;
import seedu.address.model.Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import seedu.address.model.ModelManager;
import seedu.address.testutil.TypicalPersons;

public class AppointmentContainsKeywordsPredicateTest {

    private AppointmentTime appointmentTime = new AppointmentTime("10/02/2024 11am-2pm");
    private Appointment appointment = new Appointment(ALICE.getId(), appointmentTime);
    private ModelManager modelManager = new ModelManager();

    @Test
    public void equals() {
        modelManager.addPerson(ALICE);
        modelManager.addAppointment(appointment);
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");
        Function<Model, AppointmentContainsKeywordsPredicate> predicateFunction1 = AppointmentContainsKeywordsPredicate
                .build(firstPredicateKeywordList);
        AppointmentContainsKeywordsPredicate firstPredicate = predicateFunction1.apply(modelManager);
        Function<Model, AppointmentContainsKeywordsPredicate> predicateFunction2 = AppointmentContainsKeywordsPredicate
                .build(secondPredicateKeywordList);
        AppointmentContainsKeywordsPredicate secondPredicate = predicateFunction2.apply(modelManager);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        Function<Model, AppointmentContainsKeywordsPredicate> predicateFunction1Copy = AppointmentContainsKeywordsPredicate
                .build(firstPredicateKeywordList);
        AppointmentContainsKeywordsPredicate firstPredicateCopy = predicateFunction1Copy.apply(modelManager);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_appointmentContainsKeywords_returnsTrue() {
        modelManager.addPerson(ALICE);
        modelManager.addAppointment(appointment);
        // One keyword
        Function<Model, AppointmentContainsKeywordsPredicate> predicateFunction1 = AppointmentContainsKeywordsPredicate
                .build(Collections.singletonList("Alice"));
        AppointmentContainsKeywordsPredicate predicate = predicateFunction1.apply(modelManager);
        assertTrue(predicate.test(appointment));

        // Multiple keywords
        Function<Model, AppointmentContainsKeywordsPredicate> predicateFunction2 = AppointmentContainsKeywordsPredicate
                .build(Arrays.asList("Alice", "Bob"));
        predicate = predicateFunction2.apply(modelManager);
        assertTrue(predicate.test(appointment));

        // Mixed-case keywords
        Function<Model, AppointmentContainsKeywordsPredicate> predicateFunction3 = AppointmentContainsKeywordsPredicate
                .build(Arrays.asList("aLIce", "bOB"));
        predicate = predicateFunction3.apply(modelManager);
        assertTrue(predicate.test(appointment));

        // By UUID
        Function<Model, AppointmentContainsKeywordsPredicate> predicateFunction4 = AppointmentContainsKeywordsPredicate
                .build(Arrays.asList(TypicalPersons.ALICE.getId().toString()));
        predicate = predicateFunction4.apply(modelManager);
        assertTrue(predicate.test(appointment));
    }

    @Test
    public void test_appointmentDoesNotContainKeywords_returnsFalse() {
        modelManager.addPerson(ALICE);
        modelManager.addAppointment(appointment);
        // Zero keywords
        Function<Model, AppointmentContainsKeywordsPredicate> predicateFunction1 = AppointmentContainsKeywordsPredicate
                .build(Collections.emptyList());
        AppointmentContainsKeywordsPredicate predicate = predicateFunction1.apply(modelManager);
        assertFalse(predicate.test(APPT1));

        // Non-matching keyword
        Function<Model, AppointmentContainsKeywordsPredicate> predicateFunction2 = AppointmentContainsKeywordsPredicate
                .build(Arrays.asList("Carol"));
        predicate = predicateFunction2.apply(modelManager);
        assertFalse(predicate.test(appointment));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        Function<Model, AppointmentContainsKeywordsPredicate> predicateFunction1 = AppointmentContainsKeywordsPredicate
                .build(keywords);
        AppointmentContainsKeywordsPredicate predicate = predicateFunction1.apply(modelManager);

        String expected = AppointmentContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
