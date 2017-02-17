package com.siyala.nat;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
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
public class PantallaPlayHist implements Screen {
    private static final float ANCHO = 1280;
    private static final float ALTO = 800;
    private final Siyala siyala;

    //camara y vista
    private OrthographicCamera camara;
    private Viewport vista;

    //Texturas
    private Texture texturaFondo;
    private Texture texturaBotonSalir;
    private Texture texturaSiyalaCharac;

    //Escenas
    private Stage escena;
    private SpriteBatch batch;

    //SiyalaPersonaje
    private PersonajeSiyala siyala1;

    //Manejo de pantallas
    public PantallaPlayHist(Siyala siyala) {

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

        //Boton Salir
        TextureRegionDrawable trdBtnSalir = new TextureRegionDrawable(new TextureRegion(texturaBotonSalir));
        ImageButton btnSalir = new ImageButton(trdBtnSalir);
        btnSalir.setPosition(ANCHO-texturaBotonSalir.getWidth(),ALTO-texturaBotonSalir.getHeight());
        escena.addActor(btnSalir);

        //Evento del Boton Salir
        btnSalir.addListener(new ClickListener(){
            public void clicked(InputEvent event,float x, float y){
                siyala.setScreen(new PantallaMenu(siyala));
            }

        });
        Gdx.input.setInputProcessor(escena);

        //Crear a Siyala
        siyala1 = new PersonajeSiyala(texturaSiyalaCharac,ANCHO/4,ALTO/3);
    }

    private void cargarTexturas() {
        texturaFondo = new Texture("FondoJuego.png");
        texturaBotonSalir = new Texture("ExitBoton.png");
        texturaSiyalaCharac = new Texture("Siyala2.png");
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

        batch.begin();

        siyala1.dibujar(batch);

        batch.end();

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
        texturaBotonSalir.dispose();
        texturaFondo.dispose();

    }
}
