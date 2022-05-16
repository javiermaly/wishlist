package uy.maly.wishlist.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import uy.maly.wishlist.domain.dto.BooksApiDTO;
import uy.maly.wishlist.domain.dto.google.VolumeDTO;

/**
 * @author JMaly
 * @project wishlist
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = { WishlistMapper.class })
public interface GoogleBookMapper {
    GoogleBookMapper INSTANCE = Mappers.getMapper( GoogleBookMapper.class );

    @Mapping(source = "id", target = "gbid")
    @Mapping(target = "title", expression = "java( book.getVolumeInfo().getTitle())")
    @Mapping(target = "publisher", expression = "java( book.getVolumeInfo().getPublisher())")
    @Mapping(target = "authors", expression = "java( book.getVolumeInfo().getAuthors())")
    BooksApiDTO googleApiBookToBookDto(VolumeDTO book);




}
