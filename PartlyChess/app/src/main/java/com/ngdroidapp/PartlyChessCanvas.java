package com.ngdroidapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.mustafamese.chess.GameZone.Board;
import com.mustafamese.chess.GameZone.Deck;
import com.mustafamese.chess.Manager.GameManager;
import com.mustafamese.chess.Pieces.Bishop;
import com.mustafamese.chess.Pieces.King;
import com.mustafamese.chess.Pieces.Knight;
import com.mustafamese.chess.Pieces.Pawn;
import com.mustafamese.chess.Pieces.Piece;
import com.mustafamese.chess.Pieces.Queen;
import com.mustafamese.chess.Pieces.Rook;

import java.util.ArrayList;

import istanbul.gamelab.ngdroid.base.BaseCanvas;
import istanbul.gamelab.ngdroid.util.Utils;


/**
 * Created by noyan on 24.06.2016.
 * Nitra Games Ltd.
 */


public class PartlyChessCanvas extends BaseCanvas {

    Board board;
    Deck deck;
    GameManager gameManager;

    private Bitmap boardImage;
    private Bitmap deckImage;

    private Bitmap whitePawnImage;
    private Bitmap whiteKnightImage;
    private Bitmap whiteRookImage;
    private Bitmap whiteBishopImage;
    private Bitmap whiteQueenImage;
    private Bitmap whiteKingImage;

    private Bitmap blackPawnImage;
    private Bitmap blackKnightImage;
    private Bitmap blackRookImage;
    private Bitmap blackBishopImage;
    private Bitmap blackQueenImage;
    private Bitmap blackKingImage;

    private Bitmap allowedCellBlue;                 // Deck to Board
    private Bitmap allowedCellRed;                  // Allowed moves
    private Bitmap allowedCellPurple;               // Taking piece

    private int chosenCellOnBoard;                  // for chosen index number of cell on board
    private int chosenCellOnDeck;                   // for chosen index number of cell on deck

    private int boardX, boardY, boardW, boardH;
    private int deckX, deckY;
    private int deckW, deckH;

    // White piece Array
    private Piece whitePieces[];

    // Black piece Array
    private Piece blackPieces[];

    private ArrayList<Integer> allowedCellIndexes;

    // Counters
    private int counter1 = 0;
    private int counter2 = 0;

    public PartlyChessCanvas(NgApp ngApp) {
        super(ngApp);
    }

    public void setup() {
        initializeVariables();

        // Initialize White King
        board.getCells()[51].setWhatSide('w');
        board.getCells()[51].setPiece(whitePieces[5]);

        // Initialize Black King
        board.getCells()[11].setWhatSide('b');
        board.getCells()[11].setPiece(blackPieces[4]);

        // Initialize Black Pleb's
        for(counter1 = 16; counter1 < 24; counter1++){
            board.getCells()[counter1].setWhatSide('b');
            board.getCells()[counter1].setPiece(blackPieces[0]);
        }
    }

    public void update() {

    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.scale(getWidth()/768.0f, getHeight()/1280.0f);
        drawBoard(canvas);
        drawDeck(canvas);
        if(gameManager.state.pieceSelectedOnDeck)
            paintCellsDeckToBoard(canvas);
        if(gameManager.state.pieceSelectedOnBoard)
            paintCellsAllowedMoves(canvas);
        drawPieces(canvas);
        canvas.restore();
    }

    private void paintCellsDeckToBoard(Canvas canvas){
        for (counter1 = 0; counter1 < board.numberOfCells; counter1++){
            gameManager.paintAllowedCells(canvas, counter1, allowedCellBlue, board);
        }
    }

    private void paintCellsAllowedMoves(Canvas canvas){
        for (counter1 = 0; counter1 < board.numberOfCells; counter1++){
            gameManager.paintAllowedCells(canvas, allowedCellRed, allowedCellPurple, board, allowedCellIndexes);
        }
    }

