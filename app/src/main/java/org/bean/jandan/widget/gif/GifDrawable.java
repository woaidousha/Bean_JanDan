package org.bean.jandan.widget.gif;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;

import org.bean.jandan.widget.gif.GifImage;

import java.io.IOException;
import java.io.InputStream;

public class GifDrawable extends Drawable {

    protected final Paint mPaint = new Paint(Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG);
    private static final Handler UI_HANDLER = new Handler(Looper.getMainLooper());

    private float mSx = 1f;
    private float mSy = 1f;
    private boolean mApplyTransformation;
    private final Rect mDstRect = new Rect();

    private GifImage image;

    private boolean pause;

    private int currentIndex = -1;

    private final Runnable mInvalidateTask = new Runnable() {
        @Override
        public void run() {
            if (image == null) {
                return;
            }
            if (nextFrame() != null) {
                invalidateSelf();
            } else {
                UI_HANDLER.postDelayed(this, 20);
            }
        }
    };

    /**
     * Creates drawable from resource.
     *
     * @param res Resources to read from
     * @param id  resource id
     * @throws android.content.res.Resources.NotFoundException    if the given ID does not exist.
     * @throws java.io.IOException          when opening failed
     * @throws NullPointerException if res is null
     */
    public GifDrawable(Resources res, int id) throws NotFoundException, IOException {
        this(res.openRawResourceFd(id));
    }

    /**
     * Creates drawable from asset.
     *
     * @param assets    AssetManager to read from
     * @param assetName name of the asset
     * @throws java.io.IOException          when opening failed
     * @throws NullPointerException if assets or assetName is null
     */
    public GifDrawable(AssetManager assets, String assetName) throws IOException {
        this(assets.openFd(assetName));
    }

    /**
     * Constructs drawable from given file path.<br>
     * Only metadata is read, no graphic data is decoded here.
     * In practice can be called from main thread. However it will violate
     * {@link android.os.StrictMode} policy if disk reads detection is enabled.<br>
     *
     * @param filePath path to the GIF file
     * @throws java.io.IOException          when opening failed
     * @throws NullPointerException if filePath is null
     */
    public GifDrawable(String filePath) throws IOException {
        if (filePath == null) {
            throw new NullPointerException("Gif file path is null");
        }

        image = new GifImage(filePath);
    }

    /**
     * Constructs drawable from InputStream.
     * InputStream must support marking, IllegalArgumentException will be thrown otherwise.
     *
     * @param is
     * @throws java.io.IOException              when opening failed
     * @throws IllegalArgumentException if stream does not support marking
     * @throws NullPointerException     if stream is null
     */
    public GifDrawable(InputStream is) throws IOException {
        if (is == null) {
            throw new NullPointerException("Gif input stream is null");
        }
        if (!is.markSupported())
            throw new IllegalArgumentException("InputStream does not support marking");

        image = new GifImage(is);
    }

    /**
     * Constructs drawable from AssetFileDescriptor.
     * Convenience wrapper for {@link GifDrawable#GifDrawable(java.io.FileDescriptor)}
     *
     * @param afd source
     * @throws NullPointerException if afd is null
     * @throws java.io.IOException          when opening failed
     */
    public GifDrawable(AssetFileDescriptor afd) throws IOException {
        if (afd == null)
            throw new NullPointerException("Source is null");

        image = new GifImage(afd);
    }

    public void recycle() {
        if (image != null) {
            image.recycle();
            image = null;
        }
    }

    public void pause() {
        pause = true;
    }

    public void resume() {
        if (pause) {
            pause = false;
            UI_HANDLER.post(mInvalidateTask);
        }
    }

    public void replay() {
        currentIndex = -1;
        UI_HANDLER.post(mInvalidateTask);
    }

    public Bitmap getPreview() {
        int[] bmp = image.getPreview();
        if (bmp != null) {
            return Bitmap.createBitmap(bmp, getIntrinsicWidth(), getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        return null;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mApplyTransformation = true;
    }

    @Override
    public void draw(Canvas canvas) {
        if (mApplyTransformation) {
            mDstRect.set(getBounds());
            mSx = (float) mDstRect.width() / getIntrinsicWidth();
            mSy = (float) mDstRect.height() / getIntrinsicHeight();
            mApplyTransformation = false;
        }

        if (mPaint.getShader() == null) {
            int delay = 20;
            int[] bmp = nextFrame();
            if (bmp != null) {
                canvas.scale(mSx, mSy);
                canvas.drawBitmap(bmp, 0, getIntrinsicWidth(), 0f, 0f, getIntrinsicWidth(), getIntrinsicHeight(), true, mPaint);
                currentIndex = (currentIndex + 1) % image.getFrameCount();
                delay = image.getFrameDelay(currentIndex);
            }
            if (image.getFrameCount() > 1 && !pause)
                UI_HANDLER.removeCallbacks(mInvalidateTask);
            UI_HANDLER.postDelayed(mInvalidateTask, delay);
        } else {
            canvas.drawRect(mDstRect, mPaint);
        }
    }

    private int[] nextFrame() {
        int index = (currentIndex + 1) % image.getFrameCount();
        return image.getFrame(index);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    public int getIntrinsicWidth() {
        return image.getIntrinsicWidth();
    }

    public int getIntrinsicHeight() {
        return image.getIntrinsicHeight();
    }

    @Override
    public int getMinimumWidth() {
        return image.getIntrinsicWidth();
    }

    @Override
    public int getMinimumHeight() {
        return image.getIntrinsicHeight();
    }
}
