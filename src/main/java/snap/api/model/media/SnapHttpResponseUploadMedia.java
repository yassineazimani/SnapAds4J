package snap.api.model.media;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SnapHttpResponseUploadMedia {

    private SnapInnerMedia result;

    public Optional<CreativeMedia> getResultUploadMedia() {
	return Optional.ofNullable(result.getMedia());
    }// getResultUploadMedia()

}// SnapHttpResponseMedia
