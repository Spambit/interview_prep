/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javaapplication1.Array.Search.Range;

/**
 *
 * @author sambit
 */
public class Array {

    public static void rotate2dArrayBy90DegRight() {
        int arr[][] = new int[][] { new int[] { 1, 2, 3, 4 }, new int[] { 5, 6, 7, 8 }, new int[] { 9, 10, 11, 12 },
                new int[] { 13, 14, 15, 16 } };
        System.out.println(Arrays.deepToString(arr));
        rorate2dArrayInternal(arr);
        System.out.println(Arrays.deepToString(arr));
    }

    private static class Array2DCell {
        int row;
        int col;

        Array2DCell(int r, int c) {
            row = r;
            col = c;
        }
    }

    private enum Array2DTravelPath {
        rowWiseTowardsRight {
            public void advance(int steps, ArrayList<Array2DCell> trail, int wallPosition) {
                Array2DCell currentCell = trail.get(trail.size() - 1);
                int newRow = currentCell.row;
                int newCol = currentCell.col + steps;
                int exceedSteps = Math.abs(wallPosition - newCol);
                if (exceedSteps > 0) {
                    newRow = Math.abs(exceedSteps);
                    newCol = wallPosition;
                }
                trail.add(new Array2DCell(newRow, newCol));
            }
        },

        colWiseDownwards {
            public void advance(int steps, ArrayList<Array2DCell> trail, int wallPosition) {
                Array2DCell currentCell = trail.get(trail.size() - 1);
                int newRow = currentCell.row + steps;
                int newCol = currentCell.col;
                int exceedSteps = Math.abs(wallPosition - newRow);
                if (exceedSteps > 0) {
                    newCol = wallPosition - Math.abs(exceedSteps);
                    newRow = wallPosition;
                }
                trail.add(new Array2DCell(newRow, newCol));
            }
        },

        rowWiseTowardsLeft {
            public void advance(int steps, ArrayList<Array2DCell> trail, int wallPosition) {
                Array2DCell currentCell = trail.get(trail.size() - 1);
                int newRow = currentCell.row ;
                int newCol = currentCell.col - steps;
                if (newCol < 0) {
                    newRow = wallPosition - Math.abs(newCol);
                    newCol = 0;
                }
                trail.add(new Array2DCell(newRow, newCol));
            }
        },
        colWiseUpwards {
            public void advance(int steps, ArrayList<Array2DCell> trail, int wallPosition) {
                Array2DCell currentCell = trail.get(trail.size() - 1);
                int newRow = currentCell.row - steps ;
                int newCol = currentCell.col ;
                if (newRow < 0) {
                    newCol = Math.abs(newRow);
                    newRow = 0;
                }
                trail.add(new Array2DCell(newRow, newCol));
            }
        };
        public void advance(int steps, ArrayList<Array2DCell> trail, int wallPosition) {

        }
    }

    private static void rorate2dArrayInternal(int arr[][]) {
        int layer = 0, size = arr.length;
        Array2DTravelPath[] paths = new Array2DTravelPath[] { Array2DTravelPath.rowWiseTowardsRight,
                Array2DTravelPath.colWiseDownwards, Array2DTravelPath.rowWiseTowardsLeft,
                Array2DTravelPath.colWiseUpwards };
        for (; layer < arr.length / 2; layer++) {
            size = size - layer - 1;
            int j = layer;
            do {
                // int replaceWith = arr[layer][j];
                ArrayList<Array2DCell> trail = new ArrayList();
                trail.add(new Array2DCell(layer, j));
                for (Array2DTravelPath path : paths) {
                    path.advance(size, trail, arr.length - 1 - layer);
                }

                int replaceWith = arr[trail.get(0).row][trail.get(0).col];
                for (int i = 1; i < trail.size(); i++) {
                    Array2DCell current = trail.get(i);
                    int replacedData = arr[current.row][current.col];
                    arr[current.row][current.col] = replaceWith;
                    System.out.println("Replaced data :"+replacedData+" with :"+replaceWith);
                    replaceWith = replacedData;
                }
                System.out.println();
                j++;
            } while(j < size);
        }
    }

    public static int smallestMissingNumber(int unsortedArrWithPositiveAndNegative[]) {
        int onlyPositives[] = null;// allPositiveNumbers(unsortedArrWithPositiveAndNegative);
        return smallestMissingNumber(onlyPositives);
    }

    private static int maxSumOfConsecuitive(int start, int count) {
        int end = (count - 1) * start;
        int sum = (int) ((count * (end + start)) / 2);
        return sum;
    }

    private static class MissingNumberInfo {
        int missing;
        int totalSum;
        int smallest;

        public static MissingNumberInfo create(int missing, int totalSum, int smallest) {
            MissingNumberInfo info = new MissingNumberInfo();
            info.missing = missing;
            info.totalSum = totalSum;
            info.smallest = smallest;
            return info;
        }
    }

    private static MissingNumberInfo smallestMissingNumberInternal(int positiveNumberArr[], int index) {
        if (index >= positiveNumberArr.length - 1) {
            return MissingNumberInfo.create(0, positiveNumberArr[index], positiveNumberArr[index]);
        }

        MissingNumberInfo info = smallestMissingNumberInternal(positiveNumberArr, index + 1);

        int current = positiveNumberArr[index];
        int prev = info.totalSum;
        int sum = current + prev;

        if (current < info.smallest) {
            info.smallest = current;
        }

        int count = positiveNumberArr.length - index;
        int maxPossibleSum = maxSumOfConsecuitive(info.smallest, count);

        int missing = Math.abs(maxPossibleSum - sum);
        if (missing > 1) {
            // not consecutive
            missing = info.smallest + 1;
        }

        return MissingNumberInfo.create(missing, sum, info.smallest);
    }

    private static int sudukuBoard[][] = new int[][] { new int[] { 0, 0, 4, 0 }, new int[] { 4, 0, 3, 0 },
            new int[] { 0, 4, 0, 3 }, new int[] { 0, 2, 0, 0 } };

    private static final int MAX_NUMBER = sudukuBoard.length;
    private static int[] possibleValues = new int[] { 1, 2, 3, MAX_NUMBER };

    private static int fillPosition(SudukuCell cell, int board[][], HashSet<Integer> excludeList) {
        if (cell.prefilled) {
            return -1;
        }
        return cell.makeGuess(board, excludeList);
    }

    private static class SudukuCell {
        int row;
        int col;
        boolean prefilled;

        public static SudukuCell create(int row, int col, boolean prefilled) {
            SudukuCell cell = new SudukuCell();
            cell.row = row;
            cell.col = col;
            cell.prefilled = prefilled;
            return cell;
        }

        public static SudukuCell create(int row, int col) {
            SudukuCell cell = new SudukuCell();
            cell.row = row;
            cell.col = col;
            return cell;
        }

        public boolean isAlreadyFilled(int board[][]) {
            return board[this.row][this.col] != 0;
        }

        public int getData(int board[][]) {
            return board[row][col];
        }

        public int makeGuess(int board[][], HashSet<Integer> excludeNumbers) {
            ArrayList<Integer> rowWiseFilledNumbers = rowWiseFilledNumbers(board);
            ArrayList<Integer> colWiseFilledNumbers = colWiseFilledNumbers(board);
            ArrayList<Integer> squareWiseFilledNumbers = squareWiseFilledNumbers(board);
            HashSet<Integer> allNumbersThatAreAlreadyUsed = new HashSet(rowWiseFilledNumbers);
            allNumbersThatAreAlreadyUsed.addAll(colWiseFilledNumbers);
            allNumbersThatAreAlreadyUsed.addAll(squareWiseFilledNumbers);
            allNumbersThatAreAlreadyUsed.addAll(excludeNumbers);
            for (int i = 1; i <= MAX_NUMBER; i++) {
                if (!allNumbersThatAreAlreadyUsed.contains(i)) {
                    return i;
                }
            }
            return -1;
        }

        private ArrayList<Integer> rowWiseFilledNumbers(int board[][]) {
            ArrayList<Integer> filledNumbers = new ArrayList();
            for (Integer data : board[row]) {
                if (data != 0) {
                    filledNumbers.add(data);
                }
            }
            return filledNumbers;
        }

        private ArrayList<Integer> colWiseFilledNumbers(int board[][]) {
            ArrayList<Integer> filledNumbers = new ArrayList();
            for (int i = 0; i < board.length; i++) {
                int row[] = board[i];
                int data = row[this.col];
                if (data != 0) {
                    filledNumbers.add(data);
                }
            }
            return filledNumbers;
        }

        private ArrayList<Integer> squareWiseFilledNumbers(int board[][]) {
            return SudukuSquare.squareWiseFilledNumbers(board, this);
        }

