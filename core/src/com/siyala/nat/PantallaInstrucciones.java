package com.siyala.nat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Victoria on 25/03/2017.
 */

public class PantallaInstrucciones extends Pantalla {

    private final Siyala juego;

    private Texture texturaInstrucciones1;
    private Texture texturaInstrucciones3;
    private Texture texturaInstrucciones2;
    private float countAlfa=3.9f;
    private float countDelta=3.9f;
    private float counBeta=3.9f;

    private Stage escena;


    public PantallaInstrucciones(Siyala juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        cargarTexturas();
        Gdx.input.setInputProcessor(new Procesador());

    }

    private void cargarTexturas() {
        texturaInstrucciones1 = new Texture("Instrucciones1.jpg");
        texturaInstrucciones2 = new Texture("Instrucciones2.jpg");
        texturaInstrucciones3 = new Texture("Instrucciones3.jpg");
    }


    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.begin();
        if(countAlfa>=0){
            batch.draw(texturaInstrucciones1,0,0);
            countAlfa-=delta;
        }
        else{
            if(countDelta>=0){
                batch.draw(texturaInstrucciones2,0,0);
                countDelta-=delta;
            }
            else{
                if(counBeta>=0){
                    batch.draw(texturaInstrucciones3,0,0);
                    counBeta-=delta;
                }
                else{
                    juego.setInstruccionesCheck();
                    juego.setScreen(new PantallaPlayHist(juego));
                }
            }
        }
        batch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    private class Procesador implements InputProcessor {
        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}
