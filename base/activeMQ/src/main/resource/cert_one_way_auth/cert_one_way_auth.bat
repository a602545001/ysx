DEL /q *.keystore
del /q *.cer

@echo.
@echo 1.生成server和client证书密钥库
keytool -genkeypair -alias server_store_alias -keystore ./server.keystore -storepass serverstorepass -keypass serverkeypass -validity 36500 -keyalg RSA -storetype JKS -dname "CN=localhost, OU=TEST, O=TEST, L=TEST, ST=TEST, C=86"
keytool -genkeypair -alias client_store_alias -keystore ./client.keystore -storepass clientstorepass -keypass clientkeypass -validity 36500 -keyalg RSA -storetype JKS -dname "OU=TEST, O=TEST, L=TEST, ST=TEST, C=86"

@echo.
@ECHO 2.导出server证书
keytool -export -rfc -alias server_store_alias -file ./server.cer -keystore ./server.keystore -storepass serverstorepass

@echo.
@ECHO 3.将服务端证书导入到客户端密钥库
keytool -import -noprompt -trustcacerts -alias server_store_alias -file ./server.cer -keystore ./client.keystore -keypass clientkeypass -storepass clientstorepass

PAUSE