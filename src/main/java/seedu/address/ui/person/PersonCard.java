package seedu.address.ui.person;

import java.util.Comparator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.ui.MainWindow;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "personView/PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved
     * keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The
     *      issue on AddressBook level 4</a>
     */

    public final Person person;

    private final MainWindow mainWindow;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane associatedAppointments;
    @FXML
    private HBox tagsIcon;

    /**
     * Creates a {@code PersonCode} with the given {@code Person}, its associated
     * {@code Appointments}, and index to display.
     */
    public PersonCard(Person person, List<Appointment> appointments, int displayedIndex, MainWindow mainWindow) {
        super(FXML);
        this.person = person;
        this.mainWindow = mainWindow;

        // Display
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        appointments.stream()
                .sorted()
                .findFirst()
                .ifPresent(appointment -> associatedAppointments.getChildren().add(new Label(appointment.getAppointmentTimeString())));
        boolean hasTags = !person.getTags().isEmpty();
        tagsIcon.setVisible(hasTags); // This will show the icon only if there are tags
        tagsIcon.setManaged(hasTags);
    }

    /**
     * Displays all appointments of selected person
     */
    @FXML
    public void onClick(MouseEvent e) {
        if (e.getClickCount() == 2) {
            String commandText = "findappt " + this.person.getName().fullName;
            try {
                mainWindow.executeCommand(commandText);
            } catch (CommandException | ParseException err) {
                return;
            }
        }
    }
}
