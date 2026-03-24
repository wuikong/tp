package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PropertyAddressContainsKeywordsPredicateTest {

    private static final Property PROPERTY = new Property(
            new PropertyAddress("311 Clementi Ave 2"),
            new Price("1200000"),
            new Size("1200"));

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PropertyAddressContainsKeywordsPredicate firstPredicate =
                new PropertyAddressContainsKeywordsPredicate(firstPredicateKeywordList);
        PropertyAddressContainsKeywordsPredicate secondPredicate =
                new PropertyAddressContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PropertyAddressContainsKeywordsPredicate firstPredicateCopy =
                new PropertyAddressContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        // One keyword
        PropertyAddressContainsKeywordsPredicate predicate =
                new PropertyAddressContainsKeywordsPredicate(Collections.singletonList("Clementi"));
        assertTrue(predicate.test(PROPERTY));

        // Multiple keywords
        predicate = new PropertyAddressContainsKeywordsPredicate(Arrays.asList("Clementi", "Punggol"));
        assertTrue(predicate.test(PROPERTY));

        // Mixed-case keywords
        predicate = new PropertyAddressContainsKeywordsPredicate(Arrays.asList("cleMENTi", "aVe"));
        assertTrue(predicate.test(PROPERTY));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PropertyAddressContainsKeywordsPredicate predicate =
                new PropertyAddressContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(PROPERTY));

        // Non-matching keyword
        predicate = new PropertyAddressContainsKeywordsPredicate(Collections.singletonList("Orchard"));
        assertFalse(predicate.test(PROPERTY));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        PropertyAddressContainsKeywordsPredicate predicate =
                new PropertyAddressContainsKeywordsPredicate(keywords);

        String expected = PropertyAddressContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
