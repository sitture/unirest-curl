#!/bin/sh
mvn clean -B test -Dmaven.javadoc.skip=true -Dgpg.skip