        public boolean isRowWiseValid(int number, int board[][]) {
            ArrayList<Integer> filledNumbers = rowWiseFilledNumbers(board);
            return number <= MAX_NUMBER && !filledNumbers.contains(number);
        }

        public boolean isColWiseValid(int number, int board[][]) {
            ArrayList<Integer> filledNumbers = colWiseFilledNumbers(board);
            return number <= MAX_NUMBER && !filledNumbers.contains(number);
        }

        public boolean isSquareWiseValid(int number, int board[][]) {
            ArrayList<Integer> filledNumbers = SudukuSquare.squareWiseFilledNumbers(board, this);
            return number <= MAX_NUMBER && !filledNumbers.contains(number);
        }
    }

    private static class SudukuSquare {
        SudukuCell start;
        SudukuCell end;

        private static SudukuSquare create(SudukuCell start, SudukuCell end) {
            SudukuSquare square = new SudukuSquare();
            square.start = start;
            square.end = end;
            return square;
        }

        private static SudukuSquare container(SudukuCell cell) {
            ArrayList<SudukuSquare> list = squares();
            for (SudukuSquare square : list) {
                if (Math.abs(square.start.row - cell.row) <= 1) {
                    if (Math.abs(square.start.col - cell.col) <= 1) {
                        return square;
                    }
                }
            }
            return null;
        }

        public static ArrayList<SudukuSquare> squares() {
            ArrayList<SudukuSquare> squares = new ArrayList();

            squares.add(SudukuSquare.create(SudukuCell.create(0, 0), SudukuCell.create(1, 1)));
            squares.add(SudukuSquare.create(SudukuCell.create(0, 2), SudukuCell.create(1, 3)));
            squares.add(SudukuSquare.create(SudukuCell.create(2, 0), SudukuCell.create(3, 1)));
            squares.add(SudukuSquare.create(SudukuCell.create(2, 2), SudukuCell.create(3, 3)));

            /*
             * squares.add(SudukuSquare.create(SudukuCell.create(0, 0), SudukuCell.create(2,
             * 2))); squares.add(SudukuSquare.create(SudukuCell.create(0, 3),
             * SudukuCell.create(2, 5)));
             * squares.add(SudukuSquare.create(SudukuCell.create(0, 6), SudukuCell.create(2,
             * 8)));
             * 
             * squares.add(SudukuSquare.create(SudukuCell.create(3, 0), SudukuCell.create(5,
             * 2))); squares.add(SudukuSquare.create(SudukuCell.create(3, 3),
             * SudukuCell.create(5, 5)));
             * squares.add(SudukuSquare.create(SudukuCell.create(3, 6), SudukuCell.create(5,
             * 8)));
             * 
             * squares.add(SudukuSquare.create(SudukuCell.create(6, 0), SudukuCell.create(8,
             * 2))); squares.add(SudukuSquare.create(SudukuCell.create(6, 3),
             * SudukuCell.create(8, 5)));
             * squares.add(SudukuSquare.create(SudukuCell.create(6, 6), SudukuCell.create(8,
             * 8)));
             */
            return squares;
        }

        public static ArrayList<Integer> squareWiseFilledNumbers(int board[][], SudukuCell cell) {
            ArrayList<Integer> list = new ArrayList();
            SudukuSquare square = container(cell);
            for (int i = square.start.row; i <= square.end.row; i++) {
                for (int j = square.start.col; j <= square.end.col; j++) {
                    int data = board[i][j];
                    if (data != 0) {
                        list.add(data);
                    }
                }
            }
            return list;
        }
    }

    private static ArrayList<ArrayList<SudukuCell>> amendedBoard = new ArrayList();

    private static ArrayList<ArrayList<SudukuCell>> amendedBoard(int[][] board) {
        if (amendedBoard.size() != 0) {
            return amendedBoard;
        }

        for (int i = 0; i < board.length; i++) {
            ArrayList<SudukuCell> row = new ArrayList();
            for (int j = 0; j < board[0].length; j++) {
                row.add(SudukuCell.create(i, j, board[i][j] != 0));
            }
            amendedBoard.add(row);
        }
        return amendedBoard;
    }

    public static boolean validSuduku() {
        HashSet<Integer> excludeNumbers = new HashSet();
        ArrayList<ArrayList<SudukuCell>> board = amendedBoard(sudukuBoard);
        int row = 0, col = 0;
        boolean movedUpwardInBackwardDirection = false;
        for (; row < board.size();) {
            ArrayList<SudukuCell> boardRow = board.get(row);

            for (; col < boardRow.size();) {
                SudukuCell cell = boardRow.get(col);
                int guess = fillPosition(cell, sudukuBoard, excludeNumbers);

                if (cell.prefilled) {
                    // ignore - prefilled
                    col++;
                } else if (guess == -1 && !cell.prefilled) {
                    // not able to guess - traverse backward and guess another
                    do {
                        col--;
                        if (col < 0) {
                            break;
                        }
                    } while (boardRow.get(col).prefilled);
                    if (col >= 0) {
                        int oldValue = sudukuBoard[row][col];
                        excludeNumbers.add(oldValue);
                        sudukuBoard[row][col] = 0;
                    }
                } else {
                    // guessed correctly - put on the board
                    // boardRow.set(col, cell);
                    sudukuBoard[row][col] = guess;
                    col++;
                    excludeNumbers.clear();
                }

                if (col < 0) {
                    break;// if traversed too backward
                }
            }
            if (col < 0 && row != 0) {
                row--;
                if (row < 0) {
                    break;
                }
                col = sudukuBoard[0].length - 1;
                movedUpwardInBackwardDirection = true;
            } else {
                row++;
                col = 0;
                excludeNumbers.clear();
                movedUpwardInBackwardDirection = false;
            }
        }

        if (col < 0 && row < 0) {
            return false;// backward travese to correct the board not possible
        }
        return true;
    }

    private static ArrayList<ArrayItem> indexArrayThatHasZeroValue(int[] countArray) {
        ArrayList<ArrayItem> list = new ArrayList();
        for (int i = 0; i < countArray.length; i++) {
            if (countArray[i] == 0) {
                list.add(ArrayItem.create(countArray[i], i));
            }
        }
        return list;
    }

    public static int[] permutationOfNumbers(int arr[]) {
        if (arr.length <= 1) {
            return arr;
        }

        int countingArr[] = new int[arr.length + 1];
        ArrayList<ArrayItem> needChangeIndexes = new ArrayList();
        for (int i = 0; i < arr.length; i++) {
            int data = arr[i];
            if (data <= arr.length) {
                if (countingArr[data] == 1) {
                    // duplicate
                    needChangeIndexes.add(ArrayItem.create(data, i));
                } else {
                    countingArr[data] = 1;
                }
            } else {
                needChangeIndexes.add(ArrayItem.create(data, i));
            }
        }

        ArrayList<ArrayItem> availableValuesAsIndexes = indexArrayThatHasZeroValue(countingArr);

        for (int i = 0; i < needChangeIndexes.size(); i++) {
            ArrayItem item = needChangeIndexes.get(i);
            int value = availableValuesAsIndexes.remove(availableValuesAsIndexes.size() - 1).index;
            if (value != 0) {
                arr[item.index] = value;
            }
        }
        return arr;
    }

    public static ArrayList<MaxProfitStockInfo> maxProfitBuySellStockMultipleTimes(int arr[]) {
        if (arr.length <= 1) {
            return null;
        }

        ArrayList<MaxProfitStockInfo> list = new ArrayList();
        int buy = -1, sell = -1;
        int current = arr[0];
        for (int i = 1; i < arr.length;) {
            int next = arr[i];
            if (next > current) {
                if (buy == -1) {
                    buy = i - 1;
                }
            } else {
                if (buy != -1) {
                    sell = i - 1;
                    ArrayItem buyItem = ArrayItem.create(arr[buy], buy);
                    ArrayItem sellItem = ArrayItem.create(arr[sell], sell);
                    MaxProfitStockInfo info = MaxProfitStockInfo.create(buyItem, sellItem);
                    list.add(info);

                    buy = -1;
                    sell = -1;
                }
            }
            i++;
            current = next;
        }
        if (buy != -1) {
            sell = arr.length - 1;
            ArrayItem buyItem = ArrayItem.create(arr[buy], buy);
            ArrayItem sellItem = ArrayItem.create(arr[sell], sell);
            MaxProfitStockInfo info = MaxProfitStockInfo.create(buyItem, sellItem);
            list.add(info);

            buy = -1;
            sell = -1;
        }
        return list;
    }

