package mx.itesm.proyecto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
public class PantallaCreditos implements Screen {
    private static final float ANCHO = 1280;
    private static final float ALTO = 800;
    private final Siyala juego;
    private final AssetManager manager;

    //camara y vista
    private OrthographicCamera camara;
    private Viewport vista;

    //Texturas
    private Texture texturaFondo;
    private Texture texturaBotonSalir;
    private Texture texturaBotonSalir2;

    //Escenas
    private Stage escena;
    private SpriteBatch batch;
    private Music musicaFondo;

    //Manejo de pantallas
    public PantallaCreditos(Siyala juego) {
        this.juego = juego;
        manager=juego.getAssetManager();
        musicaFondo=manager.get("DarkMusic.mp3");
        mx.itesm.proyecto.Setts.cargarMusica(musicaFondo);
    }

    @Override
    public void show() {
        mx.itesm.proyecto.Setts.ponerMusica();
        crearCamara();
        cargarTexturas();
        crearObjetos();
    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escena = new Stage(vista,batch);
        Image imgFondo = new Image(texturaFondo);
        escena.addActor(imgFondo);

        //Boton Salir
        TextureRegionDrawable trdBtnSalir = new TextureRegionDrawable(new TextureRegion(texturaBotonSalir));
        TextureRegionDrawable trdBtnSalir2=new TextureRegionDrawable(new TextureRegion(texturaBotonSalir2));
        ImageButton btnSalir = new ImageButton(trdBtnSalir,trdBtnSalir2);
        btnSalir.setPosition(ANCHO-texturaBotonSalir.getWidth(),ALTO-texturaBotonSalir.getHeight());
        escena.addActor(btnSalir);

        //Evento del Boton Salir
        btnSalir.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                juego.setScreen(new PantallaCarga(juego, mx.itesm.proyecto.Pantallas.MENU));
            }

        });
        Gdx.input.setInputProcessor(escena);

    }

    private void cargarTexturas() {
        texturaFondo = manager.get("PantallaCreditos.png");
        texturaBotonSalir = manager.get("Botones/BotonBack1.png");
        texturaBotonSalir2=manager.get("Botones/BotonBack2.png");

    }

    private void crearCamara() {
        camara = new OrthographicCamera(ANCHO, ALTO);
        camara.position.set(ANCHO/2,ALTO/2,0);
        camara.update();
        vista = new StretchViewport(ANCHO,ALTO,camara);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        escena.draw();
    }

    private void borrarPantalla() {
        Gdx.gl.glClearColor(0,1,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void resize(int width, int height) {
        vista.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        escena.dispose();
        manager.unload("PantallaCreditos.png");
        manager.unload("Botones/BotonBack1.png");
        manager.unload("Botones/BotonBack2.png");

        manager.unload("DarkMusic.mp3");

    }
}
