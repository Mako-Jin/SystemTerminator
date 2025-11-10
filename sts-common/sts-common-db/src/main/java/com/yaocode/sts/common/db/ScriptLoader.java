package com.yaocode.sts.common.db;

import com.yaocode.sts.common.db.parser.SqlParser;
import com.yaocode.sts.common.db.statement.SqlStatement;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月03日 23:44
 */
public class ScriptLoader {

    private final SqlParser sqlParser;

    public ScriptLoader(SqlParser sqlParser) {
        this.sqlParser = sqlParser;
    }

    private static final ResourcePatternResolver RESOURCE_PATTERN_RESOLVER = new PathMatchingResourcePatternResolver();

    public List<Resource> loadScript(String location, Predicate<Resource> predicate) throws IOException {
        Resource[] resources = RESOURCE_PATTERN_RESOLVER.getResources(location);
        return Arrays.stream(resources).filter(predicate).toList();
    }

    public List<SqlStatement> loadSql(Resource resource) throws URISyntaxException, IOException {
        String content = new String(Files.readAllBytes(Path.of(resource.getURI())));
        List<SqlStatement> sqlStatements = this.sqlParser.parseSqlContent(content);
        sqlStatements.forEach(e -> e.setResourceName(resource.getFilename()));
        return sqlStatements;
    }

}
