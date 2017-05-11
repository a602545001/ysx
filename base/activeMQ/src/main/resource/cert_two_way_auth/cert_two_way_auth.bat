DEL /q *.keystore
del /q *.cer

@echo.
@echo 1.����server��client֤����Կ��
keytool -genkeypair -alias server_store_alias -keystore ./server.keystore -storepass serverstorepass -keypass serverkeypass -validity 36500 -keyalg RSA -storetype JKS -dname "CN=localhost, OU=TEST, O=TEST, L=TEST, ST=TEST, C=86"
keytool -genkeypair -alias client_store_alias -keystore ./client.keystore -storepass clientstorepass -keypass clientkeypass -validity 36500 -keyalg RSA -storetype JKS -dname "OU=TEST, O=TEST, L=TEST, ST=TEST, C=86"

@echo.
@ECHO 2.����server֤���client֤��
keytool -export -rfc -alias server_store_alias -file ./server.cer -keystore ./server.keystore -storepass serverstorepass
keytool -export -rfc -alias client_store_alias -file ./client.cer -keystore ./client.keystore -storepass clientstorepass

@echo.
@ECHO 3.��server֤�鵼�뵽client��Կ��, ��client֤�鵼�뵽server��Կ��
keytool -import -noprompt -trustcacerts -alias server_store_alias -file ./server.cer -keystore ./client.keystore -keypass clientkeypass -storepass clientstorepass
keytool -import -noprompt -trustcacerts -alias client_store_alias -file ./client.cer -keystore ./server.keystore -keypass serverkeypass -storepass serverstorepass

PAUSE