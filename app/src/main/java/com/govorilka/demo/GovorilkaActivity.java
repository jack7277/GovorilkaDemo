/* ====================================================================
 * Copyright (c) 2014 Alpha Cephei Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY ALPHA CEPHEI INC. ``AS IS'' AND
 * ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL CARNEGIE MELLON UNIVERSITY
 * NOR ITS EMPLOYEES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * ====================================================================
 */

package com.govorilka.demo;

//import static android.widget.Toast.makeText;

import android.Manifest;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.rollbar.android.Rollbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import ru.yandex.speechkit.Recognition;
import ru.yandex.speechkit.Recognizer;
import ru.yandex.speechkit.RecognizerListener;
import ru.yandex.speechkit.SpeechKit;

import static edu.cmu.pocketsphinx.SpeechRecognizerSetup.defaultSetup;


public class GovorilkaActivity extends Activity implements RecognitionListener, RecognizerListener, LockScreenUtils.OnLockStatusChangedListener {
    //, TextToSpeech.OnInitListener {

    // User-interface

    protected Button exitBtn1;
    // Member variables
    public LockScreenUtils mLockscreenUtils;


    private static final String YANDEX_API_KEY = "c5cb6a99-37c7-4a31-ac50-4966221c9a8d";
    private static final String ROLLBAR_API_KEY = "dd7b5fce90ef4516b3e3a6efde9ba080";
    private static final int REQUEST_PERMISSION_CODE = 1;

    private Recognizer recognizer2;

    /* Named searches allow to quickly reconfigure the decoder */
    private static final String KWS_SEARCH = "wakeup";
    private static final String CODE_PHRASE_SEARCH = "digits";
//    private static final String COMMAND_SEARCH = "commands";

    private int timeToListen = 3000;

  /* Keyword we are looking for to activate */
//    private static final String KEYPHRASE = "няня";

    protected long startTimestamp, endTimestamp;

    private SpeechRecognizer recognizer;
    private HashMap<String, Integer> captions;

//    private TextToSpeech mTTS;

    private float senseValue = 1e-12f;

    boolean yandexIsOn = false;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private ImageView imageView;

    public float getSenseValue() {
        return senseValue;
    }

    private void setSenseValue(float senseValue) {
        this.senseValue = senseValue;
    }

    // Яндекс интерфейс, реализация //
    @Override
    public void onPowerUpdated(Recognizer recognizer2, float power) {
//        updateProgress((int) (power * progressBar.getMax()));
    }

    private void updateResult(String text) {
        // recognitionResult удален
//        ((TextView) findViewById(R.id.recognitionResult)).setText(text);
    }

    // recognitionResult удален
    private void updateStatus(final String text) {
//        ((TextView) findViewById(R.id.recognitionResult)).setText(text);
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        resetRecognizer();
//    }


    private void resetRecognizer() {
        if (recognizer2 != null) {
            recognizer2.cancel();
            recognizer2 = null;
        }
    }

    @Override
    public void onRecordingBegin(Recognizer recognizer2) {
//        updateStatus("Recording begin");
    }

    @Override
    public void onSpeechDetected(Recognizer recognizer2) {
//        updateStatus("Speech detected");
//        findViewById(R.id.talkBtn).setEnabled(false);
//        ((TextView) findViewById(R.id.log_text)).setText("Яндекс слушает...");
    }

    @Override
    public void onSpeechEnds(Recognizer recognizer2) {
//        updateStatus("Speech ends");
//        findViewById(R.id.talkBtn).setEnabled(true);
//        ((TextView) findViewById(R.id.log_text)).setText("Яндекс услышал.");
    }

    @Override
    public void onRecordingDone(Recognizer recognizer2) {
//        updateStatus("Recording done");
    }

    @Override
    public void onSoundDataRecorded(Recognizer recognizer2, byte[] bytes) {
    }

    @Override
    public void onPartialResults(Recognizer recognizer2, Recognition recognition, boolean b) {
//      updateStatus("Неточный результат: " + recognition.getBestResultText());
        String bestPartialResults = recognition.getBestResultText();
    }

    @Override
    public void onRecognitionDone(Recognizer recognizer2, Recognition recognition) {
        String recognitionString = recognition.getBestResultText();
//        updateResult(recognitionString + " final");


        findViewById(R.id.talkBtn).setEnabled(true);
        ((TextView) findViewById(R.id.log_text)).setText("Готов.");
    }

    @Override
    public void onError(Recognizer recognizer2, ru.yandex.speechkit.Error error) {
        if (error.getCode() == error.ERROR_CANCELED) {
            updateStatus("Cancelled");

        } else {
            updateStatus("Error occurred " + error.getString());
            resetRecognizer();
        }
    }


    // Set appropriate flags to make the screen appear over the keyguard
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }


