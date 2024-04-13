package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Exports the patient information stored in the addressBook to a CSV file located in the data file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_CREATE_DIRECTORY_FAILURE = "Unable to create the directory for the CSV file.";
    public static final String MESSAGE_JSON_FILE_NOT_FOUND_FAILURE = "Unable to find the JSON file.";
    public static final String MESSAGE_PARSE_JSON_FILE_FAILURE = "Error occurred while parsing JSON file.";
    public static final String MESSAGE_NO_PATIENT_FAILURE = "No patients data stored in the JSON file";

    public static final String MESSAGE_SUCCESS = "Exported all patients' information to a CSV file \n"
            + "The CSV file can be found in the RapidTracerData directory.";

    private String csvFilePath = "./RapidTracerData/PatientData.csv";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            File jsonFile = model.getAddressBookFilePath().toFile();
            File csvFile = new File(csvFilePath);

            createDirectory(csvFile);

            JsonNode jsonTree = readJsonFile(jsonFile);
            JsonNode personArray = readPerson(jsonTree);
            writeToCsv(personArray, csvFile);

            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IOException e) {
            throw new CommandException("Error" + e.getMessage());
        }
    }

    /**
     * Gets the current CSV file path.
     *
     * @return The current CSV file path.
     */
    public String getCsvFilePath() {
        return this.csvFilePath;
    }

    /**
     * Updates the CSV file path.
     *
     * @param filePath The file path to change to.
     */
    public void updateCsvFilePath(String filePath) {
        this.csvFilePath = filePath;
    }

    /**
     * Creates the directory for the CSV file.
     *
     * @param csvFile The csvFile where to patient data will be exported to.
     * @throws CommandException If unable to create a directory.
     */
    public void createDirectory(File csvFile) throws CommandException {
        File csvParentDirectory = csvFile.getParentFile();
        if (!csvParentDirectory.exists()) {
            boolean isCreated = csvParentDirectory.mkdir();
            if (!isCreated) {
                throw new CommandException(MESSAGE_CREATE_DIRECTORY_FAILURE);
            }
        }
    }

    /**
     * Reads the JSON file into a tree of JsonNode Objects.
     *
     * @param jsonFile The JSON file to be read.
     * @return A tree of JsonNode Objects.
     * @throws CommandException If an error occurs while reading the JSON file or mapping it to the CSV schema.
     */
    public JsonNode readJsonFile(File jsonFile) throws CommandException {
        try {
            JsonNode jsonTree = new ObjectMapper().readTree(jsonFile);
            return jsonTree;
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                throw new CommandException(MESSAGE_JSON_FILE_NOT_FOUND_FAILURE);
            } else if (e instanceof JsonParseException) {
                throw new CommandException(MESSAGE_PARSE_JSON_FILE_FAILURE);
            } else {
                throw new CommandException("Unexpected error" + e.getMessage());
            }
        }
    }

    /**
     * Reads the patient as an Array from the given tree of JsonNode Objects.
     *
     * @param jsonTree A tree of JsonNode Objects.
     * @return An array of patients.
     * @throws CommandException
     */
    public JsonNode readPerson(JsonNode jsonTree) throws CommandException {
        JsonNode personArray = jsonTree.get("persons");
        if (personArray == null || personArray.size() == 0) {
            throw new CommandException(MESSAGE_NO_PATIENT_FAILURE);
        }
        for (JsonNode person : personArray) {
            if (person instanceof ObjectNode) {
                ObjectNode objectNode = (ObjectNode) person;
                objectNode.remove("id");
            }
        }
        return personArray;
    }

    /**
     * Creates a CsvSchema Builder and set the column headers to match the JSON field names.
     *
     * @param personArray The tree of JsonNode Objects.
     * @return A matching CsvSchema.
     */
    public CsvSchema createCsvSchema(JsonNode personArray) {
        Builder csvSchemaBuilder = CsvSchema.builder();
        JsonNode firstObject = personArray.elements().next();
        firstObject.fieldNames().forEachRemaining(fieldName -> {
            csvSchemaBuilder.addColumn(fieldName); });
        CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
        return csvSchema;
    }

    /**
     * Writes the JsonTree to CSV file.
     *
     * @param jsonTree The tree of JsonNode Objects.
     * @param csvFile the specified csv file.
     * @throws IOException
     */
    public void writeToCsv(JsonNode jsonTree, File csvFile) throws IOException {
        CsvSchema csvSchema = createCsvSchema(jsonTree);
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.writerFor(JsonNode.class)
                .with(csvSchema)
                .writeValue(csvFile, jsonTree);
    }
}
