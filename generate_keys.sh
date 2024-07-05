openssl genrsa -out keypair.pem 2048

mkdir "src/main/resources/certs"

openssl rsa -in keypair.pem -pubout -out "src/main/resources/certs/public.pem"

openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out "src/main/resources/certs/private.pem"

rm keypair.pem