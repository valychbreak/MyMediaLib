INSERT INTO oauth_client_details
    (client_id, client_secret, resource_ids, scope, authorized_grant_types,
    web_server_redirect_uri, authorities, access_token_validity,
    refresh_token_validity, additional_information, autoapprove)
VALUES
    ('gigy', '$2a$10$IEfwBgnqu9XMR0G6FEUob.b7toYdiEqhe1QXfnrr7M0yKzHQjiBfC', 'resource-id', 'read,write', 'password, refresh_token', null, null, 3600, 3600, null, true) 
    ON CONFLICT DO NOTHING;
