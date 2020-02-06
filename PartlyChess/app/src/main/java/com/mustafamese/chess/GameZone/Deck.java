package com.mustafamese.chess.GameZone;

import android.graphics.Bitmap;

import com.mustafamese.chess.Pieces.Piece;

public class Deck {

    public final int numberOfCells = 5;
    public final int numberOfRows = 2;
    public final int numberOfColumns = 5;

    private Bitmap deckImage;
    private Cell cells[] = new Cell[numberOfCells];

    public int position[] = new int[2];

    // Counters
    private int counter1 = 0;
    private int counter2 = 0;
    private int index;

    public Deck(int startPosX, int startPosY, Bitmap image){
        this.position[0] = startPosX;
        this.position[1] = startPosY;
        this.deckImage = image;
    }

    public int getCellIndex(int posX, int posY){
        for(counter1 = 0; counter1 < numberOfCells; counter1++){
            if(cells[counter1].getBounds().intersects(posX, posY, posX, posY)){
                return cells[counter1].getIndex();
            }
        }
        return -1;
    }

    public Cell[] putCells(int deckStartPosX, int deckStartPosY, int deckWidth, int deckHeight){
        index = 0;
        for(counter1 = 0; counter1 < numberOfRows; counter1++){
            for(counter2 = 0; counter2 < numberOfColumns; counter2++){
                Cell cell;
                if((counter2 == 0 || counter2 == 2 || counter2 == 4) && counter1 == 0){
                    cell = new Cell(deckStartPosX + (deckWidth/numberOfColumns) * counter2, deckStartPosY + (deckHeight/numberOfRows) * counter1
                            , deckWidth/numberOfColumns, deckHeight/numberOfRows, index);
                    cells[index] = cell;
                }
                else if((counter2 == 1 || counter2 == 3) && counter1 == 1){
                    cell = new Cell(deckStartPosX + (deckWidth/numberOfColumns) * counter2, deckStartPosY + (deckHeight/numberOfRows) * counter1
                            , deckWidth/numberOfColumns, deckHeight/numberOfRows, index);
                    cells[index] = cell;
                }
                else {continue;}
                index++;
            }
        }
        return cells;
    }

    public void putPieceToCell(Piece piece, int index){
        this.cells[index].setPiece(piece);
    }

    public Cell[] getCells() {
        return cells;
    }

    public void setCells(Cell[] cells) {
        this.cells = cells;
    }

    public Bitmap getDeckImage() {
        return deckImage;
    }

    public void setDeckImage(Bitmap deckImage) {
        this.deckImage = deckImage;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int posX, int posY) {
        this.position[0] = posX;
        this.position[1] = posY;
    }
}
