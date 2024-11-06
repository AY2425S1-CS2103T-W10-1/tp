package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_MAXLEADINGZEROS;
import static seedu.address.logic.Messages.MESSAGE_OVERFLOW_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NETID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.Collection;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.list.GroupList;
import seedu.address.model.person.Email;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.StudentId;
import seedu.address.model.person.Year;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Error: Index is not a single non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_TAG_ADD = "If you are trying to use a tag (eg. '/n'), ensure the tag "
            + "is valid for this command and there is a space before it."
            + "\n"
            + "Supported tags: "
            + PREFIX_NAME + ", "
            + PREFIX_STUDENTID + ", "
            + PREFIX_NETID + ", "
            + PREFIX_MAJOR + ", "
            + PREFIX_YEAR + ", "
            + PREFIX_GROUP;

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();

        if (StringUtil.verifyNotNumber(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        if (!StringUtil.verifyNotExcessiveLeadingZeros(trimmedIndex)) {
            throw new ParseException(MESSAGE_MAXLEADINGZEROS);
        }
        if (!StringUtil.verifyNotIntOverflow(trimmedIndex)) {
            throw new ParseException(MESSAGE_OVERFLOW_INDEX);
        }
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String studentId} into a {@code StudentId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code studentId} is invalid.
     */
    public static StudentId parseStudentId(String studentId) throws ParseException {
        requireNonNull(studentId);
        String trimmedStudentId = studentId.trim();
        if (!StudentId.isValidStudentId(trimmedStudentId)) {
            String errorMsg = StudentId.MESSAGE_CONSTRAINTS;

            if (trimmedStudentId.contains("/")) {
                errorMsg = errorMsg + "\n"
                        + MESSAGE_INVALID_TAG_ADD;
            }

            throw new ParseException(errorMsg);
        }
        return new StudentId(trimmedStudentId);
    }

    /**
     * Parses a {@code String major} into a {@code Major}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code major} is invalid.
     */
    public static Major parseMajor(String major) throws ParseException {
        requireNonNull(major);
        String trimmedMajor = major.trim();
        if (!Major.isValidMajor(trimmedMajor)) {
            throw new ParseException(Major.MESSAGE_CONSTRAINTS);
        }
        return Major.makeMajor(trimmedMajor);
    }

    /**
     * Parses a {@code String major} into a {@code Major} but allows empty major to be supplied.
     *
     * @throws ParseException if the given {@code major} is invalid or not empty.
     */
    public static Major parseOptionalMajor(String major) throws ParseException {
        requireNonNull(major);
        String trimmedMajor = major.trim();

        if (trimmedMajor.isEmpty()) {
            return Major.makeMajor(trimmedMajor);
        }

        return parseMajor(trimmedMajor);
    }

    /**
     * Parses a {@code String netId} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code netId} is invalid.
     */
    public static Email parseNetId(String netId) throws ParseException {
        requireNonNull(netId);
        String trimmedNetId = netId.trim();

        if (!Email.isValidNetId(trimmedNetId)) {
            String errorMsg = Email.MESSAGE_CONSTRAINTS;

            if (trimmedNetId.contains("/")) {
                errorMsg = errorMsg + "\n"
                        + MESSAGE_INVALID_TAG_ADD;
            }

            throw new ParseException(errorMsg);
        }

        return Email.makeEmail(trimmedNetId + Email.DOMAIN);
    }

    /**
     * Parses a {@code String netId} into an {@code Email} but allows empty netid to be supplied.
     *
     * @throws ParseException if the given {@code netId} is invalid or not empty.
     */
    public static Email parseOptionalNetId(String netId) throws ParseException {
        requireNonNull(netId);
        String trimmedNetId = netId.trim();

        if (trimmedNetId.isEmpty()) {
            return Email.makeEmail(trimmedNetId);
        }

        return parseNetId(trimmedNetId);
    }

    /**
     * Parses a {@code String year} into a {@code Year}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code year} is invalid.
     */
    public static Year parseYear(String year) throws ParseException {
        requireNonNull(year);
        String trimmedYear = year.trim();
        if (!Year.isValidYear(trimmedYear)) {
            String errorMsg = Year.MESSAGE_CONSTRAINTS;

            if (trimmedYear.contains("/")) {
                errorMsg = errorMsg + "\n"
                        + MESSAGE_INVALID_TAG_ADD;
            }

            throw new ParseException(errorMsg);
        }
        return Year.makeYear(trimmedYear);
    }

    /**
     * Parses a {@code String year} into a {@code Year} but allows empty year to be supplied.
     *
     * @throws ParseException if the given {@code year} is invalid or not empty.
     */
    public static Year parseOptionalYear(String year) throws ParseException {
        requireNonNull(year);
        String trimmedYear = year.trim();

        if (trimmedYear.isEmpty()) {
            return Year.makeYear(trimmedYear);
        }

        return parseYear(trimmedYear);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Group parseGroup(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Group.isValidGroupName(trimmedTag)) {
            throw new ParseException(Group.MESSAGE_CONSTRAINTS);
        }
        return new Group(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> groups} into a {@code Set<Tag>}.
     */
    public static GroupList parseGroups(Collection<String> groups) throws ParseException {
        requireNonNull(groups);
        final GroupList groupList = new GroupList();
        for (String groupName : groups) {
            groupList.addGroup(parseGroup(groupName));
        }
        return groupList;
    }
}
