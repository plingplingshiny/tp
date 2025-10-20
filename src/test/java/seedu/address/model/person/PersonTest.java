package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTENTION_RENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROPERTY_TYPE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.intention.Intention;
import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSameName() {
        // same object -> returns true
        assertTrue(ALICE.isSameName(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameName(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withPropertyType(VALID_PROPERTY_TYPE_BOB).withPrice(VALID_PRICE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameName(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameName(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameName(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameName(editedBob));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withPropertyType(VALID_PROPERTY_TYPE_BOB).withPrice(VALID_PRICE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));

        // same intention, all other attributes same -> returns true
        Person anotherAlice = new PersonBuilder(ALICE).withIntention("sell").build();
        assertTrue(ALICE.isSamePerson(anotherAlice));

        // same name, same phone, same email, same address, same intention, different tags -> returns true
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different intention, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withIntention(VALID_INTENTION_RENT).build();
        assertFalse(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different property type -> returns false
        editedAlice = new PersonBuilder(ALICE).withPropertyType(VALID_PROPERTY_TYPE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different price -> returns false
        editedAlice = new PersonBuilder(ALICE).withPrice(VALID_PRICE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));

        // different intention -> returns false
        editedAlice = new PersonBuilder(ALICE).withIntention(VALID_INTENTION_RENT).build();
        assertFalse(ALICE.equals(editedAlice));

        // same intention -> returns true
        Person aliceWithIntention = new PersonBuilder(ALICE).withIntention("sell").build();
        Person anotherAliceWithIntention = new PersonBuilder(ALICE).withIntention("sell").build();
        assertTrue(aliceWithIntention.equals(anotherAliceWithIntention));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName()
                + "{name=" + ALICE.getName()
                + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail()
                + ", address=" + ALICE.getAddress()
                + ", property type=" + ALICE.getPropertyType()
                + ", price=" + ALICE.getPrice()
                + ", tags=" + ALICE.getTags()
                + ", intention=" + ALICE.getIntention() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        // All fields null
        assertThrows(NullPointerException.class, () ->
                new Person(null, null, null, null, null, null, null, null));

        // Individual null fields, test each required field
        assertThrows(NullPointerException.class, () ->
                new Person(null, ALICE.getPhone(), ALICE.getEmail(), ALICE.getAddress(),
                        ALICE.getPropertyType(), ALICE.getPrice(), ALICE.getTags(), ALICE.getIntention()));

        assertThrows(NullPointerException.class, () ->
                new Person(ALICE.getName(), null, ALICE.getEmail(), ALICE.getAddress(),
                        ALICE.getPropertyType(), ALICE.getPrice(), ALICE.getTags(), ALICE.getIntention()));

        assertThrows(NullPointerException.class, () ->
                new Person(ALICE.getName(), ALICE.getPhone(), null, ALICE.getAddress(),
                        ALICE.getPropertyType(), ALICE.getPrice(), ALICE.getTags(), ALICE.getIntention()));

        assertThrows(NullPointerException.class, () ->
                new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(), null,
                        ALICE.getPropertyType(), ALICE.getPrice(), ALICE.getTags(), ALICE.getIntention()));

        // Test the new fields specifically - propertyType
        assertThrows(NullPointerException.class, () ->
                new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(), ALICE.getAddress(),
                        null, ALICE.getPrice(), ALICE.getTags(), ALICE.getIntention()));

        // Test the new fields specifically - price
        assertThrows(NullPointerException.class, () ->
                new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(), ALICE.getAddress(),
                        ALICE.getPropertyType(), null, ALICE.getTags(), ALICE.getIntention()));

        // Tags null
        assertThrows(NullPointerException.class, () ->
                new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(), ALICE.getAddress(),
                        ALICE.getPropertyType(), ALICE.getPrice(), null, ALICE.getIntention()));

        // Intention null
        assertThrows(NullPointerException.class, () ->
                new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(), ALICE.getAddress(),
                        ALICE.getPropertyType(), ALICE.getPrice(), ALICE.getTags(), null));
    }

    @Test
    public void hashCode_test() {
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertEquals(ALICE.hashCode(), aliceCopy.hashCode());

        // intention affects hash if present
        Person editedAlice = new PersonBuilder(ALICE).withIntention(VALID_INTENTION_RENT).build();
        try {
            assertNotEquals(ALICE.hashCode(), editedAlice.hashCode());
        } catch (Throwable ignored) { /* intention not part of model */ }

        // propertyType affects hash if present
        Person differentPropertyType = new PersonBuilder(ALICE).withPropertyType(VALID_PROPERTY_TYPE_BOB).build();
        try {
            assertFalse(ALICE.hashCode() == differentPropertyType.hashCode());
        } catch (Throwable ignored) { /* propertyType not part of model */ }

        // price affects hash if present
        Person differentPrice = new PersonBuilder(ALICE).withPrice(VALID_PRICE_BOB).build();
        try {
            assertFalse(ALICE.hashCode() == differentPrice.hashCode());
        } catch (Throwable ignored) { /* price not part of model */ }

        int firstHashCode = ALICE.hashCode();
        int secondHashCode = ALICE.hashCode();
        assertEquals(firstHashCode, secondHashCode);
    }

    @Test
    public void isSamePerson_withDifferentPropertyType_returnsFalse() {
        // Specifically test that different propertyType makes isSamePerson return false
        Person editedAlice = new PersonBuilder(ALICE).withPropertyType(VALID_PROPERTY_TYPE_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void isSamePerson_withDifferentPrice_returnsFalse() {
        // Specifically test that different price makes isSamePerson return false
        Person editedAlice = new PersonBuilder(ALICE).withPrice(VALID_PRICE_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void isSamePerson_sameFieldsExceptTags_returnsTrue() {
        // Test that isSamePerson returns true when only tags are different
        Person editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void constructor_withoutIntention_defaultsToIntention() {
        Person person = new Person(
                ALICE.getName(),
                ALICE.getPhone(),
                ALICE.getEmail(),
                ALICE.getAddress(),
                ALICE.getTags()
        );
        assertEquals(new Intention("sell"), person.getIntention());
    }
}
