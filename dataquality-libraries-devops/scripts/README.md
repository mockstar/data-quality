Scripts for uploading necessary dependencies to Nexus server
===================

**What do they do?**

The *.sh scripts in this folder are used to upload the following artifacts to "talend-update" nexus server:
- upload-artifacts-daikon.sh (for the 4 daikon artifacts)
- upload-artifacts-tdp-core.sh (for the 3 dataprep-core artifacts)
- upload-artifacts-tdq.sh and upload-artifacts-tdq-ee.sh (for the 11 dataquality artifacts)

They download the artifacts from "artifacts-zl" nexus server build by the jenkins jobs and upload them to "talend-update" server, so that the studio can download them for the tDataprepRun jobs. These 3 scripts can be executed independently.



**How to run them?**
- make sure the artifacts to upload are available on "artifacts-zl" server.
- specify the right version in the scripts
- run the scripts one by one, or only the ones needed.


