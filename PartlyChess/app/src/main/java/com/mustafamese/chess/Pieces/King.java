package com.mustafamese.chess.Pieces;

import android.graphics.Bitmap;

import com.mustafamese.chess.GameZone.Board;
import com.mustafamese.chess.GameZone.Cell;
import com.mustafamese.chess.Manager.GameManager;

import java.util.ArrayList;

public class King implements Piece {

    private Bitmap kingImage;
    private ArrayList<Integer> cellIndexes;
    private int ownCellIndex;
    private boolean isMoved = false;

    public King(Bitmap image) {
        kingImage = image;
        ownCellIndex = 5;
    }

    @Override
    public boolean isLegalToTakePiece(Cell cell, char ownSide) {
        if (cell.getWhatSide() != '.' && ownSide != cell.getWhatSide())
            return true;
        return false;
    }

    @Override
    public boolean isWhite(Cell cell) {
        if (cell.getWhatSide() != 'w')
            return false;
        return true;
    }

    @Override
    public boolean isEmpty(Cell cell) {
        if (cell.getWhatSide() != '.')
            return false;
        return true;
    }

    @Override
    public boolean isLegalToMove(Cell cell, char ownSide) {
        if (isEmpty(cell) || cell.getWhatSide() != ownSide)
            return true;
        return false;
    }

    // TODO ADD: Check control for king. Change position with Rook.
    @Override
    public ArrayList<Integer> allowedMoves(Cell cell, Board board, GameManager gameManager) {
        cellIndexes = new ArrayList<>();
        topMove(cell, board, gameManager);
        topLeftMove(cell, board, gameManager);
        topRightMove(cell, board, gameManager);
        rightMove(cell, board, gameManager);
        leftMove(cell, board, gameManager);
        downMove(cell, board, gameManager);
        downRightMove(cell, board, gameManager);
        downLeftMove(cell, board, gameManager);
        shortDistanceRookMove(cell, board, gameManager);
        longDistanceRokMove(cell, board, gameManager);
        return cellIndexes;
    }

    // Four times left
    private void longDistanceRokMove(Cell cell, Board board, GameManager gameManager) {
        for (int i = 1; i < 5; i++) {
            if (cell.getIndex() % 8 != 0) {
                if (i != 4) {
                    if (!isLegalToMove(board.getCells()[cell.getIndex() - 1 * i], cell.getWhatSide())) {
                        break;
                    }
                }
                else {
                    if (board.getCells()[cell.getIndex() - 1 * i].getPiece() != null
                            && board.getCells()[cell.getIndex() - 1 * i].getPiece().getOwnCellIndex() == 3
                            && !board.getCells()[cell.getIndex() - 1 * i].getPiece().getIsMoved()) {
                        cellIndexes.add(cell.getIndex() - 1 * i);
                        gameManager.classicRuleManager.setRokMove(cell.getIndex() - 1 * i);
                        gameManager.classicRuleManager.setRokDistance(1);
                    }
                }
            }
            else {
                break;
            }
        }
    }

    // Three times right
    private void shortDistanceRookMove(Cell cell, Board board, GameManager gameManager) {
        for (int i = 1; i < 4; i++) {
            if ((cell.getIndex() + 1 * i) % 8 != 0) {
                if (i != 3) {
                    if (!isLegalToMove(board.getCells()[cell.getIndex() + 1 * i], cell.getWhatSide())) {
                        break;
                    }
                }
                else {
                    if (board.getCells()[cell.getIndex() + 1 * i].getPiece() != null
                            && board.getCells()[cell.getIndex() + 1 * i].getPiece().getOwnCellIndex() == 3
                            && !board.getCells()[cell.getIndex() + 1 * i].getPiece().getIsMoved()) {
                        cellIndexes.add(cell.getIndex() + 1 * i);
                        gameManager.classicRuleManager.setRokMove(cell.getIndex() + 1 * i);
                        gameManager.classicRuleManager.setRokDistance(0);
                    }
                }
            }
            else {
                break;
            }
        }
    }


    private void topMove(Cell cell, Board board, GameManager gameManager) {
        if (cell.getIndex() - 8 >= 0
                && isLegalToMove(board.getCells()[cell.getIndex() - 8], cell.getWhatSide())) {
            if (gameManager.classicRuleManager.checkFromTop(board.getCells()[cell.getIndex() - 8], board) == -1)
                cellIndexes.add(cell.getIndex() - 8);
        }
    }

