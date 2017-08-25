package hipercompumegared.weatherfiuba;

import android.graphics.Color;

import java.io.File;

/**
 * Created by andres on 8/25/17.
 */

public class ImageFinder {
    public static int getImage(Weather.Status status){

        if(status==Weather.Status.PLAYA_SOLEADA){
            return Color.LTGRAY;
        }
        if(status==Weather.Status.SELVA_NUBLADA){
            return Color.GRAY;

        }
        if(status==Weather.Status.BALNEARIA_LLUVIOSA){
            return Color.DKGRAY;

        }
        if(status==Weather.Status.CIUDAD_SOLEADA){
            return Color.YELLOW;
        }
        if(status==Weather.Status.CIUADAD_NUBLADA){
            return Color.BLUE;

        }
        if(status==Weather.Status.CIUDAD_LLUVIOSA){
            return Color.RED;
            }
        if(status==Weather.Status.MONTANIA_NEVADA){
            return Color.WHITE;
            }
        if(status==Weather.Status.PUENTE_NUBLADO){
            return Color.MAGENTA;
        }
        if(status==Weather.Status.TORMENTA_PAREJA){
            return Color.CYAN;
            }
        if(status==Weather.Status.NOCHE_ESTRELLADA){
            return Color.GRAY;

        }
        return Color.DKGRAY;//ventana_lluviosa
    }

}
