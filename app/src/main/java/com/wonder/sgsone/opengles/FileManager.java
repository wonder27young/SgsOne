package com.wonder.sgsone.opengles;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.support.v4.view.MotionEventCompat;
import com.qihoopp.qcoinpay.common.C2850e;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public final class FileManager {
    private static Context context;

    public static void DeleteRecord(String str) {
        context.deleteFile(str);
    }

    public static int GetAssetFileSize(String str) {
        InputStream inputStream = null;
        int i = -1;
        try {
            inputStream = getResourceAsInputStream(str);
            i = inputStream.available();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e2) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e32) {
                    e32.printStackTrace();
                }
            }
        }
        return i;
    }

    public static final AssetFileDescriptor getAssetFileDescriptor(String str) {
        try {
            return context.getAssets().openFd(str);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static InputStream getRecordAsInputStream(String str) {
        try {
            return context.openFileInput(str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static FileOutputStream getRecordAsOutputStream(String str) {
        try {
            return context.openFileOutput(str, 0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final InputStream getResourceAsInputStream(String str) {
        try {
            return context.getAssets().open(str);
        } catch (IOException e) {
            return null;
        }
    }

    public static boolean isResourceFileExists(String str) {
        try {
            InputStream open = context.getAssets().open(str);
            if (open != null) {
                try {
                    open.close();
                } catch (IOException e) {
                }
            }
            return true;
        } catch (IOException e2) {
            return false;
        }
    }

    public static final boolean readBoolean(InputStream inputStream) throws Exception {
        return inputStream.read() != 0;
    }

    public static final char readChar(InputStream inputStream) throws Exception {
        return (char) ((inputStream.read() & ImageInfo.ID_card44) | ((inputStream.read() & ImageInfo.ID_card44) << 8));
    }

    public static final void readData(InputStream inputStream, byte[] bArr, int i, int i2) throws Exception {
        int i3 = 0;
        while (i3 < i2) {
            i3 += inputStream.read(bArr, i + i3, i2 - i3);
        }
    }

    public static final int readFileData(int i, byte[] bArr, int i2) {
        try {
            readData(context.getResources().openRawResource(i), bArr, 0, i2);
            return i2;
        } catch (Exception e) {
            System.out.println("read " + i + "error!");
            return 0;
        }
    }

    public static final byte[] readFileData(int i) {
        InputStream openRawResource;
        InputStream inputStream;
        Throwable th;
        try {
            openRawResource = context.getResources().openRawResource(i);
            try {
                int available = openRawResource.available();
                byte[] bArr = new byte[available];
                readData(openRawResource, bArr, 0, available);
                if (openRawResource == null) {
                    return bArr;
                }
                try {
                    openRawResource.close();
                    return bArr;
                } catch (IOException e) {
                    e.printStackTrace();
                    return bArr;
                }
            } catch (Exception e2) {
                inputStream = openRawResource;
                try {
                    System.out.println("read " + i + "error!");
                    if (inputStream != null) {
                        return null;
                    }
                    try {
                        inputStream.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    return null;
                } catch (Throwable th2) {
                    openRawResource = inputStream;
                    th = th2;
                    if (openRawResource != null) {
                        try {
                            openRawResource.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (openRawResource != null) {
                    openRawResource.close();
                }
                throw th;
            }
        } catch (Exception e5) {
            inputStream = null;
            System.out.println("read " + i + "error!");
            if (inputStream != null) {
                return null;
            }
            inputStream.close();
            return null;
        } catch (Throwable th4) {
            th = th4;
            openRawResource = null;
            if (openRawResource != null) {
                openRawResource.close();
            }
            throw th;
        }
    }

    public static final byte[] readFileData(String str) {
        InputStream open;
        InputStream inputStream;
        Throwable th;
        try {
            open = context.getAssets().open(str);
            try {
                int available = open.available();
                byte[] bArr = new byte[available];
                readData(open, bArr, 0, available);
                if (open == null) {
                    return bArr;
                }
                try {
                    open.close();
                    return bArr;
                } catch (IOException e) {
                    e.printStackTrace();
                    return bArr;
                }
            } catch (Exception e2) {
                inputStream = open;
                try {
                    System.out.println("read " + str + "error!");
                    if (inputStream != null) {
                        return null;
                    }
                    try {
                        inputStream.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    return null;
                } catch (Throwable th2) {
                    open = inputStream;
                    th = th2;
                    if (open != null) {
                        try {
                            open.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (open != null) {
                    open.close();
                }
                throw th;
            }
        } catch (Exception e5) {
            inputStream = null;
            System.out.println("read " + str + "error!");
            if (inputStream != null) {
                return null;
            }
            inputStream.close();
            return null;
        } catch (Throwable th4) {
            th = th4;
            open = null;
            if (open != null) {
                open.close();
            }
            throw th;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final byte[] readFileMuitiData(java.lang.String r9) {
        /*
        r3 = 0;
        r1 = 0;
        r0 = GetAssetFileSize(r9);
        if (r0 < 0) goto L_0x000d;
    L_0x0008:
        r0 = readFileData(r9);
    L_0x000c:
        return r0;
    L_0x000d:
        r0 = 46;
        r0 = r9.indexOf(r0);
        r2 = -1;
        if (r0 != r2) goto L_0x001a;
    L_0x0016:
        r0 = r9.length();
    L_0x001a:
        r2 = new java.lang.StringBuilder;
        r4 = r9.substring(r1, r0);
        r4 = java.lang.String.valueOf(r4);
        r2.<init>(r4);
        r4 = "_";
        r2 = r2.append(r4);
        r6 = r2.toString();
        r7 = r9.substring(r0);
        r0 = r1;
        r2 = r1;
    L_0x0037:
        r4 = new java.lang.StringBuilder;
        r5 = java.lang.String.valueOf(r6);
        r4.<init>(r5);
        r4 = r4.append(r2);
        r4 = r4.append(r7);
        r4 = r4.toString();
        r4 = GetAssetFileSize(r4);
        if (r4 >= 0) goto L_0x005d;
    L_0x0052:
        if (r2 != 0) goto L_0x0061;
    L_0x0054:
        r0 = java.lang.System.out;
        r1 = "---------multiple file error--------";
        r0.println(r1);
        r0 = r3;
        goto L_0x000c;
    L_0x005d:
        r0 = r0 + r4;
        r2 = r2 + 1;
        goto L_0x0037;
    L_0x0061:
        r0 = new byte[r0];
        r5 = r1;
    L_0x0064:
        if (r1 >= r2) goto L_0x000c;
    L_0x0066:
        r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008d }
        r8 = java.lang.String.valueOf(r6);	 Catch:{ Exception -> 0x008d }
        r4.<init>(r8);	 Catch:{ Exception -> 0x008d }
        r4 = r4.append(r1);	 Catch:{ Exception -> 0x008d }
        r4 = r4.append(r7);	 Catch:{ Exception -> 0x008d }
        r4 = r4.toString();	 Catch:{ Exception -> 0x008d }
        r4 = getResourceAsInputStream(r4);	 Catch:{ Exception -> 0x008d }
        r8 = r4.available();	 Catch:{ Exception -> 0x00b4, all -> 0x00b1 }
        readData(r4, r0, r5, r8);	 Catch:{ Exception -> 0x00b4, all -> 0x00b1 }
        r5 = r5 + r8;
        r4.close();	 Catch:{ Exception -> 0x00b4, all -> 0x00b1 }
        r1 = r1 + 1;
        goto L_0x0064;
    L_0x008d:
        r1 = move-exception;
    L_0x008e:
        r2 = java.lang.System.out;	 Catch:{ all -> 0x00a5 }
        r4 = "read multiple bbm file error!";
        r2.println(r4);	 Catch:{ all -> 0x00a5 }
        r1.printStackTrace();	 Catch:{ all -> 0x00a5 }
        if (r3 == 0) goto L_0x000c;
    L_0x009a:
        r3.close();	 Catch:{ IOException -> 0x009f }
        goto L_0x000c;
    L_0x009f:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x000c;
    L_0x00a5:
        r0 = move-exception;
    L_0x00a6:
        if (r3 == 0) goto L_0x00ab;
    L_0x00a8:
        r3.close();	 Catch:{ IOException -> 0x00ac }
    L_0x00ab:
        throw r0;
    L_0x00ac:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00ab;
    L_0x00b1:
        r0 = move-exception;
        r3 = r4;
        goto L_0x00a6;
    L_0x00b4:
        r1 = move-exception;
        r3 = r4;
        goto L_0x008e;
        */
        throw new UnsupportedOperationException("Method not decompiled: library.opengles.FileManager.readFileMuitiData(java.lang.String):byte[]");
    }

    public static final int readInt(InputStream inputStream) throws Exception {
        return (((inputStream.read() & ImageInfo.ID_card44) | ((inputStream.read() << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | ((inputStream.read() << 16) & 16711680)) | ((inputStream.read() << 24) & C2850e.f8721k);
    }

    public static final short readShort(InputStream inputStream) throws Exception {
        return (short) ((inputStream.read() & ImageInfo.ID_card44) | ((inputStream.read() & ImageInfo.ID_card44) << 8));
    }

    public static final String readUTF(InputStream inputStream) throws Exception {
        int readShort = 65535 & readShort(inputStream);
        if (readShort <= 0) {
            return null;
        }
        byte[] bArr = new byte[readShort];
        readData(inputStream, bArr, 0, readShort);
        try {
            return new String(bArr, 0, readShort, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final void setContext(Context context) {
        context = context;
    }
}
