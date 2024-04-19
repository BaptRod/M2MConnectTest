cp ../common/src/main/resources/M2MConnect.xml filesToUpload/M2MConnect.xml
cp ../InnoSetup/M2MConnect_Setup.exe filesToUpload/M2MConnect_Setup.exe

cd filesToUpload
md5sum * > 'files.chk'


wput -u -B * ftp://updatetrcm:DxtaFJvgt545@ftp.cluster027.hosting.ovh.net:21/updateM2MConnect/