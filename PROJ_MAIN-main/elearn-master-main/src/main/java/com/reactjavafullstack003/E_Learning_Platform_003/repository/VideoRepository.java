package com.reactjavafullstack003.E_Learning_Platform_003.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reactjavafullstack003.E_Learning_Platform_003.model.Video;
@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
 
    List<Video> findByCourseId(int courseID);
    Optional<Video> findByTitle(String title);



}