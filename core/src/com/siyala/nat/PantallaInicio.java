package com.siyala.nat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Dess on 26/04/2017.
 */

public class PantallaInicio extends Pantalla {

    private float tiempoMuestra=1.5f;

    private Siyala juego;
    private Texture textLogo;
    private Sprite spriteLogo;

    public PantallaInicio(Siyala juego){
        this.juego=juego;
    }


    @Override
    public void show() {
        textLogo = new Texture(Gdx.files.internal("logo.png"));
        spriteLogo = new Sprite(textLogo);
        spriteLogo.setPosition(ANCHO/2-spriteLogo.getWidth()/2, ALTO/2-spriteLogo.getHeight()/2);
        escalarLogo();
    }

    //para la proporcion
    private void escalarLogo() {
        float factorCamara = ANCHO / ALTO;
        float factorPantalla = 1f*Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        float escala = (factorCamara) / (factorPantalla*1.6f);
        spriteLogo.setScale(escala, .6f);
    }



    @Override
    public void render(float delta) {
        borrarPantalla(1,0.55f,0);

        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        // Dibuja el logo
        spriteLogo.draw(batch);
        batch.end();

        // Para cambiar pantalla
        tiempoMuestra -= delta;
        if (tiempoMuestra<=0) {
            juego.setScreen(new PantallaCarga(juego, Pantallas.MENU));
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
        textLogo.dispose();

    }
}
