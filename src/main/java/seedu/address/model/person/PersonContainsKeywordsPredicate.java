// src/main/java/seedu/address/model/person/PersonContainsKeywordsPredicate.java
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Tests whether a {@link Person} satisfies at least one of two possible conditions:
 * <ul>
 *   <li>The person's {@code Name} contains any of the given name keywords (case-insensitive).</li>
 *   <li>At least one of the person's {@code Tag}s contains any of the given tag keywords (case-insensitive).</li>
 * </ul>
 * <p>
 * This predicate implements <b>OR semantics</b>: a {@code Person} matches if either their name or tags
 * match any of the keywords provided.
 * </p>
 * <p>
 * Keywords are matched by case-insensitive substring containment, not by exact word equality.
 * Example: the keyword {@code "ali"} will match a name like {@code "Alice"} or a tag like {@code "family"}.
 * </p>
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final List<String> tagKeywords;

    /**
     * Constructs a predicate that checks for matching name and/or tag keywords.
     *
     * @param nameKeywords a list of keywords to match against a person's name; may be empty but not {@code null}
     * @param tagKeywords a list of keywords to match against a person's tags; may be empty but not {@code null}
     */
    public PersonContainsKeywordsPredicate(List<String> nameKeywords, List<String> tagKeywords) {
        this.nameKeywords = nameKeywords;
        this.tagKeywords = tagKeywords;
    }

    @Override
    public boolean test(Person person) {
        boolean nameMatches = nameKeywords.stream()
                .anyMatch(keyword -> person.getName().fullName.toLowerCase().contains(keyword.toLowerCase()));

        boolean tagMatches = tagKeywords.stream()
                .anyMatch(keyword -> person.getTags().stream()
                        .anyMatch(tag -> tag.tagName.toLowerCase().contains(keyword.toLowerCase())));

        return nameMatches || tagMatches;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PersonContainsKeywordsPredicate)) {
            return false;
        }
        PersonContainsKeywordsPredicate o = (PersonContainsKeywordsPredicate) other;
        return nameKeywords.stream().map(String::toLowerCase).collect(Collectors.toSet())
                .equals(o.nameKeywords.stream().map(String::toLowerCase).collect(Collectors.toSet()))
                && tagKeywords.stream().map(String::toLowerCase).collect(Collectors.toSet())
                .equals(o.tagKeywords.stream().map(String::toLowerCase).collect(Collectors.toSet()));
    }

}
