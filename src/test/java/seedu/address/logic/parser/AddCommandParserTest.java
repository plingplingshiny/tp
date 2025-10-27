package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INTENTION_DESC_RENT;
import static seedu.address.logic.commands.CommandTestUtil.INTENTION_DESC_SELL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INTENTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PROPERTY_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PROPERTY_TYPE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PROPERTY_TYPE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROPERTY_TYPE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTENTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_TYPE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.intention.Intention;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Price;
import seedu.address.model.person.PropertyType;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags("friend").withIntention("sell").build();

        // whitespace only preamble
        assertParseSuccess(
                parser,
                PREAMBLE_WHITESPACE + INTENTION_DESC_SELL + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + PROPERTY_TYPE_DESC_BOB + PRICE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson)
        );


        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB)
                .withTags("friend", "husband")
                .withIntention("rent")
                .build();
        assertParseSuccess(
                parser,
                INTENTION_DESC_RENT + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + PROPERTY_TYPE_DESC_BOB
                        + PRICE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags)
        );
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String base = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + PROPERTY_TYPE_DESC_BOB + PRICE_DESC_BOB + TAG_DESC_FRIEND;
        String validExpectedPersonString = INTENTION_DESC_SELL + base;

        // multiple names
        assertParseFailure(
                parser,
                INTENTION_DESC_SELL + NAME_DESC_AMY + base,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME)
        );

        // multiple phones
        assertParseFailure(
                parser,
                INTENTION_DESC_SELL + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + PROPERTY_TYPE_DESC_BOB + PRICE_DESC_BOB + TAG_DESC_FRIEND,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE)
        );

        // multiple emails
        assertParseFailure(
                parser,
                INTENTION_DESC_SELL + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + PROPERTY_TYPE_DESC_BOB + PRICE_DESC_BOB + TAG_DESC_FRIEND,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL)
        );

        // multiple addresses
        assertParseFailure(
                parser,
                INTENTION_DESC_SELL + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                        + ADDRESS_DESC_BOB + PROPERTY_TYPE_DESC_BOB + PRICE_DESC_BOB + TAG_DESC_FRIEND,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS)
        );

        // multiple property types
        assertParseFailure(
                parser,
                INTENTION_DESC_SELL + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + PROPERTY_TYPE_DESC_AMY + PROPERTY_TYPE_DESC_BOB + PRICE_DESC_BOB + TAG_DESC_FRIEND,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PROPERTY_TYPE)
        );

        // multiple prices
        assertParseFailure(
                parser,
                validExpectedPersonString + PRICE_DESC_AMY + PRICE_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PRICE)
        );

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + ADDRESS_DESC_AMY + PROPERTY_TYPE_DESC_AMY + PRICE_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_INTENTION, PREFIX_ADDRESS,
                        PREFIX_EMAIL, PREFIX_PHONE, PREFIX_PROPERTY_TYPE, PREFIX_PRICE));
        // multiple fields repeated (do not repeat intention)
        String requiredOnce = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + PROPERTY_TYPE_DESC_BOB + PRICE_DESC_BOB + TAG_DESC_FRIEND;
        assertParseFailure(
                parser,
                INTENTION_DESC_SELL + requiredOnce + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + ADDRESS_DESC_AMY + TAG_DESC_FRIEND,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE)
        );
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().withIntention("sell").build();
        assertParseSuccess(
                parser,
                INTENTION_DESC_SELL + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + PROPERTY_TYPE_DESC_AMY + PRICE_DESC_AMY, new AddCommand(expectedPerson)
        );
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing intention prefix
        assertParseFailure(
                parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage
        );

        // missing name prefix
        assertParseFailure(
                parser,
                INTENTION_DESC_SELL + VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + PROPERTY_TYPE_DESC_BOB + PRICE_DESC_BOB, expectedMessage
        );

        // missing phone prefix
        assertParseFailure(
                parser,
                INTENTION_DESC_SELL + NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + PROPERTY_TYPE_DESC_BOB + PRICE_DESC_BOB, expectedMessage
        );

        // missing email prefix
        assertParseFailure(
                parser,
                INTENTION_DESC_SELL + NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                + PROPERTY_TYPE_DESC_BOB + PRICE_DESC_BOB, expectedMessage
        );

        // missing address prefix
        assertParseFailure(
                parser,
                INTENTION_DESC_SELL + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                + PROPERTY_TYPE_DESC_BOB + PRICE_DESC_BOB, expectedMessage);

        // missing property type prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + VALID_PROPERTY_TYPE_BOB + PRICE_DESC_BOB, expectedMessage);

        // missing price prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + PROPERTY_TYPE_DESC_BOB + VALID_PRICE_BOB, expectedMessage
        );

        // all prefixes missing
        assertParseFailure(
                parser,
                VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                + VALID_PROPERTY_TYPE_BOB + VALID_PRICE_BOB, expectedMessage
        );
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid intention
        assertParseFailure(
                parser,
                INVALID_INTENTION_DESC + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + PROPERTY_TYPE_DESC_BOB + PRICE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Intention.MESSAGE_CONSTRAINTS
        );

        // invalid name
        assertParseFailure(
                parser,
                INTENTION_DESC_SELL + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + PROPERTY_TYPE_DESC_BOB + PRICE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_CONSTRAINTS
        );

        // invalid phone
        assertParseFailure(
                parser,
                INTENTION_DESC_SELL + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + PROPERTY_TYPE_DESC_BOB + PRICE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_CONSTRAINTS
        );

        // invalid email
        assertParseFailure(
                parser,
                INTENTION_DESC_SELL + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                        + PROPERTY_TYPE_DESC_BOB + PRICE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Email.MESSAGE_CONSTRAINTS
        );

        // invalid address
        assertParseFailure(
                parser,
                INTENTION_DESC_SELL + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                        + PROPERTY_TYPE_DESC_BOB + PRICE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Address.MESSAGE_CONSTRAINTS
        );

        // invalid property type
        assertParseFailure(parser, INTENTION_DESC_SELL + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_PROPERTY_TYPE_DESC + PRICE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, PropertyType.MESSAGE_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser, INTENTION_DESC_SELL + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + PROPERTY_TYPE_DESC_BOB + INVALID_PRICE_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Price.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(
                parser,
                INTENTION_DESC_SELL + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + PROPERTY_TYPE_DESC_BOB + PRICE_DESC_BOB
                + INVALID_TAG_DESC + TAG_DESC_FRIEND,
                Tag.MESSAGE_CONSTRAINTS
        );

        // two invalid values, only first invalid value reported
        assertParseFailure(
                parser,
                INTENTION_DESC_SELL + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + PROPERTY_TYPE_DESC_BOB + PRICE_DESC_BOB, Name.MESSAGE_CONSTRAINTS
        );

        // non-empty preamble
        assertParseFailure(
                parser,
                PREAMBLE_NON_EMPTY + INTENTION_DESC_SELL + NAME_DESC_BOB
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + PROPERTY_TYPE_DESC_BOB + PRICE_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE)
        );
    }
}
