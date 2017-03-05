package com.siyala.nat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
    private final Siyala siyala;

    //camara y vista
    private OrthographicCamera camara;
    private Viewport vista;

    //Texturas
    private Texture texturaFondoSetts;
    private Texture texturaBotonMusica;
    private Texture texturaBotonSonidos;
    private Texture texturaBotonMenu;
    private Texture texturaBotonMusicaMutted;
    private Texture texturaBotonSonidosMutted;

    //Escenas
    private Stage escena;
    private SpriteBatch batch;
    public PantallaSettings(Siyala siyala) {
        this.siyala=siyala;

    }

    @Override
    public void show() {
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
        ImageButton btnMusica = new ImageButton(trdBtnMusica);
        btnMusica.setPosition(2*ANCHO/3,4.5f*ALTO/8);
        escena.addActor(btnMusica);

        //Evento del Boton Musica
        btnMusica.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){



            }
        });
    }

    private void btnSonidos(){
        TextureRegionDrawable trdBtnSonido = new TextureRegionDrawable(new TextureRegion(texturaBotonSonidos));
        ImageButton btnSonido = new ImageButton(trdBtnSonido);
        btnSonido.setPosition(2*ANCHO/3
                ,6*ALTO/8);
        escena.addActor(btnSonido);

        //Evento del Boton Sonido
        btnSonido.addListener(new ClickListener(){
            public void clicked(InputEvent event,float x, float y){

            }

        });

    }
    private void btnVolver(){
        TextureRegionDrawable trdBtnMenu = new TextureRegionDrawable(new TextureRegion(texturaBotonMenu));
        ImageButton btnMenu = new ImageButton(trdBtnMenu);
        btnMenu.setPosition(ANCHO-texturaBotonMenu.getWidth(),ALTO-texturaBotonMenu.getHeight());
        escena.addActor(btnMenu);

        //Evento del Boton Salir
        btnMenu.addListener(new ClickListener(){
            public void clicked(InputEvent event,float x, float y){
                siyala.setScreen(new PantallaMenu(siyala));
            }

        });
    }

    private void cargarTexturas() {
        texturaFondoSetts=new Texture("FondoMenuInicio.png");
        texturaBotonMusica=new Texture("BotonsSounds.png");
        texturaBotonSonidos=new Texture("BotonsSounds.png");
        texturaBotonMenu=new Texture("ExitBoton.png");
        // texturaBotonMusicaMutted=new Texture("mutted.png");
        //texturaBotonSonidosMutted=new Texture("mutted.png");
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
        escena.dispose();
        texturaBotonMenu.dispose();
        texturaBotonMusica.dispose();
        //texturaBotonMusicaMutted.dispose();
        texturaBotonSonidos.dispose();
        //texturaBotonSonidosMutted.dispose();
        texturaFondoSetts.dispose();

    }
}
