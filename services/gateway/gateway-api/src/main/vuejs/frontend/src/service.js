import axios from 'axios';
import router from './router'

axios.defaults.baseURL = 'http://localhost:8085/api/v1';
const token = 'eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJCZW5kaXMiLCJzdWIiOiJhdXRoIiwicm9sZUlkIjoxLCJhY2NvdW50SWQiOiJkNzllYzExYS0yMDExLTQ0MjMtYmEwMS0zYWY4ZGUwYTNlMTQiLCJpYXQiOjE1ODU1OTcxOTMsImV4cCI6MTU4NjgwNjc5M30.JEWHDYjGSAHfuConpPGGLPV277meHaBV4-wAczOsiJU';
axios.defaults.headers.common['auth-key'] = token;


axios.interceptors.response.use(
    (response) => {
        return response
    },
    (error => {
        if (error.response.status === 401) {
            router.push('/login')
        }
        return Promise.reject(error)
    })
);

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
                .get('/tradelog/summary' )
                .then(response => {
                    if (response.status === 200) {
                        resolve(response.data)
                    } else {
                        console.debug(response)
                    }
                })
                .catch(error => {
                    console.error(error);
            })
        });
    },

    getSummaryMatrix() {
        return new Promise(resolve => {
            axios
                .get('/tradelog/summary/matrix' )
                .then(response => {
                    if (response.status === 200) {
                        resolve(response.data)
                    } else {
                        console.debug(response)
                    }
                })
                .catch(error => {
                    console.error(error);
                })
        });
    },

    getSharePrice(symbol) {
        return new Promise(resolve => {
            axios
                .get('/stockdata/' + symbol.toUpperCase())
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
                .put('/tradelog/options/' + dto.transactionId, dto)
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
    },

    saveSettings(settings) {
        return new Promise(resolve => {
            axios.put('/tradelog/settings/bulk', settings)
                .then(response => {
                    // console.debug("Bulk Settings saved");
                })
                .catch(error => {
                    console.error(error);
                    resolve(null);
                })
        });
    },
};

export default appService;
