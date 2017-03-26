package com.siyala.nat;

import com.badlogic.gdx.Gdx;
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

/**
 * Created by Dess on 26/03/2017.
 */

public class PantallaFin extends Pantalla {
    //score
    private int score=0;
    private final Siyala juego;

    private Texto texto=new Texto("fuente.fnt");

    //texturas
    private Texture texturaFondo;
    private Texture texturaBotJugar;
    private Texture texturaBotMenu;

    //Escenas
    private Stage escena;
    private SpriteBatch batch;

    public PantallaFin(Siyala juego,int score){
        this.juego=juego;
        this.score=score;

    }
    @Override
    public void show() {
        crearCamara();
        cargarTexturas();
        crearObjetos();
        Gdx.input.setCatchBackKey(true);

    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escena = new Stage(vista,batch);
        Image imgFondo = new Image(texturaFondo);
        escena.addActor(imgFondo);

        //Jugar de nuevo
        TextureRegionDrawable trdBtnPlayHist = new TextureRegionDrawable(new TextureRegion(texturaBotJugar));
        ImageButton btnPlayHist = new ImageButton(trdBtnPlayHist);
        btnPlayHist.setPosition(ANCHO/4-texturaBotMenu.getWidth(),ALTO/2);
        escena.addActor(btnPlayHist);

        //Evento del Boton
        btnPlayHist.addListener(new ClickListener(){
            public void clicked(InputEvent event,float x,float y){
                juego.setScreen(new PantallaPlayHist(juego));
            }
        });


        //Menu
        TextureRegionDrawable trdBtnMenu = new TextureRegionDrawable(new TextureRegion(texturaBotMenu));
        ImageButton btnMenu = new ImageButton(trdBtnMenu);
        btnMenu.setPosition(3*ANCHO/4,3*ALTO/2);
        escena.addActor(btnMenu);

        //Evento del Boton Creditos
        btnPlayHist.addListener(new ClickListener(){
            public void clicked(InputEvent event,float x,float y){
                juego.setScreen(new PantallaMenu(juego));
            }
        });
        /*batch.begin();
        texto.mostrarMensaje(batch,"SCORE: " + score,ANCHO/2,ALTO/8);
        batch.end();*/
    }


    private void cargarTexturas() {
        texturaFondo=new Texture("GameOver.png");
        texturaBotJugar=new Texture("BotonPlayMejorado.png");
        texturaBotMenu=new Texture("ExitBoton.png");

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
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
