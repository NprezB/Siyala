package com.siyala.nat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Natanael on 17/02/2017.
 */

public class PersonajeSiyala{

    private final float timeAnimac;
    private Animation<TextureRegion> spriteAnimado;

    private Estado estadoSiy = Estado.Corriendo;

    public PersonajeSiyala(Texture textura, float x, float y) {
        TextureRegion texturaCompeta = new TextureRegion(textura);
        TextureRegion[][] texturaPersonaje = texturaCompeta.split(,);
        spriteAnimado = new Animation(0.12f, texturaPersonaje[0][1], texturaPersonaje[0][2],
                texturaPersonaje[0][1],texturaPersonaje[0][3]);
        spriteAnimado.setPlayMode(Animation.PlayMode.LOOP);
        timeAnimac = 0;
    }

    public void dibujar(SpriteBatch batch){
        switch (estadoSiy){
            case Corriendo:
            case Saltando:
            case Desapareciendo:
        }
    }

    public enum Estado {
        Corriendo,
        Saltando,
        Desapareciendo
    }

}