    private void init() {
        mLockscreenUtils = new LockScreenUtils();

        exitBtn1 = (Button) findViewById(R.id.exitBtn);

        exitBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // unlock home button and then screen on button press
                unlockHomeButton();
            }
        });

        exitBtn1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                stopService(new Intent(GovorilkaActivity.this, GovorilkaService.class));
                finish();
                return true;
            }
        });
    }

    // Handle events of calls and unlock screen if necessary
    private class StateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);

            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    unlockHomeButton();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
            }
        }
    }


    // Handle button clicks
    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
                || (keyCode == KeyEvent.KEYCODE_POWER)
                || (keyCode == KeyEvent.KEYCODE_VOLUME_UP)
                || (keyCode == KeyEvent.KEYCODE_CAMERA)) {
            return true;
        }
        if ((keyCode == KeyEvent.KEYCODE_HOME)) {
            return true;
        }

        return false;
    }

    // handle the key press events here itself
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP
                || (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN)
                || (event.getKeyCode() == KeyEvent.KEYCODE_POWER)) {
            return false;
        }
        if ((event.getKeyCode() == KeyEvent.KEYCODE_HOME)) {

            return true;
        }
        return false;
    }

    // Lock home button
    public void lockHomeButton() {
        mLockscreenUtils.lock(GovorilkaActivity.this);
    }

    // Unlock home button and wait for its callback
    // падает тут при повороте
    public void unlockHomeButton() {
        mLockscreenUtils.unlock(GovorilkaActivity.this);
    }

    // Simply unlock device when home button is successfully unlocked
    @Override
    public void onLockStatusChanged(boolean isLocked) {
        if (!isLocked) {
            unlockDevice();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

//        падает при повороте непонятно отчего
//        unlockHomeButton();

    }

    @SuppressWarnings("deprecation")
    private void disableKeyguard() {
        KeyguardManager mKM = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock mKL = mKM.newKeyguardLock("IN");
        mKL.disableKeyguard();
    }

    @SuppressWarnings("deprecation")
    private void enableKeyguard() {
        KeyguardManager mKM = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock mKL = mKM.newKeyguardLock("IN");
        mKL.reenableKeyguard();
    }

    //Simply unlock device by finishing the activity
    private void unlockDevice() {
        finish();
    }


    private Bundle savedState;
    private boolean saved;
    private static final String _FRAGMENT_STATE = "FRAGMENT_STATE";

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
    }

    protected Bundle getSavedState() {
        return savedState;
    }

    protected Bundle getStateToSave() {
        return null;
    }


    private String password1Text = "";
    private String password2Text = "";
    private String password3Text = "";

    private static final String LOG_TAG = "GovorilkaLog: ";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.talk:
                //onTalkButtonClick((View) GovorilkaActivity.this);
//                System.out.println("sdfsdf");
                return true;

            case R.id.settings:

                return true;

            case R.id.exit:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Log.d(LOG_TAG, "onCreate: ddddddd");

