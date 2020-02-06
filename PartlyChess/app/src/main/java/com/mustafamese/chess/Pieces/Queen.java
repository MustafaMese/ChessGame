package com.mustafamese.chess.Pieces;

import android.graphics.Bitmap;

import com.mustafamese.chess.GameZone.Board;
import com.mustafamese.chess.GameZone.Cell;
import com.mustafamese.chess.Manager.GameManager;

import java.util.ArrayList;

public class Queen implements Piece {

    private Bitmap queenImage;
    private ArrayList<Integer> cellIndexes;
    private int ownCellIndex;
    private int counter = 0;
    private boolean isMoved = false;
    private boolean top = false, down = false, right = false, left = false,
                    topRight = false, topLeft = false, downRight = false, downLeft = false;

    public Queen(Bitmap image){
        queenImage = image;
        ownCellIndex = 4;
    }

    @Override
    public boolean isWhite(Cell cell){
        if(cell.getWhatSide() != 'w')
            return false;
        return true;
    }

    @Override
    public boolean isEmpty(Cell cell){
        if(cell.getWhatSide() != '.')
            return false;
        return true;
    }

    @Override
    public boolean isLegalToMove(Cell cell, char ownSide) {
        if(isEmpty(cell) || cell.getWhatSide() != ownSide)
            return true;
        return false;
    }

    @Override
    public boolean isLegalToTakePiece(Cell cell, char ownSide){
        if(cell.getWhatSide() != '.' && ownSide != cell.getWhatSide())
            return true;
        return false;
    }

