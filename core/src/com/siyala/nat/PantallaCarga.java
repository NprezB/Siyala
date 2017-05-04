package com.siyala.nat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * Created by Dess on 20/04/2017.
 */

public class PantallaCarga extends Pantalla {

    private static final float TIEMPO=0.5f;
    private Sprite spriteCarga;
    private float timerAnimacion=TIEMPO;

    private AssetManager manager;

    //para manejar los recursos y demas
    private Siyala juego;
    private Pantallas sigPantalla;
    private int porcent;
    private Texto texto;

    private Texture textCargando;

    public PantallaCarga(Siyala juego, Pantallas sigPant){
        this.juego=juego;
        this.sigPantalla=sigPant;
    }



    @Override
    public void show() {
        textCargando=new Texture(Gdx.files.internal("loading.png"));
        spriteCarga=new Sprite(textCargando);
        spriteCarga.setPosition(ANCHO/2-spriteCarga.getWidth()/2,ALTO/2-spriteCarga.getHeight()/2);
        cargarRecursosSigPantalla();
        texto=new Texto("fuente.fnt");

    }

    //aqui vemos que recursos hay que cargar
    private void cargarRecursosSigPantalla() {
        manager=juego.getAssetManager();
        porcent=0;
        switch (sigPantalla){
            case MENU:
                cargarRecursosMenu();
            case CREDITOS:
                cargarRecursosCreditos();
            case SETTS:
                cargarRecursosSetts();
            case INSTRUCCIONES:
                cargarRecursosInst();
            case PLAYHIST:
                cargarRecursosPlayHist1();
            case PLAYHIST2:
                cargarRecursosPlayHist2();
            case PLAYHIST3:
                cargarRecursosPlayHist3();
            case PLAYSURV:
                cargarRecursosPlaySurv();
            case NIVELES:
                cargarRecursosNiveles();
        }
    }

    //metodos para cargar recursos de cada pantalla
    private void cargarRecursosMenu() {
        manager.load("FondoMenuInicio.png",Texture.class);

        manager.load("Botones/BotonCreditos1.png",Texture.class);
        manager.load("Botones/BotonCreditos2.png",Texture.class);
        manager.load("Botones/BotonPlay1.png",Texture.class);
        manager.load("Botones/BotonPlay2.png",Texture.class);
        manager.load("Botones/BotonSurvival1.png",Texture.class);
        manager.load("Botones/BotonSurvival2.png",Texture.class);
        manager.load("Botones/BotonSettings1.png",Texture.class);
        manager.load("Botones/BotonSettings2.png",Texture.class);
        manager.load("Botones/BotonInstructions1.png",Texture.class);
        manager.load("Botones/BotonInstructions2.png",Texture.class);

        manager.load("DarkMusic.mp3", Music.class);


    }
    private void cargarRecursosCreditos() {
        manager.load("PantallaCreditos.png",Texture.class);
        manager.load("Botones/BotonBack1.png",Texture.class);
        manager.load("Botones/BotonBack2.png",Texture.class);

        manager.load("DarkMusic.mp3", Music.class);

    }
    private void cargarRecursosSetts() {
        manager.load("PantallaSettings.png",Texture.class);
        manager.load("Botones/BotonSounds1.png",Texture.class);
        manager.load("Botones/BotonSounds2.png",Texture.class);
        manager.load("Botones/BotonMusic1.png",Texture.class);
        manager.load("Botones/BotonMusic2.png",Texture.class);
        manager.load("Botones/BotonBack1.png",Texture.class);
        manager.load("Botones/BotonBack2.png",Texture.class);

        manager.load("DarkMusic.mp3", Music.class);

    }
    private void cargarRecursosInst() {
        manager.load("PantallaInstruc1.png",Texture.class);
        manager.load("PantallaInstruc2.png",Texture.class);
        manager.load("PantallaInstruc3.png",Texture.class);

        manager.load("DarkMusic.mp3", Music.class);

    }
    private void cargarRecursosPlayHist1() {
        manager.load("Primer nivel.tmx",TiledMap.class);//"Primer nivelosc.tmx"
        manager.load("siyala.png", Texture.class);
        manager.load("Primer nivel.tmx", TiledMap.class);
        manager.load("Botones/BotonWorld1.png",Texture.class);

        //cargar los recursos de la pausa
        manager.load("Botones/BotonPausa1.png",Texture.class);
        manager.load("Botones/Continue1.png",Texture.class);
        manager.load("Botones/BotonExit1.png",Texture.class);
        manager.load("PantallaPausa.png",Texture.class);
        manager.load("PantallaGameOver.png",Texture.class);

        manager.load("Botones/BotonRetry1.png",Texture.class);

        manager.load("DarkMusic.mp3", Music.class);


    }
    private void cargarRecursosPlayHist2() {
        manager.load("SegundoNivel.tmx",TiledMap.class);//"Primer nivelosc.tmx"
        manager.load("SegundoNivel2.tmx",TiledMap.class);//"Primer nivelosc.tmx"
        manager.load("siyala.png", Texture.class);
        manager.load("DarkMusic.mp3", Music.class);
        manager.load("Botones/BotonWorld1.png",Texture.class);

        //cargar los recursos de la pausa
        manager.load("Botones/BotonPausa1.png",Texture.class);
        manager.load("Botones/Continue1.png",Texture.class);
        manager.load("Botones/BotonExit1.png",Texture.class);
        manager.load("PantallaPausa.png",Texture.class);
        manager.load("PantallaGameOver.png",Texture.class);

        manager.load("Botones/BotonRetry1.png",Texture.class);

        manager.load("DarkMusic.mp3", Music.class);
    }

