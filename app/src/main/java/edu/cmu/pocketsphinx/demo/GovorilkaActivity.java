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

package edu.cmu.pocketsphinx.demo;

//import static android.widget.Toast.makeText;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.rollbar.android.Rollbar;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import ru.yandex.speechkit.Recognizer;

import static edu.cmu.pocketsphinx.SpeechRecognizerSetup.defaultSetup;

//import android.content.Intent;
//import android.content.pm.ActivityInfo;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.widget.Button;
//import android.widget.ListView;import ru.yandex.speechkit.SpeechKit;


public class GovorilkaActivity extends Activity implements RecognitionListener{//, RecognizerListener {
    //, TextToSpeech.OnInitListener {
// наши часы
    private TextView clock;
    // наши картинки
    private ImageView droid, home;
    // отслеживаем в этот массив координаты пальца
    private int[] droidpos;
    // и передаем в этим переменные
    private int home_x, home_y;
    // лайот на котором находится весь лок скрин
    private LayoutParams layoutParams;
    // размеры экрана
    private int windowheight;
    private int windowwidth;


    //SpeechKit Cloud key  562590c3-16e9-485a-a731-02808875df1b
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

    private float senseValue = 1e-7f;
    private boolean lock1Button = true;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public float getSenseValue() {
        return senseValue;
    }

    private void setSenseValue(float senseValue) {
        this.senseValue = senseValue;
    }

//    private void createAndStartRecognizer() {
//        final Context context = getApplicationContext();
//        if (context == null) {
//            return;
//        }
//
//        if (ContextCompat.checkSelfPermission(context, RECORD_AUDIO) != PERMISSION_GRANTED) {
//            requestPermissions(this, new String[]{RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
//        } else {
//            // Reset the current recognizer.
//            resetRecognizer();
//            // To create a new recognizer, specify the language, the model - a scope of recognition to get the most appropriate results,
//            // set the listener to handle the recognition events.
//            recognizer2 = Recognizer.create(Recognizer.Language.RUSSIAN, Recognizer.Model.NOTES, this);
//            // Don't forget to call start on the created object.
//            recognizer2.start();
//        }
//    }
//
//    @Override
//    public void onPowerUpdated(Recognizer recognizer2, float power) {
////        updateProgress((int) (power * progressBar.getMax()));
//    }
//
//    private void updateResult(String text) {
//        ((TextView) findViewById(R.id.recognitionResult)).setText(text);
//    }
//
//    private void updateStatus(final String text) {
//        ((TextView) findViewById(R.id.recognitionResult)).setText(text);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        resetRecognizer();
//    }
//
//
//    private void resetRecognizer() {
//        if (recognizer2 != null) {
//            recognizer2.cancel();
//            recognizer2 = null;
//        }
//    }

//    @Override
//    public void onRecordingBegin(Recognizer recognizer2) {
//        updateStatus("Recording begin");
//    }
//
//    @Override
//    public void onSpeechDetected(Recognizer recognizer2) {
//        updateStatus("Speech detected");
//    }
//
//    @Override
//    public void onSpeechEnds(Recognizer recognizer2) {
//        updateStatus("Speech ends");
//    }
//
//    @Override
//    public void onRecordingDone(Recognizer recognizer2) {
//        updateStatus("Recording done");
//    }
//
//    @Override
//    public void onSoundDataRecorded(Recognizer recognizer2, byte[] bytes) {
//    }
//
//
//    @Override
//    public void onPartialResults(Recognizer recognizer2, Recognition recognition, boolean b) {
//        updateStatus("Partial results " + recognition.getBestResultText());
//    }
//
//    @Override
//    public void onRecognitionDone(Recognizer recognizer2, Recognition recognition) {
//        updateResult(recognition.getBestResultText());


//        final EditText editText = (EditText) findViewById(R.id.passwordText1);
//        String s1 = editText.getText().toString();
//
//        String myPasswordAsText = "";
//
//        // 173 = один семь три
//        myPasswordAsText = digitToString(s1.charAt(0)) + " " +
//                digitToString(s1.charAt(1)) + " " +
//                digitToString(s1.charAt(2));
//
//        String recognitionString = recognition.getBestResultText();
//
//        // пароь совпал
//        if (recognitionString.contains(myPasswordAsText) || recognitionString.contains(s1)) {
//            ((ImageView) findViewById(R.id.lock1Button)).setImageResource(R.drawable.ts);
//            ((ImageView) findViewById(R.id.lock2Button)).setImageResource(R.drawable.bio);
//            ((ImageView) findViewById(R.id.lock3Button)).setImageResource(R.drawable.bya);
//        }
//        findViewById(R.id.talkButton).setEnabled(true);
//    }
//
//    @Override
//    public void onError(Recognizer recognizer2, ru.yandex.speechkit.Error error) {
//        if (error.getCode() == Error.ERROR_CANCELED) {
//            updateStatus("Cancelled");
//
//        } else {
//            updateStatus("Error occurred " + error.getString());
//            resetRecognizer();
//        }
//    }

//    private void saveFile(File f, String content) throws IOException {
//        File dir = f.getParentFile();
//        if (!dir.exists() && !dir.mkdirs()) {
//            throw new IOException("Cannot create directory: " + dir);
//        }
//        FileUtils.writeStringToFile(f, content, "UTF8");
//    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // инициализация журнала падений Rollbar.com
        Rollbar.init(this, ROLLBAR_API_KEY, "production");
        // включаем отправку Logcat
        Rollbar.setIncludeLogcat(true);
        // оно по дефолту true, на всякий случай)
        Rollbar.setSendOnUncaughtException(true);

