package seedu.address.model.property;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Property in the address book.
 */
public class Property {

    private final String address;
    private final String type;
    private final String price;

    public Property(String address, String type, String price) {
        requireNonNull(address);
        requireNonNull(type);
        requireNonNull(price);

        this.address = address;
        this.type = type;
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return address + " | " + type + " | $" + price;
    }
}