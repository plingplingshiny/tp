package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private TableView<Person> personTableView;
    @FXML
    private TableColumn<Person, Number> colIndex;
    @FXML
    private TableColumn<Person, String> colName;
    @FXML
    private TableColumn<Person, String> colPhone;
    @FXML
    private TableColumn<Person, String> colEmail;
    @FXML
    private TableColumn<Person, String> colAddress;
    @FXML
    private TableColumn<Person, String> colPropertyType;
    @FXML
    private TableColumn<Person, String> colPrice;
    @FXML
    private TableColumn<Person, String> colIntention;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);

        personTableView.setItems(personList);

        // row index column (#)
        colIndex.setCellFactory(new Callback<TableColumn<Person, Number>, TableCell<Person, Number>>() {
            @Override
            public TableCell<Person, Number> call(TableColumn<Person, Number> param) {
                return new TableCell<Person, Number>() {
                    @Override
                    protected void updateItem(Number item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || getIndex() < 0 || getIndex() >= personTableView.getItems().size()) {
                            setText(null);
                        } else {
                            setText(String.valueOf(getIndex() + 1));
                        }
                    }
                };
            }
        });

        // text columns
        colName.setCellFactory(tc -> new TableCell<Person, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || getIndex() < 0 || getIndex() >= getTableView().getItems().size()
                        ? null
                        : getTableView().getItems().get(getIndex()).getName().fullName);
            }
        });

        colPhone.setCellFactory(tc -> new TableCell<Person, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || getIndex() < 0 || getIndex() >= getTableView().getItems().size()
                        ? null
                        : getTableView().getItems().get(getIndex()).getPhone().value);
            }
        });

        colEmail.setCellFactory(tc -> new TableCell<Person, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || getIndex() < 0 || getIndex() >= getTableView().getItems().size()
                        ? null
                        : getTableView().getItems().get(getIndex()).getEmail().value);
            }
        });

        colAddress.setCellFactory(tc -> new TableCell<Person, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || getIndex() < 0 || getIndex() >= getTableView().getItems().size()
                        ? null
                        : getTableView().getItems().get(getIndex()).getAddress().value);
            }
        });

        colPropertyType.setCellFactory(tc -> new TableCell<Person, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || getIndex() < 0 || getIndex() >= getTableView().getItems().size()
                        ? null
                        : getTableView().getItems().get(getIndex()).getPropertyType().value);
            }
        });

        colPrice.setCellFactory(tc -> new TableCell<Person, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || getIndex() < 0 || getIndex() >= getTableView().getItems().size()
                        ? null
                        : getTableView().getItems().get(getIndex()).getPrice().value);
            }
        });

        colIntention.setCellFactory(tc -> new TableCell<Person, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                // Always reset styles when updating
                getStyleClass().removeAll("intention-chip", "intention-sell", "intention-rent");

                if (empty || getIndex() < 0 || getIndex() >= getTableView().getItems().size()) {
                    setText(null);
                    return;
                }

                String intent = getTableView().getItems().get(getIndex()).getIntention().intentionName;
                String className = "intention-" + intent;
                String labelText = intent.substring(0, 1).toUpperCase() + intent.substring(1);
                setText(labelText);
                getStyleClass().add("intention-chip");
                getStyleClass().add(className);
            }
        });
    }
}
