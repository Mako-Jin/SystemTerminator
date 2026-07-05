package com.yaocode.sts.auth.application.converter;

import com.yaocode.sts.auth.application.dto.ResourceDto;
import com.yaocode.sts.auth.domain.entity.ResourceInfoEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T23:16:41+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class ResourceApplicationConverterImpl implements ResourceApplicationConverter {

    @Override
    public List<ResourceInfoEntity> toEntityList(List<ResourceDto> resourceDtoList) {
        if ( resourceDtoList == null ) {
            return null;
        }

        List<ResourceInfoEntity> list = new ArrayList<ResourceInfoEntity>( resourceDtoList.size() );
        for ( ResourceDto resourceDto : resourceDtoList ) {
            list.add( toEntity( resourceDto ) );
        }

        return list;
    }
}
