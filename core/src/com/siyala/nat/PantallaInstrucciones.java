package com.siyala.nat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Victoria on 25/03/2017.
 */

public class PantallaInstrucciones extends Pantalla {

    private final Siyala juego;
    private int countPantalla;

    private static final float ANCHO = 1280;
    private static final float ALTO = 800;

    private Texture texturaInstrucciones1;
    private Texture texturaInstrucciones3;
    private Texture texturaInstrucciones2;

    private OrthographicCamera camara;
    private Viewport vista;

    //Escenas
    private Stage escena;
    private SpriteBatch batch;

    public PantallaInstrucciones(Siyala juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        crearCamara();
        cargarTexturas();
        batch = new SpriteBatch();
        escena = new Stage(vista,batch);
        Image imgFondo = new Image(texturaInstrucciones1);
        escena.addActor(imgFondo);
        Gdx.input.setInputProcessor(new Procesador());
        countPantalla=0;

    }

    private void cargarTexturas() {
        texturaInstrucciones1 = new Texture("Instrucciones1.jpg");
        texturaInstrucciones2 = new Texture("Instrucciones2.jpg");
        texturaInstrucciones3 = new Texture("Instrucciones3.jpg");
    }


    @Override
    public void render(float delta) {
        borrarPantalla();
        escena.draw();
    }

    private void crearCamara() {
        camara = new OrthographicCamera(ANCHO, ALTO);
        camara.position.set(ANCHO/2,ALTO/2,0);
        camara.update();
        vista = new StretchViewport(ANCHO,ALTO,camara);
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
            if(countPantalla==2){
                juego.setScreen(new PantallaCarga(juego,Pantallas.MENU));
            }
            if(countPantalla==1){
                Image imgFondo = new Image(texturaInstrucciones3);
                escena.addActor(imgFondo);
                countPantalla+=1;
            }
            if(countPantalla==0){
                Image imgFondo = new Image(texturaInstrucciones2);
                escena.addActor(imgFondo);
                countPantalla+=1;
            }
            return true;
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
