package com.siyala.nat;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import sun.security.util.Length;

/**
 * Created by Natanael on 15/02/2017.
 */

public class PantallaPlayHist extends Pantalla {
    public static final int ANCHO_MAPA = 124*64;
    public static final int ALTO_MAPA = 35*32;
    private final Siyala juego;
    private float posiCamara = ANCHO/2;

    private TiledMap mapa;
    private TiledMap mapaMundoOsc;
    private OrthogonalTiledMapRenderer renderMapaMundoOsc;
    private OrthogonalTiledMapRenderer renderarMapa;
    private SpriteBatch batch;

    // Siyala
    private Personaje siyala;
    private Texture texturaSiyala;

    //Cambio de mundo
    private Texture botonSwitch;
    private boolean estaenMundoVivo=false;
    private float SwitchCooldownTime=0;
    private float TiempoSwitch=0;
    private float largoBoton;
    private float altoBoton;

    //Pausa
    private Objeto botonPausa;
    private Texture texturaPausa;
    private boolean pausa;
    private Texture texturaContinuar;
    private Texture texturaMenu;
    private Objeto botonContinuar;
    private Objeto botonMenu;
    private Sprite spriteFondo;

    //procesador
    private final ProcesadorEntrada procesadorEntrada=new ProcesadorEntrada();

    // Música
    private Music musicaFondo;  // Sonidos largos

    //HUDs
    private OrthographicCamera camaraHUD;
    private Viewport vistaHUD;
    private Stage escenaHUD;

    // AssetManager
    private AssetManager manager;
    private float velociCamara=192;
    private float distRecorrida = 0;
    private Texto texto;

    public PantallaPlayHist(Siyala juego) {
        this.juego = juego;
        manager = juego.getAssetManager();
    }

    @Override
    public void show() {

        //BotonSwitch
        botonSwitch= new Texture("BotonWorld.png");

        cargarRecursosSiyala();

        //Boton Pausa
        texturaPausa=manager.get("BotonPausa.png");
        botonPausa=new Objeto(texturaPausa,camara.position.x+320,camara.position.y+texturaPausa.getHeight());

        //Boton Continuar
        texturaContinuar=manager.get("ContinueBoton.png");
        botonContinuar=new Objeto(texturaContinuar,camara.position.x, 3*(camara.position.y/2)+texturaContinuar.getHeight());

        //Boton Menu
        texturaMenu=manager.get("ExitBoton.png");
        botonMenu=new Objeto(texturaMenu,camara.position.x,camara.position.y/2);
        //Fondo pausa
        Texture texturaFondo=new Texture("FondoPausa.png");
        spriteFondo=new Sprite(texturaFondo);

        texturaSiyala=manager.get("siyala.png");
        siyala = new Personaje(texturaSiyala,182,14*32);
        cargarMapa();

        pausa=false;

        crearHUD();
       // Gdx.input.setInputProcessor(escenaHUD);

        texto = new Texto("fuente.fnt");

        Gdx.input.setInputProcessor(procesadorEntrada);
        Gdx.input.setCatchBackKey(true);

    }

    private void crearHUD() {
        // Cámara HUD
        camaraHUD = new OrthographicCamera(ANCHO, ALTO);
        camaraHUD.position.set(ANCHO / 2, ALTO / 2, 0);
        camaraHUD.update();
        vistaHUD = new StretchViewport(ANCHO, ALTO, camaraHUD);

        // HUD
        TextureRegionDrawable tr=new TextureRegionDrawable(new TextureRegion(botonSwitch));
        ImageButton btn=new ImageButton(tr);

        largoBoton=btn.getWidth();
        altoBoton=btn.getHeight();

        escenaHUD = new Stage(vistaHUD);
        escenaHUD.addActor(btn);


    }

    private void cambiarMundo() {
        estaenMundoVivo=!estaenMundoVivo;
    }

