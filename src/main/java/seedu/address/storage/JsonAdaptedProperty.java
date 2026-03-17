package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.property.Price;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyAddress;
import seedu.address.model.property.PropertyType;
import seedu.address.model.property.Size;

/**
 * Jackson-friendly version of {@link Property}.
 */
public class JsonAdaptedProperty {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Property's %s field is missing!";

    private final String address;
    private final String price;
    private final String size;
    private final String remarks;
    private final String propertyType;

    /**
     * Constructs a {@code JsonAdaptedProperty} with the given property details.
     *
     * @param address The property address string.
     * @param price The property price string.
     * @param size The property size string.
     * @param remarks The property remarks string, may be null.
     * @param propertyType The property type string, may be null.
     */
    @JsonCreator
    public JsonAdaptedProperty(@JsonProperty("address") String address,
                               @JsonProperty("price") String price,
                               @JsonProperty("size") String size,
                               @JsonProperty("remarks") String remarks,
                               @JsonProperty("propertyType") String propertyType) {
        this.address = address;
        this.price = price;
        this.size = size;
        this.remarks = remarks;
        this.propertyType = propertyType;
    }

    /**
     * Converts a given {@code Property} into this class for Jackson use.
     *
     * @param source The property to convert.
     */
    public JsonAdaptedProperty(Property source) {
        address = source.getAddress().value;
        price = source.getPrice().value;
        size = source.getSize().value;
        remarks = source.getRemarks();
        propertyType = source.getPropertyType() != null ? source.getPropertyType().value : null;
    }

    /**
     * Converts this Jackson-friendly adapted property object into the model's {@code Property} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted property.
     */
    public Property toModelType() throws IllegalValueException {
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PropertyAddress.class.getSimpleName()));
        }
        if (!PropertyAddress.isValidPropertyAddress(address)) {
            throw new IllegalValueException(PropertyAddress.MESSAGE_CONSTRAINTS);
        }
        final PropertyAddress modelAddress = new PropertyAddress(address);

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price);

        if (size == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Size.class.getSimpleName()));
        }
        if (!Size.isValidSize(size)) {
            throw new IllegalValueException(Size.MESSAGE_CONSTRAINTS);
        }
        final Size modelSize = new Size(size);

        PropertyType modelPropertyType = null;
        if (propertyType != null) {
            if (!PropertyType.isValidPropertyType(propertyType)) {
                throw new IllegalValueException(PropertyType.MESSAGE_CONSTRAINTS);
            }
            modelPropertyType = new PropertyType(propertyType);
        }

        Property property = new Property(modelAddress, modelPrice, modelSize, modelPropertyType);
        if (remarks != null) {
            property.setRemarks(remarks);
        }
        return property;
    }
}
