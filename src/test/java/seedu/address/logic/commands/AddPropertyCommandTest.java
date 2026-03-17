package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.property.Price;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyAddress;
import seedu.address.model.property.Size;

public class AddPropertyCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private Property validProperty = new Property(
            new PropertyAddress("311 Clementi Ave 2, #02-25"),
            new Price("1200000"),
            new Size("1200")
    );

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPropertyCommand(null, validProperty));
    }

    @Test
    public void constructor_nullProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPropertyCommand(INDEX_FIRST_PERSON, null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws CommandException {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        AddPropertyCommand addCommand = new AddPropertyCommand(INDEX_FIRST_PERSON, validProperty);

        Person editedPerson = personToEdit.addProperty(validProperty);

        String expectedMessage = String.format(
                AddPropertyCommand.MESSAGE_SUCCESS,
                personToEdit.getName(),
                validProperty
        );

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(addCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddPropertyCommand addCommand = new AddPropertyCommand(outOfBoundIndex, validProperty);

        assertCommandFailure(addCommand, model, "The person index provided is invalid.");
    }

    @Test
    public void execute_duplicateProperty_throwsCommandException() throws CommandException {
        AddPropertyCommand addCommand = new AddPropertyCommand(INDEX_FIRST_PERSON, validProperty);
        addCommand.execute(model);

        AddPropertyCommand duplicateCommand = new AddPropertyCommand(INDEX_FIRST_PERSON, validProperty);

        assertCommandFailure(duplicateCommand, model, AddPropertyCommand.MESSAGE_DUPLICATE_PROPERTY);
    }

    @Test
    public void execute_samePropertyToDifferentPersons_success() throws CommandException {
        Model testModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        AddPropertyCommand addCommand1 = new AddPropertyCommand(INDEX_FIRST_PERSON, validProperty);
        AddPropertyCommand addCommand2 = new AddPropertyCommand(
                Index.fromOneBased(2), validProperty);

        Person personToEdit1 = testModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson1 = personToEdit1.addProperty(validProperty);

        String expectedMessage1 = String.format(
                AddPropertyCommand.MESSAGE_SUCCESS,
                personToEdit1.getName(),
                validProperty
        );

        Model expectedModel1 = new ModelManager(testModel.getAddressBook(), new UserPrefs());
        expectedModel1.setPerson(personToEdit1, editedPerson1);

        assertCommandSuccess(addCommand1, testModel, expectedMessage1, expectedModel1);

        // Now execute the second command on the updated model
        Person personToEdit2 = testModel.getFilteredPersonList().get(1);

        String expectedMessage2 = String.format(
                AddPropertyCommand.MESSAGE_SUCCESS,
                personToEdit2.getName(),
                validProperty
        );

        CommandResult result = addCommand2.execute(testModel);
        assertEquals(expectedMessage2, result.getFeedbackToUser());
    }

    @Test
    public void execute_noPersons_throwsCommandException() {
        Model emptyModel = new ModelManager();

        AddPropertyCommand addCommand = new AddPropertyCommand(INDEX_FIRST_PERSON, validProperty);

        assertCommandFailure(addCommand, emptyModel, AddPropertyCommand.MESSAGE_NO_PERSONS);
    }

    @Test
    public void equals() {
        AddPropertyCommand firstCommand = new AddPropertyCommand(INDEX_FIRST_PERSON, validProperty);
        AddPropertyCommand secondCommand = new AddPropertyCommand(INDEX_FIRST_PERSON,
                new Property(new PropertyAddress("123 Street"), new Price("500000"), new Size("1000")));

        AddPropertyCommand firstCommandCopy = new AddPropertyCommand(INDEX_FIRST_PERSON, validProperty);

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(firstCommandCopy));
        assertFalse(firstCommand.equals(null));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void toStringMethod() {
        AddPropertyCommand command = new AddPropertyCommand(INDEX_FIRST_PERSON, validProperty);
        String expected = AddPropertyCommand.class.getCanonicalName()
                + "{targetIndex=" + INDEX_FIRST_PERSON + ", property=" + validProperty + "}";
        assertEquals(expected, command.toString());
    }
}
