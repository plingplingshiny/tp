package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

/**
 * Tests for {@code PersonContainsKeywordsPredicate}.
 * Ensures all constructor assertions pass by supplying valid non-null lists.
 */
public class PersonContainsKeywordsPredicateTest {

    @Test
    public void constructor_allNonNullLists_assertionsPass() {
        // This should not throw AssertionError since all lists are non-null
        new PersonContainsKeywordsPredicate(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());
        // If we reach here, all assertions have passed
        assertTrue(true);
    }

    @Test
    public void test_nameMatch_returnsTrue() {
        Person person = new PersonBuilder().withName("Alice Pauline").build();
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                Arrays.asList("Alice"),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_phoneMatch_returnsTrue() {
        Person person = new PersonBuilder().withPhone("91234567").build();
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                Collections.emptyList(),
                Arrays.asList("1234"),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_emailMatch_returnsTrue() {
        Person person = new PersonBuilder().withEmail("alex@gmail.com").build();
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                Collections.emptyList(),
                Collections.emptyList(),
                Arrays.asList("gmail"),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_addressMatch_returnsTrue() {
        Person person = new PersonBuilder().withAddress("Clementi Ave 3").build();
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Arrays.asList("Clementi"),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());
        assertTrue(predicate.test(person));
    }

    @Test
    public void equals_sameValidLists_returnsTrue() {
        PersonContainsKeywordsPredicate a = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"),
                Arrays.asList("gmail"), Arrays.asList("street"),
                Arrays.asList("friend"), Arrays.asList("500000"),
                Arrays.asList("hdb"), Arrays.asList("buy"));
        PersonContainsKeywordsPredicate b = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"),
                Arrays.asList("gmail"), Arrays.asList("street"),
                Arrays.asList("friend"), Arrays.asList("500000"),
                Arrays.asList("hdb"), Arrays.asList("buy"));
        assertTrue(a.equals(b));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        PersonContainsKeywordsPredicate a = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"),
                Arrays.asList("gmail"), Arrays.asList("street"),
                Arrays.asList("friend"), Arrays.asList("500000"),
                Arrays.asList("hdb"), Arrays.asList("buy"));
        PersonContainsKeywordsPredicate b = new PersonContainsKeywordsPredicate(
                Arrays.asList("bob"), Arrays.asList("9999"),
                Arrays.asList("yahoo"), Arrays.asList("road"),
                Arrays.asList("colleague"), Arrays.asList("700000"),
                Arrays.asList("condo"), Arrays.asList("sell"));
        assertFalse(a.equals(b));
    }
}
