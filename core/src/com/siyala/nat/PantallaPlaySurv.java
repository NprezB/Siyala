package com.siyala.nat;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Natanael on 15/02/2017.
 */
public class PantallaPlaySurv extends Pantalla {
    public static final int ANCHO_MAPA = 210*64;
    public static final int ALTO_MAPA = 35*32;
    private final Siyala juego;
    private float posiCamara = ANCHO/2;

    private TiledMap mapa;
    private OrthogonalTiledMapRenderer renderarMapa;
    private SpriteBatch batch;

    // Siyala
    private Personaje siyala;
    private Texture texturaSiyala;

    // Música
    private Music musicaFondo;  // Sonidos largos

    //HUDs
    private OrthographicCamera camaraHUD;
    private Viewport vistaHUD;
    private Stage escenaHUD;

    // AssetManager
    private AssetManager manager;
    private float velociCamara=192;
    private int varAcciones;

    public PantallaPlaySurv(Siyala juego) {
        this.juego = juego;
        manager = juego.getAssetManager();
    }

    @Override
    public void show() {
        //cargarRecursosSiyala();
        texturaSiyala = manager.get("siyala.png");
        siyala = new Personaje(texturaSiyala,182,14*32);
        cargarMapa();

        Gdx.input.setInputProcessor(new PantallaPlaySurv.ProcesadorEntrada());
        Gdx.input.setCatchBackKey(true);

    }

    /*private void cargarRecursosSiyala() {
        manager.load("siyala.png", Texture.class);
        manager.load("Survival.tmx", TiledMap.class);
        manager.load("DarkMusic.mp3", Music.class);
        manager.finishLoading();
    }*/

    private void cargarMapa() {
        mapa = manager.get("Survival.tmx");
        musicaFondo = manager.get("DarkMusic.mp3");
        musicaFondo.setLooping(true);
        musicaFondo.play();

        batch = new SpriteBatch();

        renderarMapa = new OrthogonalTiledMapRenderer(mapa, batch);
        renderarMapa.setView(camara);
    }

    @Override
    public void render(float delta) {
        boolean pierde = false;
        varAcciones = siyala.actualizar(mapa,delta,velociCamara);
        if(varAcciones==0){
            pierde=false;
        }
        if(varAcciones==1){
            pierde=true;
        }
        if(varAcciones==2){
            juego.setScreen(new PantallaMenu(juego));
        }
        //actualizarCamara();
        //posiCamara+=delta*velociCamara;

        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        renderarMapa.setView(camara);
        renderarMapa.render();;

        batch.begin();
        siyala.dibujar(batch);
        actualizarCamara();
        posiCamara+=delta*velociCamara;
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            juego.setScreen(new PantallaMenu(juego));
        }
        if (pierde){
            juego.setScreen(new PantallaMenu(juego));
        }
    }

    private void actualizarCamara() {
        if(siyala.sprite.getY()<=(ALTO_MAPA-(ALTO/2)) && siyala.sprite.getY()>=ALTO/2){
            camara.position.set(posiCamara,siyala.sprite.getY(),0);
        }
        else{
            if(siyala.sprite.getY()>(ALTO_MAPA-(ALTO/2))){
                camara.position.set(posiCamara,(ALTO_MAPA-(ALTO/2)),0);
            }
            if(siyala.sprite.getY()<(ALTO)/2){
                camara.position.set(posiCamara,(ALTO)/2,0);
            }
        }
        camara.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        manager.unload("siyala.png");
        manager.unload("Survival.tmx");
        manager.unload("DarkMusic.mp3");
    }

    private class ProcesadorEntrada implements InputProcessor
    {
        private Vector3 v = new Vector3();
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
            v.set(screenX,screenY,0);
            camara.unproject(v);
            if(!siyala.getDoubleJump()) {
                if (siyala.getEstadoMovimiento() == Personaje.EstadoMovimiento.MOV_DERECHA && v.x > posiCamara) {
                    siyala.setEstadoMovimiento(Personaje.EstadoMovimiento.SUBIENDO);
                }
                //siyala.getEstadoMovimiento() == Personaje.EstadoMovimiento.MOV_DERECHA &&
                if (v.x <= posiCamara && siyala.getEstadoMovimiento()!= Personaje.EstadoMovimiento.DESAPARECIDO) {
                    siyala.setXDesaparecido();
                    siyala.setEstadoMovimiento(Personaje.EstadoMovimiento.DESAPARECIDO);
                }
            }
            if(siyala.getDoubleJump()){
                if(siyala.getNumJump()<=2){
                    siyala.setY();
                    siyala.setEstadoMovimiento(Personaje.EstadoMovimiento.SUBIENDO);
                    siyala.setOneNumJump();
                }
            }
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            //if(siyala.getEstadoMovimiento() == Personaje.EstadoMovimiento.DESAPARECIDO){
            //    siyala.setEstadoMovimiento(Personaje.EstadoMovimiento.MOV_DERECHA);
            //}
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
