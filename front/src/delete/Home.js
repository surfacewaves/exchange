import React, { useEffect, useState } from 'react'
import { exchangeApi } from '../service/ExchangeApi'
import { useAuth } from "react-oidc-context"
import AuthBar from '../service/AuthBar'

function Home() {
    const [latestRates, setLatestRates] = useState({ dateOfRates: null, rates: [] });
    const auth = useAuth()
    const access_token = auth.user.access_token

    useEffect(() => {
        handleLatestRates()
    }, [])

    const handleLatestRates = async () => {
        try {
            const response = await exchangeApi.getPopular(access_token)
            setLatestRates(response.data)
        } catch (error) {
            handleLogError(error)
        }
    }

    const handleLogError = (error) => {
        if (error.response) {
            console.log(error.response.data)
        } else if (error.request) {
            console.log(error.request)
        } else {
            console.log(error.message)
        }
    }

    const isUser = () => {
        const { profile } = auth.user
        const hasClientRole = profile?.client_roles?.includes('EXCHANGE-USER')
        return profile && hasClientRole
    }

    return (
        <div>
            <h1>hello</h1>
            <AuthBar />
            {isUser() ? (
                <div className="col-md-8">
                <h4 className="text-center mb-4" style={{ marginTop: "100px" }}>Popular rates list for RUB</h4>
                <div className="table-responsive">
                    <table className="table table-striped table-sm">
                        <thead>
                            <tr>
                                <th>From/To</th>
                                <th>Price</th>
                                <th>MaxToday</th>
                                <th>MinToday</th>
                                <th>Difference</th>
                                <th>Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            {latestRates.rates.map((rate, index) => (
                                <tr key={index + 1}>
                                    <td>{rate.fromCode}/{rate.toCode}</td>
                                    <td>{rate.price}</td>
                                    <td>{rate.maxPriceForLastDay}</td>
                                    <td>{rate.minPriceForLastDay}</td>
                                    <td>{rate.difference}</td>
                                    <td>{latestRates.dateOfRates}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
            ) : (
                <div style={{ textAlign: "center" }}>
                    <h2 style={{ color: 'grey' }}>You do not have access!</h2>
                </div>
            )}
        </div>
    )
}

export default Home