package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemarkPropertyCommand;

public class RemarkPropertyCommandParserTest {

    private static final String VALID_REMARK = "Near MRT";
    private static final String REMARK_PREFIX = " r/";

    private final RemarkPropertyCommandParser parser = new RemarkPropertyCommandParser();

    @Test
    public void parse_validArgs_returnsRemarkPropertyCommand() {
        assertParseSuccess(parser, "1" + REMARK_PREFIX + VALID_REMARK,
                new RemarkPropertyCommand(INDEX_FIRST_PERSON, VALID_REMARK));
    }

    @Test
    public void parse_validArgsSecondIndex_returnsRemarkPropertyCommand() {
        assertParseSuccess(parser, "2" + REMARK_PREFIX + VALID_REMARK,
                new RemarkPropertyCommand(INDEX_SECOND_PERSON, VALID_REMARK));
    }

    @Test
    public void parse_emptyRemark_returnsRemarkPropertyCommand() {
        // empty remark should clear the remark
        assertParseSuccess(parser, "1" + REMARK_PREFIX,
                new RemarkPropertyCommand(INDEX_FIRST_PERSON, ""));
    }

    @Test
    public void parse_missingIndex_failure() {
        assertParseFailure(parser, REMARK_PREFIX + VALID_REMARK,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkPropertyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_failure() {
        assertParseFailure(parser, "a" + REMARK_PREFIX + VALID_REMARK,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkPropertyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingRemarkPrefix_failure() {
        assertParseFailure(parser, "1 " + VALID_REMARK,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkPropertyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_failure() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkPropertyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceOnly_failure() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkPropertyCommand.MESSAGE_USAGE));
    }
}