    public static int maxProfit(ArrayList<MaxProfitStockInfo> list) {
        int profit = 0;
        for (int i = 0; i < list.size(); i++) {
            MaxProfitStockInfo current = list.get(i);
            profit += current.sell.data - current.buy.data;
        }
        return profit;
    }

    public static class ArrayItem {
        int index = 0;
        int data;

        public static ArrayItem create(int data, int index) {
            ArrayItem item = new ArrayItem();
            item.data = data;
            item.index = index;
            return item;
        }

        public String toString() {
            return "[ " + index + " ] = [ " + data + " ] ";
        }
    }

    public static class MaxProfitStockInfo {
        ArrayItem buy;
        ArrayItem sell;

        public static MaxProfitStockInfo create(ArrayItem buy, ArrayItem sell) {
            MaxProfitStockInfo info = new MaxProfitStockInfo();
            info.buy = buy;
            info.sell = sell;
            return info;
        }

        public String toString() {
            return " Buy : " + buy + " Sell : " + sell;
        }
    }

    private static ArrayItem maxInUnsortedArray(int arr[], int startIndex, int endIndex) {
        int max = arr[startIndex];
        int index = 0;
        for (int i = startIndex + 1; i <= endIndex; i++) {
            int current = arr[i];
            if (max < current) {
                max = current;
                index = i;
            }
        }
        ArrayItem maxItem = new ArrayItem();
        maxItem.data = max;
        maxItem.index = index;
        return maxItem;
    }

    private static ArrayItem minInUnsortedArray(int arr[], int startIndex, int endIndex) {
        int min = arr[startIndex];
        int index = 0;
        for (int i = startIndex + 1; i <= endIndex; i++) {
            int current = arr[i];
            if (min > current) {
                min = current;
                index = i;
            }
        }
        ArrayItem minItem = new ArrayItem();
        minItem.data = min;
        minItem.index = index;
        return minItem;
    }

    public static MaxProfitStockInfo maxProfitStockSellBuyOnce(int arr[]) {
        ArrayItem allTimeLow = minInUnsortedArray(arr, 0, arr.length - 1);
        ArrayItem allTimeHigh = maxInUnsortedArray(arr, 0, arr.length - 1);
        if (allTimeLow.index < allTimeHigh.index) {
            return MaxProfitStockInfo.create(allTimeLow, allTimeHigh);
        }

        ArrayItem highAtRightHandOfAllTimeLow = maxInUnsortedArray(arr, allTimeLow.index, arr.length - 1);
        ArrayItem lowAtLeftHandOfAllTimeHigh = minInUnsortedArray(arr, 0, allTimeHigh.index);

        int diffAtRight = highAtRightHandOfAllTimeLow.data - allTimeLow.data;
        int diffAtLeft = allTimeHigh.data - lowAtLeftHandOfAllTimeHigh.data;

        if (diffAtRight > diffAtLeft) {
            return MaxProfitStockInfo.create(allTimeLow, highAtRightHandOfAllTimeLow);
        } else {
            return MaxProfitStockInfo.create(lowAtLeftHandOfAllTimeHigh, allTimeHigh);
        }
    }

    public static int[] deleteDuplicatesFromSortedArrayWithoutExtraBuffer(int[] arr) {
        if (arr == null) {
            return arr;
        }
        if (arr != null && arr.length <= 1) {
            return arr;
        }

        int current = arr[0];
        int start = -1, end = -1, droppedItemsSize = 0;
        for (int i = 1; i < arr.length; i++) {
            int next = arr[i];
            if (current == next) {
                if (start == -1) {
                    start = i - 1;
                }
                end = i;
                continue;
            }
            if (start != -1 && end != -1) {
                droppedItemsSize += end - start - 1;
                dropItems(arr, start, end);
                i = start - 1;
                start = -1;
                end = -1;
            }
            current = next;
        }
        if (start != -1 && end != -1) {
            droppedItemsSize += end - start;
            dropItems(arr, start, end);
            start = -1;
            end = -1;
        }
        // copy in a new array
        int newSize = arr.length - droppedItemsSize;
        int[] array = new int[newSize];
        for (int i = 0; i < newSize; i++) {
            array[i] = arr[i];
        }
        return array;
    }

    private static int dropItems(int[] arr, int startIndex, int endIndex) {
        // System.out.println();
        // System.out.println("Duplicate found between "+startIndex + " and endIndex:
        // "+endIndex);
        for (int i = startIndex, j = endIndex; i < arr.length && j < arr.length; i++, j++) {
            arr[i] = arr[j];
        }
        // System.out.println("");
        // Array.print(arr);
        return startIndex;
    }

    private static HashMap<String, Integer> memoFrogJump = new HashMap();

    private static void memoizeFrogJumpEntry(String key, int val) {
        memoFrogJump.put(key, val);
    }

    private static Integer memoizedFrogJumpEntry(String key) {
        return (Integer) memoFrogJump.get(key);
    }

    private static String createFrogJumpEntry(int levelOnRock, int currentIndex) {
        return "jump(" + levelOnRock + "," + currentIndex + ")";
    }

    private static Integer alreadyCalculatedJumpFromCurrentRockLevel(int rockLevel, int currentIndex) {
        String memoKey = createFrogJumpEntry(rockLevel, currentIndex);
        Integer entry = memoizedFrogJumpEntry(memoKey);
        return entry;
    }

    private static ArrayList<Integer> test = new ArrayList();

    private static int minJumpToEnd(int arr[], int startIndex, int maxMove) {

        if (startIndex < 0 || startIndex >= arr.length) {
            return -1;
        }

        if (arr[startIndex] == 0) {
            return -1;
        }

        if (maxMove > arr.length - 1) {
            return 1;
        }

        int minJump = -1;
        for (int i = startIndex + 1; i <= maxMove; i++) {
            int rock = arr[i];
            Integer memoizedJump = memoizedFrogJumpEntry(createFrogJumpEntry(rock, i));
            int currentJump = 0;
            if (memoizedJump == null) {
                currentJump = minJumpToEnd(arr, i, rock + i);
            } else {
                currentJump = memoizedJump;
            }

            if (minJump == -1) {
                minJump = currentJump;
            } else {
                minJump = Math.min(currentJump, minJump);
            }
        }
        return minJump + 1;
    }

    public static int minFrogJumpAnother(int[] arr) {
        int jump = minJumpToEnd(arr, 0, arr[0]);
        System.out.print(test);
        return jump;
    }

    private static int minFrogJumpInternal(int levelOnRock, int[] arr, int currentIndex, ArrayList<Integer> result) {
        if (levelOnRock == 0) {
            return -1;
        }

        if (levelOnRock + currentIndex >= arr.length - 1) {
            return 1;
        }

        int maxMove = currentIndex + levelOnRock;
        int smallestJump = -1;
        for (int i = currentIndex + 1; i <= maxMove; i++) {
            int currentRockLabel = arr[i];
            int jumpFromCurrentRock = -1;
            Integer entry = alreadyCalculatedJumpFromCurrentRockLevel(currentRockLabel, i);
            if (entry != null) {
                jumpFromCurrentRock = entry;
                if (jumpFromCurrentRock != -1 && (smallestJump == -1 || smallestJump > entry)) {
                    smallestJump = entry;
                }
                break;
            } else {
                jumpFromCurrentRock = minFrogJumpInternal(currentRockLabel, arr, i, result);
                String memoKey = createFrogJumpEntry(currentRockLabel, i);
                Integer memoizedValue = alreadyCalculatedJumpFromCurrentRockLevel(currentRockLabel, i);
                if (memoizedValue == null) {
                    memoizeFrogJumpEntry(memoKey, jumpFromCurrentRock);
                }
                if (jumpFromCurrentRock == -1) {
                    // ignore - jump not possible
                } else if (smallestJump == -1) {
                    smallestJump = jumpFromCurrentRock;
                    result.add(currentRockLabel);
                } else if (smallestJump > jumpFromCurrentRock) {
                    smallestJump = jumpFromCurrentRock;
                    result.add(currentRockLabel);
                }
            }
        }
        if (smallestJump != -1) {
            smallestJump += 1;
        }
        return smallestJump;
    }

    public static ArrayList<Integer> minFrogJump(int arr[]) {
        ArrayList<Integer> result = new ArrayList();
        if (arr == null) {
            return null;
        }
        if (arr.length == 1) {
            result.add(arr[0]);
            return result;
        }

        if (arr[0] == 0) {
            return null;
        }

        int levelOnRock = arr[0];
        int jump = minFrogJumpInternal(levelOnRock, arr, 0, result);
        System.out.println("Jump: " + jump);
        result.add(levelOnRock);
        Collections.reverse(result);
        return result;
    }

