package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalPersons;

public class AllFieldsContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> keywords = new ArrayList<>();
        keywords.add("Name");
        keywords.add("911");

        List<String> keywordsDuplicate = new ArrayList<>();
        keywordsDuplicate.add("Name");
        keywordsDuplicate.add("911");

        List<String> keywordsReversed = new ArrayList<>();
        keywordsReversed.add("911");
        keywordsReversed.add("Name");

        AllFieldsContainsKeywordsPredicate allFieldsContainsKeywordsPredicate = new AllFieldsContainsKeywordsPredicate(keywords);
        AllFieldsContainsKeywordsPredicate copyPredicate = new AllFieldsContainsKeywordsPredicate(keywords);
        AllFieldsContainsKeywordsPredicate duplicatePredicate = new AllFieldsContainsKeywordsPredicate(keywordsDuplicate);
        AllFieldsContainsKeywordsPredicate reversedPredicate = new AllFieldsContainsKeywordsPredicate(keywordsReversed);

        assertTrue(allFieldsContainsKeywordsPredicate.equals(allFieldsContainsKeywordsPredicate));
        assertTrue(allFieldsContainsKeywordsPredicate.equals(copyPredicate));
        assertTrue(allFieldsContainsKeywordsPredicate.equals(duplicatePredicate));
        assertFalse(allFieldsContainsKeywordsPredicate.equals(reversedPredicate));
        assertFalse(allFieldsContainsKeywordsPredicate.equals(new Object()));
    }

    @Test
    public void test() {
        Person person = TypicalPersons.ALICE;
        List<String> nameKeyWord = new ArrayList<>();
        nameKeyWord.add("Alice");
        List<String> phoneKeyWord = new ArrayList<>();
        phoneKeyWord.add("94351253");
        List<String> addressKeyWord = new ArrayList<>();
        addressKeyWord.add("123");
        List<String> partialNameKeyWord = new ArrayList<>();
        partialNameKeyWord.add("alice");
        List<String> partialPhoneKeyWord = new ArrayList<>();
        partialPhoneKeyWord.add("9435");
        List<String> partialAddressKeyWord = new ArrayList<>();
        partialAddressKeyWord.add("Jur");

        AllFieldsContainsKeywordsPredicate testNameKeyWord = new AllFieldsContainsKeywordsPredicate(nameKeyWord);
        AllFieldsContainsKeywordsPredicate testPhoneKeyWord = new AllFieldsContainsKeywordsPredicate(phoneKeyWord);
        AllFieldsContainsKeywordsPredicate testAddressKeyWord = new AllFieldsContainsKeywordsPredicate(addressKeyWord);
        AllFieldsContainsKeywordsPredicate testPartialNameKeyWord = new AllFieldsContainsKeywordsPredicate(partialNameKeyWord);
        AllFieldsContainsKeywordsPredicate testPartialPhoneKeyWord = new AllFieldsContainsKeywordsPredicate(partialPhoneKeyWord);
        AllFieldsContainsKeywordsPredicate testPartialAddressKeyWord = new AllFieldsContainsKeywordsPredicate(partialAddressKeyWord);

        assertTrue(testNameKeyWord.test(person));
        assertTrue(testPhoneKeyWord.test(person));
        assertTrue(testAddressKeyWord.test(person));
        assertTrue(testPartialNameKeyWord.test(person));
        assertTrue(testPartialPhoneKeyWord.test(person));
        assertTrue(testPartialAddressKeyWord.test(person));
    }

}