//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setType(
                WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);

        this.getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        );

        setContentView(R.layout.main);

        init();

        // unlock screen in case of app get killed by system
        if (getIntent() != null && getIntent().hasExtra("kill")
                && getIntent().getExtras().getInt("kill") == 1) {
            enableKeyguard();
            unlockHomeButton();

        } else {
            try {
                // disable keyguard
                disableKeyguard();

                // lock home button
                lockHomeButton();

                // start service for observing intents
                startService(new Intent(this, GovorilkaService.class));

                // listen the events get fired during the call
                StateListener phoneStateListener = new StateListener();
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
            } catch (Exception e) {
            }
        }

        try {
            // инициализация журнала падений Rollbar.com
            Rollbar.init(this, ROLLBAR_API_KEY, "production");
            // включаем отправку Logcat
            Rollbar.setIncludeLogcat(true);
            // оно по дефолту true, на всякий случай)
            Rollbar.setSendOnUncaughtException(true);
        } catch (Exception e) {
        }

//        mTTS = new TextToSpeech(this, this);

        // Prepare the data for UI
        captions = new HashMap<String, Integer>();
//        captions.put(KWS_SEARCH, R.string.kws_caption);
        captions.put(CODE_PHRASE_SEARCH, R.string.digits_caption);

        // читаем пароль из файлов code1.txt, code2.txt, code3.txt
        password1Text = openCode("code1.txt");
        password2Text = openCode("code2.txt");
        password3Text = openCode("code3.txt");


        // Отключаем все кнопки, пока не инициализируется движок
        findViewById(R.id.talkBtn).setEnabled(false);
        findViewById(R.id.exitBtn).setEnabled(false);
        findViewById(R.id.settingsBtn).setEnabled(false);
        findViewById(R.id.resetBtn).setEnabled(false);

        ((TextView) findViewById(R.id.log_text)).setText("Подготовка..."); // Preparing the recognizer

        // Recognizer initialization is a time-consuming and it involves IO,
        // so we execute it in async task
//        if (savedInstanceState == null) {
//            try {
        SphinxInit();
    }

    // Инициализация Сфинкс
    public void SphinxInit() {
        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(GovorilkaActivity.this);
                    File assetDir = assets.syncAssets();
                    setupRecognizer(assetDir);
                } catch (IOException e) {
                    Rollbar.reportException(e, "critical", "Crash at doInBackground");
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                    Rollbar.reportException(result, "critical", "init error");
                } else {
                    // включаем кнопку говорить после инициализации
                    findViewById(R.id.talkBtn).setEnabled(true);
                    findViewById(R.id.exitBtn).setEnabled(true);
                    findViewById(R.id.settingsBtn).setEnabled(true);
                    findViewById(R.id.resetBtn).setEnabled(true);

                    ((TextView) findViewById(R.id.log_text)).setText("Готов.");
                }
            }
        }.execute();
    }

    private String openCode(String filename) {
        String imagePath = "";

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(openFileInput(filename)));
            imagePath = br.readLine();
            br.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        return imagePath;
    }


    //    @Override
