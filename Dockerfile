FROM tomcat:9.0
ADD build/libs/ROOT.war /usr/local/tomcat/webapps/
EXPOSE 7070
CMD ["catalina.sh", "run"]

