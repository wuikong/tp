package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

public class PropertyMatchesFilterPredicateTest {

    private static final Property PROPERTY = new Property(
            new PropertyAddress("311 Clementi Ave 2"),
            new Price("1200000"),
            new Size("1200"),
            new PropertyType("HDB"));

    @Test
    public void equals() {
        PropertyMatchesFilterPredicate firstPredicate = new PropertyMatchesFilterPredicate(
                Collections.singletonList("Clementi"),
                Collections.emptyList(),
                0,
                Long.MAX_VALUE,
                0,
                Long.MAX_VALUE);

        PropertyMatchesFilterPredicate secondPredicate = new PropertyMatchesFilterPredicate(
                Collections.singletonList("Punggol"),
                Collections.emptyList(),
                0,
                Long.MAX_VALUE,
                0,
                Long.MAX_VALUE);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PropertyMatchesFilterPredicate firstPredicateCopy = new PropertyMatchesFilterPredicate(
                Collections.singletonList("Clementi"),
                Collections.emptyList(),
                0,
                Long.MAX_VALUE,
                0,
                Long.MAX_VALUE);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void equals_differentTypeKeywords_returnsFalse() {
        PropertyMatchesFilterPredicate firstPredicate = new PropertyMatchesFilterPredicate(
                Collections.singletonList("Clementi"),
                Collections.singletonList("HDB"),
                0,
                Long.MAX_VALUE,
                0,
                Long.MAX_VALUE);

        PropertyMatchesFilterPredicate secondPredicate = new PropertyMatchesFilterPredicate(
                Collections.singletonList("Clementi"),
                Collections.singletonList("Condo"),
                0,
                Long.MAX_VALUE,
                0,
                Long.MAX_VALUE);

        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_matchesAddressKeyword_returnsTrue() {
        PropertyMatchesFilterPredicate predicate = new PropertyMatchesFilterPredicate(
                Collections.singletonList("Clementi"),
                Collections.emptyList(),
                0,
                Long.MAX_VALUE,
                0,
                Long.MAX_VALUE);
        assertTrue(predicate.test(PROPERTY));
    }

    @Test
    public void test_matchesPriceAndSizeRange_returnsTrue() {
        PropertyMatchesFilterPredicate predicate = new PropertyMatchesFilterPredicate(
                Collections.emptyList(),
                Collections.emptyList(),
                1100000,
                1300000,
                1000,
                1500);
        assertTrue(predicate.test(PROPERTY));
    }

    @Test
    public void test_outsideRange_returnsFalse() {
        PropertyMatchesFilterPredicate pricePredicate = new PropertyMatchesFilterPredicate(
                Collections.emptyList(),
                Collections.emptyList(),
                100000,
                200000,
                0,
                Long.MAX_VALUE);
        assertFalse(pricePredicate.test(PROPERTY));

        PropertyMatchesFilterPredicate sizePredicate = new PropertyMatchesFilterPredicate(
                Collections.emptyList(),
                Collections.emptyList(),
                0,
                Long.MAX_VALUE,
                10,
                100);
        assertFalse(sizePredicate.test(PROPERTY));
    }

    @Test
    public void test_noAddressKeywordsMatch_returnsFalse() {
        PropertyMatchesFilterPredicate predicate = new PropertyMatchesFilterPredicate(
                Arrays.asList("Punggol"),
                Collections.emptyList(),
                1100000,
                1300000,
                1000,
                1500);
        assertFalse(predicate.test(PROPERTY));
    }

    @Test
    public void test_matchesTypeKeyword_returnsTrue() {
        PropertyMatchesFilterPredicate predicate = new PropertyMatchesFilterPredicate(
                Collections.emptyList(),
                Collections.singletonList("hdb"),
                0,
                Long.MAX_VALUE,
                0,
                Long.MAX_VALUE);
        assertTrue(predicate.test(PROPERTY));
    }

    @Test
    public void test_noTypeKeywordsMatch_returnsFalse() {
        PropertyMatchesFilterPredicate predicate = new PropertyMatchesFilterPredicate(
                Collections.emptyList(),
                Collections.singletonList("Condo"),
                0,
                Long.MAX_VALUE,
                0,
                Long.MAX_VALUE);
        assertFalse(predicate.test(PROPERTY));
    }
}
