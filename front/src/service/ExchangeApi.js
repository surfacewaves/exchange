import axios from 'axios'

export const exchangeApi = {
    getPopular,
    getLatest,
    getHistorical
}

function getPopular() {
    return instance.get('/v1/rates/latest')
}

function getLatest(currencyName) {
    return instance.get('/v1/rates/latest?currency_name=' + `${currencyName}`)
}

function getHistorical(access_token, currencyName, currencyDate) {
    return instance.get('/v1/rates/historical?currency_name=' + `${currencyName}` + '&currency_date=' + `${currencyDate}`, {
        headers: { 'Authorization': `Bearer ${access_token}` }
    })
}

// -- Axios

const instance = axios.create({
    baseURL: 'http://localhost:8010'
})