    private void cargarRecursosPlayHist3() {
        manager.load("TercerNivel.tmx",TiledMap.class);//"Primer nivelosc.tmx"
        manager.load("TercerNivel2.tmx",TiledMap.class);//"Primer nivelosc.tmx"
        manager.load("siyala.png", Texture.class);
        manager.load("DarkMusic.mp3", Music.class);
        manager.load("Botones/BotonWorld1.png",Texture.class);

        //cargar los recursos de la pausa
        manager.load("BotonPausa.png",Texture.class);
        manager.load("Botones/BotonPausa1.png",Texture.class);
        manager.load("Botones/Continue1.png",Texture.class);
        manager.load("Botones/BotonExit1.png",Texture.class);
        manager.load("PantallaPausa.png",Texture.class);
        manager.load("PantallaGameOver.png",Texture.class);

        manager.load("Botones/BotonRetry1.png",Texture.class);

        manager.load("DarkMusic.mp3", Music.class);

    }

    private void cargarRecursosPlaySurv() {

        manager.load("siyala.png", Texture.class);
        manager.load("SurvivalF2.tmx",TiledMap.class);
        manager.load("SurvivalF.tmx",TiledMap.class);
        manager.load("DarkMusic.mp3", Music.class);
        //"Primer nivelosc.tmx"

        manager.load("Botones/BotonWorld1.png",Texture.class);

        //cargar los recursos de la pausa
        manager.load("Botones/BotonPausa1.png",Texture.class);
        manager.load("Botones/Continue1.png",Texture.class);
        manager.load("Botones/BotonExit1.png",Texture.class);
        manager.load("PantallaPausa.png",Texture.class);
        manager.load("PantallaGameOver.png",Texture.class);
        manager.load("Botones/BotonRetry1.png",Texture.class);

        manager.load("DarkMusic.mp3", Music.class);
    }

    private void cargarRecursosNiveles() {
        manager.load("PantallaStorymode.png",Texture.class);
        manager.load("Botones/BotonNivel1_1.png",Texture.class);
        manager.load("Botones/BotonNivel1_2.png",Texture.class);
        manager.load("Botones/BotonNivel2_1.png",Texture.class);
        manager.load("Botones/BotonNivel2_2.png",Texture.class);
        manager.load("Botones/BotonNivel3_1.png",Texture.class);
        manager.load("Botones/BotonNivel3_2.png",Texture.class);

        manager.load("DarkMusic.mp3", Music.class);
    }



    @Override
    public void render(float delta) {
        borrarPantalla(0.5f, 0.5f, 0.5f);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        spriteCarga.draw(batch);
        texto.mostrarMensaje(batch, porcent + " %", ANCHO / 2, ALTO / 2+20);
        batch.end();
        // Actualizar
        timerAnimacion -= delta;
        if (timerAnimacion <= 0) {
            timerAnimacion = TIEMPO;
            spriteCarga.rotate(20);
        }
        actualizarCargaRecursos();
    }

    private void actualizarCargaRecursos() {
        if(manager.update()) {
            switch (sigPantalla) {
                case PLAYHIST3:
                    juego.setScreen(new PantallaPlayHist3(juego));
                    break;
                case PLAYHIST:
                    juego.setScreen(new PantallaPlayHist(juego));
                    break;
                case MENU:
                   juego.setScreen(new PantallaMenu(juego));
                    break;
                case CREDITOS:
                    juego.setScreen(new PantallaCreditos(juego));
                    break;
                case SETTS:
                    juego.setScreen(new PantallaSettings(juego));
                    break;
                case INSTRUCCIONES:
                    juego.setScreen(new PantallaInstrucciones(juego));
                    break;
                case PLAYHIST2:
                    juego.setScreen(new PantallaPlayHist2(juego));
                    break;
                case PLAYSURV:
                    juego.setScreen(new PantallaPlaySurv(juego));
                    break;
                case NIVELES:
                    juego.setScreen(new PantallaNiveles(juego));
                    break;
            }
        }
        porcent = (int)(manager.getProgress()*100);
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



