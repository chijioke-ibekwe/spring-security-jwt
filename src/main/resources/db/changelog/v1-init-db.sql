CREATE TABLE permissions (
    id                      BIGSERIAL NOT NULL,
    created_at              TIMESTAMP NOT NULL,
    updated_at              TIMESTAMP,
    name                    VARCHAR(255) NOT NULL,
    description             VARCHAR(255),
    requires_verification   BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id)
);

CREATE TABLE permission_role (
    role_id         INT8 NOT NULL,
    permission_id   INT8 NOT NULL,
    PRIMARY KEY (role_id, permission_id)
);

CREATE TABLE roles (
    id              BIGSERIAL NOT NULL,
    created_at      TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP,
    description     VARCHAR(255),
    name            VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users (
    id                                  BIGSERIAL NOT NULL,
    created_at                          TIMESTAMP NOT NULL,
    updated_at                          TIMESTAMP,
    first_name                          VARCHAR(255) NOT NULL,
    last_name                           VARCHAR(255) NOT NULL,
    username                            VARCHAR(255) NOT NULL,
    password                            VARCHAR(255),
    phone_number                        VARCHAR(255),
    account_non_expired                 BOOLEAN DEFAULT TRUE,
    account_non_locked                  BOOLEAN DEFAULT TRUE,
    credentials_non_expired             BOOLEAN DEFAULT TRUE,
    enabled                             BOOLEAN DEFAULT TRUE,
    verified                            BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id)
);

CREATE TABLE user_role (
    user_id         INT8 NOT NULL,
    role_id         INT8 NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

ALTER TABLE users ADD CONSTRAINT uk_username unique (username);

ALTER TABLE permission_role ADD CONSTRAINT fk_permission_role_permissions FOREIGN KEY (permission_id) REFERENCES permissions ON DELETE CASCADE;

ALTER TABLE permission_role ADD CONSTRAINT fk_permission_role_roles FOREIGN KEY (role_id) REFERENCES roles ON DELETE CASCADE;

ALTER TABLE user_role ADD CONSTRAINT fk_user_role_roles FOREIGN KEY (role_id) REFERENCES roles ON DELETE CASCADE;

ALTER TABLE user_role ADD CONSTRAINT fk_user_role_users FOREIGN KEY (user_id) REFERENCES users ON DELETE CASCADE;

CREATE INDEX idx_username_users ON users(username);

CREATE INDEX idx_name_roles ON roles(name);