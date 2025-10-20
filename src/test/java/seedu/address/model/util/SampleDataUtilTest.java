package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.intention.Intention;
import seedu.address.model.person.Person;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_intentionsPresentAndCorrect() {
        Person[] persons = SampleDataUtil.getSamplePersons();
        assertTrue(persons.length >= 6);

        // Expected alternating intentions: sell, rent, sell, rent, sell, rent
        String[] expectedIntentions = {"sell", "rent", "sell", "rent", "sell", "rent"};
        for (int i = 0; i < expectedIntentions.length; i++) {
            Intention actual = persons[i].getIntention();
            assertNotNull(actual);
            assertEquals(new Intention(expectedIntentions[i]), actual);
        }
    }

    @Test
    public void getSampleAddressBook_allPersonsHaveValidIntentions() {
        ReadOnlyAddressBook ab = SampleDataUtil.getSampleAddressBook();
        for (Person p : ab.getPersonList()) {
            Intention intention = p.getIntention();
            assertNotNull(intention);
            assertTrue(Intention.isValidIntentionName(intention.intentionName));
        }
    }
}

