package com.dailycodework.dreamshops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodework.dreamshops.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
