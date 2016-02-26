package com.wonder.sgsone.opengles;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.Log;
import com.bf.sgs.CharacterCard;
import com.bf.sgs.info.ChrSkinItem;
import com.bf.sgs.zym;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import org.kxml2.wap.Wbxml;

public class ChrHeadBuffer {
    private static Bitmap bmDefault;
    private String TAG;
    private byte[] bitmapIndex;
    private int[] chrIdArr;
    private int imgHeight;
    private int imgWidth;
    private boolean isAlias;
    private int mColor;
    private CTexture[] mTextureSheets;
    private int[] skinIdArr;
    private int texture_h;
    private int texture_w;
    private short[] xOffset;
    private short[] yOffset;

    public ChrHeadBuffer() {
        this.TAG = "ChrHeadBuffer";
        this.texture_w = 0;
        this.texture_h = 0;
        this.mColor = -1;
    }

    public static Vector<Bitmap> getBitmaps(String str, List<? extends ChrSkinItem> list) {
        Vector<Bitmap> vector;
        Exception e;
        if (list == null || list.size() <= 0) {
            vector = null;
        } else {
            Vector<Bitmap> vector2;
            try {
                byte[] readFileMuitiData = FileManager.readFileMuitiData(str);
                if (readFileMuitiData == null) {
                    return null;
                }
                int i;
                InputStream byteArrayInputStream = new ByteArrayInputStream(readFileMuitiData);
                FileManager.readShort(byteArrayInputStream);
                FileManager.readUTF(byteArrayInputStream);
                int readInt = FileManager.readInt(byteArrayInputStream);
                int readInt2 = FileManager.readInt(byteArrayInputStream);
                int readInt3 = FileManager.readInt(byteArrayInputStream);
                vector2 = new Vector();
                int i2 = 0;
                while (i2 < list.size()) {
                    try {
                        vector2.add(null);
                        i2++;
                    } catch (Exception e2) {
                        e = e2;
                    }
                }
                boolean readBoolean = FileManager.readBoolean(byteArrayInputStream);
                FileManager.readInt(byteArrayInputStream);
                FileManager.readInt(byteArrayInputStream);
                short[] sArr = new short[readInt3];
                short[] sArr2 = new short[readInt3];
                byte[] bArr = new byte[readInt3];
                for (i2 = 0; i2 < readInt3; i2++) {
                    bArr[i2] = (byte) byteArrayInputStream.read();
                    sArr[i2] = FileManager.readShort(byteArrayInputStream);
                    sArr2[i2] = FileManager.readShort(byteArrayInputStream);
                }
                FileManager.readInt(byteArrayInputStream);
                FileManager.readInt(byteArrayInputStream);
                int[] iArr = new int[readInt3];
                int[] iArr2 = new int[readInt3];
                for (i2 = 0; i2 < readInt3; i2++) {
                    iArr[i2] = FileManager.readInt(byteArrayInputStream);
                    iArr2[i2] = FileManager.readInt(byteArrayInputStream);
                }
                int readInt4 = FileManager.readInt(byteArrayInputStream);
                int readInt5 = FileManager.readInt(byteArrayInputStream);
                int readInt6 = FileManager.readInt(byteArrayInputStream);
                byte[] bArr2 = new byte[((readInt5 * readInt6) * 4)];
                Inflater inflater = new Inflater();
                for (int i3 = 0; i3 < readInt4; i3++) {
                    int i4;
                    int i5;
                    int i6;
                    int i7;
                    int readInt7 = FileManager.readInt(byteArrayInputStream);
                    if (readBoolean) {
                        i = 0;
                        i4 = readInt5 / 8;
                        for (i5 = 0; i5 < readInt7; i5++) {
                            i6 = 0;
                            while (i6 < i4) {
                                byte read = (byte) byteArrayInputStream.read();
                                i7 = i;
                                i = 7;
                                while (i >= 0) {
                                    if (((read >> i) & 1) == 1) {
                                        i2 = i7 + 1;
                                        bArr2[i7] = (byte) -1;
                                    } else {
                                        i2 = i7 + 1;
                                        bArr2[i7] = (byte) 0;
                                    }
                                    i--;
                                    i7 = i2;
                                }
                                i6++;
                                i = i7;
                            }
                        }
                    } else {
                        i2 = FileManager.readInt(byteArrayInputStream);
                        byte[] bArr3 = new byte[i2];
                        byteArrayInputStream.read(bArr3, 0, i2);
                        inflater.reset();
                        inflater.setInput(bArr3, 0, i2);
                        try {
                            inflater.inflate(bArr2, 0, bArr2.length);
                        } catch (DataFormatException e3) {
                            e3.printStackTrace();
                        }
                    }
                    i4 = readInt5 / readInt;
                    int i8 = readInt6 / readInt2;
                    i2 = readInt3 - ((i3 * i4) * i8);
                    i = i2 > i4 * i8 ? i4 * i8 : i2;
                    for (readInt7 = 0; readInt7 < i; readInt7++) {
                        Object obj;
                        int i9 = ((i3 * i4) * i8) + readInt7;
                        if ((iArr[i9] == 0 && iArr2[i9] == 0) || i9 == 0) {
                            i7 = 1;
                        } else {
                            obj = null;
                        }
                        i6 = -1;
                        for (i5 = 0; i5 < list.size(); i5++) {
                            ChrSkinItem chrSkinItem = (ChrSkinItem) list.get(i5);
                            if (chrSkinItem != null && iArr[i9] == chrSkinItem.chrId && iArr2[i9] == chrSkinItem.skinId) {
                                i6 = i5;
                                break;
                            }
                        }
                        if (obj != null || i6 >= 0) {
                            i5 = readInt7 / i4;
                            int[] iArr3 = new int[(readInt * readInt2)];
                            i2 = 0;
                            while (true) {
                                int length = iArr3.length;
                                if (i2 >= r0) {
                                    break;
                                }
                                length = ((((i5 * readInt2) + (i2 / readInt)) * readInt5) + ((readInt7 % i4) * readInt)) + (i2 % readInt);
                                byte b = bArr2[length * 4];
                                byte b2 = bArr2[(length * 4) + 1];
                                iArr3[i2] = ((((bArr2[(length * 4) + 3] & ImageInfo.ID_card44) << 24) | ((b & ImageInfo.ID_card44) << 16)) | ((b2 & ImageInfo.ID_card44) << 8)) | (bArr2[(length * 4) + 2] & ImageInfo.ID_card44);
                                i2++;
                            }
                            Bitmap ToScaledBitmap = zym.ToScaledBitmap(Bitmap.createBitmap(iArr3, readInt, readInt2, Config.ARGB_8888));
                            if (i6 >= 0) {
                                vector2.set(i6, ToScaledBitmap);
                            }
                            if (obj != null) {
                                bmDefault = ToScaledBitmap;
                            }
                        }
                    }
                }
                inflater.end();
                byteArrayInputStream.close();
                if (bmDefault != null) {
                    for (i = 0; i < vector2.size(); i++) {
                        if (((Bitmap) vector2.get(i)) == null) {
                            vector2.set(i, bmDefault);
                        }
                    }
                    bmDefault = null;
                    return vector2;
                }
                vector = vector2;
            } catch (Exception e4) {
                e = e4;
                vector2 = null;
                e.printStackTrace();
                return vector2;
            }
        }
        return vector;
    }

