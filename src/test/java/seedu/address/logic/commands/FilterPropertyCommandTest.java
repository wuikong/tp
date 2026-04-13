package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.FilterPropertyCommand.MESSAGE_PROPERTIES_LISTED;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.property.Price;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyAddress;
import seedu.address.model.property.PropertyMatchesFilterPredicate;
import seedu.address.model.property.PropertyType;
import seedu.address.model.property.Size;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterPropertyCommand}.
 */
public class FilterPropertyCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        addPropertyToModel(model, INDEX_FIRST_PERSON, "311 Clementi Ave 2", "1200000", "1200", "HDB");
        addPropertyToModel(model, INDEX_FIRST_PERSON, "50 Jurong East St 24", "850000", "1000", "Condo");
        addPropertyToModel(model, INDEX_SECOND_PERSON, "10 Punggol Walk", "950000", "1100", "HDB");

        addPropertyToModel(expectedModel, INDEX_FIRST_PERSON, "311 Clementi Ave 2", "1200000", "1200", "HDB");
        addPropertyToModel(expectedModel, INDEX_FIRST_PERSON, "50 Jurong East St 24", "850000", "1000", "Condo");
        addPropertyToModel(expectedModel, INDEX_SECOND_PERSON, "10 Punggol Walk", "950000", "1100", "HDB");
    }

    @Test
    public void equals() {
        PropertyMatchesFilterPredicate firstPredicate =
                new PropertyMatchesFilterPredicate(Collections.singletonList("first"),
                        Collections.emptyList(), 0, Long.MAX_VALUE, 0, Long.MAX_VALUE);
        PropertyMatchesFilterPredicate secondPredicate =
                new PropertyMatchesFilterPredicate(Collections.singletonList("second"),
                        Collections.emptyList(), 0, Long.MAX_VALUE, 0, Long.MAX_VALUE);

        FilterPropertyCommand findFirstCommand = new FilterPropertyCommand(firstPredicate);
        FilterPropertyCommand findSecondCommand = new FilterPropertyCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FilterPropertyCommand findFirstCommandCopy = new FilterPropertyCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_addressKeyword_noPropertyFound() {
        String expectedMessage = String.format(MESSAGE_PROPERTIES_LISTED, 0);
        PropertyMatchesFilterPredicate predicate = preparePredicate("Orchard");
        FilterPropertyCommand command = new FilterPropertyCommand(predicate);
        expectedModel.updateFilteredPropertyList(predicate);
        expectedModel.updateFilteredPersonList(person -> expectedModel.getFilteredPropertyList().stream()
                .anyMatch(property -> person.getProperties().contains(property)));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPropertyList());
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePropertiesAndOwnersFound() {
        String expectedMessage = String.format(MESSAGE_PROPERTIES_LISTED, 2);
        PropertyMatchesFilterPredicate predicate = preparePredicate("Clementi Punggol");
        FilterPropertyCommand command = new FilterPropertyCommand(predicate);
        expectedModel.updateFilteredPropertyList(predicate);
        expectedModel.updateFilteredPersonList(person -> expectedModel.getFilteredPropertyList().stream()
                .anyMatch(property -> person.getProperties().contains(property)));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(2, model.getFilteredPropertyList().size());
        assertEquals(2, model.getFilteredPersonList().size());
        assertTrue(model.getFilteredPersonList().stream()
                .allMatch(person -> model.getFilteredPropertyList().stream()
                        .anyMatch(property -> person.getProperties().contains(property))));
    }

    @Test
    public void execute_priceRangeAndSizeRange_propertiesAndOwnersFound() {
        String expectedMessage = String.format(MESSAGE_PROPERTIES_LISTED, 2);
        PropertyMatchesFilterPredicate predicate =
                new PropertyMatchesFilterPredicate(Collections.emptyList(), Collections.emptyList(),
                        900000, 1300000, 1050, 1300);
        FilterPropertyCommand command = new FilterPropertyCommand(predicate);
        expectedModel.updateFilteredPropertyList(predicate);
        expectedModel.updateFilteredPersonList(person -> expectedModel.getFilteredPropertyList().stream()
                .anyMatch(property -> person.getProperties().contains(property)));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(2, model.getFilteredPropertyList().size());
        assertEquals(2, model.getFilteredPersonList().size());
    }

    @Test
    public void toStringMethod() {
        PropertyMatchesFilterPredicate predicate =
                new PropertyMatchesFilterPredicate(Arrays.asList("keyword"),
                        Collections.emptyList(), 0, Long.MAX_VALUE, 0, Long.MAX_VALUE);
        FilterPropertyCommand filterPropertyCommand = new FilterPropertyCommand(predicate);
        String expected = FilterPropertyCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, filterPropertyCommand.toString());
    }

    @Test
    public void execute_typeKeyword_propertiesAndOwnersFound() {
        String expectedMessage = String.format(MESSAGE_PROPERTIES_LISTED, 1);
        PropertyMatchesFilterPredicate predicate =
                new PropertyMatchesFilterPredicate(Collections.emptyList(), Collections.singletonList("Condo"),
                        0, Long.MAX_VALUE, 0, Long.MAX_VALUE);
        FilterPropertyCommand command = new FilterPropertyCommand(predicate);
        expectedModel.updateFilteredPropertyList(predicate);
        expectedModel.updateFilteredPersonList(person -> expectedModel.getFilteredPropertyList().stream()
                .anyMatch(property -> person.getProperties().contains(property)));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(1, model.getFilteredPropertyList().size());
        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Parses {@code userInput} into a {@code PropertyMatchesFilterPredicate} with no price/size restriction.
     */
    private PropertyMatchesFilterPredicate preparePredicate(String userInput) {
        return new PropertyMatchesFilterPredicate(Arrays.asList(userInput.split("\\s+")),
                Collections.emptyList(), 0, Long.MAX_VALUE, 0, Long.MAX_VALUE);
    }

    private void addPropertyToModel(Model model, Index index, String address, String price, String size, String type)
            throws Exception {
        Property property = new Property(new PropertyAddress(address), new Price(price), new Size(size),
                new PropertyType(type));
        new AddPropertyCommand(index, property).execute(model);
    }
}
