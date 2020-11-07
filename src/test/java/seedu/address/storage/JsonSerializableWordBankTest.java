package seedu.address.storage;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
//
//import seedu.address.commons.exceptions.IllegalValueException;
//import seedu.address.commons.util.JsonUtil;
//import seedu.address.model.WordBank;
//import seedu.address.testutil.entry.TypicalEntries;

public class JsonSerializableWordBankTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableWordBankTest");
    private static final Path TYPICAL_ENTRIES_FILE = TEST_DATA_FOLDER.resolve("typicalEntriesAddressBook.json");
    private static final Path INVALID_ENTRY_FILE = TEST_DATA_FOLDER.resolve("invalidEntryWordBank.json");
    private static final Path DUPLICATE_ENTRY_FILE = TEST_DATA_FOLDER.resolve("duplicateEntryAddressBook.json");

    @Test
    public void toModelType_typicalEntriesFile_success() throws Exception {
    //        JsonSerializableWordBank dataFromFile = JsonUtil.readJsonFile(TYPICAL_ENTRIES_FILE,
    //        JsonSerializableWordBank.class).get();
    //        WordBank addressBookFromFile = dataFromFile.toModelType();
    //        WordBank typicalEntriesAddressBook = TypicalEntries.getTypicalAddressBook();
    //        assertEquals(addressBookFromFile, typicalEntriesAddressBook);
    }

    @Test
    public void toModelType_invalidEntryFile_throwsIllegalValueException() throws Exception {
    //        JsonSerializableWordBank dataFromFile = JsonUtil.readJsonFile(INVALID_ENTRY_FILE,
    //                JsonSerializableWordBank.class).get();
    //        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateEntries_throwsIllegalValueException() throws Exception {
    //        JsonSerializableWordBank dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ENTRY_FILE,
    //                JsonSerializableWordBank.class).get();
    //        assertThrows(IllegalValueException.class, JsonSerializableWordBank.MESSAGE_DUPLICATE_DECK,
    //                dataFromFile::toModelType);
    }

}
