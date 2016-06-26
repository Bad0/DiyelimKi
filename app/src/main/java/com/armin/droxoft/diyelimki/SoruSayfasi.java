package com.armin.droxoft.diyelimki;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class SoruSayfasi extends Activity {

    TextView textviewwhatif, textviewresult;
    int hangisorudasin;
    String userid;
    private String sharedPrefUserIdAl(){
        SharedPreferences sP = getSharedPreferences("kullaniciverileri" , Context.MODE_PRIVATE);
        return sP.getString("userid" , "defaultuserid");
    }
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.sorusayfasi);
        tanimlar();
        userid = sharedPrefUserIdAl();
        hangisorudasin = 1;
        soruyugetir(hangisorudasin);
    }

    private void soruyugetir(int hangisorudasin) {
        DatabaseClassSorular dCS = new DatabaseClassSorular(this);
        dCS.open();
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
                        String soruid = sss.soruidcek(hangisorudasin);
                        sss.close();
                        int dogrulukyuzdesi = (100*yes)/(yes + no+1);
                        String status = "0";
                        if(dogrulukyuzdesi>50){
                            status = "1";
                        }
                        textviewistatistik.setText(String.valueOf(dogrulukyuzdesi));
                        ServerEvetCevapGonder sECG = new ServerEvetCevapGonder(soruid,userid,status);
                        sECG.execute();
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
                        String soruid = sss.soruidcek(hangisorudasin);
                        sss.close();
                        int dogrulukyuzdesi = (100*yes)/(yes + no+ 1);
                        String status = "0";
                        if(dogrulukyuzdesi<50){
                            status = "1";
                        }
                        textviewistatistik.setText(String.valueOf(dogrulukyuzdesi));
                        ServerHayirCevapGonder sHCG = new ServerHayirCevapGonder(soruid,userid,status);
                        sHCG.execute();
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


    private class ServerEvetCevapGonder extends AsyncTask<String, Void, String>{

        String soruid, userid , status;
        String query;
        String charset;
        public ServerEvetCevapGonder(String soruid , String userid , String status ){
            this.soruid = soruid;
            this.userid = userid;
            this.status = status;
            String param1 = "whatif";
            String param2 = "result";
            String param3 = "kategori";
            charset = "UTF-8";
            try {
                query = String.format("param1=%s&param2=%s&param3=%s", URLEncoder.encode(param1, charset),
                        URLEncoder.encode(param2, charset), URLEncoder.encode(param3, charset));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        protected String doInBackground(String... params) {
            URLConnection connection = null;
            try {
                connection = new URL("http://185.22.187.60/diyelimki/yes.php?id="+soruid+"&userid="+userid+"&status="+ status).openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            try {
                OutputStream output = new BufferedOutputStream(connection.getOutputStream());
                output.write(query.getBytes(charset));
                output.close();
                InputStream is = connection.getInputStream();
            } catch (IOException exception) {

            }
            return "haha";
        }
    }

    private class ServerHayirCevapGonder extends AsyncTask<String,Void,String>{

        String soruid, userid , status;
        String query;
        String charset;
        public ServerHayirCevapGonder(String soruid , String userid , String status ){
            this.soruid = soruid;
            this.userid = userid;
            this.status = status;
            String param1 = "whatif";
            String param2 = "result";
            String param3 = "kategori";
            charset = "UTF-8";
            try {
                query = String.format("param1=%s&param2=%s&param3=%s", URLEncoder.encode(param1, charset),
                        URLEncoder.encode(param2, charset), URLEncoder.encode(param3, charset));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        protected String doInBackground(String... params) {
            URLConnection connection = null;
            try {
                connection = new URL("http://185.22.187.60/diyelimki/no.php?id="+soruid+"&userid="+userid+"&status="+ status).openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            try {
                OutputStream output = new BufferedOutputStream(connection.getOutputStream());
                output.write(query.getBytes(charset));
                output.close();
                InputStream is = connection.getInputStream();
            } catch (IOException exception) {

            }
            return "haha";
        }
    }
}
