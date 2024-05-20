import React, { useEffect, useState } from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import Header from "./HeaderComponent";
import Footer from "./FooterComponent";
import { exchangeApi } from "../service/ExchangeApi";

function LatestComponent() {
    const [latestRates, setLatestRates] = useState({ dateOfRates: null, rates: [] });
    const [currencyName, setCurrencyName] = useState(""); // Состояние для хранения введенной валюты
    const [errorMessage, setErrorMessage] = useState(""); // Сообщение об ошибке

    const handleLatestRates = async () => {
        try {
            const response = await exchangeApi.getLatest(currencyName)
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

    const handleInputChange = (event) => {
        const input = event.target.value.toUpperCase(); // Преобразуем введенный текст в верхний регистр
        // Проверяем, что введенные символы латинские буквы и их количество не превышает 3
        if (/^[A-Za-z]{0,3}$/.test(input)) {
            setCurrencyName(input);
            setErrorMessage(""); // Сбрасываем сообщение об ошибке, если ввод верный
        } else {
            setErrorMessage("Неверный формат кода валюты!"); // Устанавливаем сообщение об ошибке
        }
    };

    const handleButtonClick = () => {
        // Проверяем, что введено ровно три латинские буквы
        if (/^[A-Za-z]{3}$/.test(currencyName)) {
            handleLatestRates();
            setErrorMessage(""); // Сбрасываем сообщение об ошибке, если ввод верный
        } else {
            setErrorMessage("Неверный формат кода валюты!"); // Устанавливаем сообщение об ошибке
        }
    };

    const currentDate = new Date();
    const currentDay = currentDate.getDate();
    const currentMonth = currentDate.getMonth();
    const currentHours = currentDate.getHours();
    const currentMinutes = currentDate.getMinutes();

    return (
        <div>
            <Header />
            <div className="container mt-5">
                <div className="row justify-content-center">
                    <div className="col-md-8">
                        <div className="text-center mb-4">
                            <input 
                                type="text" 
                                value={currencyName} 
                                onChange={handleInputChange} 
                                maxLength={3} 
                                placeholder="Enter currency code (e.g., USD)" 
                                className="form-control"
                            />
                            <button className="btn btn-primary mt-2" onClick={handleButtonClick}>Get Latest Rates</button>
                            {errorMessage && <p className="text-danger mt-2">{errorMessage}</p>} {/* Отображение сообщения об ошибке */}
                        </div>
                        {
                            currencyName && (
                                <h4 className="text-center mb-4">Latest rates list for {currencyName} ({currentDay}.{currentMonth}, {currentHours}:{currentMinutes})</h4>
                            )
                        }   
                        {
                            !currencyName && (
                                <h4 className="text-center mb-4">Please enter currency code</h4>
                            )
                        }
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
                </div>
            </div>
            <Footer />
        </div>
    );
}

export default LatestComponent;
