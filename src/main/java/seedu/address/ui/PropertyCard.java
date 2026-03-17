package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.property.Property;

/**
 * An UI component that displays information of a {@code Property}.
 */
public class PropertyCard extends UiPart<Region> {

    private static final String FXML = "PropertyCard.fxml";

    public final Property property;

    @FXML
    private HBox propertyCardPane;
    @FXML
    private Label propertyId;
    @FXML
    private Label propertyAddress;
    @FXML
    private Label price;
    @FXML
    private Label size;
    @FXML
    private FlowPane tags;
    @FXML
    private Label remarks;

    /**
     * Creates a {@code PropertyCard} with the given {@code Property} and index to display.
     */
    public PropertyCard(Property property, int displayedIndex) {
        super(FXML);
        this.property = property;
        propertyId.setText(displayedIndex + ". ");
        propertyAddress.setText(property.getAddress().toString());
        price.setText("Price: $" + property.getPrice());
        size.setText("Size: " + property.getSize() + " sqft");

        String propertyRemarks = property.getRemarks();
        if (propertyRemarks != null && !propertyRemarks.isBlank()) {
            remarks.setText("Remarks: " + propertyRemarks);
        } else {
            remarks.setVisible(false);
            remarks.setManaged(false);
        }
    }
}
