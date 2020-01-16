import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8085/api/v1';
const account_id = 'd79ec11a-2011-4423-ba01-3af8de0a3e14';
axios.defaults.headers.common['accountId'] = account_id;

const appService = {
    // getTradedSymbols() {
    //     return new Promise(resolve => {
    //         axios
    //             .get('/tradelog/symbols')
    //             .then(response => {
    //                 resolve(response.data)
    //             })
    //             .catch(error => {
    //                 console.error(error);
    //             })
    //     });
    // },

    getSummary() {
        return new Promise(resolve => {
            axios
                .get('/tradelog/summary')
                .then(response => {
                    resolve(response.data)
                })
                .catch(error => {
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
                })
                .catch(error => {
                    console.log(error);
            })
        });
    },

    getTradesPerSymbol(symbol) {
        return new Promise(resolve => {
            axios
                .get('/tradelog/all/' + symbol.toUpperCase())
                .then(response => {
                    resolve(response.data);
                })
                .catch(error => {
                    console.error(error);
                })
            });
    },

    recordShareTrade(dto) {
        return new Promise(resolve => {
            axios
                .post('/tradelog/shares', dto)
                .then(response => {
                    resolve(response.data);
                })
                .catch(error => {
                    console.error(error);
                    resolve(null);
            })
        });
    },

    editShareTrade(dto) {
        return new Promise(resolve => {
            axios
                .put('/tradelog/shares/' + dto.transactionId, dto)
                .then(() => {
                        // console.debug("AXIOS FINISHED");
                        resolve(null);
                    }
                )
                .catch(error => {
                    console.error(error);
                    resolve(null);
                })
        })
    },

    deleteShareTrade(dto) {
        return new Promise(resolve => {
            axios
                .delete('/tradelog/shares/' + dto.transactionId)
                .then(() => {
                        // console.debug("AXIOS FINISHED");
                        resolve(null);
                    }
                )
                .catch(error => {
                    console.error(error);
                    resolve(null);
                })
        })
    },

    recordOptionTrade(dto) {
        return new Promise(resolve => {
            axios
                .post('/tradelog/options', dto)
                .then(response => {
                    resolve(response.data);
                })
                .catch(error => {
                    console.error(error);
                    resolve(null);
            })
        });
    },

    editOptionTrade(dto) {
        return new Promise(resolve => {
            axios
                .put('/option/' + dto.symbol + '/' + dto.transactionId, dto)
                .then(() => {
                        // console.debug("AXIOS FINISHED");
                        resolve(null);
                    }
                )
                .catch(error => {
                    console.error(error);
                    resolve(null);
                })
        })
    },

    deleteOptionTrade(dto) {
        return new Promise(resolve => {
            axios
                .delete('/tradelog/options/' + dto.transactionId)
                .then(() => {
                        // console.debug("AXIOS FINISHED");
                        resolve(null);
                    }
                )
                .catch(error => {
                    console.error(error);
                    resolve(null);
                });
        })
    },

    recordDividendTrade(dto) {
        return new Promise(resolve => {
            axios
                .post('/tradelog/dividends', dto)
                .then(response => {
                    resolve(response.data);
                })
                .catch(error => {
                    console.error(error);
                    resolve(null);
                });
        })
    },

    deleteDividendRecord(dto) {
        return new Promise(resolve => {
            // console.debug("DELETING DIVIDEND started");
            axios
                .delete('/tradelog/dividends/' + dto.transactionId)
                .then(() => {
                        // console.debug("AXIOS FINISHED");
                        resolve(null);
                    }
                )
                .catch(error => {
                    console.error(error);
                    resolve(null);
                });
        })
    },

    saveSetting(setting) {
        return new Promise(resolve => {
            axios.put('/tradelog/settings/' + setting.transactionId, setting)
                .then(response => {
                    // console.debug("Settings saved");
                })
                .catch(error => {
                    console.error(error);
                    resolve(null);
                })
        });
    }
};

export default appService;
