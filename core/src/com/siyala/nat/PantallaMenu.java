package com.siyala.nat;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
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
    private Texture texturaBotonSalir;

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

        //Boton Play Surv
        TextureRegionDrawable trdBtnPlaySurv = new TextureRegionDrawable(new TextureRegion(texturaBotonJuegoHist));
        ImageButton btnPlaySurv = new ImageButton(trdBtnPlaySurv);
        btnPlaySurv.setPosition(3*ANCHO/4,6*ALTO/16);
        escena.addActor(btnPlayHist);

        //Boton Play Creditos
        TextureRegionDrawable trdBtnCred = new TextureRegionDrawable(new TextureRegion(texturaBotonJuegoHist));
        ImageButton btnCred = new ImageButton(trdBtnCred);
        btnCred.setPosition(3*ANCHO/4,7*ALTO/16);
        escena.addActor(btnPlayHist);


    }

    private void cargarTexturas() {
        texturaFondo = new Texture("FondoMenuInicio.png");
        texturaBotonCreditos = new Texture("");
        texturaBotonJuegoHist = new Texture("");
        texturaBotonJuegoSurv = new Texture("");
        texturaBotonSalir = new Texture("");
    }

    private void crearCamara() {
        camara = new OrthographicCamera(ANCHO, ALTO);
        camara.position.set(ANCHO/2,ALTO/2,0);
        camara.update();
        vista = new StretchViewport(ANCHO,ALTO,camara);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

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

    }
}
