package com.mustafamese.chess.Pieces;

import android.graphics.Bitmap;

import com.mustafamese.chess.GameZone.Board;
import com.mustafamese.chess.GameZone.Cell;
import com.mustafamese.chess.Manager.GameManager;

import java.util.ArrayList;

public class Rook implements Piece {

    private Bitmap rookImage;
    private ArrayList<Integer> cellIndexes;
    private int ownCellIndex;
    private int counter = 0;
    private boolean top = false, down = false, right = false, left = false;
    private boolean isMoved = false;

    public Rook(Bitmap image){
        rookImage = image;
        ownCellIndex = 3;
    }

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
            checkKingStraight(board, gameManager);
            if(!gameManager.classicRuleManager.getCheck()){
                if(side == 'w'  && gameManager.classicRuleManager.controlCheckDiagonal(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1) {
                    cell.setPiece(this);
                    cell.setWhatSide(side);
                    gameManager.classicRuleManager.setCheck(false);
                    Reset();
                    return cellIndexes;
                }
                else if(side == 'b' && gameManager.classicRuleManager.controlCheckDiagonal(board.getCells()[gameManager.pieceManager.getBlackKingCellIndex()], board) != -1){
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

    private void checkMoves(Cell cell, Board board){
        if(top)
            topMoves(cell, board);
        if(down)
            downMoves(cell, board);
        if(left)
            leftMoves(cell, board);
        if(right)
            rightMoves(cell, board);
    }

    private void checkKingStraight(Board board, GameManager gameManager){
        if(gameManager.classicRuleManager.getWhoseTurn() == 'w') {
            if(gameManager.classicRuleManager.checkFromTop(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1)
                top = true;
            if(gameManager.classicRuleManager.checkFromDown(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1)
                down = true;
            if(gameManager.classicRuleManager.checkFromRight(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1)
                right = true;
            if(gameManager.classicRuleManager.checkFromLeft(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board) != -1)
                left = true;
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
        }
    }

    private void setMoves(Cell cell, Board board){
        topMoves(cell, board);
        downMoves(cell, board);
        rightMoves(cell, board);
        leftMoves(cell, board);
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
        return rookImage;
    }

    @Override
    public void setImage(Bitmap image) {
        this.rookImage = image;
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
    }
}
