package seedu.address.model.property;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents a property listing.
 */
public class Property {
    private final PropertyAddress address;
    private final Price price;
    private final Size size;
    private String remarks;

    /**
     * Constructs a {@code Property}.
     *
     * @param address A valid property address.
     * @param price A valid property price.
     * @param size A valid property size.
     */
    public Property(PropertyAddress address, Price price, Size size) {
        requireNonNull(address);
        requireNonNull(price);
        requireNonNull(size);
        this.address = address;
        this.price = price;
        this.size = size;
    }

    public PropertyAddress getAddress() {
        return address;
    }

    public Price getPrice() {
        return price;
    }

    public Size getSize() {
        return size;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * Creates a copy of the property with updated remarks.
     */
    public Property withRemarks(String remarks) {
        Property updated = new Property(this.address, this.price, this.size);
        updated.setRemarks(remarks);
        return updated;
    }

    /**
     * Returns true if both properties have the same address.
     */
    public boolean isSameProperty(Property otherProperty) {
        if (otherProperty == this) {
            return true;
        }

        return otherProperty != null
                && address.equals(otherProperty.address);
    }

    @Override
    public String toString() {
        return "Address: " + address + ", Price: " + price + ", Size: " + size + ", Remarks: " + remarks;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Property)) {
            return false;
        }

        Property otherProperty = (Property) other;
        return address.equals(otherProperty.address)
                && price.equals(otherProperty.price)
                && size.equals(otherProperty.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, price, size);
    }
}
