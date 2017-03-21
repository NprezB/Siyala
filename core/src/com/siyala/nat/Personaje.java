package com.siyala.nat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Created by Natanael on 15/03/2017.
 */


public class Personaje extends Objeto
{

    private Animation<TextureRegion> spriteAnimado;         // Animación caminando
    private float timerAnimacion;                           // Tiempo para cambiar frames de la animación

    private EstadoMovimiento estadoMovimiento = EstadoMovimiento.MOV_DERECHA;
    private float xDesaparecido;
    private boolean doubJump = false;

    public float y; //La posición origen de salto, modificada cada vez que se aterriza
    private int numJump = 1;

    // Recibe una imagen con varios frames (ver marioSprite.png)
    public Personaje(Texture textura, float x, float y) {
        // Lee la textura como región
        TextureRegion texturaCompleta = new TextureRegion(textura);
        // La divide en 4 frames de 32x64 (ver marioSprite.png)
        TextureRegion[][] texturaPersonaje = texturaCompleta.split(46,30);
        // Crea la animación con tiempo de 0.15 segundos entre frames.

        spriteAnimado = new Animation(0.15f, texturaPersonaje[0][0],
                texturaPersonaje[0][1], texturaPersonaje[0][2], texturaPersonaje[0][1]);
        // Animación infinita
        spriteAnimado.setPlayMode(Animation.PlayMode.LOOP);
        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;
        // Crea el sprite con el personaje quieto (idle)
        sprite = new Sprite(texturaPersonaje[0][1]);    // QUIETO
        sprite.setPosition(x,y);// Posición inicial
        this.y=y;
    }

    // Dibuja el personaje
    public void dibujar(SpriteBatch batch) {
        // Dibuja el personaje dependiendo del estadoMovimiento
        switch (estadoMovimiento) {
            case MOV_DERECHA:
                timerAnimacion += Gdx.graphics.getDeltaTime();
                // Frame que se dibujará
                TextureRegion region = spriteAnimado.getKeyFrame(timerAnimacion);
                if (region.isFlipX()) {
                    region.flip(true,false);
                }
                batch.draw(region,sprite.getX(),sprite.getY());
                break;
            case QUIETO:
            case INICIANDO:
            case SUBIENDO:
            case BAJANDO:
            case DESAPARECIDO:
            case PERDIENDO:
                sprite.draw(batch); // Dibuja el sprite estático
                break;
        }
    }

    // Actualiza el sprite, de acuerdo al estadoMovimiento y estadoSalto
    public boolean actualizar(TiledMap mapa, float delta, float velocidad) {
        switch (estadoMovimiento) {
            case MOV_DERECHA:
            case SUBIENDO:
            case BAJANDO:
            case DESAPARECIDO:
            case PERDIENDO:
                return mover(mapa, delta, velocidad);
        }
        return false;
    }

