package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/** Represents a person's name. */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names must start with a letter and should only contain "
                    + "letters, numbers, accents, spaces, periods, apostrophes, or hyphens";

    public static final String VALIDATION_REGEX = "^[\\p{L}][\\p{L}\\p{M}\\p{N} .'-]*$";

    public final String fullName;

    /** Constructs a {@code Name}. */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
