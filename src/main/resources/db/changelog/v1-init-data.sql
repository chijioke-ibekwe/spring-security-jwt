INSERT INTO permissions (created_at, name, description, requires_verification) VALUES (
CURRENT_TIMESTAMP,
'users:read',
'Permission to fetch all users',
true
);

INSERT INTO roles (created_at, name, description) VALUES (
CURRENT_TIMESTAMP,
'role_admin',
'Role for users that carry out administrative functions on the application'
);

INSERT INTO roles (created_at, name, description) VALUES (
CURRENT_TIMESTAMP,
'role_user',
'Role for regular users of the application'
);

INSERT INTO permission_role (role_id, permission_id) VALUES (
(SELECT id FROM roles WHERE name = 'role_admin'),
(SELECT id FROM permissions WHERE name = 'users:read')
);

INSERT INTO permission_role (role_id, permission_id) VALUES (
(SELECT id FROM roles WHERE name = 'role_user'),
(SELECT id FROM permissions WHERE name = 'users:read')
);