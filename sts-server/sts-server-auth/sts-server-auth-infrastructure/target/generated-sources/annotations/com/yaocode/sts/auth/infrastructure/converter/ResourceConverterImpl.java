package com.yaocode.sts.auth.infrastructure.converter;

import com.yaocode.sts.auth.domain.entity.ResourceInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.composites.ResourcesIdentity;
import com.yaocode.sts.auth.domain.valueobjects.primitives.ResourceValue;
import com.yaocode.sts.auth.infrastructure.po.ResourceInfoPo;
import com.yaocode.sts.common.resources.enums.ResourceTypeEnums;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T23:16:37+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class ResourceConverterImpl implements ResourceConverter {

    @Override
    public ResourceInfoPo toPo(ResourceInfoEntity resourceEntity) {
        if ( resourceEntity == null ) {
            return null;
        }

        ResourceInfoPo resourceInfoPo = new ResourceInfoPo();

        resourceInfoPo.setResourceId( resourceIdToString( resourceEntity.getId() ) );
        resourceInfoPo.setResourceValue( resourceValueToString( resourceEntityIdentityResourceValue( resourceEntity ) ) );
        resourceInfoPo.setRequestUrl( listToString( resourceEntityIdentityRequestUrl( resourceEntity ) ) );
        resourceInfoPo.setRequestMethod( listToString( resourceEntityIdentityRequestMethod( resourceEntity ) ) );
        resourceInfoPo.setParentCode( listToString( resourceEntity.getParentCode() ) );
        resourceInfoPo.setVersion( versionToString( resourceEntity.getVersion() ) );
        ResourceTypeEnums resourceType = resourceEntityIdentityResourceType( resourceEntity );
        if ( resourceType != null ) {
            resourceInfoPo.setResourceType( resourceType.ordinal() );
        }
        resourceInfoPo.setResourceName( resourceEntity.getResourceName() );
        resourceInfoPo.setResourceDesc( resourceEntity.getResourceDesc() );
        if ( resourceEntity.getIsDeprecated() != null ) {
            resourceInfoPo.setIsDeprecated( resourceEntity.getIsDeprecated().ordinal() );
        }
        if ( resourceEntity.getIsWhiteList() != null ) {
            resourceInfoPo.setIsWhiteList( resourceEntity.getIsWhiteList().ordinal() );
        }
        resourceInfoPo.setIcon( resourceEntity.getIcon() );

        return resourceInfoPo;
    }

    @Override
    public List<ResourceInfoPo> toPoList(List<ResourceInfoEntity> resourceEntityList) {
        if ( resourceEntityList == null ) {
            return null;
        }

        List<ResourceInfoPo> list = new ArrayList<ResourceInfoPo>( resourceEntityList.size() );
        for ( ResourceInfoEntity resourceInfoEntity : resourceEntityList ) {
            list.add( toPo( resourceInfoEntity ) );
        }

        return list;
    }

    @Override
    public List<ResourceInfoEntity> toEntityList(List<ResourceInfoPo> resourcePoList) {
        if ( resourcePoList == null ) {
            return null;
        }

        List<ResourceInfoEntity> list = new ArrayList<ResourceInfoEntity>( resourcePoList.size() );
        for ( ResourceInfoPo resourceInfoPo : resourcePoList ) {
            list.add( toEntity( resourceInfoPo ) );
        }

        return list;
    }

    private ResourceValue resourceEntityIdentityResourceValue(ResourceInfoEntity resourceInfoEntity) {
        ResourcesIdentity identity = resourceInfoEntity.getIdentity();
        if ( identity == null ) {
            return null;
        }
        return identity.getResourceValue();
    }

    private List<String> resourceEntityIdentityRequestUrl(ResourceInfoEntity resourceInfoEntity) {
        ResourcesIdentity identity = resourceInfoEntity.getIdentity();
        if ( identity == null ) {
            return null;
        }
        return identity.getRequestUrl();
    }

    private List<String> resourceEntityIdentityRequestMethod(ResourceInfoEntity resourceInfoEntity) {
        ResourcesIdentity identity = resourceInfoEntity.getIdentity();
        if ( identity == null ) {
            return null;
        }
        return identity.getRequestMethod();
    }

    private ResourceTypeEnums resourceEntityIdentityResourceType(ResourceInfoEntity resourceInfoEntity) {
        ResourcesIdentity identity = resourceInfoEntity.getIdentity();
        if ( identity == null ) {
            return null;
        }
        return identity.getResourceType();
    }
}
