package com.uniso.video.domain;

import com.uniso.video.sdk.domain.video.Status;
import com.uniso.video.sdk.domain.video.Video;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VideoResponse {
    private Video video;
    private Status status;

}
