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
 * Created by Dess on 16/02/2017.
 */

public class PantallaPausa implements Screen {
    private static final float ANCHO = 1280;
    private static final float ALTO = 800;
    private final Siyala siyala;

    //Vista y camara
    private OrthographicCamera camara;
    private Viewport vista;

    //Texturas
    private Texture texturaFondo;
    private Texture botonSalir;
    private Texture botonPlay;

    //Para escenas
    private SpriteBatch batch;
    private Stage escena;

    public PantallaPausa(Siyala siyala){
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
        Image imgFondo = new Image(texturaFondo);
        escena.addActor(imgFondo);

        TextureRegionDrawable trdBtnSalir=new TextureRegionDrawable(new TextureRegion(botonSalir));
        ImageButton btnSalir= new ImageButton(trdBtnSalir);
        btnSalir.setPosition(ANCHO/2-btnSalir.getWidth()/2,3*ALTO/4-btnSalir.getHeight()/2);
        escena.addActor(btnSalir);

        btnSalir.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("Clicked","Me hicieron click");
                siyala.setScreen(new PantallaMenu(siyala));
            }

        });

        //Falta implementar el boton play


    }

    private void cargarTexturas() {
        texturaFondo= new Texture("FondoPausa.png");
        botonSalir= new Texture("BotonSalir.png");
        botonPlay=new Texture("BotonPlay.png");
    }

    private void crearCamara() {
        camara= new OrthographicCamera(ANCHO,ALTO);
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
        botonSalir.dispose();
    }
}
