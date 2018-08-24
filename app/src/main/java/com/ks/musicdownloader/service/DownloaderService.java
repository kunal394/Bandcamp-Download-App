package com.ks.musicdownloader.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.ks.musicdownloader.ArtistInfo;
import com.ks.musicdownloader.songsprocessors.SongsDownloader;

import java.lang.ref.WeakReference;

public class DownloaderService extends DownloadService<ArtistInfo, ArtistInfo> {

    private static final String TAG = DownloaderService.class.getSimpleName();
    private final IBinder binder = new LocalBinder();
    private SongsDownloader songsDownloader;

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onTaskCompletion() {
        stopSelf();
    }

    @Override
    public void startDownload(ArtistInfo artistInfo) {
        new SongsDownloadTask(this).execute(artistInfo);
    }

    public class LocalBinder extends Binder {
        public DownloaderService getService() {
            return DownloaderService.this;
        }
    }

    public SongsDownloader getSongsDownloader() {
        return songsDownloader;
    }

    public void setSongsDownloader(SongsDownloader songsDownloader) {
        this.songsDownloader = songsDownloader;
    }

    private static class SongsDownloadTask extends AsyncTask<ArtistInfo, String, ArtistInfo> {

        // making this class static t avoid memory leak, since now static inner class can not access the members of its outer class
        // and keeping a weak reference to the service in order to use the service methods and variables from inside this class
        private final WeakReference<DownloaderService> service;

        SongsDownloadTask(DownloaderService service) {
            this.service = new WeakReference<>(service);
        }

        @Override
        protected ArtistInfo doInBackground(ArtistInfo... artistInfos) {
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            DownloaderService downloaderService = service.get();
            if (downloaderService == null) {
                Log.d(TAG, "SongsDownloadTask onProgressUpdate(): Service found null!");
                return ;
            }
            downloaderService.getDownloadCallback().updateFromDownload(null);
        }

        @Override
        protected void onPostExecute(ArtistInfo artistInfo) {
            super.onPostExecute(artistInfo);
            DownloaderService downloaderService = service.get();
            if (downloaderService == null) {
                Log.d(TAG, "SongsDownloadTask onPostExecute(): Service found null!");
                return;
            }
            downloaderService.getDownloadCallback().finishDownloading();
            downloaderService.onTaskCompletion();
        }
    }
}