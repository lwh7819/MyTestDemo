package com.cd.mytestdemo.glide;

import android.content.Context;
import android.graphics.*;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.cd.mytestdemo.R;

/**
 * Created by lv.weihao on 2018/5/3.
 */
public class CircleCrop extends BitmapTransformation {
    private Context context;

    public CircleCrop(Context context) {
        super(context);
        this.context = context;
    }

    public CircleCrop(BitmapPool bitmapPool) {
        super(bitmapPool);
    }

    @Override
    public String getId() {
        return "com.cd.mytestdemo.glide.CircleCrop";
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        int diameter = Math.min(toTransform.getWidth(), toTransform.getHeight());
        Bitmap toReuse = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        Bitmap result;
        if (toReuse != null) {
            result = toReuse;
        } else {
            result = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
        }

        int dx = (toTransform.getWidth() - diameter) / 2;
        int dy = (toTransform.getHeight() - diameter) / 2;
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(toTransform, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        if (dx != 0 || dy != 0) {
            Matrix matrix = new Matrix();
            matrix.setTranslate(-dx, -dy);
            shader.setLocalMatrix(matrix);
        }
        paint.setShader(shader);
        paint.setAntiAlias(true);
        float radius = diameter / 2;
        canvas.drawCircle(radius, radius, radius, paint);

        // 设置参数
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
        BitmapFactory.decodeResource(context.getResources(), R.drawable.wan_vip, options);
        int height = options.outHeight;
        int width= options.outWidth;
        int minLen = Math.min(height, width); // 原图的最小边长
        int r = (int) (radius - (Math.sqrt(3) * radius / 2));
        int inSampleSize = 3; // 默认像素压缩比例，压缩为原图的1/2
        options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
        options.inSampleSize = inSampleSize; // 设置为刚才计算的压缩比例
        Bitmap mLever = BitmapFactory.decodeResource(context.getResources(), R.drawable.wan_vip, options);
        Paint paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(Color.YELLOW);
        canvas.drawCircle(radius + (radius / 2), (float) (radius + (Math.sqrt(3) * radius / 2)), (float) (radius - (Math.sqrt(3) * radius / 2)), paint1);
        canvas.drawBitmap(mLever, radius + (radius / 2) - 30, (float) (radius + (Math.sqrt(3) * radius / 2)) - 30, paint1);
        if (toReuse != null && !pool.put(toReuse)) {
            toReuse.recycle();
        }
        return result;
    }
}
