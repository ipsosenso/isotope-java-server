ALTER TABLE is_asso_user_role DROP COLUMN id;
ALTER TABLE is_asso_user_role ADD PRIMARY KEY (id_user, id_role);