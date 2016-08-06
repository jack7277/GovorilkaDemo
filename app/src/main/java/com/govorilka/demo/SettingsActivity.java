package com.govorilka.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by jack on 03.07.2016.
 */

public class SettingsActivity extends Activity {

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        setContentView(R.layout.settings);

        String soundPathSuccess = null;
        soundPathSuccess = openPictureAudioURL("sound_success.txt");
        if (soundPathSuccess != null && soundPathSuccess != "") {
            ((TextView) findViewById(R.id.pathSuccessSnd)).setText(soundPathSuccess);
        } else {
            ((TextView) findViewById(R.id.pathSuccessSnd)).setText("Default TADA sound");
        }

        String soundPathFail = null;
        soundPathFail = openPictureAudioURL("sound_fail.txt");
        if (soundPathFail != null && soundPathFail != "") {
            ((TextView) findViewById(R.id.pathFailSnd)).setText(soundPathFail);
        } else {
            ((TextView) findViewById(R.id.pathFailSnd)).setText("Default FAIL sound");
        }


        // если файл codeX.txt существует, то загружается индекс списка
        // и по индексу устанавливается текст, индексы с нуля
        // filename = code1.txt,
        // spinnerIndex = 0, 1, 2
        openPasswordFileIndexForSpinner("code1.txt", 0);
        openPasswordFileIndexForSpinner("code2.txt", 1);
        openPasswordFileIndexForSpinner("code3.txt", 2);


// показываем картинки сохраненные на кнопках при открытии настроек
        showPic("pic1.txt", "lock1Button");
        showPic("pic2.txt", "lock2Button");
        showPic("pic3.txt", "lock3Button");

