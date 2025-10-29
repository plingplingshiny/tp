package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Unit tests for ConfirmCommand without Mockito (uses a simple ModelStub).
 */
public class ConfirmCommandTest {

    private ModelStub model;

    private static class ModelStub implements Model {
        final List<Person> deleted = new ArrayList<>();

        @Override
        public void deletePerson(Person person) {
            deleted.add(person);
        }

        // ---- Unused methods in these tests: throw to catch accidental use ----
        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new UnsupportedOperationException();
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addPerson(Person person) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void sortFilteredPersonListByName() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasName(Person person) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasAddress(Person person) {
            throw new UnsupportedOperationException();
        }
    }

    @BeforeEach
    void setUp() {
        model = new ModelStub();
        ConfirmationManager.clearPending();
    }

    @Test
    void execute_noPending_returnsNoPendingMessage() throws CommandException {
        ConfirmCommand command = new ConfirmCommand(true);
        CommandResult result = command.execute(model);
        assertEquals(ConfirmCommand.MESSAGE_NO_PENDING, result.getFeedbackToUser());
    }

    @Test
    void execute_abortPending_returnsAbortMessageAndClearsPending() throws CommandException {
        Person p = new PersonBuilder().withName("Alice").build();
        ConfirmationManager.setPending(Collections.singletonList(p), Collections.emptyList());
        assertTrue(ConfirmationManager.hasPending());

        ConfirmCommand command = new ConfirmCommand(false);
        CommandResult result = command.execute(model);

        assertEquals(ConfirmCommand.MESSAGE_ABORTED, result.getFeedbackToUser());
        assertFalse(ConfirmationManager.hasPending());
        // No deletions should have occurred
        assertEquals(0, model.deleted.size());
    }

    @Test
    void execute_confirmSinglePerson_deletesAndReturnsSuccessMessage() throws CommandException {
        Person p = new PersonBuilder().withName("Alice").build();
        ConfirmationManager.setPending(Collections.singletonList(p), Collections.emptyList());

        ConfirmCommand command = new ConfirmCommand(true);
        CommandResult result = command.execute(model);

        assertEquals(1, model.deleted.size());
        assertEquals(p, model.deleted.get(0));
        String expected = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(p));
        assertEquals(expected, result.getFeedbackToUser());
        assertFalse(ConfirmationManager.hasPending());
    }

    @Test
    void execute_confirmMultiplePersons_deletesAllAndReturnsSuccessMessage() throws CommandException {
        Person p1 = new PersonBuilder().withName("Alice").build();
        Person p2 = new PersonBuilder().withName("Bob").build();
        List<Person> persons = Arrays.asList(p1, p2);
        ConfirmationManager.setPending(persons, Collections.emptyList());

        ConfirmCommand command = new ConfirmCommand(true);
        CommandResult result = command.execute(model);

        assertEquals(2, model.deleted.size());
        assertEquals(p1, model.deleted.get(0));
        assertEquals(p2, model.deleted.get(1));
        String expected = String.format(DeleteCommand.MESSAGE_DELETE_MULTIPLE_PERSONS_SUCCESS, 2, "Alice, Bob");
        assertEquals(expected, result.getFeedbackToUser());
    }

    @Test
    void execute_confirmWithNotFoundNames_appendsNotFoundMessage() throws CommandException {
        Person p = new PersonBuilder().withName("Alice").build();
        List<Person> toDelete = Collections.singletonList(p);
        List<Name> notFound = Collections.singletonList(new Name("Charlie"));
        ConfirmationManager.setPending(toDelete, notFound);

        ConfirmCommand command = new ConfirmCommand(true);
        CommandResult result = command.execute(model);

        String expected = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(p))
                + "\nNote: The following persons were not found: Charlie";
        assertEquals(expected, result.getFeedbackToUser());
    }
}
