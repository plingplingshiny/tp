// src/main/java/seedu/address/model/person/PersonContainsKeywordsPredicate.java
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final List<String> tagKeywords;

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
        if (other == this) return true;
        if (!(other instanceof PersonContainsKeywordsPredicate)) return false;
        PersonContainsKeywordsPredicate o = (PersonContainsKeywordsPredicate) other;
        return nameKeywords.stream().map(String::toLowerCase).collect(Collectors.toSet())
                .equals(o.nameKeywords.stream().map(String::toLowerCase).collect(Collectors.toSet()))
                && tagKeywords.stream().map(String::toLowerCase).collect(Collectors.toSet())
                .equals(o.tagKeywords.stream().map(String::toLowerCase).collect(Collectors.toSet()));
    }

}
