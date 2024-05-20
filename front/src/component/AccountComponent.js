import React, { useState, useEffect } from "react";
import Footer from "./FooterComponent";
import Header from "./HeaderComponent";
import { userApi } from "../service/UserApi";
import AuthBar from "../service/AuthBar";
import { useAuth } from "react-oidc-context"

function AccountComponent() {
    const [user, setUser] = useState(null);

    const auth = useAuth()
    const access_token = auth.user.access_token

    useEffect(() => {
        handleUser()
    }, [])

    const handleUser = async () => {
        try {
            const response = await userApi.getAccount(access_token, access_token ? JSON.parse(atob(access_token.split('.')[1])).sub : null)
            setUser(response.data)
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
            <Header />
            <div className="container mt-5">
                {isUser() ? (
                    <div className="row justify-content-center">
                        <div className="col-md-6">
                            <div className="card">
                                <div className="card-header bg-primary text-white">
                                    <h2 className="mb-0">User Details</h2>
                                </div>
                                <div className="card-body">
                                    {user ? (
                                        <div>
                                            <p><strong>Username:</strong> {user.username}</p>
                                            <p><strong>First Name:</strong> {user.firstName}</p>
                                            <p><strong>Last Name:</strong> {user.lastName}</p>
                                            <p><strong>Email:</strong> {user.email}</p>
                                            <p><strong>Registered Date:</strong> {user.registeredDatetime}</p>
                                            <div className="d-flex justify-content-between align-items-center">
                                                <div>
                                                    <AuthBar />
                                                </div>
                                                <div>
                                                    <a href="/account/portfolio">check your portfolio</a>
                                                </div>
                                            </div>
                                        </div>
                                    ) : (
                                        <p>Loading...</p>
                                    )}
                                </div>
                            </div>
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

export default AccountComponent;