    @Override
    public ArrayList<Integer> allowedMoves(Cell cell, Board board, GameManager gameManager) {
        cellIndexes = new ArrayList<>();

        if(!gameManager.classicRuleManager.getCheck()){
            char side = cell.getWhatSide();
            cell.deletePiece();
            checkKing(board, gameManager);
            cell.setPiece(this);
            cell.setWhatSide(side);
            if(gameManager.classicRuleManager.getCheck()){
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

    private void checkMoves(Cell cell, Board board){
        if(top)
            topMoves(cell, board);
        if(down)
            downMoves(cell, board);
        if(right)
            rightMoves(cell, board);
        if(left)
            leftMoves(cell, board);
        if(topLeft)
            leftTopMoves(cell, board);
        if(topRight)
            rightTopMoves(cell, board);
        if(downLeft)
            leftDownMoves(cell, board);
        if(downRight)
            rightDownMoves(cell, board);
    }

    private void checkKing(Board board, GameManager gameManager){
        if(gameManager.classicRuleManager.getWhoseTurn() == 'w') {
            if(gameManager.classicRuleManager.checkFromTop(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1)
                top = true;
            if(gameManager.classicRuleManager.checkFromDown(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1)
                down = true;
            if(gameManager.classicRuleManager.checkFromRight(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1)
                right = true;
            if(gameManager.classicRuleManager.checkFromLeft(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1)
                left = true;
            if (gameManager.classicRuleManager.checkFromLeftdown(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1)
                downLeft = true;
            if (gameManager.classicRuleManager.checkFromLefttop(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1)
                topLeft = true;
            if (gameManager.classicRuleManager.checkFromRightdown(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1)
                downRight = true;
            if (gameManager.classicRuleManager.checkFromRighttop(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1)
                topRight = true;
        }
        else {
            if(gameManager.classicRuleManager.checkFromTop(board.getCells()[gameManager.pieceManager.getBlackKingCellIndex()], board) != -1)
                top = true;
            if(gameManager.classicRuleManager.checkFromDown(board.getCells()[gameManager.pieceManager.getBlackKingCellIndex()], board) != -1)
                down = true;
            if(gameManager.classicRuleManager.checkFromRight(board.getCells()[gameManager.pieceManager.getBlackKingCellIndex()], board) != -1)
                right = true;
            if(gameManager.classicRuleManager.checkFromLeft(board.getCells()[gameManager.pieceManager.getBlackKingCellIndex()], board) != -1)
                left = true;
            if (gameManager.classicRuleManager.checkFromLeftdown(board.getCells()[gameManager.pieceManager.getBlackKingCellIndex()], board) != -1)
                downLeft = true;
            if (gameManager.classicRuleManager.checkFromLefttop(board.getCells()[gameManager.pieceManager.getBlackKingCellIndex()], board) != -1)
                topLeft = true;
            if (gameManager.classicRuleManager.checkFromRightdown(board.getCells()[gameManager.pieceManager.getBlackKingCellIndex()], board) != -1)
                downRight = true;
            if (gameManager.classicRuleManager.checkFromRighttop(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1)
                topRight = true;
        }
    }

    private void setMoves(Cell cell, Board board){
        leftDownMoves(cell, board);
        rightDownMoves(cell, board);
        rightTopMoves(cell, board);
        leftTopMoves(cell, board);
        topMoves(cell, board);
        downMoves(cell, board);
        rightMoves(cell, board);
        leftMoves(cell, board);
    }

    private void leftDownMoves(Cell cell, Board board){
        for(counter = 1; counter < 8; counter++){
            if(cell.getIndex() + 7 * counter < 63
                    && ((cell.getIndex() + 7 * counter) + 1) % 8 != 0
                    && cell.getIndex() + 7 * counter > -1
                    && isLegalToMove((board.getCells()[cell.getIndex() + 7 * counter]), cell.getWhatSide())){
                cellIndexes.add(board.getCells()[cell.getIndex() + 7 * counter].getIndex());
                if(isLegalToTakePiece(board.getCells()[cell.getIndex() + 7 * counter], cell.getWhatSide()))
                    break;
            }
            else break;
        }
    }

    private void rightTopMoves(Cell cell, Board board){
        for(counter = 1; counter < 8; counter++){
            if(cell.getIndex() - 7 * counter > -1
                    && (cell.getIndex() - 7 * counter) % 8 != 0
                    && cell.getIndex() - 7 * counter < 63
                    && isLegalToMove(board.getCells()[cell.getIndex() - 7 * counter], cell.getWhatSide())){
                cellIndexes.add(board.getCells()[cell.getIndex() - 7 * counter].getIndex());
                if(isLegalToTakePiece(board.getCells()[cell.getIndex() - 7 * counter], cell.getWhatSide()))
                    break;
            }
            else break;
        }
    }

    private void rightDownMoves(Cell cell, Board board){
        for(counter = 1; counter < 8; counter++){
            if(cell.getIndex() + 9 * counter <= 63
                    && (cell.getIndex() + 9 * counter) % 8 != 0
                    && cell.getIndex() + 9 * counter > -1
                    && isLegalToMove(board.getCells()[cell.getIndex() + 9 * counter], cell.getWhatSide())){
                cellIndexes.add(cell.getIndex() + 9 * counter);
                if(isLegalToTakePiece(board.getCells()[cell.getIndex() + 9 * counter], cell.getWhatSide()))
                    break;
            }
            else break;
        }
    }

    private void leftTopMoves(Cell cell, Board board){
        for(counter = 1; counter < 8; counter++){
            if(cell.getIndex() - 9 * counter > -1
                    && ((cell.getIndex() - 9 * counter) + 1) % 8 != 0
                    && cell.getIndex() - 9 * counter < 63
                    && isLegalToMove(board.getCells()[cell.getIndex() - 9 * counter], cell.getWhatSide())){
                cellIndexes.add(board.getCells()[cell.getIndex() - 9 * counter].getIndex());
                if(isLegalToTakePiece(board.getCells()[cell.getIndex() - 9 * counter], cell.getWhatSide()))
                    break;
            }
            else break;
        }
    }

    private void topMoves(Cell cell, Board board){
        for(counter = 1; counter < 8; counter++){
            if(cell.getIndex() - 8 * counter >= 0 &&
                    isLegalToMove(board.getCells()[cell.getIndex() - 8 * counter],  cell.getWhatSide())){
                cellIndexes.add(cell.getIndex() - 8 * counter);
                if(isLegalToTakePiece(board.getCells()[cell.getIndex() - 8 * counter],  cell.getWhatSide()))
                    break;
            }
            else break;
        }
    }

    private void downMoves(Cell cell, Board board){
        for(counter = 1; counter < 8; counter++){
            if(cell.getIndex() + 8 * counter <= 63 &&
                    isLegalToMove(board.getCells()[cell.getIndex() + 8 * counter],  cell.getWhatSide())){
                cellIndexes.add(cell.getIndex() + 8 * counter);
                if(isLegalToTakePiece(board.getCells()[cell.getIndex() + 8 * counter],  cell.getWhatSide()))
                    break;
            }
            else break;
        }
    }

    private void rightMoves(Cell cell, Board board){
        for(counter = 1; counter < 8; counter++) {
            if (cell.getIndex() + 1 * counter <= 63
                    && (cell.getIndex() + 1 * counter) % 8 != 0
                    && isLegalToMove(board.getCells()[cell.getIndex() + 1 * counter],  cell.getWhatSide()) ) {
                cellIndexes.add(cell.getIndex() + 1 * counter);
                if(isLegalToTakePiece(board.getCells()[cell.getIndex() + 1 * counter],  cell.getWhatSide()))
                    break;
            }
            else break;
        }
    }

    private void leftMoves(Cell cell, Board board){
        for(counter = 1; counter < 8; counter++){
            if(cell.getIndex() - 1 * counter >= 0
                    && (cell.getIndex() - 1 * counter + 1) % 8 != 0
                    && isLegalToMove(board.getCells()[cell.getIndex() - 1 * counter],  cell.getWhatSide())){
                cellIndexes.add(cell.getIndex() - 1 * counter);
                if(isLegalToTakePiece(board.getCells()[cell.getIndex() - 1 * counter],  cell.getWhatSide()))
                    break;
            }
            else break;
        }
    }

    @Override
    public Bitmap getImage() {
        return queenImage;
    }

    @Override
    public void setImage(Bitmap image) {
        this.queenImage = image;
    }

    @Override
    public int getOwnCellIndex(){return ownCellIndex;}

    @Override
    public void setOwnCellIndex(int index){ this.ownCellIndex = index; }

    @Override
    public boolean getIsMoved(){ return this.isMoved; }

    @Override
    public void setIsMoved(boolean isMoved) { this.isMoved = isMoved; }

    private void Reset(){
        top = false;
        down = false;
        right = false;
        left = false;
        topLeft = false;
        topRight = false;
        downLeft = false;
        downRight = false;
    }
}
