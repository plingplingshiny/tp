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
                                           List<String> priceKeywords,
                                           List<String> propertyTypeKeywords,
                                           List<String> intentionKeywords) {

        assert nameKeywords != null : "Name keyword list should not be null.";

        this.nameKeywords = nameKeywords;
        this.phoneKeywords = phoneKeywords;
        this.emailKeywords = emailKeywords;
        this.addressKeywords = addressKeywords;
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

        boolean priceMatches = priceKeywords.stream()
                .anyMatch(k -> priceMatchesKeyword(person.getPrice().value, k));

        boolean propertyTypeMatches = propertyTypeKeywords.stream()
                .anyMatch(k -> person.getPropertyType().value.toLowerCase().contains(k.toLowerCase()));

        boolean intentionMatches = intentionKeywords.stream()
                .anyMatch(k -> person.getIntention().intentionName.toLowerCase().contains(k.toLowerCase()));

        return nameMatches || phoneMatches || emailMatches || addressMatches
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
                && equalNormalizedPrices(priceKeywords, o.priceKeywords)
                && equalIgnoreCase(propertyTypeKeywords, o.propertyTypeKeywords)
                && equalIgnoreCase(intentionKeywords, o.intentionKeywords);
    }

    /**
     * Returns true if both lists contain the same set of strings, ignoring case differences.
     * <p>
     * Comparison is case-insensitive and order-independent — duplicates are disregarded.
     */
    private boolean equalIgnoreCase(List<String> a, List<String> b) {
        return a.stream().map(String::toLowerCase).collect(Collectors.toSet())
                .equals(b.stream().map(String::toLowerCase).collect(Collectors.toSet()));
    }

    /**
     * Returns true if both price keyword lists represent the same numeric values after normalization.
     * <p>
     * Normalization removes formatting differences such as commas or trailing decimals
     * (e.g. "1,000" and "1000.00" are treated as equal).
     */
    private boolean equalNormalizedPrices(List<String> a, List<String> b) {
        return a.stream()
                .map(this::normalizePrice)
                .collect(Collectors.toSet())
                .equals(b.stream()
                        .map(this::normalizePrice)
                        .collect(Collectors.toSet())
                );
    }

    /**
     * Converts a price string to a canonical numeric form (e.g. "1,000.00" → "1000").
     * Ensures equivalent numeric values match even if formatted differently.
     */
    private String normalizePrice(String s) {
        try {
            double value = Double.parseDouble(s.replaceAll(",", ""));
            return String.valueOf(value);
        } catch (NumberFormatException e) {
            return s;
        }
    }

    /**
     * Returns true if the given price value matches the keyword exactly or falls within a range.
     * <p>
     * Examples:
     * <ul>
     *   <li>{@code priceMatchesKeyword("1000.00", "1000")} → true (exact match)</li>
     *   <li>{@code priceMatchesKeyword("1100", "1000-1200")} → true (within range)</li>
     *   <li>{@code priceMatchesKeyword("999", "1000-1200")} → false (below range)</li>
     * </ul>
     * Range comparisons are inclusive and whitespace-tolerant (e.g. "1000 - 1200" is valid).
     * Invalid keywords (non-numeric) return false.
     */
    private boolean priceMatchesKeyword(String priceValue, String keyword) {
        String normalizedKeyword = keyword.replaceAll("\\s+", ""); // remove spaces
        String normalizedPrice = normalizePrice(priceValue);

        try {
            double personPrice = Double.parseDouble(normalizedPrice);

            if (normalizedKeyword.contains("-")) {
                String[] parts = normalizedKeyword.split("-");
                if (parts.length == 2) {
                    double lower = Double.parseDouble(normalizePrice(parts[0]));
                    double upper = Double.parseDouble(normalizePrice(parts[1]));
                    return personPrice >= lower && personPrice <= upper;
                }
            }

            double keywordPrice = Double.parseDouble(normalizePrice(normalizedKeyword));
            return Double.compare(personPrice, keywordPrice) == 0;

        } catch (NumberFormatException e) {
            return false;
        }
    }
}
