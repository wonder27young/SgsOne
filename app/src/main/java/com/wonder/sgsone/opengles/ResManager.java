package com.wonder.sgsone.opengles;

import com.wonder.sgsone.opengles.CTexture;

public class ResManager {
    private static ResManager instance;
    private int[][] imgInfo;
    private CTexture[] textureArray;
    private int texture_len;

    static {
        instance = null;
    }

    private ResManager() {
        this.texture_len = 10;
        this.textureArray = new CTexture[this.texture_len];
    }

    public static ResManager Instance() {
        if (instance == null) {
            instance = new ResManager();
        }
        return instance;
    }

    public final void checkArrayLen(int i) {
        if (this.imgInfo == null || this.imgInfo.length < i) {
            Object obj = new int[i][];
            if (this.imgInfo != null) {
                System.arraycopy(this.imgInfo, 0, obj, 0, this.imgInfo.length);
                this.imgInfo = null;
            }
            this.imgInfo = obj;
        }
    }

    public final void clear() {
        instance = null;
        this.textureArray = null;
        this.texture_len = 0;
        System.out.println("ResManager cleared");
    }

    public final int[] getRect(int i) {
        return (this.imgInfo != null && i >= 0 && i < this.imgInfo.length) ? this.imgInfo[i] : null;
    }

    public final int[] getRectInfo(int i) {
        return (i < 0 || this.imgInfo == null || i >= this.imgInfo.length) ? null : this.imgInfo[i];
    }

    public final CTexture getTexture(int i) {
        if (i >= 0 && this.imgInfo != null && i < this.imgInfo.length && this.imgInfo[i] != null) {
            return this.textureArray[this.imgInfo[i][4]];
        }
        try {
            throw new ArrayIndexOutOfBoundsException("get texture null : " + i);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final void setImgInfo(int i, int[] iArr) {
        if (i < 0 || i >= this.imgInfo.length) {
            throw new ArrayIndexOutOfBoundsException("img id out of bounds" + i + ":" + this.imgInfo.length);
        }
        this.imgInfo[i] = iArr;
    }

    public final void setTexture(int i, CTexture cTexture) {
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException("texture id out of bounds");
        }
        if (i >= this.texture_len) {
            Object obj = new CTexture[(i + 1)];
            System.arraycopy(this.textureArray, 0, obj, 0, this.texture_len);
            this.texture_len = obj.length;
            this.textureArray = null;
            this.textureArray = obj;
        }
        this.textureArray[i] = cTexture;
    }
}
