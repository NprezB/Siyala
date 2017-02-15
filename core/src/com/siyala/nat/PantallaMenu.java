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
 * Created by Natanael on 10/02/2017.
 */
public class PantallaMenu implements Screen {

    private static final float ANCHO = 1280;
    private static final float ALTO = 800;
    private final Siyala siyala;

    //camara y vista
    private OrthographicCamera camara;
    private Viewport vista;

    //Texturas
    private Texture texturaFondo;
    private Texture texturaBotonJuegoHist;
    private Texture texturaBotonJuegoSurv;
    private Texture texturaBotonCreditos;

    //Escenas
    private Stage escena;
    private SpriteBatch batch;

    //Manejo de pantallas
    public PantallaMenu(Siyala siyala) {
        this.siyala = siyala;
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
        Image imgFondo = new Image(texturaFondo);
        escena.addActor(imgFondo);

        //Boton Play Hist
        TextureRegionDrawable trdBtnPlayHist = new TextureRegionDrawable(new TextureRegion(texturaBotonJuegoHist));
        ImageButton btnPlayHist = new ImageButton(trdBtnPlayHist);
        btnPlayHist.setPosition(3*ANCHO/4,5*ALTO/16);
        escena.addActor(btnPlayHist);

        //Evento del Boton Play Hist
        btnPlayHist.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("Clicked","Me hicieron click");
                siyala.setScreen(new PantallaPlayHist(siyala));
            }
        });

        //Boton Play Surv
        TextureRegionDrawable trdBtnPlaySurv = new TextureRegionDrawable(new TextureRegion(texturaBotonJuegoSurv));
        ImageButton btnPlaySurv = new ImageButton(trdBtnPlaySurv);
        btnPlaySurv.setPosition(3*ANCHO/4,6*ALTO/16);
        escena.addActor(btnPlaySurv);

        //Evento del Boton Play Surv
        btnPlaySurv.addListener(new ClickListener(){
            public void clicked(InputEvent event,float x, float y){
                Gdx.app.log("Clicked","Me hicieron click");
                siyala.setScreen(new PantallaPlaySurv(siyala));
            }

        });

        //Boton Creditos
        TextureRegionDrawable trdBtnCred = new TextureRegionDrawable(new TextureRegion(texturaBotonCreditos));
        ImageButton btnCred = new ImageButton(trdBtnCred);
        btnCred.setPosition(3*ANCHO/4,7*ALTO/16);
        escena.addActor(btnCred);

        //Evento del Boton Creditos
        btnCred.addListener(new ClickListener(){
           public void clicked(InputEvent event,float x, float y){
               Gdx.app.log("Clicked","Me hicieron click");
               siyala.setScreen(new PantallaCreditos(siyala));
           }

        });

    }

    private void cargarTexturas() {
        texturaFondo = new Texture("FondoMenuInicio.png");
        texturaBotonCreditos = new Texture("BotonCreditos.png");
        texturaBotonJuegoHist = new Texture("BotonJuegoHistoria.png");
        texturaBotonJuegoSurv = new Texture("BotonJuegoSurvival.png");
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
        dispose();
    }

    @Override
    public void dispose() {
        escena.dispose();
        texturaBotonCreditos.dispose();
        texturaBotonJuegoSurv.dispose();
        texturaBotonJuegoHist.dispose();
        texturaFondo.dispose();
    }
}
