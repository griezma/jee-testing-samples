cd docker
docker build -t my/wildfly .

echo Docker container "wildfly" has been created.
echo Appliation: http://localhost:8080
echo Administration: http://localhost:9990 '(admin/admin)'
echo Client login: clientapp/secret
echo Stop or restart container: 'docker [stop|restart] wildfly'
