package net.ashleighcarr.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity(name = "VIDEO_INFO")
public class VideoInfo {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(name = "SERIES")
    private String series;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @Column(name = "INTRO_START")
    private int introStart;

    @Column(name = "INTRO_END")
    private int introEnd;
}
