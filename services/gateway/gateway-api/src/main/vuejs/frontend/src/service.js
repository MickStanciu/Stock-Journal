import axios from 'axios';
import router from './router'

axios.defaults.baseURL = 'http://localhost:8085/api/v1';

axios.interceptors.response.use(
    (response) => {
        return response
    },
    (error => {
        if (error.response.status === 401) {
            this.$store.dispatch('auth/fail');
            router.push('/login');
        }
        return Promise.reject(error)
    })
);

const appService = {
    auth(username, password) {
        return new Promise(resolve => {
            axios
                .post('/auth', {
                    "login_name": username,
                    "password": password
                })
                .then(response => {
                    resolve(response.data);
                })
                .catch(error => {
                    console.error(error);
                    resolve(null);
                })
        });
    },

    getSummary(token) {
        const config = {
            headers: {
                'x-auth-key': token
            }
        };

        return new Promise(resolve => {
            axios
                .get('/tradelog/summary', config)
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

    getSummaryMatrix(token) {
        const config = {
            headers: {
                'x-auth-key': token
            }
        };

        return new Promise(resolve => {
            axios
                .get('/tradelog/summary/matrix', config)
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
