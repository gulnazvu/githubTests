package com.example.myproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegisterComplaintActivity extends AppCompatActivity {
    String[] items = {"Abbottabad","Bajaur", "Bannu", "Batagram","Buner","Charsadda","Dera Ismail Khan","Hangu",
            "Haripur","Karak","Khyber","Kohat","Kolai Pallas","Kurram","Lakki Marwat","Lower Chitral","Lower Dir",
            "Lower Kohistan", "Malakand","Mansehra","Mardan","Mohmand","North Waziristan","Nowshera","Orakzai",
            "Peshawar", "Shangla","South Waziristan","Swabi","Swat","Tank","Tor Ghar","Upper Chitral","Upper Dir","Upper Kohistan"};

    String[] complaintCategory ={"Unlawful / Unnecessary arrest / Detention",
            "Discriminatory behaviour","Irregularity in relation to evidence or perjury","Corrupt Practice","Mishandling of Property","Other Neglect or Failure in Duty",
            "Serious Non-Sexual assault","Sexual Assault","Other Assault","Oppressive conduct or Harassment",
            "Other Irregularity in Procedure","Incivility, Impoliteness, Intolerance","Traffic Irregularity","Other","Improper Disclosure of Info"

    };
    AutoCompleteTextView selectDistrict, selectCompliant;
    ArrayAdapter<String> adapterItems, adaptItem;
    TextView attachment;
    ImageView imagePreview;
    private Uri filePath;
    private final int PICK_FILE_REQUEST = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_complaint);

        selectDistrict = findViewById(R.id.district);
        selectCompliant = findViewById(R.id.complaint);
        attachment = findViewById(R.id.upload);
        imagePreview = findViewById(R.id.myImage);

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, items);
        selectDistrict.setAdapter(adapterItems);
        adaptItem = new ArrayAdapter<String>(this, R.layout.list_item, complaintCategory);
        selectCompliant.setAdapter(adaptItem);

        selectDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

            }
        });
        selectCompliant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemtwo = parent.getItemAtPosition(position).toString();

            }
        });

        attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


    }

    private void selectImage() {
        final CharSequence[] options = { "Picture", "Video","Audio", "Pdf","Cancel" };
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Attach file");
        alertDialogBuilder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Picture"))
                {
                    SelectImage();
                }
                else if (options[item].equals("Video"))
                {
                    SelectVideo();
                }
                else if (options[item].equals("Audio")) {
                    SelectAudio();
                }
                else if (options[item].equals("Pdf")) {
                    SelectPdf();
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        alertDialogBuilder.show();
    }

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivity(intent);

    }
    private void SelectVideo() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        startActivity(intent);
    }
    private void SelectAudio() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        startActivity(intent);
    }
    private void SelectPdf() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            try {
                final Uri imageUri = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imagePreview.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            filePath = data.getData();
        }
    }
}