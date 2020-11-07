package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.UniqueDeckList;
import seedu.address.model.deck.entry.Entry;
import seedu.address.model.deck.entry.UniqueEntryList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameEntry comparison)
 */
public class WordBank implements ReadOnlyWordBank {

    /*private final UniqueEntryList entries;*/
    private final UniqueDeckList observedDecks;
    private UniqueEntryList observedEntries;
    private FilteredList<Entry> filteredEntries;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        observedDecks = new UniqueDeckList();
        observedEntries = new UniqueEntryList();
        filteredEntries = new FilteredList<>(observedEntries.asUnmodifiableObservableList());
    }

    public WordBank() {
    }

    /**
     * Creates an WordBank using the Entries in the {@code toBeCopied}
     */
    public WordBank(ReadOnlyWordBank toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    // get list operations
    public UniqueEntryList getObservedEntries() {
        return observedEntries;
    }

    public FilteredList<Entry> getFilteredEntries() {
        return filteredEntries;
    }

    /**
     * This function directly modifies what is seen on the GUI
     *
     * @param entry refers to the entry added by the user
     */
    public void addEntry(Entry entry) {
        observedEntries.add(entry);
    }

    /**
     * Resets the observable entry list without clearing the memory of the deck
     * */
    public void resetEntryList() {
        Iterator<Entry> iterator = observedEntries.iterator(); //create iterator
        ArrayList<Entry> copy = new ArrayList<Entry>(); //initialise a copy
        while (iterator.hasNext()) { //fill the empty copy ArrayList with the existing entries
            copy.add(iterator.next()); //this avoids the concurrentModification exception
        }
        for (Entry entry : copy) { //for each entry in the copy, delete the same entry in the observedEntries
            observedEntries.remove(entry); //this changes the GUI
        }
    }

    /**
     * Replaces the current observed entry list when given a UniqueEntryList object
     * */
    public void replaceEntryList(UniqueEntryList newEntryList) {
        for (Entry entry : newEntryList) { //for each entry in the new selected deck entryList
            observedEntries.add(entry); //add it to the GUI
        }
    }

    /**
     * Replaces the current observed entry list when given a UniqueEntryList object
     * Only called during quiz mode
     */
    public void replaceEntryListLeitner (UniqueEntryList newEntryList) {
        for (Entry entry : newEntryList) {
            observedEntries.addLeitner(entry);
        }
    }

    /**
     * Replaces the contents of the entry list with {@code entries}.
     * {@code entries} must not contain duplicate entries.
     */
    public void setDecks(List<Deck> decks) {
        this.observedDecks.setDecks(decks);
    }

    /**
     * Resets the existing data of this {@code WordBank} with {@code newData}.
     */
    public void resetData(ReadOnlyWordBank newData) {
        requireNonNull(newData);

        setDecks(newData.getDeckList());
    }

    //// deck-level operations

    /**
     * Returns true if a deck with the same identity as {@code deck} exists in the word bank.
     */
    public boolean hasDeck(Deck deck) {
        requireNonNull(deck);
        return observedDecks.contains(deck);
    }

    /**
     * Removes {@code key} from this {@code WordBank}.
     * {@code key} must exist in the word bank.
     */
    public void removeDeck(Deck key) {
        observedDecks.remove(key);
    }

    /**
     * Adds a deck to the word bank.
     * The deck must not already exist in the word bank.
     */
    public void addDeck(Deck deck) {
        observedDecks.add(deck);
    }

    //// util methods

    @Override
    public String toString() {
        return observedDecks.asUnmodifiableObservableList().size() + " decks";
    }

    @Override
    public ObservableList<Deck> getDeckList() {
        return observedDecks.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WordBank // instanceof handles nulls
                && observedDecks.equals(((WordBank) other).observedDecks));
    }

    @Override
    public int hashCode() {
        return observedDecks.hashCode();
    }

    public void setEntry(Entry target, Entry editedEntry) {
        requireNonNull(editedEntry);
        observedEntries.setEntry(target, editedEntry);
    }
}
