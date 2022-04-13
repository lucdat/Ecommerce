package com.ecommerce.dto.converters;

import com.ecommerce.domain.Tag;
import com.ecommerce.dto.domain.PageDTO;
import com.ecommerce.dto.domain.PageTagDTO;
import com.ecommerce.dto.domain.TagDTO;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.stream.Collectors;

public class TagConverter {
    public static TagDTO coverTagToTagDTO(Tag tag){
        if(tag!=null){
            TagDTO tagDTO = new TagDTO(tag.getId(),tag.getName());
            return tagDTO;
        }
        return null;
    }

    public static PageTagDTO coverPageTagToPageTagDTO(Page<Tag> tags){
        if(tags!=null){
            Collection<TagDTO> tagDTOS = tags.stream().map(TagConverter::coverTagToTagDTO).collect(Collectors.toList());
            PageDTO page = new PageDTO(tags.getTotalPages(), tagDTOS.size(), tags.isFirst(), tags.isLast());
            return new PageTagDTO(tagDTOS,page);
        }
        return null;
    }

    public static Tag coverTagDTOToTag(TagDTO tagDTO){
        if(tagDTO!=null){
            Tag tag = new Tag();
            tag.setId(tagDTO.getId());
            tag.setName(tagDTO.getName());
            return tag;
        }
        return null;
    }
}
