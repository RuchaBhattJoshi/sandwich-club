package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //TextView mMainNameTextview;
    ImageView mSandwichImageView;
    TextView mIngredientsTextView;
    TextView mPlaceOfOriginTextView;
    TextView mPlaceOfOriginLabelTextView;
    TextView mAlsoKnownAsTextview;
    TextView mAlsoKnownAsLabelTextview;
    TextView mDescriptionTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;

        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

    }

    private void initViews() {

        mSandwichImageView = findViewById(R.id.iv_image);

       // mMainNameTextview = findViewById(R.id.tv_main_name);
        mIngredientsTextView = findViewById(R.id.tv_ingredients);
        mPlaceOfOriginTextView = findViewById(R.id.tv_origin);
        mPlaceOfOriginLabelTextView = findViewById(R.id.tv_place_of_origin_label);
        mAlsoKnownAsTextview = findViewById(R.id.tv_also_known_as);
        mAlsoKnownAsLabelTextview = findViewById(R.id.tv_known_as_label);
        mDescriptionTextview = findViewById(R.id.tv_description);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        setTitle(sandwich.getMainName());

        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(mSandwichImageView);


        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            mPlaceOfOriginLabelTextView.setVisibility(View.GONE);
            mPlaceOfOriginTextView.setVisibility(View.GONE);
        } else {
            mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getAlsoKnownAs().isEmpty()) {
            mAlsoKnownAsLabelTextview.setVisibility(View.GONE);
            mAlsoKnownAsTextview.setVisibility(View.GONE);
        } else {
            List<String> aka = sandwich.getAlsoKnownAs();
            String aka_str = TextUtils.join(", ", aka);
            mAlsoKnownAsTextview.setText(aka_str);
        }

        List<String> ing = sandwich.getIngredients();
        String ing_str = TextUtils.join(", ", ing);
        mIngredientsTextView.setText(ing_str);

        mDescriptionTextview.setText(sandwich.getDescription());


    }
}
