package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;

import javax.swing.SwingUtilities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonCardTest {

    @BeforeAll
    public static void initJfx() throws Exception {
        // Initialize JavaFX toolkit
        SwingUtilities.invokeAndWait(JFXPanel::new);
    }

    @Test
    public void intention_sell_showsChipWithRedClassAndText() throws Exception {
        Person person = new PersonBuilder().withName("Alice")
                .withIntention("sell")
                .build();
        PersonCard card = new PersonCard(person, 1);

        Label intention = (Label) getPrivateField(card, "intention");
        Label id = (Label) getPrivateField(card, "id");
        Label name = (Label) getPrivateField(card, "name");

        assertEquals("Sell", intention.getText());
        List<String> classes = intention.getStyleClass();
        assertTrue(classes.contains("intention-chip"));
        assertTrue(classes.contains("intention-sell"));

        assertEquals("1. ", id.getText());
        assertEquals(person.getName().fullName, name.getText());
    }

    @Test
    public void intention_rent_showsChipWithOrangeClassAndText() throws Exception {
        Person person = new PersonBuilder().withName("Bob")
                .withIntention("rent")
                .build();
        PersonCard card = new PersonCard(person, 2);

        Label intention = (Label) getPrivateField(card, "intention");
        Label id = (Label) getPrivateField(card, "id");
        Label name = (Label) getPrivateField(card, "name");

        assertEquals("Rent", intention.getText());
        List<String> classes = intention.getStyleClass();
        assertTrue(classes.contains("intention-chip"));
        assertTrue(classes.contains("intention-rent"));

        assertEquals("2. ", id.getText());
        assertEquals(person.getName().fullName, name.getText());
    }

    private Object getPrivateField(Object target, String name) throws Exception {
        Field f = target.getClass().getDeclaredField(name);
        f.setAccessible(true);
        return f.get(target);
    }
}

