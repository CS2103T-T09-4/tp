package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyWordBank;
import seedu.address.model.WordBank;
import seedu.address.model.deck.Deck;


/**
 * An Immutable WordBank that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableWordBank {

    public static final String MESSAGE_DUPLICATE_DECK = "Decks list contains duplicate deck(s).";
    private final List<JsonAdaptedDeck> decks = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableWordBank} with the given entries.
     */
    @JsonCreator
    public JsonSerializableWordBank(@JsonProperty("decks") List<JsonAdaptedDeck> decks) {
        this.decks.addAll(decks);
    }

    /**
     * Converts a given {@code ReadOnlyWordBank} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableWordBank}.
     */
    public JsonSerializableWordBank(ReadOnlyWordBank source) {
        decks.addAll(source.getDeckList().stream().map(JsonAdaptedDeck::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code WordBank} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public WordBank toModelType() throws IllegalValueException {
        WordBank wordBank = new WordBank();
        for (JsonAdaptedDeck jsonAdaptedDeck: decks) {
            Deck deck = jsonAdaptedDeck.toModelType();
            if (wordBank.hasDeck(deck)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DECK);
            }
            wordBank.addDeck(deck);
        }
        return wordBank;
    }
}
