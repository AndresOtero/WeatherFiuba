package hipercompumegared.weatherfiuba;

import android.graphics.Color;



public class ImageFinder {
    public static int getImageColor(Weather.Status status){

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

    public static int getImage(Weather.Status status){

        if(status==Weather.Status.PLAYA_SOLEADA){
            return R.drawable.playa_5_dia_soleado;
        }
        if(status==Weather.Status.SELVA_NUBLADA){
            return R.drawable.selva_nublada;
        }
        if(status==Weather.Status.BALNEARIA_LLUVIOSA){
            return R.drawable.balneario_lluvioso;
        }
        if(status==Weather.Status.CIUDAD_SOLEADA){
            return R.drawable.ciudad_soleada;
        }
        if(status==Weather.Status.CIUADAD_NUBLADA){
            return R.drawable.ciudad_nublada;
        }
        if(status==Weather.Status.CIUDAD_LLUVIOSA){
            return R.drawable.ciudad_lluviosa;
        }
        if(status==Weather.Status.MONTANIA_NEVADA){
            return R.drawable.nevada;
        }
        if(status==Weather.Status.PUENTE_NUBLADO){
            return R.drawable.puente_nublado;
        }
        if(status==Weather.Status.TORMENTA_PAREJA){
            return R.drawable.tormenta_pareja;
        }
        if(status==Weather.Status.NOCHE_ESTRELLADA){
            return R.drawable.noche_estrellada;
        }
        return R.drawable.ciudad_soleada;//ventana_lluviosa
    }

}
