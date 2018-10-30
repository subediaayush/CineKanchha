package com.cinekancha.utils;

import android.util.Log;

import com.cinekancha.BuildConfig;
import com.cinekancha.entities.model.HomeData;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyCache {

    public static DiskLruCache mDiskLruCache;
    public static Writer journalWriter;

    public MyCache(File directory) throws IOException {
        mDiskLruCache = DiskLruCache.open(directory, BuildConfig.VERSION_CODE, 1, 20 * 2 ^ 20);
        mDiskLruCache.setMaxSize(1000);
    }

    public static void put(String key, Object object) throws IOException {
        String sanitizedKey = getDiskHashKey(key);
        DiskLruCache.Editor creator = mDiskLruCache.edit(String.valueOf(key.hashCode()));
        if (creator != null) {
            try {
                ObjectOutputStream objOutStream = new ObjectOutputStream(new BufferedOutputStream(creator.newOutputStream(0)));
                objOutStream.writeObject(object);
                objOutStream.close();
                mDiskLruCache.flush();
                creator.commit();
                Log.d("ContainKey", String.valueOf(containsKey(String.valueOf(key.hashCode()))));

            } catch (Exception e) {
                e.printStackTrace();
                creator.abort();
            }
            Log.d("ContainKey", String.valueOf(containsKey(String.valueOf(key.hashCode()))));

        }
    }


    public static Object get(String key) throws IOException, ClassNotFoundException {
        String sanitizedKey = getDiskHashKey(key);

        Object result = null;
        if (mDiskLruCache != null) {

            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(String.valueOf(key.hashCode()));
            if (snapshot != null) {
                InputStream inputStream = snapshot.getInputStream(0);
                InputStream buffer = new BufferedInputStream(inputStream);
                ObjectInput input = new ObjectInputStream(buffer);
                result = input.readObject();
                snapshot.close();
            }
        }
        return result;
    }

    public static boolean containsKey(String key) {

        boolean contained = false;
        DiskLruCache.Snapshot snapshot = null;
        try {
            snapshot = mDiskLruCache.get(key);
            contained = snapshot != null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (snapshot != null) {
                snapshot.close();
            }
        }

        return contained;

    }

    public static String getDiskHashKey(String s) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(Charset.forName("US-ASCII")), 0, s.length());
            byte[] magnitude = digest.digest();
            BigInteger bi = new BigInteger(1, magnitude);
            String hash = String.format("%0" + (magnitude.length << 1) + "x", bi);
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}
