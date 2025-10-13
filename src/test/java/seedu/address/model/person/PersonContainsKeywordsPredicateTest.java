package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import seedu.address.testutil.PersonBuilder;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

/**
 * Integration tests for {@code PersonContainsKeywordsPredicate}.
 */
public class PersonContainsKeywordsPredicateTest {
    @Test
    public void equals_differentType_returnsFalse() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                Arrays.asList("alice"),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());
        assertFalse(predicate.equals("not a predicate"));
    }

    @Test
    public void test_phoneMatch_returnsTrue() {
        Person person = new PersonBuilder().withPhone("91234567").build();
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                Collections.emptyList(),
                Arrays.asList("1234"),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());
        assertTrue(predicate.test(person)); // phoneMatches = true
    }

    @Test
    public void test_emailMatch_returnsTrue() {
        Person person = new PersonBuilder().withEmail("alex@gmail.com").build();
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                Collections.emptyList(),
                Collections.emptyList(),
                Arrays.asList("gmail"),
                Collections.emptyList(),
                Collections.emptyList());
        assertTrue(predicate.test(person)); // emailMatches = true
    }

    @Test
    public void test_addressMatch_returnsTrue() {
        Person person = new PersonBuilder().withAddress("Clementi Ave 3").build();
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Arrays.asList("Clementi"),
                Collections.emptyList());
        assertTrue(predicate.test(person)); // addressMatches = true
    }

    @Test
    public void equals_sameKeywords_returnsTrue() {
        PersonContainsKeywordsPredicate a = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"),
                Arrays.asList("gmail"), Arrays.asList("street"), Arrays.asList("friend"));
        PersonContainsKeywordsPredicate b = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"),
                Arrays.asList("gmail"), Arrays.asList("street"), Arrays.asList("friend"));
        assertTrue(a.equals(b)); // all true
    }

    @Test
    public void equals_differentPhone_returnsFalse() {
        PersonContainsKeywordsPredicate a = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"),
                Arrays.asList("gmail"), Arrays.asList("street"), Arrays.asList("friend"));
        PersonContainsKeywordsPredicate b = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9999"), // different phone
                Arrays.asList("gmail"), Arrays.asList("street"), Arrays.asList("friend"));
        assertFalse(a.equals(b)); // phoneKeywords false, others true
    }

    @Test
    public void equals_differentEmail_returnsFalse() {
        PersonContainsKeywordsPredicate a = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"),
                Arrays.asList("gmail"), Arrays.asList("street"), Arrays.asList("friend"));
        PersonContainsKeywordsPredicate b = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"),
                Arrays.asList("yahoo"), Arrays.asList("street"), Arrays.asList("friend"));
        assertFalse(a.equals(b)); // emailKeywords false
    }

    @Test
    public void equals_differentAddress_returnsFalse() {
        PersonContainsKeywordsPredicate a = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"),
                Arrays.asList("gmail"), Arrays.asList("street"), Arrays.asList("friend"));
        PersonContainsKeywordsPredicate b = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"),
                Arrays.asList("gmail"), Arrays.asList("road"), Arrays.asList("friend"));
        assertFalse(a.equals(b)); // addressKeywords false
    }

    @Test
    public void equals_differentTag_returnsFalse() {
        PersonContainsKeywordsPredicate a = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"),
                Arrays.asList("gmail"), Arrays.asList("street"), Arrays.asList("friend"));
        PersonContainsKeywordsPredicate b = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"),
                Arrays.asList("gmail"), Arrays.asList("street"), Arrays.asList("colleague"));
        assertFalse(a.equals(b)); // tagKeywords false
    }


}
