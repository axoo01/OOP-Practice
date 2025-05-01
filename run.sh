#!/bin/bash

if [ -z "$1" ]; then
  echo "Usage: docker run <image> <package.MainClass>"
  echo "Examples:"
  echo "  docker run practice-26987 landMgtSystem.LandMain"
  echo "  docker run practice-26987 missionMgtSystem.MMain"
  echo "  docker run practice-26987 nurseryMgtSystem.NMain"
  exit 1
fi

echo "Running class: $1"
echo "Target folder contents:"
ls -R target

java -cp target "$1"
