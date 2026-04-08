package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.property.Price;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyAddress;
import seedu.address.model.property.PropertyType;
import seedu.address.model.property.Size;

/**
 * Contains unit tests for {@link JsonAdaptedProperty}.
 */
public class JsonAdaptedPropertyTest {

    private static final String VALID_ADDRESS = "123 Main Street";
    private static final String VALID_PRICE = "500000";
    private static final String VALID_SIZE = "1200";
    private static final String VALID_REMARKS = "Near MRT";
    private static final String VALID_TYPE = "HDB";

    @Test
    public void toModelType_validPropertyDetails_returnsProperty() throws Exception {
        JsonAdaptedProperty property = new JsonAdaptedProperty(
                VALID_ADDRESS, VALID_PRICE, VALID_SIZE, null, VALID_TYPE);
        Property expectedProperty = new Property(
                new PropertyAddress(VALID_ADDRESS),
                new Price(VALID_PRICE),
                new Size(VALID_SIZE),
                new PropertyType(VALID_TYPE));

        assertEquals(expectedProperty, property.toModelType());
    }

    @Test
    public void toModelType_validPropertyWithRemarks_returnsPropertyWithRemarks() throws Exception {
        JsonAdaptedProperty property = new JsonAdaptedProperty(
                VALID_ADDRESS, VALID_PRICE, VALID_SIZE, VALID_REMARKS, VALID_TYPE);
        Property result = property.toModelType();

        assertEquals(VALID_REMARKS, result.getRemarks());
    }

    @Test
    public void toModelType_validPropertyWithType_returnsPropertyWithType() throws Exception {
        JsonAdaptedProperty property = new JsonAdaptedProperty(
                VALID_ADDRESS, VALID_PRICE, VALID_SIZE, null, VALID_TYPE);
        Property result = property.toModelType();

        assertEquals(new PropertyType(VALID_TYPE), result.getPropertyType());
    }

    @Test
    public void toModelType_validPropertyWithRemarksAndType_returnsProperty() throws Exception {
        JsonAdaptedProperty property = new JsonAdaptedProperty(
                VALID_ADDRESS, VALID_PRICE, VALID_SIZE, VALID_REMARKS, VALID_TYPE);
        Property result = property.toModelType();

        assertEquals(VALID_REMARKS, result.getRemarks());
        assertEquals(new PropertyType(VALID_TYPE), result.getPropertyType());
    }

    @Test
    public void toModelType_nullRemarks_returnsPropertyWithNullRemarks() throws Exception {
        JsonAdaptedProperty property = new JsonAdaptedProperty(
                VALID_ADDRESS, VALID_PRICE, VALID_SIZE, null, VALID_TYPE);
        Property result = property.toModelType();

        assertNull(result.getRemarks());
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(
                null, VALID_PRICE, VALID_SIZE, null, VALID_TYPE);

        assertThrows(IllegalValueException.class,
                String.format(JsonAdaptedProperty.MISSING_FIELD_MESSAGE_FORMAT,
                        PropertyAddress.class.getSimpleName()),
                property::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(
                "@123", VALID_PRICE, VALID_SIZE, null, VALID_TYPE);

        assertThrows(IllegalValueException.class,
                PropertyAddress.MESSAGE_CONSTRAINTS,
                property::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(
                VALID_ADDRESS, null, VALID_SIZE, null, VALID_TYPE);

        assertThrows(IllegalValueException.class,
                String.format(JsonAdaptedProperty.MISSING_FIELD_MESSAGE_FORMAT,
                        Price.class.getSimpleName()),
                property::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(
                VALID_ADDRESS, "abc", VALID_SIZE, null, VALID_TYPE);

        assertThrows(IllegalValueException.class,
                Price.MESSAGE_CONSTRAINTS,
                property::toModelType);
    }

    @Test
    public void toModelType_nullSize_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(
                VALID_ADDRESS, VALID_PRICE, null, null, VALID_TYPE);

        assertThrows(IllegalValueException.class,
                String.format(JsonAdaptedProperty.MISSING_FIELD_MESSAGE_FORMAT,
                        Size.class.getSimpleName()),
                property::toModelType);
    }

    @Test
    public void toModelType_invalidSize_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(
                VALID_ADDRESS, VALID_PRICE, "abc", null, VALID_TYPE);

        assertThrows(IllegalValueException.class,
                Size.MESSAGE_CONSTRAINTS,
                property::toModelType);
    }

    @Test
    public void toModelType_invalidPropertyType_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(
                VALID_ADDRESS, VALID_PRICE, VALID_SIZE, null, "@@@invalid");

        assertThrows(IllegalValueException.class,
                PropertyType.MESSAGE_CONSTRAINTS,
                property::toModelType);
    }

    @Test
    public void constructor_fromPropertySource_success() throws Exception {
        Property source = new Property(
                new PropertyAddress(VALID_ADDRESS),
                new Price(VALID_PRICE),
                new Size(VALID_SIZE),
                new PropertyType(VALID_TYPE));
        source.setRemarks(VALID_REMARKS);

        JsonAdaptedProperty adapted = new JsonAdaptedProperty(source);
        Property result = adapted.toModelType();

        assertEquals(new PropertyAddress(VALID_ADDRESS), result.getAddress());
        assertEquals(new Price(VALID_PRICE), result.getPrice());
        assertEquals(new Size(VALID_SIZE), result.getSize());
        assertEquals(new PropertyType(VALID_TYPE), result.getPropertyType());
        assertEquals(VALID_REMARKS, result.getRemarks());
    }

    @Test
    public void constructor_fromPropertySourceNoTypeNoRemarks_success() throws Exception {
        Property source = new Property(
                new PropertyAddress(VALID_ADDRESS),
                new Price(VALID_PRICE),
                new Size(VALID_SIZE),
                new PropertyType(VALID_TYPE));

        JsonAdaptedProperty adapted = new JsonAdaptedProperty(source);
        Property result = adapted.toModelType();

        assertEquals(new PropertyAddress(VALID_ADDRESS), result.getAddress());
        assertEquals(new Price(VALID_PRICE), result.getPrice());
        assertEquals(new Size(VALID_SIZE), result.getSize());
        assertEquals(new PropertyType(VALID_TYPE), result.getPropertyType());
        assertNull(result.getRemarks());
    }
}
