package snap.api.model.media;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SnapHttpRequestMedia {

    private List<CreativeMedia> media;

    public SnapHttpRequestMedia() {
	this.media = new ArrayList<>();
    }// SnapHttpRequestMedia()

    public void addMedia(CreativeMedia media) {
	this.media.add(media);
    }// addMedia()
}// SnapHttpRequestMedia
