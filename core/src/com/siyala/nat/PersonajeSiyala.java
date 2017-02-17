package com.siyala.nat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Natanael on 17/02/2017.
 */

public class PersonajeSiyala extends Objeto {

    private EstadoAnimacionSiyala estado;
    private float tiempoAnim = 2;
    private float tiempoActual = 2;
    private Texture texturaSiyalaCharac1;
    private Texture texturaSiyalaCharac2;
    private Texture texturaSiyalaCharac3;
    private float xSiya,ySiya;


    public PersonajeSiyala(Texture textura, float x, float y) {
        super(textura, x, y);
        xSiya=this.x;
        ySiya=this.y;
        sprite.setScale(sprite.getScaleX()*3f, sprite.getScaleY()*3f);
    }


}