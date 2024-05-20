import React, { useState } from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import Header from "./HeaderComponent";
import Footer from "./FooterComponent";
import { exchangeApi } from "../service/ExchangeApi";
import { useAuth } from "react-oidc-context"

function HistoricalComponent() {
    const [historicalRates, setHistoricalRates] = useState({ dateOfRates: null, rates: [] });
    const [currencyName, setCurrencyName] = useState(""); // Состояние для хранения введенной валюты
    const [currencyDate, setCurrencyDate] = useState(""); // Состояние для хранения введенной даты
    const [errorMessage, setErrorMessage] = useState(""); // Сообщение об ошибке

    const auth = useAuth()
    const access_token = auth.user.access_token

    const handleHistoricalRates = async () => {
        try {
            const response = await exchangeApi.getHistorical(access_token, currencyName, currencyDate)
            setHistoricalRates(response.data)
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

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        if (name === "currencyName") {
            setCurrencyName(value.toUpperCase());
        } else if (name === "currencyDate") {
            setCurrencyDate(value);
        }
        setErrorMessage(""); // Сбрасываем сообщение об ошибке при изменении ввода
    };

    const handleButtonClick = () => {
        if (!currencyName || !currencyDate) {
            setErrorMessage("Please enter both currency code and date!");
            return;
        }

        handleHistoricalRates();
    };

    const isUser = () => {
        const { profile } = auth.user
        const hasClientRole = profile?.client_roles?.includes('EXCHANGE-USER')
        return profile && hasClientRole
    }

    return (
        <div>
            <Header />
            <div className="container mt-5">
                {isUser() ? (
                    <div className="row justify-content-center">
                        <div className="col-md-8">
                            <div className="text-center mb-4">
                                <input 
                                    type="text" 
                                    name="currencyName"
                                    value={currencyName} 
                                    onChange={handleInputChange} 
                                    maxLength={3} 
                                    placeholder="Enter currency code (e.g., USD)" 
                                    className="form-control"
                                />
                                <input 
                                    type="date" 
                                    name="currencyDate"
                                    value={currencyDate} 
                                    onChange={handleInputChange} 
                                    className="form-control mt-2"
                                />
                                <button className="btn btn-primary mt-2" onClick={handleButtonClick}>Get Historical Rates</button>
                                {errorMessage && <p className="text-danger mt-2">{errorMessage}</p>} {/* Отображение сообщения об ошибке */}
                            </div>
                            {
                                historicalRates.rates.length > 0 && (
                                    <div>
                                        <h4 className="text-center mb-4">Historical rates list for {currencyName} ({currencyDate})</h4>
                                        <div className="table-responsive">
                                            <table className="table table-striped table-sm">
                                                <thead>
                                                    <tr>
                                                        <th>From/To</th>
                                                        <th>Price</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    {historicalRates.rates.map((rate, index) => (
                                                        <tr key={index + 1}>
                                                            <td>{rate.fromCode}/{rate.toCode}</td>
                                                            <td>{rate.price}</td>
                                                        </tr>
                                                    ))}
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                )
                            }   
                        </div>
                    </div>
                ) : (
                    <div style={{ textAlign: "center" }}>
                        <h2 style={{ color: 'grey' }}>You do not have access!</h2>
                    </div>
                )}
            </div>
            <Footer />
        </div>
    );
}

export default HistoricalComponent;
