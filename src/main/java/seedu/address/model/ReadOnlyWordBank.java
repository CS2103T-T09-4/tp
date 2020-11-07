package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.deck.Deck;


/**
 * Unmodifiable view of a word bank
 */
public interface ReadOnlyWordBank {

    /*
     *
     * Returns an unmodifiable view of the entries list.
     * This list will not contain any duplicate entries.
     */
    /*ObservableList<Entry> getEntryList();*/

    /**
     * Returns an unmodifiable view of the deck list.
     * This list will not contain any duplicate decks.
     */
    ObservableList<Deck> getDeckList();

}
