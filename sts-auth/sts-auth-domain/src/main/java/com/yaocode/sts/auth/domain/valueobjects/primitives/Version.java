package com.yaocode.sts.auth.domain.valueobjects.primitives;

import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 资源版本值对象（不可变）
 * @author: Jin-LiangBo
 * @date: 2026年03月04日 18:32
 */
@Getter
public class Version implements Comparable<Version> {

    private static final Pattern VERSION_PATTERN =
            Pattern.compile("^(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)$");

    /**
     * 主版本 - 资源整体重构
     */
    private final Integer major;

    /**
     * 次版本 - 核心字段变化
     */
    private final Integer minor;

    /**
     * 修订版本 - 非核心字段变化
     */
    private final Integer patch;

    /**
     * 构建版本 - 客户自定义/构建号
     */
    private final Integer build;

    private Version (int major, int minor, int patch, int build) {
        validateVersionNumbers(major, minor, patch, build);
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.build = build;
    }

    private void validateVersionNumbers(int major, int minor, int patch, int build) {
        if (major < 0 || minor < 0 || patch < 0 || build < 0) {
            throw new IllegalArgumentException("版本号不能为负数");
        }
        // if (major == 0 && minor == 0 && patch == 0 && build == 0) {
        //     throw new IllegalArgumentException("版本号不能全为0");
        // }
    }

    public static Version of(int major, int minor, int patch, int build) {
        return new Version(major, minor, patch, build);
    }

    public static Version of(String versionStr) {
        if (versionStr == null || versionStr.trim().isEmpty()) {
            return initial();
        }

        var matcher = VERSION_PATTERN.matcher(versionStr.trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("版本格式不正确，应为: 主.次.修.建 (如: 1.2.3.4)");
        }

        return new Version(
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3)),
                Integer.parseInt(matcher.group(4))
        );
    }

    /**
     * 获取初始版本
     */
    public static Version initial() {
        return of(0, 0, 0, 1);
    }

    /**
     * 主版本升级（第一位+1，其余归零）
     */
    public Version bumpMajor() {
        return new Version(major + 1, 0, 0, 0);
    }

    /**
     * 次版本升级（第二位+1，后续归零）
     */
    public Version bumpMinor() {
        return new Version(major, minor + 1, 0, 0);
    }

    /**
     * 修订版本升级（第三位+1，后续归零）
     */
    public Version bumpPatch() {
        return new Version(major, minor, patch + 1, 0);
    }

    /**
     * 构建版本升级（第四位+1）
     */
    public Version bumpBuild() {
        return new Version(major, minor, patch, build + 1);
    }

    @Override
    public int compareTo(Version other) {
        if (!this.major.equals(other.major)) {
            return Integer.compare(this.major, other.major);
        }
        if (!this.minor.equals(other.minor)) {
            return Integer.compare(this.minor, other.minor);
        }
        if (!this.patch.equals(other.patch)) {
            return Integer.compare(this.patch, other.patch);
        }
        return Integer.compare(this.build, other.build);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Version version = (Version) o;

        if (!Objects.equals(major, version.major)) {
            return false;
        }
        if (!Objects.equals(minor, version.minor)) {
            return false;
        }
        if (!Objects.equals(patch, version.patch)) {
            return false;
        }
        return Objects.equals(build, version.build);
    }

    @Override
    public int hashCode() {
        int result = major != null ? major.hashCode() : 0;
        result = 31 * result + (minor != null ? minor.hashCode() : 0);
        result = 31 * result + (patch != null ? patch.hashCode() : 0);
        result = 31 * result + (build != null ? build.hashCode() : 0);
        return result;
    }

    public String getStr() {
        return major + "." + minor + "." + patch + "." + build;
    }

    @Override
    public String toString() {
        return "Version{" +
                "major=" + major +
                ", minor=" + minor +
                ", patch=" + patch +
                ", build=" + build +
                '}';
    }
}
