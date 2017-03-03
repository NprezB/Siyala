package com.siyala.nat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Natanael on 17/02/2017.
 */

public class PersonajeSiyala{

    private float timeAnimac;
    private Animation<TextureRegion> spriteAnimado;

    private Estado estadoSiy = Estado.Corriendo;
    private float alturaActual;
    private float alturaOriginal;
    private final float velocidadSalto = 10;


    public PersonajeSiyala(Texture textura, float x, float y) {
        TextureRegion texturaCompeta = new TextureRegion(textura);
        TextureRegion[][] texturaPersonaje = texturaCompeta.split(,);
        spriteAnimado = new Animation(0.12f, texturaPersonaje[0][1], texturaPersonaje[0][2],
                texturaPersonaje[0][1],texturaPersonaje[0][3]);
        spriteAnimado.setPlayMode(Animation.PlayMode.LOOP);
        timeAnimac = 0;
    }

    public void setEstado(Estado estadoSiy) {
        this.estadoSiy = estadoSiy;
    }

    public void dibujar(SpriteBatch batch, float delta){
        switch (estadoSiy){
            case Corriendo:
                timeAnimac += Gdx.graphics.getDeltaTime();
                TextureRegion region = spriteAnimado.getKeyFrame(timeAnimac);
            case Subiendo:
                alturaActual = delta*velocidadSalto;
                if (alturaActual>=alturaOriginal+96){
                    alturaActual = alturaOriginal+96;
                    estadoSiy = Estado.Bajando
                }
            case Bajando:
                alturaActual = delta*velocidadSalto;
                if (alturaActual<=alturaOriginal){
                    alturaActual = alturaOriginal;
                    estadoSiy = Estado.Corriendo;
                }
            case Desapareciendo:
        }
    }

    public enum Estado {
        Corriendo,
        Subiendo,
        Bajando,
        Desapareciendo
    }

}