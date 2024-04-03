package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ExportCommandTest {

    private static final Path EMPTY_PERSONS_PATH = Paths.get("src", "test", "data",
            "JsonAddressBookStorageTest", "emptyPersonAddressBook.json");
    private static final Path INVALID_JSON_FORMAT_PATH = Paths.get("src", "test", "data",
            "JsonAddressBookStorageTest", "notJsonFormatAddressBook.json");
    private static final Path NO_PERSONS_ARRAY_PATH = Paths.get("src", "test", "data",
            "JsonAddressBookStorageTest", "noPersonArray.json");
    private static final Path WRONG_FILE_PATH = Paths.get("src", "test", "data",
            "JsonAddressBookStorageTest", "wrongFilePath.json");
    private static final Path TYPICAL_PERSONS_PATH = Paths.get("src", "test", "data",
            "JsonSerializableAddressBookTest", "typicalPersonsAddressBook.json");
    private ExportCommand exportCommand = new ExportCommand();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
    private Model modelWithWrongFilePath = new ModelManager(new AddressBook(), new UserPrefs());
    private Model modelWithInvalidJsonFormat = new ModelManager(new AddressBook(), new UserPrefs());
    private Model modelWithNoPersonsArray = new ModelManager(new AddressBook(), new UserPrefs());
    private Model modelWithInvalidStructure = new ModelManager(new AddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        model.setAddressBookFilePath(TYPICAL_PERSONS_PATH);
        emptyModel.setAddressBookFilePath(EMPTY_PERSONS_PATH);
        modelWithWrongFilePath.setAddressBookFilePath(WRONG_FILE_PATH);
        modelWithInvalidJsonFormat.setAddressBookFilePath(INVALID_JSON_FORMAT_PATH);
        modelWithNoPersonsArray.setAddressBookFilePath(NO_PERSONS_ARRAY_PATH);
    }

    @Test
    public void updateCsvFilePathTest_success() {
        String newFilePath = "./src/test/data/RapidTracer/patientData.csv";
        exportCommand.updateCsvFilePath(newFilePath);
        assertEquals(newFilePath, exportCommand.getCsvFilePath());
    }

    @Test
    public void execute_validAddressBookExport_success() throws Exception {
        exportCommand.updateCsvFilePath("./src/test/data/RapidTracer/patientData.csv");

        CommandResult commandResult = exportCommand.execute(model);
        assertEquals(exportCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());

        File csvFile = new File("./src/test/data/RapidTracer/patientData.csv");
        assertTrue(csvFile.exists());
    }

    @Test
    public void execute_unableToCreateCsvDirectory_throwsCommandException() throws Exception {
        exportCommand.updateCsvFilePath("~/nonexistent/path");

        CommandException thrown = assertThrows(CommandException.class, () -> exportCommand.execute(model));
        assertEquals("Unable to create the directory for the CSV file.", thrown.getMessage());
    }

    @Test
    public void execute_withoutValidJsonFilePath_throwsCommandException() {
        CommandException thrown = assertThrows(CommandException.class, () -> exportCommand
                .execute(modelWithWrongFilePath));
        assertEquals("Unable to find the JSON file.", thrown.getMessage());
    }

    @Test
    public void execute_withInvalidJsonFormat_throwsCommandException() {
        CommandException thrown = assertThrows(CommandException.class, () -> exportCommand
                .execute(modelWithInvalidJsonFormat));
        assertEquals("Error occurred while parsing JSON file.", thrown.getMessage());
    }

    @Test
    public void execute_emptyPersonsAddressBook_throwsCommandException() {
        CommandException thrown = assertThrows(CommandException.class, () -> exportCommand.execute(emptyModel));
        assertEquals("No patients data stored in the JSON file", thrown.getMessage());
    }

    @Test
    public void execute_noPersonsArrayAddressBook_throwsCommandException() {
        CommandException thrown = assertThrows(CommandException.class, () -> exportCommand
                .execute(modelWithNoPersonsArray));
        assertEquals("No patients data stored in the JSON file", thrown.getMessage());
    }
}

