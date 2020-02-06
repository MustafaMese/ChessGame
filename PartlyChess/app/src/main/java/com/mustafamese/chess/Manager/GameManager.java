package com.mustafamese.chess.Manager;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.mustafamese.chess.GameZone.Board;
import com.mustafamese.chess.GameZone.Cell;
import com.mustafamese.chess.GameZone.Deck;
import com.mustafamese.chess.Pieces.Piece;

import java.util.ArrayList;

public class GameManager {

    public State state = new State();
    public ClassicRuleManager classicRuleManager;
    public PieceManager pieceManager;
    public MoveManager moveManager;

    // Putting piece deck to board for initialize that piece
    public void putPieceToCell(int x, int y, int deckIndex, Board board, Deck deck){
        if(board.getCellIndex(x, y) != -1){
            if(isEmpty(board.getCells()[board.getCellIndex(x, y)]) && board.getCellIndex(x, y) > 31){
                board.getCells()[board.getCellIndex(x, y)].pieceToThisCell(deck.getCells()[deckIndex], 'w');
            }
        }
    }

    // . means empty
    // w means white
    // b means black

    public boolean isWhite(Cell cell){
        if(cell.getWhatSide() != 'w')
            return false;
        return true;
    }

    public boolean isEmpty(Cell cell){
        if(cell.getWhatSide() != '.')
            return false;
        return true;
    }

    public boolean isLegalToTakePiece(Cell cell){
        if(!isEmpty(cell))
            return true;
        return false;
    }

    public void movePiece(int choosenPieceIndex, int toCellIndex, ArrayList<Integer> allowedMoves, Board board){
        for(int i = 0; i < allowedMoves.size(); i++) {
            if(toCellIndex == allowedMoves.get(i)){
                board.getCells()[toCellIndex].pieceToThisCell(board.getCells()[choosenPieceIndex]);
                board.getCells()[choosenPieceIndex].deletePiece();
                return;
            }
        }
    }

    public Piece rokMovePiece(int choosenPieceIndex, int toCellIndex, ArrayList<Integer> allowedMoves, int distance,Board board){
        Piece rookPiece = board.getCells()[toCellIndex].getPiece();
        for(int i = 0; i < allowedMoves.size(); i++){
            if(distance == 0) {
                if (toCellIndex == allowedMoves.get(i)) {
                    board.getCells()[toCellIndex - 1].pieceToThisCell(board.getCells()[choosenPieceIndex]);
                    board.getCells()[toCellIndex - 2].pieceToThisCell(board.getCells()[toCellIndex]);
                    board.getCells()[choosenPieceIndex].deletePiece();
                    board.getCells()[toCellIndex].deletePiece();
                    board.getCells()[toCellIndex - 1].getPiece().setIsMoved(true);
                    board.getCells()[toCellIndex - 2].getPiece().setIsMoved(true);
                }
            }
            else if(distance == 1){
                if (toCellIndex == allowedMoves.get(i)) {
                    board.getCells()[toCellIndex + 2].pieceToThisCell(board.getCells()[choosenPieceIndex]);
                    board.getCells()[toCellIndex + 3].pieceToThisCell(board.getCells()[toCellIndex]);
                    board.getCells()[choosenPieceIndex].deletePiece();
                    board.getCells()[toCellIndex].deletePiece();
                    board.getCells()[toCellIndex + 2].getPiece().setIsMoved(true);
                    board.getCells()[toCellIndex + 3].getPiece().setIsMoved(true);
                }
            }
        }
        return rookPiece;
    }

    public Piece takePiece(int choosenPieceIndex, int toCellIndex, ArrayList<Integer> allowedMoves, Board board){
        Piece deletedPiece = board.getCells()[toCellIndex].getPiece();
        for(int i = 0; i < allowedMoves.size(); i++){
            if(toCellIndex == allowedMoves.get(i)){
                board.getCells()[toCellIndex].pieceToThisCell(board.getCells()[choosenPieceIndex]);
                board.getCells()[choosenPieceIndex].deletePiece();
            }
        }
        return deletedPiece;
    }

    // Using for putting piece deck to board
    public void paintAllowedCells(Canvas canvas, int index, Bitmap image, Board board){
        if(index > 31 && isEmpty(board.getCells()[index])){
            canvas.drawBitmap(image, board.getCells()[index].getPosX(), board.getCells()[index].getPosY(), null);
        }
    }

    // using for putting piece board to board or take a piece from enemy color
    public void paintAllowedCells(Canvas canvas, Bitmap moveImage, Bitmap takeImage, Board board, ArrayList<Integer> allowedCells){
        for(int i = 0; i < allowedCells.size(); i++){
            if(isLegalToTakePiece(board.getCells()[allowedCells.get(i)]))
                canvas.drawBitmap(takeImage, board.getCells()[allowedCells.get(i)].getPosX(), board.getCells()[allowedCells.get(i)].getPosY(), null);
            else
                canvas.drawBitmap(moveImage, board.getCells()[allowedCells.get(i)].getPosX(), board.getCells()[allowedCells.get(i)].getPosY(), null);
        }
    }

    public class State{
        public boolean touchedDeck = false;
        public boolean touchedBoard = false;
        public boolean pieceSelectedOnDeck = false;
        public boolean pieceSelectedOnBoard = false;
        public boolean piecePut = false;

        public void Reset(){
            touchedDeck = false;
            touchedBoard = false;
            pieceSelectedOnDeck = false;
            piecePut = false;
            pieceSelectedOnBoard = false;
        }
    }
}

