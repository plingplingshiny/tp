package seedu.address.model.intention;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Intention in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidIntentionName(String)}
 */
public class Intention {

    public static final String MESSAGE_CONSTRAINTS = "Intention must be 'sell' or 'rent'";
    public static final String VALIDATION_REGEX = "sell|rent"; // case sensitive

    public final String intentionName;

    /**
     * Constructs a {@code Intention}.
     *
     * @param intentionName A valid intention name.
     */
    public Intention(String intentionName) {
        requireNonNull(intentionName);
        checkArgument(isValidIntentionName(intentionName), MESSAGE_CONSTRAINTS);
        this.intentionName = intentionName.toLowerCase(); // normalize to lowercase
    }

    /**
     * Returns true if a given string is a valid intention name.
     */
    public static boolean isValidIntentionName(String test) {
        return test.toLowerCase().matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Intention)) {
            return false;
        }

        Intention otherIntention = (Intention) other;
        return intentionName.equals(otherIntention.intentionName);
    }

    @Override
    public int hashCode() {
        return intentionName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + intentionName + ']';
    }

}