        // кодовая фраза 1
        final Spinner spinner1 = (Spinner) findViewById(R.id.codeSpinner1);
        // устанавливаем обработчик нажатия
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // если файла нет, то
                // сохраняет текст первый в списке и индекс 0
                String codePhrase1 = spinner1.getSelectedItem().toString();
                Integer codePosition = spinner1.getSelectedItemPosition();
                savePasswordFile("code1.txt", codePhrase1, codePosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        // кодовая фраза 2
        final Spinner spinner2 = (Spinner) findViewById(R.id.codeSpinner2);
        // устанавливаем обработчик нажатия
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String codePhrase2 = spinner2.getSelectedItem().toString();
                Integer codePosition = spinner2.getSelectedItemPosition();
                savePasswordFile("code2.txt", codePhrase2, codePosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        // кодовая фраза 3
        final Spinner spinner3 = (Spinner) findViewById(R.id.codeSpinner3);
        // устанавливаем обработчик нажатия
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String codePhrase3 = spinner3.getSelectedItem().toString();
                Integer codePosition = spinner3.getSelectedItemPosition();
                savePasswordFile("code3.txt", codePhrase3, codePosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }


    // по кнопке назад переключаемся на основной экран
    // отключаем кнопку назад
    @Override
    public void onBackPressed() {
//        Spinner spinner = (Spinner) findViewById(R.id.codeSpinner1);
//        String selected = spinner.getSelectedItem().toString();
//        Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_SHORT).show();
        // завершаем активность настройки
        finish();

        // заново запускаем основной экран активность
        Intent intent = new Intent(this, GovorilkaActivity.class);
        startActivity(intent);
    }

    // Метод для сохранения файла с кодом
    void savePasswordFile(String filename, String codePhrase, Integer codePosition) {
        try {
            OutputStream outputStream = openFileOutput(filename, 0);
            OutputStreamWriter osw = new OutputStreamWriter(outputStream);
            osw.write(codePhrase + "\n");
            osw.write("" + codePosition);
            osw.flush();
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 3 кнопки выбора картинок
    Boolean btn1Clicked = false;
    Boolean btn2Clicked = false;
    Boolean btn3Clicked = false;

    static final int GALLERY_REQUEST = 1;
    static final int AUDIO_SUCCESS_REQUEST = 2;
    static final int AUDIO_FAIL_REQUEST = 3;


    public void btn1Click(View view) {
        callGalleryPickPhoto();
        btn1Clicked = true;
    }

    public void btn2Click(View view) {
        callGalleryPickPhoto();
        btn2Clicked = true;
    }

    public void btn3Click(View view) {
        callGalleryPickPhoto();
        btn3Clicked = true;
    }

    public String readPictureUrl(String filename) {
        String imagePath = null;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        File f = new File(filename);

        try {
            BufferedReader fin = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            imagePath = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagePath;
    }

    // Метод для открытия файла с кодом
    // filename - имя файла code1.txt, code2.txt, code3.txt
    // spinnerNum - 0, 1, 2 нумерация с 0

    private void openPasswordFileIndexForSpinner(String filename, Integer spinnerNum) {
        try {
            InputStream inputStream = openFileInput(filename);

            if (inputStream != null) {
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);

                try {
                    String passwordText = reader.readLine(); // первым читаем слово
                    Integer passwordIndex = Integer.parseInt(reader.readLine()); // вторым индекс

                    switch (spinnerNum) {
                        case 0: {
                            Spinner spinner = (Spinner) findViewById(R.id.codeSpinner1);
                            spinner.setSelection(passwordIndex);
                        }
                        break;

                        case 1: {
                            Spinner spinner = (Spinner) findViewById(R.id.codeSpinner2);
                            spinner.setSelection(passwordIndex);
                        }
                        break;

                        case 2: {
                            Spinner spinner = (Spinner) findViewById(R.id.codeSpinner3);
                            spinner.setSelection(passwordIndex);
                        }
                        break;

                        default:
                            break;
                    }
                } catch (Exception e) {

                }

                inputStream.close();
            }
        } catch (IOException e) {
        }
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

            // вот этот ужас переделать на процентное соотношение
            ResizedBitmap resizedBitmap = new ResizedBitmap();
            scaledBitmap = resizedBitmap.getResizedBitmap(bitmap, bitmap.getHeight() / 5, bitmap.getWidth() / 5);

            if (buttonName == "lock1Button") {
                imageView = (ImageView) findViewById(R.id.lock1Button);
            }

            if (buttonName == "lock2Button") {
                imageView = (ImageView) findViewById(R.id.lock2Button);
            }

            if (buttonName == "lock3Button") {
                imageView = (ImageView) findViewById(R.id.lock3Button);
            }

            imageView.setImageBitmap(scaledBitmap);

            if (bitmap != null) {
                bitmap.recycle();
                bitmap = null;
                System.gc();
            }
        }
    }

    // выбор фото из галереи
    void callGalleryPickPhoto() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    public void callSuccessSoundPickUp(View view) {
        Intent intent_upload = new Intent();
        intent_upload.setType("audio/*");
        intent_upload.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent_upload, AUDIO_SUCCESS_REQUEST);
    }

    public void callFailSoundPickUp(View view) {
        Intent intent_upload = new Intent();
        intent_upload.setType("audio/*");
        intent_upload.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent_upload, AUDIO_FAIL_REQUEST);
    }

    // и его обработчик, результат активности.
    // общая ссылка на изображение
    ImageView imageView;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Bitmap bitmap = null;
        switch (requestCode) {
            // результат вызова выбора картинки или звука
            // была вызвана галерея, выбрано фото и возвращаемся назад
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();

                    String imagePath = "content://media" + selectedImage.getPath(); //      /external/images/media/9012
                    //selectedImage = Uri.parse(imagePath);

                    if (btn1Clicked) {
                        imageView = (ImageView) findViewById(R.id.lock1Button);
                        savePictureAudioURL("pic1.txt", imagePath);
                        showPic("pic1.txt", "lock1Button");
                    }
                    if (btn2Clicked) {
                        imageView = (ImageView) findViewById(R.id.lock2Button);
                        savePictureAudioURL("pic2.txt", imagePath);
                        showPic("pic2.txt", "lock2Button");
                    }
                    if (btn3Clicked) {
                        imageView = (ImageView) findViewById(R.id.lock3Button);
                        savePictureAudioURL("pic3.txt", imagePath);
                        showPic("pic3.txt", "lock3Button");
                    }
                    try {
                        // bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (Exception e) {

                    }
                    //imageView.setImageBitmap(bitmap);
                }
                break;
            //
            case AUDIO_SUCCESS_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedAudio = imageReturnedIntent.getData();
                    String audioPath = "content://media" + selectedAudio.getPath();
                    savePictureAudioURL("sound_success.txt", audioPath);
                    ((TextView) findViewById(R.id.pathSuccessSnd)).setText(audioPath);
                }
                break;

            //
            case AUDIO_FAIL_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedAudio = imageReturnedIntent.getData();
                    String audioPath = "content://media" + selectedAudio.getPath();
                    savePictureAudioURL("sound_fail.txt", audioPath);
                    ((TextView) findViewById(R.id.pathFailSnd)).setText(audioPath);
                }
                break;
        }

        btn1Clicked = false;
        btn2Clicked = false;
        btn3Clicked = false;
    }

    public void playSuccessSound(View view) {
        String soundPath = null;
        PlaySound playSound = new PlaySound();

        soundPath = openPictureAudioURL("sound_success.txt");
        if (soundPath != null && soundPath != "") {
            playSound.playSound(soundPath, this);
        } else {
            // если не задан тада звук, то играть звуком по умолчанию из ресурсов
            playSound.playDefaultTadaSound(this);
        }
    }


    public void playFailSound(View view) {
        String soundPath = null;
        PlaySound playSound = new PlaySound();

        soundPath = openPictureAudioURL("sound_fail.txt");

        if (soundPath != null && soundPath != "") {
            playSound.playSound(soundPath, this);
        } else {
            // если не задан пользовательский фейл, то играть фейл по умолчанию из ресурсов
            playSound.playDefaultFailSound(this);
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

    void savePictureAudioURL(String filename, String path) {
        try {
            OutputStream outputStream = openFileOutput(filename, 0);
            OutputStreamWriter osw = new OutputStreamWriter(outputStream);
            osw.write(path);
            osw.flush();
            osw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


