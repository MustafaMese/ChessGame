package com.mustafamese.chess.GameZone;

import android.graphics.Rect;

import com.mustafamese.chess.Pieces.Piece;
import com.ngdroidapp.NgApp;

public class Cell {

    private int posX;
    private int posY;
    private int index;
    private char whatSide;
    private Piece piece;
    private Rect bounds;
    private NgApp root;

    public Cell(int posX, int posY, int cellW, int cellH,  int index, char whatSide, NgApp root){
        this.posX = posX;
        this.posY = posY;
        this.index = index;
        this.whatSide = whatSide;
        this.root = root;
        bounds = new Rect(root.proportionX(posX), root.proportionY(posY), root.proportionX(posX + cellW), root.proportionY(posY + cellH));
    }

    public Cell(int posX, int posY, int cellW, int cellH, int index){
        this.posX = posX;
        this.posY = posY;
        this.index = index;
        bounds = new Rect(root.proportionX(posX), root.proportionY(posY), root.proportionX(posX) + cellW, root.proportionY(posY) + cellH);
    }

    public Cell pieceToThisCell(Cell fromCell){                  // Put piece to this cell using fromCell variable
        this.setPiece(fromCell.getPiece());
        this.setWhatSide(fromCell.getWhatSide());
        return this;
    }

    public Cell pieceToThisCell(Cell fromCell, char side){       // This method is overloaded version.
        this.setPiece(fromCell.getPiece());                      // This can be used at deck to board putting process.
        this.setWhatSide(side);
        return this;
    }

    public Rect getBounds(){
        return bounds;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getIndex(){
        return index;
    }

    public void setIndex(int index){
        this.index = index;
    }

    public char getWhatSide(){
        return whatSide;
    }

    public void setWhatSide(char whatSide){
        this.whatSide = whatSide;
    }

    public Piece getPiece(){ return piece; }

    public void setPiece(Piece piece){this.piece = piece;}

    public void deletePiece(){
        this.setPiece(null);
        this.setWhatSide('.');
    }
}
