package seedu.address.model.intention;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IntentionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Intention(null));
    }

    @Test
    public void constructor_invalidIntention_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Intention(""));
        assertThrows(IllegalArgumentException.class, () -> new Intention("buy"));
        assertThrows(IllegalArgumentException.class, () -> new Intention(" sell"));
    }

    @Test
    public void constructor_validIntention_normalizesToLowercase() {
        Intention sellUpper = new Intention("SELL");
        Assertions.assertEquals("sell", sellUpper.intentionName);

        Intention rentMixed = new Intention("ReNt");
        Assertions.assertEquals("rent", rentMixed.intentionName);
    }

    @Test
    public void isValidIntentionName() {
        // null intention name
        assertThrows(NullPointerException.class, () -> Intention.isValidIntentionName(null));

        // invalid intention names
        Assertions.assertFalse(Intention.isValidIntentionName("")); // empty
        Assertions.assertFalse(Intention.isValidIntentionName("buy")); // not allowed
        Assertions.assertFalse(Intention.isValidIntentionName(" sell")); // leading space not trimmed here

        // valid intention names (case-insensitive)
        Assertions.assertTrue(Intention.isValidIntentionName("sell"));
        Assertions.assertTrue(Intention.isValidIntentionName("rent"));
        Assertions.assertTrue(Intention.isValidIntentionName("SELL"));
        Assertions.assertTrue(Intention.isValidIntentionName("ReNt"));
    }

    @Test
    public void equalsAndHashCode() {
        Intention sell1 = new Intention("sell");
        Intention sell2 = new Intention("SELL");
        Intention rent = new Intention("rent");

        // equality and normalization
        Assertions.assertEquals(sell1, sell2);
        Assertions.assertEquals(sell1.hashCode(), sell2.hashCode());

        // inequality
        Assertions.assertNotEquals(sell1, rent);
        Assertions.assertNotEquals(sell1, 1);
    }

    @Test
    public void toStringMethod() {
        Intention sell = new Intention("sell");
        Assertions.assertEquals("[sell]", sell.toString());
    }
}

