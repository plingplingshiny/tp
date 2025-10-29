package seedu.address.logic.commands;

// Static imports
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Standard Java imports
import java.util.Arrays;
import java.util.List;

// Third party imports
import org.junit.jupiter.api.Test;

// Project imports
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.testutil.TypicalPersons;

/**
 * Additional coverage tests for DeleteCommand to exercise hashCode and success-message branches.
 */
public class DeleteCommandCoverageTest {

    private final Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void hashCodeIndexAndToStringIndexPath() {
        DeleteCommand byIndex = new DeleteCommand(Index.fromOneBased(1));
        int h1 = byIndex.hashCode();
        int h2 = byIndex.hashCode();
        assertEquals(h1, h2);
        String s = byIndex.toString();
        assertTrue(s.contains("index"));
    }

    @Test
    public void hashCodeMultipleNamesVariantsAndConfirmedExecuteIncludesNotFoundNote() throws Exception {
        // pick one existing name and one nonexistent
        Name existing = TypicalPersons.ALICE.getName();
        Name notFound = new Name("Nonexistent Person ZZ");

        List<Name> inputs = Arrays.asList(existing, notFound);

        // hashCode for multiple-names path
        DeleteCommand multiple = new DeleteCommand(inputs, true);
        int h = multiple.hashCode();
        assertEquals(h, multiple.hashCode());

        // executing confirmed delete should remove existing and return a message that includes the not-found note
        CommandResult result = multiple.execute(model);
        String feedback = result.getFeedbackToUser();
        // feedback should begin with a deletion success prefix (single or multiple)
        assertTrue(feedback.startsWith("Deleted "), "feedback was: '" + feedback + "'");
        assertTrue(feedback.contains("The following persons were not found"),
                "feedback was: '" + feedback + "'");
    }
}
