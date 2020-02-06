package com.mustafamese.chess.Pieces;

import android.graphics.Bitmap;

import com.mustafamese.chess.GameZone.Board;
import com.mustafamese.chess.GameZone.Cell;
import com.mustafamese.chess.Manager.GameManager;

import java.util.ArrayList;

public class Bishop implements Piece {

    private Bitmap bishopImage;
    private ArrayList<Integer> cellIndexes;
    private int ownCellIndex;
    private boolean leftTop = false, leftDown = false, rightTop = false, rightDown = false;
    private int counter = 0;
    private boolean isMoved = false;

    public Bishop(Bitmap image){
        bishopImage = image;
        ownCellIndex = 2;
    }

    // . means empty
    // w means white
    // b means black

    @Override
    public boolean isLegalToTakePiece(Cell cell, char ownSide){
        if(cell.getWhatSide() != '.' && ownSide != cell.getWhatSide())
            return true;
        return false;
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
    public ArrayList<Integer> allowedMoves(Cell cell, Board board, GameManager gameManager) {
        cellIndexes = new ArrayList<>();
        if(!gameManager.classicRuleManager.getCheck()){
            char side = cell.getWhatSide();
            cell.deletePiece();
            checkKingDiagonal(board, gameManager);
            if(!gameManager.classicRuleManager.getCheck()){
                if(side == 'w'  && gameManager.classicRuleManager.controlCheckStraight(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1) {
                    cell.setPiece(this);
                    cell.setWhatSide(side);
                    gameManager.classicRuleManager.setCheck(false);
                    Reset();
                    return cellIndexes;
                }
                else if(side == 'b' && gameManager.classicRuleManager.controlCheckStraight(board.getCells()[gameManager.pieceManager.getBlackKingCellIndex()], board) != -1){
                    cell.setPiece(this);
                    cell.setWhatSide(side);
                    gameManager.classicRuleManager.setCheck(false);
                    Reset();
                    return cellIndexes;
                }
            }
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

    private void checkKingDiagonal(Board board, GameManager gameManager){
        if(gameManager.classicRuleManager.getWhoseTurn() == 'w') {
            if (gameManager.classicRuleManager.checkFromLeftdown(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1)
                leftDown = true;
            if (gameManager.classicRuleManager.checkFromLefttop(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1)
                leftTop = true;
            if (gameManager.classicRuleManager.checkFromRightdown(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1)
                rightDown = true;
            if (gameManager.classicRuleManager.checkFromRighttop(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1)
                rightTop = true;
        }
        else {
            if (gameManager.classicRuleManager.checkFromLeftdown(board.getCells()[gameManager.pieceManager.getBlackKingCellIndex()], board) != -1)
                leftDown = true;
            if (gameManager.classicRuleManager.checkFromLefttop(board.getCells()[gameManager.pieceManager.getBlackKingCellIndex()], board) != -1)
                leftTop = true;
            if (gameManager.classicRuleManager.checkFromRightdown(board.getCells()[gameManager.pieceManager.getBlackKingCellIndex()], board) != -1)
                rightDown = true;
            if (gameManager.classicRuleManager.checkFromRighttop(board.getCells()[gameManager.pieceManager.getBlackKingCellIndex()], board) != -1)
                rightTop = true;
        }
    }

    private void checkMoves(Cell cell, Board board){
        if(leftDown)
            leftDownMoves(cell, board);
        if(rightTop)
            rightTopMoves(cell, board);
        if(rightDown)
            rightDownMoves(cell, board);
        if(leftTop)
            leftTopMoves(cell, board);
    }

    private void setMoves(Cell cell, Board board){
        leftDownMoves(cell, board);
        rightTopMoves(cell, board);
        rightDownMoves(cell, board);
        leftTopMoves(cell, board);
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

    @Override
    public Bitmap getImage() {
        return bishopImage;
    }

    @Override
    public void setImage(Bitmap image) {
        this.bishopImage = image;
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
        leftTop = false;
        leftDown = false;
        rightDown = false;
        rightTop = false;
    }
}
