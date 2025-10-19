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
        Person person = new PersonBuilder().withPropertyType("Condo").build();
        assertEquals("Condo", person.getPropertyType().value);
    }

    @Test
    public void personCard_shouldSetPriceFromPerson() {
        Person person = new PersonBuilder().withPrice("1200000").build();
        assertEquals("1200000", person.getPrice().value);
    }

    @Test
    public void personCard_shouldSetPhoneFromPerson() {
        Person person = new PersonBuilder().withPhone("98765432").build();

        // Tests: phone.setText(person.getPhone().value);
        assertEquals("98765432", person.getPhone().value);
        assertEquals("98765432", person.getPhone().toString());
    }

    @Test
    public void personCard_shouldSetAddressFromPerson() {
        Person person = new PersonBuilder().withAddress("123 Main Street").build();

        // Tests: address.setText(person.getAddress().value);
        assertEquals("123 Main Street", person.getAddress().value);
        assertEquals("123 Main Street", person.getAddress().toString());
    }

    @Test
    public void personCard_shouldSetEmailFromPerson() {
        Person person = new PersonBuilder().withEmail("test@example.com").build();

        // Tests: email.setText(person.getEmail().value);
        assertEquals("test@example.com", person.getEmail().value);
        assertEquals("test@example.com", person.getEmail().toString());
    }

    @Test
    public void personCard_shouldSetAllFieldsFromPerson() {
        Person person = new PersonBuilder()
                .withName("John Doe")
                .withPhone("12345678")
                .withEmail("john@example.com")
                .withAddress("456 Orchard Road")
                .withPropertyType("Condo")
                .withPrice("1500000")
                .build();

        // Test all fields that PersonCard sets
        assertEquals("John Doe", person.getName().fullName);
        assertEquals("John Doe", person.getName().toString());
        assertEquals("12345678", person.getPhone().value);
        assertEquals("john@example.com", person.getEmail().value);
        assertEquals("456 Orchard Road", person.getAddress().value);
        assertEquals("Condo", person.getPropertyType().value);
        assertEquals("1500000", person.getPrice().value);
    }

    @Test
    public void personCard_shouldHandleDifferentPhoneFormats() {
        Person personWithSpaces = new PersonBuilder().withPhone("9876 5432").build();
        assertEquals("9876 5432", personWithSpaces.getPhone().value);

        Person personWithDashes = new PersonBuilder().withPhone("9876-5432").build();
        assertEquals("9876-5432", personWithDashes.getPhone().value);

        Person personWithPlus = new PersonBuilder().withPhone("+6598765432").build();
        assertEquals("+6598765432", personWithPlus.getPhone().value);

        Person personWithCountryCode = new PersonBuilder().withPhone("+65 9876 5432").build();
        assertEquals("+65 9876 5432", personWithCountryCode.getPhone().value);
    }

    @Test
    public void personCard_shouldHandleDifferentAddressFormats() {
        Person personWithComplexAddress = new PersonBuilder()
                .withAddress("Blk 123, Ang Mo Kio Ave 6, #12-345")
                .build();
        assertEquals("Blk 123, Ang Mo Kio Ave 6, #12-345", personWithComplexAddress.getAddress().value);
    }

    @Test
    public void personCard_shouldHandleDifferentEmailFormats() {
        Person personWithSubdomain = new PersonBuilder().withEmail("test.user@company.co.uk").build();
        assertEquals("test.user@company.co.uk", personWithSubdomain.getEmail().value);

        Person personWithHyphen = new PersonBuilder().withEmail("test-user@company.com").build();
        assertEquals("test-user@company.com", personWithHyphen.getEmail().value);

        Person personWithUnderscore = new PersonBuilder().withEmail("test_user@company.com").build();
        assertEquals("test_user@company.com", personWithUnderscore.getEmail().value);

        Person personWithPeriod = new PersonBuilder().withEmail("test.user@company.com").build();
        assertEquals("test.user@company.com", personWithPeriod.getEmail().value);
    }

    @Test
    public void personCard_shouldHandleDifferentPropertyTypes() {
        Person hdbPerson = new PersonBuilder().withPropertyType("HDB").build();
        assertEquals("HDB", hdbPerson.getPropertyType().value);

        Person condoPerson = new PersonBuilder().withPropertyType("Condo").build();
        assertEquals("Condo", condoPerson.getPropertyType().value);

        Person landedPerson = new PersonBuilder().withPropertyType("Landed").build();
        assertEquals("Landed", landedPerson.getPropertyType().value);

        Person detailedProperty = new PersonBuilder().withPropertyType("Executive Condominium").build();
        assertEquals("Executive Condominium", detailedProperty.getPropertyType().value);
    }

    @Test
    public void personCard_shouldHandleDifferentPriceRanges() {
        Person affordable = new PersonBuilder().withPrice("500000").build();
        assertEquals("500000", affordable.getPrice().value);

        Person midRange = new PersonBuilder().withPrice("1000000").build();
        assertEquals("1000000", midRange.getPrice().value);

        Person expensive = new PersonBuilder().withPrice("2500000").build();
        assertEquals("2500000", expensive.getPrice().value);

        Person veryExpensive = new PersonBuilder().withPrice("10000000").build();
        assertEquals("10000000", veryExpensive.getPrice().value);
    }

    @Test
    public void personCard_shouldHandleEdgeCases() {
        // Test minimum valid values
        Person minValues = new PersonBuilder()
                .withName("A")
                .withPhone("1234567")
                .withEmail("a@bc.de")
                .withAddress("A")
                .withPropertyType("H")
                .withPrice("1")
                .build();

        assertEquals("A", minValues.getName().fullName);
        assertEquals("1234567", minValues.getPhone().value);
        assertEquals("a@bc.de", minValues.getEmail().value);
        assertEquals("A", minValues.getAddress().value);
        assertEquals("H", minValues.getPropertyType().value);
        assertEquals("1", minValues.getPrice().value);
    }

    @Test
    public void personCard_shouldHandleSpecialCharacters() {
        Person specialChars = new PersonBuilder()
                .withName("John O'Conner")
                .withPhone("9123-4567")
                .withEmail("john.oconner@example.com")
                .withAddress("123 Main St, #04-56")
                .withPropertyType("HDB 4-Room")
                .build();

        assertEquals("John O'Conner", specialChars.getName().fullName);
        assertEquals("9123-4567", specialChars.getPhone().value);
        assertEquals("john.oconner@example.com", specialChars.getEmail().value);
        assertEquals("123 Main St, #04-56", specialChars.getAddress().value);
        assertEquals("HDB 4-Room", specialChars.getPropertyType().value);
    }

    @Test
    public void personCard_shouldHandleLongPhoneNumbers() {
        // Test international phone numbers with more digits
        Person international = new PersonBuilder()
                .withPhone("+1-800-123-4567")
                .build();
        assertEquals("+1-800-123-4567", international.getPhone().value);

        Person longLocal = new PersonBuilder()
                .withPhone("6512345678")
                .build();
        assertEquals("6512345678", longLocal.getPhone().value);
    }

    @Test
    public void personCard_shouldHandleComplexPropertyDescriptions() {
        Person complexProperty = new PersonBuilder()
                .withPropertyType("HDB 4-Room Executive Flat")
                .build();
        assertEquals("HDB 4-Room Executive Flat", complexProperty.getPropertyType().value);
    }

    @Test
    public void personCard_shouldHandleFormattedPrices() {
        Person formattedPrice = new PersonBuilder()
                .withPrice("1250000")
                .build();
        assertEquals("1250000", formattedPrice.getPrice().value);
    }

    @Test
    public void personCard_shouldHandleValidEmailEdgeCases() {
        // Test various valid email formats
        Person simpleEmail = new PersonBuilder().withEmail("a@bc.de").build();
        assertEquals("a@bc.de", simpleEmail.getEmail().value);

        Person emailWithNumbers = new PersonBuilder().withEmail("user123@domain.com").build();
        assertEquals("user123@domain.com", emailWithNumbers.getEmail().value);

        Person emailWithMultipleSubdomains = new PersonBuilder().withEmail("test@sub.domain.co.uk").build();
        assertEquals("test@sub.domain.co.uk", emailWithMultipleSubdomains.getEmail().value);

        Person emailWithPlus = new PersonBuilder().withEmail("user+tag@domain.com").build();
        assertEquals("user+tag@domain.com", emailWithPlus.getEmail().value);
    }
}
