#!/bin/zsh
echo "-----------------------------"
echo "Running maven build commands"
echo "-----------------------------"
cd myztic-core
mvn clean package install
cd ../
cd myztic-essentials
mvn clean package install
cd ../
cd myztic-streak-system
mvn clean package install
cd ../
cd myztic-world-manager
mvn clean package install
echo "Completed executing maven commands"