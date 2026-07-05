package com.yaocode.sts.auth.interfaces.assembler;

import com.yaocode.sts.auth.application.dto.ResourceDto;
import com.yaocode.sts.auth.interfaces.model.params.CreateResourceParams;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T23:16:44+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class ResourceAssemblerImpl implements ResourceAssembler {

    @Override
    public ResourceDto toDto(CreateResourceParams resourceParams) {
        if ( resourceParams == null ) {
            return null;
        }

        ResourceDto resourceDto = new ResourceDto();

        resourceDto.setResourceName( resourceParams.getResourceName() );
        resourceDto.setResourceValue( resourceParams.getResourceValue() );
        resourceDto.setResourceDesc( resourceParams.getResourceDesc() );
        resourceDto.setResourceType( resourceParams.getResourceType() );
        List<String> list = resourceParams.getRequestUrl();
        if ( list != null ) {
            resourceDto.setRequestUrl( new ArrayList<String>( list ) );
        }
        List<String> list1 = resourceParams.getRequestMethod();
        if ( list1 != null ) {
            resourceDto.setRequestMethod( new ArrayList<String>( list1 ) );
        }
        resourceDto.setIsDeprecated( resourceParams.getIsDeprecated() );
        resourceDto.setIsWhiteList( resourceParams.getIsWhiteList() );
        resourceDto.setIcon( resourceParams.getIcon() );

        return resourceDto;
    }
}