        // делаем наше экран полностью залоченым

//        SpeechKit.getInstance().configure(getApplicationContext(), YANDEX_API_KEY);

//        mTTS = new TextToSpeech(this, this);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //для портретного режима

        // Prepare the data for UI
        captions = new HashMap<String, Integer>();
//        captions.put(KWS_SEARCH, R.string.kws_caption);
        captions.put(CODE_PHRASE_SEARCH, R.string.digits_caption);

        setContentView(R.layout.main);



//        if ((getIntent() != null) && getIntent().hasExtra("kill")
//                && (getIntent().getExtras().getInt("kill") == 1)) {
//            finish();
//        }

            try {
                // инициализация рисивера
                startService(new Intent(this, GovorilkaService.class));
                // стартуем отслеживание состояния телефона
                PhoneStateListener phoneStateListener = new PhoneStateListener();
                // узнаем все сервисы которые есть
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                // слушаем когда телефон уходит в сон и включаем нашего блокировщика
                telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
                // хватаем размеры экрана и растягиваем под него локскрин
//                windowwidth = getWindowManager().getDefaultDisplay().getWidth();
//                windowheight = getWindowManager().getDefaultDisplay().getHeight();

            } catch (Exception e) {
                Rollbar.reportException(e, "critical", "Crash at start GovorilkaSerice");
            }

            // Отключаем кнопку говорить, пока не инициализируется движок
            findViewById(R.id.talkButton).setEnabled(false);
            findViewById(R.id.exitButton).setEnabled(false);
            ((TextView) findViewById(R.id.caption_text)).setText("Подготовка..."); // Preparing the recognizer

