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
        new PersonContainsKeywordsPredicate(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());
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
                Collections.emptyList());
        assertTrue(predicate.test(person));
    }

    @Test
    public void equals_sameValidLists_returnsTrue() {
        PersonContainsKeywordsPredicate a = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"),
                Arrays.asList("gmail"), Arrays.asList("street"),
                Arrays.asList("500000"), Arrays.asList("hdb"),
                Arrays.asList("buy"));
        PersonContainsKeywordsPredicate b = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"),
                Arrays.asList("gmail"), Arrays.asList("street"),
                Arrays.asList("500000"), Arrays.asList("hdb"),
                Arrays.asList("buy"));
        assertTrue(a.equals(b));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        PersonContainsKeywordsPredicate a = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"),
                Arrays.asList("gmail"), Arrays.asList("street"),
                Arrays.asList("500000"), Arrays.asList("hdb"),
                Arrays.asList("buy"));
        PersonContainsKeywordsPredicate b = new PersonContainsKeywordsPredicate(
                Arrays.asList("bob"), Arrays.asList("9999"),
                Arrays.asList("yahoo"), Arrays.asList("road"),
                Arrays.asList("700000"), Arrays.asList("condo"),
                Arrays.asList("sell"));
        assertFalse(a.equals(b));
    }

    // === Base case: all fields equal ===
    @Test
    public void equals_allFieldsEqual_returnsTrue() {
        PersonContainsKeywordsPredicate a = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"), Arrays.asList("gmail"),
                Arrays.asList("street"), Arrays.asList("500000"),
                Arrays.asList("hdb"), Arrays.asList("buy"));

        PersonContainsKeywordsPredicate b = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"), Arrays.asList("gmail"),
                Arrays.asList("street"), Arrays.asList("500000"),
                Arrays.asList("hdb"), Arrays.asList("buy"));

        assertTrue(a.equals(b));
    }

    // === Price equality tests ===
    @Test
    public void equals_differentPriceKeywords_returnsFalse() {
        PersonContainsKeywordsPredicate a = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"), Arrays.asList("gmail"),
                Arrays.asList("street"), Arrays.asList("500000"),
                Arrays.asList("hdb"), Arrays.asList("buy"));

        PersonContainsKeywordsPredicate b = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"), Arrays.asList("gmail"),
                Arrays.asList("street"), Arrays.asList("700000"),
                Arrays.asList("hdb"), Arrays.asList("buy"));

        assertFalse(a.equals(b));
    }

    @Test
    public void equals_priceKeywordsDifferentFormatting_returnsTrue() {
        PersonContainsKeywordsPredicate a = new PersonContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(),
                Arrays.asList("1000.00"), Collections.emptyList(), Collections.emptyList());

        PersonContainsKeywordsPredicate b = new PersonContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(),
                Arrays.asList("1,000"), Collections.emptyList(), Collections.emptyList());

        assertTrue(a.equals(b)); // same numeric value
    }

    // === Property Type equality tests ===
    @Test
    public void equals_differentPropertyTypeKeywords_returnsFalse() {
        PersonContainsKeywordsPredicate a = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"), Arrays.asList("gmail"),
                Arrays.asList("street"), Arrays.asList("500000"),
                Arrays.asList("hdb"), Arrays.asList("buy"));

        PersonContainsKeywordsPredicate b = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"), Arrays.asList("gmail"),
                Arrays.asList("street"), Arrays.asList("500000"),
                Arrays.asList("condo"), Arrays.asList("buy"));

        assertFalse(a.equals(b));
    }

    @Test
    public void equals_propertyTypeDifferentCase_returnsTrue() {
        PersonContainsKeywordsPredicate a = new PersonContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Arrays.asList("HDB"), Collections.emptyList());

        PersonContainsKeywordsPredicate b = new PersonContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Arrays.asList("hdb"), Collections.emptyList());

        assertTrue(a.equals(b));
    }

    // === Intention equality tests ===
    @Test
    public void equals_differentIntentionKeywords_returnsFalse() {
        PersonContainsKeywordsPredicate a = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"), Arrays.asList("gmail"),
                Arrays.asList("street"), Arrays.asList("500000"),
                Arrays.asList("hdb"), Arrays.asList("buy"));

        PersonContainsKeywordsPredicate b = new PersonContainsKeywordsPredicate(
                Arrays.asList("alex"), Arrays.asList("9123"), Arrays.asList("gmail"),
                Arrays.asList("street"), Arrays.asList("500000"),
                Arrays.asList("hdb"), Arrays.asList("sell"));

        assertFalse(a.equals(b));
    }

    @Test
    public void equals_intentionKeywordsDifferentCase_returnsTrue() {
        PersonContainsKeywordsPredicate a = new PersonContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(), Arrays.asList("Buy"));

        PersonContainsKeywordsPredicate b = new PersonContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(), Arrays.asList("buy"));

        assertTrue(a.equals(b)); // case-insensitive
    }

    @Test
    public void test_priceMatch_returnsTrue() {
        Person person = new PersonBuilder().withPrice("1000.00").build();
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(),
                Arrays.asList("1000"), // keyword that should match
                Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_priceWithinRange_returnsTrue() {
        Person person = new PersonBuilder().withPrice("2500").build();
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(),
                Arrays.asList("2000-3000"), // price range keyword
                Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(person)); // 2500 is within 2000–3000
    }

    @Test
    public void test_priceOutsideRange_returnsFalse() {
        Person person = new PersonBuilder().withPrice("1500").build();
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(),
                Arrays.asList("2000-3000"), // 1500 below lower bound
                Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(person));
    }

    @Test
    public void test_priceOnBoundary_returnsTrue() {
        Person person = new PersonBuilder().withPrice("2000").build();
        PersonContainsKeywordsPredicate predicateLower = new PersonContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(),
                Arrays.asList("2000-3000"),
                Collections.emptyList(), Collections.emptyList());
        assertTrue(predicateLower.test(person)); // 2000 == lower bound

        person = new PersonBuilder().withPrice("3000").build();
        assertTrue(predicateLower.test(person)); // 3000 == upper bound
    }

    @Test
    public void test_priceMalformedRange_returnsFalse() {
        // malformed ranges like "2000-" or "-3000" should not crash and should not match
        Person person = new PersonBuilder().withPrice("2000").build();

        PersonContainsKeywordsPredicate predicateMissingUpper = new PersonContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(),
                Arrays.asList("2000-"),
                Collections.emptyList(), Collections.emptyList());
        assertFalse(predicateMissingUpper.test(person));

        PersonContainsKeywordsPredicate predicateMissingLower = new PersonContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(),
                Arrays.asList("-3000"),
                Collections.emptyList(), Collections.emptyList());
        assertFalse(predicateMissingLower.test(person));
    }

    @Test
    public void test_priceRangeNonNumeric_returnsFalse() {
        Person person = new PersonBuilder().withPrice("2000").build();
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(),
                Arrays.asList("abc-def"), // non-numeric
                Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(person));
    }

    @Test
    public void test_priceExactMatchFormattingVariants_returnsTrue() {
        Person person = new PersonBuilder().withPrice("1,000.00").build();
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(),
                Arrays.asList("1000", "1,000.00", "1000.0"),
                Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(person)); // normalization should handle commas/decimals
    }
}
