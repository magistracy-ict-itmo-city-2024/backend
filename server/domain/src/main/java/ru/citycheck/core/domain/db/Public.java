/*
 * This file is generated by jOOQ.
 */
package ru.citycheck.core.domain.db;


import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;
import ru.citycheck.core.domain.db.tables.*;

import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.category</code>.
     */
    public final Category CATEGORY = Category.CATEGORY;

    /**
     * The table <code>public.flyway_schema_history</code>.
     */
    public final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    /**
     * The table <code>public.issue</code>.
     */
    public final Issue ISSUE = Issue.ISSUE;

    /**
     * The table <code>public.jwt_tokens</code>.
     */
    public final JwtTokens JWT_TOKENS = JwtTokens.JWT_TOKENS;

    /**
     * The table <code>public.user_roles</code>.
     */
    public final UserRoles USER_ROLES = UserRoles.USER_ROLES;

    /**
     * The table <code>public.users</code>.
     */
    public final Users USERS = Users.USERS;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        return Arrays.<Sequence<?>>asList(
                Sequences.CATEGORY_ID_SEQ,
                Sequences.ISSUE_ID_SEQ,
                Sequences.USERS_ID_SEQ);
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
                Category.CATEGORY,
                FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
                Issue.ISSUE,
                JwtTokens.JWT_TOKENS,
                UserRoles.USER_ROLES,
                Users.USERS);
    }
}
