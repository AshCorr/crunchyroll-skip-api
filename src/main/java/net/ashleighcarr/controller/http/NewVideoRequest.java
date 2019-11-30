package net.ashleighcarr.controller.http;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewVideoRequest {
    public String series;
    public String name;

    public int introStart;
    public int introEnd;
}
