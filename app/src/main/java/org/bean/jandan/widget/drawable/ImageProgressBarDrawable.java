package org.bean.jandan.widget.drawable;

import android.content.res.Resources;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.text.TextPaint;
import android.text.TextUtils;

import org.bean.jandan.R;
import org.bean.jandan.common.util.ColorUtil;

import java.util.ArrayList;

/**
 * Created by liuyulong@yixin.im on 2015/5/4.
 */
public class ImageProgressBarDrawable extends Drawable implements Animatable, Runnable {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private TextPaint mTextPaint;

    private static final int BLOCK_SIZE = 5;
    private static final int MAX_LEVEL = 10000;
    private static final int BLOCK_LEVEL = MAX_LEVEL / BLOCK_SIZE;
    private static final int CENTER_Y_SPACE = 10;
    private static final int ROUND_RECT_RADIUS = 5;
    private static final int BLOCK_ANIMATION_DELAY = 100;
    private static final int BLING_ANIMATION_DURATION = 500;

    private ArrayList<Block> mBlocks = new ArrayList<>(BLOCK_SIZE);

    private Camera mCamera;
    private Resources mResources;

    private int mSpace;
    private int mBlockWidth;
    private int mBlockHeight;
    private float mTranslateXPercentage;

    private int mColor;
    private int[] mTextColors;
    private String mLoadingText;
    private float mTextSize = 18;

    private Shader mTextShader;
    private Shader mLineShader;
    private Rect mLineRect;
    private Matrix mTextMatrix = new Matrix();

    private boolean mRunning;
    private long mAnimationStartTime;

    public ImageProgressBarDrawable() {}

    public ImageProgressBarDrawable(Resources resources) {
        this.mResources = resources;

        setColor(mResources.getColor(R.color.loading_draw_color));
        setTextColors(mResources.getIntArray(R.array.rainbow));
        setLoadingText(mResources.getString(R.string.loading));
        mTextSize = mResources.getDimension(R.dimen.loading_image_progress_text_size);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        Rect rect = getBounds();
        int width = rect.width();
        int height = rect.height();

        mBlockHeight = height / 10;
        mBlockWidth = mSpace = width / 30;
        mBlockHeight = mBlockWidth = mSpace = Math.min(mBlockHeight, mBlockWidth);
        initBlocks();
    }

