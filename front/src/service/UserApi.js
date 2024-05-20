import axios from 'axios'

export const userApi = {
    getAccount,
    getPortfolioByUser,
    countTotal
}

function getAccount(access_token, userId) {
    return instance.get('/v1/users/' + `${userId}`, {
        headers: { 'Authorization': `Bearer ${access_token}` }
    })
}

function getPortfolioByUser(access_token, userId) {
    return instance.get('/v1/portfolios/' + `${userId}`, {
        headers: { 'Authorization': `Bearer ${access_token}` }
    })
}

function countTotal(access_token, userId) {
    return instance.get('/v1/portfolios/' + `${userId}` + '/count-total', {
        headers: { 'Authorization': `Bearer ${access_token}` }
    })
}

// -- Axios

const instance = axios.create({
    baseURL: 'http://localhost:8012'
})