#!/bin/zsh
echo "-----------------------------"
echo "Running maven build commands"
echo "-----------------------------"
echo Removing existing compiled jars...
rm target/*
declare -a StringArray=(
  "myztic-core"
  "myztic-essentials"
  "myztic-streak-system"
  "myztic-world-manager"
  "myztic-player-profiles")
for val in ${StringArray[@]}; do
   echo "Building project "$val"..."
   cd $val
   mvn clean package install
   cd ../
done
echo "-----------------------------------"
echo "Completed executing maven commands"
echo "-----------------------------------"