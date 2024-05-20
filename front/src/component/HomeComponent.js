import React, { useEffect, useState } from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import Header from "./HeaderComponent";
import Footer from "./FooterComponent";
import News from "./NewsComponent";
import { exchangeApi } from "../service/ExchangeApi";

function HomeComponent() {
    const [latestRates, setLatestRates] = useState({ dateOfRates: null, rates: [] });
    
    useEffect(() => {
        handleLatestRates()
    }, [])

    const handleLatestRates = async () => {
        try {
            const response = await exchangeApi.getPopular()
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

    if (!latestRates || !latestRates.rates) {
        return <div>Loading...</div>;
    }

    const currentDate = new Date();
    const currentDay = currentDate.getDate();
    const currentMonth = currentDate.getMonth();
    const currentHours = currentDate.getHours();
    const currentMinutes = currentDate.getMinutes();

    return (
        <div>
            <Header />
            <div className="container">
                <div className="row">
                    <div className="col-md-8">
                        <h4 className="text-center mb-4" style={{ marginTop: "100px" }}>Popular rates list for RUB ({currentDay}.{currentMonth}, {currentHours}:{currentMinutes})</h4>
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
                    <div className="col-md-4" style={{ marginTop: "150px", paddingLeft: "50px" }}>
                        <News />
                    </div>
                </div>
            </div>
            <Footer />
        </div>
    );
}

export default HomeComponent;
