package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.property.Property;

/**
 * Panel containing the list of properties.
 */
public class PropertyListPanel extends UiPart<Region> {
    private static final String FXML = "PropertyListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PropertyListPanel.class);

    @FXML
    private ListView<Property> propertyListView;

    /**
     * Creates a {@code PropertyListPanel} that displays properties from the given person list.
     */
    public PropertyListPanel(ObservableList<Person> personList) {
        super(FXML);
        propertyListView.setCellFactory(listView -> new PropertyListViewCell());
        updateProperties(personList);
        personList.addListener((ListChangeListener<Person>) change -> updateProperties(personList));
    }

    /**
     * Updates the property list view with properties from all persons in the list.
     */
    private void updateProperties(ObservableList<Person> personList) {
        ObservableList<Property> properties = FXCollections.observableArrayList();
        for (Person person : personList) {
            properties.addAll(person.getProperties());
        }
        propertyListView.setItems(properties);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Property} using a {@code PropertyCard}.
     */
    class PropertyListViewCell extends ListCell<Property> {
        @Override
        protected void updateItem(Property property, boolean empty) {
            super.updateItem(property, empty);

            if (empty || property == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PropertyCard(property, getIndex() + 1).getRoot());
            }
        }
    }
}
