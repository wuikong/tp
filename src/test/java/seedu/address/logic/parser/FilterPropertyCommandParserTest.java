package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterPropertyCommand;
import seedu.address.model.property.PropertyAddressContainsKeywordsPredicate;

public class FilterPropertyCommandParserTest {

    private FilterPropertyCommandParser parser = new FilterPropertyCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "Clementi", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterPropertyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterPropertyCommand() {
        // no leading and trailing whitespaces
        FilterPropertyCommand expectedFilterPropertyCommand =
                new FilterPropertyCommand(new PropertyAddressContainsKeywordsPredicate(Arrays.asList(
                        "Clementi", "Punggol")));
        assertParseSuccess(parser, " a/Clementi Punggol", expectedFilterPropertyCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " a/ Clementi \n \t Punggol  \t", expectedFilterPropertyCommand);
    }
}
