package org.bean.jandan.widget.gif;

import android.content.res.AssetFileDescriptor;

import org.bean.jandan.common.util.DebugLog;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class GifImage {
	
	/**
	 * 每张gif最多占用的的内存大小，默认情况下DEF，内存警告之后变为LOW_MEMORY
	 * 如果总大小本身就小于LOW_MEMORY，缓存所有帧不动
	 */
	private static final int CACHE_SIZE_DEF = 1 * 1024 * 1024;
	private static final int CACHE_SIZE_LOW_MEMORY = 1 * 1024 * 1024;

	private static final float PRELOAD_THRESHOLD = 2/3f;

	private boolean recycled = false;

	private int[] lastRequestedBits;

    private int gifInfoPtr;
    private int loopCount;
    private final int[] metaData = new int[4]; //[w,h,imageCount,errorCode]
    private int[] delays;
    private int[][] cachedFrames; // Uncached frame indexes hold `NSNull`

	private int cacheSize = CACHE_SIZE_DEF;
    private int cacheFrameCount; // 允许缓存的帧数

    private int requestedIndex;
    private int preloadEnd;
    private int cacheBegin;

    private AtomicBoolean loading = new AtomicBoolean(false);
    private boolean cached = false;

    private static final Executor loader = Executors.newSingleThreadExecutor();

    private Runnable loadRunnable = new Runnable() {
		@Override
		public void run() {
            if (!loading.compareAndSet(false, true)) {
                return;
            }

            try {
                load();
            } catch (OutOfMemoryError e) {
                DebugLog.i("gif run out of memory while loading");
                recycle(); // 回收，再也不画了
            }

            loading.set(false);
        }
    };

    private void load() {
        // snapshots
        final int requestEnd = preloadEnd;
        final int currentBegin = cacheBegin;
        final int currentEnd = (cached ? roundEnd(currentBegin + cacheFrameCount) : 1);
        final int[][] mainCache = cachedFrames;
        final int[] bitsCache = lastRequestedBits;
        int count = distance(currentEnd, requestEnd);
        if (count <= 0 || mainCache == null || gifInfoPtr == 0) {
            loading.set(false);
            return;
        }

        // 重用前面的缓存
        int[][] cache = new int[count][];
        for (int i = 0; i < count; ++i) {
            if (cached) {
                int index = roundIndex(i + currentBegin);
                cache[i] = mainCache[index];
                mainCache[index] = null;
            }
            if (cache[i] == null) {
                cache[i] = new int[bitsCache.length];
            }
        }

        for (int i = 0; i < count; ++i) {
            int index = roundIndex(i + currentEnd);
            GifImageNative.seekToFrame(gifInfoPtr, index, bitsCache);
            System.arraycopy(bitsCache, 0, cache[i], 0, bitsCache.length);
        }

        for (int i = 0; i < count; ++i) {
            int index = roundIndex(i + currentEnd);
            mainCache[index] = cache[i];
        }
        if (cached) {
            cacheBegin = roundIndex(currentBegin + count);
        }

        lastRequestedBits = bitsCache;
        cached = true;

        // 如果解开的gif大小比CACHE_SIZE_LOW_MEMORY还小，那么缓存的文件信息就不需要了，释放掉
        if (getFrameCount() == 1 || lastRequestedBits.length * 4 * getFrameCount() <= CACHE_SIZE_LOW_MEMORY) {
            freeFile();
        }
    }

    private Runnable freeRunnable = new Runnable() {
        @Override
        public void run() {
            freeFile();
        }
    };

    public GifImage(String filePath) throws IOException {
    	gifInfoPtr = GifImageNative.openFile(metaData, filePath);
    	init();
    }

    public GifImage(InputStream is) throws IOException {
    	gifInfoPtr = GifImageNative.openStream(metaData, is);
    	init();
    }

    public GifImage(AssetFileDescriptor afd) throws IOException {
        try {
            gifInfoPtr = GifImageNative.openFd(metaData, afd.getFileDescriptor(), afd.getStartOffset());
            init();
        } finally {
            afd.close();
        }
    }

    private void init() throws GifIOException {
        if (metaData[3] != 0) {
            throw new GifIOException(metaData[3]);
        }

    	loopCount = GifImageNative.getLoopCount(gifInfoPtr);
    	cachedFrames = new int[metaData[2]][];
    	cacheBegin = 0;
    	preloadEnd = -1;
    	lastRequestedBits = new int[metaData[0] * metaData[1]];

    	// determine cache size
    	int frameSize = metaData[0] * metaData[1] * 4;
    	cacheFrameCount = cacheSize / frameSize;
    	cacheFrameCount = Math.min(cacheFrameCount, metaData[2]);
    	cacheFrameCount = Math.max(cacheFrameCount, 2); // 最少缓存两帧

    	// delays
    	delays = new int[metaData[2]];
    	for (int i = 0; i < metaData[2]; ++i) {
    		delays[i] = GifImageNative.getDuration(gifInfoPtr, i);
    	}

       // 预加载第一帧
        cachedFrames[0] = new int[lastRequestedBits.length];
        GifImageNative.seekToFrame(gifInfoPtr, 0, cachedFrames[0]);
        System.arraycopy(cachedFrames[0], 0, lastRequestedBits, 0, lastRequestedBits.length);
    }

    public int[] getPreview() {
        if (recycled) {
            return null;
        }
        return cachedFrames[0];
    }

    public int[] getFrame(int index) {
        if (recycled) {
            return null;
        }

        if (index >= metaData[2]) {
            return null;
        }

        requestedIndex = index;

        if (!loading.get() && shouldUpdateCache(index)) {
            loader.execute(loadRunnable);
        }

        return cachedFrames[index];
    }

    public int getIntrinsicWidth() {
        return metaData[0];
    }

    public int getIntrinsicHeight() {
        return metaData[1];
    }

    public void recycle() {
        recycled = true;

        loader.execute(freeRunnable);

        if (cachedFrames != null) {
            for (int i = 0; i < cachedFrames.length; ++i) {
                cachedFrames[i] = null;
            }
        }
        cachedFrames = null;
    }

    public int getFrameCount() {
    	return metaData[2];
    }

    public int getLoopCount() {
    	return loopCount;
    }

    public int getFrameDelay(int index) {
    	if (index < 0 || index >= getFrameCount()) {
    		return 0;
    	}

    	return delays[index];
    }

    public void onLowMemory() {
    	int frameSize = metaData[0] * metaData[1] * 4;
    	if (frameSize * cacheFrameCount <= CACHE_SIZE_LOW_MEMORY) {
    		return;
    	}

    	// 缩减缓存。 native层的解码保存了状态，所以不能缩减还没播放到的那一段数据，只能缩减前半部分
        int newBegin = requestedIndex;
        if (newBegin < cacheBegin) {
            newBegin += getFrameCount();
        }

        int lessCacheCount = CACHE_SIZE_LOW_MEMORY / frameSize;
        int residual = cacheFrameCount - (newBegin - cacheBegin);
        lessCacheCount = Math.max(lessCacheCount, residual);
        for (int i = 0; i < cacheFrameCount - lessCacheCount; ++i) {
            int index = roundIndex(cacheBegin + i);
            cachedFrames[index] = null;
        }

        cacheBegin = roundIndex(cacheBegin + cacheFrameCount - lessCacheCount);
        cacheFrameCount = lessCacheCount;
    }
    
    private int distance(int begin, int end) {
    	return begin <= end ? end - begin : end + getFrameCount() - begin;
    }
    
    private boolean shouldUpdateCache(int requestIndex) {
    	if (!cached) {
    		preloadEnd = cacheFrameCount;
    		return true;
    	} else {
    		if (cacheFrameCount < getFrameCount()) {
    			if (distance(cacheBegin, requestIndex) > cacheFrameCount * PRELOAD_THRESHOLD) {
    				preloadEnd = roundEnd(requestIndex + cacheFrameCount);
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    private int roundEnd(int index) {
    	return index > getFrameCount() ? index - getFrameCount() : index;
    }
    
    private int roundIndex(int index) {
    	return index >= getFrameCount() ? index - getFrameCount() : index;
    }
    
    private void freeFile() {
    	if (gifInfoPtr != 0) {
    		GifImageNative.free(gifInfoPtr);
    		gifInfoPtr = 0;
    		lastRequestedBits = null;
    	}
    }
}
