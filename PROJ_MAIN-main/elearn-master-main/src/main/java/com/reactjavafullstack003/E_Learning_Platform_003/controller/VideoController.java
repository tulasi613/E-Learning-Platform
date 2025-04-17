package com.reactjavafullstack003.E_Learning_Platform_003.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactjavafullstack003.E_Learning_Platform_003.model.Video;
import com.reactjavafullstack003.E_Learning_Platform_003.service.NotificationService;
import com.reactjavafullstack003.E_Learning_Platform_003.service.VideoService;

import jakarta.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

@Validated
@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private static final Logger logger = LogManager.getLogger(VideoController.class);

    @Autowired
    private VideoService videoService;

    @Autowired
    private NotificationService notificationService ;

    /**
    * Get all videos.
     */
    @GetMapping
    public List<Video> getAllVideos() {
        logger.info("Fetching all videos");
        return videoService.getAllVideos();
    }

    /**
     * Get videos by course ID.
     */
    @GetMapping("/course/{courseId}")
    public List<Video> getVideosByCourse(@PathVariable int courseId) {
        logger.info("Fetching videos for course with ID: {}", courseId);
        return videoService.getVideosByCourseId(courseId);
    }

    /**
     * Add a new video.
     */
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @PostMapping("/save")
    public ResponseEntity<String> addVideo(@Valid @RequestBody Video video) {
        logger.info("Adding video");
        notificationService.saveNotification("Video added");
        videoService.addVideo(video);
        return ResponseEntity.ok("Video added successfully");
    }

    /**
     * Delete a video by ID.
     */
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @DeleteMapping("/{videoId}")
    public ResponseEntity<String> deleteVideo(@PathVariable int videoId) {
        logger.info("Deleting video with ID: {}", videoId);
        notificationService.saveNotification("Video deleted");
        videoService.deleteVideo(videoId);
        return ResponseEntity.ok("Video deleted successfully");
    }

    /**
     * Mark a video as completed by ID.
     * FRONTEND
     */
    @PutMapping("/complete/{videoId}")
    public Video markAsCompleted(@PathVariable int videoId) {
        logger.info("Marking video as completed with ID: {}", videoId);
        notificationService.saveNotification("Video marked as completed");
        return videoService.markAsCompleted(videoId);
    }

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
@PutMapping("/{videoId}")
public ResponseEntity<String> updateVideo(@PathVariable int videoId, @Valid @RequestBody Video video) {
    logger.info("Updating video with ID: {}", videoId);
    try {
        Video updatedVideo = videoService.updateVideo(videoId, video);
        if (updatedVideo != null) {
            notificationService.saveNotification("Video updated successfully");
            return new ResponseEntity<>("Video updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update video", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@GetMapping("/{videoId}")
public ResponseEntity<Video> getVideoById(@PathVariable int videoId) {
    logger.info("Fetching video with ID: {}", videoId);
    Video video = videoService.getVideoById(videoId);
    return new ResponseEntity<>(video, HttpStatus.OK);
}

@PreAuthorize("hasAuthority('USER')")
@PutMapping("/{videoId}/mark-watched")
public ResponseEntity<String> markVideoAsWatched(@PathVariable int videoId) {
    videoService.markAsCompleted(videoId);
    return ResponseEntity.ok("Video marked as watched");
}


}