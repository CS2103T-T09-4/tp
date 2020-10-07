package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.DeckName;
import seedu.address.model.deck.entry.Entry;
import seedu.address.model.deck.entry.Translation;
import seedu.address.model.deck.entry.Word;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Entry> filteredEntries;
    private final FilteredList<Deck> filteredDecks;
    private Optional<Index> currentDeckIndex;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredEntries = new FilteredList<>(this.addressBook.getEntryList());
        filteredDecks = new FilteredList<>(this.addressBook.getDeckList());
        currentDeckIndex = Optional.empty();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== Word Bank ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasEntry(Entry entry) {
        requireNonNull(entry);
        Deck currentDeck = getCurrentDeck();
        return currentDeck.hasEntry(entry);
    }

    @Override
    public void deleteEntry(Entry target) {
        Deck currentDeck = getCurrentDeck();
        currentDeck.removeEntry(target);
    }

    @Override
    public void addEntry(Entry entry) {
        Deck currentDeck = getCurrentDeck();
        currentDeck.addEntry(entry);
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
    }

    @Override
    public void setEntry(Entry target, Entry editedEntry) {
        requireAllNonNull(target, editedEntry);

        Deck currentDeck = getCurrentDeck();
        currentDeck.setEntry(target, editedEntry);
    }

    @Override
    public boolean hasDeck(Deck deck) {
        requireNonNull(deck);
        return addressBook.hasDeck(deck);
    }

    @Override
    public void removeDeck(Deck target) {
        addressBook.removeDeck(target);
    }


    @Override
    public void addDeck(Deck deck) {
        addressBook.addDeck(deck);
        updateFilteredDeckList(PREDICATE_SHOW_ALL_DECKS);
    }

    @Override
    public void selectDeck (Index index) {
        currentDeckIndex = Optional.of(index);
    }

    @Override
    public Deck getCurrentDeck() {
        if (currentDeckIndex.equals(Optional.empty())) {
            return null;
        }
        return filteredDecks.get(currentDeckIndex.get().getZeroBased());
    }

    //=========== Filtered Entry List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Entry} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Entry> getFilteredEntryList() {
        Deck currentDeck = getCurrentDeck(); //keeps returning null causing null error
        Deck deck = new Deck(new DeckName("Deck 1"));
        deck.addEntry(new Entry(new Word("StubEntry"), new Translation("Stub o Entry o")));
        deck.addEntry(new Entry(new Word("ScrollBarTestEntry"), new Translation("Scroll o Bar o")));
        deck.addEntry(new Entry(new Word("Vignesh Hurry up"), new Translation("Vigneshu hurry uppo")));
        deck.addEntry(new Entry(new Word("ModelManager.java"), new Translation("Line 173")));
        return deck.getFilteredEntryList();
    }

    @Override
    public void updateFilteredEntryList(Predicate<Entry> predicate) {
        requireNonNull(predicate);
        Deck currentDeck = getCurrentDeck();
        currentDeck.updateFilteredEntryList(predicate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Deck} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Deck> getFilteredDeckList() {
        return filteredDecks;
    }

    @Override
    public void updateFilteredDeckList(Predicate<Deck> predicate) {
        requireNonNull(predicate);
        filteredDecks.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredDecks.equals(other.filteredDecks);
    }
}