//    public void onInit(int status) {
//        if (status == TextToSpeech.SUCCESS) {
//
//            Locale locale = new Locale("ru");
//
//            int result = mTTS.setLanguage(locale);
//            //int result = mTTS.setLanguage(Locale.getDefault());
//
//            if (result == TextToSpeech.LANG_MISSING_DATA
//                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                Log.e("TTS", "Извините, этот язык не поддерживается");
//            } else {
//
//            }
//
//        } else {
//            Log.e("TTS", "Ошибка!");
//        }
//
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (mTTS != null) {
//            mTTS.stop();
//            mTTS.shutdown();
//        }
        try {
            recognizer.cancel();
            recognizer.shutdown();
        } catch (Exception e) {
            Rollbar.reportException(e, "critical", "crashed at OnDestroy");
        } finally {
        }
    }

    public String openPictureAudioURL(String filename) {
        String imagePath = "";

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(openFileInput(filename)));
            imagePath = br.readLine();
            br.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        return imagePath;
    }


    void showPic(String filename, String buttonName) {
        Bitmap bitmap = null;
        Bitmap scaledBitmap = null;

        String imagePath = openPictureAudioURL(filename);

        if (imagePath != null && imagePath != "") {
            Uri selectedImage = Uri.parse(imagePath);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
            } catch (IOException e) {
                return;
            }

            ResizedBitmap resizedBitmap = new ResizedBitmap();
            // вот этот ужас переделать на процентное соотношение
            scaledBitmap = resizedBitmap.getResizedBitmap(bitmap, bitmap.getHeight() / 4, bitmap.getWidth() / 4);

            switch (buttonName) {
                case "smallPic1":
                    imageView = (ImageView) findViewById(R.id.smallPic1);
                    break;
                case "smallPic2":
                    imageView = (ImageView) findViewById(R.id.smallPic2);
                    break;
                case "smallPic3":
                    imageView = (ImageView) findViewById(R.id.smallPic3);
                    break;
            }

            imageView.setImageBitmap(scaledBitmap);

            if (bitmap != null) {
                bitmap.recycle();
                bitmap = null;
                System.gc();
            }
        }
    }

    Boolean checkCode(String recognitionString){
        int counter = 0;

        if (recognitionString.equalsIgnoreCase(password1Text)){
            showPic("pic1.txt", "smallPic1");
            counter++;
        }
        if (recognitionString.equalsIgnoreCase(password2Text)){
            showPic("pic2.txt", "smallPic2");
            counter++;
        }
        if (recognitionString.equalsIgnoreCase(password3Text)){
            showPic("pic3.txt", "smallPic3");
            counter++;
        }


        return false;
    }

    Boolean checkCode(String[] separated) {
        try {
            if (separated[0].equalsIgnoreCase(password1Text)) {
                showPic("pic1.txt", "smallPic1");

                if (separated[1].equalsIgnoreCase(password2Text)) {
                    showPic("pic2.txt", "smallPic2");

                    if (separated[2].equalsIgnoreCase(password3Text)) {
                        showPic("pic3.txt", "smallPic3");

                        return true;
                    }
                }
            }

        } catch (Exception e) {
        }
        return false;
    }

    // проверка кода в Сфинксе, сделать единое упростить
    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null) return;

        String recognitionString = hypothesis.getHypstr();
        int bestScore = hypothesis.getBestScore();

        ((TextView) findViewById(R.id.log_text)).setText(recognitionString);

        String[] separated = recognitionString.split(" ");
        checkCode(separated);
    }

    protected String digitToString(char digit) {
        String digitToStringText = "";
        switch (digit) {
            case '1':
                digitToStringText = "один";
                break;
            case '2':
                digitToStringText = "два";
                break;
            case '3':
                digitToStringText = "три";
                break;
            case '4':
                digitToStringText = "четыре";
                break;
            case '5':
                digitToStringText = "пять";
                break;
            case '6':
                digitToStringText = "шесть";
                break;
            case '7':
                digitToStringText = "семь";
                break;
            case '8':
                digitToStringText = "восемь";
                break;
            case '9':
                digitToStringText = "девять";
                break;

            case '0':
                digitToStringText = "ноль";
                break;

            default:
                break;
        }
        return digitToStringText;
    }

    /**
     * This callback is called when we stop the recognizer.
     */
    // итог сфинкс, опять отдельная проверка
    @Override
    public void onResult(Hypothesis hypothesis) {
        if (hypothesis != null) {// && hypothesis.getBestScore() != 0) {
//            long bestScore = hypothesis.getBestScore();
            int bestScore = hypothesis.getBestScore();
//            long endTimestamp = System.currentTimeMillis();
//            long nmsec = endTimestamp - startTimestamp;
//            float confidence = ((float) bestScore) / (float) nmsec;

            // берем число из поля ввода и создаем строку для сравнения
//            EditText editText = (EditText) findViewById(R.id.passwordText1);
//            String s1 = editText.getText().toString();

            // 173 = один семь три
//            String myPasswordAsText = digitToString(s1.charAt(0)) + " " +
//                    digitToString(s1.charAt(1)) + " " +
//                    digitToString(s1.charAt(2));

            String recognitionString = hypothesis.getHypstr();

            String text = "Результат: " + recognitionString + "= " + bestScore;
            ((TextView) findViewById(R.id.log_text)).setText(text);

            String[] separated = recognitionString.split(" ");

            Boolean codeCorrect = checkCode(separated);
            String soundPath = null;
            PlaySound playSound = new PlaySound();

            // код верный, играем наш звук тада
            if (codeCorrect) {
                soundPath = openPictureAudioURL("sound_success.txt");
                if (soundPath != null && soundPath != "") {
                    playSound.playSound(soundPath, this);
                } else {
                    // если не задан тада звук, то играть звуком по умолчанию из ресурсов
                    playSound.playDefaultTadaSound(this);
                }
            }

            // код неверный, играем наш звук fail
            if (!codeCorrect) {
                soundPath = openPictureAudioURL("sound_fail.txt");
                if (soundPath != null && soundPath != "") {
                    playSound.playSound(soundPath, this);
                } else {
                    // если не задан пользовательский фейл, то играть фейл по умолчанию из ресурсов
                    playSound.playDefaultFailSound(this);
                }
            }

            // включаем кнопки Говорить, Выход, Настройки
            findViewById(R.id.talkBtn).setEnabled(true);
            findViewById(R.id.exitBtn).setEnabled(true);
            findViewById(R.id.settingsBtn).setEnabled(true);
        }
    }


    @Override
    public void onBeginningOfSpeech() {
    }

    /**
     * We stop recognizer here to get a final result
     */
    @Override
    public void onEndOfSpeech() {
        recognizer.stop();

        // включаем кнопки Говорить, Выход, Настройки
        findViewById(R.id.talkBtn).setEnabled(true);
        findViewById(R.id.exitBtn).setEnabled(true);
        findViewById(R.id.settingsBtn).setEnabled(true);
    }

    private void switchSearch(String searchName) {
        try {
            recognizer.stop();
        } catch (IllegalStateException | NullPointerException e) {
            Rollbar.reportException(e, "critical", "recognizer stop error.");
        }

        // If we are not spotting, start listening with timeout (10000 ms or 10 seconds).

        if (searchName.equals(KWS_SEARCH))
            recognizer.startListening(searchName);
        else //recognizer.startListening(CODE_PHRASE_SEARCH);
            // таймер на миллисекунды
            recognizer.startListening(searchName, timeToListen);

        String caption = getResources().getString(captions.get(searchName));
        ((TextView) findViewById(R.id.log_text)).setText(caption);
    }

    private void readSenseValue(File assetsDir) {
        String s;
        try {
            Scanner in = new Scanner(new File(assetsDir, "sense.value"));
            if (in.hasNext()) {
                s = in.nextLine();
                in.close();
                float fileSenseValue = Float.parseFloat(s);
                setSenseValue(fileSenseValue);
            }
        } catch (IOException | NumberFormatException e) {
            Rollbar.reportException(e, "critical", "error reading/parse sense.value");
        }
    }

    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    private void setupRecognizer(File assetsDir) throws IOException, NumberFormatException {
        readSenseValue(assetsDir);

        // Threshold to tune for keyphrase to balance between false alarms and misses
        // Примерный диапазон значений — от 1e-1 до 1e-40 в зависимости от активационной фразы.
        // было 1e-45f это вообще будет идти шумный мусор, любая фраза поток шума, всё будет распознавать как кодовую фразу, отстой
        // 1e-1f это вообще глухой слушатель, может быть дооретесь, если орать прямо в микрофон.
        // норм где-то с 1е-7 до 1е-15 для русской модели ru 4000 cont


        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);

        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
            return;
        }

