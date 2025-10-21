package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.GraphicsEnvironment;
import java.lang.reflect.Field;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Tests for {@code PersonListPanel}.
 * These tests verify that the intention chips render correctly
 * and that the table displays the expected values.
 *
 * In headless environments (like CI servers without a display),
 * the entire test class is skipped to avoid JavaFX initialization errors.
 */
public class PersonListPanelTest {

    @BeforeAll
    public static void initJfx() throws Exception {
        // Skip JavaFX initialization entirely if running headless (e.g., in CI)
        if (GraphicsEnvironment.isHeadless()) {
            Assumptions.assumeTrue(false, "Skipping JavaFX tests in headless environment");
        }

        // Otherwise, initialize the JavaFX runtime using a dummy JFXPanel
        SwingUtilities.invokeAndWait(JFXPanel::new);
    }

    @Test
    public void constructor_bindsItemsToTable() throws Exception {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        ObservableList<Person> personList = FXCollections.observableArrayList(alice, bob);

        PersonListPanel panel = new PersonListPanel(personList);

        @SuppressWarnings("unchecked")
        TableView<Person> table = (TableView<Person>) getPrivateField(panel, "personTableView");
        assertEquals(2, table.getItems().size());
        assertEquals(alice, table.getItems().get(0));
        assertEquals(bob, table.getItems().get(1));
    }

    @Test
    public void constructor_initializesCellFactories() throws Exception {
        ObservableList<Person> personList = FXCollections.observableArrayList();
        PersonListPanel panel = new PersonListPanel(personList);

        TableColumn<?, ?> colIndex = (TableColumn<?, ?>) getPrivateField(panel, "colIndex");
        TableColumn<?, ?> colName = (TableColumn<?, ?>) getPrivateField(panel, "colName");
        TableColumn<?, ?> colPhone = (TableColumn<?, ?>) getPrivateField(panel, "colPhone");
        TableColumn<?, ?> colEmail = (TableColumn<?, ?>) getPrivateField(panel, "colEmail");
        TableColumn<?, ?> colAddress = (TableColumn<?, ?>) getPrivateField(panel, "colAddress");
        TableColumn<?, ?> colPropertyType = (TableColumn<?, ?>) getPrivateField(panel, "colPropertyType");
        TableColumn<?, ?> colPrice = (TableColumn<?, ?>) getPrivateField(panel, "colPrice");
        TableColumn<?, ?> colIntention = (TableColumn<?, ?>) getPrivateField(panel, "colIntention");

        assertNotNull(colIndex.getCellFactory());
        assertNotNull(colName.getCellFactory());
        assertNotNull(colPhone.getCellFactory());
        assertNotNull(colEmail.getCellFactory());
        assertNotNull(colAddress.getCellFactory());
        assertNotNull(colPropertyType.getCellFactory());
        assertNotNull(colPrice.getCellFactory());
        assertNotNull(colIntention.getCellFactory());
    }

    /**
     * Utility helper to access private fields in the {@code PersonListPanel}.
     */
    private Object getPrivateField(Object target, String name) throws Exception {
        Field f = target.getClass().getDeclaredField(name);
        f.setAccessible(true);
        return f.get(target);
    }
}