    private void cargarRecursosSiyala() {
        manager.load("Primer nivelosc.tmx",TiledMap.class);
        manager.load("siyala.png", Texture.class);
        manager.load("Primer nivel.tmx", TiledMap.class);
        manager.load("DarkMusic.mp3", Music.class);

        //cargar los recursos de la pausa
        manager.load("BotonPausa.png",Texture.class);
        manager.load("ContinueBoton.png",Texture.class);
        manager.load("ExitBoton.png",Texture.class);
        manager.load("ContinueBoton.png",Texture.class);
        manager.load("ExitBoton.png",Texture.class);
        manager.load("FondoPausa.png",Texture.class);

        manager.finishLoading();

    }

    private void cargarMapa() {
        mapaMundoOsc = manager.get("Primer nivelosc.tmx");
        mapa = manager.get("Primer nivel.tmx");
        musicaFondo = manager.get("DarkMusic.mp3");


        musicaFondo.setLooping(true);
        musicaFondo.play();

        batch = new SpriteBatch();

        renderMapaMundoOsc = new OrthogonalTiledMapRenderer(mapaMundoOsc,batch);
        renderarMapa = new OrthogonalTiledMapRenderer(mapa, batch);
        renderarMapa.setView(camara);
        renderMapaMundoOsc.setView(camara);

    }

