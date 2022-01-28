YRGO Java20 
Projektarbete 2/11-21 -- 28/1-22

School project written by Jens Bankel, Adrian Book, Staffan Godman, and Björn Munthe.
(referred to as 'JASB' in Javadoc)

------------LooCation----------------------------------------------------------------------------------

A user driven application for finding, rating and adding public toilets in an interactive map through a web interface. Contains admin functionality for altering or deleting toilets and users. 

This repo contains four separate projects:
 - toiletprojectfrontend
 A React web app that communicates with the two REST-apis
 
 - toiletprojectbackend
 REST-api containing endpoints for toilets and other related objects. ALso handles JWT authentication.

 - toiletuserservice
 REST-api containing endpoints for handling user information and JWT authentication.
 
 - entities
 Java representaitons of database entities. Used in th REST-apis to promote generalisation of objects.

 ------------SETUP-------------------------------------------------------------------------------------

Prerequisites: 
 - Docker installed
 - Ports 3000, 3306, 8080 and 9091 available

To run this project:
 - Clone repo
 - Create empty folder 'dbVolume' in root folder of your cloned repo
 - Run following command in root folder of entities 
    ./gradlew build
 - Run follwing command in root folders of toiletprojectbackend and toiletuserservice
    ./gradlew clean bootJar
 - Run following command in root folder of your cloned repo to populate table Roles in database
    docker docker exec -it db-dev bash -c 'mysql -u root -pYrgo2021 toiletdb < /scripts/init.sql'
 - Run following command in root folder of your cloned repo
    docker-compose up
 - Web application available at localhost:3000

Only a super admin can alter the roles of other users through the web interface. To create a super admin user you need to alter the database directly. Find the id of a user in the database toiletdb in docker container 'db-dev'. Insert this id and the id of the role 'ROLE_SUPER_ADMIN' from the table 'role'.

Commands:
    docker exec -it db-dev mysql -u root -pYrgo2021
    use toiletdb
    SELECT * FROM TOILET_USER //Find id of user to grant new role to
    INSERT INTO toilet_user_roles(toilet_user_id, role_id) VALUES(<myUserID>, 1)