    public void Load(String str, int i) {
        remove();
        byte[] readFileMuitiData = FileManager.readFileMuitiData(str);
        if (readFileMuitiData != null) {
            int i2;
            InputStream byteArrayInputStream = new ByteArrayInputStream(readFileMuitiData);
            FileManager.readShort(byteArrayInputStream);
            FileManager.readUTF(byteArrayInputStream);
            this.imgWidth = FileManager.readInt(byteArrayInputStream);
            this.imgHeight = FileManager.readInt(byteArrayInputStream);
            int readInt = FileManager.readInt(byteArrayInputStream);
            this.isAlias = FileManager.readBoolean(byteArrayInputStream);
            FileManager.readInt(byteArrayInputStream);
            FileManager.readInt(byteArrayInputStream);
            this.xOffset = new short[readInt];
            this.yOffset = new short[readInt];
            this.bitmapIndex = new byte[readInt];
            for (i2 = 0; i2 < readInt; i2++) {
                this.bitmapIndex[i2] = (byte) byteArrayInputStream.read();
                this.xOffset[i2] = FileManager.readShort(byteArrayInputStream);
                this.yOffset[i2] = FileManager.readShort(byteArrayInputStream);
            }
            FileManager.readInt(byteArrayInputStream);
            FileManager.readInt(byteArrayInputStream);
            this.chrIdArr = new int[readInt];
            this.skinIdArr = new int[readInt];
            for (i2 = 0; i2 < readInt; i2++) {
                this.chrIdArr[i2] = FileManager.readInt(byteArrayInputStream);
                this.skinIdArr[i2] = FileManager.readInt(byteArrayInputStream);
            }
            int readInt2 = FileManager.readInt(byteArrayInputStream);
            this.mTextureSheets = new CTexture[readInt2];
            this.texture_w = FileManager.readInt(byteArrayInputStream);
            this.texture_h = FileManager.readInt(byteArrayInputStream);
            byte[] bArr = new byte[(this.texture_w * this.texture_h)];
            if (i == 1) {
                bArr = new byte[((this.texture_w * this.texture_h) * 4)];
            }
            Inflater inflater = new Inflater();
            for (int i3 = 0; i3 < readInt2; i3++) {
                int i4;
                int readInt3 = FileManager.readInt(byteArrayInputStream);
                if (this.isAlias) {
                    int i5 = 0;
                    int i6 = this.texture_w / 8;
                    for (int i7 = 0; i7 < readInt3; i7++) {
                        i4 = 0;
                        while (i4 < i6) {
                            byte read = (byte) byteArrayInputStream.read();
                            int i8 = i5;
                            i5 = 7;
                            while (i5 >= 0) {
                                if (((read >> i5) & 1) == 1) {
                                    i2 = i8 + 1;
                                    bArr[i8] = (byte) -1;
                                } else {
                                    i2 = i8 + 1;
                                    bArr[i8] = (byte) 0;
                                }
                                i5--;
                                i8 = i2;
                            }
                            i4++;
                            i5 = i8;
                        }
                    }
                } else {
                    i2 = FileManager.readInt(byteArrayInputStream);
                    byte[] bArr2 = new byte[i2];
                    byteArrayInputStream.read(bArr2, 0, i2);
                    inflater.reset();
                    inflater.setInput(bArr2, 0, i2);
                    try {
                        inflater.inflate(bArr, 0, bArr.length);
                    } catch (DataFormatException e) {
                        try {
                            e.printStackTrace();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            return;
                        }
                    }
                }
                this.mTextureSheets[i3] = new CTexture();
                i4 = readInt3 < 32 ? 1 : 32;
                while (i4 < readInt3) {
                    i4 *= 2;
                }
                this.mTextureSheets[i3].initWithData(bArr, i, this.texture_w, i4, this.texture_w, i4);
            }
            inflater.end();
            byteArrayInputStream.close();
        }
    }

