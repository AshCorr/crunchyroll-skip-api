package net.ashleighcarr.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class CrunchyrollService {

    private String crunchyRollUrl;

    private CrunchyrollService(@Value("${crunchyroll.url}") String crunchyRollUrl) {
        this.crunchyRollUrl = crunchyRollUrl;
    }

    public boolean isVideoExists(String season, String id) {
        String url = String.format(crunchyRollUrl, season, id);

        try {
            ResponseEntity entity = new RestTemplate().getForEntity(url, String.class);

            if (entity.getStatusCode().is2xxSuccessful()) {
                return true;
            } else {
                return false;
            }
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().is4xxClientError()) {
                return false;
            }

            throw e;
        }
    }
}
