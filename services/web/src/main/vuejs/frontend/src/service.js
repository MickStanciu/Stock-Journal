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

    getSummary() {
        return new Promise(resolve => {
            axios
                .get('/summary')
                .then(response => {
                    resolve(response.data)
                }).catch(error => {
                console.error(error);
            })
        });
    },

    getShareData(symbol) {
        return new Promise(resolve => {
            axios
                .get('/share/data/' + symbol.toUpperCase())
                .then(response => {
                    resolve(response.data);
                }).catch(error => {
                    console.log(error);
            })
        });
    },

    getTradesPerSymbol(symbol) {
        return new Promise(resolve => {
            axios
                .get('/trades/' + symbol.toUpperCase())
                .then(response => {
                    resolve(response.data);
                }).catch(error => {
                    console.error(error);
                })
            });
    },

    recordShareTrade(dto) {
        return new Promise(resolve => {
            axios
                .post('/share', dto)
                .then(response => {
                    resolve(response.data);
                }).catch(error => {
                    console.error(error);
                    resolve(null);
            })
        });
    },

    deleteShareTrade(dto) {
        return new Promise(resolve => {
            axios
                .delete('/share/' + dto.symbol + '/' + dto.transactionId)
                .catch(error => {
                    console.error(error);
                    resolve(null);
                })
        })
    },

    recordOptionTrade(dto) {
        return new Promise(resolve => {
            axios
                .post('/option', dto)
                .then(response => {
                    resolve(response.data);
                }).catch(error => {
                console.error(error);
                resolve(null);
            })
        });
    },

    deleteOptionTrade(dto) {
        return new Promise(resolve => {
            axios
                .delete('/option/' + dto.stockSymbol + '/' + dto.transactionId)
                .catch(error => {
                    console.error(error);
                    resolve(null);
                })
        })
    },
    saveSettings(settings) {
        return new Promise(resolve => {
           axios.put('/settings/bulk', settings)
               .then(response => {
                   console.debug("Settings saved");
               })
               .catch(error => {
                   console.error(error);
                   resolve(null);
               })
        });
    }
};

export default appService;