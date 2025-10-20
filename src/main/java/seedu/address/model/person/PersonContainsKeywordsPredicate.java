package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Tests whether a {@link Person} satisfies at least one of the following conditions:
 * <ul>
 *   <li>The person's {@code Name}, {@code Phone}, {@code Email}, {@code Address}, {@code Tag},
 *       {@code Price}, {@code PropertyType}, or {@code Intention}
 *       contains any of the given keywords (case-insensitive).</li>
 * </ul>
 * <p>
 * This predicate implements <b>OR semantics</b>: a {@code Person} matches if any of their fields
 * contain at least one of the specified keywords.
 * </p>
 * <p>
 * Matching is case-insensitive and substring-based — e.g., the keyword {@code "ali"} will match
 * {@code "Alice"} or {@code "Salisbury"}.
 * </p>
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {

    private final List<String> nameKeywords;
    private final List<String> phoneKeywords;
    private final List<String> emailKeywords;
    private final List<String> addressKeywords;
    private final List<String> tagKeywords;
    private final List<String> priceKeywords;
    private final List<String> propertyTypeKeywords;
    private final List<String> intentionKeywords;

    /**
     * Constructs a predicate that checks for matching keywords across multiple fields.
     */
    public PersonContainsKeywordsPredicate(List<String> nameKeywords,
                                           List<String> phoneKeywords,
                                           List<String> emailKeywords,
                                           List<String> addressKeywords,
                                           List<String> tagKeywords,
                                           List<String> priceKeywords,
                                           List<String> propertyTypeKeywords,
                                           List<String> intentionKeywords) {

        assert nameKeywords != null : "Name keyword list should not be null.";
        assert phoneKeywords != null : "Phone keyword list should not be null.";
        assert emailKeywords != null : "Email keyword list should not be null.";
        assert addressKeywords != null : "Address keyword list should not be null.";
        assert tagKeywords != null : "Tag keyword list should not be null.";
        assert priceKeywords != null : "Price keyword list should not be null.";
        assert propertyTypeKeywords != null : "Property type keyword list should not be null.";
        assert intentionKeywords != null : "Intention keyword list should not be null.";

        assert nameKeywords.stream().noneMatch(k -> k == null || k.isBlank()) : "Name keywords contain null/blank.";
        assert phoneKeywords.stream().noneMatch(k -> k == null || k.isBlank()) : "Phone keywords contain null/blank.";
        assert emailKeywords.stream().noneMatch(k -> k == null || k.isBlank()) : "Email keywords contain null/blank.";
        assert addressKeywords.stream().noneMatch(k -> k == null || k.isBlank()) : "Address keywords contain null/blank.";
        assert tagKeywords.stream().noneMatch(k -> k == null || k.isBlank()) : "Tag keywords contain null/blank.";
        assert priceKeywords.stream().noneMatch(k -> k == null || k.isBlank()) : "Price keywords contain null/blank.";
        assert propertyTypeKeywords.stream().noneMatch(k -> k == null || k.isBlank()) : "Property type keywords contain null/blank.";
        assert intentionKeywords.stream().noneMatch(k -> k == null || k.isBlank()) : "Intention keywords contain null/blank.";

        this.nameKeywords = nameKeywords;
        this.phoneKeywords = phoneKeywords;
        this.emailKeywords = emailKeywords;
        this.addressKeywords = addressKeywords;
        this.tagKeywords = tagKeywords;
        this.priceKeywords = priceKeywords;
        this.propertyTypeKeywords = propertyTypeKeywords;
        this.intentionKeywords = intentionKeywords;
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

        boolean priceMatches = priceKeywords.stream()
                .anyMatch(k -> person.getPrice().value.toLowerCase().contains(k.toLowerCase()));

        boolean propertyTypeMatches = propertyTypeKeywords.stream()
                .anyMatch(k -> person.getPropertyType().value.toLowerCase().contains(k.toLowerCase()));

        boolean intentionMatches = intentionKeywords.stream()
                .anyMatch(k -> person.getIntention().intentionName.toLowerCase().contains(k.toLowerCase()));

        return nameMatches || phoneMatches || emailMatches || addressMatches || tagMatches
                || priceMatches || propertyTypeMatches || intentionMatches;
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
                && equalIgnoreCase(tagKeywords, o.tagKeywords)
                && equalIgnoreCase(priceKeywords, o.priceKeywords)
                && equalIgnoreCase(propertyTypeKeywords, o.propertyTypeKeywords)
                && equalIgnoreCase(intentionKeywords, o.intentionKeywords);
    }

    private boolean equalIgnoreCase(List<String> a, List<String> b) {
        return a.stream().map(String::toLowerCase).collect(Collectors.toSet())
                .equals(b.stream().map(String::toLowerCase).collect(Collectors.toSet()));
    }
}
