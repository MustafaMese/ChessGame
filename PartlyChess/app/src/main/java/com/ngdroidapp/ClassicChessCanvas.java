package com.ngdroidapp;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.mustafamese.chess.GameZone.Board;
import com.mustafamese.chess.GameZone.Cell;
import com.mustafamese.chess.Manager.ClassicRuleManager;
import com.mustafamese.chess.Manager.GameManager;
import com.mustafamese.chess.Manager.MoveManager;
import com.mustafamese.chess.Manager.PieceManager;
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



public class ClassicChessCanvas extends BaseCanvas {

    GameManager gameManager;
    Board board;
    enum GameState{
        SPLASHSCREEN,
        GAME,
        FINISH
    }
    GameState gameState = GameState.SPLASHSCREEN;

    private Bitmap boardImage;
    private Bitmap background;
    private Bitmap undoButtonImage;
    private Bitmap menuButtonImage;
    private Bitmap splashScreen;

    private Rect undoButton;
    private Rect menuButton;

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

    private Bitmap allowedCellRed;
    private Bitmap allowedCellPurple;

    private int chosenCellOnBoard;                  // for chosen index number of cell on board.
    private int movedCellOnBoard;                   // for moving chosen cell's piece to another cell.

    private int boardX, boardY, boardW, boardH;
    private boolean isGameFinished;
    private boolean isGamePaused;
    private char winner;
    private char ownSide, enemySide;
    private Piece movedPiece, deletedPiece, rokMovePiece;

    // White piece Array
    private Piece whitePieces[];

    // Black piece Array
    private Piece blackPieces[];

    private int counter1 = 0;
    private int counter2 = 0;

    private ArrayList<Integer> allowedCellIndexes;

    public ClassicChessCanvas(NgApp ngApp) {
        super(ngApp);
    }

    public void setup() {
        if(gameState == GameState.SPLASHSCREEN) {
            initializeVariables();
            wait(5000);
        }
    }

    public void update() {
        if (isGameFinished) {
            System.out.println("winner: " + winner);
        }
        if (!isGamePaused && gameState == GameState.SPLASHSCREEN)
            gameState = GameState.GAME;
        if (isGamePaused)
            return;
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.scale(getWidth() / 1080.0f, getHeight() / 1920.0f);

        switch (gameState){
            case SPLASHSCREEN:
                drawSplashScreen(canvas);
                break;
            case GAME:
                drawBackground(canvas);
                drawBoard(canvas);
                drawButtons(canvas);
                if (gameManager.state.pieceSelectedOnBoard)
                    paintCellsAllowedMoves(canvas);
                drawPieces(canvas);
                break;
            case FINISH:
                break;
        }
        canvas.restore();
    }

    private void paintCellsAllowedMoves(Canvas canvas) {
        for (counter1 = 0; counter1 < board.numberOfCells; counter1++) {
            gameManager.paintAllowedCells(canvas, allowedCellRed, allowedCellPurple, board, allowedCellIndexes);
        }
    }

    private void drawPieces(Canvas canvas) {
        for (counter1 = 0; counter1 < board.numberOfCells; counter1++) {
            if (board.getCells()[counter1].getPiece() != null)
                canvas.drawBitmap(board.getCells()[counter1].getPiece().getImage(), board.getCells()[counter1].getPosX(), board.getCells()[counter1].getPosY(), null);
        }
    }

    private void drawBoard(Canvas canvas) {
        canvas.drawBitmap(board.getBoardImage(), 0, board.getPosition()[1], null);
    }

    private void drawButtons(Canvas canvas){
        undoButton(canvas);
        menuButton(canvas);
    }

    private void undoButton(Canvas canvas){
        canvas.drawBitmap(undoButtonImage, proportionX(110), proportionY(10), null);
    }

    private void menuButton(Canvas canvas) {
        canvas.drawBitmap(menuButtonImage, proportionX(10), proportionY(10), null);
    }

    private void drawBackground(Canvas canvas){
        canvas.drawBitmap(background, 0, 0, null);
    }

