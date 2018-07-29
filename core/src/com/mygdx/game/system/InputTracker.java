package com.mygdx.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ruben on 05/03/2017.
 */
public final class InputTracker extends InputAdapter {
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int SPACE = 4;
    public static final int MAX = 5;

    private static boolean[] keyPresses;
    private static boolean[] prevKeyPresses;

    private static Vector2 mousePos;

    public InputTracker() {
        keyPresses = new boolean[InputTracker.MAX];
        prevKeyPresses = new boolean[InputTracker.MAX];
        mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                keyPresses[LEFT] = true;
                break;
            case Input.Keys.D:
                keyPresses[RIGHT] = true;
                break;
            case Input.Keys.W:
                keyPresses[UP] = true;
                break;
            case Input.Keys.S:
                keyPresses[DOWN] = true;
                break;
            case Input.Keys.SPACE:
                keyPresses[SPACE] = true;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                keyPresses[LEFT] = false;
                break;
            case Input.Keys.D:
                keyPresses[RIGHT] = false;
                break;
            case Input.Keys.W:
                keyPresses[UP] = false;
                break;
            case Input.Keys.S:
                keyPresses[DOWN] = false;
                break;
            case Input.Keys.SPACE:
                keyPresses[SPACE] = false;
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        mousePos.x = x;
        mousePos.y = y;
        return true;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        mousePos.x = x;
        mousePos.y = y;
        return true;
    }

    public static boolean isNotPressed(int key) {
        return !keyPresses[key];
    }

    public static boolean isJustReleased(int key) {
        return (!keyPresses[key] && prevKeyPresses[key]);
    }

    public static boolean isPressed(int key) {
        return keyPresses[key];
    }

    public static boolean isJustPress(int key) {
        return (keyPresses[key] && !prevKeyPresses[key]);
    }

    public static Vector2 getMousePos() {
        return mousePos;
    }

    public static void updateState() {
        for (int i = 0; i < MAX; i++) {
            prevKeyPresses[i] = keyPresses[i];
        }
    }
}
