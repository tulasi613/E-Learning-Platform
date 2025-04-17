package com.reactfullstack003.ElearningPlatform_.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.reactjavafullstack003.E_Learning_Platform_003.model.Video;
import com.reactjavafullstack003.E_Learning_Platform_003.repository.VideoRepository;
import com.reactjavafullstack003.E_Learning_Platform_003.service.VideoService;

class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideoService videoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllVideos() {
        Video video1 = new Video();
        video1.setVideoId(1);
        video1.setTitle("Video 1");
        video1.setVideoUrl("http://example.com/video1");
        video1.setCourseId(1);
        video1.setCompleted(false);

        Video video2 = new Video();
        video2.setVideoId(2);
        video2.setTitle("Video 2");
        video2.setVideoUrl("http://example.com/video2");
        video2.setCourseId(1);
        video2.setCompleted(false);

        when(videoRepository.findAll()).thenReturn(Arrays.asList(video1, video2));

        List<Video> videos = videoService.getAllVideos();
        assertEquals(2, videos.size());
        assertEquals("Video 1", videos.get(0).getTitle());
        assertEquals("Video 2", videos.get(1).getTitle());
    }

    @Test
    void testGetVideosByCourseId() {
        Video video1 = new Video();
        video1.setVideoId(1);
        video1.setTitle("Video 1");
        video1.setVideoUrl("http://example.com/video1");
        video1.setCourseId(1);
        video1.setCompleted(false);

        Video video2 = new Video();
        video2.setVideoId(2);
        video2.setTitle("Video 2");
        video2.setVideoUrl("http://example.com/video2");
        video2.setCourseId(1);
        video2.setCompleted(false);

        when(videoRepository.findByCourseId(1)).thenReturn(Arrays.asList(video1, video2));

        List<Video> videos = videoService.getVideosByCourseId(1);
        assertEquals(2, videos.size());
        assertEquals("Video 1", videos.get(0).getTitle());
        assertEquals("Video 2", videos.get(1).getTitle());
    }

    @Test
    void testAddVideo() {
        Video video = new Video();
        video.setVideoId(1);
        video.setTitle("Video 1");
        video.setVideoUrl("http://example.com/video1");
        video.setCourseId(1);
        video.setCompleted(false);

        when(videoRepository.save(video)).thenReturn(video);

        Video savedVideo = videoService.addVideo(video);
        assertEquals("Video 1", savedVideo.getTitle());
    }



    @Test
    void testMarkAsCompleted() {
        Video video = new Video();
        video.setVideoId(1);
        video.setTitle("Video 1");
        video.setVideoUrl("http://example.com/video1");
        video.setCourseId(1);
        video.setCompleted(false);

        when(videoRepository.findById(1)).thenReturn(Optional.of(video));
        when(videoRepository.save(video)).thenReturn(video);

        Video completedVideo = videoService.markAsCompleted(1);
        assertTrue(completedVideo.isCompleted());
    }
}