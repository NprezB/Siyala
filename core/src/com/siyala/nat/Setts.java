package com.siyala.nat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;

/**
 * Created by Dess on 03/05/2017.
 */

public class Setts {
    private static boolean musica=true;
    private static boolean eff=true;
    private static Music musicaFondo;
    private static Preferences setting;

    public static void cargarSetts(){
        setting= Gdx.app.getPreferences("setting");
        musica=setting.getBoolean("musica",true);
        eff=setting.getBoolean("efectos",true);
    }


    public static void cargarMusica(Music musica){
        musicaFondo=musica;
        musicaFondo.setLooping(true);
    }
    public static void cambiarEstatusMusica(){
        cargarSetts();
        musica=!musica;
        setting.putBoolean("musica",musica);
        setting.flush();
        ponerMusica();

    }
    public static void ponerMusica(){
        if(musica){
            musicaFondo.play();
        }
        else {
            musicaFondo.pause();
        }
    }

    public static void cambiarEstatusEfec(){
        cargarSetts();
        eff=!eff;
        setting.putBoolean("efectos",true);
        setting.flush();
    }

    public static boolean getefect(){
        return eff;
    }
    public static boolean getMus(){
        return musica;
    }


}
