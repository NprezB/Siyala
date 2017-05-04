package mx.itesm.nat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
 * Created by Dess on 03/03/2017.
 */
public class PantallaSettings implements Screen {
    private static final float ANCHO = 1280;
    private static final float ALTO = 800;

    private final Siyala juego;
    private final AssetManager manager;

    //camara y vista
    private OrthographicCamera camara;
    private Viewport vista;


    //para controlar la musica
    private static Preferences setting;
    private boolean music;
    private boolean eff;

    //Texturas
    private Texture texturaFondoSetts;
    private Texture texturaBotonMusica;
    private Texture texturaBotonSonidos;
    private Texture texturaBotonMenu;
    private Texture texturaBotonMenu2;
    private Texture texturaBotonMusica2;
    private Texture texturaBotonSonidos2;

    private Music musicaFondo;

    //Escenas
    private Stage escena;
    private SpriteBatch batch;
    public PantallaSettings(Siyala juego) {
        this.juego=juego;
        manager=juego.getAssetManager();
        musicaFondo=manager.get("DarkMusic.mp3");
        mx.itesm.nat.Setts.cargarMusica(musicaFondo);
    }

    @Override
    public void show() {
        mx.itesm.nat.Setts.ponerMusica();
        crearCamara();
        cargarTexturas();
        crearObjetos();


    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escena = new Stage(vista,batch);
        Image imgFondo = new Image(texturaFondoSetts);
        escena.addActor(imgFondo);

        //Intentando organizar botones

        //Boton Musica
        btnMusica();

        //Boton Sonidos
        btnSonidos();

        //Boton Salir
        btnVolver();
        Gdx.input.setInputProcessor(escena);
    }


    private void btnMusica(){
        TextureRegionDrawable trdBtnMusica = new TextureRegionDrawable(new TextureRegion(texturaBotonMusica));
        TextureRegionDrawable trdBtnMusica2= new TextureRegionDrawable(new TextureRegion(texturaBotonMusica2));
        ImageButton btnMusica = new ImageButton(trdBtnMusica,trdBtnMusica2);
        btnMusica.setPosition(ANCHO/2-btnMusica.getWidth()/2,4*ALTO/8);
        escena.addActor(btnMusica);

        //Evento del Boton Musica
        btnMusica.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                mx.itesm.nat.Setts.cambiarEstatusMusica();

            }
        });
    }

    private void btnSonidos(){
        TextureRegionDrawable trdBtnSonido = new TextureRegionDrawable(new TextureRegion(texturaBotonSonidos));
        TextureRegionDrawable trdBtnSonido2=new TextureRegionDrawable(new TextureRegion(texturaBotonSonidos2));
        ImageButton btnSonido = new ImageButton(trdBtnSonido);
        btnSonido.setPosition(ANCHO/2-btnSonido.getWidth()/2,ALTO/4);
        escena.addActor(btnSonido);

        //Evento del Boton Sonido
        btnSonido.addListener(new ClickListener(){
            public void clicked(InputEvent event,float x, float y){
                mx.itesm.nat.Setts.cambiarEstatusEfec();

            }

        });

    }
    private void btnVolver(){
        TextureRegionDrawable trdBtnMenu = new TextureRegionDrawable(new TextureRegion(texturaBotonMenu));
        TextureRegionDrawable trdBtnMenu2= new TextureRegionDrawable(new TextureRegion(texturaBotonMenu2));
        ImageButton btnMenu = new ImageButton(trdBtnMenu,trdBtnMenu2);
        btnMenu.setPosition(ANCHO-260,ALTO-240);
        escena.addActor(btnMenu);

        //Evento del Boton Salir
        btnMenu.addListener(new ClickListener(){
            public void clicked(InputEvent event,float x, float y){
                juego.setScreen(new PantallaCarga(juego,Pantallas.MENU));
            }

        });
    }

    private void cargarTexturas() {

        texturaFondoSetts=manager.get("PantallaSettings.png");
        texturaBotonMusica=manager.get("Botones/BotonMusic1.png");
        texturaBotonMusica2=manager.get("Botones/BotonMusic2.png");
        texturaBotonSonidos=manager.get("Botones/BotonSounds1.png");
        texturaBotonSonidos2=manager.get("Botones/BotonSounds2.png");
        texturaBotonMenu=manager.get("Botones/BotonBack1.png");
        texturaBotonMenu2=manager.get("Botones/BotonBack2.png");

    }

    private void crearCamara() {
        camara=new OrthographicCamera(ANCHO,ALTO);
        camara.position.set(ANCHO/2,ALTO/2,0);
        camara.update();
        vista=new StretchViewport(ANCHO,ALTO,camara);
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

    @Override
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

    }

    @Override
    public void dispose() {
        manager.unload("FondoMenuInicio.png");
        manager.unload("BotonsSounds.png");
        manager.unload("BotonsSounds.png");
        manager.unload("Botones/BotonBack1.png");
        manager.unload("Botones/BotonBack2.png");
        manager.unload("DarkMusic.mp3");

    }
}
