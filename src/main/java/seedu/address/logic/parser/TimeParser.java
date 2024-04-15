package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentTime;

/**
 * Parses input arguments and creates a new AppointmentTime object
 */
public class TimeParser {
    /**
     * The ideal format for an AppointmentTime is:
     * dd/MM/yyyy [x]am-[y]pm
     */
    private static final Pattern DAY =
            Pattern.compile("[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]");
    private static final Pattern TODAY = Pattern.compile("(?i)(today|tdy)");
    private static final Pattern HOUR = Pattern.compile("([1-9]|1[0-2])(?i)[ap]m");
    private static final Pattern HOUR_WINDOW = Pattern.compile(HOUR + "([ ]?-[ ]?)" + HOUR);
    private static final Pattern DATE_APPOINTMENT_TIME = Pattern.compile(DAY + " " + HOUR_WINDOW);
    private static final Pattern TODAY_APPOINTMENT_TIME = Pattern.compile(TODAY + " " + HOUR_WINDOW);
    private static final String MESSAGE_USAGE = "Use dd/MM/yyyy [x]am-[y]pm";
    private static final String EMPTY_DATE_MESSAGE = "Please fill in a date!";
    public static final String MESSAGE_END_BEFORE_START = "End time must be strictly after start time!";
    public static final String INVALID_DATE = "Please provide a valid date! Appointments can be between 6 Feb 1819 and 1 Jan 2101";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Parses a {@code String appointmentTime} into a {@code AppointmentTime}.
     * The expected format is dd/MM/yyyy [x]am-[y]pm.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public static AppointmentTime parse(String args) throws ParseException {
        if (args.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimeParser.EMPTY_DATE_MESSAGE));
        }

        String uppercaseAppointmentTime = args.toUpperCase();
        Matcher matchDate = TimeParser.DATE_APPOINTMENT_TIME.matcher(args);
        Matcher matchToday = TimeParser.TODAY_APPOINTMENT_TIME.matcher(args);

        if (!matchDate.matches() && !matchToday.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimeParser.MESSAGE_USAGE));
        }

        if (matchToday.matches()) { // format of command is d/tdy TIME
            if (uppercaseAppointmentTime.startsWith("TODAY")) { // Format: today time
                if (validAppointmentWindow(uppercaseAppointmentTime.substring(6))) {
                    LocalDate today = LocalDate.now();
                    String todayString = today.format(DATE_FORMAT);
                    return new AppointmentTime(todayString + " " + args.split(" ")[1].trim());
                } else {
                    throw new ParseException(String.format(MESSAGE_END_BEFORE_START, TimeParser.MESSAGE_USAGE));
                }
            } else if (uppercaseAppointmentTime.startsWith("TDY")) { // Format: tdy time
                if (validAppointmentWindow(uppercaseAppointmentTime.substring(4))) {
                    LocalDate today = LocalDate.now();
                    String todayString = today.format(DATE_FORMAT);
                    return new AppointmentTime(todayString + " " + args.split(" ")[1].trim());
                } else {
                    throw new ParseException(String.format(MESSAGE_END_BEFORE_START, TimeParser.MESSAGE_USAGE));
                }
            }
        }

        if (!validAppointmentDate(args.substring(0, 10))) {
            throw new ParseException(String.format(INVALID_DATE, TimeParser.MESSAGE_USAGE));
        }

        return new AppointmentTime(args);
    }

    /**
     * Checks a time to make sure it is valid.
     *
     * @return boolean
     */
    public static boolean validAppointmentWindow(String args) {
        String[] times = args.replace(" ", "").toUpperCase().split("-");
        assert times.length == 2;

        int startHour = Integer.parseInt(times[0].substring(0, times[0].length() - 2));
        assert startHour > 0;
        assert startHour <= 12;

        if (times[0].equals("12AM")) {
            startHour = 0;
        } else if (times[0].equals("12PM")) {
            startHour = 12;
        } else if (times[0].endsWith("PM")) {
            startHour += 12;
        }

        int endHour = Integer.parseInt(times[1].substring(0, times[1].length() - 2));
        assert endHour > 0;
        assert endHour <= 12;

        if (times[1].equals("12AM")) {
            endHour = 0;
        } else if (times[1].equals("12PM")) {
            endHour = 12;
        } else if (times[1].endsWith("PM")) {
            endHour += 12;
        }

        return endHour > startHour;
    }

    /**
     * Checks whether the date is in dd/MM/yyyy format and is within a valid window.
     *
     * @return boolean
     */
    public static boolean validAppointmentDate(String args) {
        assert args != null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(args);
            if (date.before(sdf.parse("06/02/1819"))) {
                return false;
            }
            if (date.after(sdf.parse("01/01/2100"))) {
                return false;
            }
            return true;
        } catch (java.text.ParseException e) {
            return false;
        }
    }
}
