package com.mkk.dataaccess.common;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public class QueryPath<T> {
    private Path<T> pathExpression;
    private String field;

    public Expression<T> getPathExpression() {
        return this.pathExpression;
    }

    public String getField() {
        return this.field;
    }

    public QueryPath(Root<T> root, String fieldPath) {
        String[] paths = fieldPath.split("[.]");
        if (paths.length > 1) {
            Join current = root.join(paths[0]);

            for(int i = 1; i < fieldPath.length() - 1; ++i) {
                current = current.join(paths[i]);
            }

            this.field = paths[paths.length - 1];
            this.pathExpression = current;
        } else {
            this.field = fieldPath;
            this.pathExpression = root;
        }

    }

    public Path getPath() {
        return this.pathExpression.get(this.field);
    }
}