    private boolean mover(TiledMap mapa, float delta, float velocSiyala) {
        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get(3);
        int x1= (int) (((sprite.getX()+42)/64));
        int x2= (int) (((sprite.getX()+2)/64));
        int y1= (int) (sprite.getY()/32);
        int y2= (int) (((sprite.getY()+30)/32));
        //TiledMapTileLayer.Cell arribaIzquierda = capa.getCell(x2,y2);
        if (estadoMovimiento==EstadoMovimiento.BAJANDO){
            TiledMapTileLayer.Cell abajoDerecha = capa.getCell(x1,y1);
            TiledMapTileLayer.Cell abajoIzquierda = capa.getCell(x2,y1);
            if (abajoDerecha != null) {TiledMapTileLayer.Cell arribaDerecha = capa.getCell(x1,y2);
                Object tipo = (String) abajoDerecha.getTile().getProperties().get("tipo");
                if (!"pasto".equals(tipo)&&!"fuego".equals(tipo)) {
                    abajoDerecha = null;
                }
                if("pasto".equals(tipo)){
                    sprite.setPosition(sprite.getX(),(y1+1)*32);
                    setEstadoMovimiento(EstadoMovimiento.MOV_DERECHA);
                    numJump = 1;
                    y=sprite.getY();
                }
                if("fuego".equals(tipo)){
                    setEstadoMovimiento(EstadoMovimiento.PERDIENDO);
                }
            }

            if (abajoIzquierda != null) {
                Object tipo = (String) abajoIzquierda.getTile().getProperties().get("tipo");
                if (!"pasto".equals(tipo)) {
                    abajoIzquierda = null;
                }
                if("pasto".equals(tipo)){
                    sprite.setPosition(sprite.getX(),(y1+1)*32);
                    setEstadoMovimiento(EstadoMovimiento.MOV_DERECHA);
                    numJump=1;
                    y=sprite.getY();
                }
            }

            if ( abajoDerecha == null && abajoIzquierda == null) {
                sprite.setPosition(sprite.getX()+delta*velocSiyala,sprite.getY()-delta*velocSiyala);
            }
            if (sprite.getY()<0){
                setEstadoMovimiento(EstadoMovimiento.PERDIENDO);
            }
        }
        if (estadoMovimiento==EstadoMovimiento.SUBIENDO){
            TiledMapTileLayer.Cell abajoDerecha = capa.getCell(x1,y1);
            TiledMapTileLayer.Cell arribaDerecha = capa.getCell(x1,y2);
            TiledMapTileLayer.Cell arribaIzquierda = capa.getCell(x2,y2);
            if (arribaDerecha !=null) {
                Object tipo = (String) arribaDerecha.getTile().getProperties().get("tipo");
                if("pasto".equals(tipo)){
                    estadoMovimiento = EstadoMovimiento.BAJANDO;
                }
            }
            if (arribaIzquierda !=null) {
                Object tipo = (String) arribaIzquierda.getTile().getProperties().get("tipo");
                if("pasto".equals(tipo)){
                    estadoMovimiento = EstadoMovimiento.BAJANDO;
                }
            }
            if (abajoDerecha != null) {
                Object tipo = (String) abajoDerecha.getTile().getProperties().get("tipo");
                if (!"pasto".equals(tipo)) {
                    abajoDerecha = null;  // Puede pasar
                }
                if("pasto".equals(tipo)){
                    setEstadoMovimiento(EstadoMovimiento.PERDIENDO);
                }
            }
            if ( abajoDerecha == null) {
                // Prueba que no salga del mundo por la izquierda
                sprite.setPosition(sprite.getX()+delta*velocSiyala,sprite.getY()+delta*velocSiyala);
            }
            if (sprite.getY()>=(y+32*3)){
                sprite.setPosition(sprite.getX(),y+32*3);
                setEstadoMovimiento(EstadoMovimiento.BAJANDO);
            }
        }

        if ( estadoMovimiento== EstadoMovimiento.MOV_DERECHA) {
            sprite.setColor(1,1,1,1);
            // Obtiene el bloque del lado derecho. Asigna null si puede pasar.
            int x = (int) ((sprite.getX()+44) / 64);   // Convierte coordenadas del mundo en coordenadas del mapa
            int y = (int) (sprite.getY()/32);
            int yDown = (int) ((sprite.getY()-1)/32);
            int xDown = (int) ((sprite.getX()+9)/64);
            int xDown2 = (int) ((sprite.getX()+44)/64);
            TiledMapTileLayer.Cell celdaDerecha = capa.getCell(x, y);
            TiledMapTileLayer.Cell celdaAbajo1 = capa.getCell(xDown, yDown);
            TiledMapTileLayer.Cell celdaAbajo2 = capa.getCell(xDown2, yDown);
            if (celdaAbajo1!=null){
                Object tipo = (String) celdaAbajo1.getTile().getProperties().get("tipo");
                if(!"pasto".equals(tipo)){
                    celdaAbajo1 = null;
                }
            }
            if (celdaAbajo2!=null) {
                Object tipo = (String) celdaAbajo2.getTile().getProperties().get("tipo");
                if (!"pasto".equals(tipo)) {
                    celdaAbajo2 = null;
                }
            }
            if(celdaAbajo1 == null && celdaAbajo2 == null){
                setEstadoMovimiento(EstadoMovimiento.BAJANDO);
            }
            if (celdaDerecha != null) {
                Object tipo = (String) celdaDerecha.getTile().getProperties().get("tipo");
                if (!"pasto".equals(tipo)&&!"fuego".equals(tipo)) {
                    celdaDerecha = null;
                }
                if ("pasto".equals(tipo)){
                    setEstadoMovimiento(EstadoMovimiento.PERDIENDO);
                }
                if("fuego".equals(tipo)){
                    setEstadoMovimiento(EstadoMovimiento.PERDIENDO);
                }
            }
            if ( celdaDerecha==null) {
                sprite.setPosition(sprite.getX()+delta*velocSiyala,sprite.getY());
            }
        }

        if(estadoMovimiento == EstadoMovimiento.DESAPARECIDO){
            sprite.setColor(1,1,1,0.5f);
            sprite.setPosition(sprite.getX()+delta*velocSiyala,sprite.getY());
            if(xDesaparecido<=sprite.getX()){
                setEstadoMovimiento(EstadoMovimiento.MOV_DERECHA);
            }
        }
        return false;
    }

    // Accesor de estadoMovimiento
    public EstadoMovimiento getEstadoMovimiento() {
        return estadoMovimiento;
    }

    // Modificador de estadoMovimiento
    public void setEstadoMovimiento(EstadoMovimiento estadoMovimiento) {
        this.estadoMovimiento = estadoMovimiento;
    }

    public void setXDesaparecido() {
        xDesaparecido = sprite.getX()+216;
    }

    public boolean getDoubleJump(){
        return doubJump;
    }

    public int getNumJump() {
        return numJump;
    }

    public void setOneNumJump() {
        numJump+=1;
    }

    public void setY(){
        y=sprite.getY();
    }

    public enum EstadoMovimiento {
        INICIANDO,
        QUIETO,
        SUBIENDO,
        MOV_DERECHA,
        BAJANDO,
        DESAPARECIDO,
        PERDIENDO
    }
}

