#peer0 industry
export CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/industry.cert.com/users/Admin@industry.cert.com/msp
export CORE_PEER_ADDRESS=peer0.industry.cert.com:7051
export CORE_PEER_LOCALMSPID="IndustryMSP"
export CORE_PEER_TLS_ROOTCERT_FILE=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/industry.cert.com/peers/peer0.industry.cert.com/tls/ca.crt

export CHANNEL_NAME=certchannel

peer chaincode install -n fabcar -v 1.0 -p github.com/chaincode/fabcar/go/

sleep 2

#peer1 industry
export CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/industry.cert.com/users/Admin@industry.cert.com/msp
export CORE_PEER_ADDRESS=peer1.industry.cert.com:7051
export CORE_PEER_LOCALMSPID="IndustryMSP"
export CORE_PEER_TLS_ROOTCERT_FILE=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/industry.cert.com/peers/peer1.industry.cert.com/tls/ca.crt

peer chaincode install -n fabcar -v 1.0 -p github.com/chaincode/fabcar/go/

sleep 2

#peer0 ktb
export CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/krungthai.cert.com/users/Admin@krungthai.cert.com/msp
export CORE_PEER_ADDRESS=peer0.krungthai.cert.com:7051
export CORE_PEER_LOCALMSPID="KrungthaiMSP"
export CORE_PEER_TLS_ROOTCERT_FILE=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/krungthai.cert.com/peers/peer0.krungthai.cert.com/tls/ca.crt

peer chaincode install -n fabcar -v 1.0 -p github.com/chaincode/fabcar/go/

sleep 2

#peer1 ktb
export CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/krungthai.cert.com/users/Admin@krungthai.cert.com/msp
export CORE_PEER_ADDRESS=peer1.krungthai.cert.com:7051
export CORE_PEER_LOCALMSPID="KrungthaiMSP"
export CORE_PEER_TLS_ROOTCERT_FILE=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/krungthai.cert.com/peers/peer1.krungthai.cert.com/tls/ca.crt

peer chaincode install -n fabcar -v 1.0 -p github.com/chaincode/fabcar/go/

sleep 2

#peer0 commerce
export CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/commerce.cert.com/users/Admin@commerce.cert.com/msp
export CORE_PEER_ADDRESS=peer0.commerce.cert.com:7051
export CORE_PEER_LOCALMSPID="CommerceMSP"
export CORE_PEER_TLS_ROOTCERT_FILE=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/commerce.cert.com/peers/peer0.commerce.cert.com/tls/ca.crt

peer chaincode install -n fabcar -v 1.0 -p github.com/chaincode/fabcar/go/

sleep 2

#peer1 commerce
export CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/commerce.cert.com/users/Admin@commerce.cert.com/msp
export CORE_PEER_ADDRESS=peer1.commerce.cert.com:7051
export CORE_PEER_LOCALMSPID="CommerceMSP"
export CORE_PEER_TLS_ROOTCERT_FILE=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/commerce.cert.com/peers/peer1.commerce.cert.com/tls/ca.crt

peer chaincode install -n fabcar -v 1.0 -p github.com/chaincode/fabcar/go/

sleep 2

#peer0 police
export CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/police.cert.com/users/Admin@police.cert.com/msp
export CORE_PEER_ADDRESS=peer0.police.cert.com:7051
export CORE_PEER_LOCALMSPID="PoliceMSP"
export CORE_PEER_TLS_ROOTCERT_FILE=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/police.cert.com/peers/peer0.police.cert.com/tls/ca.crt

peer chaincode install -n fabcar -v 1.0 -p github.com/chaincode/fabcar/go/

sleep 2

#peer1 police
export CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/police.cert.com/users/Admin@police.cert.com/msp
export CORE_PEER_ADDRESS=peer1.police.cert.com:7051
export CORE_PEER_LOCALMSPID="PoliceMSP"
export CORE_PEER_TLS_ROOTCERT_FILE=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/police.cert.com/peers/peer1.police.cert.com/tls/ca.crt

peer chaincode install -n fabcar -v 1.0 -p github.com/chaincode/fabcar/go/

sleep 2
