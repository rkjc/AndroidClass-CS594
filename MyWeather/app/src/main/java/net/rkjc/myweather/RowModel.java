package net.rkjc.myweather;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by rkjcx on 6/1/2015.
 */

public class RowModel {
    private String text; //ether the whole weather status is pre-built, or need to add more Strings
    //need an image locator or Drawable
    private Drawable pict;


    RowModel(String text, Drawable pic){
        this.text = text;
        this.pict = pic;
    }

    public Drawable getImage(){
        return pict;
    }

    public void setImage(Drawable pic){
        this.pict = pic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}