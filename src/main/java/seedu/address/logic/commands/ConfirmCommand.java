package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Confirms or cancels a pending deletion that requires user confirmation.
 */
public class ConfirmCommand extends Command {

    public static final String COMMAND_WORD_YES = "yes";
    public static final String COMMAND_WORD_NO = "no";

    public static final String MESSAGE_NO_PENDING = "No pending confirmation to respond to.";
    public static final String MESSAGE_INVALID = "Invalid command. Please enter 'yes' to confirm or 'no' to abort.";
    public static final String MESSAGE_ABORTED = "Delete aborted.";

    private final boolean confirm;

    public ConfirmCommand(boolean confirm) {
        this.confirm = confirm;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!ConfirmationManager.hasPending()) {
            return new CommandResult(MESSAGE_NO_PENDING);
        }

        if (!confirm) {
            // user chose to abort
            ConfirmationManager.clearPending();
            return new CommandResult(MESSAGE_ABORTED);
        }

        // user confirmed: perform deletions
        List<Person> toDelete = ConfirmationManager.getPersonsToDelete();
        List<Name> notFound = ConfirmationManager.getNotFoundNames();

        for (Person p : toDelete) {
            model.deletePerson(p);
        }

        ConfirmationManager.clearPending();

        String resultMessage;
        if (toDelete.size() == 1) {
            resultMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                    Messages.format(toDelete.get(0)));
        } else {
            String personsStr = toDelete.stream()
                    .map(p -> p.getName().fullName)
                    .collect(java.util.stream.Collectors.joining(", "));
            resultMessage = String.format(DeleteCommand.MESSAGE_DELETE_MULTIPLE_PERSONS_SUCCESS,
                    toDelete.size(), personsStr);
        }

        if (!notFound.isEmpty()) {
            resultMessage += "\nNote: The following persons were not found: "
                    + notFound.stream().map(n -> n.fullName)
                    .collect(java.util.stream.Collectors.joining(", "));
        }

        return new CommandResult(resultMessage);
    }
}