    @Override
    public void render(float delta) {
        boolean pierde = false;
        //pierde = siyala.actualizar(mapa,delta,velociCamara);
        //actualizarCamara();
        //posiCamara+=delta*velociCamara;

        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        //renderarMapa.setView(camara);
       // renderarMapa.render();

        if(!pausa){
            actualizarValores(delta);
        }


        //Mapa dependiendo del estado
        if(!estaenMundoVivo) {
            pierde = siyala.actualizar(mapaMundoOsc,delta,velociCamara);
            renderMapaMundoOsc.setView(camara);
            renderMapaMundoOsc.render();
        }

        else if(estaenMundoVivo)
        {
            pierde = siyala.actualizar(mapa,delta,velociCamara);
            renderarMapa.setView(camara);
            renderarMapa.render();
        }

        batch.begin();
        siyala.dibujar(batch);

        if (estaenMundoVivo) {
            TiempoSwitch += Gdx.graphics.getDeltaTime();
            SwitchCooldownTime = TiempoSwitch;

            if (TiempoSwitch >= 7) {
                TiempoSwitch = 0;
                estaenMundoVivo = !estaenMundoVivo;
            }
        }

        if(!estaenMundoVivo && SwitchCooldownTime>=0)
            SwitchCooldownTime-=Gdx.graphics.getDeltaTime();

        actualizarCamara();
        int distImprimir = ((int) distRecorrida);
        botonPausa.dibujar(batch);
        if(siyala.getEstadoMovimiento()== Personaje.EstadoMovimiento.PERDIENDO){
            //texto.mostrarMensaje(batch,"SCORE: " + distImprimir,camara.position.x,camara.position.y+140);
            juego.setScreen(new PantallaFin(juego,distImprimir));
        }
        batch.end();

        if(pausa){
            batch.setProjectionMatrix(camara.combined);
            batch.begin();
            borrarPantalla();
            spriteFondo.setPosition(camara.position.x-ANCHO/2,camara.position.y-ALTO/2);
            spriteFondo.draw(batch);

            //Actualizo los valores de las posiciones de ambos botones
            botonContinuar.actualizar(camara.position.x-texturaContinuar.getWidth()/2,
                    camara.position.y+ALTO/4-texturaContinuar.getHeight());

            botonMenu.actualizar(camara.position.x-texturaMenu.getWidth()/2,
                    camara.position.y-ALTO/4-texturaMenu.getHeight()/2);
            botonMenu.dibujar(batch);

            //dibujo los botones
            botonContinuar.dibujar(batch);
            batch.end();
        }

        //Dibujar HUD
        batch.setProjectionMatrix(camaraHUD.combined);
        batch.begin();
        texto.mostrarMensaje(batch,distImprimir+" m",camaraHUD.position.x+360,camaraHUD.position.y+275);
        batch.end();
        escenaHUD.draw();




        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            juego.setScreen(new PantallaMenu(juego));
        }
        if (pierde){
            juego.setScreen(new PantallaFin(juego,distImprimir));
        }
    }

    private void actualizarCamara() {
        if(siyala.sprite.getY()<=(ALTO_MAPA-(ALTO/2)) && siyala.sprite.getY()>=ALTO/2){
            camara.position.set(posiCamara,siyala.sprite.getY(),0);
        }
        else{
            if(siyala.sprite.getY()>(ALTO_MAPA-(ALTO/2))){
                camara.position.set(posiCamara,(ALTO_MAPA-(ALTO/2)),0);
            }
            if(siyala.sprite.getY()<(ALTO)/2){
                camara.position.set(posiCamara,(ALTO)/2,0);
            }
        }
        camara.update();
    }

    //Aqui se actualizan los valores
    public void actualizarValores(float delta){
        if(siyala.getEstadoMovimiento()!=Personaje.EstadoMovimiento.PERDIENDO){
            posiCamara+=delta*velociCamara;
            distRecorrida+= delta*10;
            botonPausa.actualizar(camara.position.x+340,camara.position.y-texturaPausa.getHeight());

        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        manager.unload("siyala.png");
        manager.unload("Primer nivel.tmx");
        manager.unload("DarkMusic.mp3");
        manager.unload("Primer nivelosc.tmx");
    }

    private class ProcesadorEntrada implements InputProcessor
    {
        private Vector3 v = new Vector3();
        private Vector3 vHUD = new Vector3();

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {

            v.set(screenX,screenY,0);
            camara.unproject(v);

            vHUD.set(screenX,screenY,0);
            camaraHUD.unproject(vHUD);

            if(pausa){
                if(botonContinuar.contiene(v)){
                    pausa=false;
                    velociCamara=192;
                }
                if(botonMenu.contiene(v)){
                    juego.setScreen(new PantallaMenu(juego));
                }
            }

            //checa si pucharon la pausa
            if(botonPausa.contiene(v)){
                //La velocidad de camara se pone a 0
                velociCamara=0;
                //Se activa pausa
                pausa=true;
            }
            if (siyala.getEstadoMovimiento() != Personaje.EstadoMovimiento.PERDIENDO) {
                if (vHUD.x > largoBoton || vHUD.y > altoBoton) {
                    if (!siyala.getDoubleJump()) {
                        if (siyala.getEstadoMovimiento() == Personaje.EstadoMovimiento.MOV_DERECHA && v.x > posiCamara) {
                            siyala.setEstadoMovimiento(Personaje.EstadoMovimiento.SUBIENDO);
                        }
                        //siyala.getEstadoMovimiento() == Personaje.EstadoMovimiento.MOV_DERECHA &&
                        if (v.x <= posiCamara && siyala.getEstadoMovimiento() != Personaje.EstadoMovimiento.DESAPARECIDO) {
                            siyala.setXDesaparecido();
                            siyala.setEstadoMovimiento(Personaje.EstadoMovimiento.DESAPARECIDO);
                        }
                    }
                    if (siyala.getDoubleJump()) {
                        if (siyala.getNumJump() <= 2) {
                            siyala.setY();
                            siyala.setEstadoMovimiento(Personaje.EstadoMovimiento.SUBIENDO);
                            siyala.setOneNumJump();
                        }
                    }
                }

                if (vHUD.x <= largoBoton && vHUD.y <= altoBoton)
                    if ((!estaenMundoVivo && SwitchCooldownTime <= 0) || estaenMundoVivo)
                        cambiarMundo();

            }

            else{
                juego.setScreen(new PantallaMenu(juego));
            }
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            //if(siyala.getEstadoMovimiento() == Personaje.EstadoMovimiento.DESAPARECIDO){
            //    siyala.setEstadoMovimiento(Personaje.EstadoMovimiento.MOV_DERECHA);
            //}
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }


}