    private void drawPieces(Canvas canvas){

        for(counter1 = 0; counter1 < deck.numberOfCells; counter1++){
            canvas.drawBitmap(deck.getCells()[counter1].getPiece().getImage(), deck.getCells()[counter1].getPosX(), deck.getCells()[counter1].getPosY(),null);
        }
        for(counter1 = 0; counter1 < board.numberOfCells; counter1++){
            if(board.getCells()[counter1].getPiece() != null)
                canvas.drawBitmap(board.getCells()[counter1].getPiece().getImage(), board.getCells()[counter1].getPosX(), board.getCells()[counter1].getPosY(),null);
        }
    }

    private void drawDeck(Canvas canvas){
        canvas.drawBitmap(deck.getDeckImage(), deck.getPosition()[0], deck.getPosition()[1], null);
    }

    private void drawBoard(Canvas canvas){
        canvas.drawBitmap(board.getBoardImage(), 0, board.getPosition()[1], null);
    }

    private void initializeVariables(){
        // Game Manager
        gameManager = new GameManager();

        // White Pieces
        whitePawnImage = Utils.loadImage(root, "Pieces/White/Pleb.png");
        whiteKnightImage = Utils.loadImage(root, "Pieces/White/Knight.png");
        whiteBishopImage = Utils.loadImage(root, "Pieces/White/Assassin.png");
        whiteRookImage = Utils.loadImage(root, "Pieces/White/Rook.png");
        whiteQueenImage = Utils.loadImage(root, "Pieces/White/Archmaiden.png");
        whiteKingImage = Utils.loadImage(root, "Pieces/White/King.png");
        whitePieces = new Piece[6];
        whitePieces[0] = new Pawn(whitePawnImage);
        whitePieces[1] = new Knight(whiteKnightImage);
        whitePieces[2] = new Bishop(whiteBishopImage);
        whitePieces[3] = new Rook(whiteRookImage);
        whitePieces[4] = new Queen(whiteQueenImage);
        whitePieces[5] = new King(whiteKingImage);

        // Black Pieces
        blackPawnImage = Utils.loadImage(root, "Pieces/Blue/Pleb.png");
        blackKnightImage = Utils.loadImage(root, "Pieces/Blue/Knight.png");
        blackBishopImage = Utils.loadImage(root, "Pieces/Blue/Assassin.png");
        blackRookImage = Utils.loadImage(root, "Pieces/Blue/Rook.png");
        blackQueenImage = Utils.loadImage(root, "Pieces/Blue/Archmaiden.png");
        blackKingImage = Utils.loadImage(root, "Pieces/Blue/King.png");
        blackPieces = new Piece[6];
        blackPieces[0] = new Pawn(blackPawnImage);
        blackPieces[1] = new Knight(blackKnightImage);
        blackPieces[2] = new Bishop(blackBishopImage);
        blackPieces[3] = new Rook(blackRookImage);
        blackPieces[4] = new Queen(blackQueenImage);
        blackPieces[5] = new King(blackKingImage);

        // Cell
        allowedCellBlue = Utils.loadImage(root, "Cells/allowedCellBlue.png");
        allowedCellRed = Utils.loadImage(root, "Cells/allowedCellRed.png");
        allowedCellPurple = Utils.loadImage(root, "Cells/allowedCellPurple.png");
        allowedCellIndexes = new ArrayList<>();

        // Deck
        deckImage = Utils.loadImage(root, "GameZone/Deck.png");
        deckW = deckImage.getWidth() * (getWidth()/768);
        deckH = deckImage.getHeight() * (getHeight()/1280);
        deckX = (getWidth() / 8) * 3/2;
        deckY = (getHeight() / 14) * 10;

        deck = new Deck(deckX, deckY, deckImage);
        chosenCellOnDeck = 0;

        for(counter1 = 0; counter1 < deck.numberOfRows; counter1++){
            for(counter2 = 0; counter2 < deck.numberOfColumns; counter2++){
                if(counter1 == 0 && counter2 % 2 == 0)
                    deck.setCells(deck.putCells(deckX, deckY, deckW, deckH));
                if(counter1 == 1 && counter2 % 2 != 0)
                    deck.setCells(deck.putCells(deckX, deckY, deckW, deckH));
            }
        }

        for(counter2 = 0; counter2 < deck.numberOfCells; counter2++){
            deck.putPieceToCell(whitePieces[counter2], counter2);
        }

        // Board
        boardImage = Utils.loadImage(root,"GameZone/Board.png");
        boardW = boardImage.getWidth() * (getWidth()/768);
        boardH = boardImage.getHeight() * (getHeight()/1280);
        boardX = 0;
        boardY = getHeight() / 14;
        board = new Board(boardX, boardY, boardImage);
        for(counter1 = 0; counter1 < board.numberOfRows; counter1++){
            for(counter2 = 0; counter2 < board.numberOfColumns; counter2++) {
                if (counter1 == 0 && counter2 % 2 == 0)
                    board.setCells(board.putCells(boardX, boardY, boardW, boardH, root));
                if (counter1 == 1 && counter2 % 2 != 0)
                    board.setCells(board.putCells(boardX, boardY, boardW, boardH, root));
            }
        }
        chosenCellOnBoard = 0;

    }

