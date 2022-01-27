!#/bin/bash
mysql -u root -pYrgo2021 toiletdb < '/scripts/init.sql'
echo "---------------------------------" 
echo "   Added roles to table role"
echo "   Selecting all rows from role"
echo "   (Please ignore warnings)"
echo "---------------------------------"
mysql -u root -pYrgo2021 toiletdb -e 'select * from role'