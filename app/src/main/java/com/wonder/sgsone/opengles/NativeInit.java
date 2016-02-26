package com.wonder.sgsone.opengles;

public class NativeInit {
    public static int[] clipCoords;

    static {
        clipCoords = new int[4];
    }

    public static native void drawArc(float f, float f2, float f3, float f4, float f5, int i, boolean z);

    public static native void drawParticles(int i, int i2, int i3);

    public static native void fillColorRect(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    public static int[] getClip() {
        return clipCoords;
    }

    public static native void initParticles(int i, int i2);

    public static native void nativeBeginRender();

    public static native void nativeClearScreen();

    public static native void nativeDestroy();

    public static native void nativeEndRender();

    public static native void nativeGetClip(int[] iArr);

    public static native void nativeInit();

    public static native void nativeResize(int i, int i2);

    public static native void nativeSetClip(int i, int i2, int i3, int i4);

    public static void setClip(int i, int i2, int i3, int i4) {
        nativeSetClip(i, i2, i3, i4);
        clipCoords[0] = i;
        clipCoords[1] = i2;
        clipCoords[2] = i3;
        clipCoords[3] = i4;
    }
}
