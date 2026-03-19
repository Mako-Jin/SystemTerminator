package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.common.tools.IntegerUtils;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

/**
 *  资源值对象
 * @author: Jin-LiangBo
 * @date: 2026年02月12日 9:23
 */
@Getter
public class ResourcesIdentity {
    /**
     * 资源值
     */
    private final ResourceValue resourceValue;
    /**
     * 资源类型：0：系统；1：服务；2：模块；3：页面；4：接口
     */
    private final Integer resourceType;
    /**
     * 接口请求地址
     */
    private final List<String> requestUrl;
    /**
     * 请求方法，大写：POST,GET,PUT
     */
    private final List<String> requestMethod;

    private ResourcesIdentity(
            ResourceValue resourceValue, Integer resourceType,
            List<String> requestUrl, List<String> requestMethod
    ) {
        this.resourceValue = Objects.requireNonNull(resourceValue, "资源值不能为空");
        // TODO 最大最小值，魔法值需要修改
        this.resourceType = IntegerUtils.requireBetween(resourceType, 0, 7, "资源类型不合规！");
        this.requestUrl = requestUrl;
        this.requestMethod = requestMethod;
    }

    public static ResourcesIdentity of(
            ResourceValue resourceValue, Integer resourceType,
            List<String> requestUrl, List<String> requestMethod
    ) {
        return new ResourcesIdentity(
                resourceValue, resourceType, requestUrl, requestMethod
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResourcesIdentity that = (ResourcesIdentity) o;

        if (!Objects.equals(resourceValue, that.resourceValue)) {
            return false;
        }
        if (!Objects.equals(resourceType, that.resourceType)) {
            return false;
        }
        if (!Objects.equals(requestUrl, that.requestUrl)) {
            return false;
        }
        return Objects.equals(requestMethod, that.requestMethod);
    }

    @Override
    public int hashCode() {
        int result = resourceValue != null ? resourceValue.hashCode() : 0;
        result = 31 * result + (resourceType != null ? resourceType.hashCode() : 0);
        result = 31 * result + (requestUrl != null ? requestUrl.hashCode() : 0);
        result = 31 * result + (requestMethod != null ? requestMethod.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ResourcesIdentity{" +
                "resourceValue=" + resourceValue +
                ", resourceType=" + resourceType +
                ", requestUrl=" + requestUrl +
                ", requestMethod=" + requestMethod +
                '}';
    }
}
