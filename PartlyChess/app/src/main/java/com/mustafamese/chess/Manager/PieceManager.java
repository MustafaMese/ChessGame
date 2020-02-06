package com.mustafamese.chess.Manager;

import android.util.Log;

import com.mustafamese.chess.Pieces.Piece;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class PieceManager {

    class PieceData {
        int listNumber;
        char side = 0;
        Piece type = null;
        int cellIndex = -1;
    }

    private int listNumber = 0;
    private int counter = 0;
    public ArrayList<PieceData> pieces = new ArrayList<>();

    public PieceData createPieceData(char side, Piece type, int index){
        PieceData piece = new PieceData();
        piece.cellIndex = index;
        piece.side = side;
        piece.type = type;
        piece.listNumber = listNumber;
        listNumber++;
        return piece;
    }

    public void getAllData(){
        for (counter = 0; counter < pieces.size(); counter++) {
            Log.i(TAG, pieces.get(counter).listNumber + " " + pieces.get(counter).side + " " + pieces.get(counter).type + " " + pieces.get(counter).cellIndex);
        }
    }

    public void changePieceCellIndex(int oldIndex, int newIndex){
        for(counter = 0; counter < pieces.size(); counter++){
            if(pieces.get(counter).cellIndex == oldIndex){
                pieces.get(counter).cellIndex = newIndex;
                return;
            }
        }
    }

    // For Moving Process
    public int getPieceDataListNumber(char side, int newIndex, Piece piece){
        for(counter = 0; counter < pieces.size(); counter++){
            if(pieces.get(counter).side == side && pieces.get(counter).cellIndex == newIndex && pieces.get(counter).type == piece)
                return pieces.get(counter).listNumber;
        }
        return -1;
    }

    public int getWhiteKingCellIndex(){
        for (counter = 0; counter < pieces.size(); counter++){
            if(pieces.get(counter).side == 'w' && pieces.get(counter).type.getOwnCellIndex() == 5)
                return pieces.get(counter).cellIndex;
        }
        return -1;
    }

    public int getBlackKingCellIndex(){
        for(counter = 0; counter < pieces.size(); counter++){
            if(pieces.get(counter).side == 'b' && pieces.get(counter).type.getOwnCellIndex() == 5){
                return pieces.get(counter).cellIndex;
            }
        }
        return -1;
    }

    public Piece getPieceData(int listNumber){
        for(counter = 0; counter < pieces.size(); counter++){
            if(pieces.get(counter).listNumber == listNumber)
                return pieces.get(counter).type;
        }
        return null;
    }

    public char getSideData(int listNumber){
        for(counter = 0; counter < pieces.size(); counter++){
            if(pieces.get(counter).listNumber == listNumber)
                return pieces.get(counter).side;
        }
        return '.';
    }

    public int getCellData(int listNumber){
        for(counter = 0; counter < pieces.size(); counter++){
            if(pieces.get(counter).listNumber == listNumber)
                return pieces.get(counter).cellIndex;
        }
        return -1;
    }

    public void setCellData(int listNumber, int cellIndex){
        for(counter = 0; counter < pieces.size(); counter++){
            if(pieces.get(counter).listNumber == listNumber)
                pieces.get(counter).cellIndex = cellIndex;
        }
    }
}