    private void drawSplashScreen(Canvas canvas){
        canvas.drawBitmap(splashScreen, 0, 0, null);
    }

    public void keyPressed(int key) {

    }

    public void keyReleased(int key) {

    }

    public boolean backPressed() {
        return false;
    }

    public void touchDown(int x, int y, int id) {
        if(!isGamePaused){
            if(isUndoButton(x, y)) {
                doUndo();
                wait(250);
                return;
            }
            if(isMenuButton(x, y)){
                MenuCanvas menuCanvas = new MenuCanvas(root);
                root.canvasManager.setCurrentCanvas(menuCanvas);
            }
            // When we touch board
            chosenCellOnBoard = board.getCellIndex(x, y);
            if (chosenCellOnBoard != -1) {
                if (board.getCells()[chosenCellOnBoard].getWhatSide() == gameManager.classicRuleManager.getWhoseTurn()) {
                    // if we want to look allowed moves of a piece
                    if (board.getCells()[chosenCellOnBoard].getPiece() != null) {
                        allowedCellIndexes = board.getCells()[chosenCellOnBoard].getPiece().allowedMoves(board.getCells()[chosenCellOnBoard], board, gameManager);
                        gameManager.state.pieceSelectedOnBoard = true;
                    } else {
                        gameManager.state.pieceSelectedOnBoard = false;
                    }
                    gameManager.state.touchedBoard = true;
                }
            } else {
                gameManager.state.Reset();
            }
        }
    }

    public void touchMove(int x, int y, int id) {

    }

    public void touchUp(int x, int y, int id) {
        if(!isGamePaused){
            if(board == null)
                return;
            movedCellOnBoard = board.getCellIndex(x, y);
            if(chosenCellOnBoard != -1)
                movedPiece = board.getCells()[chosenCellOnBoard].getPiece();
            if (board.getCellIndex(x, y) != -1) {
                if (gameManager.state.touchedBoard && gameManager.state.pieceSelectedOnBoard) {
                    if(movedPiece.getOwnCellIndex() == 5 && gameManager.classicRuleManager.getRokMove() != -1 && board.getCells()[movedCellOnBoard].getPiece() != null){
                        rokMovePiece = gameManager.rokMovePiece(chosenCellOnBoard, movedCellOnBoard, allowedCellIndexes, gameManager.classicRuleManager.getRokDistance(),board);
                        if(rokMovePiece.getIsMoved() && gameManager.classicRuleManager.getRokDistance() == 0){
                            setSides();
                            addRokMove(movedPiece, rokMovePiece, gameManager.classicRuleManager.getRokDistance());
                            checkControl();
                            changeTurn();
                            gameManager.classicRuleManager.setRokMove(-1);
                        }
                        else if(rokMovePiece.getIsMoved() && gameManager.classicRuleManager.getRokDistance() == 1){
                            setSides();
                            addRokMove(movedPiece, rokMovePiece, gameManager.classicRuleManager.getRokDistance());
                            checkControl();
                            changeTurn();
                            gameManager.classicRuleManager.setRokMove(-1);
                        }
                    }
                    else if(board.getCells()[movedCellOnBoard].getPiece() != null) {
                        deletedPiece = gameManager.takePiece(chosenCellOnBoard, movedCellOnBoard, allowedCellIndexes, board);
                        if (board.getCells()[chosenCellOnBoard].getPiece() == null) {
                            setSides();
                            addDeletionMove(movedPiece, deletedPiece);
                            checkControl();
                            changeTurn();
                            if(movedPiece.getOwnCellIndex() == 3 || movedPiece.getOwnCellIndex() == 5)
                                movedPiece.setIsMoved(true);
                        }
                    }
                    else{
                        gameManager.movePiece(chosenCellOnBoard, movedCellOnBoard, allowedCellIndexes, board);
                        if (board.getCells()[chosenCellOnBoard].getPiece() == null) {
                            setSides();
                            addMove(movedPiece);
                            checkControl();
                            changeTurn();
                            if(movedPiece.getOwnCellIndex() == 3 || movedPiece.getOwnCellIndex() == 5)
                                movedPiece.setIsMoved(true);
                        }
                    }
                    gameManager.state.pieceSelectedOnBoard = false;
                    gameManager.state.touchedBoard = false;
                }
            }
            else {
                gameManager.state.Reset();
            }
            checkKings();
            //gameManager.pieceManager.getAllData();
            gameManager.moveManager.getAllMoves();
        }
    }

