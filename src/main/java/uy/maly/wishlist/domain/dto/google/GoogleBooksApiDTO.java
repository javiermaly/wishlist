package uy.maly.wishlist.domain.dto.google;

import lombok.Data;
import java.util.List;

@Data
public class GoogleBooksApiDTO {
    private List<VolumeDTO> items;
}
