package com.reactjavafullstack003.E_Learning_Platform_003.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactjavafullstack003.E_Learning_Platform_003.Exceptions.ResourceNotFoundException;
import com.reactjavafullstack003.E_Learning_Platform_003.model.Resource;
import com.reactjavafullstack003.E_Learning_Platform_003.repository.ResourceRepository;
import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    /**
     * Fetch all resources from the repository.
     */
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    /**
     * Fetch resources by course ID from the repository.
     * Throws RuntimeException if no resources are found for the given course ID.
     */
    public List<Resource> getResourcesByCourseId(int courseId) {
        if (resourceRepository.findByCourseId(courseId).isEmpty()) {
            throw new RuntimeException("No resources found for course with ID: " + courseId);
        }
        return resourceRepository.findByCourseId(courseId);
    }

    /**
     * Add a new resource to the repository.
     * Throws ResourceNotFoundException if a resource with the same URL already exists.
     */
    public Resource addResource(Resource resource) {
        if (resourceRepository.findByResourceUrl(resource.getResourceUrl()) != null) {
            throw new ResourceNotFoundException("Resource with URL: " + resource.getResourceUrl() + " already exists");
        }
        return resourceRepository.save(resource);
    }

    /**
     * Update an existing resource in the repository.
     * Throws ResourceNotFoundException if the resource is not found.
     */
    public Resource updateResource(int resourceId, Resource resource) {
        Resource existingResource = resourceRepository.findById(resourceId).orElse(null);
        if (existingResource == null) {
            throw new ResourceNotFoundException("Resource not found with ID: " + resourceId);
        }
        existingResource.setResourceId(resourceId);
        existingResource.setTitle(resource.getTitle());
        existingResource.setResourceType(resource.getResourceType());
        existingResource.setResourceUrl(resource.getResourceUrl());
        existingResource.setCourseId(resource.getCourseId());
        return resourceRepository.save(existingResource);
    }

    /**
     * Delete a resource by ID from the repository.
     */
    public void deleteResource(int resourceId) {
        resourceRepository.deleteById(resourceId);
    }

    public Resource getResourceById(int resourceId) {
        return resourceRepository.findById(resourceId)
            .orElseThrow(() -> new ResourceNotFoundException("Resource not found with ID: " + resourceId));
    }
    
}