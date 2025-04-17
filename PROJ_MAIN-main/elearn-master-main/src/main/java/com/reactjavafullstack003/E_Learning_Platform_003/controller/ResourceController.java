package com.reactjavafullstack003.E_Learning_Platform_003.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.reactjavafullstack003.E_Learning_Platform_003.model.Resource;
import com.reactjavafullstack003.E_Learning_Platform_003.service.NotificationService;
import com.reactjavafullstack003.E_Learning_Platform_003.service.ResourceService;

import jakarta.validation.Valid;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Validated
@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private static final Logger logger = LogManager.getLogger(ResourceController.class);

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private NotificationService notificationService;

    /**
     * Get all resources.
     */
    @GetMapping
    public List<Resource> getAllResources() {
        logger.info("Fetching all resources");
        return resourceService.getAllResources();
    }

    /**
     * Get resources by course ID.
     */
    @GetMapping("/course/{courseId}")
    public List<Resource> getResourcesByCourse(@PathVariable int courseId) {
        logger.info("Fetching resources for course with ID: {}", courseId);
        return resourceService.getResourcesByCourseId(courseId);
    }

    /**
     * Add a new resource.
     */
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @PostMapping("/save")
    public ResponseEntity<String> addResource(@Valid @RequestBody Resource resource) {
        logger.info("Adding resource");
        notificationService.saveNotification("Resource added");
        resourceService.addResource(resource);
        return ResponseEntity.ok("Resource added successfully");
    }

    /**
     * Update an existing resource.
     */
@PreAuthorize("hasAuthority('INSTRUCTOR')")
@PutMapping("/{resourceId}")
public ResponseEntity<String> updateResource(
    @PathVariable int resourceId,
    @Valid @RequestBody Resource resource
) {
    try {
        Resource updatedResource = resourceService.updateResource(resourceId, resource);
        if (updatedResource != null) {
            notificationService.saveNotification("Resource updated successfully");
            return new ResponseEntity<>("Resource updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update resource", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    /**
     * Delete a resource by ID.
     */
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @DeleteMapping("/{resourceId}")
    public ResponseEntity<String> deleteResource(@PathVariable int resourceId) {
        logger.info("Deleting resource with ID: {}", resourceId);
        notificationService.saveNotification("Resource deleted");
        resourceService.deleteResource(resourceId);
        return ResponseEntity.ok("Resource deleted successfully");
    }

    @GetMapping("/{resourceId}")
public ResponseEntity<Resource> getResourceById(@PathVariable int resourceId) {
    Resource resource = resourceService.getResourceById(resourceId);
    return new ResponseEntity<>(resource, HttpStatus.OK);
}

}