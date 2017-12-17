package com.github.kirivasile.videoservice;

import com.github.kirivasile.videoservice.model.Video;
import com.github.kirivasile.videoservice.server.IAPI;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class VideoUnitTest {
    @Test
    public void convertingIsCorrect() throws Exception {
        IAPI.VideoJSON buf = new IAPI.VideoJSON();
        buf.id = 0;
        buf.name = "0";
        buf.videoUrl = "video0";
        IAPI.VideoJSON buf2 = new IAPI.VideoJSON();
        buf2.id = 0;
        buf2.name = "0";
        buf2.videoUrl = "video1";
        IAPI.VideoJSON[] videoJSONs = new IAPI.VideoJSON[2];
        videoJSONs[0] = buf;
        videoJSONs[1] = buf2;
        List<Video> converted = Video.fromJSON(videoJSONs);
        assertEquals(converted.get(0).getVideoUrl(), "video0");
        assertEquals(converted.get(1).getVideoUrl(), "video1");
    }
}
