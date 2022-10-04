package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {
    private Button btnOk;
    private TextInputLayout TIfullname;
    private TextInputLayout  TIemail;
    private TextInputLayout TIhomepage;
    private TextInputLayout  TIabout;

    private TextInputLayout  TIpass;
    private TextInputLayout  TIconfpass;
    private TextView TIpasserror;

    private TextInputEditText Etpass;
    private TextInputEditText Etconfpass;

    private TextInputEditText Etfullname;
    private TextInputEditText Etemail;
    private TextInputEditText Ethomepage;
    private TextInputEditText Etabout;

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;
    private ImageView avatarImage;

    public void handleChangeAvatar(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnOk =(Button) findViewById(R.id.button_ok);
        avatarImage =(ImageView) findViewById(R.id.image_profile);
        TIfullname =(TextInputLayout) findViewById(R.id.layout_fullname);
        Etfullname =(TextInputEditText) findViewById(R.id.text_fullname);
        TIemail =(TextInputLayout)  findViewById(R.id.layout_email);
        Etemail =(TextInputEditText) findViewById(R.id.text_email);
        TIhomepage =(TextInputLayout)  findViewById(R.id.layout_homepage);
        Ethomepage =(TextInputEditText)  findViewById(R.id.text_homepage);
        TIabout =(TextInputLayout)  findViewById(R.id.layout_about);
        Etabout =(TextInputEditText)  findViewById(R.id.text_about);
        TIpass = findViewById(R.id.layout_password);
        Etpass = findViewById(R.id.text_password);
        TIconfpass = findViewById(R.id.layout_confirm_password);
        Etconfpass = findViewById(R.id.text_confirm_password);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = TIpass.getEditText().getText().toString();
                String confpass = TIconfpass.getEditText().getText().toString();

                boolean check = validateinfo(password, confpass);

                if(check==true) {
                    Toast.makeText(getApplicationContext(), "Data is Valid", Toast.LENGTH_SHORT).show();
                    avatarImage.setDrawingCacheEnabled(true);
                    Bitmap b = avatarImage.getDrawingCache();
                    Intent move = new Intent(RegisterActivity.this, ProfileActivity.class);
                    move.putExtra("fullname", TIfullname.getEditText().getText().toString());
                    move.putExtra("email", TIemail.getEditText().getText().toString());
                    move.putExtra("homepage", TIhomepage.getEditText().getText().toString());
                    move.putExtra("about", TIabout.getEditText().getText().toString());
                    move.putExtra("Bitmap", b);
                    startActivity(move);
                }else{
                    Toast.makeText(getApplicationContext(), "Sorry check password again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Boolean validateinfo(String password, String confpass) {
        if(!password.equals(confpass)) {
            Etconfpass.requestFocus();
            Etconfpass.setError("Password Unmatches");
            return false;
        }else{
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (data != null) {
                try {
                    Uri imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    avatarImage.setImageBitmap(bitmap);

                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }
}

