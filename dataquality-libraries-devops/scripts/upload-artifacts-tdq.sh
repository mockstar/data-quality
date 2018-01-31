#!/bin/sh
DQ_LIB_VERSION=7.4.0 # change the version of DQ libs to upload

DQ_LIB_GROUP_ID=org.talend.dataquality

NEXUS_RELEASE_LINK="https://artifacts-zl.talend.com/nexus/content/repositories/TalendOpenSourceRelease/"
NEXUS_SNAPSHOT_LINK="https://artifacts-zl.talend.com/nexus/content/repositories/TalendOpenSourceSnapshot/"

NEXUS_LINK_FOR_DOWNLOAD=${NEXUS_RELEASE_LINK} # Switch between RELEASE/SNAPSHOT link here

TALEND_UPDATE_LINK="https://talend-update.talend.com/nexus/content/repositories/libraries/"

ARTIFACT_NAMES="dataquality-common \
 dataquality-converters \
 dataquality-record-linkage \
 dataquality-standardization \
 dataquality-statistics"


for element in ${ARTIFACT_NAMES}    
do   
    echo "-------------------------------------"
    echo "|     " ${element} "    |"
    echo "-------------------------------------"

    mkdir -p "./artifacts/${element}/"
    cat ./pom_template.xml > "./artifacts/${element}/pom.xml"

    # download from artifacts-zl
    mvn dependency:get \
        -DrepoUrl=${NEXUS_LINK_FOR_DOWNLOAD} \
        -DgroupId=org.talend.dataquality \
        -DartifactId=${element} \
        -Dversion=${DQ_LIB_VERSION} \
        -Dpackaging=jar \
        -Ddest=./artifacts/${element}/${element}-${DQ_LIB_VERSION}.jar

    # prepare pom.xml file
    sed -i '' -e 's/<groupId>.*<\/groupId>/<groupId>'${DQ_LIB_GROUP_ID}'<\/groupId>/g' \
      ./artifacts/${element}/pom.xml
    sed -i '' -e 's/<artifactId>.*<\/artifactId>/<artifactId>'${element}'<\/artifactId>/g' \
      ./artifacts/${element}/pom.xml
    sed -i '' -e 's/<version>.*<\/version>/<version>'${DQ_LIB_VERSION}'<\/version>/g' \
      ./artifacts/${element}/pom.xml

    # upload to talend-update
    mvn deploy:deploy-file \
        -Durl=${TALEND_UPDATE_LINK} \
        -DrepositoryId=talend-update \
        -DgroupId=org.talend.dataquality \
        -DartifactId=${element} \
        -Dversion=${DQ_LIB_VERSION} \
        -DpomFile=./artifacts/${element}/pom.xml \
        -Dfile=./artifacts/${element}/${element}-${DQ_LIB_VERSION}.jar
done
