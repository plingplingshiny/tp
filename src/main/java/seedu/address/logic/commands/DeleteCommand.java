package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/** Deletes a person identified by index or name. */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list "
            + "or by name. Multiple names can be deleted at once.\n"
            + "Parameters: INDEX (must be a positive integer) or n/NAME [n/NAME]...\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Example: " + COMMAND_WORD + " n/John Doe\n"
            + "Example: " + COMMAND_WORD + " n/John Doe n/Mary Jane";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_MULTIPLE_PERSONS_SUCCESS = "Deleted %1$d persons: %2$s";
    public static final String MESSAGE_CONFIRM_DELETE_MULTIPLE =
            "Warning: You are about to delete %1$d persons:\n%2$s\n"
            + "To confirm, re-enter the command with the exact names "
            + "and add confirm/yes.";
    public static final String MESSAGE_PERSONS_NOT_FOUND = "The following persons were not found: %1$s";

    private enum TargetType {
        INDEX, NAME, MULTIPLE_NAMES
    }

    private final TargetType targetType;
    private final Index targetIndex;
    private final Name targetName;
    private final List<Name> targetNames;
    private final boolean isConfirmed;

    /** Creates a DeleteCommand to delete the person at the specified index. */
    public DeleteCommand(Index targetIndex) {
        this.targetType = TargetType.INDEX;
        this.targetIndex = targetIndex;
        this.targetName = null;
        this.targetNames = null;
        this.isConfirmed = false;
    }

    /** Creates a DeleteCommand to delete the person with the specified name. */
    public DeleteCommand(Name targetName, boolean isConfirmed) {
        this.targetType = TargetType.NAME;
        this.targetIndex = null;
        this.targetName = targetName;
        this.targetNames = null;
        this.isConfirmed = isConfirmed;
    }

    public DeleteCommand(Name targetName) {
        this(targetName, false);
    }

    /** Creates a DeleteCommand to delete multiple persons with the specified names. */
    public DeleteCommand(List<Name> targetNames, boolean isConfirmed) {
        this.targetType = TargetType.MULTIPLE_NAMES;
        this.targetIndex = null;
        this.targetName = null;
        this.targetNames = new ArrayList<>(targetNames);
        this.isConfirmed = isConfirmed;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        switch (targetType) {
        case INDEX:
            return executeDeleteByIndex(model);
        case NAME:
            return executeDeleteByName(model);
        case MULTIPLE_NAMES:
            return executeDeleteMultipleNames(model);
        default:
            throw new IllegalStateException("Unexpected value: " + targetType);
        }
    }

    private CommandResult executeDeleteByIndex(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    private CommandResult executeDeleteByName(Model model) throws CommandException {
        String targetLower = targetName.fullName.toLowerCase();
        List<Person> personsMatchingName = model.getAddressBook().getPersonList().stream()
                .filter(person -> person.getName().fullName.toLowerCase().equals(targetLower))
                .collect(Collectors.toList());

        if (personsMatchingName.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, targetName.fullName));
        }

        if (personsMatchingName.size() == 1 && !isConfirmed) {
            Person personToDelete = personsMatchingName.get(0);
            model.deletePerson(personToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
        } else {
            CommandResult confirmationResult = checkConfirmationRequired(personsMatchingName, new ArrayList<>());
            if (confirmationResult != null) {
                return confirmationResult;
            }
            deletePersons(model, personsMatchingName);
            return createSuccessMessage(personsMatchingName, new ArrayList<>());
        }
    }

    private CommandResult executeDeleteMultipleNames(Model model) throws CommandException {
        List<Person> personsToDelete = new ArrayList<>();
        List<Name> notFoundNames = new ArrayList<>();
        List<Name> distinctTargetNames = targetNames.stream().distinct().collect(Collectors.toList());

        for (Name name : distinctTargetNames) {
            boolean found = false;
            String targetLowerName = name.fullName.toLowerCase();
            for (Person p : model.getAddressBook().getPersonList()) {
                String pNameLower = p.getName().fullName.toLowerCase();
                if (pNameLower.equals(targetLowerName) && !personsToDelete.contains(p)) {
                    personsToDelete.add(p);
                    found = true;
                    break; // only take the first matching person per input name
                }
            }
            if (!found) {
                notFoundNames.add(name);
            }
        }

        if (personsToDelete.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_PERSONS_NOT_FOUND,
                    notFoundNames.stream().map(n -> n.fullName).collect(Collectors.joining(", "))));
        }

        CommandResult confirmationResult = checkConfirmationRequired(personsToDelete, notFoundNames);
        if (confirmationResult != null) {
            return confirmationResult;
        }

        deletePersons(model, personsToDelete);
        return createSuccessMessage(personsToDelete, notFoundNames);
    }

    private CommandResult checkConfirmationRequired(List<Person> personsToDelete, List<Name> notFoundNames) {
        if (!isConfirmed && personsToDelete.size() > 1) {
            String personsFormatted = IntStream.range(0, personsToDelete.size())
                    .mapToObj(i -> (i + 1) + ". " + personsToDelete.get(i).getName().fullName)
                    .collect(Collectors.joining("\n"));

            String notFoundMessage = "";
            if (!notFoundNames.isEmpty()) {
                notFoundMessage = "\nNote: The following persons were not found: "
                        + notFoundNames.stream().map(n -> n.fullName).collect(Collectors.joining(", "));
            }
            return new CommandResult(String.format(MESSAGE_CONFIRM_DELETE_MULTIPLE,
                    personsToDelete.size(), personsFormatted) + notFoundMessage);
        }
        return null;
    }

    private void deletePersons(Model model, List<Person> personsToDelete) {
        for (Person person : personsToDelete) {
            model.deletePerson(person);
        }
    }

    private CommandResult createSuccessMessage(List<Person> personsToDelete, List<Name> notFoundNames) {
        String successMessage;
        if (personsToDelete.size() == 1) {
            successMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                    Messages.format(personsToDelete.get(0)));
        } else {
            String personsStr = personsToDelete.stream()
                    .map(p -> p.getName().fullName)
                    .collect(Collectors.joining(", "));
            successMessage = String.format(MESSAGE_DELETE_MULTIPLE_PERSONS_SUCCESS,
                    personsToDelete.size(), personsStr);
        }

        if (!notFoundNames.isEmpty()) {
            successMessage += "\nNote: The following persons were not found: "
                    + notFoundNames.stream().map(n -> n.fullName).collect(Collectors.joining(", "));
        }

        return new CommandResult(successMessage);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetType == otherDeleteCommand.targetType
                && (targetIndex == null ? otherDeleteCommand.targetIndex == null
                        : targetIndex.equals(otherDeleteCommand.targetIndex))
                && (targetName == null ? otherDeleteCommand.targetName == null
                        : targetName.equals(otherDeleteCommand.targetName))
                && (targetNames == null ? otherDeleteCommand.targetNames == null
                        : targetNames.equals(otherDeleteCommand.targetNames))
                && isConfirmed == otherDeleteCommand.isConfirmed;
    }

    @Override
    public int hashCode() {
        switch (targetType) {
        case INDEX:
            return targetIndex.hashCode();
        case NAME:
            return targetName != null
                    ? targetName.hashCode()
                    : Boolean.hashCode(isConfirmed);
        case MULTIPLE_NAMES:
            return targetNames.hashCode() + Boolean.hashCode(isConfirmed);
        default:
            throw new IllegalStateException("Unexpected value: " + targetType);
        }
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this)
                .add("targetType", targetType);

        switch (targetType) {
        case INDEX:
            builder.add("target", targetIndex);
            break;
        case NAME:
            builder.add("target", targetName);
            builder.add("confirmed", isConfirmed);
            break;
        case MULTIPLE_NAMES:
            builder.add("targets", targetNames);
            builder.add("confirmed", isConfirmed);
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + targetType);
        }

        return builder.toString();
    }
}
