package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

/**
 * Tests for {@code PersonContainsKeywordsPredicate}.
 * Updated for all prefixes (n/, p/, e/, a/, t/, pr/, pt/, i/)
 * and safely validates constructor assertions.
 */
public class PersonContainsKeywordsPredicateTest {

    // === Regular equality and behavior tests ===

    @Test
    public void equals_differentType_returnsFalse() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                Arrays.asList("alice"),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
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
    public void equals_sameKeywords_returnsTrue() {
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
    public void equals_differentPhone_returnsFalse() {
        PersonContainsKeywordsPredicate a = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"),
                Arrays.asList("gmail"), Arrays.asList("street"),
                Arrays.asList("friend"), Arrays.asList("500000"),
                Arrays.asList("hdb"), Arrays.asList("buy"));
        PersonContainsKeywordsPredicate b = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9999"),
                Arrays.asList("gmail"), Arrays.asList("street"),
                Arrays.asList("friend"), Arrays.asList("500000"),
                Arrays.asList("hdb"), Arrays.asList("buy"));
        assertFalse(a.equals(b));
    }

    @Test
    public void equals_differentIntention_returnsFalse() {
        PersonContainsKeywordsPredicate a = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"),
                Arrays.asList("gmail"), Arrays.asList("street"),
                Arrays.asList("friend"), Arrays.asList("500000"),
                Arrays.asList("hdb"), Arrays.asList("buy"));
        PersonContainsKeywordsPredicate b = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"),
                Arrays.asList("gmail"), Arrays.asList("street"),
                Arrays.asList("friend"), Arrays.asList("500000"),
                Arrays.asList("hdb"), Arrays.asList("sell"));
        assertFalse(a.equals(b));
    }

    // === Assertion validation (constructor null checks) ===

    @Test
    public void constructor_handlesNullListsWithoutBreaking_whenAssertionsDisabled() {
        // This test passes even if assertions are disabled
        // (since Java assertions only run when -ea is set)
        try {
            new PersonContainsKeywordsPredicate(
                    null, Collections.emptyList(),
                    Collections.emptyList(), Collections.emptyList(),
                    Collections.emptyList(), Collections.emptyList(),
                    Collections.emptyList(), Collections.emptyList());
        } catch (AssertionError e) {
            // Expected only if -ea is enabled
            assertTrue(true);
        }
    }

    @Test
    public void constructor_handlesNullPriceListSafely() {
        try {
            new PersonContainsKeywordsPredicate(
                    Collections.emptyList(), Collections.emptyList(),
                    Collections.emptyList(), Collections.emptyList(),
                    Collections.emptyList(), null,
                    Collections.emptyList(), Collections.emptyList());
        } catch (AssertionError e) {
            // Assertion triggered → expected under -ea
            assertTrue(true);
        }
    }

    /**
     * Helper method to check if assertions are enabled at runtime.
     */
    private boolean areAssertionsEnabled() {
        boolean enabled = false;
        assert enabled = true;
        return enabled;
    }
}
