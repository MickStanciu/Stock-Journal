import axios from 'axios';
import router from './router'

let portfolioId = '99ca7bad-196e-4122-9d3e-f3b445db980d';

axios.defaults.baseURL = 'http://gateway-api:8085/api/v1';

axios.interceptors.response.use(
    (response) => {
        return response
    },
    (error => {
        console.debug(error);
        if (error.response.status === 401) {
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
            },
            params: {
                'portfolio-id': portfolioId
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

    getSharePrice(symbol, token) {
        const config = {
            headers: {
                'x-auth-key': token
            }
        };

        return new Promise(resolve => {
            axios
                .get('/stockdata/' + symbol.toUpperCase(), config)
                .then(response => {
                    resolve(response.data);
                })
                .catch(error => {
                    console.log(error);
            })
        });
    },

    getTradesPerSymbol(symbol, token) {
        const config = {
            headers: {
                'x-auth-key': token
            },
            params: {
                'portfolio-id': portfolioId
            }
        };

        return new Promise(resolve => {
            axios
                .get('/tradelog/all/' + symbol.toUpperCase(), config)
                .then(response => {
                    resolve(response.data);
                })
                .catch(error => {
                    console.error(error);
                })
            });
    },

    recordShareTrade(dto, token) {
        const config = {
            headers: {
                'x-auth-key': token
            },
            params: {
                'portfolio-id': portfolioId
            }
        };

        return new Promise(resolve => {
            axios
                .post('/tradelog/shares', dto, config)
                .then(response => {
                    resolve(response.data);
                })
                .catch(error => {
                    console.error(error);
                    resolve(null);
            })
        });
    },

    editShareTrade(dto, token) {
        const config = {
            headers: {
                'x-auth-key': token
            }
        };

        return new Promise(resolve => {
            axios
                .put('/tradelog/shares/' + dto.transactionId, dto, config)
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

    deleteShareTrade(dto, token) {
        const config = {
            headers: {
                'x-auth-key': token
            }
        };

        return new Promise(resolve => {
            axios
                .delete('/tradelog/shares/' + dto.transactionId, config)
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

    recordOptionTrade(dto, token) {
        const config = {
            headers: {
                'x-auth-key': token
            },
            params: {
                'portfolio-id': portfolioId
            }
        };

        return new Promise(resolve => {
            axios
                .post('/tradelog/options', dto, config)
                .then(response => {
                    resolve(response.data);
                })
                .catch(error => {
                    console.error(error);
                    resolve(null);
            })
        });
    },

    editOptionTrade(dto, token) {
        const config = {
            headers: {
                'x-auth-key': token
            }
        };

        return new Promise(resolve => {
            axios
                .put('/tradelog/options/' + dto.transactionId, dto, config)
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

    deleteOptionTrade(dto, token) {
        const config = {
            headers: {
                'x-auth-key': token
            }
        };

        return new Promise(resolve => {
            axios
                .delete('/tradelog/options/' + dto.transactionId, config)
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

    recordDividendTrade(dto, token) {
        const config = {
            headers: {
                'x-auth-key': token
            },
            params: {
                'portfolio-id': portfolioId
            }
        };

        return new Promise(resolve => {
            axios
                .post('/tradelog/dividends', dto, config)
                .then(response => {
                    resolve(response.data);
                })
                .catch(error => {
                    console.error(error);
                    resolve(null);
                });
        })
    },

    deleteDividendRecord(dto, token) {
        const config = {
            headers: {
                'x-auth-key': token
            }
        };

        return new Promise(resolve => {
            // console.debug("DELETING DIVIDEND started");
            axios
                .delete('/tradelog/dividends/' + dto.transactionId, config)
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

    saveSetting(setting, token) {
        const config = {
            headers: {
                'x-auth-key': token
            }
        };

        return new Promise(resolve => {
            axios.put('/tradelog/settings/' + setting.transactionId, setting, config)
                .then(response => {
                    // console.debug("Settings saved");
                })
                .catch(error => {
                    console.error(error);
                    resolve(null);
                })
        });
    },

    saveSettings(settings, token) {
        const config = {
            headers: {
                'x-auth-key': token
            }
        };

        return new Promise(resolve => {
            axios.put('/tradelog/settings/bulk', settings, config)
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
