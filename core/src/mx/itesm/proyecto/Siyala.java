package mx.itesm.proyecto;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Siyala extends Game {
    // Hay un SOLO assetManager para el juego
    private final AssetManager assetManager;

    public Siyala() {
        assetManager = new AssetManager();

    }

    @Override
    public void create () {
        // Lo preparamos para que cargue mapas
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        // Pone la pantalla inicial (Splash)
        setScreen(new PantallaInicio(this));
    }

    // Para que las otras pantallas usen el assetManager
    public AssetManager getAssetManager() {
        return assetManager;
    }

    @Override
    public void dispose() {
        super.dispose();
        assetManager.clear();
    }


    

}