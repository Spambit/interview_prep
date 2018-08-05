/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

/**
 *
 * @author sambit
 */
public class CrackingCodingInterview {

    public static class Sol19_2 {

        //Design an algorithm to figure out if someone has won in a game of tic-tac-toe.
        private static char[][] board = new char[][]{
            {'o', 'x', 'o'},
            {'o', 'x', 'o'},
            {'x', 'o', 'x'}
        };

        private static class Position {

            int row;
            int col;

            Position(int row, int col) {
                this.row = row;
                this.col = col;
            }

            @Override
            public String toString() {
                return "Row: " + row + " Col: " + col;
            }

        }

        private enum Strategy {
            MatchHorizontally {
                @Override
                public boolean match(char val, Position currentPosition, int horizontalLength, int verticalLength) {
                    boolean isSameCharAtRHS = true, isSameCharAtLHS = true;

                    for (int col = currentPosition.col + 1; col < verticalLength; col++) {
                        char c = board[currentPosition.row][col];
                        if (c != val) {
                            isSameCharAtRHS = false;
                            break;
                        }
                    }
                    for (int col = currentPosition.col - 1; col >= 0; col--) {
                        char c = board[currentPosition.row][col];
                        if (c != val) {
                            isSameCharAtLHS = false;
                            break;
                        }
                    }
                    return isSameCharAtLHS & isSameCharAtRHS;
                }
            },
            MatchDiagonally {
                @Override
                public boolean match(char val, Position currentPosition, int horizontalLength, int verticalLength) {
                    boolean isSameCharDiagonallyLeftUpward = true,
                            isSameCharDiagonallyRightUpward = true,
                            isSameCharDiagonallyLeftDownward = true,
                            isSameCharDiagonallyRightDownward = true;

                    for (int col = currentPosition.col + 1, row = currentPosition.row + 1; col < verticalLength && row < horizontalLength; row++, col++) {
                        char c = board[row][col];
                        if (c != val) {
                            isSameCharDiagonallyRightDownward = false;
                            break;
                        }
                    }

                    for (int col = currentPosition.col - 1, row = currentPosition.row - 1; row >= 0 && col >= 0; row--, col--) {
                        char c = board[row][col];
                        if (c != val) {
                            isSameCharDiagonallyLeftUpward = false;
                            break;
                        }
                    }

                    for (int col = currentPosition.col - 1, row = currentPosition.row + 1; row < horizontalLength && col >= 0; row++, col--) {
                        char c = board[row][col];
                        if (c != val) {
                            isSameCharDiagonallyLeftDownward = false;
                            break;
                        }
                    }
                    for (int col = currentPosition.col + 1, row = currentPosition.row - 1; row >= 0 && col < verticalLength; row--, col++) {
                        char c = board[row][col];
                        if (c != val) {
                            isSameCharDiagonallyRightUpward = false;
                            break;
                        }
                    }
                    return (isSameCharDiagonallyRightUpward & isSameCharDiagonallyLeftDownward)
                            || (isSameCharDiagonallyLeftUpward & isSameCharDiagonallyRightDownward);
                }
            },
            MatchVertially {
                @Override
                public boolean match(char val, Position currentPosition, int horizontalLength, int verticalLength) {
                    boolean isSameCharDownwards = true, isSameCharUpwards = true;

                    for (int row = currentPosition.row + 1; row < horizontalLength; row++) {
                        char c = board[row][currentPosition.col];
                        if (c != val) {
                            isSameCharDownwards = false;
                            break;
                        }
                    }
                    for (int row = currentPosition.row - 1; row >= 0; row--) {
                        char c = board[row][currentPosition.col];
                        if (c != val) {
                            isSameCharUpwards = false;
                            break;
                        }
                    }
                    return isSameCharDownwards & isSameCharUpwards;
                }
            },
            Unknown;

            public boolean match(char val, Position currentPosition, int horizontalLength, int verticalLength) {
                return false;
            }
        }

        private static class AmmendedBoardWithStrategy {

            public char value = ' ';
            public Strategy[] strategies = null;

            public AmmendedBoardWithStrategy(char value, Strategy[] startegies) {
                this.value = value;
                this.strategies = startegies;
            }

            private static boolean isEdges(Position position, int horizontalLength, int verticalLength) {
                int row = position.row;
                int col = position.col;
                if ((row == 0 || row == horizontalLength - 1)
                        || (col == 0 || col == verticalLength - 1)) {
                    return true;
                }
                return false;
            }

            public static AmmendedBoardWithStrategy[][] create(int horizontalLength, int vertialLength) {
                AmmendedBoardWithStrategy array[][] = new AmmendedBoardWithStrategy[horizontalLength][vertialLength];
                for (int row = 0; row < horizontalLength; row++) {
                    for (int col = 0; col < vertialLength; col++) {
                        char val = board[row][col];
                        AmmendedBoardWithStrategy ammendedBoardWithStrategy = new AmmendedBoardWithStrategy(val, new Strategy[]{
                            Strategy.MatchHorizontally,
                            Strategy.MatchVertially
                        });
                        if (!isEdges(new Position(row, col), horizontalLength, vertialLength)) {
                            ammendedBoardWithStrategy = new AmmendedBoardWithStrategy(val, new Strategy[]{
                                Strategy.MatchHorizontally,
                                Strategy.MatchVertially,
                                Strategy.MatchDiagonally
                            });
                        }
                        array[row][col] = ammendedBoardWithStrategy;
                    }
                }
                return array;
            }
        }

        public static boolean hasWonTicTacToe() {
            AmmendedBoardWithStrategy[][] newBoard = AmmendedBoardWithStrategy.create(3, 3);
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    AmmendedBoardWithStrategy ammendedBoardWithStrategy = newBoard[row][col];
                    if (ammendedBoardWithStrategy != null) {
                        Strategy[] strategies = ammendedBoardWithStrategy.strategies;
                        for (Strategy strategy : strategies) {
                            Position pos = new Position(row, col);
                            boolean sameCharsFoundInADirection = strategy.match(ammendedBoardWithStrategy.value, pos, 3, 3);
                            System.out.println("" + sameCharsFoundInADirection + " Same char found at position: " + pos + " for strategy : " + strategy.name());
                            if (sameCharsFoundInADirection) {
                                return true;
                            }
                        }
                    }
                }
            }

            return false;
        }
    }
}
