package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Simple manager to hold pending confirmation state (persons to delete and names not found).
 */
public class ConfirmationManager {
    private static List<Person> personsToDelete = new ArrayList<>();
    private static List<Name> notFoundNames = new ArrayList<>();
    private static boolean hasPending = false;

    /**
     * Set a pending confirmation with persons to delete and names not found.
     */
    public static void setPending(List<Person> persons, List<Name> notFound) {
        personsToDelete = new ArrayList<>(persons);
        notFoundNames = new ArrayList<>(notFound);
        hasPending = true;
    }

    /**
     * Returns true if there is a pending confirmation waiting for user response.
     */
    public static boolean hasPending() {
        return hasPending;
    }

    /**
     * Returns a copy of the list of persons pending deletion.
     */
    public static List<Person> getPersonsToDelete() {
        return new ArrayList<>(personsToDelete);
    }

    /**
     * Returns a copy of the not-found names associated with the pending confirmation.
     */
    public static List<Name> getNotFoundNames() {
        return new ArrayList<>(notFoundNames);
    }

    /**
     * Clears any pending confirmation state.
     */
    public static void clearPending() {
        personsToDelete.clear();
        notFoundNames.clear();
        hasPending = false;
    }
}
