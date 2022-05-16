package uy.maly.wishlist.domain.dto.google;

import lombok.Data;
import java.util.List;

@Data
public class VolumeInfoDTO {
    private String title;
    private String publisher;
    private List<String> authors = null;

}
