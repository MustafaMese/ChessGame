package com.mustafamese.chess.GameZone;

import android.graphics.Bitmap;

import com.ngdroidapp.NgApp;

public class Board {

    public final int numberOfCells = 64;
    public final int numberOfRows = 8;
    public final int numberOfColumns = 8;

    private Bitmap boardImage;

    private int counter1 = 0;
    private int counter2 = 0;

    private Cell cells[] = new Cell[numberOfCells];
    public int position[] = new int[2];

    public Board(int startPosX, int startPosY, Bitmap image){
        this.position[0] = startPosX;
        this.position[1] = startPosY;
        this.boardImage = image;
    }

    public int getCellIndex(int posX, int posY){
        for(counter1 = 0; counter1 < numberOfCells; counter1++){
            if(cells[counter1].getBounds().intersects(posX, posY, posX, posY)){
                return cells[counter1].getIndex();
            }
        }
        return -1;
    }

    public Bitmap getBoardImage(){
        return boardImage;
    }

    public void setBoardImage(Bitmap image){
        this.boardImage = image;
    }

    public int[] getPosition(){
        return position;
    }

    public void setPosition(int posX, int posY){
        position[0] = posX;
        position[1] = posY;
    }

    // . means empty
    // w means white
    // b means black

    public Cell[] putCells(int boardStartPosX, int boardstartPosY, int boardWidth, int boardHeight, NgApp root){
        int index = 0;
        for(counter1 = 0; counter1 < numberOfRows; counter1++){
            for(counter2 = 0; counter2 < numberOfColumns; counter2++){
                Cell cell = new Cell(boardStartPosX + (boardWidth/numberOfRows) * counter2, boardstartPosY + (boardHeight/numberOfColumns) * counter1
                        , boardWidth/numberOfRows, boardHeight/numberOfColumns, index, '.', root);
                cells[index] = cell;
                index++;
            }
        }
        return cells;
    }

    public Cell[] getCells() {
        return cells;
    }

    public void setCells(Cell[] cells) {
        this.cells = cells;
    }
}
