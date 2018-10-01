package com.ks.musicdownloader.service;

import android.app.Service;

import com.ks.musicdownloader.common.DownloadCallback;

public abstract class BaseDownloadService<Input, Res> extends Service {

    protected DownloadCallback<Res> downloadCallback;

    public void setDownloadCallback(DownloadCallback<Res> downloadCallback) {
        this.downloadCallback = downloadCallback;
    }

    public DownloadCallback<Res> getDownloadCallback() {
        return downloadCallback;
    }

    public abstract void onTaskCompletion();

    public abstract void startDownload(Input input);
}
