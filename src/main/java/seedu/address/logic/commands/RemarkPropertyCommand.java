package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.property.Property;

/**
 * Updates the remarks of a property in the currently displayed property list.
 */
public class RemarkPropertyCommand extends Command {

    public static final String COMMAND_WORD = "remarkproperty";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the remark of the property identified "
            + "by the index number in the currently displayed property list.\n"
            + "Parameters: INDEX (must be a positive integer) r/REMARK\n"
            + "Example: " + COMMAND_WORD + " 1 r/Near MRT";

    public static final String MESSAGE_SUCCESS = "Updated remark for property: %1$s";

    private final Index propertyIndex;
    private final String remark;

    /**
     * @param propertyIndex index of the property in the currently displayed property list
     * @param remark the new remark to set
     */
    public RemarkPropertyCommand(Index propertyIndex, String remark) {
        requireNonNull(propertyIndex);
        requireNonNull(remark);
        this.propertyIndex = propertyIndex;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> filteredPersonList = model.getFilteredPersonList();
        if (filteredPersonList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NO_PROPERTY);
        }

        Person currentPerson = filteredPersonList.get(0);
        List<Property> properties = new ArrayList<>(currentPerson.getProperties());

        if (properties.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NO_PROPERTY);
        }

        if (propertyIndex.getZeroBased() >= properties.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
        }

        // Create updated property with new remark
        Property updatedProperty = properties.get(propertyIndex.getZeroBased()).withRemarks(remark);
        properties.set(propertyIndex.getZeroBased(), updatedProperty);

        // Rebuild person with updated property set
        Person updatedPerson = new Person(
                currentPerson.getName(),
                currentPerson.getPhone(),
                currentPerson.getEmail(),
                currentPerson.getTags(),
                new HashSet<>(properties)
        );

        model.setPerson(currentPerson, updatedPerson);
        // Keep the filtered view on this person
        model.updateFilteredPersonList(p -> p.isSamePerson(updatedPerson));

        return new CommandResult(String.format(MESSAGE_SUCCESS, updatedProperty));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof RemarkPropertyCommand)) {
            return false;
        }
        RemarkPropertyCommand otherCommand = (RemarkPropertyCommand) other;
        return propertyIndex.equals(otherCommand.propertyIndex)
                && remark.equals(otherCommand.remark);
    }
}