docker build -t my/wildfly docker

docker run -d --name wildfly -p 8080:8080 -p 9990:9990 my/wildfly

echo
echo Docker container "wildfly" has been created.
echo Cleanup: 'docker container rm -f wildfly; docker image rm my/wildfly'
echo Application: http://localhost:8080
echo Administration: http://localhost:9990 '(admin/admin)'
echo Client login: clientapp/secret
echo Restart Wildfly: 'docker restart wildfly'
echo Stop Wildfly: 'docker stop wildfly'
