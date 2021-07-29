# mettltest
A poc to describe the relationship between User-Account-Transactions made in Spring Boot

# Test for adding a user
curl --location --request POST 'http://localhost:8080/addUser?username=shyam&userid=2' \
--header 'Authorization: Basic QWRtaW46cGFzc3dvcmQ=' \
--data-raw ''

# Test for adding an account
curl --location --request POST 'http://localhost:8080/addAccount' \
--header 'Content-Type: application/json' \
--data-raw '{
    "userId":2,
    "accountName":"Curreent2",
    "accountNumber":25252536,
    "accountType":"Current",
    "balDate":"08/11/2018",
    "currency":"inr",
    "openAvailBal":4354.909
}'

# Test for fetching all users in the db
curl --location --request GET 'http://localhost:8080/fetchAllUsers'

# Test for fetching Accounts linked to a user
curl --location --request GET 'http://localhost:8080/fetchAccounts?userid=2'

# Test for fetching all Transactions linked to an account number
curl --location --request GET 'http://localhost:8080/fetchAllTxsForAccount?accnum=252525235'

# Test for adding a Transaction
curl --location --request POST 'http://localhost:8080/addTx' \
--header 'Content-Type: application/json' \
--data-raw '{
    "accNumber":252525235,
    "debitAmit":3223.45,
    "type":"credit",
    "txNarrative":"",
    "valDate":"05/10/2018"
}'