    private static ArrayList<Integer> multiplyDigitArrayBySingleDigit(int[] digits, int singleDigit) {
        ArrayList<Integer> result = new ArrayList();
        if (singleDigit == 0) {
            for (Integer digit : digits) {
                result.add(0);
            }
            return result;
        }

        int carry = 0;
        for (int i = digits.length - 1; i >= 0; i--) {
            int digit = digits[i];
            int multipliedValue = digit * singleDigit + carry;
            carry = 0;
            int reminder = multipliedValue % 10;
            int quot = (int) (multipliedValue / 10);
            if (reminder != 0 && quot == 0) {
                // single digit;
            } else {
                carry = quot;
            }
            result.add(reminder);
        }
        if (carry != 0) {
            result.add(carry);
        }
        Collections.reverse(result);
        return result;
    }

    private static ArrayList<Integer> addDigitsArrayOfSameLength(int[] number1, int[] number2) {
        ArrayList<Integer> list = new ArrayList();
        int sum = 0, carry = 0;
        for (int i = number1.length - 1; i >= 0; i--) {
            sum = number1[i] + number2[i] + carry;
            carry = 0;
            int quot = (int) (sum / 10);
            int reminder = sum % 10;
            if (reminder != 0 && quot == 0) {
                // single digit
            } else {
                carry = 1;
            }
            list.add(reminder);
        }
        if (carry == 1) {
            list.add(1);
        }
        Collections.reverse(list);
        return list;
    }

    public static ArrayList<Integer> multiplyTwoArbitaryPrecisionIntegers(int number1[], int number2[]) {
        ArrayList<ArrayList<Integer>> products = new ArrayList();
        for (int i = number2.length - 1; i >= 0; i--) {
            ArrayList<Integer> singleRowResult = multiplyDigitArrayBySingleDigit(number1, number2[i]);
            for (int j = 0; j < number2.length - i - 1; j++) {
                singleRowResult.add(0);
            }
            products.add(singleRowResult);
        }
        // products = makeLargestLengthByAppendingZeroAtBeginning(products);
        ArrayList<Integer> sum = products.get(0);
        for (int i = 1; i < products.size(); i++) {
            ArrayList<Integer> currentList = products.get(i);

            ArrayList<ArrayList<Integer>> dummy = new ArrayList();
            dummy.add(currentList);
            dummy.add(sum);
            dummy = makeLargestLengthByAppendingZeroAtBeginning(dummy);
            sum = addDigitsArrayOfSameLength(convertIntegerArrayToIntArray(dummy.get(0)),
                    convertIntegerArrayToIntArray(dummy.get(1)));
        }
        return sum;
    }

    private static int[] convertIntegerArrayToIntArray(ArrayList<Integer> objects) {
        int arr[] = new int[objects.size()];
        int i = 0;
        for (Integer item : objects) {
            arr[i] = item;
            i++;
        }
        return arr;
    }

    private static ArrayList<ArrayList<Integer>> makeLargestLengthByAppendingZeroAtBeginning(
            ArrayList<ArrayList<Integer>> lists) {
        int maxLength = 0;

        for (ArrayList<Integer> list : lists) {
            int size = list.size();
            if (maxLength < size) {
                maxLength = size;
            }
        }

        for (int j = 0; j < lists.size(); j++) {
            ArrayList<Integer> list = lists.get(j);
            ArrayList<Integer> newList = new ArrayList();
            for (int i = 0; i < maxLength - list.size(); i++) {
                newList.add(0);
            }
            newList.addAll(list);
            lists.set(j, newList);
        }

        return lists;
    }

    public static ArrayList<Integer> sortArrayWithZerosAndTwos(int arr[]) { // not completed -
        int zeroTracker = -1, oneTracker = -1, twoTracker = -1;
        for (int i = 0, j = arr.length - 1; i < arr.length; i++, j++) {
            if (arr[i] == 0) {
                if (zeroTracker == -1) {
                    zeroTracker = i;
                }
            } else if (arr[i] == 1) {
                if (oneTracker == -1) {
                    oneTracker = i;
                }
            } else if (arr[i] == 2) {
                if (twoTracker == -1) {
                    twoTracker = i;
                }
            }

            if (zeroTracker != -1 && oneTracker != -1 && twoTracker != -1) {
                break;
            }
        }

        for (int i = 0; i < arr.length; i++) {
            int possibleSwapZeroIndex = arr[zeroTracker] != 0 ? zeroTracker : -1;
            int possibleSwapOneIndex = arr[oneTracker] != 0 ? oneTracker : -1;
            int possibleSwapTwoIndex = arr[twoTracker] != 0 ? twoTracker : -1;

            if (possibleSwapZeroIndex != -1 && possibleSwapOneIndex != -1) {
                swap(arr, possibleSwapZeroIndex, possibleSwapOneIndex);
            }

            if (possibleSwapZeroIndex != -1 && possibleSwapTwoIndex != -1) {
                swap(arr, possibleSwapZeroIndex, possibleSwapTwoIndex);
            }

            if (possibleSwapOneIndex != -1 && possibleSwapTwoIndex != -1) {
                swap(arr, possibleSwapOneIndex, possibleSwapTwoIndex);
            }

        }

        return null;
    }

    private static void swap(int arr[], int oneIndex, int twoIndex) {
        int temp = arr[oneIndex];
        arr[oneIndex] = arr[twoIndex];
        arr[twoIndex] = temp;
    }

    private static ArrayList<Integer> convertToDigitArrayAnother(int number) {
        int reminder = number % 10;
        ArrayList<Integer> list = new ArrayList();
        int quot = (int) (number / 10);

        if (quot == 0 && reminder != 0) {
            list.add(number);
            return list;
        }

        while (quot != 0) {
            list.add(reminder);
            reminder = quot % 10;
            quot = (int) (quot / 10);
        }

        list.add(reminder);

        Collections.reverse(list);
        return list;
    }

    private static int digit(int number, int position) {
        ArrayList<Integer> convertedDigitArray = convertToDigitArrayAnother(number);
        return convertedDigitArray.get(position);
    }

    public static List<Integer> bubbleSortAccordingToFirstDigit(List<Integer> A) {
        for (int i = 0; i < A.size() - 1; i++) {
            int current = A.get(i);
            int smallest = current;
            int smallestFirstDigit = digit(current, 0);
            int k = 0;
            for (int j = i + 1; j < A.size(); j++) {
                // find the smallest in this
                int data = A.get(j);
                int firstDigit = digit(data, 0);
                if (smallestFirstDigit > firstDigit) {
                    smallest = data;
                    smallestFirstDigit = firstDigit;
                    k = j;
                }
            }
            // swap
            if (current != smallest) {
                A.set(k, current);
                A.set(i, smallest);
            }
        }
        return A;
    }

    public static String makeLargestNumberAsString(String number1, String number2) {
        char ch1 = 'a';
        char ch2 = 'a';
        for (int i = 0, j = 0;;) {
            if (i < number1.length()) {
                ch1 = number1.charAt(i);
                i++;
            }

            if (j < number2.length()) {
                ch2 = number2.charAt(j);
                j++;
            }

            if (ch1 > ch2) {
                return number1 + number2;
            }
            if (ch2 > ch1) {
                return number2 + number1;
            }

            if (i >= number1.length() && j >= number2.length()) {
                return number1 + number2;
            }
        }
    }

    private static String largestNumberInternal(List<Integer> list) {
        String num1 = "" + list.get(0), num2 = "";
        for (int j = 1; j < list.size(); j++) {
            num2 = "" + list.get(j);
            String biggestNumber = makeLargestNumberAsString(num1, num2);
            num1 = biggestNumber;
        }
        return num1;
    }

    // DO NOT MODIFY THE LIST. IT IS READ ONLY
    public static String largestNumber(final List<Integer> A) {
        List<Integer> sortedList = Array.bubbleSortAccordingToFirstDigit(A);
        return largestNumberInternal(sortedList);
    }

    public static ArrayList<Integer> closestTripletSum(int arr[], int target) {
        return naiveClosestTripletSum(arr, target);
    }

    private static ArrayList<Integer> naiveClosestTripletSum(int arr[], int target) {
        ArrayList<Integer> list = new ArrayList();
        int minDistanceToTarget = 0;
        for (int k = 0; k < arr.length; k++) {
            Integer top = arr[k];
            for (int i = k + 1; i < arr.length; i++) {
                int middle = arr[i];
                for (int j = i + 1; j < arr.length; j++) {
                    int bottom = arr[j];
                    int sum = top + middle + bottom;
                    if (sum == target) {
                        list.clear();
                        list.add(top);
                        list.add(middle);
                        list.add(bottom);
                        return list;
                    }

                    int distanceToTarget = Math.abs(target - sum);
                    if (minDistanceToTarget == 0 || minDistanceToTarget > distanceToTarget) {
                        minDistanceToTarget = distanceToTarget;
                        list.clear();
                        list.add(top);
                        list.add(middle);
                        list.add(bottom);
                    }
                }
            }
        }
        return list;
    }

