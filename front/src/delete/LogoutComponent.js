import React from 'react';
import Cookies from 'js-cookie';

const LogoutComponent = () => {
  const handleLogout = () => {
    Cookies.remove('token');
    window.location.href = '/home';
  };

  return (
    <button onClick={handleLogout}>Logout</button>
  );
};

export default LogoutComponent;
