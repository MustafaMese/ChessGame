package com.ngdroidapp;

        import android.graphics.Bitmap;
        import android.graphics.Canvas;
        import android.graphics.Rect;
        import android.util.Log;

        import istanbul.gamelab.ngdroid.base.BaseCanvas;
        import istanbul.gamelab.ngdroid.util.Utils;

/**
 * Created by noyan on 27.06.2016.
 * Nitra Games Ltd.
 */

public class MenuCanvas extends BaseCanvas {

    private Bitmap background;
    private Bitmap startButtonImage;
    private Bitmap soundButtonImage;
    private Bitmap settingsButtonImage;
    private Bitmap achivementButtonImage;
    private Bitmap head;

    private Rect startButton;

    public MenuCanvas(NgApp ngApp) {
        super(ngApp);
    }

    public void setup() {
        initializeVariables();
    }

    public void update() {

    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.scale(getWidth() / 1080.0f, getHeight() / 1920.0f);
        drawBackground(canvas);
        drawHead(canvas);
        drawButtons(canvas);
        canvas.restore();
    }

    private void drawStartButton(Canvas canvas){
        canvas.drawBitmap(startButtonImage, 220, 1265, null);
    }

    private void drawSoundButton(Canvas canvas){
        canvas.drawBitmap(soundButtonImage, 750, 40, null);
    }

    private void drawSettingsButton(Canvas canvas){
        canvas.drawBitmap(settingsButtonImage, 860, 40, null);
    }

    private void drawAchivementButton(Canvas canvas){
        canvas.drawBitmap(achivementButtonImage, 970, 40, null);
    }

    private void drawBackground(Canvas canvas){
        canvas.drawBitmap(background, 0, 0, null);
    }

    private void drawHead(Canvas canvas){
        canvas.drawBitmap(head, 190, 720, null);
    }

    private void drawButtons(Canvas canvas){
        drawStartButton(canvas);
        drawAchivementButton(canvas);
        drawSettingsButton(canvas);
        drawSoundButton(canvas);
    }

    public void keyPressed(int key) {
    }

    public void keyReleased(int key) {
    }

    public boolean backPressed() {
        return false;
    }

    public void touchDown(int x, int y, int id) {
        if(isStartButton(x, y)){
            ClassicChessCanvas classicChessCanvas = new ClassicChessCanvas(root);
            root.canvasManager.setCurrentCanvas(classicChessCanvas);
        }
    }

    public void touchMove(int x, int y, int id) {
    }

    public void touchUp(int x, int y, int id) {

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

    private void initializeVariables(){
        background = Utils.loadImage(root, "UI/BG.png");
        //background = Bitmap.createScaledBitmap(background, proportionX(background.getWidth()), proportionY(background.getHeight()), false);
        startButtonImage = Utils.loadImage(root, "UI/StartButton.png");
        //startButtonImage = Bitmap.createScaledBitmap(startButtonImage, proportionX(startButtonImage.getWidth()), proportionY(startButtonImage.getHeight()), false);
        soundButtonImage = Utils.loadImage(root, "UI/Volume.png");
        //soundButtonImage = Bitmap.createScaledBitmap(soundButtonImage, proportionX(soundButtonImage.getWidth()), proportionY(soundButtonImage.getHeight()), false);
        settingsButtonImage = Utils.loadImage(root, "UI/Settings.png");
        //settingsButtonImage = Bitmap.createScaledBitmap(settingsButtonImage, proportionX(settingsButtonImage.getWidth()), proportionY(settingsButtonImage.getHeight()), false);
        achivementButtonImage = Utils.loadImage(root, "UI/Achivement.png");
        //achivementButtonImage = Bitmap.createScaledBitmap(achivementButtonImage, proportionX(achivementButtonImage.getWidth()), proportionY(achivementButtonImage.getHeight()), false);
        head = Utils.loadImage(root, "UI/Head.png");
        //head = Bitmap.createScaledBitmap(head, proportionX(head.getWidth()), proportionY(head.getHeight()), false);
        startButton = new Rect(proportionX(220), proportionY(1265), proportionX(220) + proportionX(startButtonImage.getWidth()), proportionY(1265) + proportionY(startButtonImage.getHeight()));
    }

    private boolean isStartButton(int x, int y){
        if(startButton.intersects(x, y, x + 1, y + 1))
            return true;
        return false;
    }
}