    public static ArrayList<Integer> tripletSum(int arr[], int target) {
        return betterTripletSum(arr, target);
    }

    private static ArrayList<Integer> betterTripletSum(int arr[], int target) {
        ArrayList<Integer> sortedList = createSortedListWithArr(arr);
        ArrayList<Integer> result = new ArrayList();
        for (int i = 0; i < sortedList.size(); i++) {
            Integer data = sortedList.get(i);
            int twoSumTarget = target - data;
            ArrayList<Integer> twoSumList = findTwoSum(sortedList, i + 1, twoSumTarget);
            if (!twoSumList.isEmpty()) {
                result.add(data);
                copyArrayList(result, twoSumList);
                break;
            }
        }
        return result;
    }

    private static ArrayList<Integer> findTwoSum(ArrayList<Integer> sortedList, int startIndex, int target) {
        ArrayList<Integer> result = new ArrayList();
        for (int i = startIndex, j = sortedList.size() - 1; i < j;) {
            int leading = sortedList.get(i);
            int trailing = sortedList.get(j);
            int sum = trailing + leading;
            if (sum == target) {
                result.add(leading);
                result.add(trailing);
                return result;
            }
            if (sum > target) {
                j--;
            } else {
                i++;
            }
        }
        return result;
    }

    private static ArrayList<Integer> naiveTripletSum(int arr[], int target) {
        // all posible triplet and find the match with the target
        ArrayList<Integer> result = new ArrayList();
        for (int i = 0; i < arr.length; i++) {
            int data = arr[i];
            int twoSumTarget = target - data;
            // System.out.println(twoSumTarget);
            ArrayList<Integer> twoSumList = findTwoSum(arr, i + 1, twoSumTarget);
            // System.out.println(twoSumList);
            if (!twoSumList.isEmpty()) {
                result.add(data);
                copyArrayList(result, twoSumList);
                break;
            }
        }
        return result;
    }

