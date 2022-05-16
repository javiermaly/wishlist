package uy.maly.wishlist.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import uy.maly.wishlist.domain.dto.WishlistDTO;
import uy.maly.wishlist.domain.model.Wishlist;

/**
 * @author JMaly
 * @project wishlist
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = { BookMapper.class })
public interface WishlistMapper {

    WishlistMapper INSTANCE = Mappers.getMapper( WishlistMapper.class );

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "books", target = "books")
    WishlistDTO wishlistToWishlistDto(Wishlist wishlist);
}