//        while (permissionCheck == PackageManager.PERMISSION_DENIED) {
//            permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
//        }

        recognizer = defaultSetup()
                .setAcousticModel(new File(assetsDir, "msu_ru_zero.cd_cont_2000"))
                .setDictionary(new File(assetsDir, "ruWords.dict"))
                .setBoolean("-remove_noise", true)
               // .setBoolean("-remove_silence", true)
                .setKeywordThreshold(senseValue)
                // Use context-independent phonetic search, context-dependent is too slow for mobile
                .setBoolean("-allphone_ci", true)
                .getRecognizer();

        recognizer.addListener(this);

        // Create keyword-activation search.
//        recognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE);
        File digitsGrammar = new File(assetsDir, "ruWords.gram");
        recognizer.addGrammarSearch(CODE_PHRASE_SEARCH, digitsGrammar);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == PERMISSIONS_REQUEST_RECORD_AUDIO) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                runRecognizerSetup();
//            } else {
//                finish();
//            }
//        }
//    }

    @Override
    public void onError(Exception error) {
        Rollbar.reportException(error, "critical", "error in onError method");
        ((TextView) findViewById(R.id.log_text)).setText(error.getMessage());
    }

    @Override
    public void onTimeout() {
//        switchSearch(CODE_PHRASE_SEARCH);
        // по таймауту включаем кнопку говорить
        recognizer.cancel();
        recognizer.stop();

        findViewById(R.id.talkBtn).setEnabled(true);
        findViewById(R.id.exitBtn).setEnabled(true);
        findViewById(R.id.settingsBtn).setEnabled(true);
    }

    // жмем кнопку Выход
    public void buttonExitClick(View view) {
        super.onDestroy();
        recognizer.cancel();
        recognizer.shutdown();
        finish();
    }

    private void createAndStartYandexRecognizer() {
        final Context context = getApplicationContext();
        if (context == null) {
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
        } else {
            resetRecognizer();
            recognizer2 = Recognizer.create(Recognizer.Language.RUSSIAN, Recognizer.Model.NOTES, this);
            recognizer2.start();
        }
    }

    // жмем кнопку Говорить
    public void onTalkButtonClick(View view) {
        // жмем кнопку сброса
        resetButtonClick(view);
        // выключаем кнопку Говорить
        findViewById(R.id.talkBtn).setEnabled(false);
        findViewById(R.id.exitBtn).setEnabled(false);
        findViewById(R.id.settingsBtn).setEnabled(false);
        // включаем Сфинкс, слушаем
        recognizer.startListening(CODE_PHRASE_SEARCH, timeToListen);
    }

    // жмем кнопку сброса
    public void resetButtonClick(View view) {
        // включаем кнопку Говорить
        findViewById(R.id.talkBtn).setEnabled(true);
        // чистим 4 картинки, 1 большую и 3 маленьких
        ((ImageView) findViewById(R.id.imageViewBig)).setImageBitmap(null);
        ((ImageView) findViewById(R.id.smallPic1)).setImageBitmap(null);
        ((ImageView) findViewById(R.id.smallPic2)).setImageBitmap(null);
        ((ImageView) findViewById(R.id.smallPic3)).setImageBitmap(null);

        ((TextView) findViewById(R.id.log_text)).setText("");
        // принудительно запускаем сборщик мусора
        System.gc();
    }

    public void turnYandexOn(View view) {
        if (yandexIsOn == false) {
//        if (mTTS != null) {
//            mTTS.stop();
//            mTTS.shutdown();
//        }
            try {
                recognizer.cancel();
                recognizer.shutdown();
            } catch (NullPointerException e) {
                Rollbar.reportException(e, "critical", "crashed at OnDestroy");
            } finally {

            }

            SpeechKit.getInstance().configure(getApplicationContext(), YANDEX_API_KEY);
            yandexIsOn = true;
            return;
        }

        if (yandexIsOn == true) {
            yandexIsOn = false;
            try {
                recognizer2.cancel();
            } catch (Exception e) {

            }
            try {
                SphinxInit();
            } catch (Exception e) {
            }
            return;
        }
    }

//    public class passwordText1Watcher implements TextWatcher {
////        public EditText editText;
//
//        public passwordText1Watcher(EditText et) {
//            super();
////            editText = et;
//        }
//
//        public void afterTextChanged(Editable s) {
////            ((TextView) findViewById(R.id.log_text)).setText("Текст изменен");
////            savePasswordFile("code.txt");
//        }
//
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////            ((TextView) findViewById(R.id.log_text)).setText("");
//        }
//
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//        }
//    }

    public void callSettingsScreen(View view) {
        // убиваем текущую активность
        finish();

        // запускаем активность настроек
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    // отключаем кнопку назад
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Только кнопкой Выход", Toast.LENGTH_LONG).show();
    }
}
