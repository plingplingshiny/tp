package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.intention.Intention;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final PropertyType propertyType;
    private final Price price;
    private final Intention intention;


    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, PropertyType propertyType, Price price,
                  Intention intention) {
        requireAllNonNull(name, phone, email, address, propertyType, price, intention);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.propertyType = propertyType;
        this.price = price;
        this.intention = intention;
    }

    /**
     * Backward-compatible constructor defaulting propertyType to 'unspecified', price to '0',
     * and intention to 'sell'.
     */
    public Person(Name name, Phone phone, Email email, Address address) {
        this(name, phone, email, address,
                new PropertyType("unspecified"),
                new Price("0"),
                new Intention("sell"));
    }

    /**
     * Backward-compatible constructor defaulting propertyType to 'unspecified' and price to '0'.
     */
    public Person(Name name, Phone phone, Email email, Address address, Intention intention) {
        this(name, phone, email, address,
                new PropertyType("unspecified"),
                new Price("0"),
                intention);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public Price getPrice() {
        return price;
    }

    public Intention getIntention() {
        return intention;
    }

    /**
     * Returns true if both persons have the same address.
     */
    public boolean isSameAddress(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        if (otherPerson == null) {
            return false;
        }

        return address.equals(otherPerson.address);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameName(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        if (otherPerson == null) {
            return false;
        }

        return name.equals(otherPerson.name);
    }

    /**
     * Returns true if both persons have the same identity and data fields except tags.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        if (otherPerson == null) {
            return false;
        }

        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && propertyType.equals(otherPerson.propertyType)
                && price.equals(otherPerson.price)
                && intention.equals(otherPerson.intention);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && propertyType.equals(otherPerson.propertyType)
                && price.equals(otherPerson.price)
                && intention.equals(otherPerson.intention);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, propertyType, price, intention);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("property type", propertyType)
                .add("price", price)
                .add("intention", intention)
                .toString();
    }

}
