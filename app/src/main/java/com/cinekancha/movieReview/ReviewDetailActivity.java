package com.cinekancha.movieReview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.entities.model.ReviewData;
import com.cinekancha.utils.Constants;
import com.squareup.picasso.Picasso;

public class ReviewDetailActivity extends AppCompatActivity {
    private TextView txtReview;
    private ImageView imgReview;
    private ReviewData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);
        data = getIntent().getExtras().getParcelable("review");
        initToolbar();
        findViews();
    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(data.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    private void findViews() {
        txtReview = findViewById(R.id.txtReview);
        imgReview = findViewById(R.id.imgReview);
        txtReview.setText(data.getReview());
        Picasso.with(this).load(Constants.imageUrl + data.getFeaturedImage()).placeholder(R.drawable.placeholder_movie).into(imgReview);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}
