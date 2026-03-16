package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SizeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Size(null));
    }

    @Test
    public void constructor_invalidSize_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Size(""));
    }

    @Test
    public void isValidSize() {
        assertThrows(NullPointerException.class, () -> Size.isValidSize(null));

        assertFalse(Size.isValidSize(""));
        assertFalse(Size.isValidSize(" "));
        assertFalse(Size.isValidSize("12345678901"));
        assertFalse(Size.isValidSize("12.34"));
        assertFalse(Size.isValidSize("12a34"));
        assertFalse(Size.isValidSize("12 34"));
        assertFalse(Size.isValidSize("-100"));

        assertTrue(Size.isValidSize("0"));
        assertTrue(Size.isValidSize("12345"));
        assertTrue(Size.isValidSize("1234567890"));
    }

    @Test
    public void equals() {
        Size firstSize = new Size("500");
        Size secondSize = new Size("600");
        Size firstSizeCopy = new Size("500");

        assertTrue(firstSize.equals(firstSize));

        assertTrue(firstSize.equals(firstSizeCopy));

        assertFalse(firstSize.equals("500"));

        assertFalse(firstSize.equals(null));

        assertFalse(firstSize.equals(secondSize));
    }
}
