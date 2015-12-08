package com.andressantibanez.lienzo;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by Andrés on 12/7/15.
 */
public class LienzoView extends FrameLayout implements LienzoAdapter.LienzoAdapterCallbacks {


    //Members
    ArrayList<String> mImagesPaths;
    boolean mIsVerticalGallery;
    int mBackgroundColor;


    //Controls
    RecyclerView mGalleryView;
    ProgressBar mProgressBar;


    /**
     * Constructors
     */
    public LienzoView(Context context) {
        super(context);
        init(null);
    }

    public LienzoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LienzoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    /**
     * Save state
     */
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        SavedState savedState = new SavedState(superState);
        savedState.setImagesPaths(mImagesPaths);
        savedState.setVisibility(getVisibility());

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());

        ArrayList<String> imagesPaths = savedState.getImagesPaths();
        for (String imagePath :
                imagesPaths) {
            addImage(imagePath);
        }

        loadImages();

        setVisibility(savedState.getVisibility() == View.VISIBLE ? View.VISIBLE : View.GONE);
    }

    protected static class SavedState extends BaseSavedState {
        private ArrayList<String> mImagesPaths;
        private int mVisibility;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel in) {
            super(in);
            mImagesPaths = in.readArrayList(String.class.getClassLoader());
            mVisibility = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeList(mImagesPaths);
            out.writeInt(mVisibility);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        public void setImagesPaths(ArrayList<String> imagesPaths) {
            mImagesPaths = imagesPaths;
        }
        public ArrayList<String> getImagesPaths() {
            return mImagesPaths;
        }

        public void setVisibility(int visibility) {
            mVisibility = visibility;
        }
        public int getVisibility() {
            return mVisibility;
        }
    }


    /**
     * Custom implementation
     */
    public void init(AttributeSet attributeSet) {
        //Empty images paths
        mImagesPaths = new ArrayList<>();


        //Default orientation and background
        mIsVerticalGallery = true;
        mBackgroundColor = getResources().getColor(R.color.default_background);


        //Check for custom attributes
        if(attributeSet != null) {

            TypedArray a = getContext().getTheme().obtainStyledAttributes(attributeSet, R.styleable.LienzoView, 0, 0);

            //Background color
            mBackgroundColor = a.getColor(R.styleable.LienzoView_lienzo_background, mBackgroundColor);

            //Orientation
            int orientation = a.getInt(R.styleable.LienzoView_lienzo_orientation, 1);
            mIsVerticalGallery = orientation == 1;

            a.recycle();
        }


        //Inflate view and get controls
        if (mIsVerticalGallery)
            LayoutInflater.from(getContext()).inflate(R.layout.lienzo_layout_vertical, this, true);
        else
            LayoutInflater.from(getContext()).inflate(R.layout.lienzo_layout_horizontal, this, true);

        mGalleryView = (RecyclerView) findViewById(R.id.gallery);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);


        //Set background
        mGalleryView.setBackgroundColor(mBackgroundColor);


        //Setup Gallery
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(mIsVerticalGallery ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        mGalleryView.setLayoutManager(linearLayoutManager);
        mGalleryView.setAdapter(new LienzoAdapter(mImagesPaths, mIsVerticalGallery, this));

    }

    public void addImage(String imageUrl) {
        mImagesPaths.add(imageUrl);
    }

    public void loadImages() {
        mGalleryView.setAdapter(new LienzoAdapter(mImagesPaths, mIsVerticalGallery, this));
        if (mImagesPaths.size() > 0)
            mProgressBar.setVisibility(View.VISIBLE);
        else
            mProgressBar.setVisibility(View.GONE);
    }

    public void clearImages() {
        mImagesPaths = new ArrayList<>();
        loadImages();
    }


    /**
     * Adapter callbacks
     */
    @Override
    public void onImageSuccess() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onImageError() {
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Error al mostrar algunas imágenes. Por favor, inténtelo nuevamente.", Toast.LENGTH_LONG).show();
    }
}
