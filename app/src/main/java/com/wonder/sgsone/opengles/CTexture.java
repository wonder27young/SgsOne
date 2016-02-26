package com.wonder.sgsone.opengles;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.opengl.GLUtils;

import com.wonder.sgsone.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.zip.Inflater;
import javax.microedition.khronos.opengles.GL10;

public final class CTexture {
    public static final int BOTTOM = 8;
    public static final int FLIP_H = 64;
    public static final int FLIP_V = 128;
    public static final int HCENTER = 16;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int TOP = 4;
    public static final int VCENTER = 32;
    public static final int kGLTexturePixelFormat_A8 = 3;
    public static final int kGLTexturePixelFormat_Automatic = 0;
    public static final int kGLTexturePixelFormat_RGB565 = 2;
    public static final int kGLTexturePixelFormat_RGBA4444 = 4;
    public static final int kGLTexturePixelFormat_RGBA5551 = 5;
    public static final int kGLTexturePixelFormat_RGBA8888 = 1;
    private int mPointer;
    int mTextureId;
    int m_h;
    int m_w;
    private int[][] rectInfo;
    ResManager resourceManager;

    public CTexture() {
        this.mPointer = kGLTexturePixelFormat_Automatic;
        this.m_w = MainActivity.nGameWidth;
        this.m_h = MainActivity.nGameHeight;
    }

    private void clearImgInfo() {
        if (this.rectInfo != null) {
            this.rectInfo = null;
        }
    }

    public static native void drawRect(int i, int i2, int i3, int i4, int i5, int i6);

    public static native void fillRect(int i, int i2, int i3, int i4, int i5, int i6);

    private final int getOrginalSize(int i, int i2, int i3) {
        return i == kGLTexturePixelFormat_RGBA8888 ? (i2 * i3) * kGLTexturePixelFormat_RGBA4444 : i == kGLTexturePixelFormat_A8 ? i2 * i3 : (i2 * i3) * kGLTexturePixelFormat_RGB565;
    }