    private void changeTurn() {
        if (ownSide == 'b')
            gameManager.classicRuleManager.setWhoseTurn('w');
        else if (ownSide == 'w')
            gameManager.classicRuleManager.setWhoseTurn('b');
    }

    private void checkKings(){
        if(gameManager.pieceManager.getBlackKingCellIndex() == -1){
            isGameFinished = true;
            winner = 'w';
        }
        else if(gameManager.pieceManager.getWhiteKingCellIndex() == -1){
            isGameFinished = true;
            winner = 'b';
        }
    }

    private void setSides(){
        if(gameManager.classicRuleManager.getWhoseTurn() == 'w'){
            ownSide = 'w';
            enemySide = 'b';
        }
        else{
            ownSide = 'b';
            enemySide = 'w';
        }
    }

    private void addMove(Piece movedPiece){
        gameManager.pieceManager.changePieceCellIndex(chosenCellOnBoard, movedCellOnBoard);
        gameManager.moveManager.addMove(chosenCellOnBoard, movedCellOnBoard,gameManager.pieceManager.getPieceDataListNumber(ownSide, movedCellOnBoard, movedPiece), false);
    }

    private void addRokMove(Piece movedPiece, Piece rokPiece, int rokDistance){
        if(rokDistance == 0) {
            gameManager.pieceManager.changePieceCellIndex(movedCellOnBoard, board.getCells()[movedCellOnBoard - 2].getIndex());
            gameManager.moveManager.addMove(movedCellOnBoard, board.getCells()[movedCellOnBoard - 2].getIndex(), gameManager.pieceManager.getPieceDataListNumber(ownSide, board.getCells()[movedCellOnBoard - 2].getIndex(), rokPiece), true);
            gameManager.pieceManager.changePieceCellIndex(chosenCellOnBoard, board.getCells()[movedCellOnBoard - 1].getIndex());
            gameManager.moveManager.addMove(chosenCellOnBoard, board.getCells()[movedCellOnBoard - 1].getIndex(), gameManager.pieceManager.getPieceDataListNumber(ownSide, board.getCells()[movedCellOnBoard - 1].getIndex(), movedPiece), true);
        }
        else if(rokDistance == 1){
            gameManager.pieceManager.changePieceCellIndex(movedCellOnBoard, board.getCells()[movedCellOnBoard + 3].getIndex());
            gameManager.moveManager.addMove(movedCellOnBoard, board.getCells()[movedCellOnBoard + 3].getIndex(), gameManager.pieceManager.getPieceDataListNumber(ownSide, board.getCells()[movedCellOnBoard + 3].getIndex(), rokPiece), true);
            gameManager.pieceManager.changePieceCellIndex(chosenCellOnBoard, board.getCells()[movedCellOnBoard + 2].getIndex());
            gameManager.moveManager.addMove(chosenCellOnBoard, board.getCells()[movedCellOnBoard + 2].getIndex(), gameManager.pieceManager.getPieceDataListNumber(ownSide, board.getCells()[movedCellOnBoard + 2].getIndex(), movedPiece), true);
        }
    }

    private void addDeletionMove(Piece movedPiece, Piece deletedPiece){
        gameManager.pieceManager.changePieceCellIndex(movedCellOnBoard, -1);
        gameManager.moveManager.addMove(movedCellOnBoard, -1, gameManager.pieceManager.getPieceDataListNumber(enemySide, -1, deletedPiece), false);
        gameManager.pieceManager.changePieceCellIndex(chosenCellOnBoard, movedCellOnBoard);
        gameManager.moveManager.addMove(chosenCellOnBoard, movedCellOnBoard,gameManager.pieceManager.getPieceDataListNumber(ownSide, movedCellOnBoard, movedPiece), false);
    }

