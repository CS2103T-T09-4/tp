package seedu.address.ui.panels;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.RadialGradientPaint;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.model.play.Score;
import seedu.address.model.play.scoring.QuizAttempt;

class StatisticsPanelTest {

    static List<List<QuizAttempt>> listsToMerge;
    static List<QuizAttempt> correctMostRecent;

    @BeforeEach
    void setUp() {
        final int MAX_TOTAL_SCORE = 100, MIN_TOTAL_SCORE = 10;
        final int ATTEMPTS = 500;
        final int MAX_LISTS = 100, MIN_LISTS = 10;

        var zoneId = ZoneId.systemDefault();

        final long START_DATE = LocalDateTime.of(2020, 5, 1, 0, 0).atZone(ZoneId.systemDefault())
            .toEpochSecond();
        final long END_DATE = LocalDateTime.of(2020, 12, 1, 0, 0).atZone(ZoneId.systemDefault())
            .toEpochSecond();

        listsToMerge = new ArrayList<>();

        Random rand = new Random();

        int numberOfLists = rand.nextInt(MAX_LISTS - MIN_LISTS + 1) + MIN_LISTS;

        // generate random number of buckets
        for (int i = 0; i < numberOfLists; ++i) {
            listsToMerge.add(new ArrayList<>());
        }

        // generate many random quiz attempts
        // each with randomized scores (between 1 and 100) and takenAts
        // and place each quiz attempt in a random bucket
        for (int i = 0; i < ATTEMPTS; ++i) {
            QuizAttempt quizAttempt = new QuizAttempt();
            double totalScore =
                rand.nextInt((MAX_TOTAL_SCORE - MIN_TOTAL_SCORE) + 1) + MIN_TOTAL_SCORE;
            double yourScore = rand.nextDouble() * totalScore;
            Score score = new Score(totalScore, yourScore);
            quizAttempt.setScore(score);
            LocalDateTime takenAt = LocalDateTime
                .ofInstant(Instant.ofEpochSecond(ThreadLocalRandom.current().nextLong(
                    START_DATE,
                    END_DATE
                )), ZoneId.systemDefault());
            quizAttempt.setTakenAt(takenAt);
            // System.out.println(quizAttempt.getTakenAtAndScoreInPercentage());
            int listIdx = rand.nextInt(numberOfLists);
            listsToMerge.get(listIdx).add(quizAttempt);
        }

//        for(int i=0;i<numberOfLists;++i){
//            System.out.println(listsToMerge.get(i).size());
//        }

        for (var list : listsToMerge) {
            list.sort(Comparator.comparing(QuizAttempt::getTakenAt));
        }
        List<QuizAttempt> temp = new ArrayList<>();
        for (var list : listsToMerge) {
//            try {
//                System.out.println("==============");
//                System.out.println(list.get(list.size() - 1).getTakenAtAndScoreInPercentage());
//                System.out.println(list.get(list.size() - 2).getTakenAtAndScoreInPercentage());
//                System.out.println(list.get(list.size() - 3).getTakenAtAndScoreInPercentage());
//                System.out.println("==============");
//            } catch (Exception ignored) {
//            }
            temp.addAll(list);
        }
        temp.sort(Comparator.comparing(QuizAttempt::getTakenAt).reversed());
        correctMostRecent = temp;
//        correctMostRecent = temp.subList(0, 10); // this is our correct 10 most recent elements

    }


    @Test
    void testMergeSortedListsAndRetrieveFirstK_shouldGiveCorrectResult_ForKEquals10() {
        int k = 10;
        List<QuizAttempt> ans = StatisticsPanel.mergeSortedListsAndRetrieveFirstK(listsToMerge, k);
//        for (int i = 0; i < k; ++i) {
//            System.out.println(correctMostRecent.get(i).getTakenAtAndScoreInPercentage());
//        }
//        System.out.println("================");
//        for (var i : ans) {
//            System.out.println(i.getTakenAtAndScoreInPercentage());
//        }
        for (int i = 0; i < Math.min(ans.size(), k); ++i) {
//            System.out.println(
//                "-----------" + i + ", " + ans.get(i).getTakenAtAndScoreInPercentage() + " | "
//                    + correctMostRecent.get(i).getTakenAtAndScoreInPercentage());
            assertEquals(
                correctMostRecent.get(i).getTakenAtAndScoreInPercentage(), ans.get(i)
                    .getTakenAtAndScoreInPercentage());
        }
    }

