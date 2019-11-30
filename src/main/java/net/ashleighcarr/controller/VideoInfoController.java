package net.ashleighcarr.controller;

import net.ashleighcarr.controller.http.NewVideoRequest;
import net.ashleighcarr.jpa.entity.VideoInfo;
import net.ashleighcarr.jpa.repository.VideoInfoRepository;
import net.ashleighcarr.model.AddVideoInfoRequest;
import net.ashleighcarr.service.CrunchyrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class VideoInfoController {

    private CrunchyrollService crunchyrollService;
    private VideoInfoRepository videoInfoRepository;

    @Autowired
    public VideoInfoController(CrunchyrollService crunchyrollService, VideoInfoRepository videoInfoRepository) {
        this.crunchyrollService = crunchyrollService;
        this.videoInfoRepository = videoInfoRepository;
    }

    @GetMapping("/api/v1/video_info/{videoSeries}/{videoName}")
    public VideoInfo getVideoInformation(@PathVariable String videoSeries, @PathVariable String videoName) {
        Optional<VideoInfo> video = videoInfoRepository.findFirstBySeriesAndName(videoSeries, videoName);
        if(video.isPresent()) {
            return video.get();
        } else {
            if(!crunchyrollService.isVideoExists(videoSeries , videoName)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find video on Crunchyroll.");
            }

            VideoInfo videoInfo = new VideoInfo();
            videoInfo.setSeries(videoSeries);
            videoInfo.setName(videoName);

            videoInfo = videoInfoRepository.save(videoInfo);

            return videoInfo;
        }
    }

    @PostMapping("/api/v1/video_info")
    public VideoInfo addVideoInformation(@RequestBody NewVideoRequest request) {
        Optional<VideoInfo> video = videoInfoRepository.findFirstBySeriesAndName(request.getSeries(), request.getName());

        if(video.isPresent()) {
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, "Video already exists in database.");
        } else {
            if(!crunchyrollService.isVideoExists(request.getSeries() , request.getName())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find video on Crunchyroll.");
            }

            VideoInfo videoInfo = new VideoInfo();
            videoInfo.setSeries(request.getSeries());
            videoInfo.setName(request.getName());
            videoInfo.setIntroStart(request.getIntroStart());
            videoInfo.setIntroEnd(request.getIntroEnd());

            videoInfo = videoInfoRepository.save(videoInfo);

            return videoInfo;
        }
    }
}
