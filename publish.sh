#!/bin/bash

echo "instalando bamboo request-app-stats"
grails clean && grails refresh-dependencies && grails publish-plugin

#grails publish-plugin 

