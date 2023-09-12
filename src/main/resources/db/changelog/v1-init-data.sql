INSERT INTO permissions (name, description, requires_verification) VALUES (
'users:read',
'Permission to fetch all users',
true
);

INSERT INTO roles (name, description) VALUES ('ROLE_ADMIN', 'Role for users that carry out administrative functions on the application');

INSERT INTO permission_role (role_id, permission_id) VALUES (
(SELECT id FROM roles WHERE name = 'ROLE_ADMIN'),
(SELECT id FROM permissions WHERE name = 'users:read')
);