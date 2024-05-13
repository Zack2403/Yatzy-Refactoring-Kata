package org.codingdojo.yatzy1;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Yatzy1 {

    private static final int YATZY_HIGH_SCORE = 50;
    private static final int YATZY_LOW_SCORE = 0;
    private static final int YATZY_SCORE_ZERO = 0;
    private static final int[] SMALL_STRAIGHT_EXPECTED_RESULT = {1, 2, 3, 4, 5};
    private static final int[] LARGE_STRAIGHT_EXPECTED_RESULT = {2, 3, 4, 5, 6};
    private static final int SMALL_STRAIGHT_SUM = 15;
    private static final int LARGE_STRAIGHT_SUM = 20;
    private int[] dice;

    /**
     * @deprecated please use the version with arguments
     */
    @Deprecated
    public Yatzy1() {
    }

    public Yatzy1(int die1, int die2, int die3, int die4, int die5) {
        this();
        dice = new int[5];
        dice[0] = die1;
        dice[1] = die2;
        dice[2] = die3;
        dice[3] = die4;
        dice[4] = die5;

    }

    public int chance() {
        return IntStream.of(dice)
            .sum();
    }

    public int yatzy() {
        return IntStream.of(this.dice)
            .distinct()
            .count() == 1 ? YATZY_HIGH_SCORE : YATZY_LOW_SCORE;
    }

    public int ones() {
        return filterDiceWithValue(1)
            .sum();
    }

    public int twos() {
        return filterDiceWithValue(2)
            .sum();
    }

    public int threes() {
        return filterDiceWithValue(3)
            .sum();
    }

    public int fours() {
        return filterDiceWithValue(4)
            .sum();
    }

    public int fives() {
        return filterDiceWithValue(5)
            .sum();
    }

    public int sixes() {
        return filterDiceWithValue(6)
            .sum();
    }

    public int scorePair() {
        return computeSumByKindCount(2)
            .max(Integer::compareTo)
            .orElse(0);
    }

    public int twoPair() {
        return computeSumByKindCount(2)
            .reduce(0, Integer::sum);
    }

    public int fourOfAKind() {
        return computeSumByKindCount(4)
            .reduce(0, Integer::sum);
    }

    public int threeOfAKind() {
        return computeSumByKindCount(3)
            .reduce(YATZY_SCORE_ZERO, Integer::sum);
    }


    public int smallStraight() {
        return isStraight(SMALL_STRAIGHT_EXPECTED_RESULT) ? SMALL_STRAIGHT_SUM : YATZY_SCORE_ZERO;
    }


    public int largeStraight() {
        return isStraight(LARGE_STRAIGHT_EXPECTED_RESULT) ? LARGE_STRAIGHT_SUM : YATZY_SCORE_ZERO;
    }

    public int fullHouse() {
        int threeOfKindSum = threeOfAKind();
        int pairSum = scorePair();
        if (threeOfKindSum != YATZY_SCORE_ZERO && pairSum != YATZY_SCORE_ZERO) {
            return threeOfKindSum + pairSum;
        }
        return YATZY_SCORE_ZERO;
    }

    private Stream<Integer> computeSumByKindCount(int kindCount) {
        return filterByKindCount(kindCount)
            .map(count -> count.getKey() * kindCount);
    }

    private Stream<Map.Entry<Integer, Long>> filterByKindCount(int kindCount) {
        return countDiceByValue()
            .stream()
            .filter(countByDice -> countByDice.getValue() >= kindCount);
    }

    private Set<Map.Entry<Integer, Long>> countDiceByValue() {
        return IntStream.of(this.dice)
            .boxed()
            .collect(
                Collectors.groupingBy(Function.identity(), Collectors.counting())
            ).entrySet();
    }

    private boolean isStraight(int[] expected) {
        return Arrays.equals(IntStream.of(dice)
            .sorted()
            .toArray(), expected);
    }

    private IntStream filterDiceWithValue(int value) {
        return IntStream.of(dice)
            .filter(die -> die == value);
    }
}



