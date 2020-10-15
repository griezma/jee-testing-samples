cd docker
docker build -t my/wildfly .
cd ..
docker run -d --name wildfly -p 8080:8080 -p 9990:9990 my/wildfly

echo Docker container "wildfly" has been created.
echo Apps: http://localhost:8080
echo 'Admin: http://localhost:9990 (admin/admin)'
echo Client login: clientapp/secret