            // Recognizer initialization is a time-consuming and it involves IO,
            // so we execute it in async task


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
//                    ((TextView) findViewById(R.id.caption_text)).setText("Ошибка инициализации... " + result);
                        Rollbar.reportException(result, "critical", "init error");
                    } else {
                        // включаем кнопку говорить после инициализации
                        findViewById(R.id.talkButton).setEnabled(true);
                        findViewById(R.id.exitButton).setEnabled(true);
                        ((TextView) findViewById(R.id.caption_text)).setText("Готов.");
                        ((TextView) findViewById(R.id.log_text)).setText(""); // чистим если показали ошибку чтения sense.value из файла


//                    switchSearch(KWS_SEARCH);
                    }
                }
            }.execute();
        }


    // класс слушатель тач евентов
    class StateListener extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                // если яблоко коснулось дроида то выключаем лок скрин
                case TelephonyManager.CALL_STATE_OFFHOOK: {
                    finish();
                }
                break;
            }
        }
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
        // При закрытии программы всё останавливаем
        @Override
        public void onDestroy () {
//        if (mTTS != null) {
//            mTTS.stop();
//            mTTS.shutdown();
//        }
            super.onDestroy();
            try {
                recognizer.cancel();
                recognizer.shutdown();
            } catch (NullPointerException e) {
                Rollbar.reportException(e, "critical", "crashed at OnDestroy");
            } finally {
                finish();
            }
        }

        /**
         * In partial result we get quick updates about current hypothesis. In
         * keyword spotting mode we can react here, in other modes we need to wait
         * for final result in onResult.
         */
        @Override
        public void onPartialResult (Hypothesis hypothesis){
            if (hypothesis == null) return;

            String text = hypothesis.getHypstr();
            int bestScore = hypothesis.getBestScore();

//        ((TextView) findViewById(R.id.log_text)).setText(text + ", " + bestScore);
//        if (text.equals(KEYPHRASE)) {
//            switchSearch(CODE_PHRASE_SEARCH);
//        }
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


    @Override
    public void onResult(Hypothesis hypothesis) {
        if (hypothesis != null) {// && hypothesis.getBestScore() != 0) {
//            long bestScore = hypothesis.getBestScore();
            int bestScore = hypothesis.getBestScore();
//            long endTimestamp = System.currentTimeMillis();
//            long nmsec = endTimestamp - startTimestamp;
//            float confidence = ((float) bestScore) / (float) nmsec;

            // берем число из поля ввода и создаем строку для сравнения
            final EditText editText = (EditText) findViewById(R.id.passwordText1);
            String s1 = editText.getText().toString();

            // 173 = один семь три
            String myPasswordAsText = digitToString(s1.charAt(0)) + " " +
                    digitToString(s1.charAt(1)) + " " +
                    digitToString(s1.charAt(2));

            String recognitionString = hypothesis.getHypstr();

            String text = "Результат: " + recognitionString + "= " + bestScore;
            ((TextView) findViewById(R.id.log_text)).setText(text);

            // пароь совпал
            if (recognitionString.contains(myPasswordAsText) || recognitionString.contains(s1)) {
                ((ImageView) findViewById(R.id.lock1Button)).setImageResource(R.drawable.ts);
                ((ImageView) findViewById(R.id.lock2Button)).setImageResource(R.drawable.bio);
                ((ImageView) findViewById(R.id.lock3Button)).setImageResource(R.drawable.bya);

//                finish();
            }
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
//        if (!recognizer.getSearchName().equals(KWS_SEARCH))
//            switchSearch(KWS_SEARCH);
        recognizer.stop();
        findViewById(R.id.talkButton).setEnabled(true);
//        recognizer.startListening(CODE_PHRASE_SEARCH);
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
        ((TextView) findViewById(R.id.caption_text)).setText(caption);
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
//            ((TextView) findViewById(R.id.log_text)).setText("Ошибка чтения/формата sense.value");
        }


    }

    private void setupRecognizer(File assetsDir) throws IOException, NumberFormatException {
        // The recognizer can be configured to perform multiple searches
        // of different kind and switch between them


//        PhonMapper phonMapper = new PhonMapper(getAssets().open("sync/dict/hotwords.ru"));
//
//        // добавляем шум
//        String[] words = new String[5003];
////        String[] words = new String[3];
//        int i = 0;
//
//        try {
//            Scanner in = new Scanner(new File(assetsDir, "ruWords.dict"));
//            while (in.hasNext()) {
//                words[i] = in.nextLine();
//                i++;
//            }
//            in.close();
//
//        } catch (IOException | NumberFormatException e) {
//            Rollbar.reportException(e, "critical", "error reading/parse sense.value");
////            ((TextView) findViewById(R.id.log_text)).setText("Ошибка чтения/формата sense.value");
//        }
//
//        // добавляем кодовые слова
//        words[i++] = "шаблагу";
//        words[i++] = "азаза";
//        words[i] = "котек";
//
//// new String[]{"шаблагу", "ахаха", "сыр"}
//        Grammar grammar = new Grammar(words, phonMapper);
////        String s1 = grammar.getJsgf();
////        String s2 = grammar.getDict();
////        grammar.addWords("тест");
//
//        DataFiles dataFiles = new DataFiles(getPackageName(), "ru");
//
//        File dict = new File(dataFiles.getDict());
//        File jsgf = new File(dataFiles.getJsgf());
//
//        saveFile(jsgf, grammar.getJsgf());
//        saveFile(dict, grammar.getDict());

        readSenseValue(assetsDir);

        recognizer = defaultSetup()
//                .setSampleRate(8000)
//                .setAcousticModel(new File(assetsDir, "ru_cont"))
                .setAcousticModel(new File(assetsDir, "msu_ru_zero.cd_cont_2000"))
//                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
//                .setAcousticModel(new File(assetsDir, "zero_ru.cd_ptm_4000"))
                .setDictionary(new File(assetsDir, "ruWords.dict"))
//                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))
//                .setDictionary(new File(assetsDir, "lm/lm.dic"))
//                .setDictionary(dict)

                // To disable logging of raw audio comment out this call (takes a lot of space on the device)
                //.setRawLogDir(assetsDir)

                // Threshold to tune for keyphrase to balance between false alarms and misses
                // Примерный диапазон значений — от 1e-1 до 1e-40 в зависимости от активационной фразы.
                // было 1e-45f это вообще будет идти шумный мусор, любая фраза поток шума, всё будет распознавать как кодовую фразу, отстой
                // 1e-1f это вообще глухой слушатель, может быть дооретесь, если орать прямо в микрофон, зато точнооость!
                // норм где-то с 1е-7 до 1е-15 для русской модели ru 4000 cont
                .setBoolean("-remove_noise", false)
                .setKeywordThreshold(senseValue)  // 1e-8f

                // Use context-independent phonetic search, context-dependent is too slow for mobile
                .setBoolean("-allphone_ci", true)
                .getRecognizer();

        recognizer.addListener(this);

        // Create keyword-activation search.
//        recognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE);

        // Create grammar-based search for digit recognition
        File digitsGrammar = new File(assetsDir, "rudigits.gram");
//        File digitsGrammar = new File(assetsDir, "ENdigits.gram");
        recognizer.addGrammarSearch(CODE_PHRASE_SEARCH, digitsGrammar);

//        recognizer.addGrammarSearch(CODE_PHRASE_SEARCH, jsgf);
//        recognizer.addKeywordSearch(CODE_PHRASE_SEARCH, new File(assetsDir, "digits.gram")); // работало более-менее
//        recognizer.addGrammarSearch(CODE_PHRASE_SEARCH, new File(assetsDir, "lm/lm.jsgf"));
//        recognizer.addGrammarSearch(CODE_PHRASE_SEARCH, jsgf);
    }


    @Override
    public void onError(Exception error) {
        Rollbar.reportException(error, "critical", "error in onError method");
        ((TextView) findViewById(R.id.caption_text)).setText(error.getMessage());
    }

    @Override
    public void onTimeout() {
//        switchSearch(CODE_PHRASE_SEARCH);
    }

    public void buttonExitClick(View view) {
        super.onDestroy();
        recognizer.cancel();
        recognizer.shutdown();
        this.finish();
    }

    public void onTalkButtonClick(View view) {
        //switchSearch(CODE_PHRASE_SEARCH);
        resetButtonClick(view);

        findViewById(R.id.talkButton).setEnabled(false);
//        startTimestamp = System.currentTimeMillis();
        recognizer.startListening(CODE_PHRASE_SEARCH, timeToListen);

//        createAndStartRecognizer();
    }

    public void ackBtnClick(View view) {
        //  String text = "Афирматив!";
//        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void lock1ButtonClick(View view) {
        if (lock1Button == true) {
            ((ImageView) findViewById(R.id.lock1Button)).setImageResource(R.drawable.unlocked);
            lock1Button = false;
        } else {
            ((ImageView) findViewById(R.id.lock1Button)).setImageResource(R.drawable.locked);
            lock1Button = true;
        }
    }
//
//    public void sensePlusButtonClick(View view) {
//        senseValue *= 10;
////        senseValue = Math.round((senseValue * 100d) / 100d);
//        ((TextView) findViewById(R.id.senseValue)).setText(String.valueOf(senseValue));
//    }
//
//    public void senseMinusButtonClick(View view) {
//        senseValue /= 10;
////        senseValue = Math.round((senseValue * 100d) / 100d);
//        ((TextView) findViewById(R.id.senseValue)).setText(String.valueOf(senseValue));
//    }

    public void resetButtonClick(View view) {
        ((ImageView) findViewById(R.id.lock1Button)).setImageResource(R.drawable.locked);
        ((ImageView) findViewById(R.id.lock2Button)).setImageResource(R.drawable.locked);
        ((ImageView) findViewById(R.id.lock3Button)).setImageResource(R.drawable.locked);

        findViewById(R.id.talkButton).setEnabled(true);
    }
}
