package com.armin.droxoft.diyelimki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SoruSayfasi extends Activity {

    TextView textviewwhatif, textviewresult;
    int hangisorudasin;

    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.sorusayfasi);
        tanimlar();
        hangisorudasin = 1;
        soruyugetir(hangisorudasin);
    }

    private void soruyugetir(int hangisorudasin) {
        DatabaseClassSorular dCS = new DatabaseClassSorular(this);
        dCS.open();
        int soruid = Integer.valueOf(dCS.soruidcek(hangisorudasin));
        String whatif = dCS.whatifcek(hangisorudasin);
        String result = dCS.resultcek(hangisorudasin);
        dCS.close();
        textviewwhatif.setText(whatif);
        textviewresult.setText(result);
    }

    private void tanimlar() {
        textviewwhatif = (TextView) findViewById(R.id.textView2);
        textviewresult = (TextView) findViewById(R.id.textView4);
        ImageButton homebutton = (ImageButton) findViewById(R.id.imageButton);
        homebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(SoruSayfasi.this,Home.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slideinleft, R.anim.slideoutright);
            }
        });
        final Animation ButtonAnim_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim_out);
        final Animation ButtonAnim_out_late = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim__out_late);
        final Animation ButtonAnim_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim_in);
        final RelativeLayout LayEvetHayir = (RelativeLayout) findViewById(R.id.LayEvetHayir);
        final RelativeLayout LayStat = (RelativeLayout) findViewById(R.id.layIstatistik);
        final ImageButton evetButton = (ImageButton) findViewById(R.id.bEvet);
        final ImageButton hayirButton = (ImageButton) findViewById(R.id.bHayir);
        final RelativeLayout StatButton = (RelativeLayout) findViewById(R.id.bEvet2);
        final RelativeLayout ShareButton = (RelativeLayout) findViewById(R.id.bHayir2);
        final TextView textviewistatistik = (TextView) findViewById(R.id.textView);
        evetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evetButton.startAnimation(ButtonAnim_out);
                hayirButton.startAnimation(ButtonAnim_out_late);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DatabaseClassSorular sss = new DatabaseClassSorular(SoruSayfasi.this);
                        sss.open();
                        int yes = Integer.valueOf(sss.yescek(hangisorudasin));
                        int no = Integer.valueOf(sss.nocek(hangisorudasin));
                        sss.close();
                        int dogrulukyuzdesi = (100*yes)/(yes + no+1);
                        textviewistatistik.setText(String.valueOf(dogrulukyuzdesi));
                        LayEvetHayir.setVisibility(View.INVISIBLE);
                        LayStat.setVisibility(View.VISIBLE);
                        StatButton.startAnimation(ButtonAnim_in);
                        ShareButton.startAnimation(ButtonAnim_in);


                    }
                }, 800);

            }
        });

        hayirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hayirButton.startAnimation(ButtonAnim_out);
                evetButton.startAnimation(ButtonAnim_out_late);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DatabaseClassSorular sss = new DatabaseClassSorular(SoruSayfasi.this);
                        sss.open();
                        int yes = Integer.valueOf(sss.yescek(hangisorudasin));
                        int no = Integer.valueOf(sss.nocek(hangisorudasin));
                        sss.close();
                        int dogrulukyuzdesi = (100*yes)/(yes + no+ 1);
                        textviewistatistik.setText(String.valueOf(dogrulukyuzdesi));
                        LayEvetHayir.setVisibility(View.INVISIBLE);
                        LayStat.setVisibility(View.VISIBLE);
                        StatButton.startAnimation(ButtonAnim_in);
                        ShareButton.startAnimation(ButtonAnim_in);

                    }
                }, 800);

            }
        });
        StatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatButton.startAnimation(ButtonAnim_out);
                ShareButton.startAnimation(ButtonAnim_out_late);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hangisorudasin++;
                        DatabaseClassSorular aaa = new DatabaseClassSorular(SoruSayfasi.this);
                        aaa.open();
                        String whatif = aaa.whatifcek(hangisorudasin);
                        String  result = aaa.resultcek(hangisorudasin);
                        aaa.close();
                        textviewwhatif.setText(whatif);
                        textviewresult.setText(result);
                        LayStat.setVisibility(View.INVISIBLE);
                        LayEvetHayir.setVisibility(View.VISIBLE);
                        evetButton.startAnimation(ButtonAnim_in);
                        hayirButton.startAnimation(ButtonAnim_in);

                    }
                }, 800);
            }
        });
        ShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareButton.startAnimation(ButtonAnim_out);
                StatButton.startAnimation(ButtonAnim_out_late);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LayStat.setVisibility(View.INVISIBLE);
                        LayEvetHayir.setVisibility(View.VISIBLE);
                        evetButton.startAnimation(ButtonAnim_in);
                        hayirButton.startAnimation(ButtonAnim_in);

                    }
                }, 800);
            }
        });
    }
}
