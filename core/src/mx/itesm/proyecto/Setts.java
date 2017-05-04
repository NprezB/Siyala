package mx.itesm.proyecto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;

/**
 * Created by Dess on 03/05/2017.
 */

public class Setts {
    private static boolean musica=true;
    private static boolean eff=true;
    private static Music musicaFondo;
    protected static float marcadorMayor;
    protected static String nombreMarcadorMayor;
    private static Preferences setting;
    private static int lvl;

    private static float finalDistRecorrida=0;

    public static void cargarSetts(){
        setting= Gdx.app.getPreferences("setting");
        musica=setting.getBoolean("musica",true);
        eff=setting.getBoolean("efectos",true);
        lvl=setting.getInteger("nivel",0);
        marcadorMayor = setting.getFloat("mayor",0);
        nombreMarcadorMayor = setting.getString("nombre", "");
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

    public static void verificrMarcadorAlto(float distRecorrida)
    {
        finalDistRecorrida = distRecorrida;
        Input.TextInputListener listener = new Input.TextInputListener() {
            @Override
            public void input(String text) {
                // Guarda el mejor marcador con el nombre del jugador
                setting.putFloat("mayor", finalDistRecorrida);
                setting.putString("nombre", text);
                setting.flush();

            }

            @Override
            public void canceled() {
            }
        };
        Gdx.input.getTextInput(listener, "Nuevo record, nombre:", nombreMarcadorMayor, "");

    }

    public static void cargarMarcadorMayor() {
    cargarSetts();
        marcadorMayor = setting.getFloat("mayor",0);
        nombreMarcadorMayor = setting.getString("nombre", "");
    }
    public static int cargarNiveles(){
        cargarSetts();
        lvl=setting.getInteger("nivel",0);
        return lvl;
    }
    public static void actualizarNiveles(int nivel){
        lvl=nivel;
        setting.putInteger("nivel",lvl);
        setting.flush();
    }


}
