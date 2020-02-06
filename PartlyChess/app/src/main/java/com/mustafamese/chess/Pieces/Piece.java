package com.mustafamese.chess.Pieces;

import android.graphics.Bitmap;

import com.mustafamese.chess.GameZone.Board;
import com.mustafamese.chess.GameZone.Cell;
import com.mustafamese.chess.Manager.GameManager;

import java.util.ArrayList;

public interface Piece {

    boolean isEmpty(Cell cell);
    boolean isWhite(Cell cell);
    boolean isLegalToMove(Cell cell, char ownSide);
    boolean isLegalToTakePiece(Cell cell, char ownSide);
    ArrayList<Integer> allowedMoves(Cell cell, Board board, GameManager gameManager);
    Bitmap getImage();
    void setImage(Bitmap image);
    int getOwnCellIndex();
    void setOwnCellIndex(int index);
    boolean getIsMoved();
    void setIsMoved(boolean isMoved);
}
