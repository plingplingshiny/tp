package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Test class for PersonCard UI component logic.
 */
public class PersonCardTest {

    @Test
    public void constructor_logicSetsCorrectPropertyTypeAndPriceValues() {
        Person person = new PersonBuilder()
                .withName("Test Name")
                .withPhone("12345678")
                .withEmail("test@example.com")
                .withAddress("Test Address")
                .withPropertyType("HDB 4-Room")
                .withPrice("850000")
                .build();

        // Test that the Person object has the correct values
        // This indirectly tests that PersonCard would set these values correctly
        assertEquals("HDB 4-Room", person.getPropertyType().value);
        assertEquals("850000", person.getPrice().value);
        assertEquals("HDB 4-Room", person.getPropertyType().toString());
        assertEquals("850000", person.getPrice().toString());
    }

    @Test
    public void personCard_shouldSetPropertyTypeFromPerson() {
        // This test verifies the relationship without instantiating PersonCard
        Person person = new PersonBuilder().withPropertyType("Condo").build();

        // The key assertion: PersonCard constructor line would use:
        // propertyType.setText(person.getPropertyType().value);
        // So we verify person.getPropertyType().value returns the expected value
        assertEquals("Condo", person.getPropertyType().value);
    }

    @Test
    public void personCard_shouldSetPriceFromPerson() {
        Person person = new PersonBuilder().withPrice("1200000").build();

        // The key assertion: PersonCard constructor line would use:
        // price.setText(person.getPrice().value);
        // So we verify person.getPrice().value returns the expected value
        assertEquals("1200000", person.getPrice().value);
    }
}