    private void initBlocks() {
        if (mBlocks.size() != 0) {
            return;
        }
        mCamera = new Camera();
        Rect rect = getBounds();
        int width = rect.width();
        int offsetX = (width - mBlockWidth * (2 * BLOCK_SIZE - 1)) / 2;
        int offsetY = rect.centerY() - CENTER_Y_SPACE - mBlockHeight;
        for (int i = 0; i < BLOCK_SIZE; i++) {
            int left = offsetX + mBlockWidth * i + mSpace * i;
            int top = offsetY;
            Block block = new Block(left, top, BLOCK_LEVEL * (1 + i), BLOCK_ANIMATION_DELAY * i);
            mBlocks.add(block);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        boolean firstLess = false;
        mPaint.reset();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        for (Block block : mBlocks) {
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.save();
            canvas.concat(block.getMatrix());
            canvas.drawRoundRect(block.getRectF(), ROUND_RECT_RADIUS, ROUND_RECT_RADIUS, mPaint);
            mPaint.setStyle(Paint.Style.FILL);
            int currentLevel = getLevel();
            if (currentLevel > block.level) {
                canvas.drawRoundRect(block.getRectF(), ROUND_RECT_RADIUS, ROUND_RECT_RADIUS, mPaint);
            } else if (currentLevel < block.level && !firstLess) {
                firstLess = true;
                int diff = block.level - currentLevel;
                canvas.drawRoundRect(block.getRectF(diff), ROUND_RECT_RADIUS, ROUND_RECT_RADIUS, mPaint);
            }
            canvas.restore();
        }

        drawLine(canvas);
        drawLoadingText(canvas);
    }

    private void drawLine(Canvas canvas) {
        Rect rect = getCenterLineRect();
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        if (mLineShader == null) {
            int colors[] = new int[]{ColorUtil.changeColorAlpha(0, mColor), ColorUtil.changeColorAlpha(255, mColor),
                    ColorUtil.changeColorAlpha(0, mColor)};
            float[] options = {0, 0.5f, 1};
            mLineShader = new LinearGradient(rect.left, 0, rect.right, 0, colors, options, Shader.TileMode.MIRROR);
        }
        mPaint.setShader(mLineShader);
        canvas.drawRect(rect, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    public void setColor(int color) {
        mColor = color;
    }

    public void setTextColors(int[] colors) {
        this.mTextColors = colors;
    }

    public void setLoadingText(String loadingText) {
        this.mLoadingText = loadingText;
    }

    @Override
    public int getOpacity() {
        return 0;
    }

    @Override
    public void start() {

        if (!isRunning()) {
            mAnimationStartTime = SystemClock.uptimeMillis();
            run();
        }
    }

    @Override
    public void stop() {

        if (isRunning()) {
            unscheduleSelf(this);
        }
    }

    @Override
    public boolean isRunning() {
        return mRunning;
    }

    @Override
    public void unscheduleSelf(Runnable what) {
        mRunning = false;
        super.unscheduleSelf(what);
    }

    @Override
    public void run() {
        mRunning = true;
        for (Block block : mBlocks) {
            if (block.meetConditions()) {
                block.updateDegreeX(15);
            }
        }

        mTranslateXPercentage += 0.1f;
        if (mTranslateXPercentage >= 100) {
            mTranslateXPercentage = 0;
        }

        invalidateSelf();
        scheduleSelf(this, SystemClock.uptimeMillis() + BLOCK_ANIMATION_DELAY);
    }

    class Block {
        int left;
        int top;
        int right;
        int bottom;
        int level;
        int delay;
        int degreeX = 0;

        public Block(int left, int top, int level, int delay) {
            this.left = left;
            this.top = top;
            this.right = left + mBlockWidth;
            this.bottom = top + mBlockHeight;
            this.level = level;
            this.delay = delay;
        }

        public Rect getRect() {
            return new Rect(left, top, right, bottom);
        }

        public RectF getRectF() {
            return new RectF(getRect());
        }

        public RectF getRectF(int diff) {
            return new RectF(left, bottom - (getHeight() / BLOCK_LEVEL) * (BLOCK_LEVEL - diff), right, bottom);
        }

        public int getHeight() {
            return bottom - top;
        }

        public int centerX() {
            return getRect().centerX();
        }

        public int centerY() {
            return getRect().centerY();
        }

        public int getDegreeX() {
            return degreeX;
        }

        public void updateDegreeX(int degreeX) {
            this.degreeX += degreeX;
            if (this.degreeX > 360) {
                this.degreeX = 0;
            }
        }

        public boolean meetConditions() {
            return delay < SystemClock.uptimeMillis() - mAnimationStartTime;
        }

        public Matrix getMatrix() {
            mCamera.save();
            Matrix matrix = new Matrix();
            mCamera.rotate(getDegreeX(), 0, 0);
            mCamera.getMatrix(matrix);
            matrix.preTranslate(-centerX(), -centerY());
            matrix.postTranslate(centerX(), centerY());
            mCamera.restore();
            return matrix;
        }

        @Override
        public String toString() {
            return "Block{" +
                    "left=" + left +
                    ", top=" + top +
                    ", right=" + right +
                    ", bottom=" + bottom +
                    ", level=" + level +
                    ", delay=" + delay +
                    ", degreeX=" + degreeX +
                    '}';
        }
    }

    private Rect getCenterLineRect() {
        if (mLineRect == null) {
            Rect rect = getBounds();
            int width = rect.width();
            int offsetX = (width - mBlockWidth * (2 * BLOCK_SIZE - 1)) / 2 / 2;
            int left = offsetX;
            int top = rect.centerY() - 2;
            int right = width - offsetX;
            int bottom = rect.centerY() + 2;
            mLineRect =  new Rect(left, top, right, bottom);
        }
        return mLineRect;
    }

    private void drawLoadingText(Canvas canvas) {
        if (TextUtils.isEmpty(mLoadingText)) {
            return;
        }
        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        float width = mTextPaint.getTextSize() * mTextColors.length;
        if (mTextShader == null) {
            mTextShader = new LinearGradient(0, 0, 0, width, mTextColors, null,
                    Shader.TileMode.MIRROR);
        }
        mTextMatrix.reset();
        mTextMatrix.setRotate(90);
        mTextMatrix.postTranslate(width * mTranslateXPercentage, 0);
        mTextShader.setLocalMatrix(mTextMatrix);
        mTextPaint.setShader(mTextShader);
        Rect rect = getBounds();
        float textWidth = mTextPaint.measureText(mLoadingText);
        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        float x = (rect.width() - textWidth) / 2;
        float y = rect.centerY() + CENTER_Y_SPACE - metrics.ascent;
        canvas.drawText(mLoadingText, x, y, mTextPaint);
    }
}
