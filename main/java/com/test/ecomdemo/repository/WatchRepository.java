package com.test.ecomdemo.repository;

import com.test.ecomdemo.model.Watch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchRepository extends JpaRepository<Watch, Integer> {
}
