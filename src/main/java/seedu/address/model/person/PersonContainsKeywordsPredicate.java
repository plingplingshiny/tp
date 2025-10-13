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
    private final List<String> phoneKeywords;
    private final List<String> emailKeywords;
    private final List<String> addressKeywords;
    private final List<String> tagKeywords;

    /**
     * Constructs a predicate that checks for matching name and/or tag keywords.
     *
     * @param nameKeywords a list of keywords to match against a person's name; may be empty but not {@code null}
     * @param tagKeywords a list of keywords to match against a person's tags; may be empty but not {@code null}
     */
    public PersonContainsKeywordsPredicate(List<String> nameKeywords,
                                           List<String> phoneKeywords,
                                           List<String> emailKeywords,
                                           List<String> addressKeywords,
                                           List<String> tagKeywords) {
        this.nameKeywords = nameKeywords;
        this.phoneKeywords = phoneKeywords;
        this.emailKeywords = emailKeywords;
        this.addressKeywords = addressKeywords;
        this.tagKeywords = tagKeywords;

    }

    @Override
    public boolean test(Person person) {
        boolean nameMatches = nameKeywords.stream()
                .anyMatch(k -> person.getName().fullName.toLowerCase().contains(k.toLowerCase()));

        boolean phoneMatches = phoneKeywords.stream()
                .anyMatch(k -> person.getPhone().value.toLowerCase().contains(k.toLowerCase()));

        boolean emailMatches = emailKeywords.stream()
                .anyMatch(k -> person.getEmail().value.toLowerCase().contains(k.toLowerCase()));

        boolean addressMatches = addressKeywords.stream()
                .anyMatch(k -> person.getAddress().value.toLowerCase().contains(k.toLowerCase()));

        boolean tagMatches = tagKeywords.stream()
                .anyMatch(k -> person.getTags().stream()
                        .anyMatch(tag -> tag.tagName.toLowerCase().contains(k.toLowerCase())));

        return nameMatches || phoneMatches || emailMatches || addressMatches || tagMatches;
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
        return equalIgnoreCase(nameKeywords, o.nameKeywords)
                && equalIgnoreCase(phoneKeywords, o.phoneKeywords)
                && equalIgnoreCase(emailKeywords, o.emailKeywords)
                && equalIgnoreCase(addressKeywords, o.addressKeywords)
                && equalIgnoreCase(tagKeywords, o.tagKeywords);
    }

    private boolean equalIgnoreCase(List<String> a, List<String> b) {
        return a.stream().map(String::toLowerCase).collect(Collectors.toSet())
                .equals(b.stream().map(String::toLowerCase).collect(Collectors.toSet()));
    }

}
