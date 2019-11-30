package net.ashleighcarr.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CrunchyrollService {

    private String crunchyRollUrl;

    private CrunchyrollService(@Value("${crunchyroll.url}") String crunchyRollUrl) {
        this.crunchyRollUrl = crunchyRollUrl;
    }

    public boolean isVideoExists(String season, String id) {
        String url = String.format(crunchyRollUrl, season, id);

        ResponseEntity entity = new RestTemplate().getForEntity(url, String.class);

        if (entity.getStatusCode().is2xxSuccessful()) {
            return true;
        } else if(entity.getStatusCode() == HttpStatus.NOT_FOUND) {
            return false;
        }

        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected response from Crunchyroll:" + entity);
    }
}