    private void initWithMultipleBBM(GL10 gl10, String str, byte[] bArr, int i) {
        int indexOf = str.indexOf(46);
        if (indexOf == -1) {
            System.out.println("---------multiple bbm error--------");
            System.out.println("---------wrong bbm name--------");
            return;
        }
        String str2 = str.substring(kGLTexturePixelFormat_Automatic, indexOf) + "_";
        String substring = str.substring(indexOf);
        int i2 = kGLTexturePixelFormat_Automatic;
        int i3 = kGLTexturePixelFormat_Automatic;
        while (true) {
            int GetAssetFileSize = FileManager.GetAssetFileSize(new StringBuilder(String.valueOf(str2)).append(i3).append(substring).toString());
            if (GetAssetFileSize < 0) {
                break;
            }
            i2 = GetAssetFileSize + i2;
            i3 += kGLTexturePixelFormat_RGBA8888;
        }
        if (i3 == 0 || i2 == 0) {
            System.out.println("---------multiple bbm error--------");
            System.out.println("---------no bbm files--------");
            return;
        }
        int i4 = kGLTexturePixelFormat_Automatic;
        int i5 = kGLTexturePixelFormat_Automatic;
        int i6 = kGLTexturePixelFormat_Automatic;
        int i7 = kGLTexturePixelFormat_Automatic;
        int i8 = kGLTexturePixelFormat_Automatic;
        int i9 = kGLTexturePixelFormat_Automatic;
        int i10 = kGLTexturePixelFormat_Automatic;
        int i11 = kGLTexturePixelFormat_Automatic;
        int i12 = kGLTexturePixelFormat_Automatic;
        indexOf = kGLTexturePixelFormat_Automatic;
        byte[] bArr2 = null;
        while (true) {
            InputStream inputStream = null;
            InputStream inputStream2 = null;
            if (i6 >= i3) {
                break;
            }
            int i13;
            byte[] bArr3;
            inputStream = FileManager.getResourceAsInputStream(new StringBuilder(String.valueOf(str2)).append(i6).append(substring).toString());
            int available = inputStream.available();
            if (i6 == 0) {
                i9 = inputStream.read();
                i12 = FileManager.readInt(inputStream);
                i10 = FileManager.readInt(inputStream);
                i4 = FileManager.readInt(inputStream);
                i5 = FileManager.readInt(inputStream);
                i11 = FileManager.readInt(inputStream);
                int orginalSize = getOrginalSize(i9, i4, i5);
                i8 = getOrginalSize(i9, i4, i10);
                GetAssetFileSize = orginalSize > (i2 + -21) - i11 ? orginalSize : (i2 - 21) - i11;
                bArr2 = (bArr == null || i < GetAssetFileSize) ? new byte[GetAssetFileSize] : bArr;
                indexOf = available - 21;
                available = i11;
                i11 = i10;
                i13 = orginalSize;
                bArr3 = bArr2;
                GetAssetFileSize = indexOf;
                indexOf = i9;
                i9 = i13;
            } else {
                bArr3 = bArr2;
                GetAssetFileSize = available;
                available = i11;
                i11 = i10;
                i13 = indexOf;
                indexOf = i9;
                i9 = i12;
                i12 = i8;
                i8 = i13;
            }
            i10 = (i7 + GetAssetFileSize) - available;
            if (i10 >= 0) {
                FileManager.readData(inputStream, bArr3, i7, GetAssetFileSize - i10);
                if (i8 == available) {
                    this.mPointer = nativeInitWithData(bArr3, indexOf, i12, i11, i4, i5);
                } else {
                    Inflater inflater = new Inflater();
                    inflater.reset();
                    inflater.setInput(bArr3, kGLTexturePixelFormat_Automatic, available);
                    inflater.inflate(bArr3, kGLTexturePixelFormat_Automatic, i9);
                    this.mPointer = nativeInitWithData(bArr3, indexOf, i12, i11, i4, i5);
                }
                if (i10 > 0) {
                    FileManager.readData(inputStream, bArr3, kGLTexturePixelFormat_Automatic, i10);
                }
                GetAssetFileSize = i10;
            } else {
                try {
                    FileManager.readData(inputStream, bArr3, i7, GetAssetFileSize);
                    GetAssetFileSize += i7;
                } catch (Exception e) {
                    e = e;
                    inputStream2 = inputStream;
                } catch (Throwable th) {
                    th = th;
                }
            }
            inputStream.close();
            i6 += kGLTexturePixelFormat_RGBA8888;
            i7 = GetAssetFileSize;
            i10 = i11;
            bArr2 = bArr3;
            i11 = available;
            i13 = i12;
            i12 = i9;
            i9 = indexOf;
            indexOf = i8;
            i8 = i13;
        }
        if (i7 > 0) {
            try {
                readHashTable(bArr2, kGLTexturePixelFormat_Automatic);
            } catch (Exception e2) {
                e = e2;
                try {
                    Exception e3;
                    System.out.println("read multiple bbm file error!");
                    e3.printStackTrace();
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                            return;
                        } catch (IOException e4) {
                            e4.printStackTrace();
                            return;
                        }
                    }
                    return;
                } catch (Throwable th2) {
                    Throwable th3;
                    th3 = th2;
                    inputStream = inputStream2;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    throw th3;
                }
            } catch (Throwable th4) {
                th3 = th4;
            }
        }
        if (bArr2 == bArr) {
        }
    }

    private void readHashTable(byte[] bArr, int i) {
        clearImgInfo();
        ResManager Instance = ResManager.Instance();
        int i2 = (bArr[i] & ImageInfo.ID_card44) | ((bArr[i + kGLTexturePixelFormat_RGBA8888] & ImageInfo.ID_card44) << BOTTOM);
        int i3 = i + kGLTexturePixelFormat_RGB565;
        int i4 = ((bArr[i3 + kGLTexturePixelFormat_RGBA8888] & ImageInfo.ID_card44) << BOTTOM) | (bArr[i3] & ImageInfo.ID_card44);
        i3 += kGLTexturePixelFormat_RGB565;
        int i5 = ((bArr[i3 + kGLTexturePixelFormat_RGBA8888] & ImageInfo.ID_card44) << BOTTOM) | (bArr[i3] & ImageInfo.ID_card44);
        int i6 = i3 + kGLTexturePixelFormat_RGB565;
        Instance.setTexture(i2, this);
        zym.pt("texInfoArrayLen" + i5);
        Instance.checkArrayLen(i5);
        Class cls = Integer.TYPE;
        int[] iArr = new int[kGLTexturePixelFormat_RGB565];
        iArr[kGLTexturePixelFormat_Automatic] = i4;
        iArr[kGLTexturePixelFormat_RGBA8888] = 10;
        this.rectInfo = (int[][]) Array.newInstance(cls, iArr);
        int i7 = i6;
        i3 = i7;
        for (i6 = kGLTexturePixelFormat_Automatic; i6 < i4; i6 += kGLTexturePixelFormat_RGBA8888) {
            this.rectInfo[i6][kGLTexturePixelFormat_Automatic] = (bArr[i3] & ImageInfo.ID_card44) | ((bArr[i3 + kGLTexturePixelFormat_RGBA8888] & ImageInfo.ID_card44) << BOTTOM);
            i3 += kGLTexturePixelFormat_RGB565;
            this.rectInfo[i6][kGLTexturePixelFormat_RGBA8888] = (bArr[i3] & ImageInfo.ID_card44) | ((bArr[i3 + kGLTexturePixelFormat_RGBA8888] & ImageInfo.ID_card44) << BOTTOM);
            i3 += kGLTexturePixelFormat_RGB565;
            this.rectInfo[i6][kGLTexturePixelFormat_RGB565] = (bArr[i3] & ImageInfo.ID_card44) | ((bArr[i3 + kGLTexturePixelFormat_RGBA8888] & ImageInfo.ID_card44) << BOTTOM);
            i3 += kGLTexturePixelFormat_RGB565;
            this.rectInfo[i6][kGLTexturePixelFormat_A8] = (bArr[i3] & ImageInfo.ID_card44) | ((bArr[i3 + kGLTexturePixelFormat_RGBA8888] & ImageInfo.ID_card44) << BOTTOM);
            i3 += kGLTexturePixelFormat_RGB565;
            byte b = bArr[i3];
            byte b2 = bArr[i3 + kGLTexturePixelFormat_RGBA8888];
            i3 += kGLTexturePixelFormat_RGB565;
            this.rectInfo[i6][kGLTexturePixelFormat_RGBA4444] = i2;
            this.rectInfo[i6][kGLTexturePixelFormat_RGBA5551] = bArr[i3] & ImageInfo.ID_card44;
            i3 += kGLTexturePixelFormat_RGBA8888;
            this.rectInfo[i6][6] = (bArr[i3] & ImageInfo.ID_card44) | ((bArr[i3 + kGLTexturePixelFormat_RGBA8888] & ImageInfo.ID_card44) << BOTTOM);
            i3 += kGLTexturePixelFormat_RGB565;
            this.rectInfo[i6][7] = (bArr[i3] & ImageInfo.ID_card44) | ((bArr[i3 + kGLTexturePixelFormat_RGBA8888] & ImageInfo.ID_card44) << BOTTOM);
            i3 += kGLTexturePixelFormat_RGB565;
            this.rectInfo[i6][BOTTOM] = (bArr[i3] & ImageInfo.ID_card44) | ((bArr[i3 + kGLTexturePixelFormat_RGBA8888] & ImageInfo.ID_card44) << BOTTOM);
            i3 += kGLTexturePixelFormat_RGB565;
            this.rectInfo[i6][9] = (bArr[i3] & ImageInfo.ID_card44) | ((bArr[i3 + kGLTexturePixelFormat_RGBA8888] & ImageInfo.ID_card44) << BOTTOM);
            i3 += kGLTexturePixelFormat_RGB565;
            Instance.setImgInfo((b & ImageInfo.ID_card44) | ((b2 & ImageInfo.ID_card44) << BOTTOM), this.rectInfo[i6]);
        }
    }

    public static native void setAliasParameters();

    public static native void setAntiAliasParameters();

    public void draw(int i, int i2) {
        nativeDrawInRect(this.mPointer, i, (this.m_h - i2) - getHeight(), getWidth(), getHeight(), false, kGLTexturePixelFormat_Automatic, kGLTexturePixelFormat_Automatic, getWidth(), getHeight(), kGLTexturePixelFormat_Automatic, -1);
    }

    public void draw(int i, int i2, int i3) {
        nativeDrawInRect(this.mPointer, i2, (this.m_h - i3) - getHeight(), getWidth(), getHeight(), false, kGLTexturePixelFormat_Automatic, kGLTexturePixelFormat_Automatic, getWidth(), getHeight(), kGLTexturePixelFormat_Automatic, i | 16777215);
    }

    public void draw(int i, int i2, int i3, int i4, float f, int i5, int i6, int i7, int i8, float f2, int i9) {
        nativeDrawInRect(this.mPointer, i, (this.m_h - i2) - i4, i3, i4, false, i5, i6, i7, i8, i9, -1);
    }

    public void drawAtPoint(int i, int i2, int i3, int i4, int i5, int i6, float f, float f2, int i7) {
        nativeDrawAtPoint(this.mPointer, i, i2, i3, i4, kGLTexturePixelFormat_Automatic, kGLTexturePixelFormat_Automatic, i5, (int) (((float) (this.m_h - i6)) - (((float) i4) * f2)), -f, f2, f2, kGLTexturePixelFormat_Automatic, ((i7 & ImageInfo.ID_card44) << 24) | 16777215);
    }

    public void drawAtPoint(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, float f, float f2, float f3, int i9, int i10) {
        nativeDrawAtPoint(this.mPointer, i, i2, i3, i4, i5, i6, i7, i8, -f, f2, f3, i9, i10);
    }

    public void drawAtPointNotscaleHeight(int i, int i2, int i3, int i4, int i5, int i6, float f, float f2, int i7) {
        nativeDrawAtPoint(this.mPointer, i, i2, i3, i4, kGLTexturePixelFormat_Automatic, kGLTexturePixelFormat_Automatic, i5, (int) (((float) (this.m_h - i6)) - (((float) i4) * f2)), f2, 1.0f, -f, kGLTexturePixelFormat_Automatic, ((i7 & ImageInfo.ID_card44) << 24) | 16777215);
    }

    public void drawImage(int i, int i2, int i3) {
        if (this.resourceManager == null) {
            this.resourceManager = ResManager.Instance();
        }
        CTexture texture = this.resourceManager.getTexture(i);
        int[] rectInfo = this.resourceManager.getRectInfo(i);
        if (texture != null) {
            texture.drawInRect((float) i2, (float) ((this.m_h - i3) - rectInfo[kGLTexturePixelFormat_A8]), (float) rectInfo[kGLTexturePixelFormat_RGB565], (float) rectInfo[kGLTexturePixelFormat_A8], 0.0f, rectInfo[kGLTexturePixelFormat_Automatic], rectInfo[kGLTexturePixelFormat_RGBA8888], rectInfo[kGLTexturePixelFormat_RGB565], rectInfo[kGLTexturePixelFormat_A8], 1.0f, (int) kGLTexturePixelFormat_Automatic);
        }
    }

    public void drawInRect(float f, float f2, float f3, float f4, float f5, int i, int i2, int i3, int i4, float f6, int i5) {
        nativeDrawInRect(this.mPointer, (int) f, (int) f2, (int) f3, (int) f4, false, i, i2, i3, i4, i5, -1);
    }

    public void drawInRect(float f, float f2, float f3, float f4, float f5, int i, int i2, int i3, int i4, float f6, int i5, int i6) {
        if (i6 <= 255) {
            nativeDrawInRect(this.mPointer, (int) f, (int) f2, (int) f3, (int) f4, false, i, i2, i3, i4, i5, (i6 << 24) | 16777215);
            return;
        }
        nativeDrawInRect(this.mPointer, (int) f, (int) f2, (int) f3, (int) f4, false, i, i2, i3, i4, i5, i6);
    }

    public void drawInRect(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        nativeDrawInRect(this.mPointer, i, i2, i3, i4, false, i5, i6, i7, i8, i9, i10);
    }

    public void drawInRect(int i, int i2, int i3, int i4, boolean z, int i5, int i6, int i7, int i8, int i9) {
        nativeDrawInRect(this.mPointer, i, i2, i3, i4, false, i5, i6, i7, i8, i9, -1);
    }

    public void drawInRect(int i, int i2, int i3, int i4, boolean z, int i5, int i6, int i7, int i8, int i9, int i10) {
        nativeDrawInRect(this.mPointer, i, i2, i3, i4, false, i5, i6, i7, i8, i9, i10);
    }

    public int getHeight() {
        return nativeGetHeight(this.mPointer);
    }

    public int getWidth() {
        return nativeGetWidth(this.mPointer);
    }

    public void initTextureWithData(Bitmap bitmap, GL10 gl10) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = kGLTexturePixelFormat_RGBA8888;
        while (i < width) {
            i *= kGLTexturePixelFormat_RGB565;
        }
        int i2 = kGLTexturePixelFormat_RGBA8888;
        while (i2 < height) {
            i2 *= kGLTexturePixelFormat_RGB565;
        }
        Bitmap bitmap2 = null;
        if (i > width || i2 > height) {
            bitmap2 = Bitmap.createBitmap(i, i2, bitmap.hasAlpha() ? Config.ARGB_8888 : Config.RGB_565);
            new Canvas(bitmap2).drawBitmap(bitmap, 0.0f, 0.0f, null);
        }
        int[] iArr = new int[kGLTexturePixelFormat_RGBA8888];
        gl10.glGenTextures(kGLTexturePixelFormat_RGBA8888, iArr, kGLTexturePixelFormat_Automatic);
        this.mTextureId = iArr[kGLTexturePixelFormat_Automatic];
        gl10.glBindTexture(3553, this.mTextureId);
        gl10.glTexParameterx(3553, 10241, 9729);
        gl10.glTexParameterx(3553, 10240, 9729);
        gl10.glTexParameterx(3553, 10242, 33071);
        gl10.glTexParameterx(3553, 10243, 33071);
        if (bitmap2 != null) {
            GLUtils.texImage2D(3553, kGLTexturePixelFormat_Automatic, bitmap2, kGLTexturePixelFormat_Automatic);
        } else {
            GLUtils.texImage2D(3553, kGLTexturePixelFormat_Automatic, bitmap, kGLTexturePixelFormat_Automatic);
        }
        this.mPointer = nativeSaveTexture(this.mTextureId, width, height, i, i2);
        if (this.mPointer == 0) {
            zym.pt("kengdiea...pointer 0");
        }
    }

    public void initTextureWithFont(Bitmap bitmap, GL10 gl10) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[kGLTexturePixelFormat_RGBA8888];
        gl10.glGenTextures(kGLTexturePixelFormat_RGBA8888, iArr, kGLTexturePixelFormat_Automatic);
        this.mTextureId = iArr[kGLTexturePixelFormat_Automatic];
        gl10.glBindTexture(3553, this.mTextureId);
        gl10.glTexParameterx(3553, 10241, 9729);
        gl10.glTexParameterx(3553, 10240, 9729);
        gl10.glTexParameterx(3553, 10242, 33071);
        gl10.glTexParameterx(3553, 10243, 33071);
        GLUtils.texImage2D(3553, kGLTexturePixelFormat_Automatic, bitmap, kGLTexturePixelFormat_Automatic);
        this.mPointer = nativeSaveTexture(this.mTextureId, width, height, width, height);
    }

    public void initWithBBM(GL10 gl10, String str) {
        System.gc();
        zym.pt("spell" + str);
        try {
            int GetAssetFileSize = FileManager.GetAssetFileSize(str);
            zym.pt(new StringBuilder(aY.f11134g).append(GetAssetFileSize).toString());
            if (GetAssetFileSize >= 0) {
                InputStream resourceAsInputStream = FileManager.getResourceAsInputStream(str.toString());
                int read = resourceAsInputStream.read();
                int readInt = FileManager.readInt(resourceAsInputStream);
                int readInt2 = FileManager.readInt(resourceAsInputStream);
                int readInt3 = FileManager.readInt(resourceAsInputStream);
                int readInt4 = FileManager.readInt(resourceAsInputStream);
                int readInt5 = FileManager.readInt(resourceAsInputStream);
                byte[] bArr = new byte[(GetAssetFileSize - 21)];
                FileManager.readData(resourceAsInputStream, bArr, kGLTexturePixelFormat_Automatic, GetAssetFileSize - 21);
                GetAssetFileSize = getOrginalSize(read, readInt3, readInt4);
                if (GetAssetFileSize == readInt5) {
                    this.mPointer = nativeInitWithData(bArr, read, readInt, readInt2, readInt3, readInt4);
                } else {
                    Inflater inflater = new Inflater();
                    inflater.reset();
                    inflater.setInput(bArr, kGLTexturePixelFormat_Automatic, readInt5);
                    System.gc();
                    System.out.println("org memory = " + readInt5 + ":" + (GetAssetFileSize / ImageInfo.ID_mark_num08) + "k");
                    byte[] bArr2 = new byte[GetAssetFileSize];
                    inflater.inflate(bArr2, kGLTexturePixelFormat_Automatic, GetAssetFileSize);
                    this.mPointer = nativeInitWithData(bArr2, read, readInt, readInt2, readInt3, readInt4);
                    inflater.end();
                }
                resourceAsInputStream.close();
                if (bArr.length > readInt5) {
                    readHashTable(bArr, readInt5);
                    return;
                }
                return;
            }
            initWithMultipleBBM(gl10, str, null, kGLTexturePixelFormat_Automatic);
        } catch (Exception e) {
            System.out.println("read bbm " + str + "error!");
            e.printStackTrace();
        }
    }

    public void initWithData(byte[] bArr, int i, int i2, int i3, int i4, int i5) {
        this.mPointer = nativeInitWithData(bArr, i, i2, i3, i4, i5);
    }

    public native int nativeDrawAtPoint(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, float f, float f2, float f3, int i10, int i11);

    public native void nativeDrawInRect(int i, int i2, int i3, int i4, int i5, boolean z, int i6, int i7, int i8, int i9, int i10, int i11);

    public native int nativeGetHeight(int i);

    public native int nativeGetWidth(int i);

    public native int nativeInitWithData(byte[] bArr, int i, int i2, int i3, int i4, int i5);

    public native void nativeRemoveTexture(int i);

    public native int nativeSaveTexture(int i, int i2, int i3, int i4, int i5);

    public void removeTexture() {
        if (this.mPointer != 0) {
            nativeRemoveTexture(this.mPointer);
        }
        this.mPointer = kGLTexturePixelFormat_Automatic;
        clearImgInfo();
    }

    public void subTextureText(Bitmap bitmap, GL10 gl10) {
        gl10.glBindTexture(3553, this.mTextureId);
        if (bitmap != null) {
            GLUtils.texSubImage2D(3553, kGLTexturePixelFormat_Automatic, kGLTexturePixelFormat_Automatic, kGLTexturePixelFormat_Automatic, bitmap);
        }
    }
}
