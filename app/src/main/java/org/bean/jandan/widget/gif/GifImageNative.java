package org.bean.jandan.widget.gif;

import java.io.FileDescriptor;
import java.io.InputStream;


public class GifImageNative {
	static {
        System.loadLibrary("gif");
    }
	
	static native int openFile(int[] metaData, String filePath) throws GifIOException;
	
	static native int openStream(int[] metaData, InputStream stream) throws GifIOException;
	
	static native int openFd(int[] metaData, FileDescriptor fd, long offset) throws GifIOException;
	
	static native void free(int gifFileInPtr);

    static native String getComment(int gifFileInPtr);
    
    static native int getDuration(int gifFileInPtr, int frameIndex);

    static native void seekToFrame(int gifFileInPtr, int frameIndex, int[] pixels);
    
    static native int getLoopCount(int gifFileInPtr);
}