    private void checkControl(){
        if(gameManager.classicRuleManager.getWhoseTurn() == 'b' && gameManager.pieceManager.getWhiteKingCellIndex() != -1)
            gameManager.classicRuleManager.controlCheck(board.getCells()[gameManager.pieceManager.getWhiteKingCellIndex()], board);
        else if(gameManager.classicRuleManager.getWhoseTurn() == 'w' && gameManager.pieceManager.getBlackKingCellIndex() != -1)
            gameManager.classicRuleManager.controlCheck(board.getCells()[gameManager.pieceManager.getBlackKingCellIndex()], board);
    }

    public void surfaceChanged(int width, int height) {

    }

    public void surfaceCreated() {

    }

    public void surfaceDestroyed() {

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

    public void initializeVariables() {
        initializeSplashScreen();
        initializeManagers();
        createWhitePieces();
        createBlackPieces();
        initializeBackground();
        initializeCells();
        initializeBoard();
        initializeButtons();
        initializeBlackPieces();
        initializeWhitePieces();
        chosenCellOnBoard = 0;
        movedCellOnBoard = 0;
        isGameFinished = false;
        isGamePaused = false;
        winner = ' ';
        enemySide = ' ';
        ownSide = ' ';
        movedPiece = null;
        deletedPiece = null;
        rokMovePiece = null;
    }

    private void initializeSplashScreen(){
        splashScreen = Utils.loadImage(root, "UI/SplashScreen.png");
    }

    private void initializeBackground(){
        background = Utils.loadImage(root, "UI/BG.png");
    }

    private void createWhitePieces(){
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
    }

    private void createBlackPieces(){
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
    }

    private void initializeManagers(){
        gameManager = new GameManager();
        gameManager.classicRuleManager = new ClassicRuleManager(false, 'w');
        gameManager.pieceManager = new PieceManager();
        gameManager.moveManager = new MoveManager();
    }

    private void initializeCells(){
        allowedCellRed = Utils.loadImage(root, "Cells/allowedCellRed.png");
        allowedCellPurple = Utils.loadImage(root, "Cells/allowedCellPurple.png");
        allowedCellIndexes = new ArrayList<>();
    }

    private void initializeBoard(){
        boardImage = Utils.loadImage(root, "GameZone/Board.png");
        boardW = boardImage.getWidth();
        boardH = boardImage.getHeight() - 55;
        boardX = 0;
        boardY = 393;
        board = new Board(boardX, boardY, boardImage);
        for (counter1 = 0; counter1 < board.numberOfRows; counter1++) {
            for (counter2 = 0; counter2 < board.numberOfColumns; counter2++) {
                if (counter1 == 0 && counter2 % 2 == 0)
                    board.setCells(board.putCells(boardX, boardY + 28, boardW, boardH, root));
                if (counter1 == 1 && counter2 % 2 != 0)
                    board.setCells(board.putCells(boardX, boardY + 28, boardW, boardH, root));
            }
        }
    }

    private void initializeButtons(){
        menuButtonImage = Utils.loadImage(root, "UI/MenuButton.png");
        undoButtonImage = Utils.loadImage(root, "UI/UndoButton.png");

        menuButton = new Rect(proportionX(10), proportionY(10), proportionX(10) + menuButtonImage.getWidth(), proportionY(10) + menuButtonImage.getHeight());
        undoButton = new Rect(proportionX(110), proportionY(10), proportionX(110) + undoButtonImage.getWidth(), proportionY(10) + undoButtonImage.getHeight());
    }

    private void initializeBlackPieces(){
        for (counter1 = 0; counter1 < 16; counter1++) {
            if (counter1 >= 8 && counter1 <= 15) {
                board.getCells()[counter1].setPiece(blackPieces[0]);
                board.getCells()[counter1].setWhatSide('b');
                gameManager.pieceManager.pieces.add(gameManager.pieceManager.createPieceData('b', blackPieces[0], counter1));
            } else if (counter1 == 0 || counter1 == 7) {
                board.getCells()[counter1].setPiece(blackPieces[3]);
                board.getCells()[counter1].setWhatSide('b');
                gameManager.pieceManager.pieces.add(gameManager.pieceManager.createPieceData('b', blackPieces[3], counter1));
            } else if (counter1 == 1 || counter1 == 6) {
                board.getCells()[counter1].setPiece(blackPieces[1]);
                board.getCells()[counter1].setWhatSide('b');
                gameManager.pieceManager.pieces.add(gameManager.pieceManager.createPieceData('b', blackPieces[1], counter1));
            } else if (counter1 == 2 || counter1 == 5) {
                board.getCells()[counter1].setPiece(blackPieces[2]);
                board.getCells()[counter1].setWhatSide('b');
                gameManager.pieceManager.pieces.add(gameManager.pieceManager.createPieceData('b', blackPieces[2], counter1));
            } else if (counter1 == 3) {
                board.getCells()[counter1].setPiece(blackPieces[4]);
                board.getCells()[counter1].setWhatSide('b');
                gameManager.pieceManager.pieces.add(gameManager.pieceManager.createPieceData('b', blackPieces[4], counter1));
            } else {
                board.getCells()[counter1].setPiece(blackPieces[5]);
                board.getCells()[counter1].setWhatSide('b');
                gameManager.pieceManager.pieces.add(gameManager.pieceManager.createPieceData('b', blackPieces[5], counter1));
            }
        }
    }

    private void initializeWhitePieces(){
        for (counter1 = 48; counter1 < 64; counter1++) {
            if (counter1 >= 48 && counter1 <= 55) {
                board.getCells()[counter1].setPiece(whitePieces[0]);
                board.getCells()[counter1].setWhatSide('w');
                gameManager.pieceManager.pieces.add(gameManager.pieceManager.createPieceData('w', whitePieces[0], counter1));
            } else if (counter1 == 56 || counter1 == 63) {
                board.getCells()[counter1].setPiece(whitePieces[3]);
                board.getCells()[counter1].setWhatSide('w');
                gameManager.pieceManager.pieces.add(gameManager.pieceManager.createPieceData('w', whitePieces[3], counter1));
            } else if (counter1 == 57 || counter1 == 62) {
                board.getCells()[counter1].setPiece(whitePieces[1]);
                board.getCells()[counter1].setWhatSide('w');
                gameManager.pieceManager.pieces.add(gameManager.pieceManager.createPieceData('w', whitePieces[1], counter1));
            } else if (counter1 == 58 || counter1 == 61) {
                board.getCells()[counter1].setPiece(whitePieces[2]);
                board.getCells()[counter1].setWhatSide('w');
                gameManager.pieceManager.pieces.add(gameManager.pieceManager.createPieceData('w', whitePieces[2], counter1));
            } else if (counter1 == 59) {
                board.getCells()[counter1].setPiece(whitePieces[4]);
                board.getCells()[counter1].setWhatSide('w');
                gameManager.pieceManager.pieces.add(gameManager.pieceManager.createPieceData('w', whitePieces[4], counter1));
            } else {
                board.getCells()[counter1].setPiece(whitePieces[5]);
                board.getCells()[counter1].setWhatSide('w');
                gameManager.pieceManager.pieces.add(gameManager.pieceManager.createPieceData('w', whitePieces[5], counter1));
            }
        }
    }

    private boolean isUndoButton(int x, int y){
        return undoButton.intersects(x, y, x, y);
    }

    private boolean isMenuButton(int x, int y){
        return menuButton.intersects(x, y, x, y);
    }

    private void doUndo(){
        gameManager.moveManager.undo(board, gameManager);
    }

    private void wait(final int miliSecond){
        isGamePaused = true;
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(miliSecond);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    isGamePaused = false;
                }
            }
        };
        timerThread.start();
    }
}
