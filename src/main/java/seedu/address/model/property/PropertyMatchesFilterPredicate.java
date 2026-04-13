package seedu.address.model.property;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Property} matches address keywords, type keywords and numeric ranges.
 */
public class PropertyMatchesFilterPredicate implements Predicate<Property> {
    private final List<String> addressKeywords;
    private final List<String> typeKeywords;
    private final long minPrice;
    private final long maxPrice;
    private final long minSize;
    private final long maxSize;

    /**
     * Constructs a {@code PropertyMatchesFilterPredicate} with the given address and type keywords and numeric ranges.
     */
    public PropertyMatchesFilterPredicate(
            List<String> addressKeywords, List<String> typeKeywords,
            long minPrice, long maxPrice, long minSize, long maxSize) {
        requireNonNull(addressKeywords);
        requireNonNull(typeKeywords);
        this.addressKeywords = List.copyOf(addressKeywords);
        this.typeKeywords = List.copyOf(typeKeywords);
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    @Override
    public boolean test(Property property) {
        boolean matchesAddress = addressKeywords.isEmpty()
                || addressKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(property.getAddress().value, keyword));
        boolean matchesType = typeKeywords.isEmpty() || typeKeywords.stream().anyMatch(
                keyword -> StringUtil.containsWordIgnoreCase(property.getPropertyType().toString(), keyword));

        long propertyPrice = Long.parseLong(property.getPrice().value);
        long propertySize = Long.parseLong(property.getSize().value);

        boolean matchesPrice = propertyPrice >= minPrice && propertyPrice <= maxPrice;
        boolean matchesSize = propertySize >= minSize && propertySize <= maxSize;

        return matchesAddress && matchesType && matchesPrice && matchesSize;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PropertyMatchesFilterPredicate)) {
            return false;
        }

        PropertyMatchesFilterPredicate otherPredicate = (PropertyMatchesFilterPredicate) other;
        return addressKeywords.equals(otherPredicate.addressKeywords)
                && typeKeywords.equals(otherPredicate.typeKeywords)
                && minPrice == otherPredicate.minPrice
                && maxPrice == otherPredicate.maxPrice
                && minSize == otherPredicate.minSize
                && maxSize == otherPredicate.maxSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressKeywords, typeKeywords, minPrice, maxPrice, minSize, maxSize);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("addressKeywords", addressKeywords)
                .add("typeKeywords", typeKeywords)
                .add("minPrice", minPrice)
                .add("maxPrice", maxPrice)
                .add("minSize", minSize)
                .add("maxSize", maxSize)
                .toString();
    }
}
