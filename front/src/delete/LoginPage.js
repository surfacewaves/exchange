import React, { useState } from 'react';
import axios from 'axios';
import qs from 'qs';
import Cookies from 'js-cookie';
import 'bootstrap/dist/css/bootstrap.min.css';

const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();
        
        try {
            const response = await axios.post(
                'http://localhost:8282/realms/client/protocol/openid-connect/token',
                qs.stringify({ username, // Сериализуем данные в формат x-www-form-urlencoded
                               password, 
                               client_id: 'backend',
                               client_secret: 'h0KQrGLOEVCOZf5Xc9lzfkN4QIcXyFLs',
                               grant_type: 'password'}),
                {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded' // Устанавливаем заголовок для указания формата данных
                    }
                }
            );

            
            // В случае успешного ответа выполните логин пользователя
            // Например, сохраните токен аутентификации в локальном хранилище
            // После этого перенаправьте пользователя на домашнюю страницу или куда-либо еще
            Cookies.set('token', response.data.access_token);

            // Перенаправляем пользователя на другую страницу
            window.location.href = '/home'; // Замените '/home' на путь к вашей домашней странице
            
        } catch (error) {
            console.error('Login error:', error);
            setError('Invalid credentials');
        }
    };

    return (
        <div className="container">
            <div className="row justify-content-center mt-5">
                <div className="col-md-6">
                    <h2 className="mb-4">Login</h2>
                    {error && <div className="alert alert-danger" role="alert">{error}</div>}
                    <form onSubmit={handleLogin}>
                        <div className="mb-3">
                            <label htmlFor="exampleInputUsername" className="form-label">Username</label>
                            <input type="text" className="form-control" id="exampleInputUsername" value={username} onChange={(e) => setUsername(e.target.value)} />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="exampleInputPassword" className="form-label">Password</label>
                            <input type="password" className="form-control" id="exampleInputPassword" value={password} onChange={(e) => setPassword(e.target.value)} />
                        </div>
                        <button type="submit" className="btn btn-primary">Login</button>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default LoginPage;
