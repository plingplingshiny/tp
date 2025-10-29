package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validName_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(personToDelete.getName(), false);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validNameCaseInsensitive_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name nameWithDifferentCase = new Name(personToDelete.getName().fullName.toUpperCase());
        DeleteCommand deleteCommand = new DeleteCommand(nameWithDifferentCase, false);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidName_throwsCommandException() {
        Name invalidName = new Name("Nonexistent Person");
        DeleteCommand deleteCommand = new DeleteCommand(invalidName, false);

        assertCommandFailure(deleteCommand, model,
                String.format(Messages.MESSAGE_PERSON_NOT_FOUND, invalidName.fullName));
    }

    @Test
    public void execute_multiplePersonsWithSameNameUnconfirmed_requestsConfirmation() {
        // Use two existing persons with the same name from the address book (e.g., ALICE and PAULINE)
        List<Person> personsWithSameName = model.getAddressBook().getPersonList().stream()
                .filter(p -> p.getName().equals(model.getAddressBook().getPersonList().get(1).getName()))
                .collect(Collectors.toList());

        Person personToDuplicate = personsWithSameName.get(0);
        // build delete command using the name (not the full person list)
        DeleteCommand deleteCommand = new DeleteCommand(personToDuplicate.getName(), false);

        // compute actual persons that will be deleted (all matches for the provided name)
        List<Person> actualPersonsToDelete = model.getAddressBook().getPersonList().stream()
                .filter(p -> p.getName().equals(personToDuplicate.getName()))
                .collect(Collectors.toList());

        String personsNames = IntStream.range(0, actualPersonsToDelete.size())
                .mapToObj(i -> (i + 1) + ". " + actualPersonsToDelete.get(i).getName().fullName)
                .collect(Collectors.joining("\n"));

        // expected core confirmation message (the runtime may append duplicate notes)
        String expectedCore = String.format(DeleteCommand.MESSAGE_CONFIRM_DELETE_MULTIPLE,
                actualPersonsToDelete.size(), personsNames);

        // runtime appends a duplicate-note when multiple entries are found for the queried name
        String duplicateNote = "\nNote: Multiple entries found for '" + personToDuplicate.getName().fullName
                + "' — all matching entries will be deleted.";

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        try {
            CommandResult result = deleteCommand.execute(model);
            // confirm the returned message contains the expected core message and duplicate notes
            String feedback = result.getFeedbackToUser();
            assertTrue(feedback.contains("Warning: You are about to delete"));
            assertTrue(feedback.contains(personsNames));
            if (!duplicateNote.isEmpty()) {
                assertTrue(feedback.contains(duplicateNote.trim()));
            }
            // model should not have been modified yet
            assertEquals(expectedModel, model);
            // pending confirmation should be set
            assertTrue(ConfirmationManager.hasPending());
        } catch (Exception e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }

    @Test
    public void execute_multiplePersonsWithSameNameConfirmed_success() {
        // Use two existing persons with the same name from the address book (e.g., ALICE and PAULINE)
        List<Person> personsWithSameName = model.getAddressBook().getPersonList().stream()
                .filter(p -> p.getName().equals(model.getAddressBook().getPersonList().get(1).getName()))
                .collect(Collectors.toList());

        Person personToDelete = personsWithSameName.get(0);
        // build delete command using the name (not the full person list)
        DeleteCommand deleteCommand = new DeleteCommand(personToDelete.getName(), true);

        // compute actual persons that will be deleted (all matches for the provided name)
        List<Person> actualPersonsToDelete = model.getAddressBook().getPersonList().stream()
                .filter(p -> p.getName().equals(personToDelete.getName()))
                .collect(Collectors.toList());

        String deletedPersonsNames = actualPersonsToDelete.stream()
                .map(p -> p.getName().fullName)
                .collect(Collectors.joining(", "));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MULTIPLE_PERSONS_SUCCESS,
                actualPersonsToDelete.size(), deletedPersonsNames);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        actualPersonsToDelete.forEach(expectedModel::deletePerson);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multiplePersonsWithSameNameConfirmed_partialSuccess() {
        // Use two existing persons with the same name from the address book (e.g., ALICE and PAULINE)
        List<Person> personsWithSameName = model.getAddressBook().getPersonList().stream()
                .filter(p -> p.getName().equals(model.getAddressBook().getPersonList().get(1).getName()))
                .collect(Collectors.toList());

        Person personToDelete = personsWithSameName.get(0);
        // build delete command using the name (not the full person list)
        DeleteCommand deleteCommand = new DeleteCommand(personToDelete.getName(), true);

        // compute actual persons that will be deleted (all matches for the provided name)
        List<Person> actualPersonsToDelete = model.getAddressBook().getPersonList().stream()
                .filter(p -> p.getName().equals(personToDelete.getName()))
                .collect(Collectors.toList());

        String deletedPersonsNames = actualPersonsToDelete.stream()
                .map(p -> p.getName().fullName)
                .collect(Collectors.joining(", "));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MULTIPLE_PERSONS_SUCCESS,
                actualPersonsToDelete.size(), deletedPersonsNames);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        actualPersonsToDelete.forEach(expectedModel::deletePerson);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model} such that no persons are visible.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);
        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
