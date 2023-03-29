package caixin.android.com.utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.caixin.huanyu.R;

import java.io.File;

import caixin.android.com.Application;


/**
 * Created by cxf on 2017/8/9.
 */

public class ImgLoader {
    private static RequestManager sManager;

    static {
        sManager = Glide.with(Application.getInstance());
    }

    public static void normalGlide(ImageView imageview, String url, int defaultImg) {
        RequestOptions requestOptions = new RequestOptions()
//                .placeholder(new ColorDrawable(Color.BLACK))
//                .error(new ColorDrawable(Color.BLUE))
//                .fallback(new ColorDrawable(Color.RED))
                .fitCenter()
                .error(defaultImg)
//                .skipMemoryCache(true)
//                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImg)
//                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(Application.getInstance())
                .load(url)
                .apply(requestOptions)
                .into(imageview);
    }


    public static void normalGlide(ImageView imageview, File url, int defaultImg) {
        RequestOptions requestOptions = new RequestOptions()
//                .placeholder(new ColorDrawable(Color.BLACK))
//                .error(new ColorDrawable(Color.BLUE))
//                .fallback(new ColorDrawable(Color.RED))
                .fitCenter()
                .error(defaultImg)
//                .skipMemoryCache(true)
//                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImg)
//                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(Application.getInstance())
                .load(url)
                .apply(requestOptions)
                .into(imageview);
    }


    public static void normalGlideSource(ImageView imageview, int url, int defaultImg) {
        RequestOptions requestOptions = new RequestOptions()
//                .placeholder(new ColorDrawable(Color.BLACK))
//                .error(new ColorDrawable(Color.BLUE))
//                .fallback(new ColorDrawable(Color.RED))
                .fitCenter()
                .error(defaultImg)
//                .skipMemoryCache(true)
//                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImg)
//                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(Application.getInstance())
                .load(url)
                .apply(requestOptions)
                .into(imageview);
    }

    public static void GlideLoad(ImageView imageview, String url, int defaultImg) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(new ColorDrawable(Color.BLACK))
                .error(new ColorDrawable(Color.BLUE))
                .fallback(new ColorDrawable(Color.RED))
                .fitCenter()
                .error(defaultImg)
                .skipMemoryCache(true)
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImg)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL);


        if (url != null && url.endsWith("gif")) {
            Glide.with(Application.getInstance())
                    .load(url)
                    .apply(requestOptions)
                    .into(imageview);
        } else {
            Glide.with(Application.getInstance())
                    .load(url)
                    .apply(requestOptions)
                    .into(imageview);
        }
    }

    public static void loadGif(ImageView imageview, String url){
        if (url.endsWith("gif")){
            RequestOptions options = new RequestOptions()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.DATA);
            Glide.with(Application.getInstance())
                    .asGif()
                    .load(url)
                    .apply(options)
                    .into(imageview);
        }else {
            GlideLoadCircle(imageview, url,R.mipmap.default_avatar);
        }

    }

    public static void GlideLoadCircle(ImageView imageview, String url, int defaultImg) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(new ColorDrawable(Color.BLACK))
                .error(new ColorDrawable(Color.BLUE))
                .fitCenter()
                .transform(new GlideCircleTransform(Application.getInstance()))
                .error(defaultImg)
                .skipMemoryCache(true)
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImg)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(Application.getInstance())
                .load(url)
                .apply(requestOptions)
                .into(imageview);
    }

    public static void GlideLoadCircle(ImageView imageview, File url, int defaultImg) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(new ColorDrawable(Color.BLACK))
                .error(new ColorDrawable(Color.BLUE))
                .fitCenter()
                .transform(new GlideCircleTransform(Application.getInstance()))
                .error(defaultImg)
                .skipMemoryCache(true)
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImg)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(Application.getInstance())
                .load(url)
                .apply(requestOptions)
                .into(imageview);
    }

    public static void ChatRoomRoundCorners(String url, ImageView imageview, int defaultImg) {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(18);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .error(defaultImg);
        Glide.with(Application.getInstance()).load(url).apply(options).into(imageview);
    }

    public static void ChatRoomRoundCorners(int url, ImageView imageview, int defaultImg) {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(18);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .error(defaultImg);

        Glide.with(Application.getInstance()).load(url).apply(options).into(imageview);
    }

    public static void GlideLoadRoundedCorners(String url, ImageView imageview, int defaultImg) {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(18);
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .placeholder(defaultImg)
                .error(defaultImg);

        Glide.with(Application.getInstance()).load(url).apply(options).into(imageview);

        /*     Glide.with(Application.getInstance()).load(url).apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).into(imageview);//四周都是圆角的圆角矩形图片。
         */
    }

    public static void GlideLoadRoundedCorners(File url, ImageView imageview, int defaultImg) {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(18);
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .placeholder(defaultImg)
                .error(defaultImg);

        Glide.with(Application.getInstance()).load(url).apply(options).into(imageview);
    }

    public static void GlideLoadRoundedCorners(int url, ImageView imageview, int defaultImg) {

        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(18);
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .placeholder(defaultImg)
                .error(defaultImg);

        Glide.with(Application.getInstance()).load(url).apply(options).into(imageview);


        /*     Glide.with(Application.getInstance()).load(url).apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).into(imageview);//四周都是圆角的圆角矩形图片。
         */
    }
}
