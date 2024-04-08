package seedu.address.ui.person;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.ui.MainWindow;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "personView/PersonListPanel.fxml";

    // private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    private final MainWindow mainWindow;
    private final ObservableList<Appointment> appointmentList;

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     * {@code personListView} also updates whenever changes are made to
     * {@code appointmentList}.
     */
    public PersonListPanel(ObservableList<Person> personList, ObservableList<Appointment> appointmentList,
            MainWindow mainWindow) {
        super(FXML);
        this.mainWindow = mainWindow;
        this.appointmentList = appointmentList;
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

        appointmentList.addListener((ListChangeListener<Appointment>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasPermutated() || change.wasRemoved() || change.wasReplaced()
                        || change.wasUpdated()) {
                    personListView.refresh();
                }
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using
     * a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                List<Appointment> appointments = findAppointmentsForPerson(person);
                setGraphic(new PersonCard(person, appointments, getIndex() + 1, mainWindow).getRoot());
            }
        }
    }

    private List<Appointment> findAppointmentsForPerson(Person person) {
        return appointmentList.stream()
                .filter(a -> a.getPersonId().equals(person.getId()))
                .collect(Collectors.toList());
    }

}
