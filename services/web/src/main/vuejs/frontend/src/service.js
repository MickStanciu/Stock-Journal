import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8080/api/tradelog/d79ec11a-2011-4423-ba01-3af8de0a3e14';

const appService = {
    getTradedSymbols() {
        return new Promise(resolve => {
            axios
                .get('/symbols')
                .then(response => {
                    resolve(response.data)
                }).catch(error => {
                    console.error(error);
                })
        });
    },

    getTradesPerSymbol(symbol) {
      return new Promise(resolve => {
          axios
              .get('/trades/' + symbol.toUpperCase())
              .then(response => {
                  console.log(data);
                  resolve(response.data)
              }).catch(error => {
                  console.error(error);
              })
      });
    },

    recordShareTrade(shareTradeLog) {
        console.log(shareTradeLog);

        let dto = {
            transactionId : null,
            accountId : null,
            date : null,
            symbol : null,
            price : 0.0,
            quantity : 0,
            action : null,
            actionType : 'STOCK',
            brokerFees : 0,
            mark : null
        };

        return new Promise(resolve => {
            axios
                .post('/share', dto)
                .then(response => {
                    resolve(response.data);
                }).catch(error => {
                console.error(error);
            })
        });
    }
};

export default appService;