import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Table, Form, Button } from 'react-bootstrap';
import { userApi } from "../service/UserApi";
import { useAuth } from "react-oidc-context"
import Footer from "./FooterComponent";
import Header from "./HeaderComponent";

const PortfolioComponent = () => {
    const [portfolio, setPortfolio] = useState(null);
    const [total, setTotal] = useState(null)

    const auth = useAuth()
    const access_token = auth.user.access_token

    useEffect(() => {
        handlePortfolio()
    }, [])

    const handlePortfolio = async () => {
        try {
            const response = await userApi.getPortfolioByUser(access_token, access_token ? JSON.parse(atob(access_token.split('.')[1])).sub : null)
            setPortfolio(response.data)
            setTotal(response.data.total.toFixed(4))
        } catch (error) {
            handleLogError(error)
        }
    }
    
    const handleTotal = async () => {
        try {
            const response = await userApi.countTotal(access_token, access_token ? JSON.parse(atob(access_token.split('.')[1])).sub : null)
            setPortfolio(response.data)
            setTotal(response.data.total.toFixed(4))
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

    const handleButtonClick = () => {
        handleTotal();
    };

    return (
        <div>
            <Header />
            <Container>
                <Row>
                    <Col>
                        {portfolio && (
                            <div className="portfolio-summary">
                                <h2>Portfolio Summary</h2>
                                <dl className="row">
                                    <dt className="col-sm-3">ID</dt>
                                    <dd className="col-sm-9">{portfolio.id}</dd>

                                    <dt className="col-sm-3">Base Currency</dt>
                                    <dd className="col-sm-9">{portfolio.baseCurrency}</dd>

                                    <dt className="col-sm-3">Total</dt>
                                    <dd className="col-sm-9">{total}</dd>

                                    <dt className="col-sm-3">Last Updated</dt>
                                    <dd className="col-sm-9">{portfolio.lastUpdatedDatetime}</dd>

                                    <dt className="col-sm-3">User</dt>
                                    <dd className="col-sm-9">{portfolio.user.username}</dd>
                                </dl>
                            </div>
                        )}
                    </Col>
                </Row>
                <Row>
                    <Col>
                        {portfolio && (
                            <div className="portfolio-items">
                                <h2>Portfolio Items</h2>
                                <Table striped bordered hover>
                                    <thead>
                                        <tr>
                                            <th>Active Name</th>
                                            <th>Active Amount</th>
                                            <th>Last Updated</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {portfolio.items.map(item => (
                                            <tr key={item.id}>
                                                <td>{item.activeName}</td>
                                                <td>{item.activeAmount}</td>
                                                <td>{item.lastUpdatedDatetime}</td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </Table>
                            </div>
                        )}
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Form>
                            <h4>
                                Count total by latest rates
                            </h4>

                            <Button onClick={handleButtonClick}>
                                Count
                            </Button>
                        </Form>
                    </Col>
                </Row>
            </Container>
            <Footer />
        </div>
    );
};

export default PortfolioComponent;
