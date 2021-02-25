#start.sh
	#!/bin/bash
echo 'Starting service'
cp /opt/apps/target/yuzee-company-api.jar ~/yuzee/yuzee-company-api.jar 
sudo service yuzee-company-api restart