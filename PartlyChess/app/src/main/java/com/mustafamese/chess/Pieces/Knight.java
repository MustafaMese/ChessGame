package com.mustafamese.chess.Pieces;

import android.graphics.Bitmap;

import com.mustafamese.chess.GameZone.Board;
import com.mustafamese.chess.GameZone.Cell;
import com.mustafamese.chess.Manager.GameManager;

import java.util.ArrayList;

public class Knight implements Piece {

    private Bitmap knightImage;
    private ArrayList<Integer> cellIndexes;
    private int ownCellIndex;
    private boolean isMoved = false;

    public Knight(Bitmap image) {
        knightImage = image;
        ownCellIndex = 1;
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

    @Override
    public ArrayList<Integer> allowedMoves(Cell cell, Board board, GameManager gameManager) {
        cellIndexes = new ArrayList<>();

        if (!gameManager.classicRuleManager.getCheck()) {
            char side = cell.getWhatSide();
            cell.deletePiece();
            checkKing(board, gameManager);
            cell.setPiece(this);
            cell.setWhatSide(side);
            if (gameManager.classicRuleManager.getCheck()) {
                gameManager.classicRuleManager.setCheck(false);
                return cellIndexes;
            }
        }
        setMoves(cell, board);
        return cellIndexes;
    }

    private void checkKing(Board board, GameManager gameManager){
        if (gameManager.classicRuleManager.getWhoseTurn() == 'w')
            gameManager.classicRuleManager.controlCheck(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board);
        else
            gameManager.classicRuleManager.controlCheck(board.getCells()[gameManager.pieceManager.getBlackKingCellIndex()], board);
    }

    private void setMoves(Cell cell, Board board){
        twoLeftOneTopMoves(cell, board);
        twoRightOneDownMoves(cell, board);
        twoLeftOneDownMoves(cell, board);
        twoRightOneTopMoves(cell, board);
        oneLeftTwoTopMoves(cell, board);
        oneRightTwoDownMoves(cell, board);
        oneLeftTwoDownMoves(cell, board);
        oneRightTwoTopMoves(cell, board);
    }

    private void twoLeftOneTopMoves(Cell cell, Board board) {
        if (cell.getIndex() % 8 != 0
                && (cell.getIndex() - 1) % 8 != 0
                && cell.getIndex() <= 63
                && cell.getIndex() >= 2
                && cell.getIndex() - 10 >= 0
                && isLegalToMove(board.getCells()[cell.getIndex() - 10], cell.getWhatSide())) {
            cellIndexes.add(cell.getIndex() - 10);
        }
    }

    private void twoRightOneDownMoves(Cell cell, Board board) {
        if ((cell.getIndex() + 1) % 8 != 0
                && (cell.getIndex() + 2) % 8 != 0
                && cell.getIndex() <= 61
                && cell.getIndex() >= 0
                && cell.getIndex() + 10 <= 63
                && isLegalToMove(board.getCells()[cell.getIndex() + 10], cell.getWhatSide())) {
            cellIndexes.add(cell.getIndex() + 10);
        }
    }

    private void twoLeftOneDownMoves(Cell cell, Board board) {
        if (cell.getIndex() % 8 != 0
                && (cell.getIndex() - 1) % 8 != 0
                && cell.getIndex() <= 61
                && cell.getIndex() >= 0
                && cell.getIndex() + 6 <= 63
                && isLegalToMove(board.getCells()[cell.getIndex() + 6], cell.getWhatSide())) {
            cellIndexes.add(cell.getIndex() + 6);
        }
    }

    private void twoRightOneTopMoves(Cell cell, Board board) {
        if ((cell.getIndex() + 1) % 8 != 0
                && (cell.getIndex() + 2) % 8 != 0
                && cell.getIndex() <= 63
                && cell.getIndex() >= 2
                && cell.getIndex() - 6 >= 0
                && isLegalToMove(board.getCells()[cell.getIndex() - 6], cell.getWhatSide())) {
            cellIndexes.add(cell.getIndex() - 6);
        }
    }

    private void oneLeftTwoTopMoves(Cell cell, Board board) {
        if (cell.getIndex() >= 17
                && cell.getIndex() % 8 != 0
                && cell.getIndex() <= 63
                && isLegalToMove(board.getCells()[cell.getIndex() - 17], cell.getWhatSide())) {
            cellIndexes.add(cell.getIndex() - 17);
        }
    }

    private void oneRightTwoDownMoves(Cell cell, Board board) {
        if (cell.getIndex() <= 47
                && (cell.getIndex() + 1) % 8 != 0
                && cell.getIndex() >= 0
                && isLegalToMove(board.getCells()[cell.getIndex() + 17], cell.getWhatSide())) {
            cellIndexes.add(cell.getIndex() + 17);
        }
    }

    private void oneLeftTwoDownMoves(Cell cell, Board board) {
        if (cell.getIndex() <= 47
                && cell.getIndex() >= 0
                && cell.getIndex() % 8 != 0
                && isLegalToMove(board.getCells()[cell.getIndex() + 15], cell.getWhatSide())) {
            cellIndexes.add(cell.getIndex() + 15);
        }
    }

    private void oneRightTwoTopMoves(Cell cell, Board board) {
        if (cell.getIndex() >= 16
                && cell.getIndex() <= 63
                && (cell.getIndex() + 1) % 8 != 0
                && isLegalToMove(board.getCells()[cell.getIndex() - 15], cell.getWhatSide())) {
            cellIndexes.add(cell.getIndex() - 15);
        }

    }

    @Override
    public Bitmap getImage() {
        return knightImage;
    }

    @Override
    public void setImage(Bitmap image) {
        this.knightImage = image;
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
    public boolean getIsMoved(){ return this.isMoved; }

    @Override
    public void setIsMoved(boolean isMoved) { this.isMoved = isMoved; }

}
