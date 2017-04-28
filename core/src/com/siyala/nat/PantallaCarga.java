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
            case PLAYSURV:
                cargarRecursosPlaySurv();
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


    }
    private void cargarRecursosCreditos() {
        manager.load("Pantalla Creditos.png",Texture.class);
        manager.load("ExitBoton.png",Texture.class);

    }
    private void cargarRecursosSetts() {
        manager.load("FondoMenuInicio.png",Texture.class);
        manager.load("BotonsSounds.png",Texture.class);
        manager.load("BotonsSounds.png",Texture.class);
        manager.load("ExitBoton.png",Texture.class);

    }
    private void cargarRecursosInst() {
        manager.load("PantallaInstrucc1.png",Texture.class);
        manager.load("PantallaInstrucc2.png",Texture.class);
        manager.load("Instrucciones3.jpg",Texture.class);

    }
    private void cargarRecursosPlayHist1() {
        manager.load("Segundo Nivel.tmx",TiledMap.class);//"Primer nivelosc.tmx"
        manager.load("siyala.png", Texture.class);
        manager.load("Primer nivel.tmx", TiledMap.class);
        manager.load("DarkMusic.mp3", Music.class);
        manager.load("BotonWorld.png",Texture.class);

        //cargar los recursos de la pausa
        manager.load("BotonPausa.png",Texture.class);
        manager.load("ContinueBoton.png",Texture.class);
        manager.load("ExitBoton.png",Texture.class);
        manager.load("PantallaPausa.png",Texture.class);
        manager.load("PantallaGameOver.png",Texture.class);

        manager.load("BotonRetry.png",Texture.class);


    }
    private void cargarRecursosPlayHist2() {
        manager.load("Segundo Nivel.tmx",TiledMap.class);//"Primer nivelosc.tmx"
        manager.load("siyala.png", Texture.class);
        manager.load("Primer nivel.tmx", TiledMap.class);
        manager.load("DarkMusic.mp3", Music.class);
        manager.load("BotonWorld.png",Texture.class);

        //cargar los recursos de la pausa
        manager.load("BotonPausa.png",Texture.class);
        manager.load("ContinueBoton.png",Texture.class);
        manager.load("ExitBoton.png",Texture.class);
        manager.load("PantallaPausa.png",Texture.class);
        manager.load("PantallaGameOver.png",Texture.class);

        manager.load("BotonRetry.png",Texture.class);


    }
    private void cargarRecursosPlaySurv() {
        manager.load("siyala.png", Texture.class);
        manager.load("Survival.tmx", TiledMap.class);
        manager.load("DarkMusic.mp3", Music.class);


    }



    @Override
    public void render(float delta) {
        borrarPantalla(0.5f, 0.5f, 0.5f);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        spriteCarga.draw(batch);
        texto.mostrarMensaje(batch, porcent + " %", ANCHO / 2, ALTO / 2);
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


