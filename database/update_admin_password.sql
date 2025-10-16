USE hotel_management;

-- Update admin user's password to use SHA2 hash
UPDATE users 
SET password = SHA2('admin123', 256) 
WHERE username = 'admin';