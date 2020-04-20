package uth.raad.raad_news;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private RecyclerView blogItems;

    private DatabaseReference db;
    private int id = 0;

    int txtSize = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("راد نیوز Raad news");

        db = FirebaseDatabase.getInstance().getReference().child("News_Box");

        blogItems = (RecyclerView) findViewById(R.id.blogList);
        blogItems.setHasFixedSize(true);
        blogItems.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<NewsAdapter, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<NewsAdapter, BlogViewHolder>(
                NewsAdapter.class,
                R.layout.blog_row,
                BlogViewHolder.class,
                db
        ) {


            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, NewsAdapter model, int position) {
                //viewHolder.getItemId(position)
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setDate(model.getDate());
                viewHolder.setImage(getApplicationContext(), model.getImage());


            }
        };
        blogItems.setAdapter(firebaseRecyclerAdapter);
    }


    // start of blog_items list
    static TextView post_title;
    static TextView post_dsc;
    static TextView post_date;
    public static class BlogViewHolder extends RecyclerView.ViewHolder{
        View mView;

    public BlogViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
  }



    public void setTitle(String title){
         post_title = (TextView) mView.findViewById(R.id.post_title);
        post_title.setText(title);
    }
    public void setDescription(String description){
        post_dsc = (TextView) mView.findViewById(R.id.post_dsc);
        post_dsc.setText(description);
    }
    public void setDate(String date){
        post_date= (TextView) mView.findViewById(R.id.post_date);
        post_date.setText(date);
    }
    public void setImage(final Context ctx, String image){
        final ImageView post_image = (ImageView) mView.findViewById(R.id.post_img);
        Picasso.with(ctx).load(image).into(post_image);
    }

    }



    // option bor top right cornor of screen codes
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == R.id.addPost){
//            startActivity(new Intent(MainActivity.this, PostActivity.class));
//        }
//        else

        if(item.getItemId() == R.id.contact){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            mBuilder.setIcon(R.mipmap.me);
            mBuilder.setTitle(R.string.dailog_title);
            mBuilder.setMessage(R.string.dialog_msg);
            mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = mBuilder.create();
            alertDialog.show();

        }
        else if(item.getItemId() == R.id.about){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            mBuilder.setIcon(R.mipmap.ic_info_outline_black_24dp);
            mBuilder.setTitle(R.string.about_app_title);
            mBuilder.setMessage(R.string.about_app_text );
            mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = mBuilder.create();
            alertDialog.show();
        }
        else if(item.getItemId() == R.id.quite){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            mBuilder.setIcon(R.mipmap.ic_exit_to_app_black_24dp);
            mBuilder.setTitle("Do you want to quite?");
            mBuilder.setCancelable(true);
            mBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            mBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = mBuilder.create();
            alertDialog.show();

        }/*else if (item.getItemId() == R.id.zi){
            txtSize = txtSize + 5;
            post_dsc.setTextSize(TypedValue.COMPLEX_UNIT_PX, txtSize);

        }else if (item.getItemId() == R.id.zo){
            txtSize = txtSize - 5;
            post_dsc.setTextSize(TypedValue.COMPLEX_UNIT_PX, txtSize);
        }*/

        return super.onOptionsItemSelected(item);
    }


}
