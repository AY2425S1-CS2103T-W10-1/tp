package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_NAME_CANNOT_BE_EMPTY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.CompositePredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.StudentIdMatchesPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_missingPrefixes_throwsParseException() {
        // No prefixes provided
        assertParseFailure(parser, "Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        // Empty input
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameArg_returnsFindCommand() {
        // Valid name search
        CompositePredicate predicate = new CompositePredicate();
        predicate.addPredicate(new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        FindCommand expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, "/n Alice", expectedFindCommand);
    }

    @Test
    public void parse_validStudentIdArg_returnsFindCommand() {
        // Valid student ID search
        CompositePredicate predicate = new CompositePredicate();
        predicate.addPredicate(new StudentIdMatchesPredicate("A1234567E"));
        FindCommand expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, "/id A1234567E", expectedFindCommand);
    }

    @Test
    public void parse_validCombinedArgs_returnsFindCommand() {
        // Valid combined search
        CompositePredicate predicate = new CompositePredicate();
        predicate.addPredicate(new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        predicate.addPredicate(new StudentIdMatchesPredicate("A1234567E"));
        FindCommand expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, "/n Alice /id A1234567E", expectedFindCommand);
    }
    @Test
    public void parse_emptyName_throwsParseException() {
        // Empty name
        assertParseFailure(parser, "/n ",
                MESSAGE_NAME_CANNOT_BE_EMPTY);
    }

}

