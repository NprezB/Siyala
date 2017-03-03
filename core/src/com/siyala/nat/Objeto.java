package com.siyala.nat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Natanael on 17/02/2017.
 */

public class Objeto {
    protected Sprite sprite;    // Imagen
    public float x,y;

    public Objeto(Texture textura, float x, float y) {
        sprite = new Sprite(textura);
        sprite.setPosition(x, y);
        x=this.x;
        y=this.y;
    }

    public void dibujar(SpriteBatch batch) {

        sprite.draw(batch);
    }

    public void setSprite(Texture textura){
        sprite = new Sprite(textura);
    }
}
