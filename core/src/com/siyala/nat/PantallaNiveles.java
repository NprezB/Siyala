package com.siyala.nat;

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
 * Created by Natanael on 10/02/2017.
 */
public class PantallaNiveles implements Screen {

    private static final float ANCHO = 1280;
    private static final float ALTO = 800;

    private final Siyala juego;
    private final AssetManager manager;

    private Music musicaFondo;

    //camara y vista
    private OrthographicCamera camara;
    private Viewport vista;

    //Texturas
    private Texture texturaFondo;
    private Texture texturaBotonNivel1;
    private Texture texturaBotonNivel2;
    private Texture texturaBotonNivel3;
    private Texture texturaBotonNivel1_2;
    private Texture texturaBotonNivel2_2;
    private Texture texturaBotonNivel3_2;

    //Escenas
    private Stage escena;
    private SpriteBatch batch;

    //Manejo de pantallas
    public PantallaNiveles(Siyala siyala) {
        this.juego = siyala;
        manager=juego.getAssetManager();
        musicaFondo=manager.get("DarkMusic.mp3");
        Setts.cargarMusica(musicaFondo);
    }

    @Override
    public void show() {

        Setts.ponerMusica();

        crearCamara();
        cargarTexturas();
        crearObjetos();
        Gdx.input.setCatchBackKey(false);
    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escena = new Stage(vista,batch);
        Image imgFondo = new Image(texturaFondo);
        escena.addActor(imgFondo);

        //Boton Nivel1
        TextureRegionDrawable trdBtnNiv1=new TextureRegionDrawable(new TextureRegion(texturaBotonNivel1));
        TextureRegionDrawable trdBtnNiv1_2=new TextureRegionDrawable(new TextureRegion(texturaBotonNivel1_2));
        ImageButton btnNiv1=new ImageButton(trdBtnNiv1,trdBtnNiv1_2);
        btnNiv1.setPosition(1*ANCHO/5-40,3.85f*ALTO/10);
        escena.addActor(btnNiv1);

        //Evento del boton Nivel1
        btnNiv1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

                juego.setScreen(new PantallaCarga(juego,Pantallas.PLAYHIST));
            }
        });


        //Boton Nivel2
        TextureRegionDrawable trdBtnNiv2=new TextureRegionDrawable(new TextureRegion(texturaBotonNivel2));
        TextureRegionDrawable trdBtnNiv2_2=new TextureRegionDrawable(new TextureRegion(texturaBotonNivel2_2));
        ImageButton btnNiv2=new ImageButton(trdBtnNiv2,trdBtnNiv2_2);
        btnNiv2.setPosition(2*ANCHO/5,3.85f*ALTO/10);
        escena.addActor(btnNiv2);

        //Evento del boton Nivel2
        btnNiv2.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

                if(Setts.cargarNiveles()>=2){
                    juego.setScreen(new PantallaCarga(juego,Pantallas.PLAYHIST2));
                }
            }
        });

        //Boton Nivel3
        TextureRegionDrawable trdBtnNiv3 = new TextureRegionDrawable(new TextureRegion(texturaBotonNivel3));
        TextureRegionDrawable trdBtnNiv3_2= new TextureRegionDrawable(new TextureRegion(texturaBotonNivel3_2));
        ImageButton btnNiv3 = new ImageButton(trdBtnNiv3,trdBtnNiv3_2);
        btnNiv3.setPosition(3*ANCHO/5+40,3.85f*ALTO/10);
        escena.addActor(btnNiv3);

        //Evento del Boton Nivel3
        btnNiv3.addListener(new ClickListener(){
            public void clicked(InputEvent event,float x,float y){
                if(Setts.cargarNiveles()>=3){
                    juego.setScreen(new PantallaCarga(juego,Pantallas.PLAYHIST3));
                }
            }
        });

        Gdx.input.setInputProcessor(escena);

    }

    private void cargarTexturas() {
        texturaFondo = manager.get("PantallaStorymode.png");
        texturaBotonNivel1 = manager.get("Botones/BotonNivel1_1.png");
        texturaBotonNivel1_2 =manager.get("Botones/BotonNivel1_2.png");
        texturaBotonNivel2 = manager.get("Botones/BotonNivel2_1.png");
        texturaBotonNivel2_2 =manager.get("Botones/BotonNivel2_2.png");
        texturaBotonNivel3 = manager.get("Botones/BotonNivel3_1.png");
        texturaBotonNivel3_2 =manager.get("Botones/BotonNivel3_2.png");
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
        manager.unload("PantallaStorymode.png");
        manager.unload("Botones/BotonNivel1_1.png");
        manager.unload("Botones/BotonNivel1_2.png");
        manager.unload("Botones/BotonNivel2_1.png");
        manager.unload("Botones/BotonNivel2_2.png");
        manager.unload("Botones/BotonNivel3_1.png");
        manager.unload("Botones/BotonNivel3_2.png");
    }
}
