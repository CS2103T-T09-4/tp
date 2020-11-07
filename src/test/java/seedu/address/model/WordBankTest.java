package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.deck.TypicalDecks.JAPANESE_DECK;
import static seedu.address.testutil.deck.TypicalDecks.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.exceptions.DuplicateDeckException;
import seedu.address.testutil.deck.DeckBuilder;

public class WordBankTest {

    private final WordBank wordBank = new WordBank();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), wordBank.getDeckList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> wordBank.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        WordBank newData = getTypicalAddressBook();
        wordBank.resetData(newData);
        assertEquals(newData, wordBank);
    }

    @Test
    public void resetData_withDuplicateDecks_throwsDuplicateDeckException() {
        // Two decks with the same identity fields
        Deck editedJapaneseDeck = new DeckBuilder(JAPANESE_DECK).build();
        List<Deck> newDecks = Arrays.asList(JAPANESE_DECK, editedJapaneseDeck);
        WordBankStub newData = new WordBankStub(newDecks);

        assertThrows(DuplicateDeckException.class, () -> wordBank.resetData(newData));
    }

    @Test
    public void hasDeck_nullDeck_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> wordBank.hasDeck(null));
    }

    @Test
    public void hasDeck_deckNotInAddressBook_returnsFalse() {
        assertFalse(wordBank.hasDeck(JAPANESE_DECK));
    }

    @Test
    public void hasDeck_deckInAddressBook_returnsTrue() {
        wordBank.addDeck(JAPANESE_DECK);
        assertTrue(wordBank.hasDeck(JAPANESE_DECK));
    }

    @Test
    public void removeDeck_emptyAddressBook_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> wordBank.getDeckList().remove(0));
    }

    @Test
    public void removeDeck_entryInAddressBook_success() {
        ObservableList deckListCopy = wordBank.getDeckList();
        Deck validDeck = new DeckBuilder().build();
        wordBank.addDeck(validDeck);
        wordBank.removeDeck(validDeck);
        assertEquals(deckListCopy, wordBank.getDeckList());
    }

    @Test
    public void hasDeck_deckWithSameIdentityFieldsInAddressBook_returnsTrue() {
        wordBank.addDeck(JAPANESE_DECK);
        Deck editedJapaneseDeck = new DeckBuilder(JAPANESE_DECK).build();
        assertTrue(wordBank.hasDeck(editedJapaneseDeck));
    }

    @Test
    public void toString_emptyAddressBook_returnsZeroDecks() {
        String expectedString = wordBank.getObservedEntries().asUnmodifiableObservableList().size() + " decks";
        assertEquals(expectedString, wordBank.toString());
    }


    /**
     * A stub ReadOnlyWordBank whose entries list can violate interface constraints.
     */
    private static class WordBankStub implements ReadOnlyWordBank {
        private final ObservableList<Deck> decks = FXCollections.observableArrayList();

        WordBankStub(Collection<Deck> decks) {
            this.decks.setAll(decks);
        }

        @Override
        public ObservableList<Deck> getDeckList() {
            return decks;
        }
    }
}
