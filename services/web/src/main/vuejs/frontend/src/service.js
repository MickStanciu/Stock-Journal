import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8080/api/tradelog/d79ec11a-2011-4423-ba01-3af8de0a3e14';

const appService = {
    getTradedSymbols() {

        return new Promise(resolve => {
            axios.get('/symbols')
                .then(response => {
                    resolve(response.data)
                })
        });
    }
};

export default appService;