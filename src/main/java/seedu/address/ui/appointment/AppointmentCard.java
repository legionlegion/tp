package seedu.address.ui.appointment;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.ui.MainWindow;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of an {@code Appointment}.
 */
public class AppointmentCard extends UiPart<Region> {

    private static final String FXML = "appointmentView/AppointmentListCard.fxml";

    public final Appointment appointment;

    public final Person person;

    private final MainWindow mainWindow;

    private int displayedIndex;

    @FXML
    private HBox appointmentCardPane;
    @FXML
    private Label appointmentDisplayedIndex;
    @FXML
    private Label nameOfAppointmentHolder;
    @FXML
    private Label appointmentTime;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to
     * display.
     */
    public AppointmentCard(Appointment appointment, Person person, int displayedIndex, MainWindow mainWindow) {
        super(FXML);
        this.displayedIndex = displayedIndex;
        this.appointment = appointment;
        this.person = person;
        this.mainWindow = mainWindow;

        // Display
        appointmentDisplayedIndex.setText(displayedIndex + ". ");
        nameOfAppointmentHolder.setText("Patient: " + person.getName().fullName);
        appointmentTime.setText(appointment.getAppointmentTimeString());
    }

    /**
     * Traces close contacts of selected appointment
     */
    @FXML
    public void onClick(MouseEvent e) {
        if (e.getClickCount() == 2) {
            String commandText = "trace " + displayedIndex;
            try {
                mainWindow.executeCommand(commandText);
            } catch (CommandException | ParseException err) {
                return;
            }
        }
    }
}
