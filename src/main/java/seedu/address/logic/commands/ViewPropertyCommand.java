package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.Property;

/**
 * Displays properties matching the selected property and the clients who own them.
 */
public class ViewPropertyCommand extends Command {

    public static final String COMMAND_WORD = "viewProperty";

    private final Index index;

    /**
     * Creates a ViewPropertyCommand to view the information of the specified property.
     */
    public ViewPropertyCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Property> lastShownPropertyList = model.getFilteredPropertyList();

        if (index.getZeroBased() >= lastShownPropertyList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
        }

        Property propertyToView = lastShownPropertyList.get(index.getZeroBased());

        model.updateFilteredPropertyList(property -> property.isSameProperty(propertyToView));
        model.updateFilteredPersonList(person -> person.getProperties().stream()
                .anyMatch(personProperty -> model.getFilteredPropertyList().stream()
                        .anyMatch(filteredProperty -> filteredProperty.isSameProperty(personProperty))));

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewPropertyCommand)) {
            return false;
        }

        ViewPropertyCommand otherViewPropertyCommand = (ViewPropertyCommand) other;
        return index.equals(otherViewPropertyCommand.index);
    }
}
