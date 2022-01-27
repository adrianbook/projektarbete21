use toiletdb; 
SELECT * FROM role;
INSERT IGNORE INTO role
  (name) 
VALUES 
  ('ROLE_SUPER_ADMIN'), ('ROLE_ADMIN'), ('ROLE_APPUSER'), ('BLOCKED');