    public void LoadHeadImgs(String str) {
        Load(str, 1);
    }

    public void drawHeadImg(int i, int i2, int i3) {
        drawHeadImg(i, 0, i2, i3);
    }

    public void drawHeadImg(int i, int i2, int i3, int i4) {
        drawHeadImg(i, i2, i3, i4, 0, -1, 0.0f, 1.0f);
    }

    public void drawHeadImg(int i, int i2, int i3, int i4, int i5) {
        drawHeadImg(i, i2, i4, i5, 0, 16777215 | ((i3 & ImageInfo.ID_card44) << 24), 0.0f, 1.0f);
    }

    public void drawHeadImg(int i, int i2, int i3, int i4, int i5, int i6, float f, float f2) {
        drawHeadImg(i, i2, i3, i4, i5, i6, f, f2, f2, zym.wScale, zym.hScale);
    }

    public void drawHeadImg(int i, int i2, int i3, int i4, int i5, int i6, float f, float f2, float f3, float f4, float f5) {
        drawHeadImg(i, i2, 0, 0, this.imgWidth, this.imgHeight, i3, i4, (int) ((((float) this.imgWidth) * f2) * f4), (int) ((((float) this.imgHeight) * f3) * f5), i5, i6, f);
    }

    public void drawHeadImg(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, float f) {
        if (i < 0 || i2 < 0) {
            Log.d(this.TAG, String.format("Invalid id params: chrId=%d skinId=%d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
        } else if (this.chrIdArr == null || this.chrIdArr.length <= 0) {
            Log.d(this.TAG, "No imgs packed, the chrIdArr is null");
        } else {
            int i13 = 0;
            int i14 = 0;
            int i15 = 0;
            while (i15 < this.chrIdArr.length) {
                if (this.chrIdArr[i15] == i && this.skinIdArr != null && i15 < this.skinIdArr.length) {
                    if (i14 == 0) {
                        i13 = i15;
                    }
                    i14++;
                    if (i2 == this.skinIdArr[i15]) {
                        i14 = i13;
                        i13 = i15;
                        break;
                    }
                }
                i15++;
            }
            i14 = i13;
            i13 = 0;
            if (i13 == 0) {
                i13 = i14;
            }
            if (this.bitmapIndex == null || this.bitmapIndex.length <= 0 || i13 >= this.bitmapIndex.length) {
                Log.d(this.TAG, String.format("bitmapIndex is not as long as chrIdArr", new Object[0]));
                return;
            }
            i14 = this.bitmapIndex[i13] & ImageInfo.ID_card44;
            if (this.mTextureSheets != null && i14 < this.mTextureSheets.length) {
                this.mTextureSheets[i14].drawAtPoint((65535 & this.xOffset[i13]) + i3, (65535 & this.yOffset[i13]) + i4, i5, i6, 0, 0, i7 + ((i11 & 64) != 0 ? i9 : 0), (zym.m_height - i8) - ((i11 & Wbxml.EXT_T_0) != 0 ? i10 : 0), f, ((float) i9) / ((float) i5), ((float) i10) / ((float) i6), i11, i12);
            }
        }
    }

    public void drawHeadImg(CharacterCard characterCard, int i, int i2) {
        if (characterCard != null) {
            int GetCardId = characterCard.GetCardId();
            if (characterCard.getAvatarId() > 0) {
                GetCardId = characterCard.getAvatarId();
            }
            if (characterCard.getSkinId() > 0 && characterCard.getSkinParentId() > 0) {
                GetCardId = characterCard.getSkinParentId();
            }
            drawHeadImg(GetCardId, characterCard.getSkinId(), i, i2);
        }
    }

    public void drawHeadImg(CharacterCard characterCard, int i, int i2, int i3) {
        if (characterCard != null) {
            int GetCardId = characterCard.GetCardId();
            if (characterCard.getAvatarId() > 0) {
                GetCardId = characterCard.getAvatarId();
            }
            drawHeadImg(GetCardId, characterCard.getSkinId(), i, i2, i3);
        }
    }

    public void drawRawHeadImg(int i, int i2, int i3) {
        drawRawHeadImg(i, 0, i2, i3);
    }

    public void drawRawHeadImg(int i, int i2, int i3, int i4) {
        drawHeadImg(i, i2, i3, i4, 0, -1, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    public boolean existInPack(int i) {
        return existInPack(i, 0);
    }

    public boolean existInPack(int i, int i2) {
        if (this.chrIdArr == null) {
            return false;
        }
        int i3 = 0;
        while (i3 < this.chrIdArr.length) {
            if (this.chrIdArr[i3] == i && this.skinIdArr[i3] == i2) {
                return true;
            }
            i3++;
        }
        return false;
    }

    public boolean existInPack(CharacterCard characterCard) {
        if (characterCard == null) {
            return false;
        }
        int GetCardId = characterCard.GetCardId();
        if (characterCard.getAvatarId() > 0) {
            GetCardId = characterCard.getAvatarId();
        }
        return GetCardId != 0 && existInPack(GetCardId, 0);
    }

    public int getColor() {
        return this.mColor;
    }

    public int getImgHeight() {
        return (int) (((float) this.imgHeight) * zym.hScale);
    }

    public int getImgRawHeight() {
        return this.imgHeight;
    }

    public int getImgRawWidth() {
        return this.imgWidth;
    }

    public int getImgWidth() {
        return (int) (((float) this.imgWidth) * zym.wScale);
    }

    public void remove() {
        this.xOffset = null;
        this.yOffset = null;
        this.bitmapIndex = null;
        if (this.mTextureSheets != null) {
            for (int i = 0; i < this.mTextureSheets.length; i++) {
                if (this.mTextureSheets[i] != null) {
                    this.mTextureSheets[i].removeTexture();
                    this.mTextureSheets[i] = null;
                }
            }
            this.mTextureSheets = null;
        }
    }

    public void setColor(int i) {
        this.mColor = i;
    }
}
