package com.andressantibanez.lienzo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class LienzoActivity extends AppCompatActivity {

    private static final String IMAGES_PATHS = "images_paths";
    private static final String CURRENT_PAGE = "initial_page";

    List<String> mImagesPaths;
    int mCurrentPage;

    ViewPager mPager;

    public static Intent launchIntent(Context context, ArrayList<String> imagesPaths, int initialPage) {
        Intent intent = new Intent(context, LienzoActivity.class);
        intent.putStringArrayListExtra(IMAGES_PATHS, imagesPaths);
        intent.putExtra(CURRENT_PAGE, initialPage);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lienzo);

        mImagesPaths = getIntent().getStringArrayListExtra(IMAGES_PATHS);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new LienzoPagerAdapter(getSupportFragmentManager(), mImagesPaths));

        if (savedInstanceState == null)
            mCurrentPage = getIntent().getIntExtra(CURRENT_PAGE, 0);
        else
            mCurrentPage = savedInstanceState.getInt(CURRENT_PAGE);

        mPager.setCurrentItem(mCurrentPage, true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_PAGE, mCurrentPage);
    }


    /**
     * PagerAdapter implementation
     */
    public static class LienzoPagerAdapter extends FragmentStatePagerAdapter {

        List<String> mImagesPaths;

        public LienzoPagerAdapter(FragmentManager fm, List<String> imagesPaths) {
            super(fm);
            mImagesPaths = imagesPaths;
        }

        @Override
        public Fragment getItem(int position) {
            return LienzoFragment.newInstance(mImagesPaths.get(position));
        }

        @Override
        public int getCount() {
            return mImagesPaths.size();
        }
    }


    /**
     * Fragment for pager
     */
    public static class LienzoFragment extends Fragment {

        private static final String IMAGE_PATH = "image_path";

        private String mImagePath;

        public static LienzoFragment newInstance(String imagePath) {
            LienzoFragment fragment = new LienzoFragment();
            Bundle args = new Bundle();
            args.putString(IMAGE_PATH, imagePath);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mImagePath = getArguments().getString(IMAGE_PATH);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_lienzo, container, false);

            final SubsamplingScaleImageView imagePlaceholder = (SubsamplingScaleImageView) view.findViewById(R.id.image_placeholder);
            Picasso.with(getContext())
                    .load(mImagePath)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            imagePlaceholder.setImage(ImageSource.bitmap(bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });


            return view;
        }

    }

}
