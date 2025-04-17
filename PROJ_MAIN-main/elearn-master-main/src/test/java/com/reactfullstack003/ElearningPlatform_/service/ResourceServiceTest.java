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

import com.reactjavafullstack003.E_Learning_Platform_003.model.Resource;
import com.reactjavafullstack003.E_Learning_Platform_003.repository.ResourceRepository;
import com.reactjavafullstack003.E_Learning_Platform_003.service.ResourceService;

class ResourceServiceTest {

    @Mock
    private ResourceRepository resourceRepository;

    @InjectMocks
    private ResourceService resourceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllResources() {
        Resource resource1 = new Resource();
        resource1.setResourceId(1);
        resource1.setTitle("Resource 1");
        resource1.setResourceType("Video");
        resource1.setResourceUrl("http://example.com/resource1");
        resource1.setCourseId(1);

        Resource resource2 = new Resource();
        resource2.setResourceId(2);
        resource2.setTitle("Resource 2");
        resource2.setResourceType("Document");
        resource2.setResourceUrl("http://example.com/resource2");
        resource2.setCourseId(1);

        when(resourceRepository.findAll()).thenReturn(Arrays.asList(resource1, resource2));

        List<Resource> resources = resourceService.getAllResources();
        assertEquals(2, resources.size());
        assertEquals("Resource 1", resources.get(0).getTitle());
        assertEquals("Resource 2", resources.get(1).getTitle());
    }

    @Test
    void testGetResourcesByCourseId() {
        Resource resource1 = new Resource();
        resource1.setResourceId(1);
        resource1.setTitle("Resource 1");
        resource1.setResourceType("Video");
        resource1.setResourceUrl("http://example.com/resource1");
        resource1.setCourseId(1);

        Resource resource2 = new Resource();
        resource2.setResourceId(2);
        resource2.setTitle("Resource 2");
        resource2.setResourceType("Document");
        resource2.setResourceUrl("http://example.com/resource2");
        resource2.setCourseId(1);

        when(resourceRepository.findByCourseId(1)).thenReturn(Arrays.asList(resource1, resource2));

        List<Resource> resources = resourceService.getResourcesByCourseId(1);
        assertEquals(2, resources.size());
        assertEquals("Resource 1", resources.get(0).getTitle());
        assertEquals("Resource 2", resources.get(1).getTitle());
    }

    @Test
    void testAddResource() {
        Resource resource = new Resource();
        resource.setResourceId(1);
        resource.setTitle("Resource 1");
        resource.setResourceType("Video");
        resource.setResourceUrl("http://example.com/resource1");
        resource.setCourseId(1);

        when(resourceRepository.save(resource)).thenReturn(resource);

        Resource savedResource = resourceService.addResource(resource);
        assertEquals("Resource 1", savedResource.getTitle());
    }

    @Test
    void testUpdateResource() {
        Resource existingResource = new Resource();
        existingResource.setResourceId(1);
        existingResource.setTitle("Resource 1");
        existingResource.setResourceType("Video");
        existingResource.setResourceUrl("http://example.com/resource1");
        existingResource.setCourseId(1);

        Resource updatedResource = new Resource();
        updatedResource.setResourceId(1);
        updatedResource.setTitle("Updated Resource");
        updatedResource.setResourceType("Document");
        updatedResource.setResourceUrl("http://example.com/updatedresource");
        updatedResource.setCourseId(1);

        when(resourceRepository.findById(1)).thenReturn(Optional.of(existingResource));
        when(resourceRepository.save(existingResource)).thenReturn(updatedResource);

        Resource result = resourceService.updateResource(1, updatedResource);
        assertEquals("Updated Resource", result.getTitle());
        assertEquals("Document", result.getResourceType());
    }

    @Test
    void testDeleteResource() {
        when(resourceRepository.existsById(1)).thenReturn(true);
        doNothing().when(resourceRepository).deleteById(1);

        resourceService.deleteResource(1);
        verify(resourceRepository, times(1)).deleteById(1);
    }
}
