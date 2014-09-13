#!/bin/bash

echo "instalando bamboo request-app-stats"
grails clean && grails refresh-dependencies && grails maven-install

#grails publish-plugin 

