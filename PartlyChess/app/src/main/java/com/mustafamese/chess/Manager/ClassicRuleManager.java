package com.mustafamese.chess.Manager;

import com.mustafamese.chess.GameZone.Board;
import com.mustafamese.chess.GameZone.Cell;

public class ClassicRuleManager {

    private boolean check;
    private char whoseTurn;
    private int counter;
    private int rokMove;
    private int rokDistance;

    public ClassicRuleManager(boolean check, char whoseTurn) {
        this.check = check;
        this.whoseTurn = whoseTurn;
        this.rokDistance = -1;
    }

    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean checkmate) {
        this.check = checkmate;
    }

    public char getWhoseTurn() {
        return whoseTurn;
    }

    public void setWhoseTurn(char whoseTurn) {
        this.whoseTurn = whoseTurn;
    }

    public int getRokMove(){
        return rokMove;
    }

    public void setRokMove(int rokMove) {
        this.rokMove = rokMove;
    }

    public int getRokDistance() {
        return rokDistance;
    }

    public void setRokDistance(int rokDistance){
        this.rokDistance = rokDistance;
    }

    public int controlCheck(Cell cell, Board board) {
        if (checkFromTop(cell, board) != -1) {
            return checkFromTop(cell, board);
        }
        if (checkFromDown(cell, board) != -1) {
            return checkFromDown(cell, board);
        }
        if (checkFromRight(cell, board) != -1) {
            return checkFromRight(cell, board);
        }
        if (checkFromLeft(cell, board) != -1) {
            return checkFromLeft(cell, board);
        }
        if (checkFromLeftdown(cell, board) != -1) {
            return checkFromLeftdown(cell, board);
        }
        if (checkFromLefttop(cell, board) != -1) {
            return checkFromLefttop(cell, board);
        }
        if (checkFromRightdown(cell, board) != -1) {
            return checkFromRightdown(cell, board);
        }
        if (checkFromRighttop(cell, board) != -1) {
            return checkFromRighttop(cell, board);
        }
        if (checkFromTwoLeftOneTop(cell, board) != -1) {
            return checkFromTwoLeftOneTop(cell, board);
        }
        if (checkFromTwoRightOneDown(cell, board) != -1) {
            return checkFromTwoRightOneDown(cell, board);
        }
        if (checkFromTwoLeftOneDown(cell, board) != -1) {
            return checkFromTwoLeftOneDown(cell, board);
        }
        if (checkFromTwoRightOneTop(cell, board) != -1) {
            return checkFromTwoRightOneTop(cell, board);
        }
        if (checkFromOneLeftTwoDown(cell, board) != -1) {
            return checkFromOneLeftTwoDown(cell, board);
        }
        if (checkFromOneLeftTwoTop(cell, board) != -1) {

            return checkFromOneLeftTwoTop(cell, board);
        }
        if (checkFromOneRightTwoDown(cell, board) != -1) {
            return checkFromOneRightTwoDown(cell, board);
        }
        if (checkFromOneRightTwoTop(cell, board) != -1) {
            return checkFromOneRightTwoTop(cell, board);
        }
        setCheck(false);
        return -1;
    }

    public int controlCheckStraight(Cell cell, Board board){
        if (checkFromTop(cell, board) != -1) {
            return checkFromTop(cell, board);
        }
        if (checkFromDown(cell, board) != -1) {
            return checkFromDown(cell, board);
        }
        if (checkFromRight(cell, board) != -1) {
            return checkFromRight(cell, board);
        }
        if (checkFromLeft(cell, board) != -1) {
            return checkFromLeft(cell, board);
        }
        setCheck(false);
        return -1;
    }

    public int controlCheckDiagonal(Cell cell, Board board){
        if (checkFromLeftdown(cell, board) != -1) {
            return checkFromLeftdown(cell, board);
        }
        if (checkFromLefttop(cell, board) != -1) {
            return checkFromLefttop(cell, board);
        }
        if (checkFromRightdown(cell, board) != -1) {
            return checkFromRightdown(cell, board);
        }
        if (checkFromRighttop(cell, board) != -1) {
            return checkFromRighttop(cell, board);
        }
        setCheck(false);
        return -1;
    }

    public int checkFromTop(Cell kingCell, Board board) {
        for (counter = 1; counter < 8; counter++) {
            if (kingCell.getIndex() - 8 * counter >= 0) {
                if (!isEmpty(board.getCells()[kingCell.getIndex() - 8 * counter])) {
                    if (isEnemy(board.getCells()[kingCell.getIndex() - 8 * counter], kingCell.getWhatSide())
                            && board.getCells()[kingCell.getIndex() - 8 * counter].getPiece().getOwnCellIndex() != 0
                            && board.getCells()[kingCell.getIndex() - 8 * counter].getPiece().getOwnCellIndex() != 2
                            && board.getCells()[kingCell.getIndex() - 8 * counter].getPiece().getOwnCellIndex() != 1
                            && board.getCells()[kingCell.getIndex() - 8 * counter].getPiece().getOwnCellIndex() != 5) {
                        setCheck(true);
                        return kingCell.getIndex() - 8 * counter;
                    } else break;
                }
            } else break;
        }
        return -1;
    }

    public int checkFromDown(Cell kingCell, Board board) {
        for (counter = 1; counter < 8; counter++) {
            if (kingCell.getIndex() + 8 * counter <= 63) {
                if (!isEmpty(board.getCells()[kingCell.getIndex() + 8 * counter])) {
                    if (isEnemy(board.getCells()[kingCell.getIndex() + 8 * counter], kingCell.getWhatSide())
                            && board.getCells()[kingCell.getIndex() + 8 * counter].getPiece().getOwnCellIndex() != 0
                            && board.getCells()[kingCell.getIndex() + 8 * counter].getPiece().getOwnCellIndex() != 1
                            && board.getCells()[kingCell.getIndex() + 8 * counter].getPiece().getOwnCellIndex() != 2
                            && board.getCells()[kingCell.getIndex() + 8 * counter].getPiece().getOwnCellIndex() != 5) {
                        setCheck(true);
                        return kingCell.getIndex() + 8 * counter;
                    } else break;
                }
            } else break;
        }
        return -1;
    }

    public int checkFromRight(Cell kingCell, Board board) {
        for (counter = 1; counter < 8; counter++) {
            if (kingCell.getIndex() + 1 * counter <= 63 && (kingCell.getIndex() + 1 * counter) % 8 != 0) {
                if (!isEmpty(board.getCells()[kingCell.getIndex() + 1 * counter])) {
                    if (isEnemy(board.getCells()[kingCell.getIndex() + 1 * counter], kingCell.getWhatSide())
                            && board.getCells()[kingCell.getIndex() + 1 * counter].getPiece().getOwnCellIndex() != 0
                            && board.getCells()[kingCell.getIndex() + 1 * counter].getPiece().getOwnCellIndex() != 1
                            && board.getCells()[kingCell.getIndex() + 1 * counter].getPiece().getOwnCellIndex() != 2
                            && board.getCells()[kingCell.getIndex() + 1 * counter].getPiece().getOwnCellIndex() != 5) {
                        setCheck(true);
                        return kingCell.getIndex() + 1 * counter;
                    } else break;
                }
            } else break;
        }
        return -1;
    }

    public int checkFromLeft(Cell kingCell, Board board) {
        for (counter = 1; counter < 8; counter++) {
            if (kingCell.getIndex() - 1 * counter <= 63 && (kingCell.getIndex() - 1 * counter + 1) % 8 != 0) {
                if (!isEmpty(board.getCells()[kingCell.getIndex() - 1 * counter])) {
                    if (isEnemy(board.getCells()[kingCell.getIndex() - 1 * counter], kingCell.getWhatSide())
                            && board.getCells()[kingCell.getIndex() - 1 * counter].getPiece().getOwnCellIndex() != 0
                            && board.getCells()[kingCell.getIndex() - 1 * counter].getPiece().getOwnCellIndex() != 1
                            && board.getCells()[kingCell.getIndex() - 1 * counter].getPiece().getOwnCellIndex() != 2
                            && board.getCells()[kingCell.getIndex() - 1 * counter].getPiece().getOwnCellIndex() != 5) {
                        return kingCell.getIndex() - 1 * counter;
                    } else break;
                }
            } else break;
        }
        return -1;
    }

    public int checkFromLeftdown(Cell kingCell, Board board) {
        for (counter = 1; counter < 8; counter++) {
            if (kingCell.getIndex() + 7 * counter < 63
                    && ((kingCell.getIndex() + 7 * counter) + 1) % 8 != 0
                    && kingCell.getIndex() + 7 * counter > -1) {
                if (!isEmpty(board.getCells()[kingCell.getIndex() + 7 * counter])) {
                    if (counter == 1 && kingCell.getWhatSide() == 'b' && isEnemy(board.getCells()[kingCell.getIndex() + 7 * counter], kingCell.getWhatSide())
                            && board.getCells()[kingCell.getIndex() + 7 * counter].getPiece().getOwnCellIndex() == 0) {
                        setCheck(true);
                        return kingCell.getIndex() + 7 * counter;
                    }
                    if (isEnemy(board.getCells()[kingCell.getIndex() + 7 * counter], kingCell.getWhatSide())
                            && board.getCells()[kingCell.getIndex() + 7 * counter].getPiece().getOwnCellIndex() != 0
                            && board.getCells()[kingCell.getIndex() + 7 * counter].getPiece().getOwnCellIndex() != 1
                            && board.getCells()[kingCell.getIndex() + 7 * counter].getPiece().getOwnCellIndex() != 3
                            && board.getCells()[kingCell.getIndex() + 7 * counter].getPiece().getOwnCellIndex() != 5) {
                        setCheck(true);
                        return kingCell.getIndex() + 7 * counter;
                    } else break;
                }
            } else break;
        }
        return -1;
    }

    public int checkFromRighttop(Cell kingCell, Board board) {
        for (counter = 1; counter < 8; counter++) {
            if (kingCell.getIndex() - 7 * counter > -1
                    && (kingCell.getIndex() - 7 * counter) % 8 != 0
                    && kingCell.getIndex() - 7 * counter < 63) {
                if (!isEmpty(board.getCells()[kingCell.getIndex() - 7 * counter])) {
                    if (counter == 1 && kingCell.getWhatSide() == 'w' && isEnemy(board.getCells()[kingCell.getIndex() - 7 * counter], kingCell.getWhatSide())
                            && board.getCells()[kingCell.getIndex() - 7 * counter].getPiece().getOwnCellIndex() == 0) {
                        setCheck(true);
                        return kingCell.getIndex() - 7 * counter;
                    }
                    if (isEnemy(board.getCells()[kingCell.getIndex() - 7 * counter], kingCell.getWhatSide())
                            && board.getCells()[kingCell.getIndex() - 7 * counter].getPiece().getOwnCellIndex() != 0
                            && board.getCells()[kingCell.getIndex() - 7 * counter].getPiece().getOwnCellIndex() != 1
                            && board.getCells()[kingCell.getIndex() - 7 * counter].getPiece().getOwnCellIndex() != 3
                            && board.getCells()[kingCell.getIndex() - 7 * counter].getPiece().getOwnCellIndex() != 5) {
                        setCheck(true);
                        return kingCell.getIndex() - 7 * counter;
                    } else break;
                }
            } else break;
        }
        return -1;
    }

    public int checkFromRightdown(Cell kingCell, Board board) {
        for (counter = 1; counter < 8; counter++) {
            if (kingCell.getIndex() + 9 * counter <= 63
                    && (kingCell.getIndex() + 9 * counter) % 8 != 0
                    && kingCell.getIndex() + 9 * counter > -1) {
                if (!isEmpty(board.getCells()[kingCell.getIndex() + 9 * counter])) {
                    if (counter == 1 && kingCell.getWhatSide() == 'b' && isEnemy(board.getCells()[kingCell.getIndex() + 9 * counter], kingCell.getWhatSide())
                            && board.getCells()[kingCell.getIndex() + 9 * counter].getPiece().getOwnCellIndex() == 0) {
                        setCheck(true);
                        return kingCell.getIndex() + 9 * counter;
                    }
                    if (isEnemy(board.getCells()[kingCell.getIndex() + 9 * counter], kingCell.getWhatSide())
                            && board.getCells()[kingCell.getIndex() + 9 * counter].getPiece().getOwnCellIndex() != 0
                            && board.getCells()[kingCell.getIndex() + 9 * counter].getPiece().getOwnCellIndex() != 1
                            && board.getCells()[kingCell.getIndex() + 9 * counter].getPiece().getOwnCellIndex() != 3
                            && board.getCells()[kingCell.getIndex() + 9 * counter].getPiece().getOwnCellIndex() != 5) {
                        setCheck(true);
                        return kingCell.getIndex() + 9 * counter;
                    } else break;
                }
            } else break;
        }
        return -1;
    }

    public int checkFromLefttop(Cell kingCell, Board board) {
        for (counter = 1; counter < 8; counter++) {
            if (kingCell.getIndex() - 9 * counter > -1
                    && ((kingCell.getIndex() - 9 * counter) + 1) % 8 != 0
                    && kingCell.getIndex() - 9 * counter < 63) {
                if (!isEmpty(board.getCells()[kingCell.getIndex() - 9 * counter])) {
                    if (counter == 1 && kingCell.getWhatSide() == 'w' && isEnemy(board.getCells()[kingCell.getIndex() - 9 * counter], kingCell.getWhatSide())
                            && board.getCells()[kingCell.getIndex() - 9 * counter].getPiece().getOwnCellIndex() == 0) {
                        setCheck(true);
                        return kingCell.getIndex() - 9 * counter;
                    }
                    if (isEnemy(board.getCells()[kingCell.getIndex() - 9 * counter], kingCell.getWhatSide())
                            && board.getCells()[kingCell.getIndex() - 9 * counter].getPiece().getOwnCellIndex() != 0
                            && board.getCells()[kingCell.getIndex() - 9 * counter].getPiece().getOwnCellIndex() != 1
                            && board.getCells()[kingCell.getIndex() - 9 * counter].getPiece().getOwnCellIndex() != 3
                            && board.getCells()[kingCell.getIndex() - 9 * counter].getPiece().getOwnCellIndex() != 5) {
                        setCheck(true);
                        return kingCell.getIndex() - 9 * counter;
                    } else break;
                }
            } else break;
        }
        return -1;
    }

    public int checkFromTwoLeftOneTop(Cell kingCell, Board board) {
        if (kingCell.getIndex() % 8 != 0
                && (kingCell.getIndex() - 1) % 8 != 0
                && kingCell.getIndex() <= 63
                && kingCell.getIndex() >= 2
                && kingCell.getIndex() - 10 >= 0) {
            if (!isEmpty(board.getCells()[kingCell.getIndex() - 10]) && board.getCells()[kingCell.getIndex() - 10].getPiece().getOwnCellIndex() == 1) {
                if (isEnemy(board.getCells()[kingCell.getIndex() - 10], kingCell.getWhatSide())) {
                    setCheck(true);
                    return kingCell.getIndex() - 10;
                }
            }
        }
        return -1;
    }

    public int checkFromTwoRightOneDown(Cell kingCell, Board board) {
        if ((kingCell.getIndex() + 1) % 8 != 0
                && (kingCell.getIndex() + 2) % 8 != 0
                && kingCell.getIndex() <= 61
                && kingCell.getIndex() >= 0
                && kingCell.getIndex() + 10 <= 63) {
            if (!isEmpty(board.getCells()[kingCell.getIndex() + 10]) && board.getCells()[kingCell.getIndex() + 10].getPiece().getOwnCellIndex() == 1) {
                if (isEnemy(board.getCells()[kingCell.getIndex() + 10], kingCell.getWhatSide())) {
                    setCheck(true);
                    return kingCell.getIndex() + 10;
                }
            }
        }
        return -1;
    }

    public int checkFromTwoLeftOneDown(Cell kingCell, Board board) {
        if (kingCell.getIndex() % 8 != 0
                && (kingCell.getIndex() - 1) % 8 != 0
                && kingCell.getIndex() <= 61
                && kingCell.getIndex() >= 0
                && kingCell.getIndex() + 6 <= 63) {
            if (!isEmpty(board.getCells()[kingCell.getIndex() + 6]) && board.getCells()[kingCell.getIndex() + 6].getPiece().getOwnCellIndex() == 1) {
                if (isEnemy(board.getCells()[kingCell.getIndex() + 6], kingCell.getWhatSide())) {
                    setCheck(true);
                    return kingCell.getIndex() + 6;
                }
            }
        }
        return -1;
    }

    public int checkFromTwoRightOneTop(Cell kingCell, Board board) {
        if ((kingCell.getIndex() + 1) % 8 != 0
                && (kingCell.getIndex() + 2) % 8 != 0
                && kingCell.getIndex() <= 63
                && kingCell.getIndex() >= 2
                && kingCell.getIndex() - 6 >= 0) {
            if (!isEmpty(board.getCells()[kingCell.getIndex() - 6]) && board.getCells()[kingCell.getIndex() - 6].getPiece().getOwnCellIndex() == 1) {
                if (isEnemy(board.getCells()[kingCell.getIndex() - 6], kingCell.getWhatSide())) {
                    setCheck(true);
                    return kingCell.getIndex() - 6;
                }
            }
        }
        return -1;
    }

    public int checkFromOneLeftTwoTop(Cell kingCell, Board board) {
        if (kingCell.getIndex() >= 17
                && kingCell.getIndex() % 8 != 0
                && kingCell.getIndex() <= 63) {
            if (!isEmpty(board.getCells()[kingCell.getIndex() - 17]) && board.getCells()[kingCell.getIndex() - 17].getPiece().getOwnCellIndex() == 1) {
                if (isEnemy(board.getCells()[kingCell.getIndex() - 17], kingCell.getWhatSide())) {
                    setCheck(true);
                    return kingCell.getIndex() - 17;
                }
            }
        }
        return -1;
    }

    public int checkFromOneRightTwoDown(Cell kingCell, Board board) {
        if (kingCell.getIndex() <= 47
                && (kingCell.getIndex() + 1) % 8 != 0
                && kingCell.getIndex() >= 0) {
            if (!isEmpty(board.getCells()[kingCell.getIndex() + 17]) && board.getCells()[kingCell.getIndex() + 17].getPiece().getOwnCellIndex() == 1) {
                if (isEnemy(board.getCells()[kingCell.getIndex() + 17], kingCell.getWhatSide())) {
                    setCheck(true);
                    return kingCell.getIndex() + 17;
                }
            }
        }
        return -1;
    }

    public int checkFromOneLeftTwoDown(Cell kingCell, Board board) {
        if (kingCell.getIndex() <= 47
                && kingCell.getIndex() >= 0
                && kingCell.getIndex() % 8 != 0) {
            if (!isEmpty(board.getCells()[kingCell.getIndex() + 15]) && board.getCells()[kingCell.getIndex() + 15].getPiece().getOwnCellIndex() == 1) {
                if (isEnemy(board.getCells()[kingCell.getIndex() + 15], kingCell.getWhatSide())) {
                    setCheck(true);
                    return kingCell.getIndex() + 15;
                }
            }
        }
        return -1;
    }

    public int checkFromOneRightTwoTop(Cell kingCell, Board board) {
        if (kingCell.getIndex() >= 16
                && kingCell.getIndex() <= 63
                && (kingCell.getIndex() + 1) % 8 != 0) {
            if (!isEmpty(board.getCells()[kingCell.getIndex() - 15]) && board.getCells()[kingCell.getIndex() - 15].getPiece().getOwnCellIndex() == 1) {
                if (isEnemy(board.getCells()[kingCell.getIndex() - 15], kingCell.getWhatSide())) {
                    setCheck(true);
                    return kingCell.getIndex() - 15;
                }
            }
        }
        return -1;
    }

    public boolean isEmpty(Cell cell) {
        if (cell.getWhatSide() == '.' && cell.getPiece() == null)
            return true;
        return false;
    }

    public boolean isEnemy(Cell cell, char ownSide) {
        if (!isEmpty(cell) && ownSide != '.' && cell.getWhatSide() != ownSide)
            return true;
        return false;
    }

    public void Reset() {
        setCheck(false);
        setWhoseTurn('.');
    }
}