    @Test
    void testMergeSortedListsAndRetrieveFirstK_shouldGiveCorrectResult_ForKEquals20() {
        int k = 20;
        List<QuizAttempt> ans = StatisticsPanel.mergeSortedListsAndRetrieveFirstK(listsToMerge, k);
//        for (int i = 0; i < k; ++i) {
//            System.out.println(correctMostRecent.get(i).getTakenAtAndScoreInPercentage());
//        }
//        System.out.println("================");
//        for (var i : ans) {
//            System.out.println(i.getTakenAtAndScoreInPercentage());
//        }
        for (int i = 0; i < Math.min(ans.size(), k); ++i) {
//            System.out
//                .println("===========" + i + ", " + ans.get(i).getTakenAtAndScoreInPercentage());
            assertEquals(
                correctMostRecent.get(i).getTakenAtAndScoreInPercentage(), ans.get(i)
                    .getTakenAtAndScoreInPercentage());
        }
    }

    @Test
    void testMergeSortedListsAndRetrieveFirstK_shouldGiveCorrectResult_ForKEquals30() {
        int k = 30;
        List<QuizAttempt> ans = StatisticsPanel.mergeSortedListsAndRetrieveFirstK(listsToMerge, k);
//        for (int i = 0; i < k; ++i) {
//            System.out.println(correctMostRecent.get(i).getTakenAtAndScoreInPercentage());
//        }
//        System.out.println("================");
//        for (var i : ans) {
//            System.out.println(i.getTakenAtAndScoreInPercentage());
//        }
        for (int i = 0; i < Math.min(ans.size(), k); ++i) {
            assertEquals(
                correctMostRecent.get(i).getTakenAtAndScoreInPercentage(), ans.get(i)
                    .getTakenAtAndScoreInPercentage());
        }
    }

    @Test
    void testMergeSortedListsAndRetrieveFirstK_shouldGiveCorrectResult_ForKEquals50() {
        int k = 50;
        List<QuizAttempt> ans = StatisticsPanel.mergeSortedListsAndRetrieveFirstK(listsToMerge, k);
//        for (int i = 0; i < k; ++i) {
//            System.out.println(correctMostRecent.get(i).getTakenAtAndScoreInPercentage());
//        }
//        System.out.println("================");
//        for (var i : ans) {
//            System.out.println(i.getTakenAtAndScoreInPercentage());
//        }
        for (int i = 0; i < Math.min(ans.size(), k); ++i) {
            assertEquals(
                correctMostRecent.get(i).getTakenAtAndScoreInPercentage(), ans.get(i)
                    .getTakenAtAndScoreInPercentage());
        }
    }

    @Test
    void testMergeSortedListsAndRetrieveFirstK_shouldGiveCorrectResult_ForKEquals100() {
        int k = 100;
        List<QuizAttempt> ans = StatisticsPanel.mergeSortedListsAndRetrieveFirstK(listsToMerge, k);
//        for (int i = 0; i < k; ++i) {
//            System.out.println(correctMostRecent.get(i).getTakenAtAndScoreInPercentage());
//        }
//        System.out.println("================");
//        for (var i : ans) {
//            System.out.println(i.getTakenAtAndScoreInPercentage());
//        }
        for (int i = 0; i < Math.min(ans.size(), k); ++i) {
            assertEquals(
                correctMostRecent.get(i).getTakenAtAndScoreInPercentage(), ans.get(i)
                    .getTakenAtAndScoreInPercentage());
        }
    }


    @AfterEach
    void tearDown() {
    }
}