    public void keyPressed(int key) {

    }

    public void keyReleased(int key) {

    }

    public boolean backPressed() {
        return true;
    }

    public void surfaceChanged(int width, int height) {

    }

    public void surfaceCreated() {

    }

    public void surfaceDestroyed() {

    }

    public void touchDown(int x, int y, int id) {
        if(board.getCellIndex(x, y) != -1)
            System.out.println("index: " + board.getCells()[board.getCellIndex(x, y)].getIndex());
        // When we touch deck
        chosenCellOnDeck = deck.getCellIndex(x, y);
        if(chosenCellOnDeck != -1){
            // if we choose a piece
            gameManager.state.pieceSelectedOnDeck = true;
            gameManager.state.touchedDeck = true;
            gameManager.state.touchedBoard = false;
            gameManager.state.pieceSelectedOnBoard = false;
        }
        else{
            gameManager.state.pieceSelectedOnDeck = false;
            gameManager.state.touchedDeck = false; //--------
        }

        // When we touch board
        chosenCellOnBoard = board.getCellIndex(x, y);
        if(chosenCellOnBoard != -1){
            // if we want to look allowed moves of a piece
            if(board.getCells()[chosenCellOnBoard].getPiece() != null){
                allowedCellIndexes = board.getCells()[chosenCellOnBoard].getPiece().allowedMoves(board.getCells()[chosenCellOnBoard], board, gameManager);
                gameManager.state.pieceSelectedOnBoard = true;
            }
            else{
                gameManager.state.pieceSelectedOnBoard = false;
            }
            gameManager.state.touchedBoard = true;
            gameManager.state.touchedDeck = false;
            gameManager.state.pieceSelectedOnDeck = false;
        }
        else{
            gameManager.state.pieceSelectedOnBoard = false;
            gameManager.state.touchedBoard = false;     //---------
        }

        // When we touchdown blackhole
        if(chosenCellOnBoard == -1 && chosenCellOnDeck == -1)
            // Reset everything
            gameManager.state.Reset();
    }

    public void touchMove(int x, int y, int id) {
        System.out.println();
    }

    public void touchUp(int x, int y, int id) {
        // When we touchdown blackhole
        if(board.getCellIndex(x, y) == -1 && deck.getCellIndex(x, y) == -1)
            // Reset everything
            gameManager.state.Reset();

        if(board.getCellIndex(x, y) != -1){
            // We put a piece to board.
            if(gameManager.state.touchedDeck && gameManager.state.pieceSelectedOnDeck){
                gameManager.putPieceToCell(x, y, chosenCellOnDeck, board, deck);

                gameManager.state.pieceSelectedOnDeck = false;
                gameManager.state.touchedDeck = false;
            }

            if(gameManager.state.touchedBoard && gameManager.state.pieceSelectedOnBoard){
                gameManager.movePiece(chosenCellOnBoard, board.getCellIndex(x, y), allowedCellIndexes, board);

                gameManager.state.pieceSelectedOnBoard = false;
                gameManager.state.touchedBoard = false;
            }
        }


    }


    public void pause() {

    }


    public void resume() {

    }


    public void reloadTextures() {

    }


    public void showNotify() {
    }

    public void hideNotify() {
    }
}