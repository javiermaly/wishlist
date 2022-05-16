package uy.maly.wishlist.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import uy.maly.wishlist.domain.dto.BooksApiDTO;
import uy.maly.wishlist.domain.model.Book;

/**
 * @author JMaly
 * @project wishlist
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = { WishlistMapper.class })
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper( BookMapper.class );

    @Mapping(source = "id", target = "id")
    @Mapping(source = "gbid", target = "gbid")
    @Mapping(source = "authors", target = "authors")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "publisher", target = "publisher")
    BooksApiDTO bookToBookDto(Book book);

}
