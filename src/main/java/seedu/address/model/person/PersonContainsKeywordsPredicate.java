package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Tests whether a {@link Person} satisfies at least one of the following conditions:
 * <ul>
 *   <li>The person's {@code Name}, {@code Phone}, {@code Email}, {@code Address}, {@code Tag},
 *       {@code PropertyType}, or {@code Intention}
 *       contains any of the given keywords (case-insensitive).</li>
 *   <li>The person's {@code Price} matches any of the given keywords exactly.</li>
 * </ul>
 * <p>
 * This predicate implements <b>OR semantics</b>: a {@code Person} matches if any of their fields
 * contain at least one of the specified keywords.
 * </p>
 * <p>
 * Matching for most fields is case-insensitive and substring-based — e.g., the keyword {@code "ali"} will match
 * {@code "Alice"} or {@code "Salisbury"}. Price matching is exact.
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
                .anyMatch(k -> normalizePrice(person.getPrice().value)
                        .equalsIgnoreCase(normalizePrice(k)));

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
                && equalNormalizedPrices(priceKeywords, o.priceKeywords)
                && equalIgnoreCase(propertyTypeKeywords, o.propertyTypeKeywords)
                && equalIgnoreCase(intentionKeywords, o.intentionKeywords);
    }

    private boolean equalIgnoreCase(List<String> a, List<String> b) {
        return a.stream().map(String::toLowerCase).collect(Collectors.toSet())
                .equals(b.stream().map(String::toLowerCase).collect(Collectors.toSet()));
    }

    private boolean equalNormalizedPrices(List<String> a, List<String> b) {
        return a.stream()
                .map(this::normalizePrice)
                .collect(Collectors.toSet())
                .equals(b.stream()
                        .map(this::normalizePrice)
                        .collect(Collectors.toSet())
                );
    }

    private String normalizePrice(String s) {
        return s.replaceAll("[^\\d.]", ""); // removes everything except digits and decimal points
    }

}
