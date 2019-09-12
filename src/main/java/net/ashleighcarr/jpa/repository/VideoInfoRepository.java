package net.ashleighcarr.jpa.repository;

import net.ashleighcarr.jpa.entity.VideoInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoInfoRepository extends JpaRepository<VideoInfo, Long> {
    Optional<VideoInfo> findFirstBySeriesAndName(String series, String name);
}
