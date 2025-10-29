package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ArgumentTokenizerTest {

    @Test
    public void tokenize_leadingSpacePrefixes_detectsPrefixes() {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(
                " n/Alice n/Benson confirm/yes",
                CliSyntax.PREFIX_NAME,
                CliSyntax.PREFIX_CONFIRM);
        assertEquals(1, map.getAllValues(CliSyntax.PREFIX_CONFIRM).size());
        assertEquals(2, map.getAllValues(CliSyntax.PREFIX_NAME).size());
    }
}
