1.生成server和client证书密钥库
-genkeypair  [-v] [-protected]
             [-alias <别名>]
             [-keyalg <keyalg>] [-keysize <密钥大小>]
             [-sigalg <sigalg>] [-dname <dname>]
             [-validity <valDays>] [-keypass <密钥库口令>]
             [-keystore <密钥库>] [-storepass <存储库口令>]
             [-storetype <存储类型>] [-providername <名称>]
             [-providerclass <提供方类名称> [-providerarg <参数>]] ...
             [-providerpath <路径列表>]
keytool -genkeypair -alias server_store_alias -keystore ./cert/server.keystore -storepass serverstorepass -keypass serverkeypass -validity 36500 -keyalg RSA -storetype JKS -dname "CN=localhost, OU=AI, O=AI, L=NJ, ST=JS, C=86"
keytool -genkeypair -alias client_store_alias -keystore ./cert/client.keystore -storepass clientstorepass -keypass clientkeypass -validity 36500 -keyalg RSA -storetype JKS -dname "OU=AI, O=AI, L=NJ, ST=JS, C=86"


2.导出server，client证书
-exportcert  [-v] [-rfc] [-protected]
             [-alias <别名>] [-file <认证文件>]
             [-keystore <密钥库>] [-storepass <存储库口令>]
             [-storetype <存储类型>] [-providername <名称>]
             [-providerclass <提供方类名称> [-providerarg <参数>]] ...
             [-providerpath <路径列表>]
keytool -export -rfc -alias server_store_alias -file ./cert/server.cer -keystore ./cert/server.keystore -storepass serverstorepass
keytool -export -rfc -alias client_store_alias -file ./cert/client.cer -keystore ./cert/client.keystore -storepass clientstorepass


3.将客户端证书导入到服务端证书密钥库，将服务端证书导入到客户端密钥库
-importcert  [-v] [-noprompt] [-trustcacerts] [-protected]
             [-alias <别名>]
             [-file <认证文件>] [-keypass <密钥库口令>]
             [-keystore <密钥库>] [-storepass <存储库口令>]
             [-storetype <存储类型>] [-providername <名称>]
             [-providerclass <提供方类名称> [-providerarg <参数>]] ...
             [-providerpath <路径列表>]
keytool -import -noprompt -trustcacerts -alias client_store_alias -file ./cert/client.cer -keystore ./cert/server.keystore -keypass serverkeypass -storepass serverstorepass
keytool -import -noprompt -trustcacerts -alias server_store_alias -file ./cert/server.cer -keystore ./cert/client.keystore -keypass clientkeypass -storepass clientstorepass

