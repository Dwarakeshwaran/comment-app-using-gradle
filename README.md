# comment-app-using-gradle
A Feedback Application

#Docker Commands to Run the Application
docker pull dwarki/commentapp-dbserver
docker pull dwarki/commentapp-appserver

docker run --name commentapp-dbserver  -d dwarki/commentapp-dbserver
docker run -p 7070:8080 --name commentapp --link commentapp-dbserver:mysql -d dwarki/commentapp-appserver