package com.mustafamese.chess.Pieces;

import android.graphics.Bitmap;

import com.mustafamese.chess.GameZone.Board;
import com.mustafamese.chess.GameZone.Cell;
import com.mustafamese.chess.Manager.GameManager;

import java.util.ArrayList;

public class Pawn implements Piece {

    private Bitmap pawnImage;
    private ArrayList<Integer> cellIndexes;
    private int ownCellIndex;
    private int counter = 0;
    private boolean isMoved = false;
    private boolean top = false, down = false, topLeft = false, topRight = false, downRight = false, downLeft = false;

    public Pawn(Bitmap image) {
        this.pawnImage = image;
        ownCellIndex = 0;
    }

    public Bitmap getImage() {
        return pawnImage;
    }

    public void setImage(Bitmap pawnImage) {
        this.pawnImage = pawnImage;
    }

    @Override
    public boolean isLegalToTakePiece(Cell cell, char ownSide) {
        if (!isEmpty(cell) && ownSide != cell.getWhatSide())
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
        if (isEmpty(cell))
            return true;
        return false;
    }

    public ArrayList<Integer> allowedMoves(Cell cell, Board board, GameManager gameManager) {
        cellIndexes = new ArrayList<>();
        if (!gameManager.classicRuleManager.getCheck()) {
            char side = cell.getWhatSide();
            cell.deletePiece();
            checkKing(board, gameManager);
            cell.setPiece(this);
            cell.setWhatSide(side);
            if (gameManager.classicRuleManager.getCheck()) {
                checkMoves(cell, board);
                gameManager.classicRuleManager.setCheck(false);
                Reset();
                return cellIndexes;
            }
        }
        setMoves(cell, board);
        Reset();
        return cellIndexes;
    }

    private void setMoves(Cell cell, Board board){
        if (isWhite(cell)) {
            topMoves(cell, board);
            topLeftMoves(cell, board);
            topRightMoves(cell, board);
        } else if (!isWhite(cell)) {
            downMoves(cell, board);
            downRightMoves(cell, board);
            downLeftMoves(cell, board);
        }
    }

    private void checkKing(Board board, GameManager gameManager){
        if (gameManager.classicRuleManager.getWhoseTurn() == 'w') {
            if (gameManager.classicRuleManager.checkFromTop(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1)
                top = true;
            if (gameManager.classicRuleManager.checkFromRighttop(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1)
                topRight = true;
            if (gameManager.classicRuleManager.checkFromLefttop(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1)
                topLeft = true;
        } else {
            if (gameManager.classicRuleManager.checkFromDown(board.getCells()[gameManager.pieceManager.getBlackKingCellIndex()], board) != -1)
                down = true;
            if (gameManager.classicRuleManager.checkFromRightdown(board.getCells()[gameManager.pieceManager.getBlackKingCellIndex()], board) != -1)
                downRight = true;
            if (gameManager.classicRuleManager.checkFromLeftdown(board.getCells()[gameManager.pieceManager.getBlackKingCellIndex()], board) != -1)
                downLeft = true;
        }
    }

    private void checkMoves(Cell cell, Board board){
        if (isWhite(cell)) {
            if (top)
                topMoves(cell, board);
            if (topLeft)
                topLeftMoves(cell, board);
            if (topRight)
                topRightMoves(cell, board);
        } else if (!isWhite(cell)) {
            if (down)
                downMoves(cell, board);
            if (downRight)
                downRightMoves(cell, board);
            if (downLeft)
                downLeftMoves(cell, board);
        }
    }

    private void topMoves(Cell cell, Board board) {
        for (counter = 1; counter < 3; counter++) {
            if ((cell.getIndex() - 8 * counter) >= 0
                    && isLegalToMove(board.getCells()[cell.getIndex() - 8 * counter], cell.getWhatSide())
                    && !isLegalToTakePiece(board.getCells()[cell.getIndex() - 8 * counter], cell.getWhatSide())) {
                // At the beginning pawn can move two cells forward.
                if (counter == 2 && !(cell.getIndex() >= 48 && cell.getIndex() <= 55))
                    break;
                cellIndexes.add(cell.getIndex() - 8 * counter);
            }
            else break;
        }
    }

    private void topLeftMoves(Cell cell, Board board) {
        if (cell.getIndex() - 9 >= 0
                && (cell.getIndex() - 9) % 8 != 7
                && isLegalToMove(board.getCells()[cell.getIndex() - 9], cell.getWhatSide())) {
            // If pawn can take any piece, it can move diagonal move.
            if (isLegalToTakePiece(board.getCells()[cell.getIndex() - 9], cell.getWhatSide()))
                cellIndexes.add(cell.getIndex() - 9);
        }
    }

    private void topRightMoves(Cell cell, Board board) {
        if (cell.getIndex() - 7 >= 0
                && (cell.getIndex() - 7) % 8 != 0
                && isLegalToMove(board.getCells()[cell.getIndex() - 7], cell.getWhatSide())) {
            // If pawn can take any piece, it can move diagonal move.
            if (isLegalToTakePiece(board.getCells()[cell.getIndex() - 7], cell.getWhatSide()))
                cellIndexes.add(cell.getIndex() - 7);
        }
    }

    private void downMoves(Cell cell, Board board) {
        for (counter = 1; counter < 3; counter++) {
            if ((cell.getIndex() + 8 * counter) <= 63
                    && isLegalToMove(board.getCells()[cell.getIndex() + 8 * counter], cell.getWhatSide())
                    && !isLegalToTakePiece(board.getCells()[cell.getIndex() + 8 * counter], cell.getWhatSide())) {
                // At the beginning pawn can move two cells forward.
                if (counter == 2 && !(cell.getIndex() >= 8 && cell.getIndex() <= 15))
                    break;
                cellIndexes.add(cell.getIndex() + 8 * counter);
            }
            else break;
        }
    }

    private void downRightMoves(Cell cell, Board board) {
        if (cell.getIndex() + 9 <= 63
                && (cell.getIndex() + 9) % 8 != 0
                && isLegalToMove(board.getCells()[cell.getIndex() + 9], cell.getWhatSide())) {
            // If pawn can take any piece, it can move diagonal move.
            if (isLegalToTakePiece(board.getCells()[cell.getIndex() + 9], cell.getWhatSide()))
                cellIndexes.add(cell.getIndex() + 9);
        }
    }

    private void downLeftMoves(Cell cell, Board board) {
        if (cell.getIndex() + 7 <= 63
                && (cell.getIndex() + 7) % 8 != 7
                && isLegalToMove(board.getCells()[cell.getIndex() + 7], cell.getWhatSide())) {
            // If pawn can take any piece, it can move diagonal move.
            if (isLegalToTakePiece(board.getCells()[cell.getIndex() + 7], cell.getWhatSide()))
                cellIndexes.add(cell.getIndex() + 7);
        }
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

    private void Reset(){
        top = false;
        topLeft = false;
        topRight = false;
        downLeft = false;
        downRight = false;
        downLeft = false;
    }
}
