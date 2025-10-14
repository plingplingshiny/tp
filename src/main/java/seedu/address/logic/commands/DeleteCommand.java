package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index or name from the
 * address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list "
            + "or by name.\n"
            + "Parameters: INDEX (must be a positive integer) or n/NAME\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Example: " + COMMAND_WORD + " n/John Doe";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private enum TargetType {
        INDEX, NAME
    }

    private final TargetType targetType;
    private final Index targetIndex;
    private final Name targetName;

    /**
     * Creates a DeleteCommand to delete the person at the specified index.
     */
    public DeleteCommand(Index targetIndex) {
        this.targetType = TargetType.INDEX;
        this.targetIndex = targetIndex;
        this.targetName = null;
    }

    /**
     * Creates a DeleteCommand to delete the person with the specified name.
     */
    public DeleteCommand(Name targetName) {
        this.targetType = TargetType.NAME;
        this.targetIndex = null;
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToDelete;

        if (targetType == TargetType.INDEX) {
            List<Person> lastShownList = model.getFilteredPersonList();
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personToDelete = lastShownList.get(targetIndex.getZeroBased());
        } else {
            // Find by name
            personToDelete = model.getAddressBook().getPersonList().stream()
                    .filter(person -> person.getName().equals(targetName))
                    .findFirst()
                    .orElseThrow(() -> new CommandException(
                    Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX));
        }

        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetType == otherDeleteCommand.targetType
                && (targetIndex == null ? otherDeleteCommand.targetIndex == null
                        : targetIndex.equals(otherDeleteCommand.targetIndex))
                && (targetName == null ? otherDeleteCommand.targetName == null
                        : targetName.equals(otherDeleteCommand.targetName));
    }

    @Override
    public int hashCode() {
        return (targetType == TargetType.INDEX ? targetIndex : targetName).hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetType", targetType)
                .add("target", targetType == TargetType.INDEX ? targetIndex : targetName)
                .toString();
    }
}
