package com.germang.reproductormusical;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageButton play, pause, bot_repetir;
    MediaPlayer mp;/*no se para que sirve esto*/
    ImageView vista;
    int repetir = 2, posicion = 0;
    int contador = 0;

    /*solo tengo 3 canciones*/
    MediaPlayer[] vectormp = new MediaPlayer[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        /*necesito declarar estos botones (play and pause) para aplicar desde aquí el cambio visual*/
        pause = findViewById(R.id.pause);
        vista = findViewById(R.id.imageView2);
        bot_repetir = findViewById(R.id.bucle);

        vectormp[0] = MediaPlayer.create(this,R.raw.creepcan);
        vectormp[1] = MediaPlayer.create(this,R.raw.californication);
        vectormp[2] = MediaPlayer.create(this,R.raw.numb);

        /*No hay reproducción automatica, si quieres avanzar debes, darle al boton siguiente
        * por eso le ha puesto por defecto que se encuentre en un loop*/
    }

    public void PlayPause (View view) {
        if(vectormp[posicion].isPlaying()) {
            /*si la posición se encuentra en reproducción*/
            vectormp[posicion].pause();/*al darle al boton pauso, por lo que ahora en pausa tiene
            que verse el logo del "PLAY" para la proxima reproducción*/
            play.setVisibility(View.VISIBLE);
            pause.setVisibility(View.INVISIBLE);
            /*los toast me sobran*/
            Toast.makeText(this, "Pausa", Toast.LENGTH_SHORT);

        } else {
                vectormp[posicion].start();
                play.setVisibility(View.INVISIBLE);
                pause.setVisibility(View.VISIBLE);
            /*los toast me sobran*/
            Toast.makeText(this, "Play", Toast.LENGTH_SHORT);
        }
    }


    public void stop (View view) {
        /*definir las imagenes*/
        if (vectormp[posicion] != null) {
            vectormp[posicion].stop();
            /*una vez utilizado el estado stop() no se puede llamar al start o pause hasta que
            * vuelvas a prepaprar el MediPlayer*/
            vectormp[0] = MediaPlayer.create(this,R.raw.creepcan);
            vectormp[1] = MediaPlayer.create(this,R.raw.californication);
            vectormp[2] = MediaPlayer.create(this,R.raw.numb);
            /*una vez preparado el media player, reinicio la posición incial*/
            posicion=0;
            /*debe verse el boton de "PLAY"*/
            play.setVisibility(View.VISIBLE);
            pause.setVisibility(View.INVISIBLE);
            /*la imagen que corresponde al [0] es la de "creep"*/
            vista.setImageResource(R.mipmap.creep);

            /*los toast me sobran*/
            Toast.makeText(this, "Stop", Toast.LENGTH_SHORT);
        }
    }

    public void repetir (View view) {
        if (repetir == 1) {
            /*al dar click se activa el no repetir*/
            bot_repetir.setImageResource(R.drawable.ic_baseline_loop_bri);
            Toast.makeText(this,"No repetir", Toast.LENGTH_SHORT).show();
            vectormp[posicion].setLooping(false);/*no hay loop*/
            repetir = 2;/*cambio al otro valor par permitir cambiar en el siguiente click*/
            /*esto ha funcionado pero no lo tengo muy claro, mejorar luego*/
        } else {
            bot_repetir.setImageResource(R.drawable.ic_baseline_loop_24);
            Toast.makeText(this,"Repetir", Toast.LENGTH_SHORT).show();
            /*por defecto la cancion se repite (inicia en el valor 2)*/
            vectormp[posicion].setLooping(true); /*se encuentra en un loop*/
            repetir = 1;/*defino la segundo opción*/
        }
    }

    public void siguiente (View view) {
        if (posicion < vectormp.length -1) {

            if (vectormp[posicion].isPlaying()) {
                /*la canción se esta tocando*/
                vectormp[posicion].stop();

                vectormp[0] = MediaPlayer.create(this,R.raw.creepcan);
                vectormp[1] = MediaPlayer.create(this,R.raw.californication);
                vectormp[2] = MediaPlayer.create(this,R.raw.numb);

                posicion++;/*pasar al siguiente*/
                vectormp[posicion].start(); /*iniciar la canción*/
                /*definir las portadas que corresponden a las canciones*/
                if (posicion == 0) {
                    vista.setImageResource(R.mipmap.creep);
                } else if (posicion == 1) {
                    vista.setImageResource(R.mipmap.redhot);
                } else if (posicion == 2) {
                    vista.setImageResource(R.mipmap.meteora);
                }
            } else {
                /*la canción esta en pausa*/
                posicion++;
                if (posicion == 0) {
                    vista.setImageResource(R.mipmap.creep);
                } else if (posicion == 1) {
                    vista.setImageResource(R.mipmap.redhot);
                } else if (posicion == 2) {
                    vista.setImageResource(R.mipmap.meteora);
                }
            }
        } else {
            Toast.makeText(this,"No hay mas canciones", Toast.LENGTH_SHORT).show();
        }
    }

    public void anterior (View view) {
        if (posicion >= 1) {
            /*se esta tocando*/
            if (vectormp[posicion].isPlaying()) {
                vectormp[posicion].stop();

                vectormp[0] = MediaPlayer.create(this,R.raw.creepcan);
                vectormp[1] = MediaPlayer.create(this,R.raw.californication);
                vectormp[2] = MediaPlayer.create(this,R.raw.numb);

                posicion--;

                if (posicion == 0) {
                    vista.setImageResource(R.mipmap.creep);
                } else if (posicion == 1) {
                    vista.setImageResource(R.mipmap.redhot);
                } else if (posicion == 2) {
                    vista.setImageResource(R.mipmap.meteora);
                }
                vectormp[posicion].start();

            } else {
                /*se esta en pausa*/
                posicion--;

                if (posicion == 0) {
                    vista.setImageResource(R.mipmap.creep);
                } else if (posicion == 1) {
                    vista.setImageResource(R.mipmap.redhot);
                } else if (posicion == 2) {
                    vista.setImageResource(R.mipmap.meteora);
                }
            }
        } else {
            Toast.makeText(this,"No hay mas canciones", Toast.LENGTH_SHORT).show();
        }
    }
}