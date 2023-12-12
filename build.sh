#!/bin/zsh

# Declare all the projects
declare -a StringArray=(
  "myztic-core"
  "myztic-essentials"
  "myztic-streak-system"
  "myztic-world-manager"
  "myztic-player-profiles")

# Declare which folder you need the compiled jars to be moved
# For ex: the mc-server plugins folder
targetPluginFolder=/Users/rounick/IdeaProjects/MCServer_1.19.3/plugins

echo "-----------------------------"
echo "Running maven build commands"
echo "-----------------------------"
echo Removing existing compiled jars...
rm target/*

for val in ${StringArray[@]}; do
   echo "Building project "$val"..."
   cd $val
   mvn clean package install
   cd ../
done
echo "-----------------------------------"
echo "Completed executing maven commands"
echo "-----------------------------------"
echo Moving compiled jars to target folder...
echo Target folder: $targetPluginFolder
cd target/
for f in *.jar
do
  echo Moving $f...
  cp $f $targetPluginFolder
done
echo Completed moving all jars to target folder!