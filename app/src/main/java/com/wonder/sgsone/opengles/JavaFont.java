package com.wonder.sgsone.opengles;

import com.bf.sgs.hdexp.MainActivity;
import com.bf.sgs.zym;
import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class JavaFont {
    private static final boolean IS_SHOW_DEFAULT_CHAR = true;
    public static final int STYLE_BOLD = 1;
    public static final int STYLE_PLAIN = 0;
    public static final int STYLE_SHADOW = 2;
    private static final int TAB_SPACE_NUM = 4;
    private char[] _mRemapTable;
    private byte[] bitmapIndex;
    private byte[] data;
    private int fontHeight;
    private int fontWidth;
    private boolean isAlias;
    private int mColor;
    private CTexture[] mTextureSheets;
    private int maxGlyph;
    private int minGlyph;
    private int offset;
    private int style;
    private int texture_h;
    private int texture_w;
    private short[] xOffset;
    private short[] yOffset;

    public JavaFont() {
        this.texture_w = STYLE_PLAIN;
        this.texture_h = STYLE_PLAIN;
        this.mColor = -1;
    }

    private final int binarySearch(char c) {
        int length = this._mRemapTable.length - 1;
        int i = STYLE_PLAIN;
        while (i <= length) {
            int i2 = (i + length) / STYLE_SHADOW;
            if (c == this._mRemapTable[i2]) {
                return i2;
            }
            if (c > this._mRemapTable[i2]) {
                i = i2 + STYLE_BOLD;
            } else {
                length = i2 - 1;
            }
        }
        return -1;
    }

    private final void drawUnknowChar(char c, int i, int i2, int i3, int i4) {
    }

    private int getExtraHeight() {
        return (this.style & STYLE_SHADOW) == 0 ? STYLE_BOLD : STYLE_PLAIN;
    }

    private int getExtraWidth() {
        int i = STYLE_PLAIN;
        if ((this.style & STYLE_BOLD) != 0) {
            i = STYLE_BOLD;
        }
        return (this.style & STYLE_SHADOW) != 0 ? i + STYLE_BOLD : i;
    }

    private final boolean readBoolean() {
        boolean z = IS_SHOW_DEFAULT_CHAR;
        if (this.data[this.offset] != (byte) 1) {
            z = false;
        }
        this.offset += STYLE_BOLD;
        return z;
    }

    private final char readChar() {
        short s = (short) ((this.data[this.offset] & ImageInfo.ID_card44) | ((this.data[this.offset + STYLE_BOLD] & ImageInfo.ID_card44) << 8));
        this.offset += STYLE_SHADOW;
        return (char) s;
    }

    private int readInt() {
        byte b = this.data[this.offset];
        byte b2 = this.data[this.offset + STYLE_BOLD];
        byte b3 = this.data[this.offset + STYLE_SHADOW];
        byte b4 = this.data[this.offset + 3];
        this.offset += TAB_SPACE_NUM;
        return (((b & ImageInfo.ID_card44) | ((b2 & ImageInfo.ID_card44) << 8)) | ((b3 & ImageInfo.ID_card44) << 16)) | ((b4 & ImageInfo.ID_card44) << 24);
    }

    private final short readShort() {
        short s = (short) ((this.data[this.offset] & ImageInfo.ID_card44) | ((this.data[this.offset + STYLE_BOLD] & ImageInfo.ID_card44) << 8));
        this.offset += STYLE_SHADOW;
        return s;
    }

    private final String readUTF() {
        String str;
        short readShort = readShort();
        try {
            str = new String(this.data, this.offset, readShort, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            str = null;
        }
        this.offset += readShort;
        return str;
    }

    public void Load(String str) {
        Load(str, 3);
    }

    public void Load(String str, int i) {
        remove();
        this.data = FileManager.readFileMuitiData(str);
        this.offset = STYLE_PLAIN;
        if (this.data != null) {
            int i2;
            int i3;
            readShort();
            readUTF();
            this.fontWidth = readInt();
            this.fontHeight = readInt();
            int readInt = readInt();
            this.isAlias = readBoolean();
            readInt();
            readInt();
            this.xOffset = new short[readInt];
            this.yOffset = new short[readInt];
            this.bitmapIndex = new byte[readInt];
            for (i2 = STYLE_PLAIN; i2 < readInt; i2 += STYLE_BOLD) {
                byte[] bArr = this.bitmapIndex;
                byte[] bArr2 = this.data;
                i3 = this.offset;
                this.offset = i3 + STYLE_BOLD;
                bArr[i2] = bArr2[i3];
                this.xOffset[i2] = readShort();
                this.yOffset[i2] = readShort();
            }
            this.minGlyph = readInt();
            this.maxGlyph = readInt();
            this._mRemapTable = new char[readInt];
            for (i2 = STYLE_PLAIN; i2 < readInt; i2 += STYLE_BOLD) {
                this._mRemapTable[i2] = readChar();
            }
            int readInt2 = readInt();
            this.mTextureSheets = new CTexture[readInt2];
            this.texture_w = readInt();
            this.texture_h = readInt();
            byte[] bArr3 = new byte[(this.texture_w * this.texture_h)];
            if (i == STYLE_BOLD) {
                bArr3 = new byte[((this.texture_w * this.texture_h) * TAB_SPACE_NUM)];
            }
            Inflater inflater = new Inflater();
            for (int i4 = STYLE_PLAIN; i4 < readInt2; i4 += STYLE_BOLD) {
                int readInt3 = readInt();
                int i5;
                if (this.isAlias) {
                    i5 = STYLE_PLAIN;
                    int i6 = this.texture_w / 8;
                    for (int i7 = STYLE_PLAIN; i7 < readInt3; i7 += STYLE_BOLD) {
                        i3 = STYLE_PLAIN;
                        while (i3 < i6) {
                            byte[] bArr4 = this.data;
                            int i8 = this.offset;
                            this.offset = i8 + STYLE_BOLD;
                            byte b = bArr4[i8];
                            i8 = i5;
                            i5 = 7;
                            while (i5 >= 0) {
                                if (((b >> i5) & STYLE_BOLD) == STYLE_BOLD) {
                                    i2 = i8 + STYLE_BOLD;
                                    bArr3[i8] = (byte) -1;
                                } else {
                                    i2 = i8 + STYLE_BOLD;
                                    bArr3[i8] = (byte) 0;
                                }
                                i5--;
                                i8 = i2;
                            }
                            i3 += STYLE_BOLD;
                            i5 = i8;
                        }
                    }
                } else {
                    i5 = readInt();
                    inflater.reset();
                    inflater.setInput(this.data, this.offset, i5);
                    try {
                        inflater.inflate(bArr3, STYLE_PLAIN, bArr3.length);
                    } catch (DataFormatException e) {
                        e.printStackTrace();
                    }
                    this.offset += i5;
                }
                this.mTextureSheets[i4] = new CTexture();
                i3 = readInt3 < 32 ? STYLE_BOLD : 32;
                while (i3 < readInt3) {
                    i3 *= STYLE_SHADOW;
                }
                this.mTextureSheets[i4].initWithData(bArr3, i, this.texture_w, i3, this.texture_w, i3);
            }
            inflater.end();
        }
    }

    public void LoadSkillFont(String str) {
        Load(str, STYLE_BOLD);
    }

    public void drawMultiText(String str, int i, int i2, int i3) {
        int fontHeight = (zym.m_height - i2) - getFontHeight();
        int i4 = i + i3;
        int fontHeight2 = getFontHeight();
        int extraWidth = getExtraWidth();
        int i5 = STYLE_PLAIN;
        int i6 = STYLE_PLAIN;
        int i7 = i;
        while (i6 < str.length()) {
            int i8;
            char charAt = str.charAt(i6);
            if (charAt == '\n') {
                fontHeight -= fontHeight2;
                i8 = i;
            } else if (charAt == '\r') {
                i8 = i7;
            } else if (charAt == ' ') {
                i8 = i7 + (this.fontWidth / STYLE_SHADOW);
            } else if (charAt == '\t') {
                i8 = i7 + ((this.fontWidth * TAB_SPACE_NUM) / STYLE_SHADOW);
            } else if (charAt < this.minGlyph || charAt > this.maxGlyph) {
                if (i5 + i7 > i4) {
                    i8 = fontHeight - fontHeight2;
                    fontHeight = i;
                } else {
                    i8 = fontHeight;
                    fontHeight = i7;
                }
                i7 = charAt > '\u007f' ? this.fontWidth : this.fontWidth / STYLE_SHADOW;
                drawUnknowChar(charAt, fontHeight, i8, i7, this.fontHeight);
                i5 = fontHeight + i7;
                fontHeight = i8;
                i8 = i5;
                i5 = i7;
            } else {
                int binarySearch = binarySearch(charAt);
                if (binarySearch == -1) {
                    if (i5 + i7 > i4) {
                        i8 = fontHeight - fontHeight2;
                        fontHeight = i;
                    } else {
                        i8 = fontHeight;
                        fontHeight = i7;
                    }
                    i7 = charAt > '\u007f' ? this.fontWidth : this.fontWidth / STYLE_SHADOW;
                    drawUnknowChar(charAt, fontHeight, i8, i7, this.fontHeight);
                    i5 = fontHeight + i7;
                    fontHeight = i8;
                    i8 = i5;
                    i5 = i7;
                } else if ((this.bitmapIndex[binarySearch] & ImageInfo.ID_card44) == -1) {
                    if (i5 + i7 > i4) {
                        i8 = fontHeight - fontHeight2;
                        fontHeight = i;
                    } else {
                        i8 = fontHeight;
                        fontHeight = i7;
                    }
                    i7 = charAt > '\u007f' ? this.fontWidth : this.fontWidth / STYLE_SHADOW;
                    drawUnknowChar(charAt, fontHeight, i8, i7, this.fontHeight);
                    i5 = fontHeight + i7;
                    fontHeight = i8;
                    i8 = i5;
                    i5 = i7;
                } else {
                    int i9;
                    int i10;
                    int i11;
                    CTexture cTexture = this.mTextureSheets[this.bitmapIndex[binarySearch] & ImageInfo.ID_card44];
                    if (charAt < '\u007f') {
                        i9 = this.fontWidth / STYLE_SHADOW;
                        i8 = this.fontWidth / STYLE_SHADOW;
                    } else {
                        i9 = this.fontWidth;
                        i8 = this.fontWidth;
                    }
                    if ((i7 + i9) + extraWidth > i4) {
                        i10 = fontHeight - fontHeight2;
                        i11 = i;
                    } else {
                        i11 = i7;
                        i10 = fontHeight;
                    }
                    if ((this.style & STYLE_SHADOW) != 0) {
                        int i12 = ((this.mColor >> 24) & ImageInfo.ID_card44) / STYLE_SHADOW;
                        if (i12 > 5) {
                            cTexture.drawInRect(i11 + extraWidth, i10 - 1, i8, this.fontHeight, this.xOffset[binarySearch] & 65535, this.yOffset[binarySearch] & 65535, i9, this.fontHeight, (int) STYLE_PLAIN, (i12 << 24) | (this.mColor & 16777215));
                        }
                    }
                    cTexture.drawInRect(i11, i10, i8, this.fontHeight, this.xOffset[binarySearch] & 65535, this.yOffset[binarySearch] & 65535, i9, this.fontHeight, (int) STYLE_PLAIN, this.mColor);
                    if ((this.style & STYLE_BOLD) != 0) {
                        cTexture.drawInRect(i11 + STYLE_BOLD, i10, i8, this.fontHeight, 65535 & this.xOffset[binarySearch], 65535 & this.yOffset[binarySearch], i9, this.fontHeight, (int) STYLE_PLAIN, this.mColor);
                    }
                    i5 = i8;
                    fontHeight = i10;
                    i8 = i11 + (i8 + extraWidth);
                }
            }
            i6 += STYLE_BOLD;
            i7 = i8;
        }
    }

    public void drawMultiText(String str, int i, int i2, int i3, int[] iArr, int[] iArr2) {
        int fontHeight = (zym.m_height - i2) - ((int) (MainActivity.scaled * ((float) getFontHeight())));
        int i4 = i + i3;
        float f = MainActivity.scaled;
        float f2 = (((float) this.fontWidth) * f) / 2.0f;
        float f3 = f * ((float) this.fontWidth);
        float fontHeight2 = f * ((float) getFontHeight());
        int extraWidth = getExtraWidth();
        int i5 = STYLE_PLAIN;
        int i6 = i;
        int i7 = -1;
        while (i5 < str.length()) {
            int i8;
            Object obj;
            int i9;
            int i10;
            char charAt = str.charAt(i5);
            for (i8 = STYLE_PLAIN; i8 < iArr.length; i8 = (byte) (i8 + STYLE_BOLD)) {
                if (iArr[i8] == i5) {
                    obj = STYLE_BOLD;
                    i9 = iArr2[i8];
                    break;
                }
            }
            i9 = i7;
            obj = STYLE_PLAIN;
            if (obj == null) {
                i9 = -1;
            }
            if (charAt == '\n') {
                i10 = (int) (((float) fontHeight) - fontHeight2);
                fontHeight = i;
            } else if (charAt == '\r') {
                i10 = fontHeight;
                fontHeight = i6;
            } else if (charAt == ' ') {
                i10 = fontHeight;
                fontHeight = (int) (((float) i6) + f2);
            } else if (charAt == '\t') {
                i10 = fontHeight;
                fontHeight = (int) (((float) i6) + (4.0f * f2));
            } else if (charAt < this.minGlyph || charAt > this.maxGlyph) {
                r5 = (int) (charAt > '\u007f' ? f3 : f2);
                if (i6 + r5 > i4) {
                    i8 = (int) (((float) fontHeight) - fontHeight2);
                    fontHeight = i;
                } else {
                    i8 = fontHeight;
                    fontHeight = i6;
                }
                drawUnknowChar(charAt, fontHeight, i8, r5, (int) fontHeight2);
                fontHeight += r5;
                i10 = i8;
            } else {
                int binarySearch = binarySearch(charAt);
                if (binarySearch == -1) {
                    r5 = (int) (charAt > '\u007f' ? f3 : f2);
                    if (i6 + r5 > i4) {
                        i8 = (int) (((float) fontHeight) - fontHeight2);
                        fontHeight = i;
                    } else {
                        i8 = fontHeight;
                        fontHeight = i6;
                    }
                    drawUnknowChar(charAt, fontHeight, i8, r5, (int) fontHeight2);
                    fontHeight += r5;
                    i10 = i8;
                } else if ((this.bitmapIndex[binarySearch] & ImageInfo.ID_card44) == -1) {
                    r5 = (int) (charAt > '\u007f' ? f3 : f2);
                    if (i6 + r5 > i4) {
                        i8 = (int) (((float) fontHeight) - fontHeight2);
                        fontHeight = i;
                    } else {
                        i8 = fontHeight;
                        fontHeight = i6;
                    }
                    drawUnknowChar(charAt, fontHeight, i8, r5, (int) fontHeight2);
                    fontHeight += r5;
                    i10 = i8;
                } else {
                    int i11;
                    int i12;
                    int i13;
                    CTexture cTexture = this.mTextureSheets[this.bitmapIndex[binarySearch] & ImageInfo.ID_card44];
                    if (charAt < '\u007f') {
                        i11 = this.fontWidth / STYLE_SHADOW;
                        i8 = (int) (0.5f + f2);
                    } else {
                        i11 = this.fontWidth;
                        i8 = (int) (0.5f + f3);
                    }
                    if ((i6 + i8) + extraWidth > i4) {
                        i12 = (int) (((float) fontHeight) - fontHeight2);
                        i13 = i;
                    } else {
                        i13 = i6;
                        i12 = fontHeight;
                    }
                    if ((this.style & STYLE_SHADOW) != 0) {
                        int i14 = ((i9 >> 24) & ImageInfo.ID_card44) / STYLE_SHADOW;
                        if (i14 > 5) {
                            cTexture.drawInRect(((int) (((float) i13) + 0.5f)) + extraWidth, ((int) (((float) i12) + 0.5f)) - 1, i8, (int) (0.5f + fontHeight2), this.xOffset[binarySearch] & 65535, this.yOffset[binarySearch] & 65535, i11, this.fontHeight, (int) STYLE_PLAIN, (i14 << 24) | (16777215 & i9));
                        }
                    }
                    cTexture.drawInRect((int) (((float) i13) + 0.5f), (int) (((float) i12) + 0.5f), i8, (int) (0.5f + fontHeight2), this.xOffset[binarySearch] & 65535, this.yOffset[binarySearch] & 65535, i11, this.fontHeight, (int) STYLE_PLAIN, i9);
                    if ((this.style & STYLE_BOLD) != 0) {
                        cTexture.drawInRect(((int) (((float) i13) + 0.5f)) + STYLE_BOLD, (int) (((float) i12) + 0.5f), i8, (int) (0.5f + fontHeight2), this.xOffset[binarySearch] & 65535, this.yOffset[binarySearch] & 65535, i11, this.fontHeight, (int) STYLE_PLAIN, i9);
                    }
                    fontHeight = i13 + (i8 + extraWidth);
                    i10 = i12;
                }
            }
            i5 += STYLE_BOLD;
            i6 = fontHeight;
            fontHeight = i10;
            i7 = i9;
        }
    }

    public void drawScaleMultiText(String str, float f, float f2, float f3, int i) {
        float f4 = f + ((float) i);
        float f5 = (((float) this.fontWidth) * f3) / 2.0f;
        float f6 = f3 * ((float) this.fontWidth);
        float fontHeight = f3 * ((float) getFontHeight());
        int extraWidth = getExtraWidth();
        int i2 = STYLE_PLAIN;
        float f7 = f;
        while (i2 < str.length()) {
            float f8;
            char charAt = str.charAt(i2);
            if (charAt == '\n') {
                f2 -= fontHeight;
                f8 = f;
            } else if (charAt == '\r') {
                f8 = f7;
            } else if (charAt == ' ') {
                f8 = f7 + f5;
            } else if (charAt == '\t') {
                f8 = f7 + (4.0f * f5);
            } else if (charAt < this.minGlyph || charAt > this.maxGlyph) {
                r5 = (int) (charAt > '\u007f' ? f6 : f5);
                if (((float) r5) + f7 > f4) {
                    f2 -= fontHeight;
                    r7 = f;
                } else {
                    r7 = f7;
                }
                drawUnknowChar(charAt, (int) r7, (int) f2, r5, (int) fontHeight);
                f8 = r7 + ((float) r5);
            } else {
                int binarySearch = binarySearch(charAt);
                if (binarySearch == -1) {
                    r5 = (int) (charAt > '\u007f' ? f6 : f5);
                    if (((float) r5) + f7 > f4) {
                        f2 -= fontHeight;
                        r7 = f;
                    } else {
                        r7 = f7;
                    }
                    drawUnknowChar(charAt, (int) r7, (int) f2, r5, (int) fontHeight);
                    f8 = r7 + ((float) r5);
                } else if ((this.bitmapIndex[binarySearch] & ImageInfo.ID_card44) == -1) {
                    r5 = (int) (charAt > '\u007f' ? f6 : f5);
                    if (((float) r5) + f7 > f4) {
                        f2 -= fontHeight;
                        r7 = f;
                    } else {
                        r7 = f7;
                    }
                    drawUnknowChar(charAt, (int) r7, (int) f2, r5, (int) fontHeight);
                    f8 = r7 + ((float) r5);
                } else {
                    int i3;
                    int i4;
                    float f9;
                    CTexture cTexture = this.mTextureSheets[this.bitmapIndex[binarySearch] & ImageInfo.ID_card44];
                    if (charAt < '\u007f') {
                        i3 = this.fontWidth / STYLE_SHADOW;
                        i4 = (int) (0.5f + f5);
                    } else {
                        i3 = this.fontWidth;
                        i4 = (int) (0.5f + f6);
                    }
                    if ((((float) i4) + f7) + ((float) extraWidth) > f4) {
                        f2 -= fontHeight;
                        f9 = f;
                    } else {
                        f9 = f7;
                    }
                    if ((this.style & STYLE_SHADOW) != 0) {
                        int i5 = ((this.mColor >> 24) & ImageInfo.ID_card44) / STYLE_SHADOW;
                        if (i5 > 5) {
                            cTexture.drawInRect(((int) (0.5f + f9)) + extraWidth, ((int) (0.5f + f2)) - 1, i4, (int) (0.5f + fontHeight), this.xOffset[binarySearch] & 65535, this.yOffset[binarySearch] & 65535, i3, this.fontHeight, (int) STYLE_PLAIN, (i5 << 24) | (this.mColor & 16777215));
                        }
                    }
                    cTexture.drawInRect((int) (0.5f + f9), (int) (0.5f + f2), i4, (int) (0.5f + fontHeight), this.xOffset[binarySearch] & 65535, this.yOffset[binarySearch] & 65535, i3, this.fontHeight, (int) STYLE_PLAIN, this.mColor);
                    if ((this.style & STYLE_BOLD) != 0) {
                        cTexture.drawInRect(((int) (0.5f + f9)) + STYLE_BOLD, (int) (0.5f + f2), i4, (int) (0.5f + fontHeight), this.xOffset[binarySearch] & 65535, this.yOffset[binarySearch] & 65535, i3, this.fontHeight, (int) STYLE_PLAIN, this.mColor);
                    }
                    f8 = f9 + ((float) (i4 + extraWidth));
                }
            }
            i2 += STYLE_BOLD;
            f7 = f8;
        }
    }

    public float drawScaleText(String str, float f, float f2, float f3) {
        float f4 = (((float) this.fontWidth) * f3) / 2.0f;
        float f5 = f3 * ((float) this.fontWidth);
        float f6 = f3 * ((float) this.fontHeight);
        float f7 = (((float) zym.m_height) - f2) - f6;
        int extraWidth = getExtraWidth();
        int i = STYLE_PLAIN;
        float f8 = f;
        while (i < str.length()) {
            float f9;
            char charAt = str.charAt(i);
            if (charAt == '\r') {
                f9 = f8;
            } else if (charAt == '\n') {
                f9 = f8;
            } else if (charAt == ' ') {
                f9 = f8 + f4;
            } else if (charAt == '\t') {
                f9 = f8 + (4.0f * f4);
            } else if (charAt < this.minGlyph || charAt > this.maxGlyph) {
                r5 = (int) (charAt > '\u007f' ? f5 : f4);
                drawUnknowChar(charAt, (int) f8, (int) f7, r5, (int) f6);
                f9 = f8 + ((float) r5);
            } else {
                int binarySearch = binarySearch(charAt);
                if (binarySearch == -1) {
                    r5 = (int) (charAt > '\u007f' ? f5 : f4);
                    drawUnknowChar(charAt, (int) f8, (int) f7, r5, (int) f6);
                    f9 = f8 + ((float) r5);
                } else if ((this.bitmapIndex[binarySearch] & ImageInfo.ID_card44) == -1) {
                    r5 = (int) (charAt > '\u007f' ? f5 : f4);
                    drawUnknowChar(charAt, (int) f8, (int) f7, r5, (int) f6);
                    f9 = f8 + ((float) r5);
                } else {
                    int i2;
                    int i3;
                    CTexture cTexture = this.mTextureSheets[this.bitmapIndex[binarySearch] & ImageInfo.ID_card44];
                    if (charAt < '\u007f') {
                        i2 = this.fontWidth / STYLE_SHADOW;
                        i3 = (int) (0.5f + f4);
                    } else {
                        i2 = this.fontWidth;
                        i3 = (int) (0.5f + f5);
                    }
                    if ((this.style & STYLE_SHADOW) != 0) {
                        int i4 = ((this.mColor >> 24) & ImageInfo.ID_card44) / STYLE_SHADOW;
                        if (i4 > 5) {
                            cTexture.drawInRect(((int) (0.5f + f8)) + extraWidth, ((int) (0.5f + f7)) - 1, i3, (int) (0.5f + f6), this.xOffset[binarySearch] & 65535, this.yOffset[binarySearch] & 65535, i2, this.fontHeight, (int) STYLE_PLAIN, (i4 << 24) | (this.mColor & 16777215));
                        }
                    }
                    cTexture.drawInRect((int) (0.5f + f8), (int) (0.5f + f7), i3, (int) (0.5f + f6), this.xOffset[binarySearch] & 65535, this.yOffset[binarySearch] & 65535, i2, this.fontHeight, (int) STYLE_PLAIN, this.mColor);
                    if ((this.style & STYLE_BOLD) != 0) {
                        cTexture.drawInRect(((int) (0.5f + f8)) + STYLE_BOLD, (int) (0.5f + f7), i3, (int) (0.5f + f6), this.xOffset[binarySearch] & 65535, this.yOffset[binarySearch] & 65535, i2, this.fontHeight, (int) STYLE_PLAIN, this.mColor);
                    }
                    f9 = f8 + ((float) (i3 + extraWidth));
                }
            }
            i += STYLE_BOLD;
            f8 = f9;
        }
        return f8 - f;
    }

    public int drawText(String str, int i, int i2) {
        int fontHeight = (zym.m_height - i2) - getFontHeight();
        int extraWidth = getExtraWidth();
        int i3 = STYLE_PLAIN;
        int i4 = i;
        while (i3 < str.length()) {
            int i5;
            char charAt = str.charAt(i3);
            if (charAt == ' ') {
                i5 = i4 + (this.fontWidth / STYLE_SHADOW);
            } else {
                int i6 = charAt == '\t' ? ((this.fontWidth * TAB_SPACE_NUM) / STYLE_SHADOW) + i4 : i4;
                if (charAt == '\r' || charAt == '\n') {
                    i5 = i6;
                } else if (charAt < this.minGlyph || charAt > this.maxGlyph) {
                    r6 = charAt > '\u007f' ? this.fontWidth : this.fontWidth / STYLE_SHADOW;
                    drawUnknowChar(charAt, i6, fontHeight, r6, this.fontHeight);
                    i5 = i6 + r6;
                } else {
                    i4 = binarySearch(charAt);
                    if (i4 == -1) {
                        r6 = charAt > '\u007f' ? this.fontWidth : this.fontWidth / STYLE_SHADOW;
                        drawUnknowChar(charAt, i6, fontHeight, r6, this.fontHeight);
                        i5 = i6 + r6;
                    } else if ((this.bitmapIndex[i4] & ImageInfo.ID_card44) == -1) {
                        r6 = charAt > '\u007f' ? this.fontWidth : this.fontWidth / STYLE_SHADOW;
                        drawUnknowChar(charAt, i6, fontHeight, r6, this.fontHeight);
                        i5 = i6 + r6;
                    } else {
                        int i7;
                        int i8;
                        CTexture cTexture = this.mTextureSheets[this.bitmapIndex[i4] & ImageInfo.ID_card44];
                        if (charAt < '\u007f') {
                            i7 = this.fontWidth / STYLE_SHADOW;
                            i8 = this.fontWidth / STYLE_SHADOW;
                        } else {
                            i7 = this.fontWidth;
                            i8 = this.fontWidth;
                        }
                        if ((this.style & STYLE_SHADOW) != 0) {
                            i5 = ((this.mColor >> 24) & ImageInfo.ID_card44) / STYLE_SHADOW;
                            if (i5 > 5) {
                                cTexture.drawInRect(i6 + extraWidth, fontHeight - 1, i8, this.fontHeight, this.xOffset[i4] & 65535, this.yOffset[i4] & 65535, i7, this.fontHeight, (int) STYLE_PLAIN, (this.mColor & 16777215) | (i5 << 24));
                            }
                        }
                        cTexture.drawInRect(i6, fontHeight, i8, this.fontHeight, this.xOffset[i4] & 65535, this.yOffset[i4] & 65535, i7, this.fontHeight, (int) STYLE_PLAIN, this.mColor);
                        if ((this.style & STYLE_BOLD) != 0) {
                            cTexture.drawInRect(i6 + STYLE_BOLD, fontHeight, i8, this.fontHeight, this.xOffset[i4] & 65535, this.yOffset[i4] & 65535, i7, this.fontHeight, (int) STYLE_PLAIN, this.mColor);
                        }
                        i5 = i6 + (i8 + extraWidth);
                    }
                }
            }
            i3 += STYLE_BOLD;
            i4 = i5;
        }
        return i4 - i;
    }

    public final CTexture[] getCTextures() {
        return this.mTextureSheets;
    }

    public int getCharWidth(char c) {
        return c == '\t' ? (this.fontWidth * TAB_SPACE_NUM) / STYLE_SHADOW : (c == '\r' || c == '\n') ? STYLE_PLAIN : c < '\u0080' ? (this.fontWidth / STYLE_SHADOW) + getExtraWidth() : this.fontWidth + getExtraWidth();
    }

    public int getColor() {
        return this.mColor;
    }

    public int getFontHeight() {
        return this.fontHeight + getExtraHeight();
    }

    public final int getNoScaledTextWidth(String str) {
        return getTextWidth(str, STYLE_PLAIN, str.length());
    }

    public final int getScaledTextWidth(String str, float f) {
        return (int) (((float) getTextWidth(str, STYLE_PLAIN, str.length())) * f);
    }

    public float getTextWidth(String str, int i, int i2, float f) {
        float f2 = 0.0f;
        int extraWidth = getExtraWidth();
        while (i < i2) {
            char charAt = str.charAt(i);
            if (!(charAt == '\r' || charAt == '\n')) {
                if (charAt == ' ') {
                    f2 += (((float) this.fontWidth) * f) / 2.0f;
                } else if (charAt == '\t') {
                    f2 += ((4.0f * f) * ((float) this.fontWidth)) / 2.0f;
                } else if (charAt < this.minGlyph || charAt > this.maxGlyph) {
                    f2 += ((float) (charAt > '\u007f' ? this.fontWidth : this.fontWidth / STYLE_SHADOW)) * f;
                } else {
                    int binarySearch = binarySearch(charAt);
                    if (binarySearch == -1) {
                        f2 += ((float) (charAt > '\u007f' ? this.fontWidth : this.fontWidth / STYLE_SHADOW)) * f;
                    } else if ((this.bitmapIndex[binarySearch] & ImageInfo.ID_card44) == -1) {
                        f2 += ((float) (charAt > '\u007f' ? this.fontWidth : this.fontWidth / STYLE_SHADOW)) * f;
                    } else {
                        f2 = charAt < '\u007f' ? f2 + (((((float) this.fontWidth) * f) / 2.0f) + ((float) extraWidth)) : f2 + ((((float) this.fontWidth) * f) + ((float) extraWidth));
                    }
                }
            }
            i += STYLE_BOLD;
        }
        return f2;
    }

    public final int getTextWidth(String str) {
        return (int) (MainActivity.scaled * ((float) getTextWidth(str, STYLE_PLAIN, str.length())));
    }

    public int getTextWidth(String str, int i, int i2) {
        int i3 = STYLE_PLAIN;
        int extraWidth = getExtraWidth();
        while (i < i2) {
            char charAt = str.charAt(i);
            if (!(charAt == '\r' || charAt == '\n')) {
                if (charAt == ' ') {
                    i3 += this.fontWidth / STYLE_SHADOW;
                } else if (charAt == '\t') {
                    i3 += (this.fontWidth * TAB_SPACE_NUM) / STYLE_SHADOW;
                } else if (charAt < this.minGlyph || charAt > this.maxGlyph) {
                    i3 += charAt > '\u007f' ? this.fontWidth : this.fontWidth / STYLE_SHADOW;
                } else {
                    int binarySearch = binarySearch(charAt);
                    if (binarySearch == -1) {
                        i3 += charAt > '\u007f' ? this.fontWidth : this.fontWidth / STYLE_SHADOW;
                    } else if ((this.bitmapIndex[binarySearch] & ImageInfo.ID_card44) == -1) {
                        i3 += charAt > '\u007f' ? this.fontWidth : this.fontWidth / STYLE_SHADOW;
                    } else {
                        i3 = charAt < '\u007f' ? i3 + ((this.fontWidth / STYLE_SHADOW) + extraWidth) : i3 + (this.fontWidth + extraWidth);
                    }
                }
            }
            i += STYLE_BOLD;
        }
        return i3;
    }

    public void remove() {
        this.xOffset = null;
        this.yOffset = null;
        this.bitmapIndex = null;
        this._mRemapTable = null;
        if (this.mTextureSheets != null) {
            for (int i = STYLE_PLAIN; i < this.mTextureSheets.length; i += STYLE_BOLD) {
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

    public void setStyle(int i) {
        this.style = i;
    }
}
