package snap.api.model.media;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import lombok.Getter;
import lombok.Setter;
import snap.api.model.SnapHttpResponse;

@Getter
@Setter
public class SnapHttpResponseMedia extends SnapHttpResponse{

    private List<SnapInnerMedia> media;

    public Optional<CreativeMedia> getSpecificMedia() {
	return (CollectionUtils.isNotEmpty(media) && media.get(0) != null) ? Optional.of(media.get(0).getMedia())
		: Optional.empty();
    }// getSpecificMedia()

    public List<CreativeMedia> getAllMedia() {
	return media.stream().map(org -> org.getMedia()).collect(Collectors.toList());
    }// getAllMedia()
}// SnapHttpResponseMedia
