package uth.raad.raad_news;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class PostActivity extends AppCompatActivity {
    private ImageButton imgBtn;
    private EditText postTitle;
    private EditText postDsc;
    private EditText postDate;
    private Button submitPost;
    private Uri GalleryImage = null;
    private static final int GALLERY_REQUEST=1;
    private StorageReference DBStorage;
    private DatabaseReference DB;
    private ProgressDialog postProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        DBStorage = FirebaseStorage.getInstance().getReference();
        DB = FirebaseDatabase.getInstance().getReference().child("News_Box");

        imgBtn = (ImageButton) findViewById(R.id.selectImg);
        postTitle = (EditText) findViewById(R.id.enterName);
        postDsc = (EditText) findViewById(R.id.enterDsc);
        postDate = (EditText) findViewById(R.id.enterDate);
        submitPost = (Button) findViewById(R.id.submitPost);

        postProgress = new ProgressDialog(this);

        imgBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        // when submit button pressed
        submitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });
    }
    // start of posting method
    private void startPosting() {
        postProgress.setMessage("Posting to NewsAdapter ...");


        final String title_val = postTitle.getText().toString().trim();
        final String dsc_val = postDsc.getText().toString().trim();
        final String date_val = postDate.getText().toString().trim();
        // start posting to database
        if(!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(dsc_val) && !TextUtils.isEmpty(date_val) && GalleryImage != null){
            // when users enter something or make fill all empty places then progress showed
            postProgress.show();

            StorageReference filePath = DBStorage.child("News_Imagaes").child(GalleryImage.getLastPathSegment());
            filePath.putFile(GalleryImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    DatabaseReference newPost = DB.push();
                    newPost.child("title").setValue(title_val);
                    newPost.child("description").setValue(dsc_val);
                    newPost.child("date").setValue(date_val);
                    newPost.child("image").setValue(downloadUrl.toString());

                    //postProgress.dismiss();
                    //startActivity(new Intent(PostActivity.this, MainActivity.class));
                    finish();
                }
            });
        }
    }

    //selectd image from gallery showed on assigned button;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
          GalleryImage = data.getData();
          imgBtn.setImageURI(GalleryImage);
        }
    }
}
