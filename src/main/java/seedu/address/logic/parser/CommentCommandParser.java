package seedu.address.logic.parser;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Comment;

/**
 * Parses input arguments and creates a new {@code CommentCommand} object
 */
public class CommentCommandParser implements Parser<CommentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code CommentCommand}
     * and returns a {@code CommentCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CommentCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COMMENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_COMMENT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CommentCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_COMMENT);

        Index index;
        index = ParserUtil.parseIndex(argMultimap.getPreamble());
        String comment = argMultimap.getValue(PREFIX_COMMENT).orElse("");
        return new CommentCommand(index, new Comment(comment));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
