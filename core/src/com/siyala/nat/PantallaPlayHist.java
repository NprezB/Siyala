package com.siyala.nat;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Natanael on 15/02/2017.
 */

public class PantallaPlayHist extends Pantalla {
    public static final int ANCHO_MAPA = 2560;
    public static final int ALTO_MAPA = 800;
    private final Siyala juego;
    private float posiCamara = ANCHO/2;

    private TiledMap mapa;
    private OrthogonalTiledMapRenderer renderarMapa;
    private SpriteBatch batch;

    // Mario
    private Personaje siyala;
    private Texture texturaSiyala;

    // Música
    private Music musicaFondo;  // Sonidos largos
    private Sound efectoMoneda; // Sonido cortos

    // Joystick
    private Touchpad pad;

    // HUD
    private OrthographicCamera camaraHUD;
    private Viewport vistaHUD;
    private Stage escenaHUD;

    // AssetManager
    private AssetManager manager;
    private float velociCamara=64;

    public PantallaPlayHist(Siyala juego) {
        this.juego = juego;
        manager = juego.getAssetManager();
    }

    @Override
    public void show() {
        cargarRecursosMario();
        texturaSiyala = manager.get("siyala.png"); //new Texture("marioSprite.png");
        siyala = new Personaje(texturaSiyala,182,128);
        cargarMapa();

        Gdx.input.setInputProcessor(new ProcesadorEntrada());
        //Gdx.input.setInputProcessor(escenaHUD);
        Gdx.input.setCatchBackKey(true);
    }

    private void cargarRecursosMario() {
        manager.load("siyala.png", Texture.class);
        manager.load("Primer nivel.tmx", TiledMap.class);
        manager.finishLoading();
    }

    private void cargarMapa() {
        /*
        AssetManager manager = new AssetManager();

        manager.setLoader(TiledMap.class,
                new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("mapaMario.tmx", TiledMap.class);
        // Cargar audios
        manager.load("audio/marioBros.mp3",Music.class);
        manager.load("audio/moneda.mp3",Sound.class);
        manager.finishLoading();
        */
        mapa = manager.get("Primer nivel.tmx");
        //musicaFondo = manager.get("Algo.mp3");
        //musicaFondo.setLooping(true);
        //musicaFondo.play();

        //efectoMoneda = manager.get("moneda.mp3");
        batch = new SpriteBatch();

        renderarMapa = new OrthogonalTiledMapRenderer(mapa, batch);
        renderarMapa.setView(camara);
    }

    @Override
    public void render(float delta) {
        siyala.actualizar(mapa, delta, velociCamara);
        //if (siyala.recolectarMonedas(mapa)) {
        //    efectoMoneda.play();
        //}
        // ACTUALIZAR LA CAMARA
        actualizarCamara();
        posiCamara+=delta*velociCamara;

        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        renderarMapa.setView(camara);
        renderarMapa.render();  // DIBUJA el mapa
        float nuevaX = camara.position.x + 1;
        float nuevaY = camara.position.y;

        batch.begin();
        siyala.dibujar(batch);
        batch.end();

        // Salir?
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            juego.setScreen(new PantallaMenu(juego));
        }
    }

    private void actualizarCamara() {

        camara.position.set(posiCamara,ALTO/2,0);
        camara.update();
    }
    // Usar resize para actualizar camaHUD

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        manager.unload("siyala.png");
        manager.unload("Primer nivel.tmx");
        //manager.unload("Algo.mp3");
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
            if(siyala.getEstadoMovimiento()==Personaje.EstadoMovimiento.MOV_DERECHA) {
                siyala.setEstadoMovimiento(Personaje.EstadoMovimiento.SUBIENDO);
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