    private static ArrayList<Integer> findTwoSum(int arr[], int startIndex, int target) {
        ArrayList<Integer> list = new ArrayList();
        for (int i = startIndex; i < arr.length; i++) {
            int current = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                int next = arr[j];
                int sum = current + next;
                if (sum == target) {
                    list.add(current);
                    list.add(next);
                    return list;
                }
            }
        }
        return list;
    }

    private static void copyArrayList(ArrayList<Integer> dst, ArrayList<Integer> src) {
        for (Integer data : src) {
            dst.add(data);
        }
    }

    private static void copyForMaxSumSubArray(ArrayList<Integer> dst, ArrayList<Integer> src) {
        for (Integer data : src) {
            dst.add(data);
        }
    }

    private static int sumOfStagingListItems(ArrayList<Integer> set) {
        int sum = 0;
        for (Integer data : set) {
            sum += data;
        }

        return sum;
    }

    public int maxSubArrayKadenes(final List<Integer> A) {
        int workingSum = 0, stagingSum = 0;

        for (int i = 0; i < A.size(); i++) {
            int data = A.get(i);
            workingSum += data;
            if (workingSum < 0) {
                workingSum = 0;
            }
            if (stagingSum < workingSum) {
                stagingSum = workingSum;
            }
            if (data > stagingSum) {
                stagingSum = data;
            }
        }

        return stagingSum;
    }

    // DO NOT MODIFY THE LIST. IT IS READ ONLY - interviewBit
    public static int maxSubArray(final List<Integer> A) {
        ArrayList<Integer> stagingSet = new ArrayList();
        ArrayList<Integer> workingSet = new ArrayList();
        int sum = 0;
        for (int i = 0; i < A.size(); i++) {
            sum = A.get(i);
            if (stagingSet.isEmpty()) {
                stagingSet.add(sum);
            }
            workingSet.add(sum);

            int stagingSum = sumOfStagingListItems(stagingSet);
            if (stagingSum < sum) {
                stagingSet.clear();
                copyForMaxSumSubArray(stagingSet, workingSet);
                // System.out.print(stagingSet);
            }

            for (int j = i + 1; j < A.size(); j++) {
                sum += A.get(j);
                workingSet.add(A.get(j));
                int stagingSum2 = sumOfStagingListItems(stagingSet);
                // System.out.print("Sum : "+sum+ " Staging Sum: "+stagingSum2);
                if (stagingSum2 < sum) {
                    stagingSet.clear();
                    copyForMaxSumSubArray(stagingSet, workingSet);
                    // System.out.print(stagingSet);
                }
            }
            workingSet.clear();
        }

        // ystem.out.print(stagingSet);
        return sumOfStagingListItems(stagingSet);
    }

    public static class Pair {
        int item1;
        int item2;

        public static Pair create(int one, int two) {
            Pair p = new Pair();
            p.item1 = one;
            p.item2 = two;
            return p;
        }

        public int getSum() {
            return item1 + item2;
        }

        public String toString() {
            return " ( " + item1 + " , " + item2 + " ) ";
        }
    }

    public static Pair closestPairWithSum(int arr[], int target) {
        ArrayList<Integer> sortedList = createSortedListWithArr(arr);
        return closestPairWithSumInternal(sortedList, target);
    }

    public static Pair pairWithSum(int arr[], int target) {
        ArrayList<Integer> sortedList = createSortedListWithArr(arr);
        for (int i = 0, j = sortedList.size() - 1; i < j;) {
            int sum = sortedList.get(i) + sortedList.get(j);
            if (sum == target) {
                return Pair.create(sortedList.get(i), sortedList.get(j));
            }
            if (sum > target) {
                j--;
            } else {
                i++;
            }
        }
        return null;
    }

    private static ArrayList<Integer> createSortedListWithArr(int arr[]) {
        arr = Sort.bubble(arr);

        ArrayList<Integer> sortedList = new ArrayList();
        for (Integer item : arr) {
            sortedList.add(item);
        }
        return sortedList;
    }

    public static Pair closestPairWithSumInternal(ArrayList<Integer> sortedList, int target) {
        Pair foundPair = null;
        int smallestDistnceTowardsTarget = 0;
        int currentDistanceTowardsTarget = 0;
        int i = 0, j = sortedList.size() - 1;
        for (; i < j;) {
            int leadingItem = sortedList.get(i);
            int trailingItem = sortedList.get(j);
            int sum = leadingItem + trailingItem;

            if (sum == target) {
                foundPair = Pair.create(leadingItem, trailingItem);
                break;
            } else if (sum > target) {
                currentDistanceTowardsTarget = Math.abs(target - sum);
                j--;
            } else {
                currentDistanceTowardsTarget = Math.abs(target - sum);
                i++;
            }
            if (smallestDistnceTowardsTarget == 0 || smallestDistnceTowardsTarget > currentDistanceTowardsTarget) {
                smallestDistnceTowardsTarget = currentDistanceTowardsTarget;
                foundPair = Pair.create(leadingItem, trailingItem);
            }
        }
        return foundPair;
    }

    public static HashSet<Integer> largestConsecutiveSequence(int arr[]) {
        arr = Array.Sort.bubble(arr);
        boolean transactionOn = true;
        HashSet<Integer> workingSeq = new HashSet();
        HashSet<Integer> stagingSeq = new HashSet();
        for (int i = 0, j = 1; j < arr.length; i++, j++) {
            if (arr[j] - arr[i] == 1) {
                workingSeq.add(arr[i]);
                workingSeq.add(arr[j]);
            } else {
                if (stagingSeq.size() < workingSeq.size()) {
                    stagingSeq.clear();
                    copyHashSet(workingSeq, stagingSeq);
                }

                workingSeq.clear();
            }
        }

        if (stagingSeq.size() < workingSeq.size()) {
            stagingSeq.clear();
            copyHashSet(workingSeq, stagingSeq);
        }
        return stagingSeq;
    }

    private static void copyHashSet(HashSet<Integer> src, HashSet<Integer> dst) {
        for (Integer val : src) {
            dst.add(val);
        }
    }

    public static int[] addOneToNumber(int digits[]) {
        return convertInternal(digits);
    }

    public static int[] addOneToVeryBigNumber(int digits[]) {
        return addOneToVeryBigNumberInternal(digits);
    }

    private static int[] addOneToVeryBigNumberInternal(int[] digits) {
        int newDigits[] = new int[digits.length];
        int carry = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            int val = digits[i] + carry;
            if (val > 9) {
                carry = 1;
                newDigits[i] = 0;
            } else {
                carry = 0;
                newDigits[i] = val;
            }
        }
        int newarr[] = newDigits;
        if (carry == 1) {
            newarr = new int[newDigits.length + 1];
            for (int i = 1, j = 0; j < newDigits.length; j++, i++) {
                newarr[i] = newDigits[j];
            }
            newarr[0] = 1;
        }

        return newarr;
    }

    private static int[] convertInternal(int digits[]) {
        int number = convertToNumber(digits);
        number += 1;
        int newDigits[] = convertToDigitArray(number);
        return newDigits;
    }

    private static int numberOfDigits(int number) {
        int i = 1;
        while ((int) (number / 10) != 0) {
            number = (int) (number / 10);
            i++;
        }
        return i;
    }

    private static int[] convertToDigitArray(int number) {
        int numberOfDigits = numberOfDigits(number);
        int arr[] = new int[numberOfDigits];
        int multiplier = 1;
        for (int i = 0; i < numberOfDigits - 1; i++) {
            multiplier *= 10;
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (number / multiplier);
            number %= multiplier;
            multiplier /= 10;
        }
        return arr;
    }

    private static int convertToNumber(int digits[]) {
        int num = 0;
        for (int i = digits.length - 1, j = 0; i >= 0; i--, j++) {
            int digit = digits[j];
            int multiplier = 1;
            for (int k = 1; k <= i; k++) {
                multiplier = multiplier * 10;
            }
            num += digit * multiplier;
        }
        return num;
    }

    public static class Rotate {
        public static void left(int arr[], int pos) {
            pos = pos % arr.length;
            for (int i = 0; i < pos; i++) {
                rotateLeftByOnePlace(arr);
            }
        }

        private static void rotateLeftByOnePlace(int arr[]) {
            int data = arr[0];
            int j = 1, i = 0;
            for (; j < arr.length; j++, i++) {
                arr[i] = arr[j];
            }

            arr[j - 1] = data;
        }
    }

    public static class Search {
        public static class Range {
            int start;
            int end;

            private Range(int start, int end) {
                this.start = start;
                this.end = end;
            }

            public static Range make(int start, int end) {
                return new Range(start, end);
            }

            public boolean isDataInRange(int data) {
                return data > start && data < end;
            }

            public boolean startsWith(int data) {
                return data == start;
            }

            public boolean endsWith(int data) {
                return data == end;
            }

        }

        public static int positionAtWhichItemShouldBeInserted(int sortedArr[], int data) {
            return positionAtWhichItemShouldBeInsertedInternal(sortedArr, data, 0, sortedArr.length - 1);
        }

        private static int positionAtWhichItemShouldBeInsertedInternal(int sortedArr[], int data, int startIndex,
                int endIndex) {
            int mid = (int) ((startIndex + endIndex) / 2);
            if (data == sortedArr[mid]) {
                return mid;
            }

            if (mid - 1 > 0 && data < sortedArr[mid] && sortedArr[mid - 1] < data) {
                return mid;
            }

            if (mid + 1 < sortedArr.length - 1 && data > sortedArr[mid] && data < sortedArr[mid + 1]) {
                return mid + 1;
            }

            if (mid == 0 && sortedArr[mid] > data) {
                return mid;
            }

            if (mid >= sortedArr.length - 2 && sortedArr[mid] < data) {
                return sortedArr.length;
            }

            if (data > sortedArr[mid]) {
                return positionAtWhichItemShouldBeInsertedInternal(sortedArr, data, mid, endIndex);
            } else {
                return positionAtWhichItemShouldBeInsertedInternal(sortedArr, data, startIndex, mid);
            }
        }

        public static int binarySearch(int[] arr, int data) {
            if (!isSorted(arr, 0, arr.length - 1)) {
                return -1;
            }
            return binarySearchInternal(arr, data, 0, arr.length - 1, -1);
        }

        public static boolean rangeSearch(int arr[], Range range) {
            return naiveRangeSearch(arr, range);
        }

        private static boolean naiveRangeSearch(int arr[], Range range) {
            boolean transactionStarted = false, transactionEnd = false;
            for (int i = 0; i < arr.length; i++) {
                int data = arr[i];
                boolean startsWith = range.startsWith(data);
                boolean endsWith = range.endsWith(data);

                if (startsWith) {
                    transactionStarted = true;
                    transactionEnd = false;
                }

                if (endsWith) {
                    transactionEnd = true;
                }

                if (transactionStarted && transactionEnd) {
                    return true;
                }
            }
            return false;
        }

        private static int binarySearchInternal(int arr[], int data, int startIndex, int endIndex, int foundIndex) {

            int midIndex = (int) ((startIndex + endIndex) / 2);
            if (arr[midIndex] == data) {
                return midIndex;
            }

            if (midIndex == startIndex) {
                return -1;
            }

            if (data > arr[midIndex]) {
                foundIndex = binarySearchInternal(arr, data, midIndex + 1, endIndex, foundIndex);
            } else {
                foundIndex = binarySearchInternal(arr, data, startIndex, midIndex - 1, foundIndex);
            }

            return foundIndex;
        }

        private static boolean isSorted(int arr[], int startIndex, int endIndex) {
            int data = arr[startIndex];
            for (int i = startIndex + 1; i < endIndex; i++) {
                if (data > arr[i]) {
                    return false;
                }
                data = arr[i];
            }
            return true;
        }
    }

    public static void moveAll0sAtEnd(int arr[]) {
        for (int i = 0; i < arr.length; i++) {
            int data = arr[i];
            if (data == 0) {
                shiftArrayToLeftByOnePosition(arr, i + 1, arr.length - 1);
                arr[arr.length - 1] = 0;
            }
        }
    }

    private static void shiftArrayToLeftByOnePosition(int arr[], int startIndex, int endIndex) {
        if (startIndex > arr.length - 1 || startIndex <= 0) {
            return;
        }

        for (int i = startIndex; i <= endIndex; i++) {
            arr[i - 1] = arr[i];
        }
    }

    public static class Sort {

        private static boolean isSuitableForCountingSort(int arr[]) {
            for (Integer data : arr) {
                if (data > 10) {
                    return false;
                }
            }
            return true;
        }

        private static int maxLinear(int arr[]) {
            if (arr.length == 0) {
                return 0;
            }
            int max = arr[0];
            for (int data : arr) {
                if (max < data) {
                    max = data;
                }
            }
            return max;
        }

        private static int[] countArrayWithInfoHowManyNumbersAreSmallerThan(int arr[]) {
            int countArray[] = new int[maxLinear(arr) + 1];
            for (int i = 0; i < arr.length; i++) {
                countArray[arr[i]] += 1;
            }

            int prevSum = countArray[0];
            for (int i = 1; i < countArray.length; i++) {
                prevSum += countArray[i];
                countArray[i] = prevSum;
            }
            return countArray;
        }

        public static int[] countingSort(int arr[]) {
            if (!isSuitableForCountingSort(arr)) {
                return null;
            }

            int result[] = new int[arr.length];
            int countArray[] = countArrayWithInfoHowManyNumbersAreSmallerThan(arr);
            for (int i = 0; i < arr.length; i++) {
                int targetIndex = countArray[arr[i]] - 1;
                result[targetIndex] = arr[i];
                countArray[arr[i]] = targetIndex;
            }
            return result;
        }

        public static int[] mergeTwoSortedArray(int[] arr1, int arr2[]) {
            int newArr[] = new int[arr1.length + arr2.length];
            return mergeInternal(newArr, arr1, 0, arr1.length - 1, arr2, 0, arr2.length - 1);
        }

        private static int[] mergeInternal(int dst[], int arr1[], int startIndex1, int endIndex1, int arr2[],
                int startIndex2, int endIndex2) {
            int i, k, j;
            for (i = startIndex1, k = 0, j = startIndex2; k < dst.length; k++) {
                int arr1data = 0;
                if (i <= endIndex1) {
                    arr1data = arr1[i];
                }

                int arr2data = 0;
                if (j <= endIndex2) {
                    arr2data = arr2[j];
                }

                if (arr1data < arr2data) {
                    dst[k] = arr1data;
                    i++;
                } else {
                    dst[k] = arr2data;
                    j++;
                }

                if (i == arr1.length && j < arr2.length) {
                    copyItems(arr2, j, endIndex2, dst, k + 1);
                    break;
                }

                if (j == arr2.length && i < arr1.length) {
                    copyItems(arr1, i, endIndex1, dst, k + 1);
                    break;
                }
            }
            return dst;
        }

        private static void copyItems(int src[], int startIndex, int endIndex, int dst[], int dstStartIndex) {
            for (int i = startIndex, j = dstStartIndex; i <= endIndex && j < dst.length; i++, j++) {
                dst[j] = src[i];
            }
        }

        public static int[] insertion(int[] arr) {
            for (int i = 1; i < arr.length; i++) {
                int data = arr[i];
                placeDataInProperPosition(arr, data, 0, i);
            }

            return arr;
        }

        // idea is - left part of array is always sorted and right is always unsorted.
        // Finally left part is the result.
        private static void placeDataInProperPosition(int arr[], int data, int startIndex, int endIndex) {
            for (int i = endIndex; i > startIndex; i--) {
                int prevData = arr[i - 1];
                if (data < prevData) {
                    arr[i - 1] = data;
                    arr[i] = prevData;
                }
            }
        }

        private static int indexOfMin(int arr[], int startIndex, int endIndex) {
            int minIndex = startIndex, min = arr[startIndex];
            for (int i = startIndex; i <= endIndex; i++) {
                int current = arr[i];
                if (min > current) {
                    minIndex = i;
                    min = current;
                }
            }
            return minIndex;
        }

        private static void findMinAndSwapWithData(int arr[], int data, int dataIndex, int startIndex) {
            int indexOfMin = indexOfMin(arr, startIndex, arr.length - 1);
            int minDataAtRight = arr[indexOfMin];
            if (data > minDataAtRight) {
                arr[dataIndex] = minDataAtRight;
                arr[indexOfMin] = data;
            }
        }

        public static int[] bubble(int[] arr) {
            for (int i = 0; i < arr.length - 1; i++) {
                int data = arr[i];
                findMinAndSwapWithData(arr, data, i, i + 1);
            }

            return arr;
        }
    }

    public static void print(int arr[]) {
        if (arr == null) {
            System.out.print("null");
            return;
        }
        if (arr.length == 0) {
            System.out.print("[]");
        }
        System.out.print(" [ ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(" " + arr[i] + " ");
        }
        System.out.print(" ] ");
    }

    public static class SubsetSum {

        public static class ImprovedRecusive {

            public static HashSet<Integer> findSubsetWhichSumUpto(int[] arr, int sum) {
                HashSet<Integer> hashset = null;
                for (int i = 0; i < arr.length; i++) {
                    hashset = findSubsetWhichSumUptoInternal(arr, sum, i, arr.length - 1);
                    if (!hashset.isEmpty()) {
                        break;
                    }
                }
                System.out.println("Accessed array: " + arrayAccessedCounter);
                return hashset;
            }

            static int arrayAccessedCounter = 0;

            private static HashSet<Integer> findSubsetWhichSumUptoInternal(int[] arr, int sum, int startIndex,
                    int endIndex) {
                HashSet<Integer> list = new HashSet();
                int data = arr[startIndex];
                arrayAccessedCounter++;
                System.out.println(
                        "Looking for subset which sum upto: " + sum + " start: " + startIndex + " end: " + endIndex);
                if (data > sum) {
                    System.out.println("Returning empty list because " + data + " is greater than " + sum);
                    return list;
                }
                if (data == sum) {
                    System.out.println("Adding the value : " + sum + " because " + data + " is equal to " + sum);
                    list.add(data);
                    return list;
                }
                if ((startIndex == endIndex)) {
                    System.out.println(
                            "Returning list because startIndex and endIndex are equal which is : " + startIndex);
                    return list;
                }

                int findSum = sum - data;
                HashSet<Integer> subsetsWhichSumUptoFindSum = new HashSet();
                System.out.println("Now looking for subset which sum upto remaining sum : " + findSum
                        + " because value now is : " + data);

                for (int i = startIndex + 1; i <= endIndex; i++) {
                    System.out.println("Saecrh start from : " + i);
                    subsetsWhichSumUptoFindSum = findSubsetWhichSumUptoInternal(arr, findSum, i, endIndex);
                    System.out.println("Search end from : " + i);
                    if (!subsetsWhichSumUptoFindSum.isEmpty()) {
                        System.out.println("Found a Search end from : " + i + " so breaking from the loop.");
                        break;
                    }
                }

                System.out.println("Search complete for sum : " + sum + " in elements from: " + startIndex
                        + " to endIndex: " + endIndex);

                if (!subsetsWhichSumUptoFindSum.isEmpty()) {
                    list.addAll(subsetsWhichSumUptoFindSum);
                    System.out.println("Found sum:  " + subsetsWhichSumUptoFindSum);
                    list.add(data);
                    System.out.println("Found a match and the list is : " + list);
                }
                return list;
            }
        }

        public static class NaiveRecusive {

            public static ArrayList<HashSet<Integer>> findSubsetWhichSumUpto(int[] arr, int sum) {
                HashSet<Integer> set = new HashSet();
                ArrayList<HashSet<Integer>> list = findSubsetWhichSumUptoInternal(arr, sum);
                return list;
            }

            private static ArrayList<HashSet<Integer>> amendListByCreatingNewEntryAddingNumberToEachEntry(
                    ArrayList<HashSet<Integer>> list, int num) {
                ArrayList<HashSet<Integer>> newList = new ArrayList();
                for (HashSet<Integer> hashSet : list) {
                    HashSet<Integer> newHashSet = new HashSet();
                    newHashSet.addAll(hashSet);
                    newHashSet.add(num);
                    newList.add(newHashSet);
                }
                list.addAll(newList);
                return list;
            }

            private static ArrayList<HashSet<Integer>> findSubsetWhichSumUptoInternal(int[] arr, int sum) {
                ArrayList<HashSet<Integer>> allPossibleSubSets = new ArrayList<HashSet<Integer>>();
                findAllPossibleSubset(arr, allPossibleSubSets, 0, arr.length - 1);
                ArrayList<HashSet<Integer>> list = new ArrayList<HashSet<Integer>>();
                for (HashSet<Integer> subset : allPossibleSubSets) {
                    int subsetsum = sumOfElementsInSet(subset);
                    if (subsetsum == sum) {
                        list.add(subset);
                    }
                }
                return list;
            }

            private static ArrayList<HashSet<Integer>> findAllPossibleSubset(int[] arr,
                    ArrayList<HashSet<Integer>> list, int startIndex, int endIndex) {
                if (arr.length == 0) {
                    return null;
                }

                if (startIndex != endIndex) {
                    list = findAllPossibleSubset(arr, list, startIndex + 1, endIndex);

                    int data = arr[startIndex];
                    list = amendListByCreatingNewEntryAddingNumberToEachEntry(list, data);
                } else {
                    HashSet<Integer> includeSet = new HashSet();
                    int data = arr[startIndex];
                    includeSet.add(data);

                    HashSet<Integer> emptyHashSet = new HashSet();

                    list.add(includeSet);
                    list.add(emptyHashSet);
                }

                return list;

            }

            private static int sumOfElementsInSet(HashSet<Integer> set) {
                int sum = 0;
                for (Integer el : set) {
                    sum += el;
                }
                return sum;
            }
        }
    }

    public static int longestCommonSpanWithSameSum() {
        int arr1[] = new int[] { 1, 0, 0, 1, 1, 0, 0 };
        int arr2[] = new int[] { 0, 1, 0, 1, 1, 0, 1 };
        int n = arr1.length;
        // Initialize result
        int maxLen = 0;

        // Initialize prefix sums of two arrays
        int preSum1 = 0, preSum2 = 0;

        // Create an array to store staring and ending
        // indexes of all possible diff values. diff[i]
        // would store starting and ending points for
        // difference "i-n"
        int diff[] = new int[2 * n + 1];

        // Initialize all starting and ending values as -1.
        for (int i = 0; i < diff.length; i++) {
            diff[i] = -1;
        }

        // Traverse both arrays
        for (int i = 0; i < n; i++) {
            // Update prefix sums
            preSum1 += arr1[i];
            preSum2 += arr2[i];

            // Comput current diff and index to be used
            // in diff array. Note that diff can be negative
            // and can have minimum value as -1.
            int curr_diff = preSum1 - preSum2;
            int diffIndex = n + curr_diff;

            // If current diff is 0, then there are same number
            // of 1's so far in both arrays, i.e., (i+1) is
            // maximum length.
            if (curr_diff == 0) {
                maxLen = i + 1;
            } // If current diff is seen first time, then update
              // starting index of diff.
            else if (diff[diffIndex] == -1) {
                diff[diffIndex] = i;
            } // Current diff is already seen
            else {
                // Find lenght of this same sum common span
                int len = i - diff[diffIndex];

                // Update max len if needed
                if (len > maxLen) {
                    maxLen = len;
                }
            }
        }
        return maxLen;
    }

    // at least one +ve number in array
    public static int findLargestSum(int[] array) {
        if (array.length == 0) {
            return 0;
        }

        int max_so_far = 0, max_ending_here = 0, index = 0;
        while (index < array.length) {
            int item = array[index];
            max_ending_here = max_ending_here + item;
            if (max_ending_here < 0) {
                max_ending_here = 0;
            }

            if (max_ending_here > max_so_far) {
                max_so_far = max_ending_here;
            }
            index++;
        }
        return max_so_far;
    }

    public static Tuple findLargestSumEx(int[] array) {
        int max_so_far = 0, max_ending_here = 0, index = 0, startIndex = 0, endIndex = 0;
        while (index < array.length) {
            int item = array[index];
            max_ending_here = max_ending_here + item;
            if (max_ending_here < 0) {
                max_ending_here = 0;
                startIndex = endIndex = 0;
            } else {
                if (startIndex == 0) {
                    startIndex = index;
                }
            }

            if (max_ending_here > max_so_far) {
                max_so_far = max_ending_here;
                endIndex = index;
            }
            index++;
        }
        return new Tuple(startIndex, endIndex, max_so_far);
    }

    public static Tuple findSmallestSum(int[] array) {
        int min_so_far = 0, min_ending_here = 0, index = 0, startIndex = 0, endIndex = 0;
        while (index < array.length) {
            int item = array[index];
            min_ending_here = min_ending_here + item;
            if (min_ending_here > 0) {
                min_ending_here = 0;
                startIndex = index; // TODO: startIndex output is wrong
            }

            if (min_ending_here < min_so_far) {
                min_so_far = min_ending_here;
                endIndex = index;
            }
            index++;
        }
        return new Tuple(startIndex, endIndex, min_so_far);
    }

    public static int largest(int[] array) {
        int max = 0;
        for (int i = 0; i < array.length; i++) {
            int val = array[i];
            if (val > max) {
                max = val;
            }
        }
        return max;
    }

    public static Tuple largestPair(int[] array) {
        int max_sum = 0, first = 0, second = 0;
        for (int i = 0, j = 1; j < array.length; i++, j++) {
            int f = array[i];
            int s = array[j];
            int sum = f + s;

            if (sum > max_sum) {
                max_sum = sum;
                first = f;
                second = s;
            }
        }
        return new Tuple(first, second, max_sum);
    }

    private static int[] mergeSortInternal(int[] arr, int startIndex, int endIndex) {
        if (startIndex == endIndex) {
            int[] newArr = new int[1];
            newArr[0] = arr[startIndex];
            return newArr;
        }

        int size = (endIndex - startIndex) + 1;
        int halfSize = Math.abs(size / 2);
        int leftStart = startIndex;
        int leftEnd = startIndex + (halfSize - 1);
        int rightStart = leftEnd + 1;
        int rightEnd = endIndex;
        int[] leftSortedHalf = mergeSortInternal(arr, leftStart, leftEnd);
        int[] rightSortedHalf = mergeSortInternal(arr, rightStart, rightEnd);
        int[] joined = joinTwoSortedArray(leftSortedHalf, rightSortedHalf);
        return joined;
    }

    public static int[] recursiveMergeSort(int[] arr) {
        return mergeSortInternal(arr, 0, arr.length - 1);
    }

    public static int[] joinTwoSortedArray(int[] arr1, int[] arr2) {
        int totalSize = arr1.length + arr2.length;
        boolean arr1Exhaust = false, arr2Exhaust = false;
        int[] newArr = new int[totalSize];
        int arr1Item = Integer.MAX_VALUE;
        int arr2Item = Integer.MAX_VALUE;
        for (int arr1Iter = 0, arr2Iter = 0, k = 0; k < totalSize; k++) {
            if (arr1Iter >= arr1.length) {
                arr1Exhaust = true;
            }

            if (arr2Iter >= arr2.length) {
                arr2Exhaust = true;
            }

            if (!arr1Exhaust) {
                arr1Item = arr1[arr1Iter];
            }
            if (!arr2Exhaust) {
                arr2Item = arr2[arr2Iter];
            }
            if (!arr1Exhaust && arr2Exhaust) {
                newArr[k] = arr1Item;
                arr1Iter++;
            } else if (!arr2Exhaust && arr1Exhaust) {
                newArr[k] = arr2Item;
                arr2Iter++;
            } else {
                if (arr1Item < arr2Item) {
                    newArr[k] = arr1Item;
                    arr1Iter++;
                } else {
                    newArr[k] = arr2Item;
                    arr2Iter++;
                }
            }

            arr1Item = Integer.MAX_VALUE;
            arr2Item = Integer.MAX_VALUE;

        }

        return newArr;
    }

    private static int[] quicksortInternal(int[] arr, int startIndex, int endIndex) {

        if (startIndex == endIndex) {
            return arr;
        }

        int wall = -1;
        int pivot = 0;
        int currentIndex = 0;
        int rightAfterWallIndex = 0;
        int size = endIndex - startIndex + 1;
        while (true) {
            rightAfterWallIndex = wall + 1;

            pivot = arr[endIndex];
            if (rightAfterWallIndex >= endIndex) {
                break;
            }

            int currentItem = arr[currentIndex];

            if (pivot >= currentItem) {
                int temp = arr[rightAfterWallIndex];
                arr[rightAfterWallIndex] = arr[currentIndex];
                arr[currentIndex] = temp;
                wall++;
                currentIndex = wall + 1;
            } else {
                currentIndex++;
            }
        }

        quicksortInternal(arr, startIndex, wall);

        return arr;
    }

    public static int[] quicksort(int[] arr) {
        return quicksortInternal(arr, 0, arr.length - 1);
    }

    public static int[] unionOfSortedArray(int[] arr1, int[] arr2) {
        int size = arr1.length + arr2.length;
        int[] arr = new int[size];
        int arr1Iter = 0, arr2Iter = 0, k = 0;
        for (; arr1Iter < arr1.length && arr2Iter < arr2.length; k++) {
            int arr1Item = arr1[arr1Iter];
            int arr2Item = arr2[arr2Iter];
            if (arr1Item < arr2Item) {
                arr[k] = arr1Item;
                arr1Iter++;
            } else if (arr1Item > arr2Item) {
                arr[k] = arr2Item;
                arr2Iter++;
            } else {
                // if both items are equal - ignore one of them if not already present in
                // resulting array
                // else ignore both of them and increment both iterator
                int prevIndex = k == 0 ? k : k - 1;
                int data = arr[prevIndex];
                if (data != arr1Item) {
                    arr[k] = arr1Item;
                    arr1Iter++;
                } else {
                    k = prevIndex;
                    arr1Iter++;
                    arr2Iter++;
                }
            }
        }

        int[] unexhaustedArray = null;
        int exhaustPoint = 0;
        if (arr1Iter >= arr1.length) {
            // arr1 exhausted
            // put all elements from arr2 to arr
            unexhaustedArray = arr2;
            exhaustPoint = arr1Iter;
        } else {
            unexhaustedArray = arr1;
            exhaustPoint = arr2Iter;
        }

        for (int i = k, j = exhaustPoint; j < unexhaustedArray.length; i++, j++) {
            int prevIndex = i == 0 ? i : i - 1;
            int data = arr[prevIndex];
            int item = unexhaustedArray[j];
            if (data != item) {
                arr[i] = item;
            }
        }

        return arr;
    }

    public static int[] removeFrom(int[] arr, int data) {
        int[] newArr = new int[arr.length - 1];
        int i = 0;
        for (int item : arr) {
            if (item != data) {
                newArr[i] = item;
            }
            i++;
            if (i >= newArr.length) {
                break;
            }
        }
        return newArr;
    }

    public static int[] insertTo(int arr[], int data) {
        int[] newArr = Arrays.copyOf(arr, arr.length + 1);
        newArr[arr.length] = data;
        return newArr;
    }

    public static int[] intersectionOfSortedArray(int[] arr1, int[] arr2) {
        // only the common elements
        int size = arr1.length + arr2.length;
        int[] arr = new int[size];
        int arr1Item = 0, arr2Item = 0, k = 0, arr1Iter = 0, arr2Iter = 0;
        for (; arr1Iter < arr1.length && arr2Iter < arr2.length;) {
            arr1Item = arr1[arr1Iter];
            arr2Item = arr2[arr2Iter];
            int prevIndex = k == 0 ? k : k - 1;
            int prevData = arr[prevIndex];
            if (arr1Item < arr2Item) {
                arr1Iter++;
            } else if (arr1Item > arr2Item) {
                arr2Iter++;
            } else {
                if (prevData != arr1Item) {
                    arr[k] = arr1Item;
                    arr1Iter++;
                    k++;
                } else {

                    arr1Iter++;
                    arr2Iter++;
                }
            }
        }
        return arr;
    }
}
