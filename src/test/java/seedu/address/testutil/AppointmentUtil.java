package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import seedu.address.logic.commands.appointment.AddAppointmentCommand;
import seedu.address.model.appointment.Appointment;

/**
 * A utility class for Appointment.
 */
public class AppointmentUtil {

    /**
     * Returns an add command string for adding the {@code appointment}.
     */
    public static String getAddCommand(Appointment appointment) {
        return AddAppointmentCommand.COMMAND_WORD + " " + getAppointmentDetails(appointment);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getAppointmentDetails(Appointment appointment) {
        StringBuilder sb = new StringBuilder();
        sb.append("1 ");
        sb.append(PREFIX_DATE + appointment.getAppointmentTime().getFormattedDateTime());
        return sb.toString();
    }

    // /**
    //  * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
    //  */
    // public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
    //     StringBuilder sb = new StringBuilder();
    //     descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
    //     descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
    //     descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
    //     if (descriptor.getTags().isPresent()) {
    //         Set<Tag> tags = descriptor.getTags().get();
    //         if (tags.isEmpty()) {
    //             sb.append(PREFIX_TAG);
    //         } else {
    //             tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
    //         }
    //     }
    //     return sb.toString();
    // }
}
