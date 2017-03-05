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

public class PantallaPausa implements Screen {
    private static final float ANCHO = 1280;
    private static final float ALTO = 800;
    private final Siyala siyala;

    //camara y vista
    private OrthographicCamera camara;
    private Viewport vista;

    //Texturas botones
    private Texture texturaFondoPausa;
    private Texture texturaBotonMenu;
    private Texture texturaBotonPlay;
    private Texture texturaBotonSetts;

    //Escenas
    private Stage escena;
    private SpriteBatch batch;

    public PantallaPausa(Siyala siyala) {
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
        Image imgFondo = new Image(texturaFondoPausa);
        escena.addActor(imgFondo);


        //Boton Continuar
        TextureRegionDrawable trdBtnPlay = new TextureRegionDrawable(new TextureRegion(texturaBotonPlay));
        ImageButton btnPlay = new ImageButton(trdBtnPlay);
        btnPlay.setPosition(ANCHO/2-btnPlay.getWidth()/2,6*ALTO/8);
        escena.addActor(btnPlay);

        //Evento del Boton
        btnPlay.addListener(new ClickListener(){
            public void clicked(InputEvent event,float x, float y){
                siyala.setScreen(new PantallaPlayHist(siyala));
            }

        });

        //Boton Menu
        TextureRegionDrawable trdBtnMenu = new TextureRegionDrawable(new TextureRegion(texturaBotonMenu));
        ImageButton btnMenu = new ImageButton(trdBtnMenu);
        btnMenu.setPosition(ANCHO/2-btnMenu.getWidth()/2,4.5f*ALTO/8);
        escena.addActor(btnMenu);

        //Evento del Boton Menu
        btnMenu.addListener(new ClickListener(){
            public void clicked(InputEvent event,float x, float y){
                siyala.setScreen(new PantallaMenu(siyala));
            }

        });

        //Boton Settings
        TextureRegionDrawable trdBtnSettings = new TextureRegionDrawable(new TextureRegion(texturaBotonSetts));
        ImageButton btnSettings = new ImageButton(trdBtnSettings);
        btnSettings.setPosition(ANCHO/2-btnSettings.getWidth()/2,3*ALTO/8);
        escena.addActor(btnSettings);

        //Evento del Boton Settings
        btnSettings.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                siyala.setScreen(new PantallaSettings(siyala));
            }
        });


        Gdx.input.setInputProcessor(escena);
    }

    private void cargarTexturas() {
        texturaFondoPausa = new Texture("FondoPausa.png");
        texturaBotonPlay = new Texture("ContinueBoton.png");
        texturaBotonSetts = new Texture("BotonSetting.png");
        texturaBotonMenu = new Texture("ExitBoton.png");
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
        texturaBotonPlay.dispose();
        texturaBotonSetts.dispose();
        texturaFondoPausa.dispose();

    }
}
