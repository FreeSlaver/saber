package com.song.sojob.xiami;

import com.alibaba.fastjson.JSON;

/**
 * Created by 001844 on 2018/1/24.
 */
public class XiamiSongVo {
    private String name;

    private String href;

    private String mvHref;

    private String artist;

    private String artistHref;

    private String album;

    private String albumHref;

    private String lyricists;

    private String composers;

    private String lyric;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getMvHref() {
        return mvHref;
    }

    public void setMvHref(String mvHref) {
        this.mvHref = mvHref;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getLyricists() {
        return lyricists;
    }

    public void setLyricists(String lyricists) {
        this.lyricists = lyricists;
    }

    public String getArtistHref() {
        return artistHref;
    }

    public void setArtistHref(String artistHref) {
        this.artistHref = artistHref;
    }

    public String getComposers() {
        return composers;
    }

    public void setComposers(String composers) {
        this.composers = composers;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public String getAlbumHref() {
        return albumHref;
    }

    public void setAlbumHref(String albumHref) {
        this.albumHref = albumHref;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
