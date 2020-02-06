package com.mustafamese.chess.Manager;

import android.util.Log;

import com.mustafamese.chess.GameZone.Board;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MoveManager {

    class MoveData{
        int listNumber;
        int fromCellIndex;
        int toCellIndex;
        boolean rokMove;
    }

    private MoveData lastMove = null;
    private MoveData previousMove = null;
    public ArrayList<MoveData> moveList = new ArrayList<>();

    public MoveData createMoveData(int listNumber, int fromCellIndex, int toCellIndex, boolean rokMove){
        MoveData moveData = new MoveData();
        moveData.listNumber = listNumber;
        moveData.fromCellIndex = fromCellIndex;
        moveData.toCellIndex = toCellIndex;
        moveData.rokMove = rokMove;
        return moveData;
    }

    public void addMove(int fromCellIndex, int toCellIndex, int listNumber, boolean rokMove){
        moveList.add(createMoveData(listNumber, fromCellIndex, toCellIndex, rokMove));
    }

    public void removeMove(MoveData move){
        moveList.remove(move);
    }

    public void getAllMoves(){
        for (MoveData moveData : moveList){
            Log.i(TAG, "old index: " + moveData.fromCellIndex + " listnumber: " + moveData.listNumber + " new index" + moveData.toCellIndex);
        }
    }

    public void undo(Board board, GameManager gameManager){
        if(moveList.size() - 1 != -1) {
            lastMove = moveList.get(moveList.size() - 1);
            if(moveList.size() - 2 != -1)
                previousMove = moveList.get(moveList.size() - 2);
            if(moveList.size() != 1 && lastMove.rokMove && previousMove.rokMove){
                board.getCells()[lastMove.fromCellIndex].setPiece(gameManager.pieceManager.getPieceData(lastMove.listNumber));
                board.getCells()[lastMove.fromCellIndex].setWhatSide(gameManager.pieceManager.getSideData(lastMove.listNumber));
                board.getCells()[lastMove.fromCellIndex].getPiece().setIsMoved(false);
                board.getCells()[lastMove.toCellIndex].deletePiece();
                gameManager.pieceManager.setCellData(lastMove.listNumber, lastMove.fromCellIndex);
                removeMove(lastMove);

                board.getCells()[previousMove.fromCellIndex].setPiece(gameManager.pieceManager.getPieceData(previousMove.listNumber));
                board.getCells()[previousMove.fromCellIndex].setWhatSide(gameManager.pieceManager.getSideData(previousMove.listNumber));
                board.getCells()[previousMove.fromCellIndex].getPiece().setIsMoved(false);
                board.getCells()[previousMove.toCellIndex].deletePiece();
                gameManager.pieceManager.setCellData(previousMove.listNumber, previousMove.fromCellIndex);
                removeMove(previousMove);
                gameManager.classicRuleManager.setRokMove(-1);
            }
            else if(moveList.size() == 1 || previousMove.toCellIndex != -1){
                board.getCells()[lastMove.fromCellIndex].setPiece(gameManager.pieceManager.getPieceData(lastMove.listNumber));
                board.getCells()[lastMove.fromCellIndex].setWhatSide(gameManager.pieceManager.getSideData(lastMove.listNumber));
                board.getCells()[lastMove.toCellIndex].deletePiece();
                gameManager.pieceManager.setCellData(lastMove.listNumber, lastMove.fromCellIndex);
                removeMove(lastMove);
            }
            else if(previousMove.toCellIndex == -1){
                board.getCells()[lastMove.fromCellIndex].setPiece(gameManager.pieceManager.getPieceData(lastMove.listNumber));
                board.getCells()[lastMove.fromCellIndex].setWhatSide(gameManager.pieceManager.getSideData(lastMove.listNumber));
                gameManager.pieceManager.setCellData(lastMove.listNumber, lastMove.fromCellIndex);
                removeMove(lastMove);

                board.getCells()[previousMove.fromCellIndex].setPiece(gameManager.pieceManager.getPieceData(previousMove.listNumber));
                board.getCells()[previousMove.fromCellIndex].setWhatSide(gameManager.pieceManager.getSideData(previousMove.listNumber));
                gameManager.pieceManager.setCellData(previousMove.listNumber, previousMove.fromCellIndex);
                removeMove(previousMove);
            }
            gameManager.classicRuleManager.setWhoseTurn(gameManager.pieceManager.getSideData(lastMove.listNumber));
        }
    }
}
