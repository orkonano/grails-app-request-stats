#!/bin/bash

./grailsw refresh-dependencies --non-interactive
./grailsw test-app :unit --non-interactive