    private void downMove(Cell cell, Board board, GameManager gameManager) {
        if (cell.getIndex() + 8 <= 63
                && isLegalToMove(board.getCells()[cell.getIndex() + 8], cell.getWhatSide())) {
            if (gameManager.classicRuleManager.checkFromDown(board.getCells()[cell.getIndex() + 8], board) == -1)
                cellIndexes.add(cell.getIndex() + 8);
        }
    }

    private void leftMove(Cell cell, Board board, GameManager gameManager) {
        if ((cell.getIndex()) % 8 != 0
                && isLegalToMove(board.getCells()[cell.getIndex() - 1], cell.getWhatSide())) {
            if (gameManager.classicRuleManager.checkFromLeft(board.getCells()[cell.getIndex() - 1], board) == -1)
                cellIndexes.add(cell.getIndex() - 1);
        }
    }

    private void rightMove(Cell cell, Board board, GameManager gameManager) {
        if ((cell.getIndex() + 1) % 8 != 0
                && isLegalToMove(board.getCells()[cell.getIndex() + 1], cell.getWhatSide())) {
            if (gameManager.classicRuleManager.checkFromRight(board.getCells()[cell.getIndex() + 1], board) == -1)
                cellIndexes.add(cell.getIndex() + 1);
        }
    }

    private void topLeftMove(Cell cell, Board board, GameManager gameManager) {
        if (cell.getIndex() - 9 >= 0
                && (cell.getIndex() - 9) % 8 != 7
                && isLegalToMove(board.getCells()[cell.getIndex() - 9], cell.getWhatSide())) {
            if (gameManager.classicRuleManager.checkFromLefttop(board.getCells()[cell.getIndex() - 9], board) == -1)
                cellIndexes.add(cell.getIndex() - 9);
        }
    }

    private void topRightMove(Cell cell, Board board, GameManager gameManager) {
        if (cell.getIndex() - 7 >= 0
                && (cell.getIndex() - 7) % 8 != 0
                && isLegalToMove(board.getCells()[cell.getIndex() - 7], cell.getWhatSide())) {
            if (gameManager.classicRuleManager.checkFromRighttop(board.getCells()[cell.getIndex() - 7], board) == -1)
                cellIndexes.add(cell.getIndex() - 7);
        }
    }

    private void downLeftMove(Cell cell, Board board, GameManager gameManager) {
        if (cell.getIndex() + 7 <= 63
                && (cell.getIndex() + 7) % 8 != 7
                && isLegalToMove(board.getCells()[cell.getIndex() + 7], cell.getWhatSide())) {
            if (gameManager.classicRuleManager.checkFromLeftdown(board.getCells()[cell.getIndex() + 7], board) == -1)
                cellIndexes.add(cell.getIndex() + 7);
        }
    }

    private void downRightMove(Cell cell, Board board, GameManager gameManager) {
        if (cell.getIndex() + 9 <= 63
                && (cell.getIndex() + 9) % 8 != 0
                && isLegalToMove(board.getCells()[cell.getIndex() + 9], cell.getWhatSide())) {
            if (gameManager.classicRuleManager.checkFromRightdown(board.getCells()[cell.getIndex() + 9], board) == -1)
                cellIndexes.add(cell.getIndex() + 9);
        }
    }

    private void threeRightMove(Cell cell, Board board, GameManager gameManager) {
        if ((cell.getIndex() + 1) % 8 != 0
                && isLegalToMove(board.getCells()[cell.getIndex() + 1], cell.getWhatSide())) {
            cellIndexes.add(cell.getIndex() + 1);
        }
    }

    private void fourLeftMove(Cell cell, Board board, GameManager gameManager) {

    }

    @Override
    public Bitmap getImage() {
        return kingImage;
    }

    @Override
    public void setImage(Bitmap image) {
        this.kingImage = image;
    }

    @Override
    public int getOwnCellIndex() {
        return ownCellIndex;
    }

    @Override
    public void setOwnCellIndex(int index) {
        this.ownCellIndex = index;
    }

    @Override
    public boolean getIsMoved() {
        return this.isMoved;
    }

    @Override
    public void setIsMoved(boolean isMoved) {
        this.isMoved = isMoved;
